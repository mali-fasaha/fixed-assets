package io.github.assets.web.rest;

import io.github.assets.service.FixedAssetCategoryService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.FixedAssetCategoryDTO;
import io.github.assets.service.dto.FixedAssetCategoryCriteria;
import io.github.assets.service.FixedAssetCategoryQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.FixedAssetCategory}.
 */
@RestController
@RequestMapping("/api")
public class FixedAssetCategoryResource {

    private final Logger log = LoggerFactory.getLogger(FixedAssetCategoryResource.class);

    private static final String ENTITY_NAME = "fixedAssetCategory";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FixedAssetCategoryService fixedAssetCategoryService;

    private final FixedAssetCategoryQueryService fixedAssetCategoryQueryService;

    public FixedAssetCategoryResource(FixedAssetCategoryService fixedAssetCategoryService, FixedAssetCategoryQueryService fixedAssetCategoryQueryService) {
        this.fixedAssetCategoryService = fixedAssetCategoryService;
        this.fixedAssetCategoryQueryService = fixedAssetCategoryQueryService;
    }

    /**
     * {@code POST  /fixed-asset-categories} : Create a new fixedAssetCategory.
     *
     * @param fixedAssetCategoryDTO the fixedAssetCategoryDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fixedAssetCategoryDTO, or with status {@code 400 (Bad Request)} if the fixedAssetCategory has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/fixed-asset-categories")
    public ResponseEntity<FixedAssetCategoryDTO> createFixedAssetCategory(@Valid @RequestBody FixedAssetCategoryDTO fixedAssetCategoryDTO) throws URISyntaxException {
        log.debug("REST request to save FixedAssetCategory : {}", fixedAssetCategoryDTO);
        if (fixedAssetCategoryDTO.getId() != null) {
            throw new BadRequestAlertException("A new fixedAssetCategory cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FixedAssetCategoryDTO result = fixedAssetCategoryService.save(fixedAssetCategoryDTO);
        return ResponseEntity.created(new URI("/api/fixed-asset-categories/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /fixed-asset-categories} : Updates an existing fixedAssetCategory.
     *
     * @param fixedAssetCategoryDTO the fixedAssetCategoryDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fixedAssetCategoryDTO,
     * or with status {@code 400 (Bad Request)} if the fixedAssetCategoryDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fixedAssetCategoryDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/fixed-asset-categories")
    public ResponseEntity<FixedAssetCategoryDTO> updateFixedAssetCategory(@Valid @RequestBody FixedAssetCategoryDTO fixedAssetCategoryDTO) throws URISyntaxException {
        log.debug("REST request to update FixedAssetCategory : {}", fixedAssetCategoryDTO);
        if (fixedAssetCategoryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FixedAssetCategoryDTO result = fixedAssetCategoryService.save(fixedAssetCategoryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fixedAssetCategoryDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /fixed-asset-categories} : get all the fixedAssetCategories.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fixedAssetCategories in body.
     */
    @GetMapping("/fixed-asset-categories")
    public ResponseEntity<List<FixedAssetCategoryDTO>> getAllFixedAssetCategories(FixedAssetCategoryCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get FixedAssetCategories by criteria: {}", criteria);
        Page<FixedAssetCategoryDTO> page = fixedAssetCategoryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /fixed-asset-categories/count} : count all the fixedAssetCategories.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/fixed-asset-categories/count")
    public ResponseEntity<Long> countFixedAssetCategories(FixedAssetCategoryCriteria criteria) {
        log.debug("REST request to count FixedAssetCategories by criteria: {}", criteria);
        return ResponseEntity.ok().body(fixedAssetCategoryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /fixed-asset-categories/:id} : get the "id" fixedAssetCategory.
     *
     * @param id the id of the fixedAssetCategoryDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fixedAssetCategoryDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/fixed-asset-categories/{id}")
    public ResponseEntity<FixedAssetCategoryDTO> getFixedAssetCategory(@PathVariable Long id) {
        log.debug("REST request to get FixedAssetCategory : {}", id);
        Optional<FixedAssetCategoryDTO> fixedAssetCategoryDTO = fixedAssetCategoryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fixedAssetCategoryDTO);
    }

    /**
     * {@code DELETE  /fixed-asset-categories/:id} : delete the "id" fixedAssetCategory.
     *
     * @param id the id of the fixedAssetCategoryDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/fixed-asset-categories/{id}")
    public ResponseEntity<Void> deleteFixedAssetCategory(@PathVariable Long id) {
        log.debug("REST request to delete FixedAssetCategory : {}", id);
        fixedAssetCategoryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/fixed-asset-categories?query=:query} : search for the fixedAssetCategory corresponding
     * to the query.
     *
     * @param query the query of the fixedAssetCategory search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/fixed-asset-categories")
    public ResponseEntity<List<FixedAssetCategoryDTO>> searchFixedAssetCategories(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of FixedAssetCategories for query {}", query);
        Page<FixedAssetCategoryDTO> page = fixedAssetCategoryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
