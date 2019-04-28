package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.RegistrationJwtConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class JwtFileWriter {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RegistrationJwtConfiguration registrationJwtConfiguration;
    private GenerateOptionsConfiguration generateOptionsConfiguration;
    private ClientConfiguration clientConfiguration;

    private String targetName;
    private String registrationJwtContents;

    @Autowired
    public void setRegistrationJwtConfiguration(RegistrationJwtConfiguration registrationJwtConfiguration) {
        this.registrationJwtConfiguration = registrationJwtConfiguration;
    }

    @Autowired
    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Autowired
    public void setGenerateOptionsConfiguration(GenerateOptionsConfiguration generateOptionsConfiguration){
        this.generateOptionsConfiguration = generateOptionsConfiguration;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public void setRegistrationJwtContents(String registrationJwtContents) {
        this.registrationJwtContents = registrationJwtContents;
    }


    public void writeFile() throws IllegalArgumentException{
        if ((targetName == null || targetName.isEmpty()) || (registrationJwtContents == null || registrationJwtContents.isEmpty())){
            logger.error(String.format("Unable to wtite JWT file as mandatory parameters missing target: {%s} jwtContents: {%s}",
                    targetName, registrationJwtContents));
            throw new IllegalArgumentException();
        }

        try {
            removeOldFileForTarget();
            writeNewFileForTarget();
            logger.debug("File contents written");
        } catch (Exception e) {
            logger.error("Failed to write regJwt to file", e);
        }
    }

    private void writeNewFileForTarget() throws Exception {
        //Get the file reference
        Path path = Paths.get(generateOptionsConfiguration.getOutputDirectory(), getTargetFileName());
        path.toFile().createNewFile();

        //Use try-with-resource to get auto-closeable writer instance
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(registrationJwtContents);
        }
    }

    private void removeOldFileForTarget() {
        //Get the file reference
        File oldFile = Paths.get(generateOptionsConfiguration.getOutputDirectory(), getTargetFileName()).toFile();

        if(oldFile.exists()){
            oldFile.delete();
            logger.debug("old file removed");
        }
    }

    private String getTargetFileName() {
        return clientConfiguration.getId() + "-" +targetName+ ".jwt";
    }
}
