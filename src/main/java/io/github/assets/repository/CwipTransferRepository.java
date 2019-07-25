package io.github.assets.repository;

import io.github.assets.domain.CwipTransfer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the CwipTransfer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CwipTransferRepository extends JpaRepository<CwipTransfer, Long>, JpaSpecificationExecutor<CwipTransfer> {

}
