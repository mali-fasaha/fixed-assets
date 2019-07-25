package io.github.assets.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import io.github.assets.domain.enumeration.TitleTypes;

/**
 * A Dealer.
 */
@Entity
@Table(name = "dealer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "dealer")
public class Dealer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "title", nullable = false)
    private TitleTypes title;

    @NotNull
    @Column(name = "dealer_name", nullable = false)
    private String dealerName;

    @Column(name = "dealer_address")
    private String dealerAddress;

    @Column(name = "dealer_phone_number")
    private String dealerPhoneNumber;

    @Column(name = "dealer_email")
    private String dealerEmail;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "bank_account_number")
    private String bankAccountNumber;

    @Column(name = "bank_branch")
    private String bankBranch;

    @Column(name = "bank_swift_code")
    private String bankSwiftCode;

    @Column(name = "bank_physical_address")
    private String bankPhysicalAddress;

    @Column(name = "domicile")
    private String domicile;

    @Column(name = "tax_authority_ref")
    private String taxAuthorityRef;

    @OneToMany(mappedBy = "dealer")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<FixedAssetInvoice> fixedAssetInvoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TitleTypes getTitle() {
        return title;
    }

    public Dealer title(TitleTypes title) {
        this.title = title;
        return this;
    }

    public void setTitle(TitleTypes title) {
        this.title = title;
    }

    public String getDealerName() {
        return dealerName;
    }

    public Dealer dealerName(String dealerName) {
        this.dealerName = dealerName;
        return this;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public Dealer dealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
        return this;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerPhoneNumber() {
        return dealerPhoneNumber;
    }

    public Dealer dealerPhoneNumber(String dealerPhoneNumber) {
        this.dealerPhoneNumber = dealerPhoneNumber;
        return this;
    }

    public void setDealerPhoneNumber(String dealerPhoneNumber) {
        this.dealerPhoneNumber = dealerPhoneNumber;
    }

    public String getDealerEmail() {
        return dealerEmail;
    }

    public Dealer dealerEmail(String dealerEmail) {
        this.dealerEmail = dealerEmail;
        return this;
    }

    public void setDealerEmail(String dealerEmail) {
        this.dealerEmail = dealerEmail;
    }

    public String getBankName() {
        return bankName;
    }

    public Dealer bankName(String bankName) {
        this.bankName = bankName;
        return this;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public Dealer bankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
        return this;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public Dealer bankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
        return this;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankSwiftCode() {
        return bankSwiftCode;
    }

    public Dealer bankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
        return this;
    }

    public void setBankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public String getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public Dealer bankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
        return this;
    }

    public void setBankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public String getDomicile() {
        return domicile;
    }

    public Dealer domicile(String domicile) {
        this.domicile = domicile;
        return this;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getTaxAuthorityRef() {
        return taxAuthorityRef;
    }

    public Dealer taxAuthorityRef(String taxAuthorityRef) {
        this.taxAuthorityRef = taxAuthorityRef;
        return this;
    }

    public void setTaxAuthorityRef(String taxAuthorityRef) {
        this.taxAuthorityRef = taxAuthorityRef;
    }

    public Set<FixedAssetInvoice> getFixedAssetInvoices() {
        return fixedAssetInvoices;
    }

    public Dealer fixedAssetInvoices(Set<FixedAssetInvoice> fixedAssetInvoices) {
        this.fixedAssetInvoices = fixedAssetInvoices;
        return this;
    }

    public Dealer addFixedAssetInvoice(FixedAssetInvoice fixedAssetInvoice) {
        this.fixedAssetInvoices.add(fixedAssetInvoice);
        fixedAssetInvoice.setDealer(this);
        return this;
    }

    public Dealer removeFixedAssetInvoice(FixedAssetInvoice fixedAssetInvoice) {
        this.fixedAssetInvoices.remove(fixedAssetInvoice);
        fixedAssetInvoice.setDealer(null);
        return this;
    }

    public void setFixedAssetInvoices(Set<FixedAssetInvoice> fixedAssetInvoices) {
        this.fixedAssetInvoices = fixedAssetInvoices;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Dealer)) {
            return false;
        }
        return id != null && id.equals(((Dealer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Dealer{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", dealerName='" + getDealerName() + "'" +
            ", dealerAddress='" + getDealerAddress() + "'" +
            ", dealerPhoneNumber='" + getDealerPhoneNumber() + "'" +
            ", dealerEmail='" + getDealerEmail() + "'" +
            ", bankName='" + getBankName() + "'" +
            ", bankAccountNumber='" + getBankAccountNumber() + "'" +
            ", bankBranch='" + getBankBranch() + "'" +
            ", bankSwiftCode='" + getBankSwiftCode() + "'" +
            ", bankPhysicalAddress='" + getBankPhysicalAddress() + "'" +
            ", domicile='" + getDomicile() + "'" +
            ", taxAuthorityRef='" + getTaxAuthorityRef() + "'" +
            "}";
    }
}
