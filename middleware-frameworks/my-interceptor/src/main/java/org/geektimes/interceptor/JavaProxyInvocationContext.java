package org.geektimes.interceptor;

import javax.interceptor.InvocationContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * JavaProxyInvocationContext
 *
 * @author qrXun on 2021/7/28
 */
public class JavaProxyInvocationContext implements InvocationContext {

    private final Object target;

    private final Map<String, Object> contextData;

    private final Method method;

    private Object[] parameters;

    public JavaProxyInvocationContext(Object target, Method method, Object[] parameters) {
        this.target = target;
        this.contextData = new HashMap<>();
        this.method = method;
        this.parameters = parameters;
    }

    @Override
    public Object getTarget() {
        return target;
    }

    @Override
    public Object getTimer() {
        return null;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Constructor<?> getConstructor() {
        return null;
    }

    @Override
    public Object[] getParameters() {
        return parameters;
    }

    @Override
    public void setParameters(Object[] params) {
        this.parameters = params;
    }

    @Override
    public Map<String, Object> getContextData() {
        return contextData;
    }

    @Override
    public Object proceed() throws Exception {
        return method.invoke(target, parameters);
    }
}
