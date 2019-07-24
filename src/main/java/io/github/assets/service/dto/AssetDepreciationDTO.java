package io.github.assets.service.dto;
import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.AssetDepreciation} entity.
 */
public class AssetDepreciationDTO implements Serializable {

    private Long id;

    private String description;

    @NotNull
    private BigDecimal depreciationAmount;

    @NotNull
    private LocalDate depreciationDate;

    private Long categoryId;

    private Long assetItemId;


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

    public BigDecimal getDepreciationAmount() {
        return depreciationAmount;
    }

    public void setDepreciationAmount(BigDecimal depreciationAmount) {
        this.depreciationAmount = depreciationAmount;
    }

    public LocalDate getDepreciationDate() {
        return depreciationDate;
    }

    public void setDepreciationDate(LocalDate depreciationDate) {
        this.depreciationDate = depreciationDate;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getAssetItemId() {
        return assetItemId;
    }

    public void setAssetItemId(Long assetItemId) {
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

        AssetDepreciationDTO assetDepreciationDTO = (AssetDepreciationDTO) o;
        if (assetDepreciationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), assetDepreciationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "AssetDepreciationDTO{" +
            "id=" + getId() +
            ", description='" + getDescription() + "'" +
            ", depreciationAmount=" + getDepreciationAmount() +
            ", depreciationDate='" + getDepreciationDate() + "'" +
            ", categoryId=" + getCategoryId() +
            ", assetItemId=" + getAssetItemId() +
            "}";
    }
}
