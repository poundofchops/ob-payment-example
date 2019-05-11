package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SigningKeyReaderTest {
    private static final Logger logger = LoggerFactory.getLogger(SigningKeyReaderTest.class);
    private static final String TEST_RESOURCES_DIR = "/Users/james/dev/ideaProjects/dynamic-reg/create-registration-jwt/src/test/model";

    @Mock
    private ClientConfiguration clientConfiguration;

    @Mock
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @InjectMocks
    private SigningKeyReader classUnderTest;


    @Test(expected = Exception.class)
    public void given_No_Key_File_Present_when_called_then_exception_is_thrown() throws Exception {
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TEST_RESOURCES_DIR);
        when(clientConfiguration.getId()).thenReturn("sjsjjos");
        classUnderTest.getSigningKey();

    }

    @Test(expected = Exception.class)
    public void given_Invalid_Key_File_Content_When_Called_Then_Exception_Is_Thrown() throws Exception{
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TEST_RESOURCES_DIR);
        when(clientConfiguration.getId()).thenReturn("corrupt");
        classUnderTest.getSigningKey();
    }

    @Test
    public void given_Key_File_Content_When_Called_Then_Private_Key_Is_Returned() throws Exception{
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TEST_RESOURCES_DIR);
        when(clientConfiguration.getId()).thenReturn("valid");
        assertThat(classUnderTest.getSigningKey().getAlgorithm(), equalTo("RSA"));
    }


}