package io.github.assets.web.rest;

import io.github.assets.domain.MessageToken;
import io.github.assets.service.MessageTokenService;
import io.github.assets.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link io.github.assets.domain.MessageToken}.
 */
@RestController
@RequestMapping("/api")
public class MessageTokenResource {

    private final Logger log = LoggerFactory.getLogger(MessageTokenResource.class);

    private static final String ENTITY_NAME = "messageToken";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MessageTokenService messageTokenService;

    public MessageTokenResource(MessageTokenService messageTokenService) {
        this.messageTokenService = messageTokenService;
    }

    /**
     * {@code POST  /message-tokens} : Create a new messageToken.
     *
     * @param messageToken the messageToken to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new messageToken, or with status {@code 400 (Bad Request)} if the messageToken has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/message-tokens")
    public ResponseEntity<MessageToken> createMessageToken(@Valid @RequestBody MessageToken messageToken) throws URISyntaxException {
        log.debug("REST request to save MessageToken : {}", messageToken);
        if (messageToken.getId() != null) {
            throw new BadRequestAlertException("A new messageToken cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessageToken result = messageTokenService.save(messageToken);
        return ResponseEntity.created(new URI("/api/message-tokens/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /message-tokens} : Updates an existing messageToken.
     *
     * @param messageToken the messageToken to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated messageToken,
     * or with status {@code 400 (Bad Request)} if the messageToken is not valid,
     * or with status {@code 500 (Internal Server Error)} if the messageToken couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/message-tokens")
    public ResponseEntity<MessageToken> updateMessageToken(@Valid @RequestBody MessageToken messageToken) throws URISyntaxException {
        log.debug("REST request to update MessageToken : {}", messageToken);
        if (messageToken.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        MessageToken result = messageTokenService.save(messageToken);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, messageToken.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /message-tokens} : get all the messageTokens.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of messageTokens in body.
     */
    @GetMapping("/message-tokens")
    public ResponseEntity<List<MessageToken>> getAllMessageTokens(Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to get a page of MessageTokens");
        Page<MessageToken> page = messageTokenService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /message-tokens/:id} : get the "id" messageToken.
     *
     * @param id the id of the messageToken to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the messageToken, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/message-tokens/{id}")
    public ResponseEntity<MessageToken> getMessageToken(@PathVariable Long id) {
        log.debug("REST request to get MessageToken : {}", id);
        Optional<MessageToken> messageToken = messageTokenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(messageToken);
    }

    /**
     * {@code DELETE  /message-tokens/:id} : delete the "id" messageToken.
     *
     * @param id the id of the messageToken to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/message-tokens/{id}")
    public ResponseEntity<Void> deleteMessageToken(@PathVariable Long id) {
        log.debug("REST request to delete MessageToken : {}", id);
        messageTokenService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }

    /**
     * {@code SEARCH  /_search/message-tokens?query=:query} : search for the messageToken corresponding
     * to the query.
     *
     * @param query the query of the messageToken search.
     * @param pageable the pagination information.
     * @return the result of the search.
     */
    @GetMapping("/_search/message-tokens")
    public ResponseEntity<List<MessageToken>> searchMessageTokens(@RequestParam String query, Pageable pageable, @RequestParam MultiValueMap<String, String> queryParams, UriComponentsBuilder uriBuilder) {
        log.debug("REST request to search for a page of MessageTokens for query {}", query);
        Page<MessageToken> page = messageTokenService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(uriBuilder.queryParams(queryParams), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
