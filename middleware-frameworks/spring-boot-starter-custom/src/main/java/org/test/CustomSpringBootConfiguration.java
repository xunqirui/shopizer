package org.test;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * CustomSpringBootConfiguration
 *
 * @author qrXun on 2021/9/15
 */
@Configuration
@ConditionalOnNotWebApplication
public class CustomSpringBootConfiguration {

    @Bean
    public ApplicationRunner applicationRunner(){
        return args -> System.out.println("hello world");
    }

}
