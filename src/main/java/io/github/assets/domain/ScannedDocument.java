package io.github.assets.domain;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;

/**
 * A ScannedDocument.
 */
@Entity
@Table(name = "scanned_document")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "scanneddocument")
public class ScannedDocument implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @Lob
    @Column(name = "approval_document")
    private byte[] approvalDocument;

    @Column(name = "approval_document_content_type")
    private String approvalDocumentContentType;

    @Lob
    @Column(name = "invoice_document")
    private byte[] invoiceDocument;

    @Column(name = "invoice_document_content_type")
    private String invoiceDocumentContentType;

    @Lob
    @Column(name = "lpo_document")
    private byte[] lpoDocument;

    @Column(name = "lpo_document_content_type")
    private String lpoDocumentContentType;

    @Lob
    @Column(name = "requisition_document")
    private byte[] requisitionDocument;

    @Column(name = "requisition_document_content_type")
    private String requisitionDocumentContentType;

    @Lob
    @Column(name = "other_documents")
    private byte[] otherDocuments;

    @Column(name = "other_documents_content_type")
    private String otherDocumentsContentType;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public ScannedDocument description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getApprovalDocument() {
        return approvalDocument;
    }

    public ScannedDocument approvalDocument(byte[] approvalDocument) {
        this.approvalDocument = approvalDocument;
        return this;
    }

    public void setApprovalDocument(byte[] approvalDocument) {
        this.approvalDocument = approvalDocument;
    }

    public String getApprovalDocumentContentType() {
        return approvalDocumentContentType;
    }

    public ScannedDocument approvalDocumentContentType(String approvalDocumentContentType) {
        this.approvalDocumentContentType = approvalDocumentContentType;
        return this;
    }

    public void setApprovalDocumentContentType(String approvalDocumentContentType) {
        this.approvalDocumentContentType = approvalDocumentContentType;
    }

    public byte[] getInvoiceDocument() {
        return invoiceDocument;
    }

    public ScannedDocument invoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
        return this;
    }

    public void setInvoiceDocument(byte[] invoiceDocument) {
        this.invoiceDocument = invoiceDocument;
    }

    public String getInvoiceDocumentContentType() {
        return invoiceDocumentContentType;
    }

    public ScannedDocument invoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
        return this;
    }

    public void setInvoiceDocumentContentType(String invoiceDocumentContentType) {
        this.invoiceDocumentContentType = invoiceDocumentContentType;
    }

    public byte[] getLpoDocument() {
        return lpoDocument;
    }

    public ScannedDocument lpoDocument(byte[] lpoDocument) {
        this.lpoDocument = lpoDocument;
        return this;
    }

    public void setLpoDocument(byte[] lpoDocument) {
        this.lpoDocument = lpoDocument;
    }

    public String getLpoDocumentContentType() {
        return lpoDocumentContentType;
    }

    public ScannedDocument lpoDocumentContentType(String lpoDocumentContentType) {
        this.lpoDocumentContentType = lpoDocumentContentType;
        return this;
    }

    public void setLpoDocumentContentType(String lpoDocumentContentType) {
        this.lpoDocumentContentType = lpoDocumentContentType;
    }

    public byte[] getRequisitionDocument() {
        return requisitionDocument;
    }

    public ScannedDocument requisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
        return this;
    }

    public void setRequisitionDocument(byte[] requisitionDocument) {
        this.requisitionDocument = requisitionDocument;
    }

    public String getRequisitionDocumentContentType() {
        return requisitionDocumentContentType;
    }

    public ScannedDocument requisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
        return this;
    }

    public void setRequisitionDocumentContentType(String requisitionDocumentContentType) {
        this.requisitionDocumentContentType = requisitionDocumentContentType;
    }

    public byte[] getOtherDocuments() {
        return otherDocuments;
    }

    public ScannedDocument otherDocuments(byte[] otherDocuments) {
        this.otherDocuments = otherDocuments;
        return this;
    }

    public void setOtherDocuments(byte[] otherDocuments) {
        this.otherDocuments = otherDocuments;
    }

    public String getOtherDocumentsContentType() {
        return otherDocumentsContentType;
    }

    public ScannedDocument otherDocumentsContentType(String otherDocumentsContentType) {
        this.otherDocumentsContentType = otherDocumentsContentType;
        return this;
    }

    public void setOtherDocumentsContentType(String otherDocumentsContentType) {
        this.otherDocumentsContentType = otherDocumentsContentType;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ScannedDocument)) {
            return false;
        }
        return id != null && id.equals(((ScannedDocument) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ScannedDocument{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", approvalDocument='" + getApprovalDocument() + "'" +
            ", approvalDocumentContentType='" + getApprovalDocumentContentType() + "'" +
            ", invoiceDocument='" + getInvoiceDocument() + "'" +
            ", invoiceDocumentContentType='" + getInvoiceDocumentContentType() + "'" +
            ", lpoDocument='" + getLpoDocument() + "'" +
            ", lpoDocumentContentType='" + getLpoDocumentContentType() + "'" +
            ", requisitionDocument='" + getRequisitionDocument() + "'" +
            ", requisitionDocumentContentType='" + getRequisitionDocumentContentType() + "'" +
            ", otherDocuments='" + getOtherDocuments() + "'" +
            ", otherDocumentsContentType='" + getOtherDocumentsContentType() + "'" +
            "}";
    }
}
