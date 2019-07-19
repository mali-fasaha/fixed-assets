package io.github.assets.repository;

import io.github.assets.domain.FixedAssetItem;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FixedAssetItem entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetItemRepository extends JpaRepository<FixedAssetItem, Long>, JpaSpecificationExecutor<FixedAssetItem> {

}
