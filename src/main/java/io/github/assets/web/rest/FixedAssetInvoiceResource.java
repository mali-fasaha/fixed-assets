package io.github.assets.web.rest;

import io.github.assets.service.FixedAssetInvoiceService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.FixedAssetInvoiceDTO;
import io.github.assets.service.dto.FixedAssetInvoiceCriteria;
import io.github.assets.service.FixedAssetInvoiceQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.FixedAssetInvoice}.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetInvoiceResource.class);

    private static final String ENTITY_NAME = "fixedAssetInvoice";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixedAssetInvoiceService fixedAssetInvoiceService;

    private final FixedAssetInvoiceQueryService fixedAssetInvoiceQueryService;

    public FixedAssetInvoiceResource(FixedAssetInvoiceService fixedAssetInvoiceService, FixedAssetInvoiceQueryService fixedAssetInvoiceQueryService) {
        this.fixedAssetInvoiceService = fixedAssetInvoiceService;
        this.fixedAssetInvoiceQueryService = fixedAssetInvoiceQueryService;
    }

    /**
     * {@code POST  /fixed-asset-invoices} : Create a new fixedAssetInvoice.
     *
     * @param fixedAssetInvoiceDTO the fixedAssetInvoiceDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixedAssetInvoiceDTO, or with status {@code 400 (Bad Request)} if the fixedAssetInvoice has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixed-asset-invoices")
    public ResponseEntity<FixedAssetInvoiceDTO> createFixedAssetInvoice(@Valid @RequestBody FixedAssetInvoiceDTO fixedAssetInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to save FixedAssetInvoice : {}", fixedAssetInvoiceDTO);
        if (fixedAssetInvoiceDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssetInvoice cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssetInvoiceDTO result = fixedAssetInvoiceService.save(fixedAssetInvoiceDTO);
        return ResponseEntity.created(new URI("/api/fixed-asset-invoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixed-asset-invoices} : Updates an existing fixedAssetInvoice.
     *
     * @param fixedAssetInvoiceDTO the fixedAssetInvoiceDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixedAssetInvoiceDTO,
     * or with status {@code 400 (Bad Request)} if the fixedAssetInvoiceDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixedAssetInvoiceDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixed-asset-invoices")
    public ResponseEntity<FixedAssetInvoiceDTO> updateFixedAssetInvoice(@Valid @RequestBody FixedAssetInvoiceDTO fixedAssetInvoiceDTO) throws URISyntaxException {
        log.debug("REST request to update FixedAssetInvoice : {}", fixedAssetInvoiceDTO);
        if (fixedAssetInvoiceDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssetInvoiceDTO result = fixedAssetInvoiceService.save(fixedAssetInvoiceDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fixedAssetInvoiceDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixed-asset-invoices} : get all the fixedAssetInvoices.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetInvoices in body.
     */
    @GetMapping("/fixed-asset-invoices")
    public ResponseEntity<List<FixedAssetInvoiceDTO>> getAllFixedAssetInvoices(FixedAssetInvoiceCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FixedAssetInvoices by criteria: {}", criteria);
        Page<FixedAssetInvoiceDTO> page = fixedAssetInvoiceQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fixed-asset-invoices/count} : count all the fixedAssetInvoices.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fixed-asset-invoices/count")
    public ResponseEntity<Long> countFixedAssetInvoices(FixedAssetInvoiceCriteria criteria) {
        log.debug("REST request to count FixedAssetInvoices by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixedAssetInvoiceQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixed-asset-invoices/:id} : get the "id" fixedAssetInvoice.
     *
     * @param id the id of the fixedAssetInvoiceDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixedAssetInvoiceDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixed-asset-invoices/{id}")
    public ResponseEntity<FixedAssetInvoiceDTO> getFixedAssetInvoice(@PathVariable Long id) {
        log.debug("REST request to get FixedAssetInvoice : {}", id);
        Optional<FixedAssetInvoiceDTO> fixedAssetInvoiceDTO = fixedAssetInvoiceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedAssetInvoiceDTO);
    }

    /**
     * {@code DELETE  /fixed-asset-invoices/:id} : delete the "id" fixedAssetInvoice.
     *
     * @param id the id of the fixedAssetInvoiceDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixed-asset-invoices/{id}")
    public ResponseEntity<Void> deleteFixedAssetInvoice(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssetInvoice : {}", id);
        fixedAssetInvoiceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fixed-asset-invoices?query=:query} : search for the fixedAssetInvoice corresponding
     * to the query.
     *
     * @param query the query of the fixedAssetInvoice search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fixed-asset-invoices")
    public ResponseEntity<List<FixedAssetInvoiceDTO>> searchFixedAssetInvoices(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of FixedAssetInvoices for query {}", query);
        Page<FixedAssetInvoiceDTO> page = fixedAssetInvoiceService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
