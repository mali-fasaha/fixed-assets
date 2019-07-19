package io.github.assets.repository;

import io.github.assets.domain.FixedAssetAssessment;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FixedAssetAssessment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetAssessmentRepository extends JpaRepository<FixedAssetAssessment, Long>, JpaSpecificationExecutor<FixedAssetAssessment> {

}
