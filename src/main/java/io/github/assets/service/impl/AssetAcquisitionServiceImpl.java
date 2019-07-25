package io.github.assets.service.impl;

import io.github.assets.service.AssetAcquisitionService;
import io.github.assets.domain.AssetAcquisition;
import io.github.assets.repository.AssetAcquisitionRepository;
import io.github.assets.repository.search.AssetAcquisitionSearchRepository;
import io.github.assets.service.dto.AssetAcquisitionDTO;
import io.github.assets.service.mapper.AssetAcquisitionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AssetAcquisition}.
 */
@Service
@Transactional
public class AssetAcquisitionServiceImpl implements AssetAcquisitionService {

    private final Logger log = LoggerFactory.getLogger(AssetAcquisitionServiceImpl.class);

    private final AssetAcquisitionRepository assetAcquisitionRepository;

    private final AssetAcquisitionMapper assetAcquisitionMapper;

    private final AssetAcquisitionSearchRepository assetAcquisitionSearchRepository;

    public AssetAcquisitionServiceImpl(AssetAcquisitionRepository assetAcquisitionRepository, AssetAcquisitionMapper assetAcquisitionMapper, AssetAcquisitionSearchRepository assetAcquisitionSearchRepository) {
        this.assetAcquisitionRepository = assetAcquisitionRepository;
        this.assetAcquisitionMapper = assetAcquisitionMapper;
        this.assetAcquisitionSearchRepository = assetAcquisitionSearchRepository;
    }

    /**
     * Save a assetAcquisition.
     *
     * @param assetAcquisitionDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AssetAcquisitionDTO save(AssetAcquisitionDTO assetAcquisitionDTO) {
        log.debug("Request to save AssetAcquisition : {}", assetAcquisitionDTO);
        AssetAcquisition assetAcquisition = assetAcquisitionMapper.toEntity(assetAcquisitionDTO);
        assetAcquisition = assetAcquisitionRepository.save(assetAcquisition);
        AssetAcquisitionDTO result = assetAcquisitionMapper.toDto(assetAcquisition);
        assetAcquisitionSearchRepository.save(assetAcquisition);
        return result;
    }

    /**
     * Get all the assetAcquisitions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssetAcquisitionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetAcquisitions");
        return assetAcquisitionRepository.findAll(pageable)
            .map(assetAcquisitionMapper::toDto);
    }


    /**
     * Get one assetAcquisition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AssetAcquisitionDTO> findOne(Long id) {
        log.debug("Request to get AssetAcquisition : {}", id);
        return assetAcquisitionRepository.findById(id)
            .map(assetAcquisitionMapper::toDto);
    }

    /**
     * Delete the assetAcquisition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssetAcquisition : {}", id);
        assetAcquisitionRepository.deleteById(id);
        assetAcquisitionSearchRepository.deleteById(id);
    }

    /**
     * Search for the assetAcquisition corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssetAcquisitionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AssetAcquisitions for query {}", query);
        return assetAcquisitionSearchRepository.search(queryStringQuery(query), pageable)
            .map(assetAcquisitionMapper::toDto);
    }
}
