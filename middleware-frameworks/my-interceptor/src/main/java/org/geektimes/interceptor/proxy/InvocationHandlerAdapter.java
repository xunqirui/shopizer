package org.geektimes.interceptor.proxy;

import org.geektimes.interceptor.ChainableInvocationContext;
import org.geektimes.interceptor.JavaProxyInvocationContext;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * InvocationHandlerAdapter
 *
 * @author qrXun on 2021/7/28
 */
public class InvocationHandlerAdapter implements InvocationHandler {

    private final Object[] interceptors;

    private final Object target;

    public InvocationHandlerAdapter(Object target, Object[] interceptors) {
        this.interceptors = interceptors;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        JavaProxyInvocationContext delegateContext = new JavaProxyInvocationContext(target, method, args);
        ChainableInvocationContext context = new ChainableInvocationContext(delegateContext, interceptors);
        return context.proceed();
    }
}
