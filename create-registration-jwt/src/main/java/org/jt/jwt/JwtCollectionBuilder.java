package org.jt.jwt;

import org.jt.config.GenerateOptionsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class JwtCollectionBuilder {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private GenerateOptionsConfiguration generateOptionsConfiguration;
    private RegistrationJwtBuilder registrationJwtBuilder;

    @Autowired
    public void setGenerateOptionsConfiguration(GenerateOptionsConfiguration generateOptionsConfiguration) {
        this.generateOptionsConfiguration = generateOptionsConfiguration;
    }

    @Autowired
    public void setRegistrationJwtBuilder(RegistrationJwtBuilder registrationJwtBuilder) {
        this.registrationJwtBuilder = registrationJwtBuilder;
    }

    @PostConstruct
    public void buildRegistrationJwtCollection(){
        logger.info("Geronimo!!");
        generateOptionsConfiguration.getTargets().stream().forEach(this::buildRegistrationJwtForTarget);
    }

    private void buildRegistrationJwtForTarget(String targetName){
        registrationJwtBuilder.setTarget(targetName);
        try {
            logger.info(registrationJwtBuilder.build());
        } catch (Exception e) {
            logger.error(String.format("Unable to build registration JWT for target {s}", targetName), e);
        }
    }

}
