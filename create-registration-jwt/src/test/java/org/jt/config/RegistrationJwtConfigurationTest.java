package org.jt.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class RegistrationJwtConfigurationTest {

    private RegistrationJwtConfiguration registrationJwtConfiguration;


    @Autowired
    public void setRegistrationJwtConfiguration(RegistrationJwtConfiguration registrationJwtConfiguration) {
        this.registrationJwtConfiguration = registrationJwtConfiguration;
    }

    @Test
    public void getAudience() {
        Map<String, String> expected = new HashMap<String, String> ();
        expected.put("nwb", "https://test.natwest.register");
        expected.put("ubn", "https://test.ubn.register");
        expected.put("rbs", "https://test.rbs.register");

        Map<String, String> actual = registrationJwtConfiguration.getAudience();

        assertThat(actual.get("larry"), equalTo(expected.get("larry")));
        assertThat(actual.get("curly"), equalTo(expected.get("curly")));
        assertThat(actual.get("mo"), equalTo(expected.get("mo")));
    }


    @Test
    public void getRequestObjectSigningAlg() {
        assertThat(registrationJwtConfiguration.getRequestObjectSigningAlg(), equalTo("test-RS256"));
    }

    @Test
    public void getRequestObjectEncryptionEnc() {
        assertThat(registrationJwtConfiguration.getRequestObjectEncryptionEnc(), equalTo("test-request-obj-enc-enc"));
    }

    @Test
    public void getRequestObjectEncryptionAlg() {
        assertThat(registrationJwtConfiguration.getRequestObjectEncryptionAlg(), equalTo("test-RSA-OAEP-256"));
    }

    @Test
    public void getResponseTypes() {
        assertThat(registrationJwtConfiguration.getResponseTypes(), containsInAnyOrder("test-code", "test-token", "test-code id_token"));
    }

    @Test
    public void getIdTokenSignedResponseAlg() {
        assertThat(registrationJwtConfiguration.getIdTokenSignedResponseAlg(), equalTo("test-RS256"));
    }

    @Test
    public void getTokenEndpointAuthMethod(){
        assertThat(registrationJwtConfiguration.getTokenEndpointAuthMethod(), equalTo("test-tls_client_auth"));

    }

    @Test
    public void getTokenEndpointSigningAlg(){
        assertThat(registrationJwtConfiguration.getTokenEndpointSigningAlg(), equalTo("test-RS256"));
    }


    @Test
    public void getGrantTypes(){
        assertThat(registrationJwtConfiguration.getGrantTypes(), containsInAnyOrder("test-authorization_code", "test-client_credentials", "test-refresh_token"));
    }
}