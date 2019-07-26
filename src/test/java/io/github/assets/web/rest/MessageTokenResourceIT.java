package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.MessageToken;
import io.github.assets.repository.MessageTokenRepository;
import io.github.assets.repository.search.MessageTokenSearchRepository;
import io.github.assets.service.MessageTokenService;
import io.github.assets.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;

import static io.github.assets.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@Link MessageTokenResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class MessageTokenResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_TIME_SENT = 1L;
    private static final Long UPDATED_TIME_SENT = 2L;

    private static final String DEFAULT_TOKEN_VALUE = "AAAAAAAAAA";
    private static final String UPDATED_TOKEN_VALUE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_RECEIVED = false;
    private static final Boolean UPDATED_RECEIVED = true;

    @Autowired
    private MessageTokenRepository messageTokenRepository;

    @Autowired
    private MessageTokenService messageTokenService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.MessageTokenSearchRepositoryMockConfiguration
     */
    @Autowired
    private MessageTokenSearchRepository mockMessageTokenSearchRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restMessageTokenMockMvc;

    private MessageToken messageToken;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessageTokenResource messageTokenResource = new MessageTokenResource(messageTokenService);
        this.restMessageTokenMockMvc = MockMvcBuilders.standaloneSetup(messageTokenResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageToken createEntity(EntityManager em) {
        MessageToken messageToken = new MessageToken()
            .description(DEFAULT_DESCRIPTION)
            .timeSent(DEFAULT_TIME_SENT)
            .tokenValue(DEFAULT_TOKEN_VALUE)
            .received(DEFAULT_RECEIVED);
        return messageToken;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MessageToken createUpdatedEntity(EntityManager em) {
        MessageToken messageToken = new MessageToken()
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED);
        return messageToken;
    }

    @BeforeEach
    public void initTest() {
        messageToken = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessageToken() throws Exception {
        int databaseSizeBeforeCreate = messageTokenRepository.findAll().size();

        // Create the MessageToken
        restMessageTokenMockMvc.perform(post("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageToken)))
            .andExpect(status().isCreated());

        // Validate the MessageToken in the database
        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeCreate + 1);
        MessageToken testMessageToken = messageTokenList.get(messageTokenList.size() - 1);
        assertThat(testMessageToken.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testMessageToken.getTimeSent()).isEqualTo(DEFAULT_TIME_SENT);
        assertThat(testMessageToken.getTokenValue()).isEqualTo(DEFAULT_TOKEN_VALUE);
        assertThat(testMessageToken.isReceived()).isEqualTo(DEFAULT_RECEIVED);

        // Validate the MessageToken in Elasticsearch
        verify(mockMessageTokenSearchRepository, times(1)).save(testMessageToken);
    }

    @Test
    @Transactional
    public void createMessageTokenWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messageTokenRepository.findAll().size();

        // Create the MessageToken with an existing ID
        messageToken.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessageTokenMockMvc.perform(post("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageToken)))
            .andExpect(status().isBadRequest());

        // Validate the MessageToken in the database
        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeCreate);

        // Validate the MessageToken in Elasticsearch
        verify(mockMessageTokenSearchRepository, times(0)).save(messageToken);
    }


    @Test
    @Transactional
    public void checkTimeSentIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTokenRepository.findAll().size();
        // set the field null
        messageToken.setTimeSent(null);

        // Create the MessageToken, which fails.

        restMessageTokenMockMvc.perform(post("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageToken)))
            .andExpect(status().isBadRequest());

        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTokenValueIsRequired() throws Exception {
        int databaseSizeBeforeTest = messageTokenRepository.findAll().size();
        // set the field null
        messageToken.setTokenValue(null);

        // Create the MessageToken, which fails.

        restMessageTokenMockMvc.perform(post("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageToken)))
            .andExpect(status().isBadRequest());

        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMessageTokens() throws Exception {
        // Initialize the database
        messageTokenRepository.saveAndFlush(messageToken);

        // Get all the messageTokenList
        restMessageTokenMockMvc.perform(get("/api/message-tokens?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE.toString())))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())));
    }
    
    @Test
    @Transactional
    public void getMessageToken() throws Exception {
        // Initialize the database
        messageTokenRepository.saveAndFlush(messageToken);

        // Get the messageToken
        restMessageTokenMockMvc.perform(get("/api/message-tokens/{id}", messageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messageToken.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.timeSent").value(DEFAULT_TIME_SENT.intValue()))
            .andExpect(jsonPath("$.tokenValue").value(DEFAULT_TOKEN_VALUE.toString()))
            .andExpect(jsonPath("$.received").value(DEFAULT_RECEIVED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMessageToken() throws Exception {
        // Get the messageToken
        restMessageTokenMockMvc.perform(get("/api/message-tokens/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessageToken() throws Exception {
        // Initialize the database
        messageTokenService.save(messageToken);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockMessageTokenSearchRepository);

        int databaseSizeBeforeUpdate = messageTokenRepository.findAll().size();

        // Update the messageToken
        MessageToken updatedMessageToken = messageTokenRepository.findById(messageToken.getId()).get();
        // Disconnect from session so that the updates on updatedMessageToken are not directly saved in db
        em.detach(updatedMessageToken);
        updatedMessageToken
            .description(UPDATED_DESCRIPTION)
            .timeSent(UPDATED_TIME_SENT)
            .tokenValue(UPDATED_TOKEN_VALUE)
            .received(UPDATED_RECEIVED);

        restMessageTokenMockMvc.perform(put("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedMessageToken)))
            .andExpect(status().isOk());

        // Validate the MessageToken in the database
        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeUpdate);
        MessageToken testMessageToken = messageTokenList.get(messageTokenList.size() - 1);
        assertThat(testMessageToken.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testMessageToken.getTimeSent()).isEqualTo(UPDATED_TIME_SENT);
        assertThat(testMessageToken.getTokenValue()).isEqualTo(UPDATED_TOKEN_VALUE);
        assertThat(testMessageToken.isReceived()).isEqualTo(UPDATED_RECEIVED);

        // Validate the MessageToken in Elasticsearch
        verify(mockMessageTokenSearchRepository, times(1)).save(testMessageToken);
    }

    @Test
    @Transactional
    public void updateNonExistingMessageToken() throws Exception {
        int databaseSizeBeforeUpdate = messageTokenRepository.findAll().size();

        // Create the MessageToken

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMessageTokenMockMvc.perform(put("/api/message-tokens")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messageToken)))
            .andExpect(status().isBadRequest());

        // Validate the MessageToken in the database
        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeUpdate);

        // Validate the MessageToken in Elasticsearch
        verify(mockMessageTokenSearchRepository, times(0)).save(messageToken);
    }

    @Test
    @Transactional
    public void deleteMessageToken() throws Exception {
        // Initialize the database
        messageTokenService.save(messageToken);

        int databaseSizeBeforeDelete = messageTokenRepository.findAll().size();

        // Delete the messageToken
        restMessageTokenMockMvc.perform(delete("/api/message-tokens/{id}", messageToken.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<MessageToken> messageTokenList = messageTokenRepository.findAll();
        assertThat(messageTokenList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the MessageToken in Elasticsearch
        verify(mockMessageTokenSearchRepository, times(1)).deleteById(messageToken.getId());
    }

    @Test
    @Transactional
    public void searchMessageToken() throws Exception {
        // Initialize the database
        messageTokenService.save(messageToken);
        when(mockMessageTokenSearchRepository.search(queryStringQuery("id:" + messageToken.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(messageToken), PageRequest.of(0, 1), 1));
        // Search the messageToken
        restMessageTokenMockMvc.perform(get("/api/_search/message-tokens?query=id:" + messageToken.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messageToken.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].timeSent").value(hasItem(DEFAULT_TIME_SENT.intValue())))
            .andExpect(jsonPath("$.[*].tokenValue").value(hasItem(DEFAULT_TOKEN_VALUE)))
            .andExpect(jsonPath("$.[*].received").value(hasItem(DEFAULT_RECEIVED.booleanValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessageToken.class);
        MessageToken messageToken1 = new MessageToken();
        messageToken1.setId(1L);
        MessageToken messageToken2 = new MessageToken();
        messageToken2.setId(messageToken1.getId());
        assertThat(messageToken1).isEqualTo(messageToken2);
        messageToken2.setId(2L);
        assertThat(messageToken1).isNotEqualTo(messageToken2);
        messageToken1.setId(null);
        assertThat(messageToken1).isNotEqualTo(messageToken2);
    }
}
