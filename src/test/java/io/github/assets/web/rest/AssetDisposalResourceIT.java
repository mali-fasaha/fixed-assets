package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.AssetDisposal;
import io.github.assets.repository.AssetDisposalRepository;
import io.github.assets.repository.search.AssetDisposalSearchRepository;
import io.github.assets.service.AssetDisposalService;
import io.github.assets.service.dto.AssetDisposalDTO;
import io.github.assets.service.mapper.AssetDisposalMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.AssetDisposalCriteria;
import io.github.assets.service.AssetDisposalQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@Link AssetDisposalResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class AssetDisposalResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_DISPOSAL_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_DISPOSAL_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_ASSET_CATEGORY_ID = 1L;
    private static final Long UPDATED_ASSET_CATEGORY_ID = 2L;

    private static final Long DEFAULT_ASSET_ITEM_ID = 1L;
    private static final Long UPDATED_ASSET_ITEM_ID = 2L;

    private static final BigDecimal DEFAULT_DISPOSAL_PROCEEDS = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISPOSAL_PROCEEDS = new BigDecimal(2);

    private static final BigDecimal DEFAULT_NET_BOOK_VALUE = new BigDecimal(1);
    private static final BigDecimal UPDATED_NET_BOOK_VALUE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_PROFIT_ON_DISPOSAL = new BigDecimal(1);
    private static final BigDecimal UPDATED_PROFIT_ON_DISPOSAL = new BigDecimal(2);

    private static final Long DEFAULT_SCANNED_DOCUMENT_ID = 1L;
    private static final Long UPDATED_SCANNED_DOCUMENT_ID = 2L;

    private static final Long DEFAULT_ASSET_DEALER_ID = 1L;
    private static final Long UPDATED_ASSET_DEALER_ID = 2L;

    private static final byte[] DEFAULT_ASSET_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSET_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSET_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSET_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private AssetDisposalRepository assetDisposalRepository;

    @Autowired
    private AssetDisposalMapper assetDisposalMapper;

    @Autowired
    private AssetDisposalService assetDisposalService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.AssetDisposalSearchRepositoryMockConfiguration
     */
    @Autowired
    private AssetDisposalSearchRepository mockAssetDisposalSearchRepository;

    @Autowired
    private AssetDisposalQueryService assetDisposalQueryService;

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

    private MockMvc restAssetDisposalMockMvc;

    private AssetDisposal assetDisposal;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final AssetDisposalResource assetDisposalResource = new AssetDisposalResource(assetDisposalService, assetDisposalQueryService);
        this.restAssetDisposalMockMvc = MockMvcBuilders.standaloneSetup(assetDisposalResource)
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
    public static AssetDisposal createEntity(EntityManager em) {
        AssetDisposal assetDisposal = new AssetDisposal()
            .description(DEFAULT_DESCRIPTION)
            .disposalDate(DEFAULT_DISPOSAL_DATE)
            .assetCategoryId(DEFAULT_ASSET_CATEGORY_ID)
            .assetItemId(DEFAULT_ASSET_ITEM_ID)
            .disposalProceeds(DEFAULT_DISPOSAL_PROCEEDS)
            .netBookValue(DEFAULT_NET_BOOK_VALUE)
            .profitOnDisposal(DEFAULT_PROFIT_ON_DISPOSAL)
            .scannedDocumentId(DEFAULT_SCANNED_DOCUMENT_ID)
            .assetDealerId(DEFAULT_ASSET_DEALER_ID)
            .assetPicture(DEFAULT_ASSET_PICTURE)
            .assetPictureContentType(DEFAULT_ASSET_PICTURE_CONTENT_TYPE);
        return assetDisposal;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetDisposal createUpdatedEntity(EntityManager em) {
        AssetDisposal assetDisposal = new AssetDisposal()
            .description(UPDATED_DESCRIPTION)
            .disposalDate(UPDATED_DISPOSAL_DATE)
            .assetCategoryId(UPDATED_ASSET_CATEGORY_ID)
            .assetItemId(UPDATED_ASSET_ITEM_ID)
            .disposalProceeds(UPDATED_DISPOSAL_PROCEEDS)
            .netBookValue(UPDATED_NET_BOOK_VALUE)
            .profitOnDisposal(UPDATED_PROFIT_ON_DISPOSAL)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID)
            .assetDealerId(UPDATED_ASSET_DEALER_ID)
            .assetPicture(UPDATED_ASSET_PICTURE)
            .assetPictureContentType(UPDATED_ASSET_PICTURE_CONTENT_TYPE);
        return assetDisposal;
    }

    @BeforeEach
    public void initTest() {
        assetDisposal = createEntity(em);
    }

    @Test
    @Transactional
    public void createAssetDisposal() throws Exception {
        int databaseSizeBeforeCreate = assetDisposalRepository.findAll().size();

        // Create the AssetDisposal
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);
        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isCreated());

        // Validate the AssetDisposal in the database
        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeCreate + 1);
        AssetDisposal testAssetDisposal = assetDisposalList.get(assetDisposalList.size() - 1);
        assertThat(testAssetDisposal.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testAssetDisposal.getDisposalDate()).isEqualTo(DEFAULT_DISPOSAL_DATE);
        assertThat(testAssetDisposal.getAssetCategoryId()).isEqualTo(DEFAULT_ASSET_CATEGORY_ID);
        assertThat(testAssetDisposal.getAssetItemId()).isEqualTo(DEFAULT_ASSET_ITEM_ID);
        assertThat(testAssetDisposal.getDisposalProceeds()).isEqualTo(DEFAULT_DISPOSAL_PROCEEDS);
        assertThat(testAssetDisposal.getNetBookValue()).isEqualTo(DEFAULT_NET_BOOK_VALUE);
        assertThat(testAssetDisposal.getProfitOnDisposal()).isEqualTo(DEFAULT_PROFIT_ON_DISPOSAL);
        assertThat(testAssetDisposal.getScannedDocumentId()).isEqualTo(DEFAULT_SCANNED_DOCUMENT_ID);
        assertThat(testAssetDisposal.getAssetDealerId()).isEqualTo(DEFAULT_ASSET_DEALER_ID);
        assertThat(testAssetDisposal.getAssetPicture()).isEqualTo(DEFAULT_ASSET_PICTURE);
        assertThat(testAssetDisposal.getAssetPictureContentType()).isEqualTo(DEFAULT_ASSET_PICTURE_CONTENT_TYPE);

        // Validate the AssetDisposal in Elasticsearch
        verify(mockAssetDisposalSearchRepository, times(1)).save(testAssetDisposal);
    }

    @Test
    @Transactional
    public void createAssetDisposalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = assetDisposalRepository.findAll().size();

        // Create the AssetDisposal with an existing ID
        assetDisposal.setId(1L);
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetDisposal in the database
        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeCreate);

        // Validate the AssetDisposal in Elasticsearch
        verify(mockAssetDisposalSearchRepository, times(0)).save(assetDisposal);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDisposalRepository.findAll().size();
        // set the field null
        assetDisposal.setDescription(null);

        // Create the AssetDisposal, which fails.
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssetCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDisposalRepository.findAll().size();
        // set the field null
        assetDisposal.setAssetCategoryId(null);

        // Create the AssetDisposal, which fails.
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssetItemIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDisposalRepository.findAll().size();
        // set the field null
        assetDisposal.setAssetItemId(null);

        // Create the AssetDisposal, which fails.
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDisposalProceedsIsRequired() throws Exception {
        int databaseSizeBeforeTest = assetDisposalRepository.findAll().size();
        // set the field null
        assetDisposal.setDisposalProceeds(null);

        // Create the AssetDisposal, which fails.
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        restAssetDisposalMockMvc.perform(post("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAssetDisposals() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetDisposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].disposalDate").value(hasItem(DEFAULT_DISPOSAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetItemId").value(hasItem(DEFAULT_ASSET_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].disposalProceeds").value(hasItem(DEFAULT_DISPOSAL_PROCEEDS.intValue())))
            .andExpect(jsonPath("$.[*].netBookValue").value(hasItem(DEFAULT_NET_BOOK_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].profitOnDisposal").value(hasItem(DEFAULT_PROFIT_ON_DISPOSAL.intValue())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetDealerId").value(hasItem(DEFAULT_ASSET_DEALER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));
    }
    
    @Test
    @Transactional
    public void getAssetDisposal() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get the assetDisposal
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals/{id}", assetDisposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(assetDisposal.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.disposalDate").value(DEFAULT_DISPOSAL_DATE.toString()))
            .andExpect(jsonPath("$.assetCategoryId").value(DEFAULT_ASSET_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.assetItemId").value(DEFAULT_ASSET_ITEM_ID.intValue()))
            .andExpect(jsonPath("$.disposalProceeds").value(DEFAULT_DISPOSAL_PROCEEDS.intValue()))
            .andExpect(jsonPath("$.netBookValue").value(DEFAULT_NET_BOOK_VALUE.intValue()))
            .andExpect(jsonPath("$.profitOnDisposal").value(DEFAULT_PROFIT_ON_DISPOSAL.intValue()))
            .andExpect(jsonPath("$.scannedDocumentId").value(DEFAULT_SCANNED_DOCUMENT_ID.intValue()))
            .andExpect(jsonPath("$.assetDealerId").value(DEFAULT_ASSET_DEALER_ID.intValue()))
            .andExpect(jsonPath("$.assetPictureContentType").value(DEFAULT_ASSET_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.assetPicture").value(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE)));
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where description equals to DEFAULT_DESCRIPTION
        defaultAssetDisposalShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the assetDisposalList where description equals to UPDATED_DESCRIPTION
        defaultAssetDisposalShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultAssetDisposalShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the assetDisposalList where description equals to UPDATED_DESCRIPTION
        defaultAssetDisposalShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where description is not null
        defaultAssetDisposalShouldBeFound("description.specified=true");

        // Get all the assetDisposalList where description is null
        defaultAssetDisposalShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalDateIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalDate equals to DEFAULT_DISPOSAL_DATE
        defaultAssetDisposalShouldBeFound("disposalDate.equals=" + DEFAULT_DISPOSAL_DATE);

        // Get all the assetDisposalList where disposalDate equals to UPDATED_DISPOSAL_DATE
        defaultAssetDisposalShouldNotBeFound("disposalDate.equals=" + UPDATED_DISPOSAL_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalDateIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalDate in DEFAULT_DISPOSAL_DATE or UPDATED_DISPOSAL_DATE
        defaultAssetDisposalShouldBeFound("disposalDate.in=" + DEFAULT_DISPOSAL_DATE + "," + UPDATED_DISPOSAL_DATE);

        // Get all the assetDisposalList where disposalDate equals to UPDATED_DISPOSAL_DATE
        defaultAssetDisposalShouldNotBeFound("disposalDate.in=" + UPDATED_DISPOSAL_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalDate is not null
        defaultAssetDisposalShouldBeFound("disposalDate.specified=true");

        // Get all the assetDisposalList where disposalDate is null
        defaultAssetDisposalShouldNotBeFound("disposalDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalDate greater than or equals to DEFAULT_DISPOSAL_DATE
        defaultAssetDisposalShouldBeFound("disposalDate.greaterOrEqualThan=" + DEFAULT_DISPOSAL_DATE);

        // Get all the assetDisposalList where disposalDate greater than or equals to UPDATED_DISPOSAL_DATE
        defaultAssetDisposalShouldNotBeFound("disposalDate.greaterOrEqualThan=" + UPDATED_DISPOSAL_DATE);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalDateIsLessThanSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalDate less than or equals to DEFAULT_DISPOSAL_DATE
        defaultAssetDisposalShouldNotBeFound("disposalDate.lessThan=" + DEFAULT_DISPOSAL_DATE);

        // Get all the assetDisposalList where disposalDate less than or equals to UPDATED_DISPOSAL_DATE
        defaultAssetDisposalShouldBeFound("disposalDate.lessThan=" + UPDATED_DISPOSAL_DATE);
    }


    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetCategoryId equals to DEFAULT_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldBeFound("assetCategoryId.equals=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the assetDisposalList where assetCategoryId equals to UPDATED_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldNotBeFound("assetCategoryId.equals=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetCategoryId in DEFAULT_ASSET_CATEGORY_ID or UPDATED_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldBeFound("assetCategoryId.in=" + DEFAULT_ASSET_CATEGORY_ID + "," + UPDATED_ASSET_CATEGORY_ID);

        // Get all the assetDisposalList where assetCategoryId equals to UPDATED_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldNotBeFound("assetCategoryId.in=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetCategoryId is not null
        defaultAssetDisposalShouldBeFound("assetCategoryId.specified=true");

        // Get all the assetDisposalList where assetCategoryId is null
        defaultAssetDisposalShouldNotBeFound("assetCategoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetCategoryId greater than or equals to DEFAULT_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldBeFound("assetCategoryId.greaterOrEqualThan=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the assetDisposalList where assetCategoryId greater than or equals to UPDATED_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldNotBeFound("assetCategoryId.greaterOrEqualThan=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetCategoryId less than or equals to DEFAULT_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldNotBeFound("assetCategoryId.lessThan=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the assetDisposalList where assetCategoryId less than or equals to UPDATED_ASSET_CATEGORY_ID
        defaultAssetDisposalShouldBeFound("assetCategoryId.lessThan=" + UPDATED_ASSET_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetItemIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetItemId equals to DEFAULT_ASSET_ITEM_ID
        defaultAssetDisposalShouldBeFound("assetItemId.equals=" + DEFAULT_ASSET_ITEM_ID);

        // Get all the assetDisposalList where assetItemId equals to UPDATED_ASSET_ITEM_ID
        defaultAssetDisposalShouldNotBeFound("assetItemId.equals=" + UPDATED_ASSET_ITEM_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetItemIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetItemId in DEFAULT_ASSET_ITEM_ID or UPDATED_ASSET_ITEM_ID
        defaultAssetDisposalShouldBeFound("assetItemId.in=" + DEFAULT_ASSET_ITEM_ID + "," + UPDATED_ASSET_ITEM_ID);

        // Get all the assetDisposalList where assetItemId equals to UPDATED_ASSET_ITEM_ID
        defaultAssetDisposalShouldNotBeFound("assetItemId.in=" + UPDATED_ASSET_ITEM_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetItemIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetItemId is not null
        defaultAssetDisposalShouldBeFound("assetItemId.specified=true");

        // Get all the assetDisposalList where assetItemId is null
        defaultAssetDisposalShouldNotBeFound("assetItemId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetItemIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetItemId greater than or equals to DEFAULT_ASSET_ITEM_ID
        defaultAssetDisposalShouldBeFound("assetItemId.greaterOrEqualThan=" + DEFAULT_ASSET_ITEM_ID);

        // Get all the assetDisposalList where assetItemId greater than or equals to UPDATED_ASSET_ITEM_ID
        defaultAssetDisposalShouldNotBeFound("assetItemId.greaterOrEqualThan=" + UPDATED_ASSET_ITEM_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetItemIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetItemId less than or equals to DEFAULT_ASSET_ITEM_ID
        defaultAssetDisposalShouldNotBeFound("assetItemId.lessThan=" + DEFAULT_ASSET_ITEM_ID);

        // Get all the assetDisposalList where assetItemId less than or equals to UPDATED_ASSET_ITEM_ID
        defaultAssetDisposalShouldBeFound("assetItemId.lessThan=" + UPDATED_ASSET_ITEM_ID);
    }


    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalProceedsIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalProceeds equals to DEFAULT_DISPOSAL_PROCEEDS
        defaultAssetDisposalShouldBeFound("disposalProceeds.equals=" + DEFAULT_DISPOSAL_PROCEEDS);

        // Get all the assetDisposalList where disposalProceeds equals to UPDATED_DISPOSAL_PROCEEDS
        defaultAssetDisposalShouldNotBeFound("disposalProceeds.equals=" + UPDATED_DISPOSAL_PROCEEDS);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalProceedsIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalProceeds in DEFAULT_DISPOSAL_PROCEEDS or UPDATED_DISPOSAL_PROCEEDS
        defaultAssetDisposalShouldBeFound("disposalProceeds.in=" + DEFAULT_DISPOSAL_PROCEEDS + "," + UPDATED_DISPOSAL_PROCEEDS);

        // Get all the assetDisposalList where disposalProceeds equals to UPDATED_DISPOSAL_PROCEEDS
        defaultAssetDisposalShouldNotBeFound("disposalProceeds.in=" + UPDATED_DISPOSAL_PROCEEDS);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByDisposalProceedsIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where disposalProceeds is not null
        defaultAssetDisposalShouldBeFound("disposalProceeds.specified=true");

        // Get all the assetDisposalList where disposalProceeds is null
        defaultAssetDisposalShouldNotBeFound("disposalProceeds.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByNetBookValueIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where netBookValue equals to DEFAULT_NET_BOOK_VALUE
        defaultAssetDisposalShouldBeFound("netBookValue.equals=" + DEFAULT_NET_BOOK_VALUE);

        // Get all the assetDisposalList where netBookValue equals to UPDATED_NET_BOOK_VALUE
        defaultAssetDisposalShouldNotBeFound("netBookValue.equals=" + UPDATED_NET_BOOK_VALUE);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByNetBookValueIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where netBookValue in DEFAULT_NET_BOOK_VALUE or UPDATED_NET_BOOK_VALUE
        defaultAssetDisposalShouldBeFound("netBookValue.in=" + DEFAULT_NET_BOOK_VALUE + "," + UPDATED_NET_BOOK_VALUE);

        // Get all the assetDisposalList where netBookValue equals to UPDATED_NET_BOOK_VALUE
        defaultAssetDisposalShouldNotBeFound("netBookValue.in=" + UPDATED_NET_BOOK_VALUE);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByNetBookValueIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where netBookValue is not null
        defaultAssetDisposalShouldBeFound("netBookValue.specified=true");

        // Get all the assetDisposalList where netBookValue is null
        defaultAssetDisposalShouldNotBeFound("netBookValue.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByProfitOnDisposalIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where profitOnDisposal equals to DEFAULT_PROFIT_ON_DISPOSAL
        defaultAssetDisposalShouldBeFound("profitOnDisposal.equals=" + DEFAULT_PROFIT_ON_DISPOSAL);

        // Get all the assetDisposalList where profitOnDisposal equals to UPDATED_PROFIT_ON_DISPOSAL
        defaultAssetDisposalShouldNotBeFound("profitOnDisposal.equals=" + UPDATED_PROFIT_ON_DISPOSAL);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByProfitOnDisposalIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where profitOnDisposal in DEFAULT_PROFIT_ON_DISPOSAL or UPDATED_PROFIT_ON_DISPOSAL
        defaultAssetDisposalShouldBeFound("profitOnDisposal.in=" + DEFAULT_PROFIT_ON_DISPOSAL + "," + UPDATED_PROFIT_ON_DISPOSAL);

        // Get all the assetDisposalList where profitOnDisposal equals to UPDATED_PROFIT_ON_DISPOSAL
        defaultAssetDisposalShouldNotBeFound("profitOnDisposal.in=" + UPDATED_PROFIT_ON_DISPOSAL);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByProfitOnDisposalIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where profitOnDisposal is not null
        defaultAssetDisposalShouldBeFound("profitOnDisposal.specified=true");

        // Get all the assetDisposalList where profitOnDisposal is null
        defaultAssetDisposalShouldNotBeFound("profitOnDisposal.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByScannedDocumentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where scannedDocumentId equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldBeFound("scannedDocumentId.equals=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetDisposalList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldNotBeFound("scannedDocumentId.equals=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByScannedDocumentIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where scannedDocumentId in DEFAULT_SCANNED_DOCUMENT_ID or UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldBeFound("scannedDocumentId.in=" + DEFAULT_SCANNED_DOCUMENT_ID + "," + UPDATED_SCANNED_DOCUMENT_ID);

        // Get all the assetDisposalList where scannedDocumentId equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldNotBeFound("scannedDocumentId.in=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByScannedDocumentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where scannedDocumentId is not null
        defaultAssetDisposalShouldBeFound("scannedDocumentId.specified=true");

        // Get all the assetDisposalList where scannedDocumentId is null
        defaultAssetDisposalShouldNotBeFound("scannedDocumentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByScannedDocumentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where scannedDocumentId greater than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldBeFound("scannedDocumentId.greaterOrEqualThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetDisposalList where scannedDocumentId greater than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldNotBeFound("scannedDocumentId.greaterOrEqualThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByScannedDocumentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where scannedDocumentId less than or equals to DEFAULT_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldNotBeFound("scannedDocumentId.lessThan=" + DEFAULT_SCANNED_DOCUMENT_ID);

        // Get all the assetDisposalList where scannedDocumentId less than or equals to UPDATED_SCANNED_DOCUMENT_ID
        defaultAssetDisposalShouldBeFound("scannedDocumentId.lessThan=" + UPDATED_SCANNED_DOCUMENT_ID);
    }


    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetDealerIdIsEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetDealerId equals to DEFAULT_ASSET_DEALER_ID
        defaultAssetDisposalShouldBeFound("assetDealerId.equals=" + DEFAULT_ASSET_DEALER_ID);

        // Get all the assetDisposalList where assetDealerId equals to UPDATED_ASSET_DEALER_ID
        defaultAssetDisposalShouldNotBeFound("assetDealerId.equals=" + UPDATED_ASSET_DEALER_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetDealerIdIsInShouldWork() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetDealerId in DEFAULT_ASSET_DEALER_ID or UPDATED_ASSET_DEALER_ID
        defaultAssetDisposalShouldBeFound("assetDealerId.in=" + DEFAULT_ASSET_DEALER_ID + "," + UPDATED_ASSET_DEALER_ID);

        // Get all the assetDisposalList where assetDealerId equals to UPDATED_ASSET_DEALER_ID
        defaultAssetDisposalShouldNotBeFound("assetDealerId.in=" + UPDATED_ASSET_DEALER_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetDealerIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetDealerId is not null
        defaultAssetDisposalShouldBeFound("assetDealerId.specified=true");

        // Get all the assetDisposalList where assetDealerId is null
        defaultAssetDisposalShouldNotBeFound("assetDealerId.specified=false");
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetDealerIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetDealerId greater than or equals to DEFAULT_ASSET_DEALER_ID
        defaultAssetDisposalShouldBeFound("assetDealerId.greaterOrEqualThan=" + DEFAULT_ASSET_DEALER_ID);

        // Get all the assetDisposalList where assetDealerId greater than or equals to UPDATED_ASSET_DEALER_ID
        defaultAssetDisposalShouldNotBeFound("assetDealerId.greaterOrEqualThan=" + UPDATED_ASSET_DEALER_ID);
    }

    @Test
    @Transactional
    public void getAllAssetDisposalsByAssetDealerIdIsLessThanSomething() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        // Get all the assetDisposalList where assetDealerId less than or equals to DEFAULT_ASSET_DEALER_ID
        defaultAssetDisposalShouldNotBeFound("assetDealerId.lessThan=" + DEFAULT_ASSET_DEALER_ID);

        // Get all the assetDisposalList where assetDealerId less than or equals to UPDATED_ASSET_DEALER_ID
        defaultAssetDisposalShouldBeFound("assetDealerId.lessThan=" + UPDATED_ASSET_DEALER_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAssetDisposalShouldBeFound(String filter) throws Exception {
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetDisposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].disposalDate").value(hasItem(DEFAULT_DISPOSAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetItemId").value(hasItem(DEFAULT_ASSET_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].disposalProceeds").value(hasItem(DEFAULT_DISPOSAL_PROCEEDS.intValue())))
            .andExpect(jsonPath("$.[*].netBookValue").value(hasItem(DEFAULT_NET_BOOK_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].profitOnDisposal").value(hasItem(DEFAULT_PROFIT_ON_DISPOSAL.intValue())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetDealerId").value(hasItem(DEFAULT_ASSET_DEALER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));

        // Check, that the count call also returns 1
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAssetDisposalShouldNotBeFound(String filter) throws Exception {
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAssetDisposal() throws Exception {
        // Get the assetDisposal
        restAssetDisposalMockMvc.perform(get("/api/asset-disposals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAssetDisposal() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        int databaseSizeBeforeUpdate = assetDisposalRepository.findAll().size();

        // Update the assetDisposal
        AssetDisposal updatedAssetDisposal = assetDisposalRepository.findById(assetDisposal.getId()).get();
        // Disconnect from session so that the updates on updatedAssetDisposal are not directly saved in db
        em.detach(updatedAssetDisposal);
        updatedAssetDisposal
            .description(UPDATED_DESCRIPTION)
            .disposalDate(UPDATED_DISPOSAL_DATE)
            .assetCategoryId(UPDATED_ASSET_CATEGORY_ID)
            .assetItemId(UPDATED_ASSET_ITEM_ID)
            .disposalProceeds(UPDATED_DISPOSAL_PROCEEDS)
            .netBookValue(UPDATED_NET_BOOK_VALUE)
            .profitOnDisposal(UPDATED_PROFIT_ON_DISPOSAL)
            .scannedDocumentId(UPDATED_SCANNED_DOCUMENT_ID)
            .assetDealerId(UPDATED_ASSET_DEALER_ID)
            .assetPicture(UPDATED_ASSET_PICTURE)
            .assetPictureContentType(UPDATED_ASSET_PICTURE_CONTENT_TYPE);
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(updatedAssetDisposal);

        restAssetDisposalMockMvc.perform(put("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isOk());

        // Validate the AssetDisposal in the database
        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeUpdate);
        AssetDisposal testAssetDisposal = assetDisposalList.get(assetDisposalList.size() - 1);
        assertThat(testAssetDisposal.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testAssetDisposal.getDisposalDate()).isEqualTo(UPDATED_DISPOSAL_DATE);
        assertThat(testAssetDisposal.getAssetCategoryId()).isEqualTo(UPDATED_ASSET_CATEGORY_ID);
        assertThat(testAssetDisposal.getAssetItemId()).isEqualTo(UPDATED_ASSET_ITEM_ID);
        assertThat(testAssetDisposal.getDisposalProceeds()).isEqualTo(UPDATED_DISPOSAL_PROCEEDS);
        assertThat(testAssetDisposal.getNetBookValue()).isEqualTo(UPDATED_NET_BOOK_VALUE);
        assertThat(testAssetDisposal.getProfitOnDisposal()).isEqualTo(UPDATED_PROFIT_ON_DISPOSAL);
        assertThat(testAssetDisposal.getScannedDocumentId()).isEqualTo(UPDATED_SCANNED_DOCUMENT_ID);
        assertThat(testAssetDisposal.getAssetDealerId()).isEqualTo(UPDATED_ASSET_DEALER_ID);
        assertThat(testAssetDisposal.getAssetPicture()).isEqualTo(UPDATED_ASSET_PICTURE);
        assertThat(testAssetDisposal.getAssetPictureContentType()).isEqualTo(UPDATED_ASSET_PICTURE_CONTENT_TYPE);

        // Validate the AssetDisposal in Elasticsearch
        verify(mockAssetDisposalSearchRepository, times(1)).save(testAssetDisposal);
    }

    @Test
    @Transactional
    public void updateNonExistingAssetDisposal() throws Exception {
        int databaseSizeBeforeUpdate = assetDisposalRepository.findAll().size();

        // Create the AssetDisposal
        AssetDisposalDTO assetDisposalDTO = assetDisposalMapper.toDto(assetDisposal);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetDisposalMockMvc.perform(put("/api/asset-disposals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(assetDisposalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the AssetDisposal in the database
        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the AssetDisposal in Elasticsearch
        verify(mockAssetDisposalSearchRepository, times(0)).save(assetDisposal);
    }

    @Test
    @Transactional
    public void deleteAssetDisposal() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);

        int databaseSizeBeforeDelete = assetDisposalRepository.findAll().size();

        // Delete the assetDisposal
        restAssetDisposalMockMvc.perform(delete("/api/asset-disposals/{id}", assetDisposal.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<AssetDisposal> assetDisposalList = assetDisposalRepository.findAll();
        assertThat(assetDisposalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the AssetDisposal in Elasticsearch
        verify(mockAssetDisposalSearchRepository, times(1)).deleteById(assetDisposal.getId());
    }

    @Test
    @Transactional
    public void searchAssetDisposal() throws Exception {
        // Initialize the database
        assetDisposalRepository.saveAndFlush(assetDisposal);
        when(mockAssetDisposalSearchRepository.search(queryStringQuery("id:" + assetDisposal.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(assetDisposal), PageRequest.of(0, 1), 1));
        // Search the assetDisposal
        restAssetDisposalMockMvc.perform(get("/api/_search/asset-disposals?query=id:" + assetDisposal.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetDisposal.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].disposalDate").value(hasItem(DEFAULT_DISPOSAL_DATE.toString())))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetItemId").value(hasItem(DEFAULT_ASSET_ITEM_ID.intValue())))
            .andExpect(jsonPath("$.[*].disposalProceeds").value(hasItem(DEFAULT_DISPOSAL_PROCEEDS.intValue())))
            .andExpect(jsonPath("$.[*].netBookValue").value(hasItem(DEFAULT_NET_BOOK_VALUE.intValue())))
            .andExpect(jsonPath("$.[*].profitOnDisposal").value(hasItem(DEFAULT_PROFIT_ON_DISPOSAL.intValue())))
            .andExpect(jsonPath("$.[*].scannedDocumentId").value(hasItem(DEFAULT_SCANNED_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetDealerId").value(hasItem(DEFAULT_ASSET_DEALER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetDisposal.class);
        AssetDisposal assetDisposal1 = new AssetDisposal();
        assetDisposal1.setId(1L);
        AssetDisposal assetDisposal2 = new AssetDisposal();
        assetDisposal2.setId(assetDisposal1.getId());
        assertThat(assetDisposal1).isEqualTo(assetDisposal2);
        assetDisposal2.setId(2L);
        assertThat(assetDisposal1).isNotEqualTo(assetDisposal2);
        assetDisposal1.setId(null);
        assertThat(assetDisposal1).isNotEqualTo(assetDisposal2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetDisposalDTO.class);
        AssetDisposalDTO assetDisposalDTO1 = new AssetDisposalDTO();
        assetDisposalDTO1.setId(1L);
        AssetDisposalDTO assetDisposalDTO2 = new AssetDisposalDTO();
        assertThat(assetDisposalDTO1).isNotEqualTo(assetDisposalDTO2);
        assetDisposalDTO2.setId(assetDisposalDTO1.getId());
        assertThat(assetDisposalDTO1).isEqualTo(assetDisposalDTO2);
        assetDisposalDTO2.setId(2L);
        assertThat(assetDisposalDTO1).isNotEqualTo(assetDisposalDTO2);
        assetDisposalDTO1.setId(null);
        assertThat(assetDisposalDTO1).isNotEqualTo(assetDisposalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(assetDisposalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(assetDisposalMapper.fromId(null)).isNull();
    }
}
