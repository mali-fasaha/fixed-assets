package io.github.assets.service;

import io.github.assets.service.dto.DepreciationRegimeDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.DepreciationRegime}.
 */
public interface DepreciationRegimeService {

    /**
     * Save a depreciationRegime.
     *
     * @param depreciationRegimeDTO the entity to save.
     * @return the persisted entity.
     */
    DepreciationRegimeDTO save(DepreciationRegimeDTO depreciationRegimeDTO);

    /**
     * Get all the depreciationRegimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepreciationRegimeDTO> findAll(Pageable pageable);


    /**
     * Get the "id" depreciationRegime.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DepreciationRegimeDTO> findOne(Long id);

    /**
     * Delete the "id" depreciationRegime.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the depreciationRegime corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DepreciationRegimeDTO> search(String query, Pageable pageable);
}
