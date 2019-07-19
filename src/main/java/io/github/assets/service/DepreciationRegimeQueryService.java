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

import io.github.assets.domain.DepreciationRegime;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.DepreciationRegimeRepository;
import io.github.assets.repository.search.DepreciationRegimeSearchRepository;
import io.github.assets.service.dto.DepreciationRegimeCriteria;
import io.github.assets.service.dto.DepreciationRegimeDTO;
import io.github.assets.service.mapper.DepreciationRegimeMapper;

/**
 * Service for executing complex queries for {@link DepreciationRegime} entities in the database.
 * The main input is a {@link DepreciationRegimeCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DepreciationRegimeDTO} or a {@link Page} of {@link DepreciationRegimeDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DepreciationRegimeQueryService extends QueryService<DepreciationRegime> {

    private final Logger log = LoggerFactory.getLogger(DepreciationRegimeQueryService.class);

    private final DepreciationRegimeRepository depreciationRegimeRepository;

    private final DepreciationRegimeMapper depreciationRegimeMapper;

    private final DepreciationRegimeSearchRepository depreciationRegimeSearchRepository;

    public DepreciationRegimeQueryService(DepreciationRegimeRepository depreciationRegimeRepository, DepreciationRegimeMapper depreciationRegimeMapper, DepreciationRegimeSearchRepository depreciationRegimeSearchRepository) {
        this.depreciationRegimeRepository = depreciationRegimeRepository;
        this.depreciationRegimeMapper = depreciationRegimeMapper;
        this.depreciationRegimeSearchRepository = depreciationRegimeSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DepreciationRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DepreciationRegimeDTO> findByCriteria(DepreciationRegimeCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DepreciationRegime> specification = createSpecification(criteria);
        return depreciationRegimeMapper.toDto(depreciationRegimeRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DepreciationRegimeDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DepreciationRegimeDTO> findByCriteria(DepreciationRegimeCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DepreciationRegime> specification = createSpecification(criteria);
        return depreciationRegimeRepository.findAll(specification, page)
            .map(depreciationRegimeMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DepreciationRegimeCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DepreciationRegime> specification = createSpecification(criteria);
        return depreciationRegimeRepository.count(specification);
    }

    /**
     * Function to convert DepreciationRegimeCriteria to a {@link Specification}.
     */
    private Specification<DepreciationRegime> createSpecification(DepreciationRegimeCriteria criteria) {
        Specification<DepreciationRegime> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), DepreciationRegime_.id));
            }
            if (criteria.getAssetDecayType() != null) {
                specification = specification.and(buildSpecification(criteria.getAssetDecayType(), DepreciationRegime_.assetDecayType));
            }
            if (criteria.getDepreciationRate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciationRate(), DepreciationRegime_.depreciationRate));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), DepreciationRegime_.description));
            }
        }
        return specification;
    }
}
