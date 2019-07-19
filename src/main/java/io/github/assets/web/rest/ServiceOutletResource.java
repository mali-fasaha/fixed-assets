package io.github.assets.web.rest;

import io.github.assets.service.ServiceOutletService;
import io.github.assets.web.rest.errors.BadRequestAlertException;
import io.github.assets.service.dto.ServiceOutletDTO;
import io.github.assets.service.dto.ServiceOutletCriteria;
import io.github.assets.service.ServiceOutletQueryService;

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
 * REST controller for managing {@link io.github.assets.domain.ServiceOutlet}.
 */
@RestController
@RequestMapping("/api")
public class ServiceOutletResource {

    private final Logger log = LoggerFactory.getLogger(ServiceOutletResource.class);

    private static final String ENTITY_NAME = "serviceOutlet";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ServiceOutletService serviceOutletService;

    private final ServiceOutletQueryService serviceOutletQueryService;

    public ServiceOutletResource(ServiceOutletService serviceOutletService, ServiceOutletQueryService serviceOutletQueryService) {
        this.serviceOutletService = serviceOutletService;
        this.serviceOutletQueryService = serviceOutletQueryService;
    }

    /**
     * {@code POST  /service-outlets} : Create a new serviceOutlet.
     *
     * @param serviceOutletDTO the serviceOutletDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new serviceOutletDTO, or with status {@code 400 (Bad Request)} if the serviceOutlet has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/service-outlets")
    public ResponseEntity<ServiceOutletDTO> createServiceOutlet(@Valid @RequestBody ServiceOutletDTO serviceOutletDTO) throws URISyntaxException {
        log.debug("REST request to save ServiceOutlet : {}", serviceOutletDTO);
        if (serviceOutletDTO.getId() != null) {
            throw new BadRequestAlertException("A new serviceOutlet cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ServiceOutletDTO result = serviceOutletService.save(serviceOutletDTO);
        return ResponseEntity.created(new URI("/api/service-outlets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /service-outlets} : Updates an existing serviceOutlet.
     *
     * @param serviceOutletDTO the serviceOutletDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated serviceOutletDTO,
     * or with status {@code 400 (Bad Request)} if the serviceOutletDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the serviceOutletDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/service-outlets")
    public ResponseEntity<ServiceOutletDTO> updateServiceOutlet(@Valid @RequestBody ServiceOutletDTO serviceOutletDTO) throws URISyntaxException {
        log.debug("REST request to update ServiceOutlet : {}", serviceOutletDTO);
        if (serviceOutletDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ServiceOutletDTO result = serviceOutletService.save(serviceOutletDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, serviceOutletDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /service-outlets} : get all the serviceOutlets.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of serviceOutlets in body.
     */
    @GetMapping("/service-outlets")
    public ResponseEntity<List<ServiceOutletDTO>> getAllServiceOutlets(ServiceOutletCriteria criteria, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get ServiceOutlets by criteria: {}", criteria);
        Page<ServiceOutletDTO> page = serviceOutletQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
    * {@code GET  /service-outlets/count} : count all the serviceOutlets.
    *
    * @param criteria the criteria which the requested entities should match.
    * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
    */
    @GetMapping("/service-outlets/count")
    public ResponseEntity<Long> countServiceOutlets(ServiceOutletCriteria criteria) {
        log.debug("REST request to count ServiceOutlets by criteria: {}", criteria);
        return ResponseEntity.ok().body(serviceOutletQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /service-outlets/:id} : get the "id" serviceOutlet.
     *
     * @param id the id of the serviceOutletDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the serviceOutletDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/service-outlets/{id}")
    public ResponseEntity<ServiceOutletDTO> getServiceOutlet(@PathVariable Long id) {
        log.debug("REST request to get ServiceOutlet : {}", id);
        Optional<ServiceOutletDTO> serviceOutletDTO = serviceOutletService.findOne(id);
        return ResponseUtil.wrapOrNotFound(serviceOutletDTO);
    }

    /**
     * {@code DELETE  /service-outlets/:id} : delete the "id" serviceOutlet.
     *
     * @param id the id of the serviceOutletDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/service-outlets/{id}")
    public ResponseEntity<Void> deleteServiceOutlet(@PathVariable Long id) {
        log.debug("REST request to delete ServiceOutlet : {}", id);
        serviceOutletService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/service-outlets?query=:query} : search for the serviceOutlet corresponding
     * to the query.
     *
     * @param query the query of the serviceOutlet search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/service-outlets")
    public ResponseEntity<List<ServiceOutletDTO>> searchServiceOutlets(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of ServiceOutlets for query {}", query);
        Page<ServiceOutletDTO> page = serviceOutletService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
