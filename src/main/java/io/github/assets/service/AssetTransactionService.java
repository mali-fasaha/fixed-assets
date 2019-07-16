package io.github.assets.service;

import io.github.assets.service.dto.AssetTransactionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.AssetTransaction}.
 */
public interface AssetTransactionService {

    /**
     * Save a assetTransaction.
     *
     * @param assetTransactionDTO the entity to save.
     * @return the persisted entity.
     */
    AssetTransactionDTO save(AssetTransactionDTO assetTransactionDTO);

    /**
     * Get all the assetTransactions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetTransactionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assetTransaction.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetTransactionDTO> findOne(Long id);

    /**
     * Delete the "id" assetTransaction.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the assetTransaction corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetTransactionDTO> search(String query, Pageable pageable);
}
