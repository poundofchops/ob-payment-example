package org.jt.Request;

import org.jt.model.Constants;
import org.jt.model.HttpMethod;
import org.jt.model.HttpRequest;

import java.util.HashMap;
import java.util.Map;

public class PaymentsRequestGenerator extends RequestGenerator{

    @Override
    protected String getClientCredentialsScope() {
        return "payments";
    }

    public HttpRequest createPaymentsConsentRequest(String consentCreationUri, String accessToken, String financialId,
                                                    String idempotencyKey, String body){

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("accept", Constants.APPLICATION_JSON);
        headers.put("content-type", Constants.APPLICATION_JSON);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("x-fapi-financial-id", financialId);
        headers.put("x-jws-signature", "ignored");
        headers.put("x-idempotency-key", idempotencyKey);

        return new HttpRequest().withMethod(HttpMethod.POST)
                .withUrl(consentCreationUri)
                .withHeaders(headers)
                .withBody(body);
    }

    public HttpRequest createPaymentsSubmitRequest(String submitUri, String accessToken, String financialId,
                                                   String body){

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("accept", Constants.APPLICATION_JSON);
        headers.put("content-type", Constants.APPLICATION_JSON);
        headers.put("Authorization", "Bearer " + accessToken);
        headers.put("x-fapi-financial-id", financialId);
        headers.put("x-jws-signature", "ignored");
        headers.put("x-idempotency-key", "ignored");

        return new HttpRequest().withMethod(HttpMethod.POST)
                .withUrl(submitUri)
                .withHeaders(headers)
                .withBody(body);
    }
}
