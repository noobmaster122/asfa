package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;
import static org.asfa.managerasfa.domain.PaymentMethodTestSamples.*;
import static org.asfa.managerasfa.domain.PaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Payment.class);
        Payment payment1 = getPaymentSample1();
        Payment payment2 = new Payment();
        assertThat(payment1).isNotEqualTo(payment2);

        payment2.setId(payment1.getId());
        assertThat(payment1).isEqualTo(payment2);

        payment2 = getPaymentSample2();
        assertThat(payment1).isNotEqualTo(payment2);
    }

    @Test
    void eventsubscriptionTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        EventSubscription eventSubscriptionBack = getEventSubscriptionRandomSampleGenerator();

        payment.addEventsubscription(eventSubscriptionBack);
        assertThat(payment.getEventsubscriptions()).containsOnly(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getPayment()).isEqualTo(payment);

        payment.removeEventsubscription(eventSubscriptionBack);
        assertThat(payment.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getPayment()).isNull();

        payment.eventsubscriptions(new HashSet<>(Set.of(eventSubscriptionBack)));
        assertThat(payment.getEventsubscriptions()).containsOnly(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getPayment()).isEqualTo(payment);

        payment.setEventsubscriptions(new HashSet<>());
        assertThat(payment.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);
        assertThat(eventSubscriptionBack.getPayment()).isNull();
    }

    @Test
    void paymentmethodsTest() {
        Payment payment = getPaymentRandomSampleGenerator();
        PaymentMethod paymentMethodBack = getPaymentMethodRandomSampleGenerator();

        payment.setPaymentmethods(paymentMethodBack);
        assertThat(payment.getPaymentmethods()).isEqualTo(paymentMethodBack);

        payment.paymentmethods(null);
        assertThat(payment.getPaymentmethods()).isNull();
    }
}
