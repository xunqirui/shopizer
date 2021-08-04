package org.geektimes.cache.interceptor;

import org.geektimes.interceptor.AnnotatedInterceptor;

import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CacheRemove;
import javax.cache.spi.CachingProvider;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * CacheRemoveInterceptor
 *
 * @author qrXun on 2021/8/4
 */
@Interceptor
public class CacheRemoveInterceptor extends AnnotatedInterceptor<CacheRemove> {

    private CachingProvider cachingProvider = Caching.getCachingProvider();

    private CacheManager cacheManager = cachingProvider.getCacheManager();

    @Override
    protected Object execute(InvocationContext context, CacheRemove cacheRemove) throws Throwable {
        Object result;
        String cacheName = cacheRemove.cacheName();
        boolean afterInvocation = cacheRemove.afterInvocation();
        if (afterInvocation) {
            result = context.proceed();
            removeCache(cacheName);
        } else {
            removeCache(cacheName);
            result = context.proceed();
        }
        return result;
    }

    private void removeCache(String cacheName) {
        cacheManager.destroyCache(cacheName);
    }
}
