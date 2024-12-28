package org.asfa.managerasfa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A EventSubscription.
 */
@Entity
@Table(name = "event_subscription")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventSubscription implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @NotNull
    @Column(name = "subscription_date", nullable = false)
    private LocalDate subscriptionDate;

    @NotNull
    @Column(name = "is_active", nullable = false)
    private Boolean isActive;

    @Size(max = 80)
    @Column(name = "anonymous_email")
    private String anonymousEmail;

    @Size(max = 80)
    @Column(name = "anonymous_name", length = 80)
    private String anonymousName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "subscriptions" }, allowSetters = true)
    private SubscriptionType types;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "eventsubscriptions", "paymentmethods" }, allowSetters = true)
    private Payment payment;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "eventsubscriptions")
    @JsonIgnoreProperties(value = { "familyMembers", "eventsubscriptions", "member" }, allowSetters = true)
    private Set<Member> members = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "eventsubscriptions")
    @JsonIgnoreProperties(value = { "eventsubscriptions", "categories" }, allowSetters = true)
    private Set<Product> products = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public EventSubscription id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubscriptionDate() {
        return this.subscriptionDate;
    }

    public EventSubscription subscriptionDate(LocalDate subscriptionDate) {
        this.setSubscriptionDate(subscriptionDate);
        return this;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public EventSubscription isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAnonymousEmail() {
        return this.anonymousEmail;
    }

    public EventSubscription anonymousEmail(String anonymousEmail) {
        this.setAnonymousEmail(anonymousEmail);
        return this;
    }

    public void setAnonymousEmail(String anonymousEmail) {
        this.anonymousEmail = anonymousEmail;
    }

    public String getAnonymousName() {
        return this.anonymousName;
    }

    public EventSubscription anonymousName(String anonymousName) {
        this.setAnonymousName(anonymousName);
        return this;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public SubscriptionType getTypes() {
        return this.types;
    }

    public void setTypes(SubscriptionType subscriptionType) {
        this.types = subscriptionType;
    }

    public EventSubscription types(SubscriptionType subscriptionType) {
        this.setTypes(subscriptionType);
        return this;
    }

    public Payment getPayment() {
        return this.payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public EventSubscription payment(Payment payment) {
        this.setPayment(payment);
        return this;
    }

    public Set<Member> getMembers() {
        return this.members;
    }

    public void setMembers(Set<Member> members) {
        if (this.members != null) {
            this.members.forEach(i -> i.removeEventsubscription(this));
        }
        if (members != null) {
            members.forEach(i -> i.addEventsubscription(this));
        }
        this.members = members;
    }

    public EventSubscription members(Set<Member> members) {
        this.setMembers(members);
        return this;
    }

    public EventSubscription addMember(Member member) {
        this.members.add(member);
        member.getEventsubscriptions().add(this);
        return this;
    }

    public EventSubscription removeMember(Member member) {
        this.members.remove(member);
        member.getEventsubscriptions().remove(this);
        return this;
    }

    public Set<Product> getProducts() {
        return this.products;
    }

    public void setProducts(Set<Product> products) {
        if (this.products != null) {
            this.products.forEach(i -> i.removeEventsubscription(this));
        }
        if (products != null) {
            products.forEach(i -> i.addEventsubscription(this));
        }
        this.products = products;
    }

    public EventSubscription products(Set<Product> products) {
        this.setProducts(products);
        return this;
    }

    public EventSubscription addProduct(Product product) {
        this.products.add(product);
        product.getEventsubscriptions().add(this);
        return this;
    }

    public EventSubscription removeProduct(Product product) {
        this.products.remove(product);
        product.getEventsubscriptions().remove(this);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventSubscription)) {
            return false;
        }
        return getId() != null && getId().equals(((EventSubscription) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventSubscription{" +
            "id=" + getId() +
            ", subscriptionDate='" + getSubscriptionDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", anonymousEmail='" + getAnonymousEmail() + "'" +
            ", anonymousName='" + getAnonymousName() + "'" +
            "}";
    }
}
