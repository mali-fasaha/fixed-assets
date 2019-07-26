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
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.AssetTransaction} entity. This class is used
 * in {@link io.github.assets.web.rest.AssetTransactionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-transactions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssetTransactionCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter transactionReference;

    private LocalDateFilter transactionDate;

    private LongFilter scannedDocumentId;

    private LongFilter transactionApprovalId;

    public AssetTransactionCriteria(){
    }

    public AssetTransactionCriteria(AssetTransactionCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.transactionReference = other.transactionReference == null ? null : other.transactionReference.copy();
        this.transactionDate = other.transactionDate == null ? null : other.transactionDate.copy();
        this.scannedDocumentId = other.scannedDocumentId == null ? null : other.scannedDocumentId.copy();
        this.transactionApprovalId = other.transactionApprovalId == null ? null : other.transactionApprovalId.copy();
    }

    @Override
    public AssetTransactionCriteria copy() {
        return new AssetTransactionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(StringFilter transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDateFilter getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDateFilter transactionDate) {
        this.transactionDate = transactionDate;
    }

    public LongFilter getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(LongFilter scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public LongFilter getTransactionApprovalId() {
        return transactionApprovalId;
    }

    public void setTransactionApprovalId(LongFilter transactionApprovalId) {
        this.transactionApprovalId = transactionApprovalId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetTransactionCriteria that = (AssetTransactionCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(transactionReference, that.transactionReference) &&
            Objects.equals(transactionDate, that.transactionDate) &&
            Objects.equals(scannedDocumentId, that.scannedDocumentId) &&
            Objects.equals(transactionApprovalId, that.transactionApprovalId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        transactionReference,
        transactionDate,
        scannedDocumentId,
        transactionApprovalId
        );
    }

    @Override
    public String toString() {
        return "AssetTransactionCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (transactionReference != null ? "transactionReference=" + transactionReference + ", " : "") +
                (transactionDate != null ? "transactionDate=" + transactionDate + ", " : "") +
                (scannedDocumentId != null ? "scannedDocumentId=" + scannedDocumentId + ", " : "") +
                (transactionApprovalId != null ? "transactionApprovalId=" + transactionApprovalId + ", " : "") +
            "}";
    }

}
