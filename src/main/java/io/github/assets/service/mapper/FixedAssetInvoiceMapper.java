package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.FixedAssetInvoiceDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetInvoice} and its DTO {@link FixedAssetInvoiceDTO}.
 */
@Mapper(componentModel = "spring", uses = {DealerMapper.class})
public interface FixedAssetInvoiceMapper extends EntityMapper<FixedAssetInvoiceDTO, FixedAssetInvoice> {

    @Mapping(source = "dealer.id", target = "dealerId")
    @Mapping(source = "dealer.dealerName", target = "dealerDealerName")
    FixedAssetInvoiceDTO toDto(FixedAssetInvoice fixedAssetInvoice);

    @Mapping(source = "dealerId", target = "dealer")
    FixedAssetInvoice toEntity(FixedAssetInvoiceDTO fixedAssetInvoiceDTO);

    default FixedAssetInvoice fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetInvoice fixedAssetInvoice = new FixedAssetInvoice();
        fixedAssetInvoice.setId(id);
        return fixedAssetInvoice;
    }
}
