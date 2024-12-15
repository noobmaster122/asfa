package org.asfa.managerasfa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubscriptionTypeTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static SubscriptionType getSubscriptionTypeSample1() {
        return new SubscriptionType().id(1L).label("label1").summary("summary1");
    }

    public static SubscriptionType getSubscriptionTypeSample2() {
        return new SubscriptionType().id(2L).label("label2").summary("summary2");
    }

    public static SubscriptionType getSubscriptionTypeRandomSampleGenerator() {
        return new SubscriptionType()
            .id(longCount.incrementAndGet())
            .label(UUID.randomUUID().toString())
            .summary(UUID.randomUUID().toString());
    }
}
