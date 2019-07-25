package io.github.assets.repository;

import io.github.assets.domain.TransactionApproval;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the TransactionApproval entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TransactionApprovalRepository extends JpaRepository<TransactionApproval, Long>, JpaSpecificationExecutor<TransactionApproval> {

}
