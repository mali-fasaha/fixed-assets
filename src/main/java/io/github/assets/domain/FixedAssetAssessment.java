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

import io.github.assets.domain.enumeration.AssetCondition;

/**
 * A FixedAssetAssessment.
 */
@Entity
@Table(name = "fixed_asset_assessment")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fixedassetassessment")
public class FixedAssetAssessment implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_condition", nullable = false)
    private AssetCondition assetCondition;

    @Column(name = "assessment_date")
    private LocalDate assessmentDate;

    @Column(name = "assessment_remarks")
    private String assessmentRemarks;

    @Column(name = "name_of_assessing_staff")
    private String nameOfAssessingStaff;

    @Column(name = "name_of_assessment_contractor")
    private String nameOfAssessmentContractor;

    @Column(name = "current_service_outlet_code")
    private String currentServiceOutletCode;

    @Column(name = "current_physical_address")
    private String currentPhysicalAddress;

    @Column(name = "next_assessment_date")
    private LocalDate nextAssessmentDate;

    @Column(name = "name_of_user")
    private String nameOfUser;

    @Lob
    @Column(name = "fixed_asset_picture")
    private byte[] fixedAssetPicture;

    @Column(name = "fixed_asset_picture_content_type")
    private String fixedAssetPictureContentType;

    @Column(name = "fixed_asset_item_id")
    private Long fixedAssetItemId;

    @Column(name = "estimated_value", precision = 21, scale = 2)
    private BigDecimal estimatedValue;

    @Column(name = "estimated_useful_months")
    private Double estimatedUsefulMonths;

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

    public FixedAssetAssessment description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public AssetCondition getAssetCondition() {
        return assetCondition;
    }

    public FixedAssetAssessment assetCondition(AssetCondition assetCondition) {
        this.assetCondition = assetCondition;
        return this;
    }

    public void setAssetCondition(AssetCondition assetCondition) {
        this.assetCondition = assetCondition;
    }

    public LocalDate getAssessmentDate() {
        return assessmentDate;
    }

    public FixedAssetAssessment assessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
        return this;
    }

    public void setAssessmentDate(LocalDate assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public String getAssessmentRemarks() {
        return assessmentRemarks;
    }

    public FixedAssetAssessment assessmentRemarks(String assessmentRemarks) {
        this.assessmentRemarks = assessmentRemarks;
        return this;
    }

    public void setAssessmentRemarks(String assessmentRemarks) {
        this.assessmentRemarks = assessmentRemarks;
    }

    public String getNameOfAssessingStaff() {
        return nameOfAssessingStaff;
    }

    public FixedAssetAssessment nameOfAssessingStaff(String nameOfAssessingStaff) {
        this.nameOfAssessingStaff = nameOfAssessingStaff;
        return this;
    }

    public void setNameOfAssessingStaff(String nameOfAssessingStaff) {
        this.nameOfAssessingStaff = nameOfAssessingStaff;
    }

    public String getNameOfAssessmentContractor() {
        return nameOfAssessmentContractor;
    }

    public FixedAssetAssessment nameOfAssessmentContractor(String nameOfAssessmentContractor) {
        this.nameOfAssessmentContractor = nameOfAssessmentContractor;
        return this;
    }

    public void setNameOfAssessmentContractor(String nameOfAssessmentContractor) {
        this.nameOfAssessmentContractor = nameOfAssessmentContractor;
    }

    public String getCurrentServiceOutletCode() {
        return currentServiceOutletCode;
    }

    public FixedAssetAssessment currentServiceOutletCode(String currentServiceOutletCode) {
        this.currentServiceOutletCode = currentServiceOutletCode;
        return this;
    }

    public void setCurrentServiceOutletCode(String currentServiceOutletCode) {
        this.currentServiceOutletCode = currentServiceOutletCode;
    }

    public String getCurrentPhysicalAddress() {
        return currentPhysicalAddress;
    }

    public FixedAssetAssessment currentPhysicalAddress(String currentPhysicalAddress) {
        this.currentPhysicalAddress = currentPhysicalAddress;
        return this;
    }

    public void setCurrentPhysicalAddress(String currentPhysicalAddress) {
        this.currentPhysicalAddress = currentPhysicalAddress;
    }

    public LocalDate getNextAssessmentDate() {
        return nextAssessmentDate;
    }

    public FixedAssetAssessment nextAssessmentDate(LocalDate nextAssessmentDate) {
        this.nextAssessmentDate = nextAssessmentDate;
        return this;
    }

    public void setNextAssessmentDate(LocalDate nextAssessmentDate) {
        this.nextAssessmentDate = nextAssessmentDate;
    }

    public String getNameOfUser() {
        return nameOfUser;
    }

    public FixedAssetAssessment nameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
        return this;
    }

    public void setNameOfUser(String nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public byte[] getFixedAssetPicture() {
        return fixedAssetPicture;
    }

    public FixedAssetAssessment fixedAssetPicture(byte[] fixedAssetPicture) {
        this.fixedAssetPicture = fixedAssetPicture;
        return this;
    }

    public void setFixedAssetPicture(byte[] fixedAssetPicture) {
        this.fixedAssetPicture = fixedAssetPicture;
    }

    public String getFixedAssetPictureContentType() {
        return fixedAssetPictureContentType;
    }

    public FixedAssetAssessment fixedAssetPictureContentType(String fixedAssetPictureContentType) {
        this.fixedAssetPictureContentType = fixedAssetPictureContentType;
        return this;
    }

    public void setFixedAssetPictureContentType(String fixedAssetPictureContentType) {
        this.fixedAssetPictureContentType = fixedAssetPictureContentType;
    }

    public Long getFixedAssetItemId() {
        return fixedAssetItemId;
    }

    public FixedAssetAssessment fixedAssetItemId(Long fixedAssetItemId) {
        this.fixedAssetItemId = fixedAssetItemId;
        return this;
    }

    public void setFixedAssetItemId(Long fixedAssetItemId) {
        this.fixedAssetItemId = fixedAssetItemId;
    }

    public BigDecimal getEstimatedValue() {
        return estimatedValue;
    }

    public FixedAssetAssessment estimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
        return this;
    }

    public void setEstimatedValue(BigDecimal estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public Double getEstimatedUsefulMonths() {
        return estimatedUsefulMonths;
    }

    public FixedAssetAssessment estimatedUsefulMonths(Double estimatedUsefulMonths) {
        this.estimatedUsefulMonths = estimatedUsefulMonths;
        return this;
    }

    public void setEstimatedUsefulMonths(Double estimatedUsefulMonths) {
        this.estimatedUsefulMonths = estimatedUsefulMonths;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedAssetAssessment)) {
            return false;
        }
        return id != null && id.equals(((FixedAssetAssessment) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FixedAssetAssessment{" +
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
            ", fixedAssetPictureContentType='" + getFixedAssetPictureContentType() + "'" +
            ", fixedAssetItemId=" + getFixedAssetItemId() +
            ", estimatedValue=" + getEstimatedValue() +
            ", estimatedUsefulMonths=" + getEstimatedUsefulMonths() +
            "}";
    }
}
