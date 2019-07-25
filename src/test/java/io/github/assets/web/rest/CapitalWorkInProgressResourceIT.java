package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.CapitalWorkInProgress;
import io.github.assets.repository.CapitalWorkInProgressRepository;
import io.github.assets.repository.search.CapitalWorkInProgressSearchRepository;
import io.github.assets.service.CapitalWorkInProgressService;
import io.github.assets.service.dto.CapitalWorkInProgressDTO;
import io.github.assets.service.mapper.CapitalWorkInProgressMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.CapitalWorkInProgressCriteria;
import io.github.assets.service.CapitalWorkInProgressQueryService;

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
import java.math.BigDecimal;
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
 * Integration tests for the {@Link CapitalWorkInProgressResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class CapitalWorkInProgressResourceIT {

    private static final LocalDate DEFAULT_TRANSACTION_MONTH = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TRANSACTION_MONTH = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_ASSET_SERIAL_TAG = "AAAAAAAAAA";
    private static final String UPDATED_ASSET_SERIAL_TAG = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_TRANSACTION_ID = 1L;
    private static final Long UPDATED_TRANSACTION_ID = 2L;

    private static final String DEFAULT_TRANSACTION_DETAILS = "AAAAAAAAAA";
    private static final String UPDATED_TRANSACTION_DETAILS = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_TRANSACTION_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRANSACTION_AMOUNT = new BigDecimal(2);

    @Autowired
    private CapitalWorkInProgressRepository capitalWorkInProgressRepository;

    @Autowired
    private CapitalWorkInProgressMapper capitalWorkInProgressMapper;

    @Autowired
    private CapitalWorkInProgressService capitalWorkInProgressService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.CapitalWorkInProgressSearchRepositoryMockConfiguration
     */
    @Autowired
    private CapitalWorkInProgressSearchRepository mockCapitalWorkInProgressSearchRepository;

    @Autowired
    private CapitalWorkInProgressQueryService capitalWorkInProgressQueryService;

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

    private MockMvc restCapitalWorkInProgressMockMvc;

    private CapitalWorkInProgress capitalWorkInProgress;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CapitalWorkInProgressResource capitalWorkInProgressResource = new CapitalWorkInProgressResource(capitalWorkInProgressService, capitalWorkInProgressQueryService);
        this.restCapitalWorkInProgressMockMvc = MockMvcBuilders.standaloneSetup(capitalWorkInProgressResource)
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
    public static CapitalWorkInProgress createEntity(EntityManager em) {
        CapitalWorkInProgress capitalWorkInProgress = new CapitalWorkInProgress()
            .transactionMonth(DEFAULT_TRANSACTION_MONTH)
            .assetSerialTag(DEFAULT_ASSET_SERIAL_TAG)
            .serviceOutletCode(DEFAULT_SERVICE_OUTLET_CODE)
            .transactionId(DEFAULT_TRANSACTION_ID)
            .transactionDetails(DEFAULT_TRANSACTION_DETAILS)
            .transactionAmount(DEFAULT_TRANSACTION_AMOUNT);
        return capitalWorkInProgress;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static CapitalWorkInProgress createUpdatedEntity(EntityManager em) {
        CapitalWorkInProgress capitalWorkInProgress = new CapitalWorkInProgress()
            .transactionMonth(UPDATED_TRANSACTION_MONTH)
            .assetSerialTag(UPDATED_ASSET_SERIAL_TAG)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDetails(UPDATED_TRANSACTION_DETAILS)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT);
        return capitalWorkInProgress;
    }

    @BeforeEach
    public void initTest() {
        capitalWorkInProgress = createEntity(em);
    }

    @Test
    @Transactional
    public void createCapitalWorkInProgress() throws Exception {
        int databaseSizeBeforeCreate = capitalWorkInProgressRepository.findAll().size();

        // Create the CapitalWorkInProgress
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);
        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isCreated());

        // Validate the CapitalWorkInProgress in the database
        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeCreate + 1);
        CapitalWorkInProgress testCapitalWorkInProgress = capitalWorkInProgressList.get(capitalWorkInProgressList.size() - 1);
        assertThat(testCapitalWorkInProgress.getTransactionMonth()).isEqualTo(DEFAULT_TRANSACTION_MONTH);
        assertThat(testCapitalWorkInProgress.getAssetSerialTag()).isEqualTo(DEFAULT_ASSET_SERIAL_TAG);
        assertThat(testCapitalWorkInProgress.getServiceOutletCode()).isEqualTo(DEFAULT_SERVICE_OUTLET_CODE);
        assertThat(testCapitalWorkInProgress.getTransactionId()).isEqualTo(DEFAULT_TRANSACTION_ID);
        assertThat(testCapitalWorkInProgress.getTransactionDetails()).isEqualTo(DEFAULT_TRANSACTION_DETAILS);
        assertThat(testCapitalWorkInProgress.getTransactionAmount()).isEqualTo(DEFAULT_TRANSACTION_AMOUNT);

        // Validate the CapitalWorkInProgress in Elasticsearch
        verify(mockCapitalWorkInProgressSearchRepository, times(1)).save(testCapitalWorkInProgress);
    }

    @Test
    @Transactional
    public void createCapitalWorkInProgressWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = capitalWorkInProgressRepository.findAll().size();

        // Create the CapitalWorkInProgress with an existing ID
        capitalWorkInProgress.setId(1L);
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CapitalWorkInProgress in the database
        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeCreate);

        // Validate the CapitalWorkInProgress in Elasticsearch
        verify(mockCapitalWorkInProgressSearchRepository, times(0)).save(capitalWorkInProgress);
    }


    @Test
    @Transactional
    public void checkTransactionMonthIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setTransactionMonth(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssetSerialTagIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setAssetSerialTag(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setServiceOutletCode(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setTransactionId(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionDetailsIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setTransactionDetails(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTransactionAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = capitalWorkInProgressRepository.findAll().size();
        // set the field null
        capitalWorkInProgress.setTransactionAmount(null);

        // Create the CapitalWorkInProgress, which fails.
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(post("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgresses() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitalWorkInProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionMonth").value(hasItem(DEFAULT_TRANSACTION_MONTH.toString())))
            .andExpect(jsonPath("$.[*].assetSerialTag").value(hasItem(DEFAULT_ASSET_SERIAL_TAG.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionDetails").value(hasItem(DEFAULT_TRANSACTION_DETAILS.toString())))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())));
    }
    
    @Test
    @Transactional
    public void getCapitalWorkInProgress() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get the capitalWorkInProgress
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses/{id}", capitalWorkInProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(capitalWorkInProgress.getId().intValue()))
            .andExpect(jsonPath("$.transactionMonth").value(DEFAULT_TRANSACTION_MONTH.toString()))
            .andExpect(jsonPath("$.assetSerialTag").value(DEFAULT_ASSET_SERIAL_TAG.toString()))
            .andExpect(jsonPath("$.serviceOutletCode").value(DEFAULT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.transactionId").value(DEFAULT_TRANSACTION_ID.intValue()))
            .andExpect(jsonPath("$.transactionDetails").value(DEFAULT_TRANSACTION_DETAILS.toString()))
            .andExpect(jsonPath("$.transactionAmount").value(DEFAULT_TRANSACTION_AMOUNT.intValue()));
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionMonthIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionMonth equals to DEFAULT_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldBeFound("transactionMonth.equals=" + DEFAULT_TRANSACTION_MONTH);

        // Get all the capitalWorkInProgressList where transactionMonth equals to UPDATED_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldNotBeFound("transactionMonth.equals=" + UPDATED_TRANSACTION_MONTH);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionMonthIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionMonth in DEFAULT_TRANSACTION_MONTH or UPDATED_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldBeFound("transactionMonth.in=" + DEFAULT_TRANSACTION_MONTH + "," + UPDATED_TRANSACTION_MONTH);

        // Get all the capitalWorkInProgressList where transactionMonth equals to UPDATED_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldNotBeFound("transactionMonth.in=" + UPDATED_TRANSACTION_MONTH);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionMonthIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionMonth is not null
        defaultCapitalWorkInProgressShouldBeFound("transactionMonth.specified=true");

        // Get all the capitalWorkInProgressList where transactionMonth is null
        defaultCapitalWorkInProgressShouldNotBeFound("transactionMonth.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionMonthIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionMonth greater than or equals to DEFAULT_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldBeFound("transactionMonth.greaterOrEqualThan=" + DEFAULT_TRANSACTION_MONTH);

        // Get all the capitalWorkInProgressList where transactionMonth greater than or equals to UPDATED_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldNotBeFound("transactionMonth.greaterOrEqualThan=" + UPDATED_TRANSACTION_MONTH);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionMonthIsLessThanSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionMonth less than or equals to DEFAULT_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldNotBeFound("transactionMonth.lessThan=" + DEFAULT_TRANSACTION_MONTH);

        // Get all the capitalWorkInProgressList where transactionMonth less than or equals to UPDATED_TRANSACTION_MONTH
        defaultCapitalWorkInProgressShouldBeFound("transactionMonth.lessThan=" + UPDATED_TRANSACTION_MONTH);
    }


    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByAssetSerialTagIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where assetSerialTag equals to DEFAULT_ASSET_SERIAL_TAG
        defaultCapitalWorkInProgressShouldBeFound("assetSerialTag.equals=" + DEFAULT_ASSET_SERIAL_TAG);

        // Get all the capitalWorkInProgressList where assetSerialTag equals to UPDATED_ASSET_SERIAL_TAG
        defaultCapitalWorkInProgressShouldNotBeFound("assetSerialTag.equals=" + UPDATED_ASSET_SERIAL_TAG);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByAssetSerialTagIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where assetSerialTag in DEFAULT_ASSET_SERIAL_TAG or UPDATED_ASSET_SERIAL_TAG
        defaultCapitalWorkInProgressShouldBeFound("assetSerialTag.in=" + DEFAULT_ASSET_SERIAL_TAG + "," + UPDATED_ASSET_SERIAL_TAG);

        // Get all the capitalWorkInProgressList where assetSerialTag equals to UPDATED_ASSET_SERIAL_TAG
        defaultCapitalWorkInProgressShouldNotBeFound("assetSerialTag.in=" + UPDATED_ASSET_SERIAL_TAG);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByAssetSerialTagIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where assetSerialTag is not null
        defaultCapitalWorkInProgressShouldBeFound("assetSerialTag.specified=true");

        // Get all the capitalWorkInProgressList where assetSerialTag is null
        defaultCapitalWorkInProgressShouldNotBeFound("assetSerialTag.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where serviceOutletCode equals to DEFAULT_SERVICE_OUTLET_CODE
        defaultCapitalWorkInProgressShouldBeFound("serviceOutletCode.equals=" + DEFAULT_SERVICE_OUTLET_CODE);

        // Get all the capitalWorkInProgressList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultCapitalWorkInProgressShouldNotBeFound("serviceOutletCode.equals=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where serviceOutletCode in DEFAULT_SERVICE_OUTLET_CODE or UPDATED_SERVICE_OUTLET_CODE
        defaultCapitalWorkInProgressShouldBeFound("serviceOutletCode.in=" + DEFAULT_SERVICE_OUTLET_CODE + "," + UPDATED_SERVICE_OUTLET_CODE);

        // Get all the capitalWorkInProgressList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultCapitalWorkInProgressShouldNotBeFound("serviceOutletCode.in=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where serviceOutletCode is not null
        defaultCapitalWorkInProgressShouldBeFound("serviceOutletCode.specified=true");

        // Get all the capitalWorkInProgressList where serviceOutletCode is null
        defaultCapitalWorkInProgressShouldNotBeFound("serviceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionId equals to DEFAULT_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldBeFound("transactionId.equals=" + DEFAULT_TRANSACTION_ID);

        // Get all the capitalWorkInProgressList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldNotBeFound("transactionId.equals=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionId in DEFAULT_TRANSACTION_ID or UPDATED_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldBeFound("transactionId.in=" + DEFAULT_TRANSACTION_ID + "," + UPDATED_TRANSACTION_ID);

        // Get all the capitalWorkInProgressList where transactionId equals to UPDATED_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldNotBeFound("transactionId.in=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionId is not null
        defaultCapitalWorkInProgressShouldBeFound("transactionId.specified=true");

        // Get all the capitalWorkInProgressList where transactionId is null
        defaultCapitalWorkInProgressShouldNotBeFound("transactionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionId greater than or equals to DEFAULT_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldBeFound("transactionId.greaterOrEqualThan=" + DEFAULT_TRANSACTION_ID);

        // Get all the capitalWorkInProgressList where transactionId greater than or equals to UPDATED_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldNotBeFound("transactionId.greaterOrEqualThan=" + UPDATED_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionId less than or equals to DEFAULT_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldNotBeFound("transactionId.lessThan=" + DEFAULT_TRANSACTION_ID);

        // Get all the capitalWorkInProgressList where transactionId less than or equals to UPDATED_TRANSACTION_ID
        defaultCapitalWorkInProgressShouldBeFound("transactionId.lessThan=" + UPDATED_TRANSACTION_ID);
    }


    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionDetailsIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionDetails equals to DEFAULT_TRANSACTION_DETAILS
        defaultCapitalWorkInProgressShouldBeFound("transactionDetails.equals=" + DEFAULT_TRANSACTION_DETAILS);

        // Get all the capitalWorkInProgressList where transactionDetails equals to UPDATED_TRANSACTION_DETAILS
        defaultCapitalWorkInProgressShouldNotBeFound("transactionDetails.equals=" + UPDATED_TRANSACTION_DETAILS);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionDetailsIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionDetails in DEFAULT_TRANSACTION_DETAILS or UPDATED_TRANSACTION_DETAILS
        defaultCapitalWorkInProgressShouldBeFound("transactionDetails.in=" + DEFAULT_TRANSACTION_DETAILS + "," + UPDATED_TRANSACTION_DETAILS);

        // Get all the capitalWorkInProgressList where transactionDetails equals to UPDATED_TRANSACTION_DETAILS
        defaultCapitalWorkInProgressShouldNotBeFound("transactionDetails.in=" + UPDATED_TRANSACTION_DETAILS);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionDetailsIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionDetails is not null
        defaultCapitalWorkInProgressShouldBeFound("transactionDetails.specified=true");

        // Get all the capitalWorkInProgressList where transactionDetails is null
        defaultCapitalWorkInProgressShouldNotBeFound("transactionDetails.specified=false");
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionAmount equals to DEFAULT_TRANSACTION_AMOUNT
        defaultCapitalWorkInProgressShouldBeFound("transactionAmount.equals=" + DEFAULT_TRANSACTION_AMOUNT);

        // Get all the capitalWorkInProgressList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCapitalWorkInProgressShouldNotBeFound("transactionAmount.equals=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionAmountIsInShouldWork() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionAmount in DEFAULT_TRANSACTION_AMOUNT or UPDATED_TRANSACTION_AMOUNT
        defaultCapitalWorkInProgressShouldBeFound("transactionAmount.in=" + DEFAULT_TRANSACTION_AMOUNT + "," + UPDATED_TRANSACTION_AMOUNT);

        // Get all the capitalWorkInProgressList where transactionAmount equals to UPDATED_TRANSACTION_AMOUNT
        defaultCapitalWorkInProgressShouldNotBeFound("transactionAmount.in=" + UPDATED_TRANSACTION_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllCapitalWorkInProgressesByTransactionAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        // Get all the capitalWorkInProgressList where transactionAmount is not null
        defaultCapitalWorkInProgressShouldBeFound("transactionAmount.specified=true");

        // Get all the capitalWorkInProgressList where transactionAmount is null
        defaultCapitalWorkInProgressShouldNotBeFound("transactionAmount.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultCapitalWorkInProgressShouldBeFound(String filter) throws Exception {
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitalWorkInProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionMonth").value(hasItem(DEFAULT_TRANSACTION_MONTH.toString())))
            .andExpect(jsonPath("$.[*].assetSerialTag").value(hasItem(DEFAULT_ASSET_SERIAL_TAG)))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionDetails").value(hasItem(DEFAULT_TRANSACTION_DETAILS)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())));

        // Check, that the count call also returns 1
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultCapitalWorkInProgressShouldNotBeFound(String filter) throws Exception {
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingCapitalWorkInProgress() throws Exception {
        // Get the capitalWorkInProgress
        restCapitalWorkInProgressMockMvc.perform(get("/api/capital-work-in-progresses/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCapitalWorkInProgress() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        int databaseSizeBeforeUpdate = capitalWorkInProgressRepository.findAll().size();

        // Update the capitalWorkInProgress
        CapitalWorkInProgress updatedCapitalWorkInProgress = capitalWorkInProgressRepository.findById(capitalWorkInProgress.getId()).get();
        // Disconnect from session so that the updates on updatedCapitalWorkInProgress are not directly saved in db
        em.detach(updatedCapitalWorkInProgress);
        updatedCapitalWorkInProgress
            .transactionMonth(UPDATED_TRANSACTION_MONTH)
            .assetSerialTag(UPDATED_ASSET_SERIAL_TAG)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .transactionId(UPDATED_TRANSACTION_ID)
            .transactionDetails(UPDATED_TRANSACTION_DETAILS)
            .transactionAmount(UPDATED_TRANSACTION_AMOUNT);
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(updatedCapitalWorkInProgress);

        restCapitalWorkInProgressMockMvc.perform(put("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isOk());

        // Validate the CapitalWorkInProgress in the database
        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeUpdate);
        CapitalWorkInProgress testCapitalWorkInProgress = capitalWorkInProgressList.get(capitalWorkInProgressList.size() - 1);
        assertThat(testCapitalWorkInProgress.getTransactionMonth()).isEqualTo(UPDATED_TRANSACTION_MONTH);
        assertThat(testCapitalWorkInProgress.getAssetSerialTag()).isEqualTo(UPDATED_ASSET_SERIAL_TAG);
        assertThat(testCapitalWorkInProgress.getServiceOutletCode()).isEqualTo(UPDATED_SERVICE_OUTLET_CODE);
        assertThat(testCapitalWorkInProgress.getTransactionId()).isEqualTo(UPDATED_TRANSACTION_ID);
        assertThat(testCapitalWorkInProgress.getTransactionDetails()).isEqualTo(UPDATED_TRANSACTION_DETAILS);
        assertThat(testCapitalWorkInProgress.getTransactionAmount()).isEqualTo(UPDATED_TRANSACTION_AMOUNT);

        // Validate the CapitalWorkInProgress in Elasticsearch
        verify(mockCapitalWorkInProgressSearchRepository, times(1)).save(testCapitalWorkInProgress);
    }

    @Test
    @Transactional
    public void updateNonExistingCapitalWorkInProgress() throws Exception {
        int databaseSizeBeforeUpdate = capitalWorkInProgressRepository.findAll().size();

        // Create the CapitalWorkInProgress
        CapitalWorkInProgressDTO capitalWorkInProgressDTO = capitalWorkInProgressMapper.toDto(capitalWorkInProgress);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCapitalWorkInProgressMockMvc.perform(put("/api/capital-work-in-progresses")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(capitalWorkInProgressDTO)))
            .andExpect(status().isBadRequest());

        // Validate the CapitalWorkInProgress in the database
        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeUpdate);

        // Validate the CapitalWorkInProgress in Elasticsearch
        verify(mockCapitalWorkInProgressSearchRepository, times(0)).save(capitalWorkInProgress);
    }

    @Test
    @Transactional
    public void deleteCapitalWorkInProgress() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);

        int databaseSizeBeforeDelete = capitalWorkInProgressRepository.findAll().size();

        // Delete the capitalWorkInProgress
        restCapitalWorkInProgressMockMvc.perform(delete("/api/capital-work-in-progresses/{id}", capitalWorkInProgress.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<CapitalWorkInProgress> capitalWorkInProgressList = capitalWorkInProgressRepository.findAll();
        assertThat(capitalWorkInProgressList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the CapitalWorkInProgress in Elasticsearch
        verify(mockCapitalWorkInProgressSearchRepository, times(1)).deleteById(capitalWorkInProgress.getId());
    }

    @Test
    @Transactional
    public void searchCapitalWorkInProgress() throws Exception {
        // Initialize the database
        capitalWorkInProgressRepository.saveAndFlush(capitalWorkInProgress);
        when(mockCapitalWorkInProgressSearchRepository.search(queryStringQuery("id:" + capitalWorkInProgress.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(capitalWorkInProgress), PageRequest.of(0, 1), 1));
        // Search the capitalWorkInProgress
        restCapitalWorkInProgressMockMvc.perform(get("/api/_search/capital-work-in-progresses?query=id:" + capitalWorkInProgress.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(capitalWorkInProgress.getId().intValue())))
            .andExpect(jsonPath("$.[*].transactionMonth").value(hasItem(DEFAULT_TRANSACTION_MONTH.toString())))
            .andExpect(jsonPath("$.[*].assetSerialTag").value(hasItem(DEFAULT_ASSET_SERIAL_TAG)))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].transactionId").value(hasItem(DEFAULT_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionDetails").value(hasItem(DEFAULT_TRANSACTION_DETAILS)))
            .andExpect(jsonPath("$.[*].transactionAmount").value(hasItem(DEFAULT_TRANSACTION_AMOUNT.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapitalWorkInProgress.class);
        CapitalWorkInProgress capitalWorkInProgress1 = new CapitalWorkInProgress();
        capitalWorkInProgress1.setId(1L);
        CapitalWorkInProgress capitalWorkInProgress2 = new CapitalWorkInProgress();
        capitalWorkInProgress2.setId(capitalWorkInProgress1.getId());
        assertThat(capitalWorkInProgress1).isEqualTo(capitalWorkInProgress2);
        capitalWorkInProgress2.setId(2L);
        assertThat(capitalWorkInProgress1).isNotEqualTo(capitalWorkInProgress2);
        capitalWorkInProgress1.setId(null);
        assertThat(capitalWorkInProgress1).isNotEqualTo(capitalWorkInProgress2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(CapitalWorkInProgressDTO.class);
        CapitalWorkInProgressDTO capitalWorkInProgressDTO1 = new CapitalWorkInProgressDTO();
        capitalWorkInProgressDTO1.setId(1L);
        CapitalWorkInProgressDTO capitalWorkInProgressDTO2 = new CapitalWorkInProgressDTO();
        assertThat(capitalWorkInProgressDTO1).isNotEqualTo(capitalWorkInProgressDTO2);
        capitalWorkInProgressDTO2.setId(capitalWorkInProgressDTO1.getId());
        assertThat(capitalWorkInProgressDTO1).isEqualTo(capitalWorkInProgressDTO2);
        capitalWorkInProgressDTO2.setId(2L);
        assertThat(capitalWorkInProgressDTO1).isNotEqualTo(capitalWorkInProgressDTO2);
        capitalWorkInProgressDTO1.setId(null);
        assertThat(capitalWorkInProgressDTO1).isNotEqualTo(capitalWorkInProgressDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(capitalWorkInProgressMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(capitalWorkInProgressMapper.fromId(null)).isNull();
    }
}
