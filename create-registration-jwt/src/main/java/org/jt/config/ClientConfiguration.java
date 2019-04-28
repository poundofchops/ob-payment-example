package org.jt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@ConfigurationProperties(prefix = "client")
@Component()
public class ClientConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String id;
    private String name;
    private String description;
    private List<String> redirectUris;
    private List<String> scope;
    private String signingKid;
    private String transportKid;
    private String applicationType;
    private String subjectType;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRedirectUris() {
        return redirectUris;
    }

    public void setRedirectUris(List<String> redirectUris) {
        this.redirectUris = redirectUris;
    }

    public List<String> getScope() {
        return scope;
    }

    public void setScope(List<String> scope) {
        this.scope = scope;
    }

    public String getSigningKid() {
        return signingKid;
    }

    public void setSigningKid(String signingKid) {
        this.signingKid = signingKid;
    }

    public String getTransportKid() {
        return transportKid;
    }

    public void setTransportKid(String transportKid) {
        this.transportKid = transportKid;
    }

    public String getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(String applicationType) {
        this.applicationType = applicationType;
    }

    public String getSubjectType() {
        return subjectType;
    }

    public void setSubjectType(String subjectType) {
        this.subjectType = subjectType;
    }


    @Override
    public String toString() {
        return "ClientConfiguration{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", redirectUris=" + redirectUris +
                ", scope=" + scope +
                ", signingKid='" + signingKid + '\'' +
                ", transportKid='" + transportKid + '\'' +
                ", applicationType='" + applicationType + '\'' +
                ", subjectType='" + subjectType + '\'' +
                '}';
    }

    @PostConstruct
    public void echoState(){
        logger.info(toString());
    }

}
