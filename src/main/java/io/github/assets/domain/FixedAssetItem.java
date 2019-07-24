package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A FixedAssetItem.
 */
@Entity
@Table(name = "fixed_asset_item")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fixedassetitem")
public class FixedAssetItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "service_outlet_code", nullable = false)
    private String serviceOutletCode;

    @NotNull
    @Column(name = "asset_category_id", nullable = false)
    private Long assetCategoryId;

    @NotNull
    @Column(name = "fixed_asset_serial_code", nullable = false)
    private String fixedAssetSerialCode;

    @NotNull
    @Column(name = "fixed_asset_description", nullable = false)
    private String fixedAssetDescription;

    @NotNull
    @Column(name = "purchase_date", nullable = false)
    private LocalDate purchaseDate;

    @NotNull
    @Column(name = "purchase_cost", precision = 21, scale = 2, nullable = false)
    private BigDecimal purchaseCost;

    @NotNull
    @Column(name = "purchase_transaction_id", nullable = false)
    private Long purchaseTransactionId;

    @Column(name = "ownership_document_id")
    private Long ownershipDocumentId;

    @Lob
    @Column(name = "asset_picture")
    private byte[] assetPicture;

    @Column(name = "asset_picture_content_type")
    private String assetPictureContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public FixedAssetItem serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public FixedAssetItem assetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
        return this;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public String getFixedAssetSerialCode() {
        return fixedAssetSerialCode;
    }

    public FixedAssetItem fixedAssetSerialCode(String fixedAssetSerialCode) {
        this.fixedAssetSerialCode = fixedAssetSerialCode;
        return this;
    }

    public void setFixedAssetSerialCode(String fixedAssetSerialCode) {
        this.fixedAssetSerialCode = fixedAssetSerialCode;
    }

    public String getFixedAssetDescription() {
        return fixedAssetDescription;
    }

    public FixedAssetItem fixedAssetDescription(String fixedAssetDescription) {
        this.fixedAssetDescription = fixedAssetDescription;
        return this;
    }

    public void setFixedAssetDescription(String fixedAssetDescription) {
        this.fixedAssetDescription = fixedAssetDescription;
    }

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public FixedAssetItem purchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
        return this;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getPurchaseCost() {
        return purchaseCost;
    }

    public FixedAssetItem purchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
        return this;
    }

    public void setPurchaseCost(BigDecimal purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public Long getPurchaseTransactionId() {
        return purchaseTransactionId;
    }

    public FixedAssetItem purchaseTransactionId(Long purchaseTransactionId) {
        this.purchaseTransactionId = purchaseTransactionId;
        return this;
    }

    public void setPurchaseTransactionId(Long purchaseTransactionId) {
        this.purchaseTransactionId = purchaseTransactionId;
    }

    public Long getOwnershipDocumentId() {
        return ownershipDocumentId;
    }

    public FixedAssetItem ownershipDocumentId(Long ownershipDocumentId) {
        this.ownershipDocumentId = ownershipDocumentId;
        return this;
    }

    public void setOwnershipDocumentId(Long ownershipDocumentId) {
        this.ownershipDocumentId = ownershipDocumentId;
    }

    public byte[] getAssetPicture() {
        return assetPicture;
    }

    public FixedAssetItem assetPicture(byte[] assetPicture) {
        this.assetPicture = assetPicture;
        return this;
    }

    public void setAssetPicture(byte[] assetPicture) {
        this.assetPicture = assetPicture;
    }

    public String getAssetPictureContentType() {
        return assetPictureContentType;
    }

    public FixedAssetItem assetPictureContentType(String assetPictureContentType) {
        this.assetPictureContentType = assetPictureContentType;
        return this;
    }

    public void setAssetPictureContentType(String assetPictureContentType) {
        this.assetPictureContentType = assetPictureContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedAssetItem)) {
            return false;
        }
        return id != null && id.equals(((FixedAssetItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FixedAssetItem{" +
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
            ", assetPictureContentType='" + getAssetPictureContentType() + "'" +
            "}";
    }
}
