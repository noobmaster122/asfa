package org.asfa.managerasfa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class ProductTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Product getProductSample1() {
        return new Product()
            .id(1L)
            .productUID(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .contractNumber("contractNumber1")
            .summary("summary1");
    }

    public static Product getProductSample2() {
        return new Product()
            .id(2L)
            .productUID(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .contractNumber("contractNumber2")
            .summary("summary2");
    }

    public static Product getProductRandomSampleGenerator() {
        return new Product()
            .id(longCount.incrementAndGet())
            .productUID(UUID.randomUUID())
            .contractNumber(UUID.randomUUID().toString())
            .summary(UUID.randomUUID().toString());
    }
}
