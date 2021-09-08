package com.resilience;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;
import io.github.resilience4j.bulkhead.BulkheadFullException;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.rpc.*;

import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Supplier;

/**
 * Resilience4JFilter
 *
 * @author qrXun on 2021/9/8
 */
@Activate(group = "provider")
public class Resilience4JDubboProviderFilter implements Filter {

    private static final String DEFAULT_MAX_CONCURRENT_CALLS = "100";

    private static final String DEFAULT_MAX_WAIT_DURATION_SECONDS = "2";

    private static final Integer MAX_CONCURRENT_CALLS = Integer.parseInt(
            System.getProperty("resilience.max.currentCall", DEFAULT_MAX_CONCURRENT_CALLS)
    );

    private static final Integer MAX_WAIT_DURATION_SECONDS = Integer.parseInt(
            System.getProperty("resilience.max.wait.duration.second", DEFAULT_MAX_WAIT_DURATION_SECONDS)
    );

    private final ConcurrentMap<String, Bulkhead> bulkheadConcurrentMap = new ConcurrentHashMap<>();

    // Create a custom configuration for a Bulkhead
    private final BulkheadConfig config = BulkheadConfig.custom()
            .maxConcurrentCalls(MAX_CONCURRENT_CALLS)
            .maxWaitDuration(Duration.ofSeconds(MAX_WAIT_DURATION_SECONDS))
            .build();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        URL url = invoker.getUrl();
        String serviceKey = url.getServiceKey();
        Bulkhead urlBulkhead = bulkheadConcurrentMap.get(serviceKey);
        if (urlBulkhead == null) {
            urlBulkhead = Bulkhead.of(serviceKey, config);
            bulkheadConcurrentMap.put(serviceKey, urlBulkhead);
        }
        Supplier<Result> resultSupplier = Bulkhead.decorateSupplier(urlBulkhead, () -> invoker.invoke(invocation));
        try {
            return resultSupplier.get();
        } catch (BulkheadFullException e) {
            throw new RpcException(
                    "Failed to invoke service " +
                            invoker.getInterface().getName() +
                            "." +
                            invocation.getMethodName() +
                            " because exceed max service tps.");
        }
    }
}
