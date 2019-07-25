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
 * Criteria class for the {@link io.github.assets.domain.AssetDisposal} entity. This class is used
 * in {@link io.github.assets.web.rest.AssetDisposalResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-disposals?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssetDisposalCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private LocalDateFilter disposalMonth;

    private LongFilter assetCategoryId;

    private LongFilter assetItemId;

    private BigDecimalFilter disposalProceeds;

    private BigDecimalFilter netBookValue;

    private BigDecimalFilter profitOnDisposal;

    private LongFilter scannedDocumentId;

    private LongFilter assetDealerId;

    public AssetDisposalCriteria(){
    }

    public AssetDisposalCriteria(AssetDisposalCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.disposalMonth = other.disposalMonth == null ? null : other.disposalMonth.copy();
        this.assetCategoryId = other.assetCategoryId == null ? null : other.assetCategoryId.copy();
        this.assetItemId = other.assetItemId == null ? null : other.assetItemId.copy();
        this.disposalProceeds = other.disposalProceeds == null ? null : other.disposalProceeds.copy();
        this.netBookValue = other.netBookValue == null ? null : other.netBookValue.copy();
        this.profitOnDisposal = other.profitOnDisposal == null ? null : other.profitOnDisposal.copy();
        this.scannedDocumentId = other.scannedDocumentId == null ? null : other.scannedDocumentId.copy();
        this.assetDealerId = other.assetDealerId == null ? null : other.assetDealerId.copy();
    }

    @Override
    public AssetDisposalCriteria copy() {
        return new AssetDisposalCriteria(this);
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

    public LocalDateFilter getDisposalMonth() {
        return disposalMonth;
    }

    public void setDisposalMonth(LocalDateFilter disposalMonth) {
        this.disposalMonth = disposalMonth;
    }

    public LongFilter getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(LongFilter assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public LongFilter getAssetItemId() {
        return assetItemId;
    }

    public void setAssetItemId(LongFilter assetItemId) {
        this.assetItemId = assetItemId;
    }

    public BigDecimalFilter getDisposalProceeds() {
        return disposalProceeds;
    }

    public void setDisposalProceeds(BigDecimalFilter disposalProceeds) {
        this.disposalProceeds = disposalProceeds;
    }

    public BigDecimalFilter getNetBookValue() {
        return netBookValue;
    }

    public void setNetBookValue(BigDecimalFilter netBookValue) {
        this.netBookValue = netBookValue;
    }

    public BigDecimalFilter getProfitOnDisposal() {
        return profitOnDisposal;
    }

    public void setProfitOnDisposal(BigDecimalFilter profitOnDisposal) {
        this.profitOnDisposal = profitOnDisposal;
    }

    public LongFilter getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(LongFilter scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public LongFilter getAssetDealerId() {
        return assetDealerId;
    }

    public void setAssetDealerId(LongFilter assetDealerId) {
        this.assetDealerId = assetDealerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetDisposalCriteria that = (AssetDisposalCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(disposalMonth, that.disposalMonth) &&
            Objects.equals(assetCategoryId, that.assetCategoryId) &&
            Objects.equals(assetItemId, that.assetItemId) &&
            Objects.equals(disposalProceeds, that.disposalProceeds) &&
            Objects.equals(netBookValue, that.netBookValue) &&
            Objects.equals(profitOnDisposal, that.profitOnDisposal) &&
            Objects.equals(scannedDocumentId, that.scannedDocumentId) &&
            Objects.equals(assetDealerId, that.assetDealerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        disposalMonth,
        assetCategoryId,
        assetItemId,
        disposalProceeds,
        netBookValue,
        profitOnDisposal,
        scannedDocumentId,
        assetDealerId
        );
    }

    @Override
    public String toString() {
        return "AssetDisposalCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (disposalMonth != null ? "disposalMonth=" + disposalMonth + ", " : "") +
                (assetCategoryId != null ? "assetCategoryId=" + assetCategoryId + ", " : "") +
                (assetItemId != null ? "assetItemId=" + assetItemId + ", " : "") +
                (disposalProceeds != null ? "disposalProceeds=" + disposalProceeds + ", " : "") +
                (netBookValue != null ? "netBookValue=" + netBookValue + ", " : "") +
                (profitOnDisposal != null ? "profitOnDisposal=" + profitOnDisposal + ", " : "") +
                (scannedDocumentId != null ? "scannedDocumentId=" + scannedDocumentId + ", " : "") +
                (assetDealerId != null ? "assetDealerId=" + assetDealerId + ", " : "") +
            "}";
    }

}
