package org.jt.io;

import org.jt.config.ClientConfiguration;
import org.jt.config.GenerateOptionsConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

@Service
public class SigningKeyReader {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ClientConfiguration clientConfiguration;
    private GenerateOptionsConfiguration generateOptionsConfiguration;

    @Autowired
    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Autowired
    public void setGenerateOptionsConfiguration(GenerateOptionsConfiguration generateOptionsConfiguration) {
        this.generateOptionsConfiguration = generateOptionsConfiguration;
    }


    private String getKeyFileName(){
        return clientConfiguration.getId()+"_signing.key";
    }

    public PrivateKey getSigningKey() throws Exception{
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(generateOptionsConfiguration.getOutputDirectory(), getKeyFileName())));

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        return kf.generatePrivate(keySpecPKCS8);
    }
}
