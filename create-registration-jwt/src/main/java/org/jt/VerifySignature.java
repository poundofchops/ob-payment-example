package org.jt;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.proc.SecurityContext;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

public class VerifySignature {

    private static final Logger logger = LoggerFactory.getLogger(VerifySignature.class);

    private static final String SIGNING_KID = "j1eN4fJOrbxS8mB-4RGz2z_Dg0U";
    private static final String KEY_FILE_DIRECTORY = "/Users/james/dev/ideaProjects/dynamic-reg/create-registration-jwt/src/main/model/keys";
    private static final String KEY_FILE_NAME = "8CPm73WMWNHzlRYCIiPKrA_signing.key";

    public static void main(String[] args) {
        try {

            String jwt = build();
            logger.info("JWT Constructed->"+jwt);
            ConfigurableJWTProcessor jwtProcessor = new DefaultJWTProcessor();
            // ulster
            JWKSource keySource = new RemoteJWKSet(new URL("http://prdtst-keystore-obd.s3-website-eu-west-1.amazonaws.com/0015800001ZEZ1iAAH/0015800001ZEZ1iAAH.jwks"));

            JWSAlgorithm expectedJWSAlg = JWSAlgorithm.RS256;

            JWSKeySelector boSelector = new JWSVerificationKeySelector(expectedJWSAlg, keySource);
            jwtProcessor.setJWSKeySelector(boSelector);

            SecurityContext ctx = null;
            JWTClaimsSet claimsSet = jwtProcessor.process(jwt, ctx);
            logger.info(claimsSet.toString());
        }catch(Exception e){
            logger.error("++Verify error++", e);
        }
    }

    public static String build() throws JOSEException, NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        JWSHeader header =  new JWSHeader.Builder(JWSAlgorithm.RS256)
                .keyID(SIGNING_KID)
                .type(JOSEObjectType.JWT)
                .build();

        JWSSigner signer = new RSASSASigner(getSigningKey());

        JWTClaimsSet claimsSet = buildClaimSet();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to Base64 compact form
        String regJwtString = signedJWT.serialize();

        return  regJwtString;



    }

    private static JWTClaimsSet buildClaimSet() {
        return new JWTClaimsSet.Builder()
                .audience("https://rbs.useinfinite.io")
                .issuer("JT")
                .issueTime(new Date(new Date().getTime()))
                .expirationTime(new Date(new Date().getTime() + (60 * 60 * 1000)))
                .claim("jti", UUID.randomUUID().toString())
                .build();

    }

    private static PrivateKey getSigningKey() throws NoSuchAlgorithmException, InvalidKeySpecException, IOException {
        String privateKeyContent = new String(Files.readAllBytes(Paths.get(KEY_FILE_DIRECTORY, KEY_FILE_NAME)));

        privateKeyContent = privateKeyContent.replaceAll("\\n", "").replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "");

        KeyFactory kf = KeyFactory.getInstance("RSA");

        PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(privateKeyContent));
        return kf.generatePrivate(keySpecPKCS8);


    }
}
