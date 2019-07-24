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

import io.github.assets.domain.FixedAssetItem;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.FixedAssetItemRepository;
import io.github.assets.repository.search.FixedAssetItemSearchRepository;
import io.github.assets.service.dto.FixedAssetItemCriteria;
import io.github.assets.service.dto.FixedAssetItemDTO;
import io.github.assets.service.mapper.FixedAssetItemMapper;

/**
 * Service for executing complex queries for {@link FixedAssetItem} entities in the database.
 * The main input is a {@link FixedAssetItemCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FixedAssetItemDTO} or a {@link Page} of {@link FixedAssetItemDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetItemQueryService extends QueryService<FixedAssetItem> {

    private final Logger log = LoggerFactory.getLogger(FixedAssetItemQueryService.class);

    private final FixedAssetItemRepository fixedAssetItemRepository;

    private final FixedAssetItemMapper fixedAssetItemMapper;

    private final FixedAssetItemSearchRepository fixedAssetItemSearchRepository;

    public FixedAssetItemQueryService(FixedAssetItemRepository fixedAssetItemRepository, FixedAssetItemMapper fixedAssetItemMapper, FixedAssetItemSearchRepository fixedAssetItemSearchRepository) {
        this.fixedAssetItemRepository = fixedAssetItemRepository;
        this.fixedAssetItemMapper = fixedAssetItemMapper;
        this.fixedAssetItemSearchRepository = fixedAssetItemSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FixedAssetItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FixedAssetItemDTO> findByCriteria(FixedAssetItemCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FixedAssetItem> specification = createSpecification(criteria);
        return fixedAssetItemMapper.toDto(fixedAssetItemRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FixedAssetItemDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedAssetItemDTO> findByCriteria(FixedAssetItemCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FixedAssetItem> specification = createSpecification(criteria);
        return fixedAssetItemRepository.findAll(specification, page)
            .map(fixedAssetItemMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixedAssetItemCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FixedAssetItem> specification = createSpecification(criteria);
        return fixedAssetItemRepository.count(specification);
    }

    /**
     * Function to convert FixedAssetItemCriteria to a {@link Specification}.
     */
    private Specification<FixedAssetItem> createSpecification(FixedAssetItemCriteria criteria) {
        Specification<FixedAssetItem> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FixedAssetItem_.id));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), FixedAssetItem_.serviceOutletCode));
            }
            if (criteria.getAssetCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCategoryId(), FixedAssetItem_.assetCategoryId));
            }
            if (criteria.getFixedAssetSerialCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFixedAssetSerialCode(), FixedAssetItem_.fixedAssetSerialCode));
            }
            if (criteria.getFixedAssetDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFixedAssetDescription(), FixedAssetItem_.fixedAssetDescription));
            }
            if (criteria.getPurchaseDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseDate(), FixedAssetItem_.purchaseDate));
            }
            if (criteria.getPurchaseCost() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseCost(), FixedAssetItem_.purchaseCost));
            }
            if (criteria.getPurchaseTransactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPurchaseTransactionId(), FixedAssetItem_.purchaseTransactionId));
            }
            if (criteria.getOwnershipDocumentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getOwnershipDocumentId(), FixedAssetItem_.ownershipDocumentId));
            }
        }
        return specification;
    }
}
