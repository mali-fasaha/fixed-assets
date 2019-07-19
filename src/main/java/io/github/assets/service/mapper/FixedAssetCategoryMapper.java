package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.FixedAssetCategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetCategory} and its DTO {@link FixedAssetCategoryDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetCategoryMapper extends EntityMapper<FixedAssetCategoryDTO, FixedAssetCategory> {



    default FixedAssetCategory fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetCategory fixedAssetCategory = new FixedAssetCategory();
        fixedAssetCategory.setId(id);
        return fixedAssetCategory;
    }
}
