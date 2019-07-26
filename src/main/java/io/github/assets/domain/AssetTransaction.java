package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A AssetTransaction.
 */
@Entity
@Table(name = "asset_transaction")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "assettransaction")
public class AssetTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "transaction_reference", nullable = false)
    private String transactionReference;

    @NotNull
    @Column(name = "transaction_date", nullable = false)
    private LocalDate transactionDate;

    @Column(name = "scanned_document_id")
    private Long scannedDocumentId;

    @Column(name = "transaction_approval_id")
    private Long transactionApprovalId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public AssetTransaction transactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
        return this;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public AssetTransaction transactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
        return this;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public AssetTransaction scannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
        return this;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public Long getTransactionApprovalId() {
        return transactionApprovalId;
    }

    public AssetTransaction transactionApprovalId(Long transactionApprovalId) {
        this.transactionApprovalId = transactionApprovalId;
        return this;
    }

    public void setTransactionApprovalId(Long transactionApprovalId) {
        this.transactionApprovalId = transactionApprovalId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetTransaction)) {
            return false;
        }
        return id != null && id.equals(((AssetTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssetTransaction{" +
            "id=" + getId() +
            ", transactionReference='" + getTransactionReference() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", scannedDocumentId=" + getScannedDocumentId() +
            ", transactionApprovalId=" + getTransactionApprovalId() +
            "}";
    }
}
