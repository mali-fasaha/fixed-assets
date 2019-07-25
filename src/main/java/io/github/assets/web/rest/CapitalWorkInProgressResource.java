package io.github.assets.web.rest;

import io.github.assets.service.CapitalWorkInProgressService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.CapitalWorkInProgressDTO;
import io.github.assets.service.dto.CapitalWorkInProgressCriteria;
import io.github.assets.service.CapitalWorkInProgressQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.CapitalWorkInProgress}.
 */
@RestController
@RequestMapping("/api")
public class CapitalWorkInProgressResource {

    private final Logger log = LoggerFactory.getLogger(CapitalWorkInProgressResource.class);

    private static final String ENTITY_NAME = "capitalWorkInProgress";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CapitalWorkInProgressService capitalWorkInProgressService;

    private final CapitalWorkInProgressQueryService capitalWorkInProgressQueryService;

    public CapitalWorkInProgressResource(CapitalWorkInProgressService capitalWorkInProgressService, CapitalWorkInProgressQueryService capitalWorkInProgressQueryService) {
        this.capitalWorkInProgressService = capitalWorkInProgressService;
        this.capitalWorkInProgressQueryService = capitalWorkInProgressQueryService;
    }

    /**
     * {@code POST  /capital-work-in-progresses} : Create a new capitalWorkInProgress.
     *
     * @param capitalWorkInProgressDTO the capitalWorkInProgressDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new capitalWorkInProgressDTO, or with status {@code 400 (Bad Request)} if the capitalWorkInProgress has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/capital-work-in-progresses")
    public ResponseEntity<CapitalWorkInProgressDTO> createCapitalWorkInProgress(@Valid @RequestBody CapitalWorkInProgressDTO capitalWorkInProgressDTO) throws URISyntaxException {
        log.debug("REST request to save CapitalWorkInProgress : {}", capitalWorkInProgressDTO);
        if (capitalWorkInProgressDTO.getId() != null) {
            throw new BadRequestAlertException("A new capitalWorkInProgress cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CapitalWorkInProgressDTO result = capitalWorkInProgressService.save(capitalWorkInProgressDTO);
        return ResponseEntity.created(new URI("/api/capital-work-in-progresses/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /capital-work-in-progresses} : Updates an existing capitalWorkInProgress.
     *
     * @param capitalWorkInProgressDTO the capitalWorkInProgressDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated capitalWorkInProgressDTO,
     * or with status {@code 400 (Bad Request)} if the capitalWorkInProgressDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the capitalWorkInProgressDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/capital-work-in-progresses")
    public ResponseEntity<CapitalWorkInProgressDTO> updateCapitalWorkInProgress(@Valid @RequestBody CapitalWorkInProgressDTO capitalWorkInProgressDTO) throws URISyntaxException {
        log.debug("REST request to update CapitalWorkInProgress : {}", capitalWorkInProgressDTO);
        if (capitalWorkInProgressDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CapitalWorkInProgressDTO result = capitalWorkInProgressService.save(capitalWorkInProgressDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, capitalWorkInProgressDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /capital-work-in-progresses} : get all the capitalWorkInProgresses.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of capitalWorkInProgresses in body.
     */
    @GetMapping("/capital-work-in-progresses")
    public ResponseEntity<List<CapitalWorkInProgressDTO>> getAllCapitalWorkInProgresses(CapitalWorkInProgressCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CapitalWorkInProgresses by criteria: {}", criteria);
        Page<CapitalWorkInProgressDTO> page = capitalWorkInProgressQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /capital-work-in-progresses/count} : count all the capitalWorkInProgresses.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/capital-work-in-progresses/count")
    public ResponseEntity<Long> countCapitalWorkInProgresses(CapitalWorkInProgressCriteria criteria) {
        log.debug("REST request to count CapitalWorkInProgresses by criteria: {}", criteria);
        return ResponseEntity.ok().body(capitalWorkInProgressQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /capital-work-in-progresses/:id} : get the "id" capitalWorkInProgress.
     *
     * @param id the id of the capitalWorkInProgressDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the capitalWorkInProgressDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/capital-work-in-progresses/{id}")
    public ResponseEntity<CapitalWorkInProgressDTO> getCapitalWorkInProgress(@PathVariable Long id) {
        log.debug("REST request to get CapitalWorkInProgress : {}", id);
        Optional<CapitalWorkInProgressDTO> capitalWorkInProgressDTO = capitalWorkInProgressService.findOne(id);
        return ResponseUtil.wrapOrNotFound(capitalWorkInProgressDTO);
    }

    /**
     * {@code DELETE  /capital-work-in-progresses/:id} : delete the "id" capitalWorkInProgress.
     *
     * @param id the id of the capitalWorkInProgressDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/capital-work-in-progresses/{id}")
    public ResponseEntity<Void> deleteCapitalWorkInProgress(@PathVariable Long id) {
        log.debug("REST request to delete CapitalWorkInProgress : {}", id);
        capitalWorkInProgressService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/capital-work-in-progresses?query=:query} : search for the capitalWorkInProgress corresponding
     * to the query.
     *
     * @param query the query of the capitalWorkInProgress search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/capital-work-in-progresses")
    public ResponseEntity<List<CapitalWorkInProgressDTO>> searchCapitalWorkInProgresses(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of CapitalWorkInProgresses for query {}", query);
        Page<CapitalWorkInProgressDTO> page = capitalWorkInProgressService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
