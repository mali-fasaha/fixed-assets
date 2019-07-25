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

import io.github.assets.domain.CapitalWorkInProgress;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.CapitalWorkInProgressRepository;
import io.github.assets.repository.search.CapitalWorkInProgressSearchRepository;
import io.github.assets.service.dto.CapitalWorkInProgressCriteria;
import io.github.assets.service.dto.CapitalWorkInProgressDTO;
import io.github.assets.service.mapper.CapitalWorkInProgressMapper;

/**
 * Service for executing complex queries for {@link CapitalWorkInProgress} entities in the database.
 * The main input is a {@link CapitalWorkInProgressCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CapitalWorkInProgressDTO} or a {@link Page} of {@link CapitalWorkInProgressDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CapitalWorkInProgressQueryService extends QueryService<CapitalWorkInProgress> {

    private final Logger log = LoggerFactory.getLogger(CapitalWorkInProgressQueryService.class);

    private final CapitalWorkInProgressRepository capitalWorkInProgressRepository;

    private final CapitalWorkInProgressMapper capitalWorkInProgressMapper;

    private final CapitalWorkInProgressSearchRepository capitalWorkInProgressSearchRepository;

    public CapitalWorkInProgressQueryService(CapitalWorkInProgressRepository capitalWorkInProgressRepository, CapitalWorkInProgressMapper capitalWorkInProgressMapper, CapitalWorkInProgressSearchRepository capitalWorkInProgressSearchRepository) {
        this.capitalWorkInProgressRepository = capitalWorkInProgressRepository;
        this.capitalWorkInProgressMapper = capitalWorkInProgressMapper;
        this.capitalWorkInProgressSearchRepository = capitalWorkInProgressSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CapitalWorkInProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CapitalWorkInProgressDTO> findByCriteria(CapitalWorkInProgressCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CapitalWorkInProgress> specification = createSpecification(criteria);
        return capitalWorkInProgressMapper.toDto(capitalWorkInProgressRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CapitalWorkInProgressDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CapitalWorkInProgressDTO> findByCriteria(CapitalWorkInProgressCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CapitalWorkInProgress> specification = createSpecification(criteria);
        return capitalWorkInProgressRepository.findAll(specification, page)
            .map(capitalWorkInProgressMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CapitalWorkInProgressCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CapitalWorkInProgress> specification = createSpecification(criteria);
        return capitalWorkInProgressRepository.count(specification);
    }

    /**
     * Function to convert CapitalWorkInProgressCriteria to a {@link Specification}.
     */
    private Specification<CapitalWorkInProgress> createSpecification(CapitalWorkInProgressCriteria criteria) {
        Specification<CapitalWorkInProgress> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CapitalWorkInProgress_.id));
            }
            if (criteria.getTransactionMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionMonth(), CapitalWorkInProgress_.transactionMonth));
            }
            if (criteria.getAssetSerialTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetSerialTag(), CapitalWorkInProgress_.assetSerialTag));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), CapitalWorkInProgress_.serviceOutletCode));
            }
            if (criteria.getTransactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionId(), CapitalWorkInProgress_.transactionId));
            }
            if (criteria.getTransactionDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionDetails(), CapitalWorkInProgress_.transactionDetails));
            }
            if (criteria.getTransactionAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionAmount(), CapitalWorkInProgress_.transactionAmount));
            }
        }
        return specification;
    }
}
