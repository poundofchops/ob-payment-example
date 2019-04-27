package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.jt.config.RegistrationJwtConfiguration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class JwtFileWriterTest {
    private static final Logger logger = LoggerFactory.getLogger(JwtFileWriterTest.class);

    private static final String TARGET_NAME = "nwb";
    private static final String TARGET_OUTPUT_DIRECTORY = "/Users/james/dev/ideaProjects/dynamic-reg/create-registration-jwt/src/test/resources";
    private static final String VALID_CONTENTS_RAW = "test.file.contents";
    private static final String TEST_ID = "87389edjxolsm";

    private static final String VALID_CONTENTS_SIGNED = "test.jwt.signed";

    @Mock
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @Mock
    private ClientConfiguration clientConfiguration;

    @InjectMocks
    private JwtFileWriter classUnderTest;

    @Before
    public void setUp(){
        removeOldFile();
    }


    @Test
    public void given_CorrectConfigurationAndNoPreviousFile_When_Called_Then_NewFileCreated() {

        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TARGET_OUTPUT_DIRECTORY);
        when(clientConfiguration.getId()).thenReturn(TEST_ID);

        classUnderTest.setTargetName(TARGET_NAME);
        classUnderTest.setRegistrationJwtContents(VALID_CONTENTS_SIGNED);

        classUnderTest.writeFile();

        assertThat(readJwtContents(), equalTo(VALID_CONTENTS_SIGNED));
    }


    @Test
    public void given_CorrectConfigurationAndOldFileExists_When_Called_Then_FileOverwritten() {
        createTestFile();
        when(generateOptionsConfiguration.getOutputDirectory()).thenReturn(TARGET_OUTPUT_DIRECTORY);
        when(clientConfiguration.getId()).thenReturn(TEST_ID);

        classUnderTest.setTargetName(TARGET_NAME);
        classUnderTest.setRegistrationJwtContents(VALID_CONTENTS_SIGNED);

        classUnderTest.writeFile();

        assertThat(readJwtContents(), equalTo(VALID_CONTENTS_SIGNED));
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_TargetNotSet_When_Called_Then_ExceptionThrown() {
        classUnderTest.writeFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_JwtContentsNotSet_When_Called_Then_ExceptionThrown() {
        classUnderTest.setTargetName(TARGET_NAME);
        classUnderTest.writeFile();
    }

    @Test(expected = IllegalArgumentException.class)
    public void given_NeitherTargetOrContentsSpecified_When_CalledThenExceptionThrown() {
        classUnderTest.setRegistrationJwtContents(VALID_CONTENTS_RAW);
        classUnderTest.writeFile();
    }

    private void createTestFile() {
        try {
            Path path = Paths.get(TARGET_OUTPUT_DIRECTORY, getFileName());
            path.toFile().createNewFile();

            //Use try-with-resource to get auto-closeable writer instance
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write(VALID_CONTENTS_RAW);
            }

            logger.info("test file created");
        }catch (IOException fileSilently){
            logger.info("Unable to create old file", fileSilently);
        }
    }

    private void removeOldFile() {
        logger.info("old test file removed");
        Path path = Paths.get(TARGET_OUTPUT_DIRECTORY, getFileName());
        path.toFile().delete();
    }

    private String readJwtContents(){

        Path path = Paths.get(TARGET_OUTPUT_DIRECTORY, getFileName());

        String jwt = "";

        //Use try-with-resource to get auto-closeable reader instance
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return reader.readLine();
        } catch (IOException e) {
            logger.error("failed to read JWT contents",e);
        }

        return jwt;
    }

    private String getFileName(){
        return TEST_ID+"-"+TARGET_NAME+".jwt";
    }


}