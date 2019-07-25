package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.assets.domain.enumeration.TitleTypes;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.Dealer} entity. This class is used
 * in {@link io.github.assets.web.rest.DealerResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /dealers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DealerCriteria implements Serializable, Criteria {
    /**
     * Class for filtering TitleTypes
     */
    public static class TitleTypesFilter extends Filter<TitleTypes> {

        public TitleTypesFilter() {
        }

        public TitleTypesFilter(TitleTypesFilter filter) {
            super(filter);
        }

        @Override
        public TitleTypesFilter copy() {
            return new TitleTypesFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private TitleTypesFilter title;

    private StringFilter dealerName;

    private StringFilter dealerAddress;

    private StringFilter dealerPhoneNumber;

    private StringFilter dealerEmail;

    private StringFilter bankName;

    private StringFilter bankAccountNumber;

    private StringFilter bankBranch;

    private StringFilter bankSwiftCode;

    private StringFilter bankPhysicalAddress;

    private StringFilter domicile;

    private StringFilter taxAuthorityRef;

    private LongFilter fixedAssetInvoiceId;

    public DealerCriteria(){
    }

    public DealerCriteria(DealerCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.dealerName = other.dealerName == null ? null : other.dealerName.copy();
        this.dealerAddress = other.dealerAddress == null ? null : other.dealerAddress.copy();
        this.dealerPhoneNumber = other.dealerPhoneNumber == null ? null : other.dealerPhoneNumber.copy();
        this.dealerEmail = other.dealerEmail == null ? null : other.dealerEmail.copy();
        this.bankName = other.bankName == null ? null : other.bankName.copy();
        this.bankAccountNumber = other.bankAccountNumber == null ? null : other.bankAccountNumber.copy();
        this.bankBranch = other.bankBranch == null ? null : other.bankBranch.copy();
        this.bankSwiftCode = other.bankSwiftCode == null ? null : other.bankSwiftCode.copy();
        this.bankPhysicalAddress = other.bankPhysicalAddress == null ? null : other.bankPhysicalAddress.copy();
        this.domicile = other.domicile == null ? null : other.domicile.copy();
        this.taxAuthorityRef = other.taxAuthorityRef == null ? null : other.taxAuthorityRef.copy();
        this.fixedAssetInvoiceId = other.fixedAssetInvoiceId == null ? null : other.fixedAssetInvoiceId.copy();
    }

    @Override
    public DealerCriteria copy() {
        return new DealerCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public TitleTypesFilter getTitle() {
        return title;
    }

    public void setTitle(TitleTypesFilter title) {
        this.title = title;
    }

    public StringFilter getDealerName() {
        return dealerName;
    }

    public void setDealerName(StringFilter dealerName) {
        this.dealerName = dealerName;
    }

    public StringFilter getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(StringFilter dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public StringFilter getDealerPhoneNumber() {
        return dealerPhoneNumber;
    }

    public void setDealerPhoneNumber(StringFilter dealerPhoneNumber) {
        this.dealerPhoneNumber = dealerPhoneNumber;
    }

    public StringFilter getDealerEmail() {
        return dealerEmail;
    }

    public void setDealerEmail(StringFilter dealerEmail) {
        this.dealerEmail = dealerEmail;
    }

    public StringFilter getBankName() {
        return bankName;
    }

    public void setBankName(StringFilter bankName) {
        this.bankName = bankName;
    }

    public StringFilter getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(StringFilter bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public StringFilter getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(StringFilter bankBranch) {
        this.bankBranch = bankBranch;
    }

    public StringFilter getBankSwiftCode() {
        return bankSwiftCode;
    }

    public void setBankSwiftCode(StringFilter bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public StringFilter getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public void setBankPhysicalAddress(StringFilter bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public StringFilter getDomicile() {
        return domicile;
    }

    public void setDomicile(StringFilter domicile) {
        this.domicile = domicile;
    }

    public StringFilter getTaxAuthorityRef() {
        return taxAuthorityRef;
    }

    public void setTaxAuthorityRef(StringFilter taxAuthorityRef) {
        this.taxAuthorityRef = taxAuthorityRef;
    }

    public LongFilter getFixedAssetInvoiceId() {
        return fixedAssetInvoiceId;
    }

    public void setFixedAssetInvoiceId(LongFilter fixedAssetInvoiceId) {
        this.fixedAssetInvoiceId = fixedAssetInvoiceId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DealerCriteria that = (DealerCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(dealerName, that.dealerName) &&
            Objects.equals(dealerAddress, that.dealerAddress) &&
            Objects.equals(dealerPhoneNumber, that.dealerPhoneNumber) &&
            Objects.equals(dealerEmail, that.dealerEmail) &&
            Objects.equals(bankName, that.bankName) &&
            Objects.equals(bankAccountNumber, that.bankAccountNumber) &&
            Objects.equals(bankBranch, that.bankBranch) &&
            Objects.equals(bankSwiftCode, that.bankSwiftCode) &&
            Objects.equals(bankPhysicalAddress, that.bankPhysicalAddress) &&
            Objects.equals(domicile, that.domicile) &&
            Objects.equals(taxAuthorityRef, that.taxAuthorityRef) &&
            Objects.equals(fixedAssetInvoiceId, that.fixedAssetInvoiceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        title,
        dealerName,
        dealerAddress,
        dealerPhoneNumber,
        dealerEmail,
        bankName,
        bankAccountNumber,
        bankBranch,
        bankSwiftCode,
        bankPhysicalAddress,
        domicile,
        taxAuthorityRef,
        fixedAssetInvoiceId
        );
    }

    @Override
    public String toString() {
        return "DealerCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (title != null ? "title=" + title + ", " : "") +
                (dealerName != null ? "dealerName=" + dealerName + ", " : "") +
                (dealerAddress != null ? "dealerAddress=" + dealerAddress + ", " : "") +
                (dealerPhoneNumber != null ? "dealerPhoneNumber=" + dealerPhoneNumber + ", " : "") +
                (dealerEmail != null ? "dealerEmail=" + dealerEmail + ", " : "") +
                (bankName != null ? "bankName=" + bankName + ", " : "") +
                (bankAccountNumber != null ? "bankAccountNumber=" + bankAccountNumber + ", " : "") +
                (bankBranch != null ? "bankBranch=" + bankBranch + ", " : "") +
                (bankSwiftCode != null ? "bankSwiftCode=" + bankSwiftCode + ", " : "") +
                (bankPhysicalAddress != null ? "bankPhysicalAddress=" + bankPhysicalAddress + ", " : "") +
                (domicile != null ? "domicile=" + domicile + ", " : "") +
                (taxAuthorityRef != null ? "taxAuthorityRef=" + taxAuthorityRef + ", " : "") +
                (fixedAssetInvoiceId != null ? "fixedAssetInvoiceId=" + fixedAssetInvoiceId + ", " : "") +
            "}";
    }

}
