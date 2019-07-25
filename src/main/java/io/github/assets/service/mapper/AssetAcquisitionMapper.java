package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.AssetAcquisitionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetAcquisition} and its DTO {@link AssetAcquisitionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetAcquisitionMapper extends EntityMapper<AssetAcquisitionDTO, AssetAcquisition> {



    default AssetAcquisition fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetAcquisition assetAcquisition = new AssetAcquisition();
        assetAcquisition.setId(id);
        return assetAcquisition;
    }
}
