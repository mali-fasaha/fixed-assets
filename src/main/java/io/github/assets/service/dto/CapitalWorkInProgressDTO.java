package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.CapitalWorkInProgress} entity.
 */
public class CapitalWorkInProgressDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate transactionMonth;

    @NotNull
    private String assetSerialTag;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private Long transactionId;

    @NotNull
    private String transactionDetails;

    @NotNull
    private BigDecimal transactionAmount;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(LocalDate transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public String getAssetSerialTag() {
        return assetSerialTag;
    }

    public void setAssetSerialTag(String assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CapitalWorkInProgressDTO capitalWorkInProgressDTO = (CapitalWorkInProgressDTO) o;
        if (capitalWorkInProgressDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), capitalWorkInProgressDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CapitalWorkInProgressDTO{" +
            "id=" + getId() +
            ", transactionMonth='" + getTransactionMonth() + "'" +
            ", assetSerialTag='" + getAssetSerialTag() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", transactionId=" + getTransactionId() +
            ", transactionDetails='" + getTransactionDetails() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            "}";
    }
}
