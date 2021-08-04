package org.geektimes.cache.interceptor;

import org.geektimes.interceptor.AnnotatedInterceptor;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.Caching;
import javax.cache.annotation.CacheRemove;
import javax.cache.configuration.MutableConfiguration;
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
            doRemoveCache(cacheName, context.getParameters()[0]);
        } else {
            doRemoveCache(cacheName, context.getParameters()[0]);
            result = context.proceed();
        }
        return result;
    }

    private void doRemoveCache(String cacheName, Object key) {
        Cache cache = getCache(cacheName);
        if (key != null) {
            cache.remove(key);
        }
    }

    private Cache getCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            cache = cacheManager.createCache(cacheName,
                    new MutableConfiguration().setTypes(Object.class, Object.class));
        }
        return cache;
    }


}
