package io.github.assets.service;

import io.github.assets.service.dto.FixedAssetAssessmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link io.github.assets.domain.FixedAssetAssessment}.
 */
public interface FixedAssetAssessmentService {

    /**
     * Save a fixedAssetAssessment.
     *
     * @param fixedAssetAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    FixedAssetAssessmentDTO save(FixedAssetAssessmentDTO fixedAssetAssessmentDTO);

    /**
     * Get all the fixedAssetAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetAssessmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" fixedAssetAssessment.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FixedAssetAssessmentDTO> findOne(Long id);

    /**
     * Delete the "id" fixedAssetAssessment.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the fixedAssetAssessment corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FixedAssetAssessmentDTO> search(String query, Pageable pageable);
}
