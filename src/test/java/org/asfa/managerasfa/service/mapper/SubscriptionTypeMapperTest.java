package org.asfa.managerasfa.service.mapper;

import static org.asfa.managerasfa.domain.SubscriptionTypeAsserts.*;
import static org.asfa.managerasfa.domain.SubscriptionTypeTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubscriptionTypeMapperTest {

    private SubscriptionTypeMapper subscriptionTypeMapper;

    @BeforeEach
    void setUp() {
        subscriptionTypeMapper = new SubscriptionTypeMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubscriptionTypeSample1();
        var actual = subscriptionTypeMapper.toEntity(subscriptionTypeMapper.toDto(expected));
        assertSubscriptionTypeAllPropertiesEquals(expected, actual);
    }
}
