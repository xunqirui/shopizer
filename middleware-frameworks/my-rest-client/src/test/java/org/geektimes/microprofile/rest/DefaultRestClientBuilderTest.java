package org.geektimes.microprofile.rest;

import org.eclipse.microprofile.rest.client.RestClientBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * DefaultRestClientBuilderTest
 *
 * @author qrXun on 2021/7/21
 */
public class DefaultRestClientBuilderTest {

    public static void main(String[] args) throws URISyntaxException, MalformedURLException {
        URL apiUri = new URL("http://127.0.0.1:8080");
        ActuatorEndPointService endPointService = RestClientBuilder.newBuilder()
                .baseUrl(apiUri)
                .build(ActuatorEndPointService.class);
        String message = endPointService.shutdown();
        System.out.println(message);
    }

}
