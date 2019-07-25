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

import io.github.assets.domain.AssetDisposal;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.AssetDisposalRepository;
import io.github.assets.repository.search.AssetDisposalSearchRepository;
import io.github.assets.service.dto.AssetDisposalCriteria;
import io.github.assets.service.dto.AssetDisposalDTO;
import io.github.assets.service.mapper.AssetDisposalMapper;

/**
 * Service for executing complex queries for {@link AssetDisposal} entities in the database.
 * The main input is a {@link AssetDisposalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetDisposalDTO} or a {@link Page} of {@link AssetDisposalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetDisposalQueryService extends QueryService<AssetDisposal> {

    private final Logger log = LoggerFactory.getLogger(AssetDisposalQueryService.class);

    private final AssetDisposalRepository assetDisposalRepository;

    private final AssetDisposalMapper assetDisposalMapper;

    private final AssetDisposalSearchRepository assetDisposalSearchRepository;

    public AssetDisposalQueryService(AssetDisposalRepository assetDisposalRepository, AssetDisposalMapper assetDisposalMapper, AssetDisposalSearchRepository assetDisposalSearchRepository) {
        this.assetDisposalRepository = assetDisposalRepository;
        this.assetDisposalMapper = assetDisposalMapper;
        this.assetDisposalSearchRepository = assetDisposalSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AssetDisposalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetDisposalDTO> findByCriteria(AssetDisposalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssetDisposal> specification = createSpecification(criteria);
        return assetDisposalMapper.toDto(assetDisposalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetDisposalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetDisposalDTO> findByCriteria(AssetDisposalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssetDisposal> specification = createSpecification(criteria);
        return assetDisposalRepository.findAll(specification, page)
            .map(assetDisposalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetDisposalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssetDisposal> specification = createSpecification(criteria);
        return assetDisposalRepository.count(specification);
    }

    /**
     * Function to convert AssetDisposalCriteria to a {@link Specification}.
     */
    private Specification<AssetDisposal> createSpecification(AssetDisposalCriteria criteria) {
        Specification<AssetDisposal> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AssetDisposal_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AssetDisposal_.description));
            }
            if (criteria.getDisposalMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisposalMonth(), AssetDisposal_.disposalMonth));
            }
            if (criteria.getAssetCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCategoryId(), AssetDisposal_.assetCategoryId));
            }
            if (criteria.getAssetItemId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetItemId(), AssetDisposal_.assetItemId));
            }
            if (criteria.getDisposalProceeds() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDisposalProceeds(), AssetDisposal_.disposalProceeds));
            }
            if (criteria.getNetBookValue() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getNetBookValue(), AssetDisposal_.netBookValue));
            }
            if (criteria.getProfitOnDisposal() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getProfitOnDisposal(), AssetDisposal_.profitOnDisposal));
            }
            if (criteria.getScannedDocumentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScannedDocumentId(), AssetDisposal_.scannedDocumentId));
            }
            if (criteria.getAssetDealerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetDealerId(), AssetDisposal_.assetDealerId));
            }
        }
        return specification;
    }
}
