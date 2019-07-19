package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import io.github.assets.domain.enumeration.AssetDecayType;

/**
 * A DTO for the {@link io.github.assets.domain.DepreciationRegime} entity.
 */
public class DepreciationRegimeDTO implements Serializable {

    private Long id;

    @NotNull
    private AssetDecayType assetDecayType;

    @NotNull
    private Double depreciationRate;

    private String description;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssetDecayType getAssetDecayType() {
        return assetDecayType;
    }

    public void setAssetDecayType(AssetDecayType assetDecayType) {
        this.assetDecayType = assetDecayType;
    }

    public Double getDepreciationRate() {
        return depreciationRate;
    }

    public void setDepreciationRate(Double depreciationRate) {
        this.depreciationRate = depreciationRate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
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

        DepreciationRegimeDTO depreciationRegimeDTO = (DepreciationRegimeDTO) o;
        if (depreciationRegimeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), depreciationRegimeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DepreciationRegimeDTO{" +
            "id=" + getId() +
            ", assetDecayType='" + getAssetDecayType() + "'" +
            ", depreciationRate=" + getDepreciationRate() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
