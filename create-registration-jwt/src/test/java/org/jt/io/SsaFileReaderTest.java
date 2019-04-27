package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class SsaFileReaderTest {
    private static final String TEST_RESOURCES_DIR = "/Users/james/dev/ideaProjects/dynamic-reg/create-registration-jwt/src/test/resources";

    @Mock
    private ClientConfiguration clientConfiguration;
    @Mock
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @InjectMocks
    private SsaFileReader classUnderTest;



    @Test(expected = Exception.class)
    public void given_No_SSA_File_Present_When_Called_then_exception_is_thrown() throws Exception {
        when(clientConfiguration.getId()).thenReturn("does-not-exist");
        classUnderTest.readFile();

    }

    @Test(expected = Exception.class)
    public void given_Invalid_Key_File_Content_When_Called_Then_Exception_Is_Thrown() throws Exception{
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TEST_RESOURCES_DIR);
        when(clientConfiguration.getId()).thenReturn("corrupt");
        classUnderTest.readFile();
    }

    @Test
    public void given_Key_File_Content_When_Called_Then_SSA_Contents_Is_Returned() throws Exception{
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TEST_RESOURCES_DIR);
        when(clientConfiguration.getId()).thenReturn("valid");
        String contents = classUnderTest.readFile();
        assertThat(contents.length(), equalTo(2754));
    }


}