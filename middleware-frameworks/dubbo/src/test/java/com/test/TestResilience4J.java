package com.test;

import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.bulkhead.BulkheadConfig;

import java.time.Duration;
import java.util.function.Supplier;

/**
 * TestResilience4J
 *
 * @author qrXun on 2021/9/8
 */
public class TestResilience4J {

    public static void main(String[] args) throws InterruptedException {
        // Create a custom configuration for a Bulkhead
        BulkheadConfig config = BulkheadConfig.custom()
                .maxConcurrentCalls(1)
                .maxWaitDuration(Duration.ofSeconds(2))
                .build();


        Bulkhead bulkhead = Bulkhead.of("test", config);

        Supplier<String> decoratedSupplier = Bulkhead
                .decorateSupplier(bulkhead, () -> {
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            return "123";
                        }
                );

        for (int i = 0; i < 3; i++) {
            Thread thread = new Thread(() -> {
                String result = decoratedSupplier.get();
                System.out.println(result);
            });
            thread.start();
        }

        Thread.sleep(100000);
    }

}
