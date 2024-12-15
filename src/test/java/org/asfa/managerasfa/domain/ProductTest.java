package org.asfa.managerasfa.domain;

import static org.asfa.managerasfa.domain.CategoryTestSamples.*;
import static org.asfa.managerasfa.domain.EventSubscriptionTestSamples.*;
import static org.asfa.managerasfa.domain.ProductTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import org.asfa.managerasfa.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Product.class);
        Product product1 = getProductSample1();
        Product product2 = new Product();
        assertThat(product1).isNotEqualTo(product2);

        product2.setId(product1.getId());
        assertThat(product1).isEqualTo(product2);

        product2 = getProductSample2();
        assertThat(product1).isNotEqualTo(product2);
    }

    @Test
    void eventsubscriptionTest() {
        Product product = getProductRandomSampleGenerator();
        EventSubscription eventSubscriptionBack = getEventSubscriptionRandomSampleGenerator();

        product.addEventsubscription(eventSubscriptionBack);
        assertThat(product.getEventsubscriptions()).containsOnly(eventSubscriptionBack);

        product.removeEventsubscription(eventSubscriptionBack);
        assertThat(product.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);

        product.eventsubscriptions(new HashSet<>(Set.of(eventSubscriptionBack)));
        assertThat(product.getEventsubscriptions()).containsOnly(eventSubscriptionBack);

        product.setEventsubscriptions(new HashSet<>());
        assertThat(product.getEventsubscriptions()).doesNotContain(eventSubscriptionBack);
    }

    @Test
    void categoriesTest() {
        Product product = getProductRandomSampleGenerator();
        Category categoryBack = getCategoryRandomSampleGenerator();

        product.setCategories(categoryBack);
        assertThat(product.getCategories()).isEqualTo(categoryBack);

        product.categories(null);
        assertThat(product.getCategories()).isNull();
    }
}
