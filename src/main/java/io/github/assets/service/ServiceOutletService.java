package io.github.assets.service;

import io.github.assets.service.dto.ServiceOutletDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.ServiceOutlet}.
 */
public interface ServiceOutletService {

    /**
     * Save a serviceOutlet.
     *
     * @param serviceOutletDTO the entity to save.
     * @return the persisted entity.
     */
    ServiceOutletDTO save(ServiceOutletDTO serviceOutletDTO);

    /**
     * Get all the serviceOutlets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOutletDTO> findAll(Pageable pageable);


    /**
     * Get the "id" serviceOutlet.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ServiceOutletDTO> findOne(Long id);

    /**
     * Delete the "id" serviceOutlet.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the serviceOutlet corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ServiceOutletDTO> search(String query, Pageable pageable);
}
