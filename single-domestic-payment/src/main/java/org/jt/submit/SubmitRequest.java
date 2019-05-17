package org.jt.submit;


import com.nimbusds.jwt.SignedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;

public class SubmitRequest {

    private Logger logger = LoggerFactory.getLogger(SubmitRequest.class);

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

    public String getConsentId() {
        String consentId = "";
        try {
            SignedJWT signedJWT = SignedJWT.parse(idToken);
            consentId = (String) signedJWT.getJWTClaimsSet().getClaim("openbanking_intent_id");
        } catch (ParseException e) {
            logger.error("Unable to extract ConsentId from ID Token",e);
        }

        return consentId;

    }
}
