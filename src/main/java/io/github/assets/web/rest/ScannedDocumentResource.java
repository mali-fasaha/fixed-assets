package io.github.assets.web.rest;

import io.github.assets.service.ScannedDocumentService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.ScannedDocumentDTO;
import io.github.assets.service.dto.ScannedDocumentCriteria;
import io.github.assets.service.ScannedDocumentQueryService;

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

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing {@link io.github.assets.domain.ScannedDocument}.
 */
@RestController
@RequestMapping("/api")
public class ScannedDocumentResource {

    private final Logger log = LoggerFactory.getLogger(ScannedDocumentResource.class);

    private static final String ENTITY_NAME = "scannedDocument";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScannedDocumentService scannedDocumentService;

    private final ScannedDocumentQueryService scannedDocumentQueryService;

    public ScannedDocumentResource(ScannedDocumentService scannedDocumentService, ScannedDocumentQueryService scannedDocumentQueryService) {
        this.scannedDocumentService = scannedDocumentService;
        this.scannedDocumentQueryService = scannedDocumentQueryService;
    }

    /**
     * {@code POST  /scanned-documents} : Create a new scannedDocument.
     *
     * @param scannedDocumentDTO the scannedDocumentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scannedDocumentDTO, or with status {@code 400 (Bad Request)} if the scannedDocument has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/scanned-documents")
    public ResponseEntity<ScannedDocumentDTO> createScannedDocument(@RequestBody ScannedDocumentDTO scannedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to save ScannedDocument : {}", scannedDocumentDTO);
        if (scannedDocumentDTO.getId() != null) {
            throw new BadRequestAlertException("A new scannedDocument cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScannedDocumentDTO result = scannedDocumentService.save(scannedDocumentDTO);
        return ResponseEntity.created(new URI("/api/scanned-documents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /scanned-documents} : Updates an existing scannedDocument.
     *
     * @param scannedDocumentDTO the scannedDocumentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scannedDocumentDTO,
     * or with status {@code 400 (Bad Request)} if the scannedDocumentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scannedDocumentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/scanned-documents")
    public ResponseEntity<ScannedDocumentDTO> updateScannedDocument(@RequestBody ScannedDocumentDTO scannedDocumentDTO) throws URISyntaxException {
        log.debug("REST request to update ScannedDocument : {}", scannedDocumentDTO);
        if (scannedDocumentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScannedDocumentDTO result = scannedDocumentService.save(scannedDocumentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, scannedDocumentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /scanned-documents} : get all the scannedDocuments.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scannedDocuments in body.
     */
    @GetMapping("/scanned-documents")
    public ResponseEntity<List<ScannedDocumentDTO>> getAllScannedDocuments(ScannedDocumentCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ScannedDocuments by criteria: {}", criteria);
        Page<ScannedDocumentDTO> page = scannedDocumentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /scanned-documents/count} : count all the scannedDocuments.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/scanned-documents/count")
    public ResponseEntity<Long> countScannedDocuments(ScannedDocumentCriteria criteria) {
        log.debug("REST request to count ScannedDocuments by criteria: {}", criteria);
        return ResponseEntity.ok().body(scannedDocumentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /scanned-documents/:id} : get the "id" scannedDocument.
     *
     * @param id the id of the scannedDocumentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scannedDocumentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/scanned-documents/{id}")
    public ResponseEntity<ScannedDocumentDTO> getScannedDocument(@PathVariable Long id) {
        log.debug("REST request to get ScannedDocument : {}", id);
        Optional<ScannedDocumentDTO> scannedDocumentDTO = scannedDocumentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scannedDocumentDTO);
    }

    /**
     * {@code DELETE  /scanned-documents/:id} : delete the "id" scannedDocument.
     *
     * @param id the id of the scannedDocumentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/scanned-documents/{id}")
    public ResponseEntity<Void> deleteScannedDocument(@PathVariable Long id) {
        log.debug("REST request to delete ScannedDocument : {}", id);
        scannedDocumentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/scanned-documents?query=:query} : search for the scannedDocument corresponding
     * to the query.
     *
     * @param query the query of the scannedDocument search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/scanned-documents")
    public ResponseEntity<List<ScannedDocumentDTO>> searchScannedDocuments(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of ScannedDocuments for query {}", query);
        Page<ScannedDocumentDTO> page = scannedDocumentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
