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

import io.github.assets.domain.CwipTransfer;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.CwipTransferRepository;
import io.github.assets.repository.search.CwipTransferSearchRepository;
import io.github.assets.service.dto.CwipTransferCriteria;
import io.github.assets.service.dto.CwipTransferDTO;
import io.github.assets.service.mapper.CwipTransferMapper;

/**
 * Service for executing complex queries for {@link CwipTransfer} entities in the database.
 * The main input is a {@link CwipTransferCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link CwipTransferDTO} or a {@link Page} of {@link CwipTransferDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class CwipTransferQueryService extends QueryService<CwipTransfer> {

    private final Logger log = LoggerFactory.getLogger(CwipTransferQueryService.class);

    private final CwipTransferRepository cwipTransferRepository;

    private final CwipTransferMapper cwipTransferMapper;

    private final CwipTransferSearchRepository cwipTransferSearchRepository;

    public CwipTransferQueryService(CwipTransferRepository cwipTransferRepository, CwipTransferMapper cwipTransferMapper, CwipTransferSearchRepository cwipTransferSearchRepository) {
        this.cwipTransferRepository = cwipTransferRepository;
        this.cwipTransferMapper = cwipTransferMapper;
        this.cwipTransferSearchRepository = cwipTransferSearchRepository;
    }

    /**
     * Return a {@link List} of {@link CwipTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<CwipTransferDTO> findByCriteria(CwipTransferCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<CwipTransfer> specification = createSpecification(criteria);
        return cwipTransferMapper.toDto(cwipTransferRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link CwipTransferDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<CwipTransferDTO> findByCriteria(CwipTransferCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<CwipTransfer> specification = createSpecification(criteria);
        return cwipTransferRepository.findAll(specification, page)
            .map(cwipTransferMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(CwipTransferCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<CwipTransfer> specification = createSpecification(criteria);
        return cwipTransferRepository.count(specification);
    }

    /**
     * Function to convert CwipTransferCriteria to a {@link Specification}.
     */
    private Specification<CwipTransfer> createSpecification(CwipTransferCriteria criteria) {
        Specification<CwipTransfer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), CwipTransfer_.id));
            }
            if (criteria.getTransferMonth() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferMonth(), CwipTransfer_.transferMonth));
            }
            if (criteria.getAssetSerialTag() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssetSerialTag(), CwipTransfer_.assetSerialTag));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), CwipTransfer_.serviceOutletCode));
            }
            if (criteria.getTransferTransactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferTransactionId(), CwipTransfer_.transferTransactionId));
            }
            if (criteria.getAssetCategoryId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAssetCategoryId(), CwipTransfer_.assetCategoryId));
            }
            if (criteria.getCwipTransactionId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCwipTransactionId(), CwipTransfer_.cwipTransactionId));
            }
            if (criteria.getTransferDetails() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTransferDetails(), CwipTransfer_.transferDetails));
            }
            if (criteria.getTransferAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransferAmount(), CwipTransfer_.transferAmount));
            }
            if (criteria.getDealerId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getDealerId(), CwipTransfer_.dealerId));
            }
            if (criteria.getTransactionInvoiceId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTransactionInvoiceId(), CwipTransfer_.transactionInvoiceId));
            }
        }
        return specification;
    }
}
