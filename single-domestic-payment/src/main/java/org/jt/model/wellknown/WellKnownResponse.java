package org.jt.model.wellknown;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class WellKnownResponse {

    private String version;
    private String issuer;
    private String authorizationEndpoint;
    private String tokenEndpoint;

    private JSONWebKeySet jwks;

    private String registrationEndpoint;
    private List<String> scopesSupported;

    private List<String> claimsSupported;

    private List<String> acrValuesSupported;
    private List<String> responseTypesSupported;

    private List<String> responseModesSupported;

    private List<String> grantTypesSupported;
    private List<String> subjectTypesSupported;

    private List<String> idTokenSigningAlgValuesSupported;

    private List<String> tokenEndpointAuthMethodsSupported;
    private List<String> tokenEndpointAuthSigningAlgValuesSupported;


    private List<String> claimTypesSupported;
    private boolean claimsParameterSupported;

    private boolean requestParameterSupported;
    private boolean requestUriParameterSupported;
    private List<String> requestObjectSigningAlgValuesSupported;
    private List<String> requestObjectEncryptionAlgValuesSupported;
    private List<String> requestObjectEncryptionEncValuesSupported;


    public String getVersion() {
        return version;
    }

    @JsonSetter("version")
    public void setVersion(String version) {
        this.version = version;
    }

    public String getIssuer() {
        return issuer;
    }

    @JsonSetter("issuer")
    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getAuthorizationEndpoint() {
        return authorizationEndpoint;
    }

    @JsonSetter("authorization_endpoint")
    public void setAuthorizationEndpoint(String authorization_endpoint) {
        this.authorizationEndpoint = authorization_endpoint;
    }

    public String getTokenEndpoint() {
        return tokenEndpoint;
    }

    @JsonSetter("token_endpoint")
    public void setTokenEndpoint(String tokenEndpoint) {
        this.tokenEndpoint = tokenEndpoint;
    }

    public JSONWebKeySet getJwks() {
        return jwks;
    }

    public void setJwks(JSONWebKeySet jwks) {
        this.jwks = jwks;
    }

    public String getRegistrationEndpoint() {
        return registrationEndpoint;
    }

    @JsonSetter("registration_endpoint")
    public void setRegistrationEndpoint(String registrationEndpoint) {
        this.registrationEndpoint = registrationEndpoint;
    }

    public List<String> getScopesSupported() {
        return scopesSupported;
    }

    @JsonSetter("scopes_supported")
    public void setScopesSupported(List<String> scopesSupported) {
        this.scopesSupported = scopesSupported;
    }

    public List<String> getClaimsSupported() {
        return claimsSupported;
    }

    @JsonSetter("claims_supported")
    public void setClaimsSupported(List<String> claimsSupported) {
        this.claimsSupported = claimsSupported;
    }

    public List<String> getAcrValuesSupported() {
        return acrValuesSupported;
    }

    @JsonSetter("acr_values_supported")
    public void setAcrValuesSupported(List<String> acrValuesSupported) {
        this.acrValuesSupported = acrValuesSupported;
    }

    public List<String> getResponseTypesSupported() {
        return responseTypesSupported;
    }

    @JsonSetter("response_types_supported")
    public void setResponseTypesSupported(List<String> responseTypesSupported) {
        this.responseTypesSupported = responseTypesSupported;
    }

    public List<String> getResponseModesSupported() {
        return responseModesSupported;
    }

    @JsonSetter("response_modes_supported")
    public void setResponseModesSupported(List<String> responseModesSupported) {
        this.responseModesSupported = responseModesSupported;
    }

    public List<String> getGrantTypesSupported() {
        return grantTypesSupported;
    }

    @JsonSetter("grant_types_supported")
    public void setGrantTypesSupported(List<String> grantTypesSupported) {
        this.grantTypesSupported = grantTypesSupported;
    }

    public List<String> getSubjectTypesSupported() {
        return subjectTypesSupported;
    }

    @JsonSetter("subject_types_supported")
    public void setSubjectTypesSupported(List<String> subjectTypesSupported) {
        this.subjectTypesSupported = subjectTypesSupported;
    }

    public List<String> getIdTokenSigningAlgValuesSupported() {
        return idTokenSigningAlgValuesSupported;
    }

    @JsonSetter("id_token_signing_alg_values_supported")
    public void setIdTokenSigningAlgValuesSupported(List<String> idTokenSigningAlgValuesSupported) {
        this.idTokenSigningAlgValuesSupported = idTokenSigningAlgValuesSupported;
    }

    public List<String> getTokenEndpointAuthMethodsSupported() {
        return tokenEndpointAuthMethodsSupported;
    }

    @JsonSetter("token_endpoint_auth_methods_supported")
    public void setTokenEndpointAuthMethodsSupported(List<String> tokenEndpointAuthMethodsSupported) {
        this.tokenEndpointAuthMethodsSupported = tokenEndpointAuthMethodsSupported;
    }

    public List<String> getTokenEndpointAuthSigningAlgValuesSupported() {
        return tokenEndpointAuthSigningAlgValuesSupported;
    }

    @JsonSetter("token_endpoint_auth_signing_alg_values_supported")
    public void setTokenEndpointAuthSigningAlgValuesSupported(List<String> tokenEndpointAuthSigningAlgValuesSupported) {
        this.tokenEndpointAuthSigningAlgValuesSupported = tokenEndpointAuthSigningAlgValuesSupported;
    }

    public List<String> getClaimTypesSupported() {
        return claimTypesSupported;
    }

    @JsonSetter("claim_types_supported")
    public void setClaimTypesSupported(List<String> claimTypesSupported) {
        this.claimTypesSupported = claimTypesSupported;
    }

    public boolean isClaimsParameterSupported() {
        return claimsParameterSupported;
    }

    @JsonSetter("claims_parameter_supported")
    public void setClaimsParameterSupported(boolean claimsParameterSupported) {
        this.claimsParameterSupported = claimsParameterSupported;
    }

    public boolean isRequestParameterSupported() {
        return requestParameterSupported;
    }

    @JsonSetter("request_parameter_supported")
    public void setRequestParameterSupported(boolean requestParameterSupported) {
        this.requestParameterSupported = requestParameterSupported;
    }

    public boolean isRequestUriParameterSupported() {
        return requestUriParameterSupported;
    }

    @JsonSetter("request_uri_parameter_supported")
    public void setRequestUriParameterSupported(boolean requestUriParameterSupported) {
        this.requestUriParameterSupported = requestUriParameterSupported;
    }

    public List<String> getRequestObjectSigningAlgValuesSupported() {
        return requestObjectSigningAlgValuesSupported;
    }

    @JsonSetter("request_object_signing_alg_values_supported")
    public void setRequestObjectSigningAlgValuesSupported(List<String> requestObjectSigningAlgValuesSupported) {
        this.requestObjectSigningAlgValuesSupported = requestObjectSigningAlgValuesSupported;
    }

    public List<String> getRequestObjectEncryptionAlgValuesSupported() {
        return requestObjectEncryptionAlgValuesSupported;
    }

    @JsonSetter("request_object_encryption_alg_values_supported")
    public void setRequestObjectEncryptionAlgValuesSupported(List<String> requestObjectEncryptionAlgValuesSupported) {
        this.requestObjectEncryptionAlgValuesSupported = requestObjectEncryptionAlgValuesSupported;
    }

    public List<String> getRequestObjectEncryptionEncValuesSupported() {
        return requestObjectEncryptionEncValuesSupported;
    }

    @JsonSetter("request_object_encryption_enc_values_supported")
    public void setRequestObjectEncryptionEncValuesSupported(List<String> requestObjectEncryptionEncValuesSupported) {
        this.requestObjectEncryptionEncValuesSupported = requestObjectEncryptionEncValuesSupported;
    }

    @Override
    public String toString() {
        return "WellKnownResponse{" +
                "version='" + version + '\'' +
                ", issuer='" + issuer + '\'' +
                ", authorizationEndpoint='" + authorizationEndpoint + '\'' +
                ", tokenEndpoint='" + tokenEndpoint + '\'' +
                ", jwks=" + jwks +
                ", registrationEndpoint='" + registrationEndpoint + '\'' +
                ", scopesSupported=" + scopesSupported +
                ", claimsSupported=" + claimsSupported +
                ", acrValuesSupported=" + acrValuesSupported +
                ", responseTypesSupported=" + responseTypesSupported +
                ", responseModesSupported=" + responseModesSupported +
                ", grantTypesSupported=" + grantTypesSupported +
                ", subjectTypesSupported=" + subjectTypesSupported +
                ", idTokenSigningAlgValuesSupported=" + idTokenSigningAlgValuesSupported +
                ", tokenEndpointAuthMethodsSupported=" + tokenEndpointAuthMethodsSupported +
                ", tokenEndpointAuthSigningAlgValuesSupported=" + tokenEndpointAuthSigningAlgValuesSupported +
                ", claimTypesSupported=" + claimTypesSupported +
                ", claimsParameterSupported=" + claimsParameterSupported +
                ", requestParameterSupported=" + requestParameterSupported +
                ", requestUriParameterSupported=" + requestUriParameterSupported +
                ", requestObjectSigningAlgValuesSupported=" + requestObjectSigningAlgValuesSupported +
                ", requestObjectEncryptionAlgValuesSupported=" + requestObjectEncryptionAlgValuesSupported +
                ", requestObjectEncryptionEncValuesSupported=" + requestObjectEncryptionEncValuesSupported +
                '}';
    }
}


