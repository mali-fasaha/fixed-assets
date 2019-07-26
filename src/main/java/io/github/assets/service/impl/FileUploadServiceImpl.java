package io.github.assets.service.impl;

import io.github.assets.service.FileUploadService;
import io.github.assets.domain.FileUpload;
import io.github.assets.repository.FileUploadRepository;
import io.github.assets.repository.search.FileUploadSearchRepository;
import io.github.assets.service.dto.FileUploadDTO;
import io.github.assets.service.mapper.FileUploadMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link FileUpload}.
 */
@Service
@Transactional
public class FileUploadServiceImpl implements FileUploadService {

    private final Logger log = LoggerFactory.getLogger(FileUploadServiceImpl.class);

    private final FileUploadRepository fileUploadRepository;

    private final FileUploadMapper fileUploadMapper;

    private final FileUploadSearchRepository fileUploadSearchRepository;

    public FileUploadServiceImpl(FileUploadRepository fileUploadRepository, FileUploadMapper fileUploadMapper, FileUploadSearchRepository fileUploadSearchRepository) {
        this.fileUploadRepository = fileUploadRepository;
        this.fileUploadMapper = fileUploadMapper;
        this.fileUploadSearchRepository = fileUploadSearchRepository;
    }

    /**
     * Save a fileUpload.
     *
     * @param fileUploadDTO the entity to save.
     * @return the persisted entity.
     */
    @Override
    public FileUploadDTO save(FileUploadDTO fileUploadDTO) {
        log.debug("Request to save FileUpload : {}", fileUploadDTO);
        FileUpload fileUpload = fileUploadMapper.toEntity(fileUploadDTO);
        fileUpload = fileUploadRepository.save(fileUpload);
        FileUploadDTO result = fileUploadMapper.toDto(fileUpload);
        fileUploadSearchRepository.save(fileUpload);
        return result;
    }

    /**
     * Get all the fileUploads.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> findAll(Pageable pageable) {
        log.debug("Request to get all FileUploads");
        return fileUploadRepository.findAll(pageable)
            .map(fileUploadMapper::toDto);
    }


    /**
     * Get one fileUpload by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<FileUploadDTO> findOne(Long id) {
        log.debug("Request to get FileUpload : {}", id);
        return fileUploadRepository.findById(id)
            .map(fileUploadMapper::toDto);
    }

    /**
     * Delete the fileUpload by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete FileUpload : {}", id);
        fileUploadRepository.deleteById(id);
        fileUploadSearchRepository.deleteById(id);
    }

    /**
     * Search for the fileUpload corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<FileUploadDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of FileUploads for query {}", query);
        return fileUploadSearchRepository.search(queryStringQuery(query), pageable)
            .map(fileUploadMapper::toDto);
    }
}
