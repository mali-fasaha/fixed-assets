package io.github.assets.repository;

import io.github.assets.domain.AssetTransaction;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the AssetTransaction entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetTransactionRepository extends JpaRepository<AssetTransaction, Long>, JpaSpecificationExecutor<AssetTransaction> {

}
