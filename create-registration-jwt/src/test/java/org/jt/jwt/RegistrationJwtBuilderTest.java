package org.jt.jwt;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.jt.config.RegistrationJwtConfiguration;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

public class RegistrationJwtBuilderTest {

    @Mock
    private RegistrationJwtConfiguration registrationJwtConfiguration;

    @Mock
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @Mock
    private ClientConfiguration clientConfiguration;

    @InjectMocks
    RegistrationJwtBuilder classUnderTest;

    @Before
    public void setUp(){
        // happy day - successful config

//        id: oeQyoDP5ewjAoL6SfuKG2K
//        name: Registration v31 test
//        description: test SSA/client for PSD2 onboarding tests
//        application_type: authorise
//        subject_type: public
//        scope:
//        - AISP
//                - PISP
//        redirect_uris:
//        - https://shatnersbassoon.net/openbanking/cb
//        - https://shatnersbassoon.net/openbanking/cb2
//        transport_kid: YvJnFzgXAtFwIhGUZN_12w1hDBA
//        signing_kid: 9xpKIuc1GDgYfAm1uo7o8DyaoEQ

        when((clientConfiguration.getId())).thenReturn("client1");
        when(clientConfiguration.getName()).thenReturn("registration builder test");
        when(clientConfiguration.getDescription()).thenReturn("");
        when(clientConfiguration.getApplicationType()).thenReturn("authorise");
        when(clientConfiguration.getSubjectType()).thenReturn("public");
        when(clientConfiguration.getScope()).thenReturn(new ArrayList<>());
        when(clientConfiguration.getRedirectUris()).thenReturn(new ArrayList<>());
        when(clientConfiguration.getTransportKid()).thenReturn("");
        when(clientConfiguration.getSigningKid()).thenReturn("");




    }

//    @Test
//    public void build() throws Exception{
//
//
//        when(generateOptionsConfiguration.getTargets()).thenReturn(expectedTargets);
//        classUnderTest.build();
//
//    }
}