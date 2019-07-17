package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.FixedAssetInvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetInvoice} and its DTO {@link FixedAssetInvoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetInvoiceMapper extends EntityMapper<FixedAssetInvoiceDTO, FixedAssetInvoice> {



    default FixedAssetInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetInvoice fixedAssetInvoice = new FixedAssetInvoice();
        fixedAssetInvoice.setId(id);
        return fixedAssetInvoice;
    }
}
