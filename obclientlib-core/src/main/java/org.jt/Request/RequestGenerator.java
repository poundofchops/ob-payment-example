package org.jt.Request;


import org.jt.model.Constants;
import org.jt.model.HttpMethod;
import org.jt.model.HttpRequest;
import org.jt.model.SubmitRequest;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestGenerator {


    protected abstract String getClientCredentialsScope();

    public HttpRequest createWellKnownEndpointRequest(String wellKnownEndpointUri){
        return new HttpRequest().withUrl(wellKnownEndpointUri);
    }

    public HttpRequest createClientCredentialsAccessTokenRequest(String tokenEndpointUri, String clientId){

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("accept", Constants.APPLICATION_JSON);
        headers.put("content-type", Constants.X_WWW_FORM_URLENCODED);

        String body = String.join(
                "grant_type=",Constants.CLIENT_CREDENTIALS_GRANT,
                "&client_id=", clientId,
                "&scope=", getClientCredentialsScope()
        );

        return new HttpRequest().withMethod(HttpMethod.POST)
                                .withUrl(tokenEndpointUri)
                                .withHeaders(headers)
                                .withBody(body);
    }

    public HttpRequest createAuthCodeAccessTokenRequest(String tokenEndpointUri, String clientId,
                                                        String redirectUri, SubmitRequest submitRequest){

        Map<String,String> headers = new HashMap<String, String>();
        headers.put("accept", Constants.APPLICATION_JSON);
        headers.put("content-type", Constants.X_WWW_FORM_URLENCODED);

        String body = String.join(
                "grant_type=",Constants.AUTH_CODE_GRANT,
                "&client_id=", clientId,
                "&redirect_uri=", redirectUri,
                "&code=", submitRequest.getAuthorisationCode()
        );

        return new HttpRequest().withMethod(HttpMethod.POST)
                .withUrl(tokenEndpointUri)
                .withHeaders(headers)
                .withBody(body);

    }
}
