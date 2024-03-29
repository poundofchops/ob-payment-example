package org.jt.consent;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.jose4j.lang.JoseException;
import org.jt.configuration.RestClientConfiguration;
import org.jt.model.payments.*;
import org.jt.model.token.AccessTokenResponse;
import org.jt.model.wellknown.WellKnownResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.PrivateKey;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SingleImmediatePaymentConsent {

    private static Logger logger = LoggerFactory.getLogger(SingleImmediatePaymentConsent.class);

    private static final String PAYMENTS_CONSENT_URI = "https://ob.ulster.useinfinite.io/open-banking/v3.1/pisp/domestic-payment-consents";

    // Rest Client Configuration
    private RestClientConfiguration restClientConfiguration;

    // RestTemplate for non mTLS calls
    private RestTemplate vanillaRestTemplate;

    // RestTemplate for mTLS calls
    private RestTemplate sslRestTemplate;

    // RSA JWT Signer
    private PrivateKey signingKey;

    @Autowired
    public void setRestClientConfiguration(RestClientConfiguration restClientConfiguration){
        this.restClientConfiguration = restClientConfiguration;
    }

    @Autowired
    public void setSslRestTemplate(RestTemplate sslRestTemplate) {
        this.sslRestTemplate = sslRestTemplate;
    }

    @Autowired
    public void setVanillaRestTemplate(RestTemplate vanillaRestTemplate){
        this.vanillaRestTemplate = vanillaRestTemplate;
    }

    @Autowired
    public void setSigningKey(PrivateKey signingKey){
        this.signingKey = signingKey;
    }

    @PostConstruct
    public void applicationLaunch() throws Exception{

        ResponseEntity<WellKnownResponse> wellKnownResponse = vanillaRestTemplate.getForEntity(restClientConfiguration.getWellKnownOpenIDConfigurationUri(), WellKnownResponse.class);
        String tokenEndpointUri=wellKnownResponse.getBody().getTokenEndpoint();
        logger.info(String.format(".well-known config retrieved, token URI identified as %s", tokenEndpointUri));

        ResponseEntity<AccessTokenResponse> accessTokenResponse = sslRestTemplate.postForEntity(tokenEndpointUri, createClientCredentialsAccessTokenRequestObject(), AccessTokenResponse.class);

        String accessToken = accessTokenResponse.getBody().getAccessToken();
        logger.info("Retrieved Access Response -> "+accessToken);

        HttpEntity<OBWriteDomesticConsent2> paymentConsentRequest = createPaymentConsentObject(accessToken);

        ResponseEntity<OBWriteDomesticConsentResponse2> paymentConsentResponse = sslRestTemplate.postForEntity(PAYMENTS_CONSENT_URI, paymentConsentRequest, OBWriteDomesticConsentResponse2.class);
        logger.info("Payment consent submitted. Response code = "+paymentConsentResponse.getStatusCode());
        logger.debug(paymentConsentResponse.getBody().toString());

        logger.info("Now authorise the request "+generateAuthoriseUrl(wellKnownResponse.getBody(), paymentConsentResponse.getBody()));
    }


    private String generateAuthoriseUrl(WellKnownResponse wellKnownResponse, OBWriteDomesticConsentResponse2 paymentConsentResponse) throws UnsupportedEncodingException, JoseException, ParseException {
        String state = generateState();

        RequestObject requestObject = new RequestObject
                                        .Builder()
                                        .consentId(paymentConsentResponse.getData().getConsentId())
                                        .clientConfiguration(restClientConfiguration)
                                        .state(state)
                                        .signingKey(signingKey)
                                        .build();

        StringBuilder authUrlBuilder = new StringBuilder();

        authUrlBuilder.append(wellKnownResponse.getAuthorizationEndpoint());
        authUrlBuilder.append("?client_id=");
        authUrlBuilder.append(restClientConfiguration.getClientId());
        authUrlBuilder.append("&response_type=");
        authUrlBuilder.append(restClientConfiguration.getResponseTypes());
        authUrlBuilder.append("&scope=");
        authUrlBuilder.append(restClientConfiguration.getScopes());
        authUrlBuilder.append("&redirect_uri=");
        authUrlBuilder.append(URLEncoder.encode(restClientConfiguration.getRedirectUri(), "UTF-8"));
        authUrlBuilder.append("&state=");
        authUrlBuilder.append(state);
        authUrlBuilder.append("&request=");
        authUrlBuilder.append(requestObject.asString());

        logger.info("Consent object -> "+requestObject.asString());

        return authUrlBuilder.toString();
    }

    private String generateState() {
        return "static_value";
    }

    public HttpEntity<MultiValueMap<String, String>> createClientCredentialsAccessTokenRequestObject(){
        List<MediaType> acceptTypes = new ArrayList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(acceptTypes);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "client_credentials");
        map.add("client_id", restClientConfiguration.getClientId());
        map.add("scope", "payments");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return request;
    }


    public HttpEntity<OBWriteDomesticConsent2> createPaymentConsentObject(String accessToken) {
        List<MediaType> acceptTypes = new ArrayList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptTypes);

        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("x-fapi-financial-id", restClientConfiguration.getFinancialId());
        headers.add("x-jws-signature", "ignored");
        headers.add("x-idempotency-key", generateGuid());

        OBWriteDomesticConsent2 requestBody = buildJsonPayload();

        HttpEntity<OBWriteDomesticConsent2> request = new HttpEntity<>(requestBody, headers);

        return request;
    }

    private OBWriteDomesticConsent2 buildJsonPayload() {
        // payment initiation data
        OBWriteDataDomesticConsent2 data = new OBWriteDataDomesticConsent2().initiation(
                        new OBDomestic2()
                                .instructedAmount(
                                        new OBDomestic2InstructedAmount()
                                            .amount("120")
                                            .currency("EUR")
                                )
                                .creditorAccount(
                                        new OBCashAccountCreditor3()
                                            .schemeName("UK.OBIE.SortCodeAccountNumber")
                                            .identification("08080021325698")
                                            .name("Where the money goes")
                                            .secondaryIdentification("secondary id")
                                )
                                .instructionIdentification("instruction id")
                                .endToEndIdentification("e2e id")
        );


        // risk data
        OBRisk1 risk = new OBRisk1().paymentContextCode(OBExternalPaymentContext1Code.ECOMMERCEGOODS);

        // consent request
        return new OBWriteDomesticConsent2().data(data).risk(risk);
    }

    private String generateGuid() {
        return UUID.randomUUID().toString().substring(0,32);
    }
}
