package io.github.assets.service.dto;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.assets.domain.ScannedDocument} entity.
 */
public class ScannedDocumentDTO implements Serializable {

    private Long id;

    private String description;

    @Lob
    private byte[] approvalDocument;

    private String approvalDocumentContentType;
    @Lob
    private byte[] invoiceDocument;

    private String invoiceDocumentContentType;
    @Lob
    private byte[] lpoDocument;

    private String lpoDocumentContentType;
    @Lob
    private byte[] requisitionDocument;

    private String requisitionDocumentContentType;
    @Lob
    private byte[] otherDocuments;

    private String otherDocumentsContentType;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getApprovalDocument() {
        return approvalDocument;
    }

    public void setApprovalDocument(byte[] approvalDocument) {
        this.approvalDocument = approvalDocument;
    }

    public String getApprovalDocumentContentType() {
        return approvalDocumentContentType;
    }

    public void setApprovalDocumentContentType(String approvalDocumentContentType) {
        this.approvalDocumentContentType = approvalDocumentContentType;
    }

    public byte[] getInvoiceDocument() {
        return invoiceDocument;
    }

    public void setInvoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public String getInvoiceDocumentContentType() {
        return invoiceDocumentContentType;
    }

    public void setInvoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
    }

    public byte[] getLpoDocument() {
        return lpoDocument;
    }

    public void setLpoDocument(byte[] lpoDocument) {
        this.lpoDocument = lpoDocument;
    }

    public String getLpoDocumentContentType() {
        return lpoDocumentContentType;
    }

    public void setLpoDocumentContentType(String lpoDocumentContentType) {
        this.lpoDocumentContentType = lpoDocumentContentType;
    }

    public byte[] getRequisitionDocument() {
        return requisitionDocument;
    }

    public void setRequisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
    }

    public String getRequisitionDocumentContentType() {
        return requisitionDocumentContentType;
    }

    public void setRequisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
    }

    public byte[] getOtherDocuments() {
        return otherDocuments;
    }

    public void setOtherDocuments(byte[] otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    public String getOtherDocumentsContentType() {
        return otherDocumentsContentType;
    }

    public void setOtherDocumentsContentType(String otherDocumentsContentType) {
        this.otherDocumentsContentType = otherDocumentsContentType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ScannedDocumentDTO scannedDocumentDTO = (ScannedDocumentDTO) o;
        if (scannedDocumentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), scannedDocumentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ScannedDocumentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", approvalDocument='" + getApprovalDocument() + "'" +
            ", invoiceDocument='" + getInvoiceDocument() + "'" +
            ", lpoDocument='" + getLpoDocument() + "'" +
            ", requisitionDocument='" + getRequisitionDocument() + "'" +
            ", otherDocuments='" + getOtherDocuments() + "'" +
            "}";
    }
}
