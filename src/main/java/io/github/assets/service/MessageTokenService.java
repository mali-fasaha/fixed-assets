package io.github.assets.service;

import io.github.assets.domain.MessageToken;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MessageToken}.
 */
public interface MessageTokenService {

    /**
     * Save a messageToken.
     *
     * @param messageToken the entity to save.
     * @return the persisted entity.
     */
    MessageToken save(MessageToken messageToken);

    /**
     * Get all the messageTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageToken> findAll(Pageable pageable);


    /**
     * Get the "id" messageToken.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MessageToken> findOne(Long id);

    /**
     * Delete the "id" messageToken.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Search for the messageToken corresponding to the query.
     *
     * @param query the query of the search.
     * 
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MessageToken> search(String query, Pageable pageable);
}
