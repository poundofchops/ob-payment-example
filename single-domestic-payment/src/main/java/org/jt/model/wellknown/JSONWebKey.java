package org.jt.model.wellknown;

import com.fasterxml.jackson.annotation.JsonSetter;

public class JSONWebKey {
    private String keyId;
    private String keyType;
    private String publicKeyModulus;
    private String publicKeyExponent;
    private String use;

    public JSONWebKey() {
    }

    public String getKeyId() {
        return keyId;
    }


    @JsonSetter("kid")
    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getKeyType() {
        return keyType;
    }

    @JsonSetter("kty")
    public void setKeyType(String keyType) {
        this.keyType = keyType;
    }

    public String getPublicKeyModulus() {
        return publicKeyModulus;
    }

    @JsonSetter("n")
    public void setPublicKeyModulus(String publicKeyModulus) {
        this.publicKeyModulus = publicKeyModulus;
    }

    public String getPublicKeyExponent() {
        return publicKeyExponent;
    }

    @JsonSetter("e")
    public void setPublicKeyExponent(String publicKeyExponent) {
        this.publicKeyExponent = publicKeyExponent;
    }

    public String getUse() {
        return use;
    }

    public void setUse(String use) {
        this.use = use;
    }

    @Override
    public String toString() {
        return "JSONWebKey{" +
                "keyId='" + keyId + '\'' +
                ", keyType='" + keyType + '\'' +
                ", publicKeyModulus='" + publicKeyModulus + '\'' +
                ", publicKeyExponent='" + publicKeyExponent + '\'' +
                ", use='" + use + '\'' +
                '}';
    }
}