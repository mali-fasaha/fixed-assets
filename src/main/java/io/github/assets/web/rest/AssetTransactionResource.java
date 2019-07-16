package io.github.assets.web.rest;

import io.github.assets.service.AssetTransactionService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.AssetTransactionDTO;
import io.github.assets.service.dto.AssetTransactionCriteria;
import io.github.assets.service.AssetTransactionQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.AssetTransaction}.
 */
@RestController
@RequestMapping("/api")
public class AssetTransactionResource {

    private final Logger log = LoggerFactory.getLogger(AssetTransactionResource.class);

    private static final String ENTITY_NAME = "assetTransaction";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetTransactionService assetTransactionService;

    private final AssetTransactionQueryService assetTransactionQueryService;

    public AssetTransactionResource(AssetTransactionService assetTransactionService, AssetTransactionQueryService assetTransactionQueryService) {
        this.assetTransactionService = assetTransactionService;
        this.assetTransactionQueryService = assetTransactionQueryService;
    }

    /**
     * {@code POST  /asset-transactions} : Create a new assetTransaction.
     *
     * @param assetTransactionDTO the assetTransactionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetTransactionDTO, or with status {@code 400 (Bad Request)} if the assetTransaction has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-transactions")
    public ResponseEntity<AssetTransactionDTO> createAssetTransaction(@Valid @RequestBody AssetTransactionDTO assetTransactionDTO) throws URISyntaxException {
        log.debug("REST request to save AssetTransaction : {}", assetTransactionDTO);
        if (assetTransactionDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetTransaction cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetTransactionDTO result = assetTransactionService.save(assetTransactionDTO);
        return ResponseEntity.created(new URI("/api/asset-transactions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-transactions} : Updates an existing assetTransaction.
     *
     * @param assetTransactionDTO the assetTransactionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetTransactionDTO,
     * or with status {@code 400 (Bad Request)} if the assetTransactionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetTransactionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-transactions")
    public ResponseEntity<AssetTransactionDTO> updateAssetTransaction(@Valid @RequestBody AssetTransactionDTO assetTransactionDTO) throws URISyntaxException {
        log.debug("REST request to update AssetTransaction : {}", assetTransactionDTO);
        if (assetTransactionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetTransactionDTO result = assetTransactionService.save(assetTransactionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetTransactionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asset-transactions} : get all the assetTransactions.
     *
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetTransactions in body.
     */
    @GetMapping("/asset-transactions")
    public ResponseEntity<List<AssetTransactionDTO>> getAllAssetTransactions(AssetTransactionCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AssetTransactions by criteria: {}", criteria);
        Page<AssetTransactionDTO> page = assetTransactionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /asset-transactions/count} : count all the assetTransactions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/asset-transactions/count")
    public ResponseEntity<Long> countAssetTransactions(AssetTransactionCriteria criteria) {
        log.debug("REST request to count AssetTransactions by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetTransactionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-transactions/:id} : get the "id" assetTransaction.
     *
     * @param id the id of the assetTransactionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetTransactionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-transactions/{id}")
    public ResponseEntity<AssetTransactionDTO> getAssetTransaction(@PathVariable Long id) {
        log.debug("REST request to get AssetTransaction : {}", id);
        Optional<AssetTransactionDTO> assetTransactionDTO = assetTransactionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetTransactionDTO);
    }

    /**
     * {@code DELETE  /asset-transactions/:id} : delete the "id" assetTransaction.
     *
     * @param id the id of the assetTransactionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-transactions/{id}")
    public ResponseEntity<Void> deleteAssetTransaction(@PathVariable Long id) {
        log.debug("REST request to delete AssetTransaction : {}", id);
        assetTransactionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/asset-transactions?query=:query} : search for the assetTransaction corresponding
     * to the query.
     *
     * @param query the query of the assetTransaction search.
     * @param pageable the pagination information.
     * @param queryParams a {@link MultiValueMap} query parameters.
     * @param uriBuilder a {@link UriComponentsBuilder} URI builder.
     * @return the result of the search.
     */
    @GetMapping("/_search/asset-transactions")
    public ResponseEntity<List<AssetTransactionDTO>> searchAssetTransactions(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AssetTransactions for query {}", query);
        Page<AssetTransactionDTO> page = assetTransactionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
