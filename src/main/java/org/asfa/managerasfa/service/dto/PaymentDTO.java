package org.asfa.managerasfa.service.dto;

import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link org.asfa.managerasfa.domain.Payment} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentDTO implements Serializable {

    private Long id;

    private UUID paymentUID;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    private Float amount;

    @NotNull
    private Instant timeStamp;

    private PaymentMethodDTO paymentmethods;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getPaymentUID() {
        return paymentUID;
    }

    public void setPaymentUID(UUID paymentUID) {
        this.paymentUID = paymentUID;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Instant getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Instant timeStamp) {
        this.timeStamp = timeStamp;
    }

    public PaymentMethodDTO getPaymentmethods() {
        return paymentmethods;
    }

    public void setPaymentmethods(PaymentMethodDTO paymentmethods) {
        this.paymentmethods = paymentmethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentDTO)) {
            return false;
        }

        PaymentDTO paymentDTO = (PaymentDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, paymentDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentDTO{" +
            "id=" + getId() +
            ", paymentUID='" + getPaymentUID() + "'" +
            ", paymentDate='" + getPaymentDate() + "'" +
            ", amount=" + getAmount() +
            ", timeStamp='" + getTimeStamp() + "'" +
            ", paymentmethods=" + getPaymentmethods() +
            "}";
    }
}
