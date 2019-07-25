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

import io.github.assets.domain.AssetAcquisition;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.AssetAcquisitionRepository;
import io.github.assets.repository.search.AssetAcquisitionSearchRepository;
import io.github.assets.service.dto.AssetAcquisitionCriteria;
import io.github.assets.service.dto.AssetAcquisitionDTO;
import io.github.assets.service.mapper.AssetAcquisitionMapper;

/**
 * Service for executing complex queries for {@link AssetAcquisition} entities in the database.
 * The main input is a {@link AssetAcquisitionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetAcquisitionDTO} or a {@link Page} of {@link AssetAcquisitionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetAcquisitionQueryService extends QueryService<AssetAcquisition> {

    private final Logger log = LoggerFactory.getLogger(AssetAcquisitionQueryService.class);

    private final AssetAcquisitionRepository assetAcquisitionRepository;

    private final AssetAcquisitionMapper assetAcquisitionMapper;

    private final AssetAcquisitionSearchRepository assetAcquisitionSearchRepository;

    public AssetAcquisitionQueryService(AssetAcquisitionRepository assetAcquisitionRepository, AssetAcquisitionMapper assetAcquisitionMapper, AssetAcquisitionSearchRepository assetAcquisitionSearchRepository) {
        this.assetAcquisitionRepository = assetAcquisitionRepository;
        this.assetAcquisitionMapper = assetAcquisitionMapper;
        this.assetAcquisitionSearchRepository = assetAcquisitionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AssetAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetAcquisitionDTO> findByCriteria(AssetAcquisitionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssetAcquisition> specification = createSpecification(criteria);
        return assetAcquisitionMapper.toDto(assetAcquisitionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetAcquisitionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetAcquisitionDTO> findByCriteria(AssetAcquisitionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssetAcquisition> specification = createSpecification(criteria);
        return assetAcquisitionRepository.findAll(specification, page)
            .map(assetAcquisitionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetAcquisitionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssetAcquisition> specification = createSpecification(criteria);
        return assetAcquisitionRepository.count(specification);
    }

    /**
     * Function to convert AssetAcquisitionCriteria to a {@link Specification}.
     */
    private Specification<AssetAcquisition> createSpecification(AssetAcquisitionCriteria criteria) {
        Specification<AssetAcquisition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AssetAcquisition_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), AssetAcquisition_.description));
            }
            if (criteria.getAcquisitionMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcquisitionMonth(), AssetAcquisition_.acquisitionMonth));
            }
            if (criteria.getAssetSerial() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetSerial(), AssetAcquisition_.assetSerial));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), AssetAcquisition_.serviceOutletCode));
            }
            if (criteria.getAcquisitionTransactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAcquisitionTransactionId(), AssetAcquisition_.acquisitionTransactionId));
            }
            if (criteria.getAssetCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCategoryId(), AssetAcquisition_.assetCategoryId));
            }
            if (criteria.getPurchaseAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseAmount(), AssetAcquisition_.purchaseAmount));
            }
            if (criteria.getAssetDealerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetDealerId(), AssetAcquisition_.assetDealerId));
            }
            if (criteria.getAssetInvoiceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetInvoiceId(), AssetAcquisition_.assetInvoiceId));
            }
        }
        return specification;
    }
}
