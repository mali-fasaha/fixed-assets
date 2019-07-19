package io.github.assets.web.rest;

import io.github.assets.service.DepreciationRegimeService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.DepreciationRegimeDTO;
import io.github.assets.service.dto.DepreciationRegimeCriteria;
import io.github.assets.service.DepreciationRegimeQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.DepreciationRegime}.
 */
@RestController
@RequestMapping("/api")
public class DepreciationRegimeResource {

    private final Logger log = LoggerFactory.getLogger(DepreciationRegimeResource.class);

    private static final String ENTITY_NAME = "depreciationRegime";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DepreciationRegimeService depreciationRegimeService;

    private final DepreciationRegimeQueryService depreciationRegimeQueryService;

    public DepreciationRegimeResource(DepreciationRegimeService depreciationRegimeService, DepreciationRegimeQueryService depreciationRegimeQueryService) {
        this.depreciationRegimeService = depreciationRegimeService;
        this.depreciationRegimeQueryService = depreciationRegimeQueryService;
    }

    /**
     * {@code POST  /depreciation-regimes} : Create a new depreciationRegime.
     *
     * @param depreciationRegimeDTO the depreciationRegimeDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new depreciationRegimeDTO, or with status {@code 400 (Bad Request)} if the depreciationRegime has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/depreciation-regimes")
    public ResponseEntity<DepreciationRegimeDTO> createDepreciationRegime(@Valid @RequestBody DepreciationRegimeDTO depreciationRegimeDTO) throws URISyntaxException {
        log.debug("REST request to save DepreciationRegime : {}", depreciationRegimeDTO);
        if (depreciationRegimeDTO.getId() != null) {
            throw new BadRequestAlertException("A new depreciationRegime cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DepreciationRegimeDTO result = depreciationRegimeService.save(depreciationRegimeDTO);
        return ResponseEntity.created(new URI("/api/depreciation-regimes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /depreciation-regimes} : Updates an existing depreciationRegime.
     *
     * @param depreciationRegimeDTO the depreciationRegimeDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated depreciationRegimeDTO,
     * or with status {@code 400 (Bad Request)} if the depreciationRegimeDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the depreciationRegimeDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/depreciation-regimes")
    public ResponseEntity<DepreciationRegimeDTO> updateDepreciationRegime(@Valid @RequestBody DepreciationRegimeDTO depreciationRegimeDTO) throws URISyntaxException {
        log.debug("REST request to update DepreciationRegime : {}", depreciationRegimeDTO);
        if (depreciationRegimeDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DepreciationRegimeDTO result = depreciationRegimeService.save(depreciationRegimeDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, depreciationRegimeDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /depreciation-regimes} : get all the depreciationRegimes.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of depreciationRegimes in body.
     */
    @GetMapping("/depreciation-regimes")
    public ResponseEntity<List<DepreciationRegimeDTO>> getAllDepreciationRegimes(DepreciationRegimeCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get DepreciationRegimes by criteria: {}", criteria);
        Page<DepreciationRegimeDTO> page = depreciationRegimeQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /depreciation-regimes/count} : count all the depreciationRegimes.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/depreciation-regimes/count")
    public ResponseEntity<Long> countDepreciationRegimes(DepreciationRegimeCriteria criteria) {
        log.debug("REST request to count DepreciationRegimes by criteria: {}", criteria);
        return ResponseEntity.ok().body(depreciationRegimeQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /depreciation-regimes/:id} : get the "id" depreciationRegime.
     *
     * @param id the id of the depreciationRegimeDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the depreciationRegimeDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/depreciation-regimes/{id}")
    public ResponseEntity<DepreciationRegimeDTO> getDepreciationRegime(@PathVariable Long id) {
        log.debug("REST request to get DepreciationRegime : {}", id);
        Optional<DepreciationRegimeDTO> depreciationRegimeDTO = depreciationRegimeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(depreciationRegimeDTO);
    }

    /**
     * {@code DELETE  /depreciation-regimes/:id} : delete the "id" depreciationRegime.
     *
     * @param id the id of the depreciationRegimeDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/depreciation-regimes/{id}")
    public ResponseEntity<Void> deleteDepreciationRegime(@PathVariable Long id) {
        log.debug("REST request to delete DepreciationRegime : {}", id);
        depreciationRegimeService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/depreciation-regimes?query=:query} : search for the depreciationRegime corresponding
     * to the query.
     *
     * @param query the query of the depreciationRegime search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/depreciation-regimes")
    public ResponseEntity<List<DepreciationRegimeDTO>> searchDepreciationRegimes(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of DepreciationRegimes for query {}", query);
        Page<DepreciationRegimeDTO> page = depreciationRegimeService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
