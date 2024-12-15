package org.asfa.managerasfa.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import org.asfa.managerasfa.domain.enumeration.ProductTypeEnum;

/**
 * A Product.
 */
@Entity
@Table(name = "product")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "product_uid")
    private UUID productUID;

    @NotNull
    @Size(max = 255)
    @Column(name = "contract_number", length = 255, nullable = false)
    private String contractNumber;

    @NotNull
    @Column(name = "start_date", nullable = false)
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Size(max = 255)
    @Column(name = "summary", length = 255)
    private String summary;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "product_type", nullable = false)
    private ProductTypeEnum productType;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "rel_product__eventsubscription",
        joinColumns = @JoinColumn(name = "product_id"),
        inverseJoinColumns = @JoinColumn(name = "eventsubscription_id")
    )
    @JsonIgnoreProperties(value = { "types", "payment", "members", "products" }, allowSetters = true)
    private Set<EventSubscription> eventsubscriptions = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties(value = { "products" }, allowSetters = true)
    private Category categories;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Product id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProductUID() {
        return this.productUID;
    }

    public Product productUID(UUID productUID) {
        this.setProductUID(productUID);
        return this;
    }

    public void setProductUID(UUID productUID) {
        this.productUID = productUID;
    }

    public String getContractNumber() {
        return this.contractNumber;
    }

    public Product contractNumber(String contractNumber) {
        this.setContractNumber(contractNumber);
        return this;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public Product startDate(LocalDate startDate) {
        this.setStartDate(startDate);
        return this;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public Product endDate(LocalDate endDate) {
        this.setEndDate(endDate);
        return this;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSummary() {
        return this.summary;
    }

    public Product summary(String summary) {
        this.setSummary(summary);
        return this;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ProductTypeEnum getProductType() {
        return this.productType;
    }

    public Product productType(ProductTypeEnum productType) {
        this.setProductType(productType);
        return this;
    }

    public void setProductType(ProductTypeEnum productType) {
        this.productType = productType;
    }

    public Set<EventSubscription> getEventsubscriptions() {
        return this.eventsubscriptions;
    }

    public void setEventsubscriptions(Set<EventSubscription> eventSubscriptions) {
        this.eventsubscriptions = eventSubscriptions;
    }

    public Product eventsubscriptions(Set<EventSubscription> eventSubscriptions) {
        this.setEventsubscriptions(eventSubscriptions);
        return this;
    }

    public Product addEventsubscription(EventSubscription eventSubscription) {
        this.eventsubscriptions.add(eventSubscription);
        return this;
    }

    public Product removeEventsubscription(EventSubscription eventSubscription) {
        this.eventsubscriptions.remove(eventSubscription);
        return this;
    }

    public Category getCategories() {
        return this.categories;
    }

    public void setCategories(Category category) {
        this.categories = category;
    }

    public Product categories(Category category) {
        this.setCategories(category);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Product)) {
            return false;
        }
        return getId() != null && getId().equals(((Product) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Product{" +
            "id=" + getId() +
            ", productUID='" + getProductUID() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", summary='" + getSummary() + "'" +
            ", productType='" + getProductType() + "'" +
            "}";
    }
}
