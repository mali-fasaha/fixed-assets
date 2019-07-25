package io.github.assets.service;

import io.github.assets.service.dto.AssetAcquisitionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.AssetAcquisition}.
 */
public interface AssetAcquisitionService {

    /**
     * Save a assetAcquisition.
     *
     * @param assetAcquisitionDTO the entity to save.
     * @return the persisted entity.
     */
    AssetAcquisitionDTO save(AssetAcquisitionDTO assetAcquisitionDTO);

    /**
     * Get all the assetAcquisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetAcquisitionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" assetAcquisition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetAcquisitionDTO> findOne(Long id);

    /**
     * Delete the "id" assetAcquisition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the assetAcquisition corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetAcquisitionDTO> search(String query, Pageable pageable);
}
