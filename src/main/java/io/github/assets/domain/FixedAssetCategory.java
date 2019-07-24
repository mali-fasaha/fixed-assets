package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A FixedAssetCategory.
 */
@Entity
@Table(name = "fixed_asset_category")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "fixedassetcategory")
public class FixedAssetCategory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "category_name", nullable = false, unique = true)
    private String categoryName;

    @Column(name = "category_description")
    private String categoryDescription;

    @NotNull
    @Column(name = "category_asset_code", nullable = false, unique = true)
    private String categoryAssetCode;

    @NotNull
    @Column(name = "category_depreciation_code", nullable = false, unique = true)
    private String categoryDepreciationCode;

    @Column(name = "depreciation_regime_id")
    private Long depreciationRegimeId;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public FixedAssetCategory categoryName(String categoryName) {
        this.categoryName = categoryName;
        return this;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public FixedAssetCategory categoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
        return this;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryAssetCode() {
        return categoryAssetCode;
    }

    public FixedAssetCategory categoryAssetCode(String categoryAssetCode) {
        this.categoryAssetCode = categoryAssetCode;
        return this;
    }

    public void setCategoryAssetCode(String categoryAssetCode) {
        this.categoryAssetCode = categoryAssetCode;
    }

    public String getCategoryDepreciationCode() {
        return categoryDepreciationCode;
    }

    public FixedAssetCategory categoryDepreciationCode(String categoryDepreciationCode) {
        this.categoryDepreciationCode = categoryDepreciationCode;
        return this;
    }

    public void setCategoryDepreciationCode(String categoryDepreciationCode) {
        this.categoryDepreciationCode = categoryDepreciationCode;
    }

    public Long getDepreciationRegimeId() {
        return depreciationRegimeId;
    }

    public FixedAssetCategory depreciationRegimeId(Long depreciationRegimeId) {
        this.depreciationRegimeId = depreciationRegimeId;
        return this;
    }

    public void setDepreciationRegimeId(Long depreciationRegimeId) {
        this.depreciationRegimeId = depreciationRegimeId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FixedAssetCategory)) {
            return false;
        }
        return id != null && id.equals(((FixedAssetCategory) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "FixedAssetCategory{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryDescription='" + getCategoryDescription() + "'" +
            ", categoryAssetCode='" + getCategoryAssetCode() + "'" +
            ", categoryDepreciationCode='" + getCategoryDepreciationCode() + "'" +
            ", depreciationRegimeId=" + getDepreciationRegimeId() +
            "}";
    }
}
