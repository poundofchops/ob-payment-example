package org.jt.model;

import java.text.ParseException;

import com.nimbusds.jwt.SignedJWT;

public class SubmitRequest {

    private String code;
    private String idToken;
    private String state;

    public SubmitRequest(String code, String idToken, String state) {
        this.code = code;
        this.idToken = idToken;
        this.state = state;
    }

    public String getAuthorisationCode() {
        return code;
    }

    public String getConsentId() throws ParseException {
        return (String)SignedJWT.parse(idToken).getJWTClaimsSet().getClaim(Claims.OPEN_BANKING_INTENT_ID_CLAIM);
    }
}
