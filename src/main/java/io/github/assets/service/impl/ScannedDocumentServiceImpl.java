package io.github.assets.service.impl;

import io.github.assets.service.ScannedDocumentService;
import io.github.assets.domain.ScannedDocument;
import io.github.assets.repository.ScannedDocumentRepository;
import io.github.assets.repository.search.ScannedDocumentSearchRepository;
import io.github.assets.service.dto.ScannedDocumentDTO;
import io.github.assets.service.mapper.ScannedDocumentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link ScannedDocument}.
 */
@Service
@Transactional
public class ScannedDocumentServiceImpl implements ScannedDocumentService {

    private final Logger log = LoggerFactory.getLogger(ScannedDocumentServiceImpl.class);

    private final ScannedDocumentRepository scannedDocumentRepository;

    private final ScannedDocumentMapper scannedDocumentMapper;

    private final ScannedDocumentSearchRepository scannedDocumentSearchRepository;

    public ScannedDocumentServiceImpl(ScannedDocumentRepository scannedDocumentRepository, ScannedDocumentMapper scannedDocumentMapper, ScannedDocumentSearchRepository scannedDocumentSearchRepository) {
        this.scannedDocumentRepository = scannedDocumentRepository;
        this.scannedDocumentMapper = scannedDocumentMapper;
        this.scannedDocumentSearchRepository = scannedDocumentSearchRepository;
    }

    /**
     * Save a scannedDocument.
     *
     * @param scannedDocumentDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public ScannedDocumentDTO save(ScannedDocumentDTO scannedDocumentDTO) {
        log.debug("Request to save ScannedDocument : {}", scannedDocumentDTO);
        ScannedDocument scannedDocument = scannedDocumentMapper.toEntity(scannedDocumentDTO);
        scannedDocument = scannedDocumentRepository.save(scannedDocument);
        ScannedDocumentDTO result = scannedDocumentMapper.toDto(scannedDocument);
        scannedDocumentSearchRepository.save(scannedDocument);
        return result;
    }

    /**
     * Get all the scannedDocuments.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScannedDocumentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ScannedDocuments");
        return scannedDocumentRepository.findAll(pageable)
            .map(scannedDocumentMapper::toDto);
    }


    /**
     * Get one scannedDocument by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ScannedDocumentDTO> findOne(Long id) {
        log.debug("Request to get ScannedDocument : {}", id);
        return scannedDocumentRepository.findById(id)
            .map(scannedDocumentMapper::toDto);
    }

    /**
     * Delete the scannedDocument by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ScannedDocument : {}", id);
        scannedDocumentRepository.deleteById(id);
        scannedDocumentSearchRepository.deleteById(id);
    }

    /**
     * Search for the scannedDocument corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ScannedDocumentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ScannedDocuments for query {}", query);
        return scannedDocumentSearchRepository.search(queryStringQuery(query), pageable)
            .map(scannedDocumentMapper::toDto);
    }
}
