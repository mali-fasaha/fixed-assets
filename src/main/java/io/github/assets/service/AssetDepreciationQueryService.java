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

import io.github.assets.domain.AssetDepreciation;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.AssetDepreciationRepository;
import io.github.assets.repository.search.AssetDepreciationSearchRepository;
import io.github.assets.service.dto.AssetDepreciationCriteria;
import io.github.assets.service.dto.AssetDepreciationDTO;
import io.github.assets.service.mapper.AssetDepreciationMapper;

/**
 * Service for executing complex queries for {@link AssetDepreciation} entities in the database.
 * The main input is a {@link AssetDepreciationCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetDepreciationDTO} or a {@link Page} of {@link AssetDepreciationDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetDepreciationQueryService extends QueryService<AssetDepreciation> {

    private final Logger log = LoggerFactory.getLogger(AssetDepreciationQueryService.class);

    private final AssetDepreciationRepository assetDepreciationRepository;

    private final AssetDepreciationMapper assetDepreciationMapper;

    private final AssetDepreciationSearchRepository assetDepreciationSearchRepository;

    public AssetDepreciationQueryService(AssetDepreciationRepository assetDepreciationRepository, AssetDepreciationMapper assetDepreciationMapper, AssetDepreciationSearchRepository assetDepreciationSearchRepository) {
        this.assetDepreciationRepository = assetDepreciationRepository;
        this.assetDepreciationMapper = assetDepreciationMapper;
        this.assetDepreciationSearchRepository = assetDepreciationSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AssetDepreciationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetDepreciationDTO> findByCriteria(AssetDepreciationCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssetDepreciation> specification = createSpecification(criteria);
        return assetDepreciationMapper.toDto(assetDepreciationRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetDepreciationDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetDepreciationDTO> findByCriteria(AssetDepreciationCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssetDepreciation> specification = createSpecification(criteria);
        return assetDepreciationRepository.findAll(specification, page)
            .map(assetDepreciationMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetDepreciationCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssetDepreciation> specification = createSpecification(criteria);
        return assetDepreciationRepository.count(specification);
    }

    /**
     * Function to convert AssetDepreciationCriteria to a {@link Specification}.
     */
    private Specification<AssetDepreciation> createSpecification(AssetDepreciationCriteria criteria) {
        Specification<AssetDepreciation> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AssetDepreciation_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AssetDepreciation_.description));
            }
            if (criteria.getDepreciationAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciationAmount(), AssetDepreciation_.depreciationAmount));
            }
            if (criteria.getDepreciationDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDepreciationDate(), AssetDepreciation_.depreciationDate));
            }
            if (criteria.getCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCategoryId(), AssetDepreciation_.categoryId));
            }
            if (criteria.getAssetItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetItemId(), AssetDepreciation_.assetItemId));
            }
        }
        return specification;
    }
}
