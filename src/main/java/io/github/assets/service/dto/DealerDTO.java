package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.assets.domain.enumeration.TitleTypes;

/**
 * A DTO for the {@link io.github.assets.domain.Dealer} entity.
 */
public class DealerDTO implements Serializable {

    private Long id;

    @NotNull
    private TitleTypes title;

    @NotNull
    private String dealerName;

    private String dealerAddress;

    private String dealerPhoneNumber;

    private String dealerEmail;

    private String bankName;

    private String bankAccountNumber;

    private String bankBranch;

    private String bankSwiftCode;

    private String bankPhysicalAddress;

    private String domicile;

    private String taxAuthorityRef;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TitleTypes getTitle() {
        return title;
    }

    public void setTitle(TitleTypes title) {
        this.title = title;
    }

    public String getDealerName() {
        return dealerName;
    }

    public void setDealerName(String dealerName) {
        this.dealerName = dealerName;
    }

    public String getDealerAddress() {
        return dealerAddress;
    }

    public void setDealerAddress(String dealerAddress) {
        this.dealerAddress = dealerAddress;
    }

    public String getDealerPhoneNumber() {
        return dealerPhoneNumber;
    }

    public void setDealerPhoneNumber(String dealerPhoneNumber) {
        this.dealerPhoneNumber = dealerPhoneNumber;
    }

    public String getDealerEmail() {
        return dealerEmail;
    }

    public void setDealerEmail(String dealerEmail) {
        this.dealerEmail = dealerEmail;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getBankSwiftCode() {
        return bankSwiftCode;
    }

    public void setBankSwiftCode(String bankSwiftCode) {
        this.bankSwiftCode = bankSwiftCode;
    }

    public String getBankPhysicalAddress() {
        return bankPhysicalAddress;
    }

    public void setBankPhysicalAddress(String bankPhysicalAddress) {
        this.bankPhysicalAddress = bankPhysicalAddress;
    }

    public String getDomicile() {
        return domicile;
    }

    public void setDomicile(String domicile) {
        this.domicile = domicile;
    }

    public String getTaxAuthorityRef() {
        return taxAuthorityRef;
    }

    public void setTaxAuthorityRef(String taxAuthorityRef) {
        this.taxAuthorityRef = taxAuthorityRef;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DealerDTO dealerDTO = (DealerDTO) o;
        if (dealerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dealerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DealerDTO{" +
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
