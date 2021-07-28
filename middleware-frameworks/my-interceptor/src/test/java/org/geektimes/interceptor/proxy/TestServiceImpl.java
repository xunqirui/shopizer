package org.geektimes.interceptor.proxy;

/**
 * TestServiceImpl
 *
 * @author qrXun on 2021/7/28
 */
public class TestServiceImpl implements TestService{
    @Override
    public void echo(String message) {
        System.out.println(message);
    }
}
