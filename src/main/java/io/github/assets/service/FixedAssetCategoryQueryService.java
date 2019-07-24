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

import io.github.assets.domain.FixedAssetCategory;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.FixedAssetCategoryRepository;
import io.github.assets.repository.search.FixedAssetCategorySearchRepository;
import io.github.assets.service.dto.FixedAssetCategoryCriteria;
import io.github.assets.service.dto.FixedAssetCategoryDTO;
import io.github.assets.service.mapper.FixedAssetCategoryMapper;

/**
 * Service for executing complex queries for {@link FixedAssetCategory} entities in the database.
 * The main input is a {@link FixedAssetCategoryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FixedAssetCategoryDTO} or a {@link Page} of {@link FixedAssetCategoryDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetCategoryQueryService extends QueryService<FixedAssetCategory> {

    private final Logger log = LoggerFactory.getLogger(FixedAssetCategoryQueryService.class);

    private final FixedAssetCategoryRepository fixedAssetCategoryRepository;

    private final FixedAssetCategoryMapper fixedAssetCategoryMapper;

    private final FixedAssetCategorySearchRepository fixedAssetCategorySearchRepository;

    public FixedAssetCategoryQueryService(FixedAssetCategoryRepository fixedAssetCategoryRepository, FixedAssetCategoryMapper fixedAssetCategoryMapper, FixedAssetCategorySearchRepository fixedAssetCategorySearchRepository) {
        this.fixedAssetCategoryRepository = fixedAssetCategoryRepository;
        this.fixedAssetCategoryMapper = fixedAssetCategoryMapper;
        this.fixedAssetCategorySearchRepository = fixedAssetCategorySearchRepository;
    }

    /**
     * Return a {@link List} of {@link FixedAssetCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FixedAssetCategoryDTO> findByCriteria(FixedAssetCategoryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FixedAssetCategory> specification = createSpecification(criteria);
        return fixedAssetCategoryMapper.toDto(fixedAssetCategoryRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FixedAssetCategoryDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedAssetCategoryDTO> findByCriteria(FixedAssetCategoryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FixedAssetCategory> specification = createSpecification(criteria);
        return fixedAssetCategoryRepository.findAll(specification, page)
            .map(fixedAssetCategoryMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixedAssetCategoryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FixedAssetCategory> specification = createSpecification(criteria);
        return fixedAssetCategoryRepository.count(specification);
    }

    /**
     * Function to convert FixedAssetCategoryCriteria to a {@link Specification}.
     */
    private Specification<FixedAssetCategory> createSpecification(FixedAssetCategoryCriteria criteria) {
        Specification<FixedAssetCategory> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FixedAssetCategory_.id));
            }
            if (criteria.getCategoryName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryName(), FixedAssetCategory_.categoryName));
            }
            if (criteria.getCategoryDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryDescription(), FixedAssetCategory_.categoryDescription));
            }
            if (criteria.getCategoryAssetCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryAssetCode(), FixedAssetCategory_.categoryAssetCode));
            }
            if (criteria.getCategoryDepreciationCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getCategoryDepreciationCode(), FixedAssetCategory_.categoryDepreciationCode));
            }
            if (criteria.getDepreciationRegimeId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciationRegimeId(), FixedAssetCategory_.depreciationRegimeId));
            }
        }
        return specification;
    }
}
