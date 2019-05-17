package org.jt;

import org.jt.consent.SingleImmediatePaymentConsent;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class RestClientApplication {

    public static void main(String[] args) {

        /**
         * See {@link SingleImmediatePaymentConsent#applicationLaunch()}
         */
        ConfigurableApplicationContext ctx = SpringApplication.run(RestClientApplication.class, args);

        // application runs as part of context creation so can shutdown when we get to here
//        SpringApplication.exit(ctx, () -> 0);
    }

}
