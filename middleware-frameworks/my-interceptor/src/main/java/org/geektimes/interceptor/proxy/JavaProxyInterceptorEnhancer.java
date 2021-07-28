package org.geektimes.interceptor.proxy;

import java.lang.reflect.Proxy;

/**
 * JavaProxyInterceptorEnhancer
 *
 * @author qrXun on 2021/7/28
 */
public class JavaProxyInterceptorEnhancer {

    public Object getEnhanceObject(Object target, Object... interceptors) {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),
                new InvocationHandlerAdapter(target, interceptors));
    }
}
