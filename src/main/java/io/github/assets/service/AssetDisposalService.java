package io.github.assets.service;

import io.github.assets.service.dto.AssetDisposalDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.AssetDisposal}.
 */
public interface AssetDisposalService {

    /**
     * Save a assetDisposal.
     *
     * @param assetDisposalDTO the entity to save.
     * @return the persisted entity.
     */
    AssetDisposalDTO save(AssetDisposalDTO assetDisposalDTO);

    /**
     * Get all the assetDisposals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDisposalDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assetDisposal.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetDisposalDTO> findOne(Long id);

    /**
     * Delete the "id" assetDisposal.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the assetDisposal corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDisposalDTO> search(String query, Pageable pageable);
}
