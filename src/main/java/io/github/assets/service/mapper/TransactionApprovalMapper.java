package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.TransactionApprovalDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link TransactionApproval} and its DTO {@link TransactionApprovalDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionApprovalMapper extends EntityMapper<TransactionApprovalDTO, TransactionApproval> {



    default TransactionApproval fromId(Long id) {
        if (id == null) {
            return null;
        }
        TransactionApproval transactionApproval = new TransactionApproval();
        transactionApproval.setId(id);
        return transactionApproval;
    }
}
