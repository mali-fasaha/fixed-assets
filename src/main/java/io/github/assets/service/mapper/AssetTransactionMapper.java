package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.AssetTransactionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link AssetTransaction} and its DTO {@link AssetTransactionDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface AssetTransactionMapper extends EntityMapper<AssetTransactionDTO, AssetTransaction> {



    default AssetTransaction fromId(Long id) {
        if (id == null) {
            return null;
        }
        AssetTransaction assetTransaction = new AssetTransaction();
        assetTransaction.setId(id);
        return assetTransaction;
    }
}
