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

import io.github.assets.domain.ServiceOutlet;
import io.github.assets.domain.*; // for static metamodels
import io.github.assets.repository.ServiceOutletRepository;
import io.github.assets.repository.search.ServiceOutletSearchRepository;
import io.github.assets.service.dto.ServiceOutletCriteria;
import io.github.assets.service.dto.ServiceOutletDTO;
import io.github.assets.service.mapper.ServiceOutletMapper;

/**
 * Service for executing complex queries for {@link ServiceOutlet} entities in the database.
 * The main input is a {@link ServiceOutletCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link ServiceOutletDTO} or a {@link Page} of {@link ServiceOutletDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class ServiceOutletQueryService extends QueryService<ServiceOutlet> {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletQueryService.class);

    private final ServiceOutletRepository serviceOutletRepository;

    private final ServiceOutletMapper serviceOutletMapper;

    private final ServiceOutletSearchRepository serviceOutletSearchRepository;

    public ServiceOutletQueryService(ServiceOutletRepository serviceOutletRepository, ServiceOutletMapper serviceOutletMapper, ServiceOutletSearchRepository serviceOutletSearchRepository) {
        this.serviceOutletRepository = serviceOutletRepository;
        this.serviceOutletMapper = serviceOutletMapper;
        this.serviceOutletSearchRepository = serviceOutletSearchRepository;
    }

    /**
     * Return a {@link List} of {@link ServiceOutletDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<ServiceOutletDTO> findByCriteria(ServiceOutletCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<ServiceOutlet> specification = createSpecification(criteria);
        return serviceOutletMapper.toDto(serviceOutletRepository.findAll(specification));
    }

    /**
     * Return a {@link Page} of {@link ServiceOutletDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<ServiceOutletDTO> findByCriteria(ServiceOutletCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<ServiceOutlet> specification = createSpecification(criteria);
        return serviceOutletRepository.findAll(specification, page)
            .map(serviceOutletMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(ServiceOutletCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<ServiceOutlet> specification = createSpecification(criteria);
        return serviceOutletRepository.count(specification);
    }

    /**
     * Function to convert ServiceOutletCriteria to a {@link Specification}.
     */
    private Specification<ServiceOutlet> createSpecification(ServiceOutletCriteria criteria) {
        Specification<ServiceOutlet> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildSpecification(criteria.getId(), ServiceOutlet_.id));
            }
            if (criteria.getServiceOutletCode() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletCode(), ServiceOutlet_.serviceOutletCode));
            }
            if (criteria.getServiceOutletDesignation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getServiceOutletDesignation(), ServiceOutlet_.serviceOutletDesignation));
            }
            if (criteria.getDescription() != null) {
                specification = specification.and(buildStringSpecification(criteria.getDescription(), ServiceOutlet_.description));
            }
            if (criteria.getLocation() != null) {
                specification = specification.and(buildStringSpecification(criteria.getLocation(), ServiceOutlet_.location));
            }
        }
        return specification;
    }
}
