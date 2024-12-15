package org.asfa.managerasfa.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventSubscriptionDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventSubscriptionDTO.class);
        EventSubscriptionDTO eventSubscriptionDTO1 = new EventSubscriptionDTO();
        eventSubscriptionDTO1.setId(1L);
        EventSubscriptionDTO eventSubscriptionDTO2 = new EventSubscriptionDTO();
        assertThat(eventSubscriptionDTO1).isNotEqualTo(eventSubscriptionDTO2);
        eventSubscriptionDTO2.setId(eventSubscriptionDTO1.getId());
        assertThat(eventSubscriptionDTO1).isEqualTo(eventSubscriptionDTO2);
        eventSubscriptionDTO2.setId(2L);
        assertThat(eventSubscriptionDTO1).isNotEqualTo(eventSubscriptionDTO2);
        eventSubscriptionDTO1.setId(null);
        assertThat(eventSubscriptionDTO1).isNotEqualTo(eventSubscriptionDTO2);
    }
}
