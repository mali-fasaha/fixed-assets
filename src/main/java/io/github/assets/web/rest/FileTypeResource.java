package io.github.assets.web.rest;

import io.github.assets.domain.FileType;
import io.github.assets.service.FileTypeService;
import io.github.assets.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.assets.domain.FileType}.
 */
@RestController
@RequestMapping("/api")
public class FileTypeResource {

    private final Logger log = LoggerFactory.getLogger(FileTypeResource.class);

    private static final String ENTITY_NAME = "fileType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileTypeService fileTypeService;

    public FileTypeResource(FileTypeService fileTypeService) {
        this.fileTypeService = fileTypeService;
    }

    /**
     * {@code POST  /file-types} : Create a new fileType.
     *
     * @param fileType the fileType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileType, or with status {@code 400 (Bad Request)} if the fileType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/file-types")
    public ResponseEntity<FileType> createFileType(@Valid @RequestBody FileType fileType) throws URISyntaxException {
        log.debug("REST request to save FileType : {}", fileType);
        if (fileType.getId() != null) {
            throw new BadRequestAlertException("A new fileType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FileType result = fileTypeService.save(fileType);
        return ResponseEntity.created(new URI("/api/file-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /file-types} : Updates an existing fileType.
     *
     * @param fileType the fileType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileType,
     * or with status {@code 400 (Bad Request)} if the fileType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/file-types")
    public ResponseEntity<FileType> updateFileType(@Valid @RequestBody FileType fileType) throws URISyntaxException {
        log.debug("REST request to update FileType : {}", fileType);
        if (fileType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FileType result = fileTypeService.save(fileType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileType.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /file-types} : get all the fileTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fileTypes in body.
     */
    @GetMapping("/file-types")
    public ResponseEntity<List<FileType>> getAllFileTypes(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of FileTypes");
        Page<FileType> page = fileTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /file-types/:id} : get the "id" fileType.
     *
     * @param id the id of the fileType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/file-types/{id}")
    public ResponseEntity<FileType> getFileType(@PathVariable Long id) {
        log.debug("REST request to get FileType : {}", id);
        Optional<FileType> fileType = fileTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileType);
    }

    /**
     * {@code DELETE  /file-types/:id} : delete the "id" fileType.
     *
     * @param id the id of the fileType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/file-types/{id}")
    public ResponseEntity<Void> deleteFileType(@PathVariable Long id) {
        log.debug("REST request to delete FileType : {}", id);
        fileTypeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/file-types?query=:query} : search for the fileType corresponding
     * to the query.
     *
     * @param query the query of the fileType search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/file-types")
    public ResponseEntity<List<FileType>> searchFileTypes(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of FileTypes for query {}", query);
        Page<FileType> page = fileTypeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
