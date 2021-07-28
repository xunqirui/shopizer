package org.geektimes.interceptor.proxy;

import org.eclipse.microprofile.faulttolerance.Bulkhead;

/**
 * TestService
 *
 * @author qrXun on 2021/7/28
 */
public interface TestService {

    @Bulkhead
    void echo(String message);

}
