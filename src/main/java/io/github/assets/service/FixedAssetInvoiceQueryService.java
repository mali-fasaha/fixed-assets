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

import io.github.assets.domain.FixedAssetInvoice;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.FixedAssetInvoiceRepository;
import io.github.assets.repository.search.FixedAssetInvoiceSearchRepository;
import io.github.assets.service.dto.FixedAssetInvoiceCriteria;
import io.github.assets.service.dto.FixedAssetInvoiceDTO;
import io.github.assets.service.mapper.FixedAssetInvoiceMapper;

/**
 * Service for executing complex queries for {@link FixedAssetInvoice} entities in the database.
 * The main input is a {@link FixedAssetInvoiceCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link FixedAssetInvoiceDTO} or a {@link Page} of {@link FixedAssetInvoiceDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FixedAssetInvoiceQueryService extends QueryService<FixedAssetInvoice> {

    private final Logger log = LoggerFactory.getLogger(FixedAssetInvoiceQueryService.class);

    private final FixedAssetInvoiceRepository fixedAssetInvoiceRepository;

    private final FixedAssetInvoiceMapper fixedAssetInvoiceMapper;

    private final FixedAssetInvoiceSearchRepository fixedAssetInvoiceSearchRepository;

    public FixedAssetInvoiceQueryService(FixedAssetInvoiceRepository fixedAssetInvoiceRepository, FixedAssetInvoiceMapper fixedAssetInvoiceMapper, FixedAssetInvoiceSearchRepository fixedAssetInvoiceSearchRepository) {
        this.fixedAssetInvoiceRepository = fixedAssetInvoiceRepository;
        this.fixedAssetInvoiceMapper = fixedAssetInvoiceMapper;
        this.fixedAssetInvoiceSearchRepository = fixedAssetInvoiceSearchRepository;
    }

    /**
     * Return a {@link List} of {@link FixedAssetInvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<FixedAssetInvoiceDTO> findByCriteria(FixedAssetInvoiceCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<FixedAssetInvoice> specification = createSpecification(criteria);
        return fixedAssetInvoiceMapper.toDto(fixedAssetInvoiceRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link FixedAssetInvoiceDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FixedAssetInvoiceDTO> findByCriteria(FixedAssetInvoiceCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<FixedAssetInvoice> specification = createSpecification(criteria);
        return fixedAssetInvoiceRepository.findAll(specification, page)
            .map(fixedAssetInvoiceMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FixedAssetInvoiceCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<FixedAssetInvoice> specification = createSpecification(criteria);
        return fixedAssetInvoiceRepository.count(specification);
    }

    /**
     * Function to convert FixedAssetInvoiceCriteria to a {@link Specification}.
     */
    private Specification<FixedAssetInvoice> createSpecification(FixedAssetInvoiceCriteria criteria) {
        Specification<FixedAssetInvoice> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), FixedAssetInvoice_.id));
            }
            if (criteria.getInvoiceReference() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInvoiceReference(), FixedAssetInvoice_.invoiceReference));
            }
            if (criteria.getInvoiceDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceDate(), FixedAssetInvoice_.invoiceDate));
            }
            if (criteria.getInvoiceAmount() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getInvoiceAmount(), FixedAssetInvoice_.invoiceAmount));
            }
            if (criteria.getIsProforma() != null) {
                specification = specification.and(buildSpecification(criteria.getIsProforma(), FixedAssetInvoice_.isProforma));
            }
            if (criteria.getIsCreditNote() != null) {
                specification = specification.and(buildSpecification(criteria.getIsCreditNote(), FixedAssetInvoice_.isCreditNote));
            }
            if (criteria.getDealerId() != null) {
                specification = specification.and(buildSpecification(criteria.getDealerId(),
                    root -> root.join(FixedAssetInvoice_.dealer, JoinType.LEFT).get(Dealer_.id)));
            }
        }
        return specification;
    }
}
