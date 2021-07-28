package org.geektimes.interceptor.proxy;

import org.junit.Test;

import static org.geektimes.interceptor.AnnotatedInterceptor.loadInterceptors;

/**
 * JavaProxyInterceptorEnhancerTest
 *
 * @author qrXun on 2021/7/28
 */
public class JavaProxyInterceptorEnhancerTest {

    @Test
    public void test() {
        JavaProxyInterceptorEnhancer enhancer = new JavaProxyInterceptorEnhancer();
        TestService testService = new TestServiceImpl();
        Object proxy = enhancer.getEnhanceObject(testService, loadInterceptors());
        TestService proxyService = (TestService) proxy;
        proxyService.echo("Hello,World");
    }

}
