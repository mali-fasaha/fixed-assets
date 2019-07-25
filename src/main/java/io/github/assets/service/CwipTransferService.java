package io.github.assets.service;

import io.github.assets.service.dto.CwipTransferDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.CwipTransfer}.
 */
public interface CwipTransferService {

    /**
     * Save a cwipTransfer.
     *
     * @param cwipTransferDTO the entity to save.
     * @return the persisted entity.
     */
    CwipTransferDTO save(CwipTransferDTO cwipTransferDTO);

    /**
     * Get all the cwipTransfers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CwipTransferDTO> findAll(Pageable pageable);


    /**
     * Get the "id" cwipTransfer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CwipTransferDTO> findOne(Long id);

    /**
     * Delete the "id" cwipTransfer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the cwipTransfer corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CwipTransferDTO> search(String query, Pageable pageable);
}
