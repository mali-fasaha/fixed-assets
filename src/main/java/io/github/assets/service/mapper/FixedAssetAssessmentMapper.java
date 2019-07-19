package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.FixedAssetAssessmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link FixedAssetAssessment} and its DTO {@link FixedAssetAssessmentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface FixedAssetAssessmentMapper extends EntityMapper<FixedAssetAssessmentDTO, FixedAssetAssessment> {



    default FixedAssetAssessment fromId(Long id) {
        if (id == null) {
            return null;
        }
        FixedAssetAssessment fixedAssetAssessment = new FixedAssetAssessment();
        fixedAssetAssessment.setId(id);
        return fixedAssetAssessment;
    }
}
