package io.github.assets.service;

import io.github.assets.service.dto.AssetDepreciationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.AssetDepreciation}.
 */
public interface AssetDepreciationService {

    /**
     * Save a assetDepreciation.
     *
     * @param assetDepreciationDTO the entity to save.
     * @return the persisted entity.
     */
    AssetDepreciationDTO save(AssetDepreciationDTO assetDepreciationDTO);

    /**
     * Get all the assetDepreciations.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDepreciationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assetDepreciation.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetDepreciationDTO> findOne(Long id);

    /**
     * Delete the "id" assetDepreciation.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the assetDepreciation corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetDepreciationDTO> search(String query, Pageable pageable);
}
