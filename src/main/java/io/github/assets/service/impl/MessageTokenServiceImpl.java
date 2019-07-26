package io.github.assets.service.impl;

import io.github.assets.service.MessageTokenService;
import io.github.assets.domain.MessageToken;
import io.github.assets.repository.MessageTokenRepository;
import io.github.assets.repository.search.MessageTokenSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing {@link MessageToken}.
 */
@Service
@Transactional
public class MessageTokenServiceImpl implements MessageTokenService {

    private final Logger log = LoggerFactory.getLogger(MessageTokenServiceImpl.class);

    private final MessageTokenRepository messageTokenRepository;

    private final MessageTokenSearchRepository messageTokenSearchRepository;

    public MessageTokenServiceImpl(MessageTokenRepository messageTokenRepository, MessageTokenSearchRepository messageTokenSearchRepository) {
        this.messageTokenRepository = messageTokenRepository;
        this.messageTokenSearchRepository = messageTokenSearchRepository;
    }

    /**
     * Save a messageToken.
     *
     * @param messageToken the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MessageToken save(MessageToken messageToken) {
        log.debug("Request to save MessageToken : {}", messageToken);
        MessageToken result = messageTokenRepository.save(messageToken);
        messageTokenSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the messageTokens.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MessageToken> findAll(Pageable pageable) {
        log.debug("Request to get all MessageTokens");
        return messageTokenRepository.findAll(pageable);
    }


    /**
     * Get one messageToken by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MessageToken> findOne(Long id) {
        log.debug("Request to get MessageToken : {}", id);
        return messageTokenRepository.findById(id);
    }

    /**
     * Delete the messageToken by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MessageToken : {}", id);
        messageTokenRepository.deleteById(id);
        messageTokenSearchRepository.deleteById(id);
    }

    /**
     * Search for the messageToken corresponding to the query.
     *
     * @param query the query of the search.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MessageToken> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of MessageTokens for query {}", query);
        return messageTokenSearchRepository.search(queryStringQuery(query), pageable);    }
}
