package io.github.assets.service.impl;

import io.github.assets.service.CwipTransferService;
import io.github.assets.domain.CwipTransfer;
import io.github.assets.repository.CwipTransferRepository;
import io.github.assets.repository.search.CwipTransferSearchRepository;
import io.github.assets.service.dto.CwipTransferDTO;
import io.github.assets.service.mapper.CwipTransferMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link CwipTransfer}.
 */
@Service
@Transactional
public class CwipTransferServiceImpl implements CwipTransferService {

    private final Logger log = LoggerFactory.getLogger(CwipTransferServiceImpl.class);

    private final CwipTransferRepository cwipTransferRepository;

    private final CwipTransferMapper cwipTransferMapper;

    private final CwipTransferSearchRepository cwipTransferSearchRepository;

    public CwipTransferServiceImpl(CwipTransferRepository cwipTransferRepository, CwipTransferMapper cwipTransferMapper, CwipTransferSearchRepository cwipTransferSearchRepository) {
        this.cwipTransferRepository = cwipTransferRepository;
        this.cwipTransferMapper = cwipTransferMapper;
        this.cwipTransferSearchRepository = cwipTransferSearchRepository;
    }

    /**
     * Save a cwipTransfer.
     *
     * @param cwipTransferDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public CwipTransferDTO save(CwipTransferDTO cwipTransferDTO) {
        log.debug("Request to save CwipTransfer : {}", cwipTransferDTO);
        CwipTransfer cwipTransfer = cwipTransferMapper.toEntity(cwipTransferDTO);
        cwipTransfer = cwipTransferRepository.save(cwipTransfer);
        CwipTransferDTO result = cwipTransferMapper.toDto(cwipTransfer);
        cwipTransferSearchRepository.save(cwipTransfer);
        return result;
    }

    /**
     * Get all the cwipTransfers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CwipTransferDTO> findAll(Pageable pageable) {
        log.debug("Request to get all CwipTransfers");
        return cwipTransferRepository.findAll(pageable)
            .map(cwipTransferMapper::toDto);
    }


    /**
     * Get one cwipTransfer by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<CwipTransferDTO> findOne(Long id) {
        log.debug("Request to get CwipTransfer : {}", id);
        return cwipTransferRepository.findById(id)
            .map(cwipTransferMapper::toDto);
    }

    /**
     * Delete the cwipTransfer by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete CwipTransfer : {}", id);
        cwipTransferRepository.deleteById(id);
        cwipTransferSearchRepository.deleteById(id);
    }

    /**
     * Search for the cwipTransfer corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<CwipTransferDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of CwipTransfers for query {}", query);
        return cwipTransferSearchRepository.search(queryStringQuery(query), pageable)
            .map(cwipTransferMapper::toDto);
    }
}
