package io.github.assets.repository;

import io.github.assets.domain.FixedAssetInvoice;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FixedAssetInvoice entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FixedAssetInvoiceRepository extends JpaRepository<FixedAssetInvoice, Long>, JpaSpecificationExecutor<FixedAssetInvoice> {

}
