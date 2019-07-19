package io.github.assets.web.rest;

import io.github.assets.service.FixedAssetAssessmentService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.FixedAssetAssessmentDTO;
import io.github.assets.service.dto.FixedAssetAssessmentCriteria;
import io.github.assets.service.FixedAssetAssessmentQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.FixedAssetAssessment}.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetAssessmentResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetAssessmentResource.class);

    private static final String ENTITY_NAME = "fixedAssetAssessment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixedAssetAssessmentService fixedAssetAssessmentService;

    private final FixedAssetAssessmentQueryService fixedAssetAssessmentQueryService;

    public FixedAssetAssessmentResource(FixedAssetAssessmentService fixedAssetAssessmentService, FixedAssetAssessmentQueryService fixedAssetAssessmentQueryService) {
        this.fixedAssetAssessmentService = fixedAssetAssessmentService;
        this.fixedAssetAssessmentQueryService = fixedAssetAssessmentQueryService;
    }

    /**
     * {@code POST  /fixed-asset-assessments} : Create a new fixedAssetAssessment.
     *
     * @param fixedAssetAssessmentDTO the fixedAssetAssessmentDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixedAssetAssessmentDTO, or with status {@code 400 (Bad Request)} if the fixedAssetAssessment has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixed-asset-assessments")
    public ResponseEntity<FixedAssetAssessmentDTO> createFixedAssetAssessment(@Valid @RequestBody FixedAssetAssessmentDTO fixedAssetAssessmentDTO) throws URISyntaxException {
        log.debug("REST request to save FixedAssetAssessment : {}", fixedAssetAssessmentDTO);
        if (fixedAssetAssessmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssetAssessment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssetAssessmentDTO result = fixedAssetAssessmentService.save(fixedAssetAssessmentDTO);
        return ResponseEntity.created(new URI("/api/fixed-asset-assessments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixed-asset-assessments} : Updates an existing fixedAssetAssessment.
     *
     * @param fixedAssetAssessmentDTO the fixedAssetAssessmentDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixedAssetAssessmentDTO,
     * or with status {@code 400 (Bad Request)} if the fixedAssetAssessmentDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixedAssetAssessmentDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixed-asset-assessments")
    public ResponseEntity<FixedAssetAssessmentDTO> updateFixedAssetAssessment(@Valid @RequestBody FixedAssetAssessmentDTO fixedAssetAssessmentDTO) throws URISyntaxException {
        log.debug("REST request to update FixedAssetAssessment : {}", fixedAssetAssessmentDTO);
        if (fixedAssetAssessmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssetAssessmentDTO result = fixedAssetAssessmentService.save(fixedAssetAssessmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fixedAssetAssessmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixed-asset-assessments} : get all the fixedAssetAssessments.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetAssessments in body.
     */
    @GetMapping("/fixed-asset-assessments")
    public ResponseEntity<List<FixedAssetAssessmentDTO>> getAllFixedAssetAssessments(FixedAssetAssessmentCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FixedAssetAssessments by criteria: {}", criteria);
        Page<FixedAssetAssessmentDTO> page = fixedAssetAssessmentQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fixed-asset-assessments/count} : count all the fixedAssetAssessments.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fixed-asset-assessments/count")
    public ResponseEntity<Long> countFixedAssetAssessments(FixedAssetAssessmentCriteria criteria) {
        log.debug("REST request to count FixedAssetAssessments by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixedAssetAssessmentQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixed-asset-assessments/:id} : get the "id" fixedAssetAssessment.
     *
     * @param id the id of the fixedAssetAssessmentDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixedAssetAssessmentDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixed-asset-assessments/{id}")
    public ResponseEntity<FixedAssetAssessmentDTO> getFixedAssetAssessment(@PathVariable Long id) {
        log.debug("REST request to get FixedAssetAssessment : {}", id);
        Optional<FixedAssetAssessmentDTO> fixedAssetAssessmentDTO = fixedAssetAssessmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedAssetAssessmentDTO);
    }

    /**
     * {@code DELETE  /fixed-asset-assessments/:id} : delete the "id" fixedAssetAssessment.
     *
     * @param id the id of the fixedAssetAssessmentDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixed-asset-assessments/{id}")
    public ResponseEntity<Void> deleteFixedAssetAssessment(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssetAssessment : {}", id);
        fixedAssetAssessmentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fixed-asset-assessments?query=:query} : search for the fixedAssetAssessment corresponding
     * to the query.
     *
     * @param query the query of the fixedAssetAssessment search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fixed-asset-assessments")
    public ResponseEntity<List<FixedAssetAssessmentDTO>> searchFixedAssetAssessments(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of FixedAssetAssessments for query {}", query);
        Page<FixedAssetAssessmentDTO> page = fixedAssetAssessmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
