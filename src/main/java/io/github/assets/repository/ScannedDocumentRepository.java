package io.github.assets.repository;

import io.github.assets.domain.ScannedDocument;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ScannedDocument entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScannedDocumentRepository extends JpaRepository<ScannedDocument, Long>, JpaSpecificationExecutor<ScannedDocument> {

}
