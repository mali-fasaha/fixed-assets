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
 * Criteria class for the {@link io.github.assets.domain.FixedAssetItem} entity. This class is used
 * in {@link io.github.assets.web.rest.FixedAssetItemResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fixed-asset-items?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FixedAssetItemCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceOutletCode;

    private LongFilter assetCategoryId;

    private StringFilter fixedAssetSerialCode;

    private StringFilter fixedAssetDescription;

    private LocalDateFilter purchaseDate;

    private BigDecimalFilter purchaseCost;

    private LongFilter purchaseTransactionId;

    private LongFilter ownershipDocumentId;

    public FixedAssetItemCriteria(){
    }

    public FixedAssetItemCriteria(FixedAssetItemCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.assetCategoryId = other.assetCategoryId == null ? null : other.assetCategoryId.copy();
        this.fixedAssetSerialCode = other.fixedAssetSerialCode == null ? null : other.fixedAssetSerialCode.copy();
        this.fixedAssetDescription = other.fixedAssetDescription == null ? null : other.fixedAssetDescription.copy();
        this.purchaseDate = other.purchaseDate == null ? null : other.purchaseDate.copy();
        this.purchaseCost = other.purchaseCost == null ? null : other.purchaseCost.copy();
        this.purchaseTransactionId = other.purchaseTransactionId == null ? null : other.purchaseTransactionId.copy();
        this.ownershipDocumentId = other.ownershipDocumentId == null ? null : other.ownershipDocumentId.copy();
    }

    @Override
    public FixedAssetItemCriteria copy() {
        return new FixedAssetItemCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public LongFilter getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(LongFilter assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public StringFilter getFixedAssetSerialCode() {
        return fixedAssetSerialCode;
    }

    public void setFixedAssetSerialCode(StringFilter fixedAssetSerialCode) {
        this.fixedAssetSerialCode = fixedAssetSerialCode;
    }

    public StringFilter getFixedAssetDescription() {
        return fixedAssetDescription;
    }

    public void setFixedAssetDescription(StringFilter fixedAssetDescription) {
        this.fixedAssetDescription = fixedAssetDescription;
    }

    public LocalDateFilter getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDateFilter purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimalFilter getPurchaseCost() {
        return purchaseCost;
    }

    public void setPurchaseCost(BigDecimalFilter purchaseCost) {
        this.purchaseCost = purchaseCost;
    }

    public LongFilter getPurchaseTransactionId() {
        return purchaseTransactionId;
    }

    public void setPurchaseTransactionId(LongFilter purchaseTransactionId) {
        this.purchaseTransactionId = purchaseTransactionId;
    }

    public LongFilter getOwnershipDocumentId() {
        return ownershipDocumentId;
    }

    public void setOwnershipDocumentId(LongFilter ownershipDocumentId) {
        this.ownershipDocumentId = ownershipDocumentId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FixedAssetItemCriteria that = (FixedAssetItemCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(assetCategoryId, that.assetCategoryId) &&
            Objects.equals(fixedAssetSerialCode, that.fixedAssetSerialCode) &&
            Objects.equals(fixedAssetDescription, that.fixedAssetDescription) &&
            Objects.equals(purchaseDate, that.purchaseDate) &&
            Objects.equals(purchaseCost, that.purchaseCost) &&
            Objects.equals(purchaseTransactionId, that.purchaseTransactionId) &&
            Objects.equals(ownershipDocumentId, that.ownershipDocumentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceOutletCode,
        assetCategoryId,
        fixedAssetSerialCode,
        fixedAssetDescription,
        purchaseDate,
        purchaseCost,
        purchaseTransactionId,
        ownershipDocumentId
        );
    }

    @Override
    public String toString() {
        return "FixedAssetItemCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (assetCategoryId != null ? "assetCategoryId=" + assetCategoryId + ", " : "") +
                (fixedAssetSerialCode != null ? "fixedAssetSerialCode=" + fixedAssetSerialCode + ", " : "") +
                (fixedAssetDescription != null ? "fixedAssetDescription=" + fixedAssetDescription + ", " : "") +
                (purchaseDate != null ? "purchaseDate=" + purchaseDate + ", " : "") +
                (purchaseCost != null ? "purchaseCost=" + purchaseCost + ", " : "") +
                (purchaseTransactionId != null ? "purchaseTransactionId=" + purchaseTransactionId + ", " : "") +
                (ownershipDocumentId != null ? "ownershipDocumentId=" + ownershipDocumentId + ", " : "") +
            "}";
    }

}
