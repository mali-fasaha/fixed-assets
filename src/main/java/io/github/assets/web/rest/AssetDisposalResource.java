package io.github.assets.web.rest;

import io.github.assets.service.AssetDisposalService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.AssetDisposalDTO;
import io.github.assets.service.dto.AssetDisposalCriteria;
import io.github.assets.service.AssetDisposalQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.AssetDisposal}.
 */
@RestController
@RequestMapping("/api")
public class AssetDisposalResource {

    private final Logger log = LoggerFactory.getLogger(AssetDisposalResource.class);

    private static final String ENTITY_NAME = "assetDisposal";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetDisposalService assetDisposalService;

    private final AssetDisposalQueryService assetDisposalQueryService;

    public AssetDisposalResource(AssetDisposalService assetDisposalService, AssetDisposalQueryService assetDisposalQueryService) {
        this.assetDisposalService = assetDisposalService;
        this.assetDisposalQueryService = assetDisposalQueryService;
    }

    /**
     * {@code POST  /asset-disposals} : Create a new assetDisposal.
     *
     * @param assetDisposalDTO the assetDisposalDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetDisposalDTO, or with status {@code 400 (Bad Request)} if the assetDisposal has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-disposals")
    public ResponseEntity<AssetDisposalDTO> createAssetDisposal(@Valid @RequestBody AssetDisposalDTO assetDisposalDTO) throws URISyntaxException {
        log.debug("REST request to save AssetDisposal : {}", assetDisposalDTO);
        if (assetDisposalDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetDisposal cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetDisposalDTO result = assetDisposalService.save(assetDisposalDTO);
        return ResponseEntity.created(new URI("/api/asset-disposals/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-disposals} : Updates an existing assetDisposal.
     *
     * @param assetDisposalDTO the assetDisposalDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetDisposalDTO,
     * or with status {@code 400 (Bad Request)} if the assetDisposalDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetDisposalDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-disposals")
    public ResponseEntity<AssetDisposalDTO> updateAssetDisposal(@Valid @RequestBody AssetDisposalDTO assetDisposalDTO) throws URISyntaxException {
        log.debug("REST request to update AssetDisposal : {}", assetDisposalDTO);
        if (assetDisposalDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetDisposalDTO result = assetDisposalService.save(assetDisposalDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetDisposalDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asset-disposals} : get all the assetDisposals.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetDisposals in body.
     */
    @GetMapping("/asset-disposals")
    public ResponseEntity<List<AssetDisposalDTO>> getAllAssetDisposals(AssetDisposalCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AssetDisposals by criteria: {}", criteria);
        Page<AssetDisposalDTO> page = assetDisposalQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /asset-disposals/count} : count all the assetDisposals.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/asset-disposals/count")
    public ResponseEntity<Long> countAssetDisposals(AssetDisposalCriteria criteria) {
        log.debug("REST request to count AssetDisposals by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetDisposalQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-disposals/:id} : get the "id" assetDisposal.
     *
     * @param id the id of the assetDisposalDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetDisposalDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-disposals/{id}")
    public ResponseEntity<AssetDisposalDTO> getAssetDisposal(@PathVariable Long id) {
        log.debug("REST request to get AssetDisposal : {}", id);
        Optional<AssetDisposalDTO> assetDisposalDTO = assetDisposalService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetDisposalDTO);
    }

    /**
     * {@code DELETE  /asset-disposals/:id} : delete the "id" assetDisposal.
     *
     * @param id the id of the assetDisposalDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-disposals/{id}")
    public ResponseEntity<Void> deleteAssetDisposal(@PathVariable Long id) {
        log.debug("REST request to delete AssetDisposal : {}", id);
        assetDisposalService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/asset-disposals?query=:query} : search for the assetDisposal corresponding
     * to the query.
     *
     * @param query the query of the assetDisposal search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/asset-disposals")
    public ResponseEntity<List<AssetDisposalDTO>> searchAssetDisposals(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AssetDisposals for query {}", query);
        Page<AssetDisposalDTO> page = assetDisposalService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
