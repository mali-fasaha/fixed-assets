package io.github.assets.service.impl;

import io.github.assets.service.AssetDisposalService;
import io.github.assets.domain.AssetDisposal;
import io.github.assets.repository.AssetDisposalRepository;
import io.github.assets.repository.search.AssetDisposalSearchRepository;
import io.github.assets.service.dto.AssetDisposalDTO;
import io.github.assets.service.mapper.AssetDisposalMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link AssetDisposal}.
 */
@Service
@Transactional
public class AssetDisposalServiceImpl implements AssetDisposalService {

    private final Logger log = LoggerFactory.getLogger(AssetDisposalServiceImpl.class);

    private final AssetDisposalRepository assetDisposalRepository;

    private final AssetDisposalMapper assetDisposalMapper;

    private final AssetDisposalSearchRepository assetDisposalSearchRepository;

    public AssetDisposalServiceImpl(AssetDisposalRepository assetDisposalRepository, AssetDisposalMapper assetDisposalMapper, AssetDisposalSearchRepository assetDisposalSearchRepository) {
        this.assetDisposalRepository = assetDisposalRepository;
        this.assetDisposalMapper = assetDisposalMapper;
        this.assetDisposalSearchRepository = assetDisposalSearchRepository;
    }

    /**
     * Save a assetDisposal.
     *
     * @param assetDisposalDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public AssetDisposalDTO save(AssetDisposalDTO assetDisposalDTO) {
        log.debug("Request to save AssetDisposal : {}", assetDisposalDTO);
        AssetDisposal assetDisposal = assetDisposalMapper.toEntity(assetDisposalDTO);
        assetDisposal = assetDisposalRepository.save(assetDisposal);
        AssetDisposalDTO result = assetDisposalMapper.toDto(assetDisposal);
        assetDisposalSearchRepository.save(assetDisposal);
        return result;
    }

    /**
     * Get all the assetDisposals.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssetDisposalDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AssetDisposals");
        return assetDisposalRepository.findAll(pageable)
            .map(assetDisposalMapper::toDto);
    }


    /**
     * Get one assetDisposal by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AssetDisposalDTO> findOne(Long id) {
        log.debug("Request to get AssetDisposal : {}", id);
        return assetDisposalRepository.findById(id)
            .map(assetDisposalMapper::toDto);
    }

    /**
     * Delete the assetDisposal by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AssetDisposal : {}", id);
        assetDisposalRepository.deleteById(id);
        assetDisposalSearchRepository.deleteById(id);
    }

    /**
     * Search for the assetDisposal corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AssetDisposalDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AssetDisposals for query {}", query);
        return assetDisposalSearchRepository.search(queryStringQuery(query), pageable)
            .map(assetDisposalMapper::toDto);
    }
}
