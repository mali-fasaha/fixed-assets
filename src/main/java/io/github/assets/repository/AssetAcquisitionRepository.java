package io.github.assets.repository;

import io.github.assets.domain.AssetAcquisition;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetAcquisition entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetAcquisitionRepository extends JpaRepository<AssetAcquisition, Long>, JpaSpecificationExecutor<AssetAcquisition> {

}
