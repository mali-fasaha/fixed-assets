package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.AssetTransaction} entity.
 */
public class AssetTransactionDTO implements Serializable {

    private Long id;

    @NotNull
    private String transactionReference;

    @NotNull
    private LocalDate transactionDate;

    private Long scannedDocumentId;

    private Long transactionApprovalId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTransactionReference() {
        return transactionReference;
    }

    public void setTransactionReference(String transactionReference) {
        this.transactionReference = transactionReference;
    }

    public LocalDate getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(LocalDate transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Long getScannedDocumentId() {
        return scannedDocumentId;
    }

    public void setScannedDocumentId(Long scannedDocumentId) {
        this.scannedDocumentId = scannedDocumentId;
    }

    public Long getTransactionApprovalId() {
        return transactionApprovalId;
    }

    public void setTransactionApprovalId(Long transactionApprovalId) {
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

        AssetTransactionDTO assetTransactionDTO = (AssetTransactionDTO) o;
        if (assetTransactionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetTransactionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetTransactionDTO{" +
            "id=" + getId() +
            ", transactionReference='" + getTransactionReference() + "'" +
            ", transactionDate='" + getTransactionDate() + "'" +
            ", scannedDocumentId=" + getScannedDocumentId() +
            ", transactionApprovalId=" + getTransactionApprovalId() +
            "}";
    }
}
