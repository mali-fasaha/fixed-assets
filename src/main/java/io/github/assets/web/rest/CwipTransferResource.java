package io.github.assets.web.rest;

import io.github.assets.service.CwipTransferService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.CwipTransferDTO;
import io.github.assets.service.dto.CwipTransferCriteria;
import io.github.assets.service.CwipTransferQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.CwipTransfer}.
 */
@RestController
@RequestMapping("/api")
public class CwipTransferResource {

    private final Logger log = LoggerFactory.getLogger(CwipTransferResource.class);

    private static final String ENTITY_NAME = "cwipTransfer";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CwipTransferService cwipTransferService;

    private final CwipTransferQueryService cwipTransferQueryService;

    public CwipTransferResource(CwipTransferService cwipTransferService, CwipTransferQueryService cwipTransferQueryService) {
        this.cwipTransferService = cwipTransferService;
        this.cwipTransferQueryService = cwipTransferQueryService;
    }

    /**
     * {@code POST  /cwip-transfers} : Create a new cwipTransfer.
     *
     * @param cwipTransferDTO the cwipTransferDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new cwipTransferDTO, or with status {@code 400 (Bad Request)} if the cwipTransfer has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/cwip-transfers")
    public ResponseEntity<CwipTransferDTO> createCwipTransfer(@Valid @RequestBody CwipTransferDTO cwipTransferDTO) throws URISyntaxException {
        log.debug("REST request to save CwipTransfer : {}", cwipTransferDTO);
        if (cwipTransferDTO.getId() != null) {
            throw new BadRequestAlertException("A new cwipTransfer cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CwipTransferDTO result = cwipTransferService.save(cwipTransferDTO);
        return ResponseEntity.created(new URI("/api/cwip-transfers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /cwip-transfers} : Updates an existing cwipTransfer.
     *
     * @param cwipTransferDTO the cwipTransferDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated cwipTransferDTO,
     * or with status {@code 400 (Bad Request)} if the cwipTransferDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the cwipTransferDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/cwip-transfers")
    public ResponseEntity<CwipTransferDTO> updateCwipTransfer(@Valid @RequestBody CwipTransferDTO cwipTransferDTO) throws URISyntaxException {
        log.debug("REST request to update CwipTransfer : {}", cwipTransferDTO);
        if (cwipTransferDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CwipTransferDTO result = cwipTransferService.save(cwipTransferDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, cwipTransferDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /cwip-transfers} : get all the cwipTransfers.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of cwipTransfers in body.
     */
    @GetMapping("/cwip-transfers")
    public ResponseEntity<List<CwipTransferDTO>> getAllCwipTransfers(CwipTransferCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get CwipTransfers by criteria: {}", criteria);
        Page<CwipTransferDTO> page = cwipTransferQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /cwip-transfers/count} : count all the cwipTransfers.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/cwip-transfers/count")
    public ResponseEntity<Long> countCwipTransfers(CwipTransferCriteria criteria) {
        log.debug("REST request to count CwipTransfers by criteria: {}", criteria);
        return ResponseEntity.ok().body(cwipTransferQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /cwip-transfers/:id} : get the "id" cwipTransfer.
     *
     * @param id the id of the cwipTransferDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the cwipTransferDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/cwip-transfers/{id}")
    public ResponseEntity<CwipTransferDTO> getCwipTransfer(@PathVariable Long id) {
        log.debug("REST request to get CwipTransfer : {}", id);
        Optional<CwipTransferDTO> cwipTransferDTO = cwipTransferService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cwipTransferDTO);
    }

    /**
     * {@code DELETE  /cwip-transfers/:id} : delete the "id" cwipTransfer.
     *
     * @param id the id of the cwipTransferDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/cwip-transfers/{id}")
    public ResponseEntity<Void> deleteCwipTransfer(@PathVariable Long id) {
        log.debug("REST request to delete CwipTransfer : {}", id);
        cwipTransferService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/cwip-transfers?query=:query} : search for the cwipTransfer corresponding
     * to the query.
     *
     * @param query the query of the cwipTransfer search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/cwip-transfers")
    public ResponseEntity<List<CwipTransferDTO>> searchCwipTransfers(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of CwipTransfers for query {}", query);
        Page<CwipTransferDTO> page = cwipTransferService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
