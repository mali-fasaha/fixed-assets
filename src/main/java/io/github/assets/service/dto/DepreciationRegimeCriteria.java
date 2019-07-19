package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.assets.domain.enumeration.AssetDecayType;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.DepreciationRegime} entity. This class is used
 * in {@link io.github.assets.web.rest.DepreciationRegimeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /depreciation-regimes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class DepreciationRegimeCriteria implements Serializable, Criteria {
    /**
     * Class for filtering AssetDecayType
     */
    public static class AssetDecayTypeFilter extends Filter<AssetDecayType> {

        public AssetDecayTypeFilter() {
        }

        public AssetDecayTypeFilter(AssetDecayTypeFilter filter) {
            super(filter);
        }

        @Override
        public AssetDecayTypeFilter copy() {
            return new AssetDecayTypeFilter(this);
        }

    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private AssetDecayTypeFilter assetDecayType;

    private DoubleFilter depreciationRate;

    private StringFilter description;

    public DepreciationRegimeCriteria(){
    }

    public DepreciationRegimeCriteria(DepreciationRegimeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.assetDecayType = other.assetDecayType == null ? null : other.assetDecayType.copy();
        this.depreciationRate = other.depreciationRate == null ? null : other.depreciationRate.copy();
        this.description = other.description == null ? null : other.description.copy();
    }

    @Override
    public DepreciationRegimeCriteria copy() {
        return new DepreciationRegimeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public AssetDecayTypeFilter getAssetDecayType() {
        return assetDecayType;
    }

    public void setAssetDecayType(AssetDecayTypeFilter assetDecayType) {
        this.assetDecayType = assetDecayType;
    }

    public DoubleFilter getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(DoubleFilter depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final DepreciationRegimeCriteria that = (DepreciationRegimeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(assetDecayType, that.assetDecayType) &&
            Objects.equals(depreciationRate, that.depreciationRate) &&
            Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        assetDecayType,
        depreciationRate,
        description
        );
    }

    @Override
    public String toString() {
        return "DepreciationRegimeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (assetDecayType != null ? "assetDecayType=" + assetDecayType + ", " : "") +
                (depreciationRate != null ? "depreciationRate=" + depreciationRate + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
            "}";
    }

}
