package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.AssetAcquisition} entity. This class is used
 * in {@link io.github.assets.web.rest.AssetAcquisitionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-acquisitions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssetAcquisitionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LocalDateFilter acquisitionMonth;

    private StringFilter assetSerial;

    private StringFilter serviceOutletCode;

    private LongFilter acquisitionTransactionId;

    private LongFilter assetCategoryId;

    private BigDecimalFilter purchaseAmount;

    private LongFilter assetDealerId;

    private LongFilter assetInvoiceId;

    public AssetAcquisitionCriteria(){
    }

    public AssetAcquisitionCriteria(AssetAcquisitionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.acquisitionMonth = other.acquisitionMonth == null ? null : other.acquisitionMonth.copy();
        this.assetSerial = other.assetSerial == null ? null : other.assetSerial.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.acquisitionTransactionId = other.acquisitionTransactionId == null ? null : other.acquisitionTransactionId.copy();
        this.assetCategoryId = other.assetCategoryId == null ? null : other.assetCategoryId.copy();
        this.purchaseAmount = other.purchaseAmount == null ? null : other.purchaseAmount.copy();
        this.assetDealerId = other.assetDealerId == null ? null : other.assetDealerId.copy();
        this.assetInvoiceId = other.assetInvoiceId == null ? null : other.assetInvoiceId.copy();
    }

    @Override
    public AssetAcquisitionCriteria copy() {
        return new AssetAcquisitionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public LocalDateFilter getAcquisitionMonth() {
        return acquisitionMonth;
    }

    public void setAcquisitionMonth(LocalDateFilter acquisitionMonth) {
        this.acquisitionMonth = acquisitionMonth;
    }

    public StringFilter getAssetSerial() {
        return assetSerial;
    }

    public void setAssetSerial(StringFilter assetSerial) {
        this.assetSerial = assetSerial;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public LongFilter getAcquisitionTransactionId() {
        return acquisitionTransactionId;
    }

    public void setAcquisitionTransactionId(LongFilter acquisitionTransactionId) {
        this.acquisitionTransactionId = acquisitionTransactionId;
    }

    public LongFilter getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(LongFilter assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public BigDecimalFilter getPurchaseAmount() {
        return purchaseAmount;
    }

    public void setPurchaseAmount(BigDecimalFilter purchaseAmount) {
        this.purchaseAmount = purchaseAmount;
    }

    public LongFilter getAssetDealerId() {
        return assetDealerId;
    }

    public void setAssetDealerId(LongFilter assetDealerId) {
        this.assetDealerId = assetDealerId;
    }

    public LongFilter getAssetInvoiceId() {
        return assetInvoiceId;
    }

    public void setAssetInvoiceId(LongFilter assetInvoiceId) {
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
        final AssetAcquisitionCriteria that = (AssetAcquisitionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(acquisitionMonth, that.acquisitionMonth) &&
            Objects.equals(assetSerial, that.assetSerial) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(acquisitionTransactionId, that.acquisitionTransactionId) &&
            Objects.equals(assetCategoryId, that.assetCategoryId) &&
            Objects.equals(purchaseAmount, that.purchaseAmount) &&
            Objects.equals(assetDealerId, that.assetDealerId) &&
            Objects.equals(assetInvoiceId, that.assetInvoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        acquisitionMonth,
        assetSerial,
        serviceOutletCode,
        acquisitionTransactionId,
        assetCategoryId,
        purchaseAmount,
        assetDealerId,
        assetInvoiceId
        );
    }

    @Override
    public String toString() {
        return "AssetAcquisitionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (acquisitionMonth != null ? "acquisitionMonth=" + acquisitionMonth + ", " : "") +
                (assetSerial != null ? "assetSerial=" + assetSerial + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (acquisitionTransactionId != null ? "acquisitionTransactionId=" + acquisitionTransactionId + ", " : "") +
                (assetCategoryId != null ? "assetCategoryId=" + assetCategoryId + ", " : "") +
                (purchaseAmount != null ? "purchaseAmount=" + purchaseAmount + ", " : "") +
                (assetDealerId != null ? "assetDealerId=" + assetDealerId + ", " : "") +
                (assetInvoiceId != null ? "assetInvoiceId=" + assetInvoiceId + ", " : "") +
            "}";
    }

}
