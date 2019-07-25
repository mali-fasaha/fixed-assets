package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.AssetAcquisition} entity.
 */
public class AssetAcquisitionDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private LocalDate acquisitionMonth;

    @NotNull
    private String assetSerial;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private Long acquisitionTransactionId;

    @NotNull
    private Long assetCategoryId;

    @NotNull
    private BigDecimal purchaseAmount;

    private Long assetDealerId;

    private Long assetInvoiceId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAcquisitionMonth() {
        return acquisitionMonth;
    }

    public void setAcquisitionMonth(LocalDate acquisitionMonth) {
        this.acquisitionMonth = acquisitionMonth;
    }

    public String getAssetSerial() {
        return assetSerial;
    }

    public void setAssetSerial(String assetSerial) {
        this.assetSerial = assetSerial;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getAcquisitionTransactionId() {
        return acquisitionTransactionId;
    }

    public void setAcquisitionTransactionId(Long acquisitionTransactionId) {
        this.acquisitionTransactionId = acquisitionTransactionId;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getAssetDealerId() {
        return assetDealerId;
    }

    public void setAssetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
    }

    public Long getAssetInvoiceId() {
        return assetInvoiceId;
    }

    public void setAssetInvoiceId(Long assetInvoiceId) {
        this.assetInvoiceId = assetInvoiceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetAcquisitionDTO assetAcquisitionDTO = (AssetAcquisitionDTO) o;
        if (assetAcquisitionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetAcquisitionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetAcquisitionDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", acquisitionMonth='" + getAcquisitionMonth() + "'" +
            ", assetSerial='" + getAssetSerial() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", acquisitionTransactionId=" + getAcquisitionTransactionId() +
            ", assetCategoryId=" + getAssetCategoryId() +
            ", purchaseAmount=" + getPurchaseAmount() +
            ", assetDealerId=" + getAssetDealerId() +
            ", assetInvoiceId=" + getAssetInvoiceId() +
            "}";
    }
}
