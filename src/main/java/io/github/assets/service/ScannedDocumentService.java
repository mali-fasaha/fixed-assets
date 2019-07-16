package io.github.assets.service;

import io.github.assets.service.dto.ScannedDocumentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.ScannedDocument}.
 */
public interface ScannedDocumentService {

    /**
     * Save a scannedDocument.
     *
     * @param scannedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    ScannedDocumentDTO save(ScannedDocumentDTO scannedDocumentDTO);

    /**
     * Get all the scannedDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScannedDocumentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" scannedDocument.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ScannedDocumentDTO> findOne(Long id);

    /**
     * Delete the "id" scannedDocument.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the scannedDocument corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ScannedDocumentDTO> search(String query, Pageable pageable);
}
