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
 * Criteria class for the {@link io.github.assets.domain.CwipTransfer} entity. This class is used
 * in {@link io.github.assets.web.rest.CwipTransferResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /cwip-transfers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class CwipTransferCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LocalDateFilter transferMonth;

    private StringFilter assetSerialTag;

    private StringFilter serviceOutletCode;

    private LongFilter transferTransactionId;

    private LongFilter assetCategoryId;

    private LongFilter cwipTransactionId;

    private StringFilter transferDetails;

    private BigDecimalFilter transferAmount;

    private LongFilter dealerId;

    private LongFilter transactionInvoiceId;

    public CwipTransferCriteria(){
    }

    public CwipTransferCriteria(CwipTransferCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transferMonth = other.transferMonth == null ? null : other.transferMonth.copy();
        this.assetSerialTag = other.assetSerialTag == null ? null : other.assetSerialTag.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.transferTransactionId = other.transferTransactionId == null ? null : other.transferTransactionId.copy();
        this.assetCategoryId = other.assetCategoryId == null ? null : other.assetCategoryId.copy();
        this.cwipTransactionId = other.cwipTransactionId == null ? null : other.cwipTransactionId.copy();
        this.transferDetails = other.transferDetails == null ? null : other.transferDetails.copy();
        this.transferAmount = other.transferAmount == null ? null : other.transferAmount.copy();
        this.dealerId = other.dealerId == null ? null : other.dealerId.copy();
        this.transactionInvoiceId = other.transactionInvoiceId == null ? null : other.transactionInvoiceId.copy();
    }

    @Override
    public CwipTransferCriteria copy() {
        return new CwipTransferCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public LocalDateFilter getTransferMonth() {
        return transferMonth;
    }

    public void setTransferMonth(LocalDateFilter transferMonth) {
        this.transferMonth = transferMonth;
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

    public LongFilter getTransferTransactionId() {
        return transferTransactionId;
    }

    public void setTransferTransactionId(LongFilter transferTransactionId) {
        this.transferTransactionId = transferTransactionId;
    }

    public LongFilter getAssetCategoryId() {
        return assetCategoryId;
    }

    public void setAssetCategoryId(LongFilter assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public LongFilter getCwipTransactionId() {
        return cwipTransactionId;
    }

    public void setCwipTransactionId(LongFilter cwipTransactionId) {
        this.cwipTransactionId = cwipTransactionId;
    }

    public StringFilter getTransferDetails() {
        return transferDetails;
    }

    public void setTransferDetails(StringFilter transferDetails) {
        this.transferDetails = transferDetails;
    }

    public BigDecimalFilter getTransferAmount() {
        return transferAmount;
    }

    public void setTransferAmount(BigDecimalFilter transferAmount) {
        this.transferAmount = transferAmount;
    }

    public LongFilter getDealerId() {
        return dealerId;
    }

    public void setDealerId(LongFilter dealerId) {
        this.dealerId = dealerId;
    }

    public LongFilter getTransactionInvoiceId() {
        return transactionInvoiceId;
    }

    public void setTransactionInvoiceId(LongFilter transactionInvoiceId) {
        this.transactionInvoiceId = transactionInvoiceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final CwipTransferCriteria that = (CwipTransferCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transferMonth, that.transferMonth) &&
            Objects.equals(assetSerialTag, that.assetSerialTag) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(transferTransactionId, that.transferTransactionId) &&
            Objects.equals(assetCategoryId, that.assetCategoryId) &&
            Objects.equals(cwipTransactionId, that.cwipTransactionId) &&
            Objects.equals(transferDetails, that.transferDetails) &&
            Objects.equals(transferAmount, that.transferAmount) &&
            Objects.equals(dealerId, that.dealerId) &&
            Objects.equals(transactionInvoiceId, that.transactionInvoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transferMonth,
        assetSerialTag,
        serviceOutletCode,
        transferTransactionId,
        assetCategoryId,
        cwipTransactionId,
        transferDetails,
        transferAmount,
        dealerId,
        transactionInvoiceId
        );
    }

    @Override
    public String toString() {
        return "CwipTransferCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transferMonth != null ? "transferMonth=" + transferMonth + ", " : "") +
                (assetSerialTag != null ? "assetSerialTag=" + assetSerialTag + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (transferTransactionId != null ? "transferTransactionId=" + transferTransactionId + ", " : "") +
                (assetCategoryId != null ? "assetCategoryId=" + assetCategoryId + ", " : "") +
                (cwipTransactionId != null ? "cwipTransactionId=" + cwipTransactionId + ", " : "") +
                (transferDetails != null ? "transferDetails=" + transferDetails + ", " : "") +
                (transferAmount != null ? "transferAmount=" + transferAmount + ", " : "") +
                (dealerId != null ? "dealerId=" + dealerId + ", " : "") +
                (transactionInvoiceId != null ? "transactionInvoiceId=" + transactionInvoiceId + ", " : "") +
            "}";
    }

}
