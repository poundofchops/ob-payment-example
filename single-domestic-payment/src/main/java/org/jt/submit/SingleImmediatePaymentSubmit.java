package org.jt.submit;

import org.jt.configuration.RestClientConfiguration;
import org.jt.model.payments.OBWriteDataDomestic2;
import org.jt.model.payments.OBWriteDomestic2;
import org.jt.model.payments.OBWriteDomesticResponse2;
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
        logger.info("++++ Submit request received "+submitRequest);

        logger.info(String.format("++++ Retrieving openid-configuration from %s", restClientConfiguration.getWellKnownOpenIDConfigurationUri()));

        ResponseEntity<WellKnownResponse> wellKnownResponse = vanillaRestTemplate.getForEntity(restClientConfiguration.getWellKnownOpenIDConfigurationUri(), WellKnownResponse.class);
        String tokenEndpointUri=wellKnownResponse.getBody().getTokenEndpoint();
        logger.info(String.format("++++ .well-known config retrieved, token URI identified as %s", tokenEndpointUri));

        logger.info("Requesting Access Token");
        ResponseEntity<AccessTokenResponse> accessTokenResponse = sslRestTemplate.postForEntity(tokenEndpointUri, createAuthCodeAccessTokenRequestObject(submitRequest), AccessTokenResponse.class);

        String accessToken = accessTokenResponse.getBody().getAccessToken();
        logger.info("+++ Access Token Response -> "+accessToken);

        HttpEntity<OBWriteDomestic2> paymentSubmitRequest = createPaymentSubmitRequest(accessToken, submitRequest);

        ResponseEntity<OBWriteDomesticResponse2> paymentConsentResponse = sslRestTemplate.postForEntity(PAYMENTS_SUBMIT_URI, paymentSubmitRequest, OBWriteDomesticResponse2.class);
        logger.info("+++ Payment Consent Response -> "+paymentConsentResponse.getBody());
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

        OBWriteDomestic2 paymentSubmitBody = createPaymentSubmitBody();

        HttpEntity<OBWriteDomestic2> request = new HttpEntity<OBWriteDomestic2>(paymentSubmitBody, headers);

        return request;

    }

    private OBWriteDomestic2 createPaymentSubmitBody() {

        OBWriteDomestic2 payment = new OBWriteDomestic2();
        payment
                .data(new OBWriteDataDomestic2()
                        .)
                .risk()

        //todo - fill me in
        return payment;
    }

    private HttpEntity<MultiValueMap<String, String>> createAuthCodeAccessTokenRequestObject(SubmitRequest submitRequest) {
//        List<MediaType> acceptTypes = new ArrayList<>();
//        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//        headers.setAccept(acceptTypes);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("grant_type", "authorization_code");
        map.add("client_id", restClientConfiguration.getClientId());
        map.add("redirect_uri", restClientConfiguration.getRedirectUri());
        map.add("code", submitRequest.getAuthorisationCode());

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

        return request;

    }


}
