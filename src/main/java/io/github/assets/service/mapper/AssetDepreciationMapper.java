package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.AssetDepreciationDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetDepreciation} and its DTO {@link AssetDepreciationDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetDepreciationMapper extends EntityMapper<AssetDepreciationDTO, AssetDepreciation> {



    default AssetDepreciation fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetDepreciation assetDepreciation = new AssetDepreciation();
        assetDepreciation.setId(id);
        return assetDepreciation;
    }
}
