package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.CwipTransferDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CwipTransfer} and its DTO {@link CwipTransferDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CwipTransferMapper extends EntityMapper<CwipTransferDTO, CwipTransfer> {



    default CwipTransfer fromId(Long id) {
        if (id == null) {
            return null;
        }
        CwipTransfer cwipTransfer = new CwipTransfer();
        cwipTransfer.setId(id);
        return cwipTransfer;
    }
}
