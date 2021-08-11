package org.geektimes.configuration.microprofile.config.annotation;

import java.lang.annotation.*;

/**
 * ConfigSources
 *
 * @author qrXun on 2021/8/11
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigSources {

    ConfigSource[] value();

}
