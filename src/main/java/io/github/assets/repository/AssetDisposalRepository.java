package io.github.assets.repository;

import io.github.assets.domain.AssetDisposal;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetDisposal entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetDisposalRepository extends JpaRepository<AssetDisposal, Long>, JpaSpecificationExecutor<AssetDisposal> {

}
