package org.asfa.managerasfa.service.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriptionTypeDTOTest {

    @Test
    void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionTypeDTO.class);
        SubscriptionTypeDTO subscriptionTypeDTO1 = new SubscriptionTypeDTO();
        subscriptionTypeDTO1.setId(1L);
        SubscriptionTypeDTO subscriptionTypeDTO2 = new SubscriptionTypeDTO();
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO2.setId(subscriptionTypeDTO1.getId());
        assertThat(subscriptionTypeDTO1).isEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO2.setId(2L);
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
        subscriptionTypeDTO1.setId(null);
        assertThat(subscriptionTypeDTO1).isNotEqualTo(subscriptionTypeDTO2);
    }
}
