package io.github.assets.service;

import io.github.assets.service.dto.TransactionApprovalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.TransactionApproval}.
 */
public interface TransactionApprovalService {

    /**
     * Save a transactionApproval.
     *
     * @param transactionApprovalDTO the entity to save.
     * @return the persisted entity.
     */
    TransactionApprovalDTO save(TransactionApprovalDTO transactionApprovalDTO);

    /**
     * Get all the transactionApprovals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionApprovalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" transactionApproval.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<TransactionApprovalDTO> findOne(Long id);

    /**
     * Delete the "id" transactionApproval.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the transactionApproval corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<TransactionApprovalDTO> search(String query, Pageable pageable);
}
