package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;
import static org.asfa.managerasfa.domain.SubscriptionTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SubscriptionTypeTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SubscriptionType.class);
        SubscriptionType subscriptionType1 = getSubscriptionTypeSample1();
        SubscriptionType subscriptionType2 = new SubscriptionType();
        assertThat(subscriptionType1).isNotEqualTo(subscriptionType2);

        subscriptionType2.setId(subscriptionType1.getId());
        assertThat(subscriptionType1).isEqualTo(subscriptionType2);

        subscriptionType2 = getSubscriptionTypeSample2();
        assertThat(subscriptionType1).isNotEqualTo(subscriptionType2);
    }

    @Test
    void subscriptionTest() {
        SubscriptionType subscriptionType = getSubscriptionTypeRandomSampleGenerator();
        EventSubscription eventSubscriptionBack = getEventSubscriptionRandomSampleGenerator();

        subscriptionType.addSubscription(eventSubscriptionBack);
        assertThat(subscriptionType.getSubscriptions()).containsOnly(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getTypes()).isEqualTo(subscriptionType);

        subscriptionType.removeSubscription(eventSubscriptionBack);
        assertThat(subscriptionType.getSubscriptions()).doesNotContain(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getTypes()).isNull();

        subscriptionType.subscriptions(new HashSet<>(Set.of(eventSubscriptionBack)));
        assertThat(subscriptionType.getSubscriptions()).containsOnly(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getTypes()).isEqualTo(subscriptionType);

        subscriptionType.setSubscriptions(new HashSet<>());
        assertThat(subscriptionType.getSubscriptions()).doesNotContain(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getTypes()).isNull();
    }
}
