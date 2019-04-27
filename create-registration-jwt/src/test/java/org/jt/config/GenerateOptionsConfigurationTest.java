package org.jt.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@ActiveProfiles("test")
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class GenerateOptionsConfigurationTest {

    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @Autowired
    public void setGenerateOptionsConfiguration(GenerateOptionsConfiguration generateOptionsConfiguration) {
        this.generateOptionsConfiguration = generateOptionsConfiguration;
    }

    @Test
    public void getOutputDirectory() {
        assertThat(generateOptionsConfiguration.getOutputDirectory(), equalTo("/Users/teste/test-directory"));
    }

    @Test
    public void getTargets() {
        assertThat(generateOptionsConfiguration.getTargets(), containsInAnyOrder("red", "green", "blue"));
    }
}