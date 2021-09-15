package org.test;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * SpringBootRunner
 *
 * @author qrXun on 2021/9/15
 */
@SpringBootApplication
public class SpringBootRunner {

    public static void main(String[] args) {
        new SpringApplicationBuilder(SpringBootRunner.class)
                .web(WebApplicationType.NONE)
                .run(args);
    }

}
