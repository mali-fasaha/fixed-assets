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
 * A AssetDisposal.
 */
@Entity
@Table(name = "asset_disposal")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "assetdisposal")
public class AssetDisposal implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "description", nullable = false)
    private String description;

    @NotNull
    @Column(name = "disposal_month", nullable = false)
    private LocalDate disposalMonth;

    @NotNull
    @Column(name = "asset_category_id", nullable = false)
    private Long assetCategoryId;

    @NotNull
    @Column(name = "asset_item_id", nullable = false)
    private Long assetItemId;

    @NotNull
    @Column(name = "disposal_proceeds", precision = 21, scale = 2, nullable = false)
    private BigDecimal disposalProceeds;

    @Column(name = "net_book_value", precision = 21, scale = 2)
    private BigDecimal netBookValue;

    @Column(name = "profit_on_disposal", precision = 21, scale = 2)
    private BigDecimal profitOnDisposal;

    @Column(name = "scanned_document_id")
    private Long scannedDocumentId;

    @Column(name = "asset_dealer_id")
    private Long assetDealerId;

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

    public String getDescription() {
        return description;
    }

    public AssetDisposal description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDisposalMonth() {
        return disposalMonth;
    }

    public AssetDisposal disposalMonth(LocalDate disposalMonth) {
        this.disposalMonth = disposalMonth;
        return this;
    }

    public void setDisposalMonth(LocalDate disposalMonth) {
        this.disposalMonth = disposalMonth;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public AssetDisposal assetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
        return this;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Long getAssetItemId() {
        return assetItemId;
    }

    public AssetDisposal assetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
        return this;
    }

    public void setAssetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
    }

    public BigDecimal getDisposalProceeds() {
        return disposalProceeds;
    }

    public AssetDisposal disposalProceeds(BigDecimal disposalProceeds) {
        this.disposalProceeds = disposalProceeds;
        return this;
    }

    public void setDisposalProceeds(BigDecimal disposalProceeds) {
        this.disposalProceeds = disposalProceeds;
    }

    public BigDecimal getNetBookValue() {
        return netBookValue;
    }

    public AssetDisposal netBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
        return this;
    }

    public void setNetBookValue(BigDecimal netBookValue) {
        this.netBookValue = netBookValue;
    }

    public BigDecimal getProfitOnDisposal() {
        return profitOnDisposal;
    }

    public AssetDisposal profitOnDisposal(BigDecimal profitOnDisposal) {
        this.profitOnDisposal = profitOnDisposal;
        return this;
    }

    public void setProfitOnDisposal(BigDecimal profitOnDisposal) {
        this.profitOnDisposal = profitOnDisposal;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public AssetDisposal scannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
        return this;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public Long getAssetDealerId() {
        return assetDealerId;
    }

    public AssetDisposal assetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
        return this;
    }

    public void setAssetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
    }

    public byte[] getAssetPicture() {
        return assetPicture;
    }

    public AssetDisposal assetPicture(byte[] assetPicture) {
        this.assetPicture = assetPicture;
        return this;
    }

    public void setAssetPicture(byte[] assetPicture) {
        this.assetPicture = assetPicture;
    }

    public String getAssetPictureContentType() {
        return assetPictureContentType;
    }

    public AssetDisposal assetPictureContentType(String assetPictureContentType) {
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
        if (!(o instanceof AssetDisposal)) {
            return false;
        }
        return id != null && id.equals(((AssetDisposal) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssetDisposal{" +
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
            ", assetPictureContentType='" + getAssetPictureContentType() + "'" +
            "}";
    }
}
