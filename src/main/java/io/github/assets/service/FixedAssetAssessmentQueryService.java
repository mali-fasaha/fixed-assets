package io.github.assets.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import io.github.assets.domain.FixedAssetAssessment;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.FixedAssetAssessmentRepository;
import io.github.assets.repository.search.FixedAssetAssessmentSearchRepository;
import io.github.assets.service.dto.FixedAssetAssessmentCriteria;
import io.github.assets.service.dto.FixedAssetAssessmentDTO;
import io.github.assets.service.mapper.FixedAssetAssessmentMapper;

/**
 * Service for executing complex queries for {@link FixedAssetAssessment} entities in the database.
 * The main input is a {@link FixedAssetAssessmentCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FixedAssetAssessmentDTO} or a {@link Page} of {@link FixedAssetAssessmentDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetAssessmentQueryService extends QueryService<FixedAssetAssessment> {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAssessmentQueryService.class);

    private final FixedAssetAssessmentRepository fixedAssetAssessmentRepository;

    private final FixedAssetAssessmentMapper fixedAssetAssessmentMapper;

    private final FixedAssetAssessmentSearchRepository fixedAssetAssessmentSearchRepository;

    public FixedAssetAssessmentQueryService(FixedAssetAssessmentRepository fixedAssetAssessmentRepository, FixedAssetAssessmentMapper fixedAssetAssessmentMapper, FixedAssetAssessmentSearchRepository fixedAssetAssessmentSearchRepository) {
        this.fixedAssetAssessmentRepository = fixedAssetAssessmentRepository;
        this.fixedAssetAssessmentMapper = fixedAssetAssessmentMapper;
        this.fixedAssetAssessmentSearchRepository = fixedAssetAssessmentSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FixedAssetAssessmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FixedAssetAssessmentDTO> findByCriteria(FixedAssetAssessmentCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FixedAssetAssessment> specification = createSpecification(criteria);
        return fixedAssetAssessmentMapper.toDto(fixedAssetAssessmentRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FixedAssetAssessmentDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedAssetAssessmentDTO> findByCriteria(FixedAssetAssessmentCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FixedAssetAssessment> specification = createSpecification(criteria);
        return fixedAssetAssessmentRepository.findAll(specification, page)
            .map(fixedAssetAssessmentMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixedAssetAssessmentCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FixedAssetAssessment> specification = createSpecification(criteria);
        return fixedAssetAssessmentRepository.count(specification);
    }

    /**
     * Function to convert FixedAssetAssessmentCriteria to a {@link Specification}.
     */
    private Specification<FixedAssetAssessment> createSpecification(FixedAssetAssessmentCriteria criteria) {
        Specification<FixedAssetAssessment> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FixedAssetAssessment_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), FixedAssetAssessment_.description));
            }
            if (criteria.getAssetCondition() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetCondition(), FixedAssetAssessment_.assetCondition));
            }
            if (criteria.getAssessmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssessmentDate(), FixedAssetAssessment_.assessmentDate));
            }
            if (criteria.getAssessmentRemarks() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssessmentRemarks(), FixedAssetAssessment_.assessmentRemarks));
            }
            if (criteria.getNameOfAssessingStaff() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameOfAssessingStaff(), FixedAssetAssessment_.nameOfAssessingStaff));
            }
            if (criteria.getNameOfAssessmentContractor() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameOfAssessmentContractor(), FixedAssetAssessment_.nameOfAssessmentContractor));
            }
            if (criteria.getCurrentServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentServiceOutletCode(), FixedAssetAssessment_.currentServiceOutletCode));
            }
            if (criteria.getCurrentPhysicalAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCurrentPhysicalAddress(), FixedAssetAssessment_.currentPhysicalAddress));
            }
            if (criteria.getNextAssessmentDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNextAssessmentDate(), FixedAssetAssessment_.nextAssessmentDate));
            }
            if (criteria.getNameOfUser() != null) {
                specification = specification.and(buildStringSpecification(criteria.getNameOfUser(), FixedAssetAssessment_.nameOfUser));
            }
            if (criteria.getFixedAssetItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getFixedAssetItemId(), FixedAssetAssessment_.fixedAssetItemId));
            }
            if (criteria.getEstimatedValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedValue(), FixedAssetAssessment_.estimatedValue));
            }
            if (criteria.getEstimatedUsefulMonths() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getEstimatedUsefulMonths(), FixedAssetAssessment_.estimatedUsefulMonths));
            }
        }
        return specification;
    }
}
