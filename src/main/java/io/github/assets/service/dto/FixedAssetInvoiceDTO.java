package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.assets.domain.FixedAssetInvoice} entity.
 */
public class FixedAssetInvoiceDTO implements Serializable {

    private Long id;

    @NotNull
    private String invoiceReference;

    private LocalDate invoiceDate;

    private BigDecimal invoiceAmount;

    private Boolean isProforma;

    private Boolean isCreditNote;

    @Lob
    private byte[] attachments;

    private String attachmentsContentType;

    private Long dealerId;

    private String dealerDealerName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getInvoiceReference() {
        return invoiceReference;
    }

    public void setInvoiceReference(String invoiceReference) {
        this.invoiceReference = invoiceReference;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public BigDecimal getInvoiceAmount() {
        return invoiceAmount;
    }

    public void setInvoiceAmount(BigDecimal invoiceAmount) {
        this.invoiceAmount = invoiceAmount;
    }

    public Boolean isIsProforma() {
        return isProforma;
    }

    public void setIsProforma(Boolean isProforma) {
        this.isProforma = isProforma;
    }

    public Boolean isIsCreditNote() {
        return isCreditNote;
    }

    public void setIsCreditNote(Boolean isCreditNote) {
        this.isCreditNote = isCreditNote;
    }

    public byte[] getAttachments() {
        return attachments;
    }

    public void setAttachments(byte[] attachments) {
        this.attachments = attachments;
    }

    public String getAttachmentsContentType() {
        return attachmentsContentType;
    }

    public void setAttachmentsContentType(String attachmentsContentType) {
        this.attachmentsContentType = attachmentsContentType;
    }

    public Long getDealerId() {
        return dealerId;
    }

    public void setDealerId(Long dealerId) {
        this.dealerId = dealerId;
    }

    public String getDealerDealerName() {
        return dealerDealerName;
    }

    public void setDealerDealerName(String dealerDealerName) {
        this.dealerDealerName = dealerDealerName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = (FixedAssetInvoiceDTO) o;
        if (fixedAssetInvoiceDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedAssetInvoiceDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedAssetInvoiceDTO{" +
            "id=" + getId() +
            ", invoiceReference='" + getInvoiceReference() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", invoiceAmount=" + getInvoiceAmount() +
            ", isProforma='" + isIsProforma() + "'" +
            ", isCreditNote='" + isIsCreditNote() + "'" +
            ", attachments='" + getAttachments() + "'" +
            ", dealer=" + getDealerId() +
            ", dealer='" + getDealerDealerName() + "'" +
            "}";
    }
}
