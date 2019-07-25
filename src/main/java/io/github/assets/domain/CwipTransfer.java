package io.github.assets.domain;


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
 * A CwipTransfer.
 */
@Entity
@Table(name = "cwip_transfer")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "cwiptransfer")
public class CwipTransfer implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "transfer_month", nullable = false)
    private LocalDate transferMonth;

    @NotNull
    @Column(name = "asset_serial_tag", nullable = false)
    private String assetSerialTag;

    @NotNull
    @Column(name = "service_outlet_code", nullable = false)
    private String serviceOutletCode;

    @NotNull
    @Column(name = "transfer_transaction_id", nullable = false)
    private Long transferTransactionId;

    @NotNull
    @Column(name = "asset_category_id", nullable = false)
    private Long assetCategoryId;

    @NotNull
    @Column(name = "cwip_transaction_id", nullable = false)
    private Long cwipTransactionId;

    @NotNull
    @Column(name = "transfer_details", nullable = false)
    private String transferDetails;

    @NotNull
    @Column(name = "transfer_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal transferAmount;

    @Column(name = "dealer_id")
    private Long dealerId;

    @Column(name = "transaction_invoice_id")
    private Long transactionInvoiceId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransferMonth() {
        return transferMonth;
    }

    public CwipTransfer transferMonth(LocalDate transferMonth) {
        this.transferMonth = transferMonth;
        return this;
    }

    public void setTransferMonth(LocalDate transferMonth) {
        this.transferMonth = transferMonth;
    }

    public String getAssetSerialTag() {
        return assetSerialTag;
    }

    public CwipTransfer assetSerialTag(String assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
        return this;
    }

    public void setAssetSerialTag(String assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public CwipTransfer serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getTransferTransactionId() {
        return transferTransactionId;
    }

    public CwipTransfer transferTransactionId(Long transferTransactionId) {
        this.transferTransactionId = transferTransactionId;
        return this;
    }

    public void setTransferTransactionId(Long transferTransactionId) {
        this.transferTransactionId = transferTransactionId;
    }

    public Long getAssetCategoryId() {
        return assetCategoryId;
    }

    public CwipTransfer assetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
        return this;
    }

    public void setAssetCategoryId(Long assetCategoryId) {
        this.assetCategoryId = assetCategoryId;
    }

    public Long getCwipTransactionId() {
        return cwipTransactionId;
    }

    public CwipTransfer cwipTransactionId(Long cwipTransactionId) {
        this.cwipTransactionId = cwipTransactionId;
        return this;
    }

    public void setCwipTransactionId(Long cwipTransactionId) {
        this.cwipTransactionId = cwipTransactionId;
    }

    public String getTransferDetails() {
        return transferDetails;
    }

    public CwipTransfer transferDetails(String transferDetails) {
        this.transferDetails = transferDetails;
        return this;
    }

    public void setTransferDetails(String transferDetails) {
        this.transferDetails = transferDetails;
    }

    public BigDecimal getTransferAmount() {
        return transferAmount;
    }

    public CwipTransfer transferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
        return this;
    }

    public void setTransferAmount(BigDecimal transferAmount) {
        this.transferAmount = transferAmount;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public CwipTransfer dealerId(Long dealerId) {
        this.dealerId = dealerId;
        return this;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public Long getTransactionInvoiceId() {
        return transactionInvoiceId;
    }

    public CwipTransfer transactionInvoiceId(Long transactionInvoiceId) {
        this.transactionInvoiceId = transactionInvoiceId;
        return this;
    }

    public void setTransactionInvoiceId(Long transactionInvoiceId) {
        this.transactionInvoiceId = transactionInvoiceId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CwipTransfer)) {
            return false;
        }
        return id != null && id.equals(((CwipTransfer) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CwipTransfer{" +
            "id=" + getId() +
            ", transferMonth='" + getTransferMonth() + "'" +
            ", assetSerialTag='" + getAssetSerialTag() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", transferTransactionId=" + getTransferTransactionId() +
            ", assetCategoryId=" + getAssetCategoryId() +
            ", cwipTransactionId=" + getCwipTransactionId() +
            ", transferDetails='" + getTransferDetails() + "'" +
            ", transferAmount=" + getTransferAmount() +
            ", dealerId=" + getDealerId() +
            ", transactionInvoiceId=" + getTransactionInvoiceId() +
            "}";
    }
}
