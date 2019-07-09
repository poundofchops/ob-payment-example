package org.jt.configuration;

import com.nimbusds.jose.crypto.RSASSASigner;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.CertificateException;

@Configuration
public class RestClientConfiguration {
    private static Logger logger = LoggerFactory.getLogger(RestClientConfiguration.class);

    @Value("${client.id}")
    private String clientId;

    @Value("${client.redirecturi}")
    private String redirectUri;

    @Value("${target.wellknown_endpoint_url}")
    private String wellKnownOpenIDConfigurationUri;

    @Value("${target.financial_id}")
    private String financialId;

    @Value("${target.audience}")
    private String audience;

    @Value("${tls.keystore.location}")
    private String keyStoreLocation;

    @Value("${tls.keystore.password}")
    private String keyStorePassword;

    @Value(("${client.id}"))
    private String transportKeyStoreAlias;

    @Value(("${client.signing_kid}"))
    private String signingKeyId;

    @Value("${tls.truststore.location}")
    private String trustStoreLocation;

    @Value("${tls.truststore.password}")
    private String trustStorePassword;

    @Value("${client.reduced_security_enabled}")
    private boolean reducedSecurityEnabled;

    @Value("${target.response_types}")
    private String responseTypes;

    @Value("${target.scopes}")
    private String scopes;

    public String getClientId() {
        return clientId;
    }

    public String getResponseTypes() {
        return responseTypes;
    }

    public String  getScopes() {
        return scopes;
    }

    public String getWellKnownOpenIDConfigurationUri(){
        return wellKnownOpenIDConfigurationUri;
    }

    public String getRedirectUri() {
        return redirectUri;
    }

    public String getFinancialId() {
        return financialId;
    }

    public boolean isReducedSecurityEnabled() {
        return reducedSecurityEnabled;
    }

    public String getAudience(){
        return audience;
    }

    public String getSigningKid() {
        return signingKeyId;
    }

    @Bean
    public RestTemplate sslRestTemplate(RestTemplateBuilder builder) throws IOException, KeyStoreException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException, KeyManagementException {

        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(ResourceUtils.getFile(keyStoreLocation)), keyStorePassword.toCharArray());

        /*
         * Create an SSLContext that uses client.jks as the client certificate
         * and the truststore.jks as the trust material (trusted CA certificates).
         * In this sample, truststore.jks contains ca.pem which was used to sign
         * both client.pfx and server.jks.
         *
         * //, (map, socket) -> transportKeyStoreAlias
         */
        SSLContext sslContext = SSLContextBuilder.create()
                .loadKeyMaterial(keyStore, keyStorePassword.toCharArray())
                .loadTrustMaterial(ResourceUtils.getFile(trustStoreLocation), trustStorePassword.toCharArray())
                .build();

        /*
         * Create an HttpClient that uses the custom SSLContext
         */
        HttpClient client = HttpClients.custom().setSSLContext(sslContext).build();

        /*
         * Create a RestTemplate that uses a request factory that references
         * our custom HttpClient
         */
        return  builder
                .requestFactory(() -> new HttpComponentsClientHttpRequestFactory(client))
                .build();
    }

    @Bean
    public RestTemplate vanillaRestTemplate(RestTemplateBuilder builder){
        return builder.build();
    }

    @Bean
    public PrivateKey jwtSigningKey() throws KeyStoreException, IOException, CertificateException, NoSuchAlgorithmException, UnrecoverableKeyException {
        KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
        keyStore.load(new FileInputStream(ResourceUtils.getFile(keyStoreLocation)), keyStorePassword.toCharArray());
        return (PrivateKey) keyStore.getKey("j1eN4fJOrbxS8mB-4RGz2z_Dg0U", keyStorePassword.toCharArray());
   }

    @Override
    public String toString() {
        return "RestClientConfiguration{" +
                "clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", wellKnownOpenIDConfigurationUri='" + wellKnownOpenIDConfigurationUri + '\'' +
                ", financialId='" + financialId + '\'' +
                ", keyStoreLocation='" + keyStoreLocation + '\'' +
                ", keyStorePassword='" + keyStorePassword + '\'' +
                ", transportKeyStoreAlias='" + transportKeyStoreAlias + '\'' +
                ", trustStoreLocation='" + trustStoreLocation + '\'' +
                ", trustStorePassword='" + trustStorePassword + '\'' +
                '}';
    }

}
