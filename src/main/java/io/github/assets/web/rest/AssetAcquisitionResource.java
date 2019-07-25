package io.github.assets.web.rest;

import io.github.assets.service.AssetAcquisitionService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.AssetAcquisitionDTO;
import io.github.assets.service.dto.AssetAcquisitionCriteria;
import io.github.assets.service.AssetAcquisitionQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.AssetAcquisition}.
 */
@RestController
@RequestMapping("/api")
public class AssetAcquisitionResource {

    private final Logger log = LoggerFactory.getLogger(AssetAcquisitionResource.class);

    private static final String ENTITY_NAME = "assetAcquisition";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetAcquisitionService assetAcquisitionService;

    private final AssetAcquisitionQueryService assetAcquisitionQueryService;

    public AssetAcquisitionResource(AssetAcquisitionService assetAcquisitionService, AssetAcquisitionQueryService assetAcquisitionQueryService) {
        this.assetAcquisitionService = assetAcquisitionService;
        this.assetAcquisitionQueryService = assetAcquisitionQueryService;
    }

    /**
     * {@code POST  /asset-acquisitions} : Create a new assetAcquisition.
     *
     * @param assetAcquisitionDTO the assetAcquisitionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new assetAcquisitionDTO, or with status {@code 400 (Bad Request)} if the assetAcquisition has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/asset-acquisitions")
    public ResponseEntity<AssetAcquisitionDTO> createAssetAcquisition(@Valid @RequestBody AssetAcquisitionDTO assetAcquisitionDTO) throws URISyntaxException {
        log.debug("REST request to save AssetAcquisition : {}", assetAcquisitionDTO);
        if (assetAcquisitionDTO.getId() != null) {
            throw new BadRequestAlertException("A new assetAcquisition cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AssetAcquisitionDTO result = assetAcquisitionService.save(assetAcquisitionDTO);
        return ResponseEntity.created(new URI("/api/asset-acquisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /asset-acquisitions} : Updates an existing assetAcquisition.
     *
     * @param assetAcquisitionDTO the assetAcquisitionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated assetAcquisitionDTO,
     * or with status {@code 400 (Bad Request)} if the assetAcquisitionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the assetAcquisitionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/asset-acquisitions")
    public ResponseEntity<AssetAcquisitionDTO> updateAssetAcquisition(@Valid @RequestBody AssetAcquisitionDTO assetAcquisitionDTO) throws URISyntaxException {
        log.debug("REST request to update AssetAcquisition : {}", assetAcquisitionDTO);
        if (assetAcquisitionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AssetAcquisitionDTO result = assetAcquisitionService.save(assetAcquisitionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, assetAcquisitionDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /asset-acquisitions} : get all the assetAcquisitions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of assetAcquisitions in body.
     */
    @GetMapping("/asset-acquisitions")
    public ResponseEntity<List<AssetAcquisitionDTO>> getAllAssetAcquisitions(AssetAcquisitionCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get AssetAcquisitions by criteria: {}", criteria);
        Page<AssetAcquisitionDTO> page = assetAcquisitionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /asset-acquisitions/count} : count all the assetAcquisitions.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/asset-acquisitions/count")
    public ResponseEntity<Long> countAssetAcquisitions(AssetAcquisitionCriteria criteria) {
        log.debug("REST request to count AssetAcquisitions by criteria: {}", criteria);
        return ResponseEntity.ok().body(assetAcquisitionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /asset-acquisitions/:id} : get the "id" assetAcquisition.
     *
     * @param id the id of the assetAcquisitionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the assetAcquisitionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/asset-acquisitions/{id}")
    public ResponseEntity<AssetAcquisitionDTO> getAssetAcquisition(@PathVariable Long id) {
        log.debug("REST request to get AssetAcquisition : {}", id);
        Optional<AssetAcquisitionDTO> assetAcquisitionDTO = assetAcquisitionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(assetAcquisitionDTO);
    }

    /**
     * {@code DELETE  /asset-acquisitions/:id} : delete the "id" assetAcquisition.
     *
     * @param id the id of the assetAcquisitionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/asset-acquisitions/{id}")
    public ResponseEntity<Void> deleteAssetAcquisition(@PathVariable Long id) {
        log.debug("REST request to delete AssetAcquisition : {}", id);
        assetAcquisitionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/asset-acquisitions?query=:query} : search for the assetAcquisition corresponding
     * to the query.
     *
     * @param query the query of the assetAcquisition search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/asset-acquisitions")
    public ResponseEntity<List<AssetAcquisitionDTO>> searchAssetAcquisitions(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of AssetAcquisitions for query {}", query);
        Page<AssetAcquisitionDTO> page = assetAcquisitionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
