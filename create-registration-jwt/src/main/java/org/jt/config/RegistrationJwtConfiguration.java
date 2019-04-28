package org.jt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "registration-jwt")
public class RegistrationJwtConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationJwtConfiguration.class);

    // claimset
    // ssa: bl..ah
    private Map<String, String>  audience;
    private String requestObjectSigningAlg;
    private String requestObjectEncryptionEnc;
    private String requestObjectEncryptionAlg;
    private List<String> responseTypes;
    private String idTokenSignedResponseAlg;
    private String tokenEndpointAuthMethod;
    private String tokenEndpointSigningAlg;
    private List<String> grantTypes;

    // exp: timestamp
    // iat: timestamp
    // jti: UUID


    public Map<String, String> getAudience() {
        return audience;
    }

    public void setAudience(Map<String, String> audienceMap) {
        this.audience = audienceMap;
    }

    public String getRequestObjectSigningAlg() {
        return requestObjectSigningAlg;
    }

    public void setRequestObjectSigningAlg(String requestObjectSigningAlg) {
        this.requestObjectSigningAlg = requestObjectSigningAlg;
    }

    public String getRequestObjectEncryptionEnc() {
        return requestObjectEncryptionEnc;
    }

    public void setRequestObjectEncryptionEnc(String requestObjectEncryptionEnc) {
        this.requestObjectEncryptionEnc = requestObjectEncryptionEnc;
    }

    public String getRequestObjectEncryptionAlg() {
        return requestObjectEncryptionAlg;
    }

    public void setRequestObjectEncryptionAlg(String requestObjectEncryptionAlg) {
        this.requestObjectEncryptionAlg = requestObjectEncryptionAlg;
    }

    public List<String> getResponseTypes() {
        return responseTypes;
    }

    public String getIdTokenSignedResponseAlg() {
        return idTokenSignedResponseAlg;
    }

    public void setIdTokenSignedResponseAlg(String idTokenSignedResponseAlg) {
        this.idTokenSignedResponseAlg = idTokenSignedResponseAlg;
    }

    public void setResponseTypes(List<String> responseTypes) {
        this.responseTypes = responseTypes;
    }

    public String getTokenEndpointAuthMethod() {
        return tokenEndpointAuthMethod;
    }

    public void setTokenEndpointAuthMethod(String tokenEndpointAuthMethod) {
        this.tokenEndpointAuthMethod = tokenEndpointAuthMethod;
    }

    public String getTokenEndpointSigningAlg() {
        return tokenEndpointSigningAlg;
    }

    public void setTokenEndpointSigningAlg(String tokenEndpointSigningAlg) {
        this.tokenEndpointSigningAlg = tokenEndpointSigningAlg;
    }

    public List<String> getGrantTypes() {
        return grantTypes;
    }

    public void setGrantTypes(List<String> grantTypes) {
        this.grantTypes = grantTypes;
    }

    @Override
    public String toString() {
        return "RegistrationJwtConfiguration{" +
                "audience=" + audience.keySet() +
                ", requestObjectSigningAlg='" + requestObjectSigningAlg + '\'' +
                ", requestObjectEncryptionEnc='" + requestObjectEncryptionEnc + '\'' +
                ", requestObjectEncryptionAlg='" + requestObjectEncryptionAlg + '\'' +
                ", responseTypes=" + responseTypes +
                ", idTokenSignedResponseAlg='" + idTokenSignedResponseAlg + '\'' +
                ", tokenEndpointAuthMethod='" + tokenEndpointAuthMethod + '\'' +
                ", tokenEndpointSigningAlg='" + tokenEndpointSigningAlg + '\'' +
                ", grantTypes=" + grantTypes +
                '}';
    }

    @PostConstruct
    public void echoState(){
        logger.info(toString());
    }
}
