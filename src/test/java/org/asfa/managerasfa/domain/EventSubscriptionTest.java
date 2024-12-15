package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;
import static org.asfa.managerasfa.domain.MemberTestSamples.*;
import static org.asfa.managerasfa.domain.PaymentTestSamples.*;
import static org.asfa.managerasfa.domain.ProductTestSamples.*;
import static org.asfa.managerasfa.domain.SubscriptionTypeTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class EventSubscriptionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventSubscription.class);
        EventSubscription eventSubscription1 = getEventSubscriptionSample1();
        EventSubscription eventSubscription2 = new EventSubscription();
        assertThat(eventSubscription1).isNotEqualTo(eventSubscription2);

        eventSubscription2.setId(eventSubscription1.getId());
        assertThat(eventSubscription1).isEqualTo(eventSubscription2);

        eventSubscription2 = getEventSubscriptionSample2();
        assertThat(eventSubscription1).isNotEqualTo(eventSubscription2);
    }

    @Test
    void typesTest() {
        EventSubscription eventSubscription = getEventSubscriptionRandomSampleGenerator();
        SubscriptionType subscriptionTypeBack = getSubscriptionTypeRandomSampleGenerator();

        eventSubscription.setTypes(subscriptionTypeBack);
        assertThat(eventSubscription.getTypes()).isEqualTo(subscriptionTypeBack);

        eventSubscription.types(null);
        assertThat(eventSubscription.getTypes()).isNull();
    }

    @Test
    void paymentTest() {
        EventSubscription eventSubscription = getEventSubscriptionRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        eventSubscription.setPayment(paymentBack);
        assertThat(eventSubscription.getPayment()).isEqualTo(paymentBack);

        eventSubscription.payment(null);
        assertThat(eventSubscription.getPayment()).isNull();
    }

    @Test
    void memberTest() {
        EventSubscription eventSubscription = getEventSubscriptionRandomSampleGenerator();
        Member memberBack = getMemberRandomSampleGenerator();

        eventSubscription.addMember(memberBack);
        assertThat(eventSubscription.getMembers()).containsOnly(memberBack);
        assertThat(memberBack.getEventsubscriptions()).containsOnly(eventSubscription);

        eventSubscription.removeMember(memberBack);
        assertThat(eventSubscription.getMembers()).doesNotContain(memberBack);
        assertThat(memberBack.getEventsubscriptions()).doesNotContain(eventSubscription);

        eventSubscription.members(new HashSet<>(Set.of(memberBack)));
        assertThat(eventSubscription.getMembers()).containsOnly(memberBack);
        assertThat(memberBack.getEventsubscriptions()).containsOnly(eventSubscription);

        eventSubscription.setMembers(new HashSet<>());
        assertThat(eventSubscription.getMembers()).doesNotContain(memberBack);
        assertThat(memberBack.getEventsubscriptions()).doesNotContain(eventSubscription);
    }

    @Test
    void productTest() {
        EventSubscription eventSubscription = getEventSubscriptionRandomSampleGenerator();
        Product productBack = getProductRandomSampleGenerator();

        eventSubscription.addProduct(productBack);
        assertThat(eventSubscription.getProducts()).containsOnly(productBack);
        assertThat(productBack.getEventsubscriptions()).containsOnly(eventSubscription);

        eventSubscription.removeProduct(productBack);
        assertThat(eventSubscription.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getEventsubscriptions()).doesNotContain(eventSubscription);

        eventSubscription.products(new HashSet<>(Set.of(productBack)));
        assertThat(eventSubscription.getProducts()).containsOnly(productBack);
        assertThat(productBack.getEventsubscriptions()).containsOnly(eventSubscription);

        eventSubscription.setProducts(new HashSet<>());
        assertThat(eventSubscription.getProducts()).doesNotContain(productBack);
        assertThat(productBack.getEventsubscriptions()).doesNotContain(eventSubscription);
    }
}
