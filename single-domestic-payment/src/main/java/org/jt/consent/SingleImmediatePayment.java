package org.jt.consent;

import org.jt.configuration.RestClientConfiguration;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class SingleImmediatePayment {

    private static Logger logger = LoggerFactory.getLogger(SingleImmediatePayment.class);

    private static final String PAYMENTS_CONSENT_URI = "https://ob.ulster.useinfinite.io/open-banking/v3.1/pisp/domestic-payment-consents";
    private static final String PAYMENTS_CONSENT_BODY = "{\n" +
            "  \"Data\": {\n" +
            "    \"Initiation\": {\n" +
            "      \"InstructionIdentification\": \"instr-identification\",\n" +
            "      \"EndToEndIdentification\": \"e2e-identification\",\n" +
            "      \"InstructedAmount\": {\n" +
            "        \"Amount\": \"50.00\",\n" +
            "        \"Currency\": \"EUR\"\n" +
            "      },\n" +
            "      \"DebtorAccount\": null,\n" +
            "      \"CreditorAccount\": {\n" +
            "        \"SchemeName\": \"IBAN\",\n" +
            "        \"Identification\": \"BE56456394728288\",\n" +
            "        \"Name\": \"ACME DIY\",\n" +
            "        \"SecondaryIdentification\": \"secondary-identif\"\n" +
            "      },\n" +
            "      \"RemittanceInformation\": {\n" +
            "        \"Unstructured\": \"Tools\",\n" +
            "        \"Reference\": \"Tools\"\n" +
            "      }\n" +
            "    }\n" +
            "  },\n" +
            "  \"Risk\": {\n" +
            "    \"PaymentContextCode\": \"EcommerceGoods\",\n" +
            "    \"MerchantCategoryCode\": null,\n" +
            "    \"MerchantCustomerIdentification\": null,\n" +
            "    \"DeliveryAddress\": null\n" +
            "  }\n" +
            "}\n";

    // Rest Client Configuration
    private RestClientConfiguration restClientConfiguration;

    // RestTemplate for non mTLS calls
    private RestTemplate vanillaRestTemplate;

    // RestTemplate for mTLS calls
    private RestTemplate sslRestTemplate;

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


    @PostConstruct
    public void applicationLaunch() throws Exception{
        logger.info(String.format("++++ Retrieving openid-configuration from %s", restClientConfiguration.getWellKnownOpenIDConfigurationUri()));

        ResponseEntity<WellKnownResponse> wellKnownResponseResponseEntity = vanillaRestTemplate.getForEntity(restClientConfiguration.getWellKnownOpenIDConfigurationUri(), WellKnownResponse.class);
        String tokenEndpointUri=wellKnownResponseResponseEntity.getBody().getTokenEndpoint();
        logger.info(String.format("++++ .well-known config retrieved, token URI identified as %s", tokenEndpointUri));

        ResponseEntity<AccessTokenResponse> accessTokenResponse = sslRestTemplate.postForEntity(tokenEndpointUri, createAccessTokenRequestObject(), AccessTokenResponse.class);

        String accessToken = accessTokenResponse.getBody().getAccessToken();
        logger.info("+++ Access Token Response -> "+accessToken);

        ResponseEntity<String> paymentConsentResponse = sslRestTemplate.postForEntity(PAYMENTS_CONSENT_URI, createPaymentConsentObject(accessToken), String.class);
        logger.info("+++ Payment Consent Response -> "+paymentConsentResponse.getBody());


    }

    public HttpEntity<MultiValueMap<String, String>> createAccessTokenRequestObject(){

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


    public HttpEntity<String> createPaymentConsentObject(String accessToken) {
        List<MediaType> acceptTypes = new ArrayList<>();
        acceptTypes.add(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(acceptTypes);

        headers.add("Authorization", "Bearer "+accessToken);
        headers.add("x-fapi-financial-id", restClientConfiguration.getFinancialId());
        headers.add("x-jws-signature", "ignored");
        headers.add("x-idempotency-key", generateGuid());


        HttpEntity<String> request = new HttpEntity<>(PAYMENTS_CONSENT_BODY, headers);

        return request;
    }

    private String generateGuid() {

        return UUID.randomUUID().toString();
    }
}
