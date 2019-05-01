package org.jt;

import org.springframework.web.client.RestTemplate;

public class DumbTest {

    public static void main(String[] args) {
        System.setProperty("javax.net.debug","ssl");

        RestTemplate template = new RestTemplate();

        template.getForEntity("https://rs.aspsp.ob.forgerock.financial:443/open-banking/mtlsTest",String.class);

    }
}
