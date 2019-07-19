package io.github.assets.repository;

import io.github.assets.domain.DepreciationRegime;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the DepreciationRegime entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DepreciationRegimeRepository extends JpaRepository<DepreciationRegime, Long>, JpaSpecificationExecutor<DepreciationRegime> {

}
