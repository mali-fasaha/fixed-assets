package io.github.assets.repository;

import io.github.assets.domain.CapitalWorkInProgress;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CapitalWorkInProgress entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CapitalWorkInProgressRepository extends JpaRepository<CapitalWorkInProgress, Long>, JpaSpecificationExecutor<CapitalWorkInProgress> {

}
