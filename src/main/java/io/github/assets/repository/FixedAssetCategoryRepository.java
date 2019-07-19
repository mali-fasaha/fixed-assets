package io.github.assets.repository;

import io.github.assets.domain.FixedAssetCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FixedAssetCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetCategoryRepository extends JpaRepository<FixedAssetCategory, Long>, JpaSpecificationExecutor<FixedAssetCategory> {

}
