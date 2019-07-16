package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.ScannedDocumentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ScannedDocument} and its DTO {@link ScannedDocumentDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ScannedDocumentMapper extends EntityMapper<ScannedDocumentDTO, ScannedDocument> {



    default ScannedDocument fromId(Long id) {
        if (id == null) {
            return null;
        }
        ScannedDocument scannedDocument = new ScannedDocument();
        scannedDocument.setId(id);
        return scannedDocument;
    }
}
