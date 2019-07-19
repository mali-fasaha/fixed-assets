package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.FixedAssetCategory} entity. This class is used
 * in {@link io.github.assets.web.rest.FixedAssetCategoryResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /fixed-asset-categories?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FixedAssetCategoryCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter categoryCode;

    private StringFilter categoryName;

    private StringFilter categoryDescription;

    private LongFilter depreciationRegimeId;

    public FixedAssetCategoryCriteria(){
    }

    public FixedAssetCategoryCriteria(FixedAssetCategoryCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.categoryCode = other.categoryCode == null ? null : other.categoryCode.copy();
        this.categoryName = other.categoryName == null ? null : other.categoryName.copy();
        this.categoryDescription = other.categoryDescription == null ? null : other.categoryDescription.copy();
        this.depreciationRegimeId = other.depreciationRegimeId == null ? null : other.depreciationRegimeId.copy();
    }

    @Override
    public FixedAssetCategoryCriteria copy() {
        return new FixedAssetCategoryCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(StringFilter categoryCode) {
        this.categoryCode = categoryCode;
    }

    public StringFilter getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(StringFilter categoryName) {
        this.categoryName = categoryName;
    }

    public StringFilter getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(StringFilter categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public LongFilter getDepreciationRegimeId() {
        return depreciationRegimeId;
    }

    public void setDepreciationRegimeId(LongFilter depreciationRegimeId) {
        this.depreciationRegimeId = depreciationRegimeId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FixedAssetCategoryCriteria that = (FixedAssetCategoryCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(categoryCode, that.categoryCode) &&
            Objects.equals(categoryName, that.categoryName) &&
            Objects.equals(categoryDescription, that.categoryDescription) &&
            Objects.equals(depreciationRegimeId, that.depreciationRegimeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        categoryCode,
        categoryName,
        categoryDescription,
        depreciationRegimeId
        );
    }

    @Override
    public String toString() {
        return "FixedAssetCategoryCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (categoryCode != null ? "categoryCode=" + categoryCode + ", " : "") +
                (categoryName != null ? "categoryName=" + categoryName + ", " : "") +
                (categoryDescription != null ? "categoryDescription=" + categoryDescription + ", " : "") +
                (depreciationRegimeId != null ? "depreciationRegimeId=" + depreciationRegimeId + ", " : "") +
            "}";
    }

}
