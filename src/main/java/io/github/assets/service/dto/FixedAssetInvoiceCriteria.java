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
 * Criteria class for the {@link io.github.assets.domain.FixedAssetInvoice} entity. This class is used
 * in {@link io.github.assets.web.rest.FixedAssetInvoiceResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fixed-asset-invoices?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FixedAssetInvoiceCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter invoiceReference;

    private LocalDateFilter invoiceDate;

    private BigDecimalFilter invoiceAmount;

    private BooleanFilter isProforma;

    private BooleanFilter isCreditNote;

    private LongFilter dealerId;

    public FixedAssetInvoiceCriteria(){
    }

    public FixedAssetInvoiceCriteria(FixedAssetInvoiceCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.invoiceReference = other.invoiceReference == null ? null : other.invoiceReference.copy();
        this.invoiceDate = other.invoiceDate == null ? null : other.invoiceDate.copy();
        this.invoiceAmount = other.invoiceAmount == null ? null : other.invoiceAmount.copy();
        this.isProforma = other.isProforma == null ? null : other.isProforma.copy();
        this.isCreditNote = other.isCreditNote == null ? null : other.isCreditNote.copy();
        this.dealerId = other.dealerId == null ? null : other.dealerId.copy();
    }

    @Override
    public FixedAssetInvoiceCriteria copy() {
        return new FixedAssetInvoiceCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(StringFilter invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public LocalDateFilter getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDateFilter invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimalFilter getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimalFilter invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public BooleanFilter getIsProforma() {
        return isProforma;
    }

    public void setIsProforma(BooleanFilter isProforma) {
        this.isProforma = isProforma;
    }

    public BooleanFilter getIsCreditNote() {
        return isCreditNote;
    }

    public void setIsCreditNote(BooleanFilter isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public LongFilter getDealerId() {
        return dealerId;
    }

    public void setDealerId(LongFilter dealerId) {
        this.dealerId = dealerId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FixedAssetInvoiceCriteria that = (FixedAssetInvoiceCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(invoiceReference, that.invoiceReference) &&
            Objects.equals(invoiceDate, that.invoiceDate) &&
            Objects.equals(invoiceAmount, that.invoiceAmount) &&
            Objects.equals(isProforma, that.isProforma) &&
            Objects.equals(isCreditNote, that.isCreditNote) &&
            Objects.equals(dealerId, that.dealerId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        invoiceReference,
        invoiceDate,
        invoiceAmount,
        isProforma,
        isCreditNote,
        dealerId
        );
    }

    @Override
    public String toString() {
        return "FixedAssetInvoiceCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (invoiceReference != null ? "invoiceReference=" + invoiceReference + ", " : "") +
                (invoiceDate != null ? "invoiceDate=" + invoiceDate + ", " : "") +
                (invoiceAmount != null ? "invoiceAmount=" + invoiceAmount + ", " : "") +
                (isProforma != null ? "isProforma=" + isProforma + ", " : "") +
                (isCreditNote != null ? "isCreditNote=" + isCreditNote + ", " : "") +
                (dealerId != null ? "dealerId=" + dealerId + ", " : "") +
            "}";
    }

}
