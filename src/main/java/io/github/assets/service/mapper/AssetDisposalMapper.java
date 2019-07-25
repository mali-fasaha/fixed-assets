package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.AssetDisposalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetDisposal} and its DTO {@link AssetDisposalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetDisposalMapper extends EntityMapper<AssetDisposalDTO, AssetDisposal> {



    default AssetDisposal fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetDisposal assetDisposal = new AssetDisposal();
        assetDisposal.setId(id);
        return assetDisposal;
    }
}
