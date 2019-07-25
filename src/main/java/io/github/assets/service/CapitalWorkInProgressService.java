package io.github.assets.service;

import io.github.assets.service.dto.CapitalWorkInProgressDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.CapitalWorkInProgress}.
 */
public interface CapitalWorkInProgressService {

    /**
     * Save a capitalWorkInProgress.
     *
     * @param capitalWorkInProgressDTO the entity to save.
     * @return the persisted entity.
     */
    CapitalWorkInProgressDTO save(CapitalWorkInProgressDTO capitalWorkInProgressDTO);

    /**
     * Get all the capitalWorkInProgresses.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CapitalWorkInProgressDTO> findAll(Pageable pageable);


    /**
     * Get the "id" capitalWorkInProgress.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<CapitalWorkInProgressDTO> findOne(Long id);

    /**
     * Delete the "id" capitalWorkInProgress.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the capitalWorkInProgress corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<CapitalWorkInProgressDTO> search(String query, Pageable pageable);
}
