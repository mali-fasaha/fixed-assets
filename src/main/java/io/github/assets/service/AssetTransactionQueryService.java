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

import io.github.assets.domain.AssetTransaction;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.AssetTransactionRepository;
import io.github.assets.repository.search.AssetTransactionSearchRepository;
import io.github.assets.service.dto.AssetTransactionCriteria;
import io.github.assets.service.dto.AssetTransactionDTO;
import io.github.assets.service.mapper.AssetTransactionMapper;

/**
 * Service for executing complex queries for {@link AssetTransaction} entities in the database.
 * The main input is a {@link AssetTransactionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link AssetTransactionDTO} or a {@link Page} of {@link AssetTransactionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class AssetTransactionQueryService extends QueryService<AssetTransaction> {

    private final Logger log = LoggerFactory.getLogger(AssetTransactionQueryService.class);

    private final AssetTransactionRepository assetTransactionRepository;

    private final AssetTransactionMapper assetTransactionMapper;

    private final AssetTransactionSearchRepository assetTransactionSearchRepository;

    public AssetTransactionQueryService(AssetTransactionRepository assetTransactionRepository, AssetTransactionMapper assetTransactionMapper, AssetTransactionSearchRepository assetTransactionSearchRepository) {
        this.assetTransactionRepository = assetTransactionRepository;
        this.assetTransactionMapper = assetTransactionMapper;
        this.assetTransactionSearchRepository = assetTransactionSearchRepository;
    }

    /**
     * Return a {@link List} of {@link AssetTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<AssetTransactionDTO> findByCriteria(AssetTransactionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<AssetTransaction> specification = createSpecification(criteria);
        return assetTransactionMapper.toDto(assetTransactionRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link AssetTransactionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<AssetTransactionDTO> findByCriteria(AssetTransactionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<AssetTransaction> specification = createSpecification(criteria);
        return assetTransactionRepository.findAll(specification, page)
            .map(assetTransactionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(AssetTransactionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<AssetTransaction> specification = createSpecification(criteria);
        return assetTransactionRepository.count(specification);
    }

    /**
     * Function to convert AssetTransactionCriteria to a {@link Specification}.
     */
    private Specification<AssetTransaction> createSpecification(AssetTransactionCriteria criteria) {
        Specification<AssetTransaction> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), AssetTransaction_.id));
            }
            if (criteria.getTransactionReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransactionReference(), AssetTransaction_.transactionReference));
            }
            if (criteria.getTransactionDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionDate(), AssetTransaction_.transactionDate));
            }
            if (criteria.getScannedDocumentId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getScannedDocumentId(), AssetTransaction_.scannedDocumentId));
            }
            if (criteria.getTransactionApprovalId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionApprovalId(), AssetTransaction_.transactionApprovalId));
            }
        }
        return specification;
    }
}
