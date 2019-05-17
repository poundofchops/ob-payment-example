package org.jt.configuration;

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

    @Value("${tls.keystore.location}")
    private String keyStoreLocation;

    @Value("${tls.keystore.password}")
    private String keyStorePassword;

    @Value(("${client.id}"))
    private String keyStoreAlias;

    @Value("${tls.truststore.location}")
    private String trustStoreLocation;

    @Value("${tls.truststore.password}")
    private String trustStorePassword;

    public String getClientId() {
        return clientId;
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
         * //, (map, socket) -> keyStoreAlias
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

    @Override
    public String toString() {
        return "RestClientConfiguration{" +
                "clientId='" + clientId + '\'' +
                ", redirectUri='" + redirectUri + '\'' +
                ", wellKnownOpenIDConfigurationUri='" + wellKnownOpenIDConfigurationUri + '\'' +
                ", financialId='" + financialId + '\'' +
                ", keyStoreLocation='" + keyStoreLocation + '\'' +
                ", keyStorePassword='" + keyStorePassword + '\'' +
                ", keyStoreAlias='" + keyStoreAlias + '\'' +
                ", trustStoreLocation='" + trustStoreLocation + '\'' +
                ", trustStorePassword='" + trustStorePassword + '\'' +
                '}';
    }
}
