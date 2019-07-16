package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.AssetTransaction;
import io.github.assets.repository.AssetTransactionRepository;
import io.github.assets.repository.search.AssetTransactionSearchRepository;
import io.github.assets.service.AssetTransactionService;
import io.github.assets.service.dto.AssetTransactionDTO;
import io.github.assets.service.mapper.AssetTransactionMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.AssetTransactionCriteria;
import io.github.assets.service.AssetTransactionQueryService;

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
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@Link AssetTransactionResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = FixedAssetsApp.class)
public class AssetTransactionResourceIT {

    private static final String DEFAULT_TRANSACTION_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_REFERENCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TRANSACTION_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_SCANNED_DOCUMENT_ID = 1L;
    private static final Long UPDATED_SCANNED_DOCUMENT_ID = 2L;

    @Autowired
    private AssetTransactionRepository assetTransactionRepository;

    @Autowired
    private AssetTransactionMapper assetTransactionMapper;

    @Autowired
    private AssetTransactionService assetTransactionService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.AssetTransactionSearchRepositoryMockConfiguration
     */
    @Autowired
    private AssetTransactionSearchRepository mockAssetTransactionSearchRepository;

    @Autowired
    private AssetTransactionQueryService assetTransactionQueryService;

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

    private MockMvc restAssetTransactionMockMvc;

    private AssetTransaction assetTransaction;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetTransactionResource assetTransactionResource = new AssetTransactionResource(assetTransactionService, assetTransactionQueryService);
        this.restAssetTransactionMockMvc = MockMvcBuilders.standaloneSetup(assetTransactionResource)
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
    public static AssetTransaction createEntity(EntityManager em) {
        AssetTransaction assetTransaction = new AssetTransaction()
            .transactionReference(DEFAULT_TRANSACTION_REFERENCE)
            .transactionDate(DEFAULT_TRANSACTION_DATE)
            .scannedDocumentId(DEFAULT_SCANNED_DOCUMENT_ID);
        return assetTransaction;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetTransaction createUpdatedEntity(EntityManager em) {
        AssetTransaction assetTransaction = new AssetTransaction()
            .transactionReference(UPDATED_TRANSACTION_REFERENCE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID);
        return assetTransaction;
    }

    @BeforeEach
    public void initTest() {
        assetTransaction = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetTransaction() throws Exception {
        int databaseSizeBeforeCreate = assetTransactionRepository.findAll().size();

        // Create the AssetTransaction
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(assetTransaction);
        restAssetTransactionMockMvc.perform(post("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetTransaction in the database
        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeCreate + 1);
        AssetTransaction testAssetTransaction = assetTransactionList.get(assetTransactionList.size() - 1);
        assertThat(testAssetTransaction.getTransactionReference()).isEqualTo(DEFAULT_TRANSACTION_REFERENCE);
        assertThat(testAssetTransaction.getTransactionDate()).isEqualTo(DEFAULT_TRANSACTION_DATE);
        assertThat(testAssetTransaction.getScannedDocumentId()).isEqualTo(DEFAULT_SCANNED_DOCUMENT_ID);

        // Validate the AssetTransaction in Elasticsearch
        verify(mockAssetTransactionSearchRepository, times(1)).save(testAssetTransaction);
    }

    @Test
    @Transactional
    public void createAssetTransactionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetTransactionRepository.findAll().size();

        // Create the AssetTransaction with an existing ID
        assetTransaction.setId(1L);
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(assetTransaction);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetTransactionMockMvc.perform(post("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetTransaction in the database
        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeCreate);

        // Validate the AssetTransaction in Elasticsearch
        verify(mockAssetTransactionSearchRepository, times(0)).save(assetTransaction);
    }


    @Test
    @Transactional
    public void checkTransactionReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetTransactionRepository.findAll().size();
        // set the field null
        assetTransaction.setTransactionReference(null);

        // Create the AssetTransaction, which fails.
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(assetTransaction);

        restAssetTransactionMockMvc.perform(post("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetTransactionRepository.findAll().size();
        // set the field null
        assetTransaction.setTransactionDate(null);

        // Create the AssetTransaction, which fails.
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(assetTransaction);

        restAssetTransactionMockMvc.perform(post("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isBadRequest());

        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetTransactions() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionReference").value(hasItem(DEFAULT_TRANSACTION_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getAssetTransaction() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get the assetTransaction
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions/{id}", assetTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetTransaction.getId().intValue()))
            .andExpect(jsonPath("$.transactionReference").value(DEFAULT_TRANSACTION_REFERENCE.toString()))
            .andExpect(jsonPath("$.transactionDate").value(DEFAULT_TRANSACTION_DATE.toString()))
            .andExpect(jsonPath("$.scannedDocumentId").value(DEFAULT_SCANNED_DOCUMENT_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionReference equals to DEFAULT_TRANSACTION_REFERENCE
        defaultAssetTransactionShouldBeFound("transactionReference.equals=" + DEFAULT_TRANSACTION_REFERENCE);

        // Get all the assetTransactionList where transactionReference equals to UPDATED_TRANSACTION_REFERENCE
        defaultAssetTransactionShouldNotBeFound("transactionReference.equals=" + UPDATED_TRANSACTION_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionReference in DEFAULT_TRANSACTION_REFERENCE or UPDATED_TRANSACTION_REFERENCE
        defaultAssetTransactionShouldBeFound("transactionReference.in=" + DEFAULT_TRANSACTION_REFERENCE + "," + UPDATED_TRANSACTION_REFERENCE);

        // Get all the assetTransactionList where transactionReference equals to UPDATED_TRANSACTION_REFERENCE
        defaultAssetTransactionShouldNotBeFound("transactionReference.in=" + UPDATED_TRANSACTION_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionReference is not null
        defaultAssetTransactionShouldBeFound("transactionReference.specified=true");

        // Get all the assetTransactionList where transactionReference is null
        defaultAssetTransactionShouldNotBeFound("transactionReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionDate equals to DEFAULT_TRANSACTION_DATE
        defaultAssetTransactionShouldBeFound("transactionDate.equals=" + DEFAULT_TRANSACTION_DATE);

        // Get all the assetTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultAssetTransactionShouldNotBeFound("transactionDate.equals=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionDate in DEFAULT_TRANSACTION_DATE or UPDATED_TRANSACTION_DATE
        defaultAssetTransactionShouldBeFound("transactionDate.in=" + DEFAULT_TRANSACTION_DATE + "," + UPDATED_TRANSACTION_DATE);

        // Get all the assetTransactionList where transactionDate equals to UPDATED_TRANSACTION_DATE
        defaultAssetTransactionShouldNotBeFound("transactionDate.in=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionDate is not null
        defaultAssetTransactionShouldBeFound("transactionDate.specified=true");

        // Get all the assetTransactionList where transactionDate is null
        defaultAssetTransactionShouldNotBeFound("transactionDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionDate greater than or equals to DEFAULT_TRANSACTION_DATE
        defaultAssetTransactionShouldBeFound("transactionDate.greaterOrEqualThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the assetTransactionList where transactionDate greater than or equals to UPDATED_TRANSACTION_DATE
        defaultAssetTransactionShouldNotBeFound("transactionDate.greaterOrEqualThan=" + UPDATED_TRANSACTION_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByTransactionDateIsLessThanSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where transactionDate less than or equals to DEFAULT_TRANSACTION_DATE
        defaultAssetTransactionShouldNotBeFound("transactionDate.lessThan=" + DEFAULT_TRANSACTION_DATE);

        // Get all the assetTransactionList where transactionDate less than or equals to UPDATED_TRANSACTION_DATE
        defaultAssetTransactionShouldBeFound("transactionDate.lessThan=" + UPDATED_TRANSACTION_DATE);
    }


    @Test
    @Transactional
    public void getAllAssetTransactionsByScannedDocumentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where scannedDocumentId equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldBeFound("scannedDocumentId.equals=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetTransactionList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldNotBeFound("scannedDocumentId.equals=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByScannedDocumentIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where scannedDocumentId in DEFAULT_SCANNED_DOCUMENT_ID or UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldBeFound("scannedDocumentId.in=" + DEFAULT_SCANNED_DOCUMENT_ID + "," + UPDATED_SCANNED_DOCUMENT_ID);

        // Get all the assetTransactionList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldNotBeFound("scannedDocumentId.in=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByScannedDocumentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where scannedDocumentId is not null
        defaultAssetTransactionShouldBeFound("scannedDocumentId.specified=true");

        // Get all the assetTransactionList where scannedDocumentId is null
        defaultAssetTransactionShouldNotBeFound("scannedDocumentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByScannedDocumentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where scannedDocumentId greater than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldBeFound("scannedDocumentId.greaterOrEqualThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetTransactionList where scannedDocumentId greater than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldNotBeFound("scannedDocumentId.greaterOrEqualThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetTransactionsByScannedDocumentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        // Get all the assetTransactionList where scannedDocumentId less than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldNotBeFound("scannedDocumentId.lessThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetTransactionList where scannedDocumentId less than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetTransactionShouldBeFound("scannedDocumentId.lessThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetTransactionShouldBeFound(String filter) throws Exception {
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionReference").value(hasItem(DEFAULT_TRANSACTION_REFERENCE)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));

        // Check, that the count call also returns 1
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetTransactionShouldNotBeFound(String filter) throws Exception {
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAssetTransaction() throws Exception {
        // Get the assetTransaction
        restAssetTransactionMockMvc.perform(get("/api/asset-transactions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetTransaction() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        int databaseSizeBeforeUpdate = assetTransactionRepository.findAll().size();

        // Update the assetTransaction
        AssetTransaction updatedAssetTransaction = assetTransactionRepository.findById(assetTransaction.getId()).get();
        // Disconnect from session so that the updates on updatedAssetTransaction are not directly saved in db
        em.detach(updatedAssetTransaction);
        updatedAssetTransaction
            .transactionReference(UPDATED_TRANSACTION_REFERENCE)
            .transactionDate(UPDATED_TRANSACTION_DATE)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID);
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(updatedAssetTransaction);

        restAssetTransactionMockMvc.perform(put("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isOk());

        // Validate the AssetTransaction in the database
        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeUpdate);
        AssetTransaction testAssetTransaction = assetTransactionList.get(assetTransactionList.size() - 1);
        assertThat(testAssetTransaction.getTransactionReference()).isEqualTo(UPDATED_TRANSACTION_REFERENCE);
        assertThat(testAssetTransaction.getTransactionDate()).isEqualTo(UPDATED_TRANSACTION_DATE);
        assertThat(testAssetTransaction.getScannedDocumentId()).isEqualTo(UPDATED_SCANNED_DOCUMENT_ID);

        // Validate the AssetTransaction in Elasticsearch
        verify(mockAssetTransactionSearchRepository, times(1)).save(testAssetTransaction);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetTransaction() throws Exception {
        int databaseSizeBeforeUpdate = assetTransactionRepository.findAll().size();

        // Create the AssetTransaction
        AssetTransactionDTO assetTransactionDTO = assetTransactionMapper.toDto(assetTransaction);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetTransactionMockMvc.perform(put("/api/asset-transactions")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetTransactionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetTransaction in the database
        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AssetTransaction in Elasticsearch
        verify(mockAssetTransactionSearchRepository, times(0)).save(assetTransaction);
    }

    @Test
    @Transactional
    public void deleteAssetTransaction() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);

        int databaseSizeBeforeDelete = assetTransactionRepository.findAll().size();

        // Delete the assetTransaction
        restAssetTransactionMockMvc.perform(delete("/api/asset-transactions/{id}", assetTransaction.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<AssetTransaction> assetTransactionList = assetTransactionRepository.findAll();
        assertThat(assetTransactionList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AssetTransaction in Elasticsearch
        verify(mockAssetTransactionSearchRepository, times(1)).deleteById(assetTransaction.getId());
    }

    @Test
    @Transactional
    public void searchAssetTransaction() throws Exception {
        // Initialize the database
        assetTransactionRepository.saveAndFlush(assetTransaction);
        when(mockAssetTransactionSearchRepository.search(queryStringQuery("id:" + assetTransaction.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(assetTransaction), PageRequest.of(0, 1), 1));
        // Search the assetTransaction
        restAssetTransactionMockMvc.perform(get("/api/_search/asset-transactions?query=id:" + assetTransaction.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetTransaction.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionReference").value(hasItem(DEFAULT_TRANSACTION_REFERENCE)))
            .andExpect(jsonPath("$.[*].transactionDate").value(hasItem(DEFAULT_TRANSACTION_DATE.toString())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetTransaction.class);
        AssetTransaction assetTransaction1 = new AssetTransaction();
        assetTransaction1.setId(1L);
        AssetTransaction assetTransaction2 = new AssetTransaction();
        assetTransaction2.setId(assetTransaction1.getId());
        assertThat(assetTransaction1).isEqualTo(assetTransaction2);
        assetTransaction2.setId(2L);
        assertThat(assetTransaction1).isNotEqualTo(assetTransaction2);
        assetTransaction1.setId(null);
        assertThat(assetTransaction1).isNotEqualTo(assetTransaction2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetTransactionDTO.class);
        AssetTransactionDTO assetTransactionDTO1 = new AssetTransactionDTO();
        assetTransactionDTO1.setId(1L);
        AssetTransactionDTO assetTransactionDTO2 = new AssetTransactionDTO();
        assertThat(assetTransactionDTO1).isNotEqualTo(assetTransactionDTO2);
        assetTransactionDTO2.setId(assetTransactionDTO1.getId());
        assertThat(assetTransactionDTO1).isEqualTo(assetTransactionDTO2);
        assetTransactionDTO2.setId(2L);
        assertThat(assetTransactionDTO1).isNotEqualTo(assetTransactionDTO2);
        assetTransactionDTO1.setId(null);
        assertThat(assetTransactionDTO1).isNotEqualTo(assetTransactionDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assetTransactionMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assetTransactionMapper.fromId(null)).isNull();
    }
}
