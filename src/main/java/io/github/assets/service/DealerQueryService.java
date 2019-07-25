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

import io.github.assets.domain.Dealer;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.DealerRepository;
import io.github.assets.repository.search.DealerSearchRepository;
import io.github.assets.service.dto.DealerCriteria;
import io.github.assets.service.dto.DealerDTO;
import io.github.assets.service.mapper.DealerMapper;

/**
 * Service for executing complex queries for {@link Dealer} entities in the database.
 * The main input is a {@link DealerCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DealerDTO} or a {@link Page} of {@link DealerDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DealerQueryService extends QueryService<Dealer> {

    private final Logger log = LoggerFactory.getLogger(DealerQueryService.class);

    private final DealerRepository dealerRepository;

    private final DealerMapper dealerMapper;

    private final DealerSearchRepository dealerSearchRepository;

    public DealerQueryService(DealerRepository dealerRepository, DealerMapper dealerMapper, DealerSearchRepository dealerSearchRepository) {
        this.dealerRepository = dealerRepository;
        this.dealerMapper = dealerMapper;
        this.dealerSearchRepository = dealerSearchRepository;
    }

    /**
     * Return a {@link List} of {@link DealerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DealerDTO> findByCriteria(DealerCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Dealer> specification = createSpecification(criteria);
        return dealerMapper.toDto(dealerRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link DealerDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DealerDTO> findByCriteria(DealerCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Dealer> specification = createSpecification(criteria);
        return dealerRepository.findAll(specification, page)
            .map(dealerMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DealerCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Dealer> specification = createSpecification(criteria);
        return dealerRepository.count(specification);
    }

    /**
     * Function to convert DealerCriteria to a {@link Specification}.
     */
    private Specification<Dealer> createSpecification(DealerCriteria criteria) {
        Specification<Dealer> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), Dealer_.id));
            }
            if (criteria.getTitle() != null) {
                specification = specification.and(buildSpecification(criteria.getTitle(), Dealer_.title));
            }
            if (criteria.getDealerName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealerName(), Dealer_.dealerName));
            }
            if (criteria.getDealerAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealerAddress(), Dealer_.dealerAddress));
            }
            if (criteria.getDealerPhoneNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealerPhoneNumber(), Dealer_.dealerPhoneNumber));
            }
            if (criteria.getDealerEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDealerEmail(), Dealer_.dealerEmail));
            }
            if (criteria.getBankName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankName(), Dealer_.bankName));
            }
            if (criteria.getBankAccountNumber() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankAccountNumber(), Dealer_.bankAccountNumber));
            }
            if (criteria.getBankBranch() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankBranch(), Dealer_.bankBranch));
            }
            if (criteria.getBankSwiftCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankSwiftCode(), Dealer_.bankSwiftCode));
            }
            if (criteria.getBankPhysicalAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getBankPhysicalAddress(), Dealer_.bankPhysicalAddress));
            }
            if (criteria.getDomicile() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDomicile(), Dealer_.domicile));
            }
            if (criteria.getTaxAuthorityRef() != null) {
                specification = specification.and(buildStringSpecification(criteria.getTaxAuthorityRef(), Dealer_.taxAuthorityRef));
            }
            if (criteria.getFixedAssetInvoiceId() != null) {
                specification = specification.and(buildSpecification(criteria.getFixedAssetInvoiceId(),
                    root -> root.join(Dealer_.fixedAssetInvoices, JoinType.LEFT).get(FixedAssetInvoice_.id)));
            }
        }
        return specification;
    }
}
