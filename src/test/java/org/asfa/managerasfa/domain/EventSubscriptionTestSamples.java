package org.asfa.managerasfa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class EventSubscriptionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static EventSubscription getEventSubscriptionSample1() {
        return new EventSubscription().id(1L).anonymousEmail("anonymousEmail1").anonymousName("anonymousName1");
    }

    public static EventSubscription getEventSubscriptionSample2() {
        return new EventSubscription().id(2L).anonymousEmail("anonymousEmail2").anonymousName("anonymousName2");
    }

    public static EventSubscription getEventSubscriptionRandomSampleGenerator() {
        return new EventSubscription()
            .id(longCount.incrementAndGet())
            .anonymousEmail(UUID.randomUUID().toString())
            .anonymousName(UUID.randomUUID().toString());
    }
}
