package org.jt.Request;

import org.jt.model.Constants;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class AuthorizationUrl {

    private String authorizationEndpoint;
    private String consentId;
    private String redirectUri;
    private String clientId;

    public AuthorizationUrl(String authorizationEndpoint, String consentId, String redirectUri, String clientId) {
        this.authorizationEndpoint = authorizationEndpoint;
        this.consentId = consentId;
        this.redirectUri = redirectUri;
        this.clientId = clientId;
    }

    @Override
    public String toString() {

        String url="";
        try {
            url = String.join(
                    authorizationEndpoint,
                    "?client_id=", clientId,
                    "&response_type=", URLEncoder.encode("code id_token", "UTF-8"),
                    "&scope=",URLEncoder.encode("openid payments", "UTF-8"),
                    "&redirect_uri=", URLEncoder.encode(redirectUri, "UTF-8"),
                    "&state=","ABC",
                    "&request=",consentId
            );
        } catch (UnsupportedEncodingException e) {
            //swallow this as it can't happen
        }
        return url;
    }
}
