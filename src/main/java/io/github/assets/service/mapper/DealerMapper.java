package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.DealerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link Dealer} and its DTO {@link DealerDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DealerMapper extends EntityMapper<DealerDTO, Dealer> {


    @Mapping(target = "fixedAssetInvoices", ignore = true)
    Dealer toEntity(DealerDTO dealerDTO);

    default Dealer fromId(Long id) {
        if (id == null) {
            return null;
        }
        Dealer dealer = new Dealer();
        dealer.setId(id);
        return dealer;
    }
}
