package io.github.assets.web.rest;

import io.github.assets.service.TransactionApprovalService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.TransactionApprovalDTO;
import io.github.assets.service.dto.TransactionApprovalCriteria;
import io.github.assets.service.TransactionApprovalQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.TransactionApproval}.
 */
@RestController
@RequestMapping("/api")
public class TransactionApprovalResource {

    private final Logger log = LoggerFactory.getLogger(TransactionApprovalResource.class);

    private static final String ENTITY_NAME = "transactionApproval";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TransactionApprovalService transactionApprovalService;

    private final TransactionApprovalQueryService transactionApprovalQueryService;

    public TransactionApprovalResource(TransactionApprovalService transactionApprovalService, TransactionApprovalQueryService transactionApprovalQueryService) {
        this.transactionApprovalService = transactionApprovalService;
        this.transactionApprovalQueryService = transactionApprovalQueryService;
    }

    /**
     * {@code POST  /transaction-approvals} : Create a new transactionApproval.
     *
     * @param transactionApprovalDTO the transactionApprovalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new transactionApprovalDTO, or with status {@code 400 (Bad Request)} if the transactionApproval has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/transaction-approvals")
    public ResponseEntity<TransactionApprovalDTO> createTransactionApproval(@Valid @RequestBody TransactionApprovalDTO transactionApprovalDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionApproval : {}", transactionApprovalDTO);
        if (transactionApprovalDTO.getId() != null) {
            throw new BadRequestAlertException("A new transactionApproval cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TransactionApprovalDTO result = transactionApprovalService.save(transactionApprovalDTO);
        return ResponseEntity.created(new URI("/api/transaction-approvals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /transaction-approvals} : Updates an existing transactionApproval.
     *
     * @param transactionApprovalDTO the transactionApprovalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated transactionApprovalDTO,
     * or with status {@code 400 (Bad Request)} if the transactionApprovalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the transactionApprovalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/transaction-approvals")
    public ResponseEntity<TransactionApprovalDTO> updateTransactionApproval(@Valid @RequestBody TransactionApprovalDTO transactionApprovalDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionApproval : {}", transactionApprovalDTO);
        if (transactionApprovalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TransactionApprovalDTO result = transactionApprovalService.save(transactionApprovalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, transactionApprovalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /transaction-approvals} : get all the transactionApprovals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of transactionApprovals in body.
     */
    @GetMapping("/transaction-approvals")
    public ResponseEntity<List<TransactionApprovalDTO>> getAllTransactionApprovals(TransactionApprovalCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get TransactionApprovals by criteria: {}", criteria);
        Page<TransactionApprovalDTO> page = transactionApprovalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /transaction-approvals/count} : count all the transactionApprovals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/transaction-approvals/count")
    public ResponseEntity<Long> countTransactionApprovals(TransactionApprovalCriteria criteria) {
        log.debug("REST request to count TransactionApprovals by criteria: {}", criteria);
        return ResponseEntity.ok().body(transactionApprovalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /transaction-approvals/:id} : get the "id" transactionApproval.
     *
     * @param id the id of the transactionApprovalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the transactionApprovalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/transaction-approvals/{id}")
    public ResponseEntity<TransactionApprovalDTO> getTransactionApproval(@PathVariable Long id) {
        log.debug("REST request to get TransactionApproval : {}", id);
        Optional<TransactionApprovalDTO> transactionApprovalDTO = transactionApprovalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(transactionApprovalDTO);
    }

    /**
     * {@code DELETE  /transaction-approvals/:id} : delete the "id" transactionApproval.
     *
     * @param id the id of the transactionApprovalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/transaction-approvals/{id}")
    public ResponseEntity<Void> deleteTransactionApproval(@PathVariable Long id) {
        log.debug("REST request to delete TransactionApproval : {}", id);
        transactionApprovalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/transaction-approvals?query=:query} : search for the transactionApproval corresponding
     * to the query.
     *
     * @param query the query of the transactionApproval search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/transaction-approvals")
    public ResponseEntity<List<TransactionApprovalDTO>> searchTransactionApprovals(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of TransactionApprovals for query {}", query);
        Page<TransactionApprovalDTO> page = transactionApprovalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
