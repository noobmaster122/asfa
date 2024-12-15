package org.asfa.managerasfa.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class MemberTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Member getMemberSample1() {
        return new Member()
            .id(1L)
            .memberUID(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .firstName("firstName1")
            .lastName("lastName1")
            .middleName("middleName1")
            .email("email1")
            .country("country1")
            .city("city1")
            .address("address1")
            .zipCode("zipCode1");
    }

    public static Member getMemberSample2() {
        return new Member()
            .id(2L)
            .memberUID(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .firstName("firstName2")
            .lastName("lastName2")
            .middleName("middleName2")
            .email("email2")
            .country("country2")
            .city("city2")
            .address("address2")
            .zipCode("zipCode2");
    }

    public static Member getMemberRandomSampleGenerator() {
        return new Member()
            .id(longCount.incrementAndGet())
            .memberUID(UUID.randomUUID())
            .firstName(UUID.randomUUID().toString())
            .lastName(UUID.randomUUID().toString())
            .middleName(UUID.randomUUID().toString())
            .email(UUID.randomUUID().toString())
            .country(UUID.randomUUID().toString())
            .city(UUID.randomUUID().toString())
            .address(UUID.randomUUID().toString())
            .zipCode(UUID.randomUUID().toString());
    }
}
