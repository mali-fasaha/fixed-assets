package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.assets.domain.AssetDisposal} entity.
 */
public class AssetDisposalDTO implements Serializable {

    private Long id;

    @NotNull
    private String description;

    @NotNull
    private LocalDate disposalMonth;

    @NotNull
    private Long assetCategoryId;

    @NotNull
    private Long assetItemId;

    @NotNull
    private BigDecimal disposalProceeds;

    private BigDecimal netBookValue;

    private BigDecimal profitOnDisposal;

    private Long scannedDocumentId;

    private Long assetDealerId;

    @Lob
    private byte[] assetPicture;

    private String assetPictureContentType;

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

    public LocalDate getDisposalMonth() {
        return disposalMonth;
    }

    public void setDisposalMonth(LocalDate disposalMonth) {
        this.disposalMonth = disposalMonth;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Long getAssetItemId() {
        return assetItemId;
    }

    public void setAssetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
    }

    public BigDecimal getDisposalProceeds() {
        return disposalProceeds;
    }

    public void setDisposalProceeds(BigDecimal disposalProceeds) {
        this.disposalProceeds = disposalProceeds;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public BigDecimal getProfitOnDisposal() {
        return profitOnDisposal;
    }

    public void setProfitOnDisposal(BigDecimal profitOnDisposal) {
        this.profitOnDisposal = profitOnDisposal;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public Long getAssetDealerId() {
        return assetDealerId;
    }

    public void setAssetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
    }

    public byte[] getAssetPicture() {
        return assetPicture;
    }

    public void setAssetPicture(byte[] assetPicture) {
        this.assetPicture = assetPicture;
    }

    public String getAssetPictureContentType() {
        return assetPictureContentType;
    }

    public void setAssetPictureContentType(String assetPictureContentType) {
        this.assetPictureContentType = assetPictureContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AssetDisposalDTO assetDisposalDTO = (AssetDisposalDTO) o;
        if (assetDisposalDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetDisposalDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetDisposalDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", disposalMonth='" + getDisposalMonth() + "'" +
            ", assetCategoryId=" + getAssetCategoryId() +
            ", assetItemId=" + getAssetItemId() +
            ", disposalProceeds=" + getDisposalProceeds() +
            ", netBookValue=" + getNetBookValue() +
            ", profitOnDisposal=" + getProfitOnDisposal() +
            ", scannedDocumentId=" + getScannedDocumentId() +
            ", assetDealerId=" + getAssetDealerId() +
            ", assetPicture='" + getAssetPicture() + "'" +
            "}";
    }
}
