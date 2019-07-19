package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Lob;
import io.github.assets.domain.enumeration.AssetCondition;

/**
 * A DTO for the {@link io.github.assets.domain.FixedAssetAssessment} entity.
 */
public class FixedAssetAssessmentDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private AssetCondition assetCondition;

    private LocalDate assessmentDate;

    private String assessmentRemarks;

    private String nameOfAssessingStaff;

    private String nameOfAssessmentContractor;

    private String currentServiceOutletCode;

    private String currentPhysicalAddress;

    private LocalDate nextAssessmentDate;

    private String nameOfUser;

    @Lob
    private byte[] fixedAssetPicture;

    private String fixedAssetPictureContentType;
    private Long fixedAssetItemId;

    private BigDecimal estimatedValue;

    private Double estimatedUsefulMonths;


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

    public AssetCondition getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(AssetCondition assetCondition) {
        this.assetCondition = assetCondition;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentRemarks() {
        return assessmentRemarks;
    }

    public void setAssessmentRemarks(String assessmentRemarks) {
        this.assessmentRemarks = assessmentRemarks;
    }

    public String getNameOfAssessingStaff() {
        return nameOfAssessingStaff;
    }

    public void setNameOfAssessingStaff(String nameOfAssessingStaff) {
        this.nameOfAssessingStaff = nameOfAssessingStaff;
    }

    public String getNameOfAssessmentContractor() {
        return nameOfAssessmentContractor;
    }

    public void setNameOfAssessmentContractor(String nameOfAssessmentContractor) {
        this.nameOfAssessmentContractor = nameOfAssessmentContractor;
    }

    public String getCurrentServiceOutletCode() {
        return currentServiceOutletCode;
    }

    public void setCurrentServiceOutletCode(String currentServiceOutletCode) {
        this.currentServiceOutletCode = currentServiceOutletCode;
    }

    public String getCurrentPhysicalAddress() {
        return currentPhysicalAddress;
    }

    public void setCurrentPhysicalAddress(String currentPhysicalAddress) {
        this.currentPhysicalAddress = currentPhysicalAddress;
    }

    public LocalDate getNextAssessmentDate() {
        return nextAssessmentDate;
    }

    public void setNextAssessmentDate(LocalDate nextAssessmentDate) {
        this.nextAssessmentDate = nextAssessmentDate;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public byte[] getFixedAssetPicture() {
        return fixedAssetPicture;
    }

    public void setFixedAssetPicture(byte[] fixedAssetPicture) {
        this.fixedAssetPicture = fixedAssetPicture;
    }

    public String getFixedAssetPictureContentType() {
        return fixedAssetPictureContentType;
    }

    public void setFixedAssetPictureContentType(String fixedAssetPictureContentType) {
        this.fixedAssetPictureContentType = fixedAssetPictureContentType;
    }

    public Long getFixedAssetItemId() {
        return fixedAssetItemId;
    }

    public void setFixedAssetItemId(Long fixedAssetItemId) {
        this.fixedAssetItemId = fixedAssetItemId;
    }

    public BigDecimal getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public Double getEstimatedUsefulMonths() {
        return estimatedUsefulMonths;
    }

    public void setEstimatedUsefulMonths(Double estimatedUsefulMonths) {
        this.estimatedUsefulMonths = estimatedUsefulMonths;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        FixedAssetAssessmentDTO fixedAssetAssessmentDTO = (FixedAssetAssessmentDTO) o;
        if (fixedAssetAssessmentDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedAssetAssessmentDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedAssetAssessmentDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", assetCondition='" + getAssetCondition() + "'" +
            ", assessmentDate='" + getAssessmentDate() + "'" +
            ", assessmentRemarks='" + getAssessmentRemarks() + "'" +
            ", nameOfAssessingStaff='" + getNameOfAssessingStaff() + "'" +
            ", nameOfAssessmentContractor='" + getNameOfAssessmentContractor() + "'" +
            ", currentServiceOutletCode='" + getCurrentServiceOutletCode() + "'" +
            ", currentPhysicalAddress='" + getCurrentPhysicalAddress() + "'" +
            ", nextAssessmentDate='" + getNextAssessmentDate() + "'" +
            ", nameOfUser='" + getNameOfUser() + "'" +
            ", fixedAssetPicture='" + getFixedAssetPicture() + "'" +
            ", fixedAssetItemId=" + getFixedAssetItemId() +
            ", estimatedValue=" + getEstimatedValue() +
            ", estimatedUsefulMonths=" + getEstimatedUsefulMonths() +
            "}";
    }
}
