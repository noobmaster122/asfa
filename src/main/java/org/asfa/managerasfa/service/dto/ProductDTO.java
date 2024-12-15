package org.asfa.managerasfa.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import org.asfa.managerasfa.domain.enumeration.ProductTypeEnum;

/**
 * A DTO for the {@link org.asfa.managerasfa.domain.Product} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ProductDTO implements Serializable {

    private Long id;

    private UUID productUID;

    @NotNull
    @Size(max = 255)
    private String contractNumber;

    @NotNull
    private LocalDate startDate;

    private LocalDate endDate;

    @Size(max = 255)
    private String summary;

    @NotNull
    private ProductTypeEnum productType;

    private Set<EventSubscriptionDTO> eventsubscriptions = new HashSet<>();

    private CategoryDTO categories;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getProductUID() {
        return productUID;
    }

    public void setProductUID(UUID productUID) {
        this.productUID = productUID;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public ProductTypeEnum getProductType() {
        return productType;
    }

    public void setProductType(ProductTypeEnum productType) {
        this.productType = productType;
    }

    public Set<EventSubscriptionDTO> getEventsubscriptions() {
        return eventsubscriptions;
    }

    public void setEventsubscriptions(Set<EventSubscriptionDTO> eventsubscriptions) {
        this.eventsubscriptions = eventsubscriptions;
    }

    public CategoryDTO getCategories() {
        return categories;
    }

    public void setCategories(CategoryDTO categories) {
        this.categories = categories;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ProductDTO)) {
            return false;
        }

        ProductDTO productDTO = (ProductDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, productDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ProductDTO{" +
            "id=" + getId() +
            ", productUID='" + getProductUID() + "'" +
            ", contractNumber='" + getContractNumber() + "'" +
            ", startDate='" + getStartDate() + "'" +
            ", endDate='" + getEndDate() + "'" +
            ", summary='" + getSummary() + "'" +
            ", productType='" + getProductType() + "'" +
            ", eventsubscriptions=" + getEventsubscriptions() +
            ", categories=" + getCategories() +
            "}";
    }
}
