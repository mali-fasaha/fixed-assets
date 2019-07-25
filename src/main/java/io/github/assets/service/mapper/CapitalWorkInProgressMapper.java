package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.CapitalWorkInProgressDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link CapitalWorkInProgress} and its DTO {@link CapitalWorkInProgressDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CapitalWorkInProgressMapper extends EntityMapper<CapitalWorkInProgressDTO, CapitalWorkInProgress> {



    default CapitalWorkInProgress fromId(Long id) {
        if (id == null) {
            return null;
        }
        CapitalWorkInProgress capitalWorkInProgress = new CapitalWorkInProgress();
        capitalWorkInProgress.setId(id);
        return capitalWorkInProgress;
    }
}
