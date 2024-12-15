package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.PaymentMethodTestSamples.*;
import static org.asfa.managerasfa.domain.PaymentTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PaymentMethodTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PaymentMethod.class);
        PaymentMethod paymentMethod1 = getPaymentMethodSample1();
        PaymentMethod paymentMethod2 = new PaymentMethod();
        assertThat(paymentMethod1).isNotEqualTo(paymentMethod2);

        paymentMethod2.setId(paymentMethod1.getId());
        assertThat(paymentMethod1).isEqualTo(paymentMethod2);

        paymentMethod2 = getPaymentMethodSample2();
        assertThat(paymentMethod1).isNotEqualTo(paymentMethod2);
    }

    @Test
    void paymentTest() {
        PaymentMethod paymentMethod = getPaymentMethodRandomSampleGenerator();
        Payment paymentBack = getPaymentRandomSampleGenerator();

        paymentMethod.addPayment(paymentBack);
        assertThat(paymentMethod.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getPaymentmethods()).isEqualTo(paymentMethod);

        paymentMethod.removePayment(paymentBack);
        assertThat(paymentMethod.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getPaymentmethods()).isNull();

        paymentMethod.payments(new HashSet<>(Set.of(paymentBack)));
        assertThat(paymentMethod.getPayments()).containsOnly(paymentBack);
        assertThat(paymentBack.getPaymentmethods()).isEqualTo(paymentMethod);

        paymentMethod.setPayments(new HashSet<>());
        assertThat(paymentMethod.getPayments()).doesNotContain(paymentBack);
        assertThat(paymentBack.getPaymentmethods()).isNull();
    }
}
