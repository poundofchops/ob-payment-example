package org.jt.submit;

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

import java.util.ArrayList;
import java.util.List;

@Component
public class SingleImmediatePaymentSubmit {
    private static final Logger logger = LoggerFactory.getLogger(SingleImmediatePaymentSubmit.class);

    private static final String PAYMENTS_SUBMIT_URI = "https://ob.ulster.useinfinite.io/open-banking/v3.1/pisp/domestic-payments";

    // Rest Client Configuration
    private RestClientConfiguration restClientConfiguration;

    // RestTemplate for non mTLS calls
    private RestTemplate vanillaRestTemplate;

    // RestTemplate for mTLS calls
    private RestTemplate sslRestTemplate;

    private String authCode = "changeme";

    @Autowired
    public void setRestClientConfiguration(RestClientConfiguration restClientConfiguration){
        this.restClientConfiguration = restClientConfiguration;
    }

    @Autowired
    public void setSslRestTemplate(RestTemplate sslRestTemplate) {
        this.sslRestTemplate = sslRestTemplate;
    }

    @Autowired
    public void setVanillaRestTemplate(RestTemplate vanillaRestTemplate) {
        this.vanillaRestTemplate = vanillaRestTemplate;
    }

    public void submit(SubmitRequest submitRequest){
        logger.info("Submit request received "+submitRequest);

        ResponseEntity<WellKnownResponse> wellKnownResponse = vanillaRestTemplate.getForEntity(restClientConfiguration.getWellKnownOpenIDConfigurationUri(), WellKnownResponse.class);
        String tokenEndpointUri=wellKnownResponse.getBody().getTokenEndpoint();

        logger.info("Requesting Access Token from "+tokenEndpointUri);
        ResponseEntity<AccessTokenResponse> accessTokenResponse = sslRestTemplate.postForEntity(tokenEndpointUri, createAuthCodeAccessTokenRequestObject(submitRequest), AccessTokenResponse.class);
        String accessToken = accessTokenResponse.getBody().getAccessToken();
        logger.info("Access Token Response "+accessToken);

        HttpEntity<OBWriteDomestic2> paymentSubmitRequest = createPaymentSubmitRequest(accessToken, submitRequest);

        ResponseEntity<OBWriteDomesticResponse2> paymentSubmitResponse = sslRestTemplate.postForEntity(PAYMENTS_SUBMIT_URI, paymentSubmitRequest, OBWriteDomesticResponse2.class);
        logger.info("Payment Submit Response -> "+paymentSubmitResponse.getBody());
    }

    private HttpEntity<OBWriteDomestic2> createPaymentSubmitRequest(String accessToken, SubmitRequest submitRequest) {
        List<MediaType> acceptTypes = new ArrayList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptTypes);

        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("x-fapi-financial-id", restClientConfiguration.getFinancialId());
        headers.add("x-jws-signature", "ignored");
        headers.add("x-idempotency-key", "ignored");

        OBWriteDomestic2 paymentSubmitBody = createPaymentSubmitBody(submitRequest);

        HttpEntity<OBWriteDomestic2> request = new HttpEntity<OBWriteDomestic2>(paymentSubmitBody, headers);

        return request;
    }

    private OBWriteDomestic2 createPaymentSubmitBody(SubmitRequest submitRequest) {
        // payment initiation data
        OBWriteDataDomestic2 data = new OBWriteDataDomestic2()
                .consentId(submitRequest.getConsentId())
                .initiation(
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
        OBRisk1 risk = new OBRisk1()
                .paymentContextCode(OBExternalPaymentContext1Code.ECOMMERCEGOODS);

        // submit payment request
        return new OBWriteDomestic2().data(data).risk(risk);
    }

    private HttpEntity<MultiValueMap<String, String>> createAuthCodeAccessTokenRequestObject(SubmitRequest submitRequest) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", restClientConfiguration.getClientId());
        map.add("redirect_uri", restClientConfiguration.getRedirectUri());
        map.add("code", submitRequest.getAuthorisationCode());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return request;
    }

}
