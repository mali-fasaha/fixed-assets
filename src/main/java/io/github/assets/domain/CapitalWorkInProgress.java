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
 * A CapitalWorkInProgress.
 */
@Entity
@Table(name = "capital_work_in_progress")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "capitalworkinprogress")
public class CapitalWorkInProgress implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "transaction_month", nullable = false)
    private LocalDate transactionMonth;

    @NotNull
    @Column(name = "asset_serial_tag", nullable = false)
    private String assetSerialTag;

    @NotNull
    @Column(name = "service_outlet_code", nullable = false)
    private String serviceOutletCode;

    @NotNull
    @Column(name = "transaction_id", nullable = false)
    private Long transactionId;

    @NotNull
    @Column(name = "transaction_details", nullable = false)
    private String transactionDetails;

    @NotNull
    @Column(name = "transaction_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal transactionAmount;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getTransactionMonth() {
        return transactionMonth;
    }

    public CapitalWorkInProgress transactionMonth(LocalDate transactionMonth) {
        this.transactionMonth = transactionMonth;
        return this;
    }

    public void setTransactionMonth(LocalDate transactionMonth) {
        this.transactionMonth = transactionMonth;
    }

    public String getAssetSerialTag() {
        return assetSerialTag;
    }

    public CapitalWorkInProgress assetSerialTag(String assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
        return this;
    }

    public void setAssetSerialTag(String assetSerialTag) {
        this.assetSerialTag = assetSerialTag;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public CapitalWorkInProgress serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public Long getTransactionId() {
        return transactionId;
    }

    public CapitalWorkInProgress transactionId(Long transactionId) {
        this.transactionId = transactionId;
        return this;
    }

    public void setTransactionId(Long transactionId) {
        this.transactionId = transactionId;
    }

    public String getTransactionDetails() {
        return transactionDetails;
    }

    public CapitalWorkInProgress transactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
        return this;
    }

    public void setTransactionDetails(String transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public BigDecimal getTransactionAmount() {
        return transactionAmount;
    }

    public CapitalWorkInProgress transactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
        return this;
    }

    public void setTransactionAmount(BigDecimal transactionAmount) {
        this.transactionAmount = transactionAmount;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CapitalWorkInProgress)) {
            return false;
        }
        return id != null && id.equals(((CapitalWorkInProgress) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "CapitalWorkInProgress{" +
            "id=" + getId() +
            ", transactionMonth='" + getTransactionMonth() + "'" +
            ", assetSerialTag='" + getAssetSerialTag() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", transactionId=" + getTransactionId() +
            ", transactionDetails='" + getTransactionDetails() + "'" +
            ", transactionAmount=" + getTransactionAmount() +
            "}";
    }
}
