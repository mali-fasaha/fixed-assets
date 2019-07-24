package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.assets.domain.FixedAssetItem} entity.
 */
public class FixedAssetItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private Long assetCategoryId;

    @NotNull
    private String fixedAssetSerialCode;

    @NotNull
    private String fixedAssetDescription;

    @NotNull
    private LocalDate purchaseDate;

    @NotNull
    private BigDecimal purchaseCost;

    @NotNull
    private Long purchaseTransactionId;

    private Long ownershipDocumentId;

    @Lob
    private byte[] assetPicture;

    private String assetPictureContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public String getFixedAssetSerialCode() {
        return fixedAssetSerialCode;
    }

    public void setFixedAssetSerialCode(String fixedAssetSerialCode) {
        this.fixedAssetSerialCode = fixedAssetSerialCode;
    }

    public String getFixedAssetDescription() {
        return fixedAssetDescription;
    }

    public void setFixedAssetDescription(String fixedAssetDescription) {
        this.fixedAssetDescription = fixedAssetDescription;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Long getPurchaseTransactionId() {
        return purchaseTransactionId;
    }

    public void setPurchaseTransactionId(Long purchaseTransactionId) {
        this.purchaseTransactionId = purchaseTransactionId;
    }

    public Long getOwnershipDocumentId() {
        return ownershipDocumentId;
    }

    public void setOwnershipDocumentId(Long ownershipDocumentId) {
        this.ownershipDocumentId = ownershipDocumentId;
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

        FixedAssetItemDTO fixedAssetItemDTO = (FixedAssetItemDTO) o;
        if (fixedAssetItemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedAssetItemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedAssetItemDTO{" +
            "id=" + getId() +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", assetCategoryId=" + getAssetCategoryId() +
            ", fixedAssetSerialCode='" + getFixedAssetSerialCode() + "'" +
            ", fixedAssetDescription='" + getFixedAssetDescription() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", purchaseCost=" + getPurchaseCost() +
            ", purchaseTransactionId=" + getPurchaseTransactionId() +
            ", ownershipDocumentId=" + getOwnershipDocumentId() +
            ", assetPicture='" + getAssetPicture() + "'" +
            "}";
    }
}
