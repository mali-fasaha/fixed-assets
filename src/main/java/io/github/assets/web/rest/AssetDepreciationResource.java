package io.github.assets.web.rest;

import io.github.assets.service.AssetDepreciationService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.AssetDepreciationDTO;
import io.github.assets.service.dto.AssetDepreciationCriteria;
import io.github.assets.service.AssetDepreciationQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.AssetDepreciation}.
 */
@RestController
@RequestMapping("/api")
public class AssetDepreciationResource {

    private final Logger log = LoggerFactory.getLogger(AssetDepreciationResource.class);

    private static final String ENTITY_NAME = "assetDepreciation";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetDepreciationService assetDepreciationService;

    private final AssetDepreciationQueryService assetDepreciationQueryService;

    public AssetDepreciationResource(AssetDepreciationService assetDepreciationService, AssetDepreciationQueryService assetDepreciationQueryService) {
        this.assetDepreciationService = assetDepreciationService;
        this.assetDepreciationQueryService = assetDepreciationQueryService;
    }

    /**
     * {@code POST  /asset-depreciations} : Create a new assetDepreciation.
     *
     * @param assetDepreciationDTO the assetDepreciationDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetDepreciationDTO, or with status {@code 400 (Bad Request)} if the assetDepreciation has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-depreciations")
    public ResponseEntity<AssetDepreciationDTO> createAssetDepreciation(@Valid @RequestBody AssetDepreciationDTO assetDepreciationDTO) throws URISyntaxException {
        log.debug("REST request to save AssetDepreciation : {}", assetDepreciationDTO);
        if (assetDepreciationDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetDepreciation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetDepreciationDTO result = assetDepreciationService.save(assetDepreciationDTO);
        return ResponseEntity.created(new URI("/api/asset-depreciations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-depreciations} : Updates an existing assetDepreciation.
     *
     * @param assetDepreciationDTO the assetDepreciationDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetDepreciationDTO,
     * or with status {@code 400 (Bad Request)} if the assetDepreciationDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetDepreciationDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-depreciations")
    public ResponseEntity<AssetDepreciationDTO> updateAssetDepreciation(@Valid @RequestBody AssetDepreciationDTO assetDepreciationDTO) throws URISyntaxException {
        log.debug("REST request to update AssetDepreciation : {}", assetDepreciationDTO);
        if (assetDepreciationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetDepreciationDTO result = assetDepreciationService.save(assetDepreciationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetDepreciationDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asset-depreciations} : get all the assetDepreciations.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetDepreciations in body.
     */
    @GetMapping("/asset-depreciations")
    public ResponseEntity<List<AssetDepreciationDTO>> getAllAssetDepreciations(AssetDepreciationCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AssetDepreciations by criteria: {}", criteria);
        Page<AssetDepreciationDTO> page = assetDepreciationQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /asset-depreciations/count} : count all the assetDepreciations.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/asset-depreciations/count")
    public ResponseEntity<Long> countAssetDepreciations(AssetDepreciationCriteria criteria) {
        log.debug("REST request to count AssetDepreciations by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetDepreciationQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-depreciations/:id} : get the "id" assetDepreciation.
     *
     * @param id the id of the assetDepreciationDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetDepreciationDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-depreciations/{id}")
    public ResponseEntity<AssetDepreciationDTO> getAssetDepreciation(@PathVariable Long id) {
        log.debug("REST request to get AssetDepreciation : {}", id);
        Optional<AssetDepreciationDTO> assetDepreciationDTO = assetDepreciationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetDepreciationDTO);
    }

    /**
     * {@code DELETE  /asset-depreciations/:id} : delete the "id" assetDepreciation.
     *
     * @param id the id of the assetDepreciationDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-depreciations/{id}")
    public ResponseEntity<Void> deleteAssetDepreciation(@PathVariable Long id) {
        log.debug("REST request to delete AssetDepreciation : {}", id);
        assetDepreciationService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/asset-depreciations?query=:query} : search for the assetDepreciation corresponding
     * to the query.
     *
     * @param query the query of the assetDepreciation search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/asset-depreciations")
    public ResponseEntity<List<AssetDepreciationDTO>> searchAssetDepreciations(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AssetDepreciations for query {}", query);
        Page<AssetDepreciationDTO> page = assetDepreciationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
