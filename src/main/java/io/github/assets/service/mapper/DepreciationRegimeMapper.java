package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.DepreciationRegimeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link DepreciationRegime} and its DTO {@link DepreciationRegimeDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface DepreciationRegimeMapper extends EntityMapper<DepreciationRegimeDTO, DepreciationRegime> {



    default DepreciationRegime fromId(Long id) {
        if (id == null) {
            return null;
        }
        DepreciationRegime depreciationRegime = new DepreciationRegime();
        depreciationRegime.setId(id);
        return depreciationRegime;
    }
}
