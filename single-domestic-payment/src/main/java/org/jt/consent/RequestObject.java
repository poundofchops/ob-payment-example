package org.jt.consent;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONString;
import org.jt.configuration.RestClientConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.PrivateKey;
import java.text.ParseException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class RequestObject {

    private static Logger logger = LoggerFactory.getLogger(RequestObject.class);

    private boolean isReducedSecurityEnabled;
    private String consentId;
    private String consentJwt;

    private RequestObject(Builder builder){
        isReducedSecurityEnabled = builder.isReducedSecurityEnabled;
        consentId = builder.consentId;
        consentJwt = builder.consentJwt;
    }

    public String asString(){
        if (isReducedSecurityEnabled){
            consentJwt = consentId;
        }

        return consentJwt;
    }

    public static class Builder {

        private String consentId;
        private String state;
        private boolean isReducedSecurityEnabled;
        private String audience;
        private String clientId;
        private String responseType;
        private String scope;
        private String redirectUri;
        private PrivateKey signingKey;
        private String signingKeyId;
        private String consentJwt;

        public Builder getBuilderInstance(){
            return new Builder();
        }

        public Builder clientConfiguration(RestClientConfiguration clientConfiguration){
            isReducedSecurityEnabled = clientConfiguration.isReducedSecurityEnabled();
            audience = clientConfiguration.getAudience();
            clientId = clientConfiguration.getClientId();
            responseType = clean(clientConfiguration.getResponseTypes());
            scope = clean(clientConfiguration.getScopes());
            redirectUri = clientConfiguration.getRedirectUri();
            signingKeyId = clientConfiguration.getSigningKid();

            return this;
        }

        private String clean(String multipleValuesPossiblySpaceEncoded) {
            if (multipleValuesPossiblySpaceEncoded!= null && multipleValuesPossiblySpaceEncoded.contains("%20")){
                return multipleValuesPossiblySpaceEncoded.replaceAll("%20", " ");
            }

            return multipleValuesPossiblySpaceEncoded;
        }

        public Builder consentId(String consentId){
            this.consentId = consentId;
            return this;
        }

        public Builder state(String state){
            this.state = state;
            return this;
        }

        public Builder signingKey(PrivateKey signingKey){
            this.signingKey = signingKey;
            return this;
        }

        public Builder signingKid(String signingKeyId){
            this.signingKeyId = signingKeyId;
            return this;
        }

        public RequestObject build() throws JoseException, ParseException {
            if(! isReducedSecurityEnabled){
                buildResponseAsJwt();
            }
            return new RequestObject(this);
        }

        private void buildResponseAsJwt() throws JoseException, ParseException {

//            String nonce = getNonce();
////            JSONObject claimsObj = getClaims();
////            logger.info("inner claims JWT "+claimsObj.toString(2));
//
//            JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
//                    .audience(audience)
//                    .issuer(clientId)
//                    .claim("response_type", responseType)
//                    .claim("client_id", clientId)
//                    .claim("redirect_uri", redirectUri)
//                    .claim("scope", scope)
//                    .claim("state", state)
//                    .claim("nonce", nonce)
//                    .claim("max_age", 86400)
//                    .claim("claims", pooBah())
//                    .build();
//
//            consentJwt = new SignedJWT(buildHeader(), claimsSet);
//
//            // Compute the RSA signature
//            consentJwt.sign(signingKey);

            String nonce = getNonce();
            JwtClaims jwtClaims = new JwtClaims();
            jwtClaims.setAudience(audience);
            jwtClaims.setIssuer(clientId);
            jwtClaims.setClaim("response_type", responseType);
            jwtClaims.setClaim("client_id", clientId);
            jwtClaims.setClaim("redirect_uri", redirectUri);
            jwtClaims.setClaim("scope", scope);
            jwtClaims.setClaim("state", state);
            jwtClaims.setClaim("nonce", nonce);
            jwtClaims.setClaim("max_age", 86400);
            jwtClaims.setClaim("claims", getNestedClaims());


            JsonWebSignature jws = new JsonWebSignature();

            // The payload of the JWS is JSON content of the JWT Claims
            jws.setPayload(jwtClaims.toJson());

            // The JWT is signed using the private key
            jws.setKey(signingKey);

            // Set the Key ID (kid) header because it's just the polite thing to do.
            // We only have one key in this example but a using a Key ID helps
            // facilitate a smooth key rollover process
            jws.setKeyIdHeaderValue(signingKeyId);

            // Set the signature algorithm on the JWT/JWS that will integrity protect the claims
            jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

            // Sign the JWS and produce the compact serialization or the complete JWT/JWS
            // representation, which is a string consisting of three dot ('.') separated
            // base64url-encoded parts in the form Header.Payload.Signature
            // If you wanted to encrypt it, you can simply set this jwt as the payload
            // of a JsonWebEncryption object and set the cty (Content Type) header to "jwt".
            consentJwt = jws.getCompactSerialization();


        }


        private String getNonce() {
            byte[] array = new byte[12];
            new Random().nextBytes(array);

            Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
            return encoder.encodeToString(array);
        }


        private JSONObject getUserInfo(){
            JSONObject intentObject = new JSONObject();
            intentObject.put("value", consentId);
            intentObject.put("essential", true);


            JSONObject userInfo = new JSONObject();
            userInfo.put("openbanking_intent_id", intentObject);

            return userInfo;
        }

        private Map<String, Object> getNestedClaims() throws ParseException{

            Map<String, Object> nestedClaims = new HashMap<>();
            Map<String, Object> essentialTrue = new HashMap<>();
            essentialTrue.put("essential", true);

            Map<String, Object> intentId = new HashMap<>();
            intentId.put("value", consentId);
            intentId.put("essential", true);

            Map<String, Object> idToken = new HashMap<>();
            idToken.put("acr", essentialTrue);
            idToken.put("openbanking_intent_id", intentId);

            Map<String, Object> userInfo = new HashMap<>();

            nestedClaims.put("id_token", idToken);
            nestedClaims.put("userinfo", idToken);

            return nestedClaims;

        }

    }

}
