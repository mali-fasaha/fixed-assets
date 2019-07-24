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

/**
 * A AssetDepreciation.
 */
@Entity
@Table(name = "asset_depreciation")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "assetdepreciation")
public class AssetDepreciation implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "depreciation_amount", precision = 21, scale = 2, nullable = false)
    private BigDecimal depreciationAmount;

    @NotNull
    @Column(name = "depreciation_date", nullable = false)
    private LocalDate depreciationDate;

    @Column(name = "category_id")
    private Long categoryId;

    @Column(name = "asset_item_id")
    private Long assetItemId;

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

    public AssetDepreciation description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getDepreciationAmount() {
        return depreciationAmount;
    }

    public AssetDepreciation depreciationAmount(BigDecimal depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
        return this;
    }

    public void setDepreciationAmount(BigDecimal depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
    }

    public LocalDate getDepreciationDate() {
        return depreciationDate;
    }

    public AssetDepreciation depreciationDate(LocalDate depreciationDate) {
        this.depreciationDate = depreciationDate;
        return this;
    }

    public void setDepreciationDate(LocalDate depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public AssetDepreciation categoryId(Long categoryId) {
        this.categoryId = categoryId;
        return this;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAssetItemId() {
        return assetItemId;
    }

    public AssetDepreciation assetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
        return this;
    }

    public void setAssetItemId(Long assetItemId) {
        this.assetItemId = assetItemId;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof AssetDepreciation)) {
            return false;
        }
        return id != null && id.equals(((AssetDepreciation) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "AssetDepreciation{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", depreciationAmount=" + getDepreciationAmount() +
            ", depreciationDate='" + getDepreciationDate() + "'" +
            ", categoryId=" + getCategoryId() +
            ", assetItemId=" + getAssetItemId() +
            "}";
    }
}
