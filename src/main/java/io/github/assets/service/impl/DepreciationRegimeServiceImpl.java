package io.github.assets.service.impl;

import io.github.assets.service.DepreciationRegimeService;
import io.github.assets.domain.DepreciationRegime;
import io.github.assets.repository.DepreciationRegimeRepository;
import io.github.assets.repository.search.DepreciationRegimeSearchRepository;
import io.github.assets.service.dto.DepreciationRegimeDTO;
import io.github.assets.service.mapper.DepreciationRegimeMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link DepreciationRegime}.
 */
@Service
@Transactional
public class DepreciationRegimeServiceImpl implements DepreciationRegimeService {

    private final Logger log = LoggerFactory.getLogger(DepreciationRegimeServiceImpl.class);

    private final DepreciationRegimeRepository depreciationRegimeRepository;

    private final DepreciationRegimeMapper depreciationRegimeMapper;

    private final DepreciationRegimeSearchRepository depreciationRegimeSearchRepository;

    public DepreciationRegimeServiceImpl(DepreciationRegimeRepository depreciationRegimeRepository, DepreciationRegimeMapper depreciationRegimeMapper, DepreciationRegimeSearchRepository depreciationRegimeSearchRepository) {
        this.depreciationRegimeRepository = depreciationRegimeRepository;
        this.depreciationRegimeMapper = depreciationRegimeMapper;
        this.depreciationRegimeSearchRepository = depreciationRegimeSearchRepository;
    }

    /**
     * Save a depreciationRegime.
     *
     * @param depreciationRegimeDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DepreciationRegimeDTO save(DepreciationRegimeDTO depreciationRegimeDTO) {
        log.debug("Request to save DepreciationRegime : {}", depreciationRegimeDTO);
        DepreciationRegime depreciationRegime = depreciationRegimeMapper.toEntity(depreciationRegimeDTO);
        depreciationRegime = depreciationRegimeRepository.save(depreciationRegime);
        DepreciationRegimeDTO result = depreciationRegimeMapper.toDto(depreciationRegime);
        depreciationRegimeSearchRepository.save(depreciationRegime);
        return result;
    }

    /**
     * Get all the depreciationRegimes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DepreciationRegimeDTO> findAll(Pageable pageable) {
        log.debug("Request to get all DepreciationRegimes");
        return depreciationRegimeRepository.findAll(pageable)
            .map(depreciationRegimeMapper::toDto);
    }


    /**
     * Get one depreciationRegime by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DepreciationRegimeDTO> findOne(Long id) {
        log.debug("Request to get DepreciationRegime : {}", id);
        return depreciationRegimeRepository.findById(id)
            .map(depreciationRegimeMapper::toDto);
    }

    /**
     * Delete the depreciationRegime by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DepreciationRegime : {}", id);
        depreciationRegimeRepository.deleteById(id);
        depreciationRegimeSearchRepository.deleteById(id);
    }

    /**
     * Search for the depreciationRegime corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DepreciationRegimeDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of DepreciationRegimes for query {}", query);
        return depreciationRegimeSearchRepository.search(queryStringQuery(query), pageable)
            .map(depreciationRegimeMapper::toDto);
    }
}
