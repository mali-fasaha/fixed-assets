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
 * A AssetAcquisition.
 */
@Entity
@Table(name = "asset_acquisition")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "assetacquisition")
public class AssetAcquisition implements Serializable {

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
    @Column(name = "acquisition_month", nullable = false)
    private LocalDate acquisitionMonth;

    @NotNull
    @Column(name = "asset_serial", nullable = false)
    private String assetSerial;

    @NotNull
    @Column(name = "service_outlet_code", nullable = false)
    private String serviceOutletCode;

    @NotNull
    @Column(name = "acquisition_transaction_id", nullable = false)
    private Long acquisitionTransactionId;

    @NotNull
    @Column(name = "asset_category_id", nullable = false)
    private Long assetCategoryId;

    @NotNull
    @Column(name = "purchase_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal purchaseAmount;

    @Column(name = "asset_dealer_id")
    private Long assetDealerId;

    @Column(name = "asset_invoice_id")
    private Long assetInvoiceId;

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

    public AssetAcquisition description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getAcquisitionMonth() {
        return acquisitionMonth;
    }

    public AssetAcquisition acquisitionMonth(LocalDate acquisitionMonth) {
        this.acquisitionMonth = acquisitionMonth;
        return this;
    }

    public void setAcquisitionMonth(LocalDate acquisitionMonth) {
        this.acquisitionMonth = acquisitionMonth;
    }

    public String getAssetSerial() {
        return assetSerial;
    }

    public AssetAcquisition assetSerial(String assetSerial) {
        this.assetSerial = assetSerial;
        return this;
    }

    public void setAssetSerial(String assetSerial) {
        this.assetSerial = assetSerial;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public AssetAcquisition serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getAcquisitionTransactionId() {
        return acquisitionTransactionId;
    }

    public AssetAcquisition acquisitionTransactionId(Long acquisitionTransactionId) {
        this.acquisitionTransactionId = acquisitionTransactionId;
        return this;
    }

    public void setAcquisitionTransactionId(Long acquisitionTransactionId) {
        this.acquisitionTransactionId = acquisitionTransactionId;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public AssetAcquisition assetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
        return this;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public BigDecimal getPurchaseAmount() {
        return purchaseAmount;
    }

    public AssetAcquisition purchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
        return this;
    }

    public void setPurchaseAmount(BigDecimal purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public Long getAssetDealerId() {
        return assetDealerId;
    }

    public AssetAcquisition assetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
        return this;
    }

    public void setAssetDealerId(Long assetDealerId) {
        this.assetDealerId = assetDealerId;
    }

    public Long getAssetInvoiceId() {
        return assetInvoiceId;
    }

    public AssetAcquisition assetInvoiceId(Long assetInvoiceId) {
        this.assetInvoiceId = assetInvoiceId;
        return this;
    }

    public void setAssetInvoiceId(Long assetInvoiceId) {
        this.assetInvoiceId = assetInvoiceId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetAcquisition)) {
            return false;
        }
        return id != null && id.equals(((AssetAcquisition) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssetAcquisition{" +
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
