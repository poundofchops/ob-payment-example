package org.jt.consent;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.RSASSASigner;
import org.jose4j.lang.JoseException;
import org.jt.configuration.RestClientConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;


import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

public class RequestObjectTest {

    private RequestObject classUnderTest;
    @Mock
    private RestClientConfiguration clientConfiguration;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

    }


    @Test
    public void givenReducedSecurityIsEnabled_WhenCalled_ThenRawConsentIdIsReturned() throws JoseException, ParseException {
        when(clientConfiguration.isReducedSecurityEnabled()).thenReturn(true);

        classUnderTest = new RequestObject
                            .Builder()
                            .getBuilderInstance()
                            .clientConfiguration(clientConfiguration)
                            .consentId("this-is-the-consent-id")
                            .state("abcde-12345-vwxyz")
                            .build();

        assertThat(classUnderTest.asString(), equalTo("this-is-the-consent-id"));
    }

    @Test
    public void  givenReducedSecurityIsDisabled_WhenCalled_ConsentJWTIsReturned() throws JoseException, ParseException, NoSuchAlgorithmException {
        when(clientConfiguration.isReducedSecurityEnabled()).thenReturn(false);
        when(clientConfiguration.getClientId()).thenReturn("client1");
        when(clientConfiguration.getRedirectUri()).thenReturn("https://boomshanka.com/cb");
        when(clientConfiguration.getFinancialId()).thenReturn("bank1");
        when(clientConfiguration.getResponseTypes()).thenReturn("code%20id_token");
        when(clientConfiguration.getScopes()).thenReturn("payments%20openid");
        when(clientConfiguration.getAudience()).thenReturn("https://ob.targetbank.com");

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        classUnderTest = new RequestObject
                .Builder()
                .getBuilderInstance()
                .clientConfiguration(clientConfiguration)
                .consentId("this-is-the-consent-id")
                .state("in-a-state")
                .signingKey(keyPair.getPrivate())
                .signingKid("signing-key-id")

                .build();

        assertThat(classUnderTest.asString(), not(equalTo("this-is-the-consent-id")));
        System.out.println(classUnderTest.asString());
    }
}