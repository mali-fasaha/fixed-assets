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
 * Criteria class for the {@link io.github.assets.domain.CapitalWorkInProgress} entity. This class is used
 * in {@link io.github.assets.web.rest.CapitalWorkInProgressResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /capital-work-in-progresses?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CapitalWorkInProgressCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter transactionMonth;

    private StringFilter assetSerialTag;

    private StringFilter serviceOutletCode;

    private LongFilter transactionId;

    private StringFilter transactionDetails;

    private BigDecimalFilter transactionAmount;

    public CapitalWorkInProgressCriteria(){
    }

    public CapitalWorkInProgressCriteria(CapitalWorkInProgressCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transactionMonth = other.transactionMonth == null ? null : other.transactionMonth.copy();
        this.assetSerialTag = other.assetSerialTag == null ? null : other.assetSerialTag.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.transactionId = other.transactionId == null ? null : other.transactionId.copy();
        this.transactionDetails = other.transactionDetails == null ? null : other.transactionDetails.copy();
        this.transactionAmount = other.transactionAmount == null ? null : other.transactionAmount.copy();
    }

    @Override
    public CapitalWorkInProgressCriteria copy() {
        return new CapitalWorkInProgressCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getTransactionMonth() {
        return transactionMonth;
    }

    public void setTransactionMonth(LocalDateFilter transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public StringFilter getAssetSerialTag() {
        return assetSerialTag;
    }

    public void setAssetSerialTag(StringFilter assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public LongFilter getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(LongFilter transactionId) {
        this.transactionId = transactionId;
    }

    public StringFilter getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(StringFilter transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public BigDecimalFilter getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(BigDecimalFilter transactionAmount) {
        this.transactionAmount = transactionAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CapitalWorkInProgressCriteria that = (CapitalWorkInProgressCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionMonth, that.transactionMonth) &&
            Objects.equals(assetSerialTag, that.assetSerialTag) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(transactionId, that.transactionId) &&
            Objects.equals(transactionDetails, that.transactionDetails) &&
            Objects.equals(transactionAmount, that.transactionAmount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionMonth,
        assetSerialTag,
        serviceOutletCode,
        transactionId,
        transactionDetails,
        transactionAmount
        );
    }

    @Override
    public String toString() {
        return "CapitalWorkInProgressCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionMonth != null ? "transactionMonth=" + transactionMonth + ", " : "") +
                (assetSerialTag != null ? "assetSerialTag=" + assetSerialTag + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (transactionId != null ? "transactionId=" + transactionId + ", " : "") +
                (transactionDetails != null ? "transactionDetails=" + transactionDetails + ", " : "") +
                (transactionAmount != null ? "transactionAmount=" + transactionAmount + ", " : "") +
            "}";
    }

}
