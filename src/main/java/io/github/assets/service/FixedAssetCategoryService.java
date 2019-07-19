package io.github.assets.service;

import io.github.assets.service.dto.FixedAssetCategoryDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.FixedAssetCategory}.
 */
public interface FixedAssetCategoryService {

    /**
     * Save a fixedAssetCategory.
     *
     * @param fixedAssetCategoryDTO the entity to save.
     * @return the persisted entity.
     */
    FixedAssetCategoryDTO save(FixedAssetCategoryDTO fixedAssetCategoryDTO);

    /**
     * Get all the fixedAssetCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetCategoryDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fixedAssetCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FixedAssetCategoryDTO> findOne(Long id);

    /**
     * Delete the "id" fixedAssetCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fixedAssetCategory corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetCategoryDTO> search(String query, Pageable pageable);
}
