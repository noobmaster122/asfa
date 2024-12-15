package org.asfa.managerasfa.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link org.asfa.managerasfa.domain.SubscriptionType} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubscriptionTypeDTO implements Serializable {

    private Long id;

    @NotNull
    @Size(max = 100)
    private String label;

    @Size(max = 255)
    private String summary;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubscriptionTypeDTO)) {
            return false;
        }

        SubscriptionTypeDTO subscriptionTypeDTO = (SubscriptionTypeDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, subscriptionTypeDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubscriptionTypeDTO{" +
            "id=" + getId() +
            ", label='" + getLabel() + "'" +
            ", summary='" + getSummary() + "'" +
            "}";
    }
}
