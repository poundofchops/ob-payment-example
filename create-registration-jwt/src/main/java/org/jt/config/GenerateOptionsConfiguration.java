package org.jt.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component()
@ConfigurationProperties(prefix = "generate-options")
public class GenerateOptionsConfiguration {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String outputDirectory;

    private List<String> targetsList;

    public String getOutputDirectory() {
        return outputDirectory;
    }

    public void setOutputDirectory(String outputDirectory) {
        this.outputDirectory = outputDirectory;
    }

    public List<String> getTargets() {
        return targetsList;
    }

    public void setTargets(List<String> targetList) {
        this.targetsList = targetList;
    }

    @Override
    public String toString() {
        return "GenerateOptionsConfiguration{" +
                "outputDirectory='" + outputDirectory + '\'' +
                ", targetsList=" + targetsList +
                '}';
    }

    @PostConstruct
    public void echoState(){
        logger.info(toString());
    }
}
