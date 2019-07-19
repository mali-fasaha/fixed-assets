package io.github.assets.web.rest;

import io.github.assets.service.FixedAssetItemService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.FixedAssetItemDTO;
import io.github.assets.service.dto.FixedAssetItemCriteria;
import io.github.assets.service.FixedAssetItemQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.FixedAssetItem}.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetItemResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetItemResource.class);

    private static final String ENTITY_NAME = "fixedAssetItem";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixedAssetItemService fixedAssetItemService;

    private final FixedAssetItemQueryService fixedAssetItemQueryService;

    public FixedAssetItemResource(FixedAssetItemService fixedAssetItemService, FixedAssetItemQueryService fixedAssetItemQueryService) {
        this.fixedAssetItemService = fixedAssetItemService;
        this.fixedAssetItemQueryService = fixedAssetItemQueryService;
    }

    /**
     * {@code POST  /fixed-asset-items} : Create a new fixedAssetItem.
     *
     * @param fixedAssetItemDTO the fixedAssetItemDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixedAssetItemDTO, or with status {@code 400 (Bad Request)} if the fixedAssetItem has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixed-asset-items")
    public ResponseEntity<FixedAssetItemDTO> createFixedAssetItem(@Valid @RequestBody FixedAssetItemDTO fixedAssetItemDTO) throws URISyntaxException {
        log.debug("REST request to save FixedAssetItem : {}", fixedAssetItemDTO);
        if (fixedAssetItemDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssetItem cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssetItemDTO result = fixedAssetItemService.save(fixedAssetItemDTO);
        return ResponseEntity.created(new URI("/api/fixed-asset-items/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixed-asset-items} : Updates an existing fixedAssetItem.
     *
     * @param fixedAssetItemDTO the fixedAssetItemDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixedAssetItemDTO,
     * or with status {@code 400 (Bad Request)} if the fixedAssetItemDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixedAssetItemDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixed-asset-items")
    public ResponseEntity<FixedAssetItemDTO> updateFixedAssetItem(@Valid @RequestBody FixedAssetItemDTO fixedAssetItemDTO) throws URISyntaxException {
        log.debug("REST request to update FixedAssetItem : {}", fixedAssetItemDTO);
        if (fixedAssetItemDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssetItemDTO result = fixedAssetItemService.save(fixedAssetItemDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fixedAssetItemDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixed-asset-items} : get all the fixedAssetItems.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetItems in body.
     */
    @GetMapping("/fixed-asset-items")
    public ResponseEntity<List<FixedAssetItemDTO>> getAllFixedAssetItems(FixedAssetItemCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FixedAssetItems by criteria: {}", criteria);
        Page<FixedAssetItemDTO> page = fixedAssetItemQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fixed-asset-items/count} : count all the fixedAssetItems.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fixed-asset-items/count")
    public ResponseEntity<Long> countFixedAssetItems(FixedAssetItemCriteria criteria) {
        log.debug("REST request to count FixedAssetItems by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixedAssetItemQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixed-asset-items/:id} : get the "id" fixedAssetItem.
     *
     * @param id the id of the fixedAssetItemDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixedAssetItemDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixed-asset-items/{id}")
    public ResponseEntity<FixedAssetItemDTO> getFixedAssetItem(@PathVariable Long id) {
        log.debug("REST request to get FixedAssetItem : {}", id);
        Optional<FixedAssetItemDTO> fixedAssetItemDTO = fixedAssetItemService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedAssetItemDTO);
    }

    /**
     * {@code DELETE  /fixed-asset-items/:id} : delete the "id" fixedAssetItem.
     *
     * @param id the id of the fixedAssetItemDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixed-asset-items/{id}")
    public ResponseEntity<Void> deleteFixedAssetItem(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssetItem : {}", id);
        fixedAssetItemService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fixed-asset-items?query=:query} : search for the fixedAssetItem corresponding
     * to the query.
     *
     * @param query the query of the fixedAssetItem search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fixed-asset-items")
    public ResponseEntity<List<FixedAssetItemDTO>> searchFixedAssetItems(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of FixedAssetItems for query {}", query);
        Page<FixedAssetItemDTO> page = fixedAssetItemService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
