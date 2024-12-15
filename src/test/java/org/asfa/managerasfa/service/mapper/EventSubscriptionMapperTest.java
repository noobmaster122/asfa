package org.asfa.managerasfa.service.mapper;

import static org.asfa.managerasfa.domain.EventSubscriptionAsserts.*;
import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class EventSubscriptionMapperTest {

    private EventSubscriptionMapper eventSubscriptionMapper;

    @BeforeEach
    void setUp() {
        eventSubscriptionMapper = new EventSubscriptionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getEventSubscriptionSample1();
        var actual = eventSubscriptionMapper.toEntity(eventSubscriptionMapper.toDto(expected));
        assertEventSubscriptionAllPropertiesEquals(expected, actual);
    }
}
