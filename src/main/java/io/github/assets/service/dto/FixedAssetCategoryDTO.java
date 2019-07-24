package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.FixedAssetCategory} entity.
 */
public class FixedAssetCategoryDTO implements Serializable {

    private Long id;

    @NotNull
    private String categoryName;

    private String categoryDescription;

    @NotNull
    private String categoryAssetCode;

    @NotNull
    private String categoryDepreciationCode;

    private Long depreciationRegimeId;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public String getCategoryAssetCode() {
        return categoryAssetCode;
    }

    public void setCategoryAssetCode(String categoryAssetCode) {
        this.categoryAssetCode = categoryAssetCode;
    }

    public String getCategoryDepreciationCode() {
        return categoryDepreciationCode;
    }

    public void setCategoryDepreciationCode(String categoryDepreciationCode) {
        this.categoryDepreciationCode = categoryDepreciationCode;
    }

    public Long getDepreciationRegimeId() {
        return depreciationRegimeId;
    }

    public void setDepreciationRegimeId(Long depreciationRegimeId) {
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

        FixedAssetCategoryDTO fixedAssetCategoryDTO = (FixedAssetCategoryDTO) o;
        if (fixedAssetCategoryDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), fixedAssetCategoryDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "FixedAssetCategoryDTO{" +
            "id=" + getId() +
            ", categoryName='" + getCategoryName() + "'" +
            ", categoryDescription='" + getCategoryDescription() + "'" +
            ", categoryAssetCode='" + getCategoryAssetCode() + "'" +
            ", categoryDepreciationCode='" + getCategoryDepreciationCode() + "'" +
            ", depreciationRegimeId=" + getDepreciationRegimeId() +
            "}";
    }
}
