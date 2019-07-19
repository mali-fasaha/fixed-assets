package io.github.assets.service.impl;

import io.github.assets.service.FixedAssetAssessmentService;
import io.github.assets.domain.FixedAssetAssessment;
import io.github.assets.repository.FixedAssetAssessmentRepository;
import io.github.assets.repository.search.FixedAssetAssessmentSearchRepository;
import io.github.assets.service.dto.FixedAssetAssessmentDTO;
import io.github.assets.service.mapper.FixedAssetAssessmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FixedAssetAssessment}.
 */
@Service
@Transactional
public class FixedAssetAssessmentServiceImpl implements FixedAssetAssessmentService {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAssessmentServiceImpl.class);

    private final FixedAssetAssessmentRepository fixedAssetAssessmentRepository;

    private final FixedAssetAssessmentMapper fixedAssetAssessmentMapper;

    private final FixedAssetAssessmentSearchRepository fixedAssetAssessmentSearchRepository;

    public FixedAssetAssessmentServiceImpl(FixedAssetAssessmentRepository fixedAssetAssessmentRepository, FixedAssetAssessmentMapper fixedAssetAssessmentMapper, FixedAssetAssessmentSearchRepository fixedAssetAssessmentSearchRepository) {
        this.fixedAssetAssessmentRepository = fixedAssetAssessmentRepository;
        this.fixedAssetAssessmentMapper = fixedAssetAssessmentMapper;
        this.fixedAssetAssessmentSearchRepository = fixedAssetAssessmentSearchRepository;
    }

    /**
     * Save a fixedAssetAssessment.
     *
     * @param fixedAssetAssessmentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FixedAssetAssessmentDTO save(FixedAssetAssessmentDTO fixedAssetAssessmentDTO) {
        log.debug("Request to save FixedAssetAssessment : {}", fixedAssetAssessmentDTO);
        FixedAssetAssessment fixedAssetAssessment = fixedAssetAssessmentMapper.toEntity(fixedAssetAssessmentDTO);
        fixedAssetAssessment = fixedAssetAssessmentRepository.save(fixedAssetAssessment);
        FixedAssetAssessmentDTO result = fixedAssetAssessmentMapper.toDto(fixedAssetAssessment);
        fixedAssetAssessmentSearchRepository.save(fixedAssetAssessment);
        return result;
    }

    /**
     * Get all the fixedAssetAssessments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetAssessmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FixedAssetAssessments");
        return fixedAssetAssessmentRepository.findAll(pageable)
            .map(fixedAssetAssessmentMapper::toDto);
    }


    /**
     * Get one fixedAssetAssessment by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FixedAssetAssessmentDTO> findOne(Long id) {
        log.debug("Request to get FixedAssetAssessment : {}", id);
        return fixedAssetAssessmentRepository.findById(id)
            .map(fixedAssetAssessmentMapper::toDto);
    }

    /**
     * Delete the fixedAssetAssessment by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FixedAssetAssessment : {}", id);
        fixedAssetAssessmentRepository.deleteById(id);
        fixedAssetAssessmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the fixedAssetAssessment corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FixedAssetAssessmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FixedAssetAssessments for query {}", query);
        return fixedAssetAssessmentSearchRepository.search(queryStringQuery(query), pageable)
            .map(fixedAssetAssessmentMapper::toDto);
    }
}
