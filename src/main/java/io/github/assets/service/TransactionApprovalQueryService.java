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

import io.github.assets.domain.TransactionApproval;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.TransactionApprovalRepository;
import io.github.assets.repository.search.TransactionApprovalSearchRepository;
import io.github.assets.service.dto.TransactionApprovalCriteria;
import io.github.assets.service.dto.TransactionApprovalDTO;
import io.github.assets.service.mapper.TransactionApprovalMapper;

/**
 * Service for executing complex queries for {@link TransactionApproval} entities in the database.
 * The main input is a {@link TransactionApprovalCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link TransactionApprovalDTO} or a {@link Page} of {@link TransactionApprovalDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class TransactionApprovalQueryService extends QueryService<TransactionApproval> {

    private final Logger log = LoggerFactory.getLogger(TransactionApprovalQueryService.class);

    private final TransactionApprovalRepository transactionApprovalRepository;

    private final TransactionApprovalMapper transactionApprovalMapper;

    private final TransactionApprovalSearchRepository transactionApprovalSearchRepository;

    public TransactionApprovalQueryService(TransactionApprovalRepository transactionApprovalRepository, TransactionApprovalMapper transactionApprovalMapper, TransactionApprovalSearchRepository transactionApprovalSearchRepository) {
        this.transactionApprovalRepository = transactionApprovalRepository;
        this.transactionApprovalMapper = transactionApprovalMapper;
        this.transactionApprovalSearchRepository = transactionApprovalSearchRepository;
    }

    /**
     * Return a {@link List} of {@link TransactionApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<TransactionApprovalDTO> findByCriteria(TransactionApprovalCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<TransactionApproval> specification = createSpecification(criteria);
        return transactionApprovalMapper.toDto(transactionApprovalRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link TransactionApprovalDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<TransactionApprovalDTO> findByCriteria(TransactionApprovalCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<TransactionApproval> specification = createSpecification(criteria);
        return transactionApprovalRepository.findAll(specification, page)
            .map(transactionApprovalMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(TransactionApprovalCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<TransactionApproval> specification = createSpecification(criteria);
        return transactionApprovalRepository.count(specification);
    }

    /**
     * Function to convert TransactionApprovalCriteria to a {@link Specification}.
     */
    private Specification<TransactionApproval> createSpecification(TransactionApprovalCriteria criteria) {
        Specification<TransactionApproval> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), TransactionApproval_.id));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), TransactionApproval_.description));
            }
            if (criteria.getRequestedBy() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getRequestedBy(), TransactionApproval_.requestedBy));
            }
            if (criteria.getRecommendedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getRecommendedBy(), TransactionApproval_.recommendedBy));
            }
            if (criteria.getReviewedBy() != null) {
                specification = specification.and(buildStringSpecification(criteria.getReviewedBy(), TransactionApproval_.reviewedBy));
            }
            if (criteria.getFirstApprover() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFirstApprover(), TransactionApproval_.firstApprover));
            }
            if (criteria.getSecondApprover() != null) {
                specification = specification.and(buildStringSpecification(criteria.getSecondApprover(), TransactionApproval_.secondApprover));
            }
            if (criteria.getThirdApprover() != null) {
                specification = specification.and(buildStringSpecification(criteria.getThirdApprover(), TransactionApproval_.thirdApprover));
            }
            if (criteria.getFourthApprover() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFourthApprover(), TransactionApproval_.fourthApprover));
            }
        }
        return specification;
    }
}
