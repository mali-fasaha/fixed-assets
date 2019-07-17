package io.github.assets.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
 * A FixedAssetInvoice.
 */
@Entity
@Table(name = "fixed_asset_invoice")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fixedassetinvoice")
public class FixedAssetInvoice implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "invoice_reference", nullable = false)
    private String invoiceReference;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "invoice_amount", precision = 21, scale = 2)
    private BigDecimal invoiceAmount;

    @Column(name = "is_proforma")
    private Boolean isProforma;

    @Column(name = "is_credit_note")
    private Boolean isCreditNote;

    @Lob
    @Column(name = "attachments")
    private byte[] attachments;

    @Column(name = "attachments_content_type")
    private String attachmentsContentType;

    @ManyToOne
    @JsonIgnoreProperties("fixedAssetInvoices")
    private Dealer dealer;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public FixedAssetInvoice invoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
        return this;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public FixedAssetInvoice invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public FixedAssetInvoice invoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
        return this;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Boolean isIsProforma() {
        return isProforma;
    }

    public FixedAssetInvoice isProforma(Boolean isProforma) {
        this.isProforma = isProforma;
        return this;
    }

    public void setIsProforma(Boolean isProforma) {
        this.isProforma = isProforma;
    }

    public Boolean isIsCreditNote() {
        return isCreditNote;
    }

    public FixedAssetInvoice isCreditNote(Boolean isCreditNote) {
        this.isCreditNote = isCreditNote;
        return this;
    }

    public void setIsCreditNote(Boolean isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public FixedAssetInvoice attachments(byte[] attachments) {
        this.attachments = attachments;
        return this;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public FixedAssetInvoice attachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
        return this;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public FixedAssetInvoice dealer(Dealer dealer) {
        this.dealer = dealer;
        return this;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedAssetInvoice)) {
            return false;
        }
        return id != null && id.equals(((FixedAssetInvoice) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FixedAssetInvoice{" +
            "id=" + getId() +
            ", invoiceReference='" + getInvoiceReference() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoiceAmount=" + getInvoiceAmount() +
            ", isProforma='" + isIsProforma() + "'" +
            ", isCreditNote='" + isIsCreditNote() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", attachmentsContentType='" + getAttachmentsContentType() + "'" +
            "}";
    }
}
