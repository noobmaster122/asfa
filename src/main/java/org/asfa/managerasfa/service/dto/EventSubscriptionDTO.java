package org.asfa.managerasfa.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A DTO for the {@link org.asfa.managerasfa.domain.EventSubscription} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class EventSubscriptionDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate subscriptionDate;

    @NotNull
    private Boolean isActive;

    private String anonymousEmail;

    @Size(max = 80)
    private String anonymousName;

    private SubscriptionTypeDTO types;

    private PaymentDTO payment;

    private Set<MemberDTO> members = new HashSet<>();

    private Set<ProductDTO> products = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getSubscriptionDate() {
        return subscriptionDate;
    }

    public void setSubscriptionDate(LocalDate subscriptionDate) {
        this.subscriptionDate = subscriptionDate;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getAnonymousEmail() {
        return anonymousEmail;
    }

    public void setAnonymousEmail(String anonymousEmail) {
        this.anonymousEmail = anonymousEmail;
    }

    public String getAnonymousName() {
        return anonymousName;
    }

    public void setAnonymousName(String anonymousName) {
        this.anonymousName = anonymousName;
    }

    public SubscriptionTypeDTO getTypes() {
        return types;
    }

    public void setTypes(SubscriptionTypeDTO types) {
        this.types = types;
    }

    public PaymentDTO getPayment() {
        return payment;
    }

    public void setPayment(PaymentDTO payment) {
        this.payment = payment;
    }

    public Set<MemberDTO> getMembers() {
        return members;
    }

    public void setMembers(Set<MemberDTO> members) {
        this.members = members;
    }

    public Set<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(Set<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EventSubscriptionDTO)) {
            return false;
        }

        EventSubscriptionDTO eventSubscriptionDTO = (EventSubscriptionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, eventSubscriptionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "EventSubscriptionDTO{" +
            "id=" + getId() +
            ", subscriptionDate='" + getSubscriptionDate() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", anonymousEmail='" + getAnonymousEmail() + "'" +
            ", anonymousName='" + getAnonymousName() + "'" +
            ", types=" + getTypes() +
            ", payment=" + getPayment() +
            ", members=" + getMembers() +
            ", products=" + getProducts() +
            "}";
    }
}
