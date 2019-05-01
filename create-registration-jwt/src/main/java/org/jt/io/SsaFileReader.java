package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SsaFileReader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClientConfiguration clientConfiguration;
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    //contents of ssa file
    private String ssaText;

    @Autowired
    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Autowired
    public void setGenerateOptionsConfiguration(GenerateOptionsConfiguration generateOptionsConfiguration) {
        this.generateOptionsConfiguration = generateOptionsConfiguration;
    }

    public String readFile() throws Exception{

        Path path = Paths.get(generateOptionsConfiguration.getOutputDirectory(), clientConfiguration.getId(), getSsaFileName());

        Stream<String> lines = Files.lines(path);
        ssaText= lines.collect(Collectors.joining());
        lines.close();

        if(ssaText.split("\\.").length != 3){
            throw new Exception("Invalid jwt file");
        }

        return ssaText;
    }

    private String getSsaFileName(){
        return clientConfiguration.getId()+".ssa";
    }
}
