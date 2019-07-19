package io.github.assets.service.impl;

import io.github.assets.service.ServiceOutletService;
import io.github.assets.domain.ServiceOutlet;
import io.github.assets.repository.ServiceOutletRepository;
import io.github.assets.repository.search.ServiceOutletSearchRepository;
import io.github.assets.service.dto.ServiceOutletDTO;
import io.github.assets.service.mapper.ServiceOutletMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ServiceOutlet}.
 */
@Service
@Transactional
public class ServiceOutletServiceImpl implements ServiceOutletService {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletServiceImpl.class);

    private final ServiceOutletRepository serviceOutletRepository;

    private final ServiceOutletMapper serviceOutletMapper;

    private final ServiceOutletSearchRepository serviceOutletSearchRepository;

    public ServiceOutletServiceImpl(ServiceOutletRepository serviceOutletRepository, ServiceOutletMapper serviceOutletMapper, ServiceOutletSearchRepository serviceOutletSearchRepository) {
        this.serviceOutletRepository = serviceOutletRepository;
        this.serviceOutletMapper = serviceOutletMapper;
        this.serviceOutletSearchRepository = serviceOutletSearchRepository;
    }

    /**
     * Save a serviceOutlet.
     *
     * @param serviceOutletDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ServiceOutletDTO save(ServiceOutletDTO serviceOutletDTO) {
        log.debug("Request to save ServiceOutlet : {}", serviceOutletDTO);
        ServiceOutlet serviceOutlet = serviceOutletMapper.toEntity(serviceOutletDTO);
        serviceOutlet = serviceOutletRepository.save(serviceOutlet);
        ServiceOutletDTO result = serviceOutletMapper.toDto(serviceOutlet);
        serviceOutletSearchRepository.save(serviceOutlet);
        return result;
    }

    /**
     * Get all the serviceOutlets.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOutletDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ServiceOutlets");
        return serviceOutletRepository.findAll(pageable)
            .map(serviceOutletMapper::toDto);
    }


    /**
     * Get one serviceOutlet by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ServiceOutletDTO> findOne(Long id) {
        log.debug("Request to get ServiceOutlet : {}", id);
        return serviceOutletRepository.findById(id)
            .map(serviceOutletMapper::toDto);
    }

    /**
     * Delete the serviceOutlet by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ServiceOutlet : {}", id);
        serviceOutletRepository.deleteById(id);
        serviceOutletSearchRepository.deleteById(id);
    }

    /**
     * Search for the serviceOutlet corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ServiceOutletDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ServiceOutlets for query {}", query);
        return serviceOutletSearchRepository.search(queryStringQuery(query), pageable)
            .map(serviceOutletMapper::toDto);
    }
}
