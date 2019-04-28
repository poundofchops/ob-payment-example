package org.jt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        /*
         * see <code>JwtCollectionBuilder.buildRegistrationJwtCollection()</code>
         */

        SpringApplication.run(Application.class, args);
    }
}
