package org.jt.model.wellknown;

import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.List;

public class JSONWebKeySet {

    private List<JSONWebKey> keys;

    public List<JSONWebKey> getKeys() {
        return keys;
    }

    @JsonSetter("keys")
    public void setKeys(List<JSONWebKey> keys) {
        this.keys = keys;
    }

    @Override
    public String toString() {
        return "JSONWebKeySet{" +
                "JSONWebKeys=" + keys +
                '}';
    }
}
