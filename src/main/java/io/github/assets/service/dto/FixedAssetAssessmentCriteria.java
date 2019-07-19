package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.assets.domain.enumeration.AssetCondition;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.FixedAssetAssessment} entity. This class is used
 * in {@link io.github.assets.web.rest.FixedAssetAssessmentResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fixed-asset-assessments?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FixedAssetAssessmentCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AssetCondition
     */
    public static class AssetConditionFilter extends Filter<AssetCondition> {

        public AssetConditionFilter() {
        }

        public AssetConditionFilter(AssetConditionFilter filter) {
            super(filter);
        }

        @Override
        public AssetConditionFilter copy() {
            return new AssetConditionFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private AssetConditionFilter assetCondition;

    private LocalDateFilter assessmentDate;

    private StringFilter assessmentRemarks;

    private StringFilter nameOfAssessingStaff;

    private StringFilter nameOfAssessmentContractor;

    private StringFilter currentServiceOutletCode;

    private StringFilter currentPhysicalAddress;

    private LocalDateFilter nextAssessmentDate;

    private StringFilter nameOfUser;

    private LongFilter fixedAssetItemId;

    private BigDecimalFilter estimatedValue;

    private DoubleFilter estimatedUsefulMonths;

    public FixedAssetAssessmentCriteria(){
    }

    public FixedAssetAssessmentCriteria(FixedAssetAssessmentCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.assetCondition = other.assetCondition == null ? null : other.assetCondition.copy();
        this.assessmentDate = other.assessmentDate == null ? null : other.assessmentDate.copy();
        this.assessmentRemarks = other.assessmentRemarks == null ? null : other.assessmentRemarks.copy();
        this.nameOfAssessingStaff = other.nameOfAssessingStaff == null ? null : other.nameOfAssessingStaff.copy();
        this.nameOfAssessmentContractor = other.nameOfAssessmentContractor == null ? null : other.nameOfAssessmentContractor.copy();
        this.currentServiceOutletCode = other.currentServiceOutletCode == null ? null : other.currentServiceOutletCode.copy();
        this.currentPhysicalAddress = other.currentPhysicalAddress == null ? null : other.currentPhysicalAddress.copy();
        this.nextAssessmentDate = other.nextAssessmentDate == null ? null : other.nextAssessmentDate.copy();
        this.nameOfUser = other.nameOfUser == null ? null : other.nameOfUser.copy();
        this.fixedAssetItemId = other.fixedAssetItemId == null ? null : other.fixedAssetItemId.copy();
        this.estimatedValue = other.estimatedValue == null ? null : other.estimatedValue.copy();
        this.estimatedUsefulMonths = other.estimatedUsefulMonths == null ? null : other.estimatedUsefulMonths.copy();
    }

    @Override
    public FixedAssetAssessmentCriteria copy() {
        return new FixedAssetAssessmentCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public AssetConditionFilter getAssetCondition() {
        return assetCondition;
    }

    public void setAssetCondition(AssetConditionFilter assetCondition) {
        this.assetCondition = assetCondition;
    }

    public LocalDateFilter getAssessmentDate() {
        return assessmentDate;
    }

    public void setAssessmentDate(LocalDateFilter assessmentDate) {
        this.assessmentDate = assessmentDate;
    }

    public StringFilter getAssessmentRemarks() {
        return assessmentRemarks;
    }

    public void setAssessmentRemarks(StringFilter assessmentRemarks) {
        this.assessmentRemarks = assessmentRemarks;
    }

    public StringFilter getNameOfAssessingStaff() {
        return nameOfAssessingStaff;
    }

    public void setNameOfAssessingStaff(StringFilter nameOfAssessingStaff) {
        this.nameOfAssessingStaff = nameOfAssessingStaff;
    }

    public StringFilter getNameOfAssessmentContractor() {
        return nameOfAssessmentContractor;
    }

    public void setNameOfAssessmentContractor(StringFilter nameOfAssessmentContractor) {
        this.nameOfAssessmentContractor = nameOfAssessmentContractor;
    }

    public StringFilter getCurrentServiceOutletCode() {
        return currentServiceOutletCode;
    }

    public void setCurrentServiceOutletCode(StringFilter currentServiceOutletCode) {
        this.currentServiceOutletCode = currentServiceOutletCode;
    }

    public StringFilter getCurrentPhysicalAddress() {
        return currentPhysicalAddress;
    }

    public void setCurrentPhysicalAddress(StringFilter currentPhysicalAddress) {
        this.currentPhysicalAddress = currentPhysicalAddress;
    }

    public LocalDateFilter getNextAssessmentDate() {
        return nextAssessmentDate;
    }

    public void setNextAssessmentDate(LocalDateFilter nextAssessmentDate) {
        this.nextAssessmentDate = nextAssessmentDate;
    }

    public StringFilter getNameOfUser() {
        return nameOfUser;
    }

    public void setNameOfUser(StringFilter nameOfUser) {
        this.nameOfUser = nameOfUser;
    }

    public LongFilter getFixedAssetItemId() {
        return fixedAssetItemId;
    }

    public void setFixedAssetItemId(LongFilter fixedAssetItemId) {
        this.fixedAssetItemId = fixedAssetItemId;
    }

    public BigDecimalFilter getEstimatedValue() {
        return estimatedValue;
    }

    public void setEstimatedValue(BigDecimalFilter estimatedValue) {
        this.estimatedValue = estimatedValue;
    }

    public DoubleFilter getEstimatedUsefulMonths() {
        return estimatedUsefulMonths;
    }

    public void setEstimatedUsefulMonths(DoubleFilter estimatedUsefulMonths) {
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
        final FixedAssetAssessmentCriteria that = (FixedAssetAssessmentCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(assetCondition, that.assetCondition) &&
            Objects.equals(assessmentDate, that.assessmentDate) &&
            Objects.equals(assessmentRemarks, that.assessmentRemarks) &&
            Objects.equals(nameOfAssessingStaff, that.nameOfAssessingStaff) &&
            Objects.equals(nameOfAssessmentContractor, that.nameOfAssessmentContractor) &&
            Objects.equals(currentServiceOutletCode, that.currentServiceOutletCode) &&
            Objects.equals(currentPhysicalAddress, that.currentPhysicalAddress) &&
            Objects.equals(nextAssessmentDate, that.nextAssessmentDate) &&
            Objects.equals(nameOfUser, that.nameOfUser) &&
            Objects.equals(fixedAssetItemId, that.fixedAssetItemId) &&
            Objects.equals(estimatedValue, that.estimatedValue) &&
            Objects.equals(estimatedUsefulMonths, that.estimatedUsefulMonths);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        assetCondition,
        assessmentDate,
        assessmentRemarks,
        nameOfAssessingStaff,
        nameOfAssessmentContractor,
        currentServiceOutletCode,
        currentPhysicalAddress,
        nextAssessmentDate,
        nameOfUser,
        fixedAssetItemId,
        estimatedValue,
        estimatedUsefulMonths
        );
    }

    @Override
    public String toString() {
        return "FixedAssetAssessmentCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (assetCondition != null ? "assetCondition=" + assetCondition + ", " : "") +
                (assessmentDate != null ? "assessmentDate=" + assessmentDate + ", " : "") +
                (assessmentRemarks != null ? "assessmentRemarks=" + assessmentRemarks + ", " : "") +
                (nameOfAssessingStaff != null ? "nameOfAssessingStaff=" + nameOfAssessingStaff + ", " : "") +
                (nameOfAssessmentContractor != null ? "nameOfAssessmentContractor=" + nameOfAssessmentContractor + ", " : "") +
                (currentServiceOutletCode != null ? "currentServiceOutletCode=" + currentServiceOutletCode + ", " : "") +
                (currentPhysicalAddress != null ? "currentPhysicalAddress=" + currentPhysicalAddress + ", " : "") +
                (nextAssessmentDate != null ? "nextAssessmentDate=" + nextAssessmentDate + ", " : "") +
                (nameOfUser != null ? "nameOfUser=" + nameOfUser + ", " : "") +
                (fixedAssetItemId != null ? "fixedAssetItemId=" + fixedAssetItemId + ", " : "") +
                (estimatedValue != null ? "estimatedValue=" + estimatedValue + ", " : "") +
                (estimatedUsefulMonths != null ? "estimatedUsefulMonths=" + estimatedUsefulMonths + ", " : "") +
            "}";
    }

}
