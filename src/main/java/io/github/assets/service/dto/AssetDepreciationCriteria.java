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
import io.github.jhipster.service.filter.BigDecimalFilter;
import io.github.jhipster.service.filter.LocalDateFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.AssetDepreciation} entity. This class is used
 * in {@link io.github.assets.web.rest.AssetDepreciationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /asset-depreciations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class AssetDepreciationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter description;

    private BigDecimalFilter depreciationAmount;

    private LocalDateFilter depreciationDate;

    private LongFilter categoryId;

    private LongFilter assetItemId;

    public AssetDepreciationCriteria(){
    }

    public AssetDepreciationCriteria(AssetDepreciationCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.depreciationAmount = other.depreciationAmount == null ? null : other.depreciationAmount.copy();
        this.depreciationDate = other.depreciationDate == null ? null : other.depreciationDate.copy();
        this.categoryId = other.categoryId == null ? null : other.categoryId.copy();
        this.assetItemId = other.assetItemId == null ? null : other.assetItemId.copy();
    }

    @Override
    public AssetDepreciationCriteria copy() {
        return new AssetDepreciationCriteria(this);
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

    public BigDecimalFilter getDepreciationAmount() {
        return depreciationAmount;
    }

    public void setDepreciationAmount(BigDecimalFilter depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
    }

    public LocalDateFilter getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(LocalDateFilter depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public LongFilter getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(LongFilter categoryId) {
        this.categoryId = categoryId;
    }

    public LongFilter getAssetItemId() {
        return assetItemId;
    }

    public void setAssetItemId(LongFilter assetItemId) {
        this.assetItemId = assetItemId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final AssetDepreciationCriteria that = (AssetDepreciationCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(description, that.description) &&
            Objects.equals(depreciationAmount, that.depreciationAmount) &&
            Objects.equals(depreciationDate, that.depreciationDate) &&
            Objects.equals(categoryId, that.categoryId) &&
            Objects.equals(assetItemId, that.assetItemId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        description,
        depreciationAmount,
        depreciationDate,
        categoryId,
        assetItemId
        );
    }

    @Override
    public String toString() {
        return "AssetDepreciationCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (depreciationAmount != null ? "depreciationAmount=" + depreciationAmount + ", " : "") +
                (depreciationDate != null ? "depreciationDate=" + depreciationDate + ", " : "") +
                (categoryId != null ? "categoryId=" + categoryId + ", " : "") +
                (assetItemId != null ? "assetItemId=" + assetItemId + ", " : "") +
            "}";
    }

}
