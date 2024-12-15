package org.asfa.managerasfa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * A SubscriptionType.
 */
@Entity
@Table(name = "subscription_type")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubscriptionType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(name = "label", length = 100, nullable = false)
    private String label;

    @Size(max = 255)
    @Column(name = "summary", length = 255)
    private String summary;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "types")
    @JsonIgnoreProperties(value = { "types", "payment", "members", "products" }, allowSetters = true)
    private Set<EventSubscription> subscriptions = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SubscriptionType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return this.label;
    }

    public SubscriptionType label(String label) {
        this.setLabel(label);
        return this;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return this.summary;
    }

    public SubscriptionType summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public Set<EventSubscription> getSubscriptions() {
        return this.subscriptions;
    }

    public void setSubscriptions(Set<EventSubscription> eventSubscriptions) {
        if (this.subscriptions != null) {
            this.subscriptions.forEach(i -> i.setTypes(null));
        }
        if (eventSubscriptions != null) {
            eventSubscriptions.forEach(i -> i.setTypes(this));
        }
        this.subscriptions = eventSubscriptions;
    }

    public SubscriptionType subscriptions(Set<EventSubscription> eventSubscriptions) {
        this.setSubscriptions(eventSubscriptions);
        return this;
    }

    public SubscriptionType addSubscription(EventSubscription eventSubscription) {
        this.subscriptions.add(eventSubscription);
        eventSubscription.setTypes(this);
        return this;
    }

    public SubscriptionType removeSubscription(EventSubscription eventSubscription) {
        this.subscriptions.remove(eventSubscription);
        eventSubscription.setTypes(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionType)) {
            return false;
        }
        return getId() != null && getId().equals(((SubscriptionType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionType{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", summary='" + getSummary() + "'" +
            "}";
    }
}
