package org.jt.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class ClientConfigurationTest {

    private ClientConfiguration classToTest;

    @Autowired
    public void setClassToTest(ClientConfiguration classToTest) {
        this.classToTest = classToTest;
    }

    @Test
    public void getId() {
        assertThat(classToTest.getId(), equalTo("test-id"));
    }


    @Test
    public void getName() {
        assertThat(classToTest.getName(), equalTo("test-name"));
    }

    @Test
    public void getDescription() {
        assertThat(classToTest.getDescription(), equalTo("test-description"));
    }

    @Test
    public void getRedirectUris(){
        assertThat(classToTest.getRedirectUris(), containsInAnyOrder("https://redirect-uri1", "https://redirect-uri2"));
    }

    @Test
    public void getScope() {
        assertThat(classToTest.getScope(), containsInAnyOrder("test-AISP", "test-PISP"));

    }

    @Test
    public void getApplicationType(){
        assertThat(classToTest.getApplicationType(), equalTo("test-authorise"));
    }

    @Test
    public void getSubjectType(){
        assertThat(classToTest.getSubjectType(), equalTo("test-public"));
    }

    @Test
    public void getSigningKid(){
        assertThat(classToTest.getSigningKid(), equalTo("test-signing-kid"));
    }

    @Test
    public void getTransportKid(){
        assertThat(classToTest.getTransportKid(), equalTo("test-transport-kid"));

    }
}