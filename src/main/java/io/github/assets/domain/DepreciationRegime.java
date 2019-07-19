package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

import io.github.assets.domain.enumeration.AssetDecayType;

/**
 * A DepreciationRegime.
 */
@Entity
@Table(name = "depreciation_regime")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "depreciationregime")
public class DepreciationRegime implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "asset_decay_type", nullable = false)
    private AssetDecayType assetDecayType;

    @NotNull
    @Column(name = "depreciation_rate", nullable = false)
    private Double depreciationRate;

    @Column(name = "description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetDecayType getAssetDecayType() {
        return assetDecayType;
    }

    public DepreciationRegime assetDecayType(AssetDecayType assetDecayType) {
        this.assetDecayType = assetDecayType;
        return this;
    }

    public void setAssetDecayType(AssetDecayType assetDecayType) {
        this.assetDecayType = assetDecayType;
    }

    public Double getDepreciationRate() {
        return depreciationRate;
    }

    public DepreciationRegime depreciationRate(Double depreciationRate) {
        this.depreciationRate = depreciationRate;
        return this;
    }

    public void setDepreciationRate(Double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getDescription() {
        return description;
    }

    public DepreciationRegime description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DepreciationRegime)) {
            return false;
        }
        return id != null && id.equals(((DepreciationRegime) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "DepreciationRegime{" +
            "id=" + getId() +
            ", assetDecayType='" + getAssetDecayType() + "'" +
            ", depreciationRate=" + getDepreciationRate() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
