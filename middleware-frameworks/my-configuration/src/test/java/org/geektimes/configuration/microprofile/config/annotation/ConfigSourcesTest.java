package org.geektimes.configuration.microprofile.config.annotation;

import org.junit.Before;
import org.junit.Test;

import java.net.URL;

/**
 * ConfigSourcesTest
 *
 * @author qrXun on 2021/8/11
 */
@ConfigSources({
        @ConfigSource(ordinal = 200, resource = "classpath:/META-INF/default.properties"),
        @ConfigSource(name = "repeatable", resource = "classpath:/META-INF/repeatable.properties")
})
public class ConfigSourcesTest {

    @Before
    public void initConfigSourceFactory() throws Throwable {
        ConfigSources configSources = getClass().getAnnotation(ConfigSources.class);
        ConfigSource[] configSourceArray = configSources.value();
        for (ConfigSource configSource : configSourceArray){
            String name = configSource.name();
            int ordinal = configSource.ordinal();
            String encoding = configSource.encoding();
            String resource = configSource.resource();
            URL resourceURL = new URL(resource);
            Class<? extends ConfigSourceFactory> configSourceFactoryClass = configSource.factory();
            if (ConfigSourceFactory.class.equals(configSourceFactoryClass)) {
                configSourceFactoryClass = DefaultConfigSourceFactory.class;
            }

            ConfigSourceFactory configSourceFactory = configSourceFactoryClass.newInstance();
            org.eclipse.microprofile.config.spi.ConfigSource source =
                    configSourceFactory.createConfigSource(name, ordinal, resourceURL, encoding);
            System.out.println(source.getProperties());
        }
    }

    @Test
    public void test() {

    }

}
