package io.github.assets.repository;

import io.github.assets.domain.AssetDepreciation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetDepreciation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetDepreciationRepository extends JpaRepository<AssetDepreciation, Long>, JpaSpecificationExecutor<AssetDepreciation> {

}
