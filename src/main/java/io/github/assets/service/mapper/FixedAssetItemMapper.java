package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.FixedAssetItemDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetItem} and its DTO {@link FixedAssetItemDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetItemMapper extends EntityMapper<FixedAssetItemDTO, FixedAssetItem> {



    default FixedAssetItem fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetItem fixedAssetItem = new FixedAssetItem();
        fixedAssetItem.setId(id);
        return fixedAssetItem;
    }
}
