package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.CwipTransfer} entity.
 */
public class CwipTransferDTO implements Serializable {

    private Long id;

    @NotNull
    private LocalDate transferMonth;

    @NotNull
    private String assetSerialTag;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private Long transferTransactionId;

    @NotNull
    private Long assetCategoryId;

    @NotNull
    private Long cwipTransactionId;

    @NotNull
    private String transferDetails;

    @NotNull
    private BigDecimal transferAmount;

    private Long dealerId;

    private Long transactionInvoiceId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransferMonth() {
        return transferMonth;
    }

    public void setTransferMonth(LocalDate transferMonth) {
        this.transferMonth = transferMonth;
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

    public Long getTransferTransactionId() {
        return transferTransactionId;
    }

    public void setTransferTransactionId(Long transferTransactionId) {
        this.transferTransactionId = transferTransactionId;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Long getCwipTransactionId() {
        return cwipTransactionId;
    }

    public void setCwipTransactionId(Long cwipTransactionId) {
        this.cwipTransactionId = cwipTransactionId;
    }

    public String getTransferDetails() {
        return transferDetails;
    }

    public void setTransferDetails(String transferDetails) {
        this.transferDetails = transferDetails;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getTransactionInvoiceId() {
        return transactionInvoiceId;
    }

    public void setTransactionInvoiceId(Long transactionInvoiceId) {
        this.transactionInvoiceId = transactionInvoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CwipTransferDTO cwipTransferDTO = (CwipTransferDTO) o;
        if (cwipTransferDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), cwipTransferDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "CwipTransferDTO{" +
            "id=" + getId() +
            ", transferMonth='" + getTransferMonth() + "'" +
            ", assetSerialTag='" + getAssetSerialTag() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", transferTransactionId=" + getTransferTransactionId() +
            ", assetCategoryId=" + getAssetCategoryId() +
            ", cwipTransactionId=" + getCwipTransactionId() +
            ", transferDetails='" + getTransferDetails() + "'" +
            ", transferAmount=" + getTransferAmount() +
            ", dealerId=" + getDealerId() +
            ", transactionInvoiceId=" + getTransactionInvoiceId() +
            "}";
    }
}
