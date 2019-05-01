package org.jt.jwt;

import com.nimbusds.jose.JOSEObjectType;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.RSASSASigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.jt.config.ClientConfiguration;
import org.jt.config.RegistrationJwtConfiguration;
import org.jt.io.JwtFileWriter;
import org.jt.io.SigningKeyReader;
import org.jt.io.SsaFileReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegistrationJwtBuilder {
    private static final Logger logger = LoggerFactory.getLogger(RegistrationJwtBuilder.class);

    //Spring wired goodness
    private RegistrationJwtConfiguration jwtConfiguration;
    private ClientConfiguration clientConfiguration;
    private SsaFileReader ssaFileReader;
    private SigningKeyReader signingKeyReader;
    private JwtFileWriter jwtFileWriter;

    // application variable
    private String target;

    public void setTarget(String target) {
        this.target = target;
    }

    @Autowired
    public void setJwtConfiguration(RegistrationJwtConfiguration jwtConfiguration) {
        this.jwtConfiguration = jwtConfiguration;
    }

    @Autowired
    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    @Autowired
    public void setJwtFileWriter(JwtFileWriter jwtFileWriter) {
        this.jwtFileWriter = jwtFileWriter;
    }

    @Autowired
    public void setSigningKeyReader(SigningKeyReader signingKeyReader) {
        this.signingKeyReader = signingKeyReader;
    }

    @Autowired
    public void setSsaFileReader(SsaFileReader ssaFileReader) {
        this.ssaFileReader = ssaFileReader;
    }

    public String build() throws Exception{
        // Create RSA-signer with the private key
        JWSSigner signer = createJWSSigner();

        // Prepare JWT with header and claims set
        JWSHeader header = buildHeader();
        JWTClaimsSet claimsSet = buildClaimSet();

        SignedJWT signedJWT = new SignedJWT(header, claimsSet);

        // Compute the RSA signature
        signedJWT.sign(signer);

        // To serialize to Base64 compact form
        String regJwtString = signedJWT.serialize();

        // write result to file
        jwtFileWriter.setRegistrationJwtContents(regJwtString);
        jwtFileWriter.setTargetName(target);
        jwtFileWriter.writeFile();

        return regJwtString;

    }

    private RSASSASigner createJWSSigner() throws Exception {
        return new RSASSASigner(signingKeyReader.getSigningKey());
    }

    private JWSHeader buildHeader() {
        return new JWSHeader.Builder(JWSAlgorithm.RS256)
                            .keyID(clientConfiguration.getSigningKid())
                            .type(JOSEObjectType.JWT)
                            .build();
    }

    private JWTClaimsSet buildClaimSet() throws Exception{
//        {
//            "software_statement": "bl..ah",
//
//            "aud": "https://useinfinite.io/openbanking",
//            "scope": [
//              "AISP",
//              "PISP"
//              ],
//            "request_object_signing_alg": "RS256",
//            "request_object_encryption_enc": "A128CBC-HS256",
//            "request_object_encryption_alg": "RSA-OAEP-256",
//
//            "response_types": [
//              "code",
//              "token",
//              "code id_token"
//              ],

//            "id_token_signed_response_alg": "ES256"
//
//            "token_endpoint_auth_method": "private_key_jwt",
//            "token_endpoint_auth_signing_alg": "RS256",
//            "grant_types": [
//              "authorization_code",
//              "refresh_token",
//              "client_credentials"
//              ],
//
//            // client config
//            "iss": "2MGpBE1fmRtAlLYqBDKyUm",
//            "redirect_uris": [
//              "https://infinite.io/callback"
//              ],
//            "subject_type": "public",
//            "application_type": "web",
//
//            // generated
//            "iat": 1551454782,
//            "exp": 1551456582,
//            "jti": "6f5b5557-e3d1-4f57-bead-42fcb6ee394a"
//        }

        return new JWTClaimsSet.Builder()
                    .claim("software_statement", ssaFileReader.readFile())
                    .audience(jwtConfiguration.getAudience().get(target))
                    .claim("request_object_signing_alg", jwtConfiguration.getRequestObjectSigningAlg())
                    .claim("id_token_signed_response_alg", jwtConfiguration.getIdTokenSignedResponseAlg())
                    .claim("token_endpoint_auth_method", jwtConfiguration.getTokenEndpointAuthMethod())
                    .claim("token_endpoint_signing_alg", jwtConfiguration.getTokenEndpointSigningAlg())
                    .claim("grant_types", jwtConfiguration.getGrantTypes())

                    .issuer(clientConfiguration.getId())
                    .claim("scope", clientConfiguration.getScope())
                    .claim("redirect_uris", clientConfiguration.getRedirectUris())
                    .claim("subject_type", clientConfiguration.getSubjectType())
                    .claim("application_type", clientConfiguration.getApplicationType())

                    .issueTime(new Date(new Date().getTime()))
                    .expirationTime(new Date(new Date().getTime() + (60 * 60 * 1000)))
                    .claim("jti", UUID.randomUUID().toString())

                    .build();
    }
}
