package org.asfa.managerasfa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class CategoryTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Category getCategorySample1() {
        return new Category().id(1L).label("label1").summary("summary1");
    }

    public static Category getCategorySample2() {
        return new Category().id(2L).label("label2").summary("summary2");
    }

    public static Category getCategoryRandomSampleGenerator() {
        return new Category().id(longCount.incrementAndGet()).label(UUID.randomUUID().toString()).summary(UUID.randomUUID().toString());
    }
}
