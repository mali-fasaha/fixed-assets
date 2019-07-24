package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.FixedAssetItem;
import io.github.assets.repository.FixedAssetItemRepository;
import io.github.assets.repository.search.FixedAssetItemSearchRepository;
import io.github.assets.service.FixedAssetItemService;
import io.github.assets.service.dto.FixedAssetItemDTO;
import io.github.assets.service.mapper.FixedAssetItemMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.FixedAssetItemCriteria;
import io.github.assets.service.FixedAssetItemQueryService;

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
 * Integration tests for the {@Link FixedAssetItemResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class FixedAssetItemResourceIT {

    private static final String DEFAULT_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final Long DEFAULT_ASSET_CATEGORY_ID = 1L;
    private static final Long UPDATED_ASSET_CATEGORY_ID = 2L;

    private static final String DEFAULT_FIXED_ASSET_SERIAL_CODE = "AAAAAAAAAA";
    private static final String UPDATED_FIXED_ASSET_SERIAL_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_FIXED_ASSET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FIXED_ASSET_DESCRIPTION = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PURCHASE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PURCHASE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_PURCHASE_COST = new BigDecimal(1);
    private static final BigDecimal UPDATED_PURCHASE_COST = new BigDecimal(2);

    private static final Long DEFAULT_PURCHASE_TRANSACTION_ID = 1L;
    private static final Long UPDATED_PURCHASE_TRANSACTION_ID = 2L;

    private static final Long DEFAULT_OWNERSHIP_DOCUMENT_ID = 1L;
    private static final Long UPDATED_OWNERSHIP_DOCUMENT_ID = 2L;

    private static final byte[] DEFAULT_ASSET_PICTURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ASSET_PICTURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ASSET_PICTURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ASSET_PICTURE_CONTENT_TYPE = "image/png";

    @Autowired
    private FixedAssetItemRepository fixedAssetItemRepository;

    @Autowired
    private FixedAssetItemMapper fixedAssetItemMapper;

    @Autowired
    private FixedAssetItemService fixedAssetItemService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.FixedAssetItemSearchRepositoryMockConfiguration
     */
    @Autowired
    private FixedAssetItemSearchRepository mockFixedAssetItemSearchRepository;

    @Autowired
    private FixedAssetItemQueryService fixedAssetItemQueryService;

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

    private MockMvc restFixedAssetItemMockMvc;

    private FixedAssetItem fixedAssetItem;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FixedAssetItemResource fixedAssetItemResource = new FixedAssetItemResource(fixedAssetItemService, fixedAssetItemQueryService);
        this.restFixedAssetItemMockMvc = MockMvcBuilders.standaloneSetup(fixedAssetItemResource)
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
    public static FixedAssetItem createEntity(EntityManager em) {
        FixedAssetItem fixedAssetItem = new FixedAssetItem()
            .serviceOutletCode(DEFAULT_SERVICE_OUTLET_CODE)
            .assetCategoryId(DEFAULT_ASSET_CATEGORY_ID)
            .fixedAssetSerialCode(DEFAULT_FIXED_ASSET_SERIAL_CODE)
            .fixedAssetDescription(DEFAULT_FIXED_ASSET_DESCRIPTION)
            .purchaseDate(DEFAULT_PURCHASE_DATE)
            .purchaseCost(DEFAULT_PURCHASE_COST)
            .purchaseTransactionId(DEFAULT_PURCHASE_TRANSACTION_ID)
            .ownershipDocumentId(DEFAULT_OWNERSHIP_DOCUMENT_ID)
            .assetPicture(DEFAULT_ASSET_PICTURE)
            .assetPictureContentType(DEFAULT_ASSET_PICTURE_CONTENT_TYPE);
        return fixedAssetItem;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FixedAssetItem createUpdatedEntity(EntityManager em) {
        FixedAssetItem fixedAssetItem = new FixedAssetItem()
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .assetCategoryId(UPDATED_ASSET_CATEGORY_ID)
            .fixedAssetSerialCode(UPDATED_FIXED_ASSET_SERIAL_CODE)
            .fixedAssetDescription(UPDATED_FIXED_ASSET_DESCRIPTION)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .purchaseCost(UPDATED_PURCHASE_COST)
            .purchaseTransactionId(UPDATED_PURCHASE_TRANSACTION_ID)
            .ownershipDocumentId(UPDATED_OWNERSHIP_DOCUMENT_ID)
            .assetPicture(UPDATED_ASSET_PICTURE)
            .assetPictureContentType(UPDATED_ASSET_PICTURE_CONTENT_TYPE);
        return fixedAssetItem;
    }

    @BeforeEach
    public void initTest() {
        fixedAssetItem = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixedAssetItem() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetItemRepository.findAll().size();

        // Create the FixedAssetItem
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);
        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isCreated());

        // Validate the FixedAssetItem in the database
        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeCreate + 1);
        FixedAssetItem testFixedAssetItem = fixedAssetItemList.get(fixedAssetItemList.size() - 1);
        assertThat(testFixedAssetItem.getServiceOutletCode()).isEqualTo(DEFAULT_SERVICE_OUTLET_CODE);
        assertThat(testFixedAssetItem.getAssetCategoryId()).isEqualTo(DEFAULT_ASSET_CATEGORY_ID);
        assertThat(testFixedAssetItem.getFixedAssetSerialCode()).isEqualTo(DEFAULT_FIXED_ASSET_SERIAL_CODE);
        assertThat(testFixedAssetItem.getFixedAssetDescription()).isEqualTo(DEFAULT_FIXED_ASSET_DESCRIPTION);
        assertThat(testFixedAssetItem.getPurchaseDate()).isEqualTo(DEFAULT_PURCHASE_DATE);
        assertThat(testFixedAssetItem.getPurchaseCost()).isEqualTo(DEFAULT_PURCHASE_COST);
        assertThat(testFixedAssetItem.getPurchaseTransactionId()).isEqualTo(DEFAULT_PURCHASE_TRANSACTION_ID);
        assertThat(testFixedAssetItem.getOwnershipDocumentId()).isEqualTo(DEFAULT_OWNERSHIP_DOCUMENT_ID);
        assertThat(testFixedAssetItem.getAssetPicture()).isEqualTo(DEFAULT_ASSET_PICTURE);
        assertThat(testFixedAssetItem.getAssetPictureContentType()).isEqualTo(DEFAULT_ASSET_PICTURE_CONTENT_TYPE);

        // Validate the FixedAssetItem in Elasticsearch
        verify(mockFixedAssetItemSearchRepository, times(1)).save(testFixedAssetItem);
    }

    @Test
    @Transactional
    public void createFixedAssetItemWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetItemRepository.findAll().size();

        // Create the FixedAssetItem with an existing ID
        fixedAssetItem.setId(1L);
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetItem in the database
        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeCreate);

        // Validate the FixedAssetItem in Elasticsearch
        verify(mockFixedAssetItemSearchRepository, times(0)).save(fixedAssetItem);
    }


    @Test
    @Transactional
    public void checkServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setServiceOutletCode(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkAssetCategoryIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setAssetCategoryId(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFixedAssetSerialCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setFixedAssetSerialCode(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFixedAssetDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setFixedAssetDescription(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseDateIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setPurchaseDate(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseCostIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setPurchaseCost(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPurchaseTransactionIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetItemRepository.findAll().size();
        // set the field null
        fixedAssetItem.setPurchaseTransactionId(null);

        // Create the FixedAssetItem, which fails.
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        restFixedAssetItemMockMvc.perform(post("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItems() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].fixedAssetSerialCode").value(hasItem(DEFAULT_FIXED_ASSET_SERIAL_CODE.toString())))
            .andExpect(jsonPath("$.[*].fixedAssetDescription").value(hasItem(DEFAULT_FIXED_ASSET_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseCost").value(hasItem(DEFAULT_PURCHASE_COST.intValue())))
            .andExpect(jsonPath("$.[*].purchaseTransactionId").value(hasItem(DEFAULT_PURCHASE_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownershipDocumentId").value(hasItem(DEFAULT_OWNERSHIP_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));
    }
    
    @Test
    @Transactional
    public void getFixedAssetItem() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get the fixedAssetItem
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items/{id}", fixedAssetItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fixedAssetItem.getId().intValue()))
            .andExpect(jsonPath("$.serviceOutletCode").value(DEFAULT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.assetCategoryId").value(DEFAULT_ASSET_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.fixedAssetSerialCode").value(DEFAULT_FIXED_ASSET_SERIAL_CODE.toString()))
            .andExpect(jsonPath("$.fixedAssetDescription").value(DEFAULT_FIXED_ASSET_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.purchaseDate").value(DEFAULT_PURCHASE_DATE.toString()))
            .andExpect(jsonPath("$.purchaseCost").value(DEFAULT_PURCHASE_COST.intValue()))
            .andExpect(jsonPath("$.purchaseTransactionId").value(DEFAULT_PURCHASE_TRANSACTION_ID.intValue()))
            .andExpect(jsonPath("$.ownershipDocumentId").value(DEFAULT_OWNERSHIP_DOCUMENT_ID.intValue()))
            .andExpect(jsonPath("$.assetPictureContentType").value(DEFAULT_ASSET_PICTURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.assetPicture").value(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE)));
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where serviceOutletCode equals to DEFAULT_SERVICE_OUTLET_CODE
        defaultFixedAssetItemShouldBeFound("serviceOutletCode.equals=" + DEFAULT_SERVICE_OUTLET_CODE);

        // Get all the fixedAssetItemList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultFixedAssetItemShouldNotBeFound("serviceOutletCode.equals=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where serviceOutletCode in DEFAULT_SERVICE_OUTLET_CODE or UPDATED_SERVICE_OUTLET_CODE
        defaultFixedAssetItemShouldBeFound("serviceOutletCode.in=" + DEFAULT_SERVICE_OUTLET_CODE + "," + UPDATED_SERVICE_OUTLET_CODE);

        // Get all the fixedAssetItemList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultFixedAssetItemShouldNotBeFound("serviceOutletCode.in=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where serviceOutletCode is not null
        defaultFixedAssetItemShouldBeFound("serviceOutletCode.specified=true");

        // Get all the fixedAssetItemList where serviceOutletCode is null
        defaultFixedAssetItemShouldNotBeFound("serviceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByAssetCategoryIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where assetCategoryId equals to DEFAULT_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldBeFound("assetCategoryId.equals=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the fixedAssetItemList where assetCategoryId equals to UPDATED_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldNotBeFound("assetCategoryId.equals=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByAssetCategoryIdIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where assetCategoryId in DEFAULT_ASSET_CATEGORY_ID or UPDATED_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldBeFound("assetCategoryId.in=" + DEFAULT_ASSET_CATEGORY_ID + "," + UPDATED_ASSET_CATEGORY_ID);

        // Get all the fixedAssetItemList where assetCategoryId equals to UPDATED_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldNotBeFound("assetCategoryId.in=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByAssetCategoryIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where assetCategoryId is not null
        defaultFixedAssetItemShouldBeFound("assetCategoryId.specified=true");

        // Get all the fixedAssetItemList where assetCategoryId is null
        defaultFixedAssetItemShouldNotBeFound("assetCategoryId.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByAssetCategoryIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where assetCategoryId greater than or equals to DEFAULT_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldBeFound("assetCategoryId.greaterOrEqualThan=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the fixedAssetItemList where assetCategoryId greater than or equals to UPDATED_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldNotBeFound("assetCategoryId.greaterOrEqualThan=" + UPDATED_ASSET_CATEGORY_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByAssetCategoryIdIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where assetCategoryId less than or equals to DEFAULT_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldNotBeFound("assetCategoryId.lessThan=" + DEFAULT_ASSET_CATEGORY_ID);

        // Get all the fixedAssetItemList where assetCategoryId less than or equals to UPDATED_ASSET_CATEGORY_ID
        defaultFixedAssetItemShouldBeFound("assetCategoryId.lessThan=" + UPDATED_ASSET_CATEGORY_ID);
    }


    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetSerialCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetSerialCode equals to DEFAULT_FIXED_ASSET_SERIAL_CODE
        defaultFixedAssetItemShouldBeFound("fixedAssetSerialCode.equals=" + DEFAULT_FIXED_ASSET_SERIAL_CODE);

        // Get all the fixedAssetItemList where fixedAssetSerialCode equals to UPDATED_FIXED_ASSET_SERIAL_CODE
        defaultFixedAssetItemShouldNotBeFound("fixedAssetSerialCode.equals=" + UPDATED_FIXED_ASSET_SERIAL_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetSerialCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetSerialCode in DEFAULT_FIXED_ASSET_SERIAL_CODE or UPDATED_FIXED_ASSET_SERIAL_CODE
        defaultFixedAssetItemShouldBeFound("fixedAssetSerialCode.in=" + DEFAULT_FIXED_ASSET_SERIAL_CODE + "," + UPDATED_FIXED_ASSET_SERIAL_CODE);

        // Get all the fixedAssetItemList where fixedAssetSerialCode equals to UPDATED_FIXED_ASSET_SERIAL_CODE
        defaultFixedAssetItemShouldNotBeFound("fixedAssetSerialCode.in=" + UPDATED_FIXED_ASSET_SERIAL_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetSerialCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetSerialCode is not null
        defaultFixedAssetItemShouldBeFound("fixedAssetSerialCode.specified=true");

        // Get all the fixedAssetItemList where fixedAssetSerialCode is null
        defaultFixedAssetItemShouldNotBeFound("fixedAssetSerialCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetDescription equals to DEFAULT_FIXED_ASSET_DESCRIPTION
        defaultFixedAssetItemShouldBeFound("fixedAssetDescription.equals=" + DEFAULT_FIXED_ASSET_DESCRIPTION);

        // Get all the fixedAssetItemList where fixedAssetDescription equals to UPDATED_FIXED_ASSET_DESCRIPTION
        defaultFixedAssetItemShouldNotBeFound("fixedAssetDescription.equals=" + UPDATED_FIXED_ASSET_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetDescription in DEFAULT_FIXED_ASSET_DESCRIPTION or UPDATED_FIXED_ASSET_DESCRIPTION
        defaultFixedAssetItemShouldBeFound("fixedAssetDescription.in=" + DEFAULT_FIXED_ASSET_DESCRIPTION + "," + UPDATED_FIXED_ASSET_DESCRIPTION);

        // Get all the fixedAssetItemList where fixedAssetDescription equals to UPDATED_FIXED_ASSET_DESCRIPTION
        defaultFixedAssetItemShouldNotBeFound("fixedAssetDescription.in=" + UPDATED_FIXED_ASSET_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByFixedAssetDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where fixedAssetDescription is not null
        defaultFixedAssetItemShouldBeFound("fixedAssetDescription.specified=true");

        // Get all the fixedAssetItemList where fixedAssetDescription is null
        defaultFixedAssetItemShouldNotBeFound("fixedAssetDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseDate equals to DEFAULT_PURCHASE_DATE
        defaultFixedAssetItemShouldBeFound("purchaseDate.equals=" + DEFAULT_PURCHASE_DATE);

        // Get all the fixedAssetItemList where purchaseDate equals to UPDATED_PURCHASE_DATE
        defaultFixedAssetItemShouldNotBeFound("purchaseDate.equals=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseDateIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseDate in DEFAULT_PURCHASE_DATE or UPDATED_PURCHASE_DATE
        defaultFixedAssetItemShouldBeFound("purchaseDate.in=" + DEFAULT_PURCHASE_DATE + "," + UPDATED_PURCHASE_DATE);

        // Get all the fixedAssetItemList where purchaseDate equals to UPDATED_PURCHASE_DATE
        defaultFixedAssetItemShouldNotBeFound("purchaseDate.in=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseDate is not null
        defaultFixedAssetItemShouldBeFound("purchaseDate.specified=true");

        // Get all the fixedAssetItemList where purchaseDate is null
        defaultFixedAssetItemShouldNotBeFound("purchaseDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseDate greater than or equals to DEFAULT_PURCHASE_DATE
        defaultFixedAssetItemShouldBeFound("purchaseDate.greaterOrEqualThan=" + DEFAULT_PURCHASE_DATE);

        // Get all the fixedAssetItemList where purchaseDate greater than or equals to UPDATED_PURCHASE_DATE
        defaultFixedAssetItemShouldNotBeFound("purchaseDate.greaterOrEqualThan=" + UPDATED_PURCHASE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseDateIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseDate less than or equals to DEFAULT_PURCHASE_DATE
        defaultFixedAssetItemShouldNotBeFound("purchaseDate.lessThan=" + DEFAULT_PURCHASE_DATE);

        // Get all the fixedAssetItemList where purchaseDate less than or equals to UPDATED_PURCHASE_DATE
        defaultFixedAssetItemShouldBeFound("purchaseDate.lessThan=" + UPDATED_PURCHASE_DATE);
    }


    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseCostIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseCost equals to DEFAULT_PURCHASE_COST
        defaultFixedAssetItemShouldBeFound("purchaseCost.equals=" + DEFAULT_PURCHASE_COST);

        // Get all the fixedAssetItemList where purchaseCost equals to UPDATED_PURCHASE_COST
        defaultFixedAssetItemShouldNotBeFound("purchaseCost.equals=" + UPDATED_PURCHASE_COST);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseCostIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseCost in DEFAULT_PURCHASE_COST or UPDATED_PURCHASE_COST
        defaultFixedAssetItemShouldBeFound("purchaseCost.in=" + DEFAULT_PURCHASE_COST + "," + UPDATED_PURCHASE_COST);

        // Get all the fixedAssetItemList where purchaseCost equals to UPDATED_PURCHASE_COST
        defaultFixedAssetItemShouldNotBeFound("purchaseCost.in=" + UPDATED_PURCHASE_COST);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseCostIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseCost is not null
        defaultFixedAssetItemShouldBeFound("purchaseCost.specified=true");

        // Get all the fixedAssetItemList where purchaseCost is null
        defaultFixedAssetItemShouldNotBeFound("purchaseCost.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseTransactionIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseTransactionId equals to DEFAULT_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldBeFound("purchaseTransactionId.equals=" + DEFAULT_PURCHASE_TRANSACTION_ID);

        // Get all the fixedAssetItemList where purchaseTransactionId equals to UPDATED_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldNotBeFound("purchaseTransactionId.equals=" + UPDATED_PURCHASE_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseTransactionIdIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseTransactionId in DEFAULT_PURCHASE_TRANSACTION_ID or UPDATED_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldBeFound("purchaseTransactionId.in=" + DEFAULT_PURCHASE_TRANSACTION_ID + "," + UPDATED_PURCHASE_TRANSACTION_ID);

        // Get all the fixedAssetItemList where purchaseTransactionId equals to UPDATED_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldNotBeFound("purchaseTransactionId.in=" + UPDATED_PURCHASE_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseTransactionIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseTransactionId is not null
        defaultFixedAssetItemShouldBeFound("purchaseTransactionId.specified=true");

        // Get all the fixedAssetItemList where purchaseTransactionId is null
        defaultFixedAssetItemShouldNotBeFound("purchaseTransactionId.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseTransactionIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseTransactionId greater than or equals to DEFAULT_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldBeFound("purchaseTransactionId.greaterOrEqualThan=" + DEFAULT_PURCHASE_TRANSACTION_ID);

        // Get all the fixedAssetItemList where purchaseTransactionId greater than or equals to UPDATED_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldNotBeFound("purchaseTransactionId.greaterOrEqualThan=" + UPDATED_PURCHASE_TRANSACTION_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByPurchaseTransactionIdIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where purchaseTransactionId less than or equals to DEFAULT_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldNotBeFound("purchaseTransactionId.lessThan=" + DEFAULT_PURCHASE_TRANSACTION_ID);

        // Get all the fixedAssetItemList where purchaseTransactionId less than or equals to UPDATED_PURCHASE_TRANSACTION_ID
        defaultFixedAssetItemShouldBeFound("purchaseTransactionId.lessThan=" + UPDATED_PURCHASE_TRANSACTION_ID);
    }


    @Test
    @Transactional
    public void getAllFixedAssetItemsByOwnershipDocumentIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where ownershipDocumentId equals to DEFAULT_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldBeFound("ownershipDocumentId.equals=" + DEFAULT_OWNERSHIP_DOCUMENT_ID);

        // Get all the fixedAssetItemList where ownershipDocumentId equals to UPDATED_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldNotBeFound("ownershipDocumentId.equals=" + UPDATED_OWNERSHIP_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByOwnershipDocumentIdIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where ownershipDocumentId in DEFAULT_OWNERSHIP_DOCUMENT_ID or UPDATED_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldBeFound("ownershipDocumentId.in=" + DEFAULT_OWNERSHIP_DOCUMENT_ID + "," + UPDATED_OWNERSHIP_DOCUMENT_ID);

        // Get all the fixedAssetItemList where ownershipDocumentId equals to UPDATED_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldNotBeFound("ownershipDocumentId.in=" + UPDATED_OWNERSHIP_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByOwnershipDocumentIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where ownershipDocumentId is not null
        defaultFixedAssetItemShouldBeFound("ownershipDocumentId.specified=true");

        // Get all the fixedAssetItemList where ownershipDocumentId is null
        defaultFixedAssetItemShouldNotBeFound("ownershipDocumentId.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByOwnershipDocumentIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where ownershipDocumentId greater than or equals to DEFAULT_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldBeFound("ownershipDocumentId.greaterOrEqualThan=" + DEFAULT_OWNERSHIP_DOCUMENT_ID);

        // Get all the fixedAssetItemList where ownershipDocumentId greater than or equals to UPDATED_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldNotBeFound("ownershipDocumentId.greaterOrEqualThan=" + UPDATED_OWNERSHIP_DOCUMENT_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetItemsByOwnershipDocumentIdIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        // Get all the fixedAssetItemList where ownershipDocumentId less than or equals to DEFAULT_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldNotBeFound("ownershipDocumentId.lessThan=" + DEFAULT_OWNERSHIP_DOCUMENT_ID);

        // Get all the fixedAssetItemList where ownershipDocumentId less than or equals to UPDATED_OWNERSHIP_DOCUMENT_ID
        defaultFixedAssetItemShouldBeFound("ownershipDocumentId.lessThan=" + UPDATED_OWNERSHIP_DOCUMENT_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFixedAssetItemShouldBeFound(String filter) throws Exception {
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].fixedAssetSerialCode").value(hasItem(DEFAULT_FIXED_ASSET_SERIAL_CODE)))
            .andExpect(jsonPath("$.[*].fixedAssetDescription").value(hasItem(DEFAULT_FIXED_ASSET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseCost").value(hasItem(DEFAULT_PURCHASE_COST.intValue())))
            .andExpect(jsonPath("$.[*].purchaseTransactionId").value(hasItem(DEFAULT_PURCHASE_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownershipDocumentId").value(hasItem(DEFAULT_OWNERSHIP_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));

        // Check, that the count call also returns 1
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFixedAssetItemShouldNotBeFound(String filter) throws Exception {
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFixedAssetItem() throws Exception {
        // Get the fixedAssetItem
        restFixedAssetItemMockMvc.perform(get("/api/fixed-asset-items/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixedAssetItem() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        int databaseSizeBeforeUpdate = fixedAssetItemRepository.findAll().size();

        // Update the fixedAssetItem
        FixedAssetItem updatedFixedAssetItem = fixedAssetItemRepository.findById(fixedAssetItem.getId()).get();
        // Disconnect from session so that the updates on updatedFixedAssetItem are not directly saved in db
        em.detach(updatedFixedAssetItem);
        updatedFixedAssetItem
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .assetCategoryId(UPDATED_ASSET_CATEGORY_ID)
            .fixedAssetSerialCode(UPDATED_FIXED_ASSET_SERIAL_CODE)
            .fixedAssetDescription(UPDATED_FIXED_ASSET_DESCRIPTION)
            .purchaseDate(UPDATED_PURCHASE_DATE)
            .purchaseCost(UPDATED_PURCHASE_COST)
            .purchaseTransactionId(UPDATED_PURCHASE_TRANSACTION_ID)
            .ownershipDocumentId(UPDATED_OWNERSHIP_DOCUMENT_ID)
            .assetPicture(UPDATED_ASSET_PICTURE)
            .assetPictureContentType(UPDATED_ASSET_PICTURE_CONTENT_TYPE);
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(updatedFixedAssetItem);

        restFixedAssetItemMockMvc.perform(put("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isOk());

        // Validate the FixedAssetItem in the database
        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeUpdate);
        FixedAssetItem testFixedAssetItem = fixedAssetItemList.get(fixedAssetItemList.size() - 1);
        assertThat(testFixedAssetItem.getServiceOutletCode()).isEqualTo(UPDATED_SERVICE_OUTLET_CODE);
        assertThat(testFixedAssetItem.getAssetCategoryId()).isEqualTo(UPDATED_ASSET_CATEGORY_ID);
        assertThat(testFixedAssetItem.getFixedAssetSerialCode()).isEqualTo(UPDATED_FIXED_ASSET_SERIAL_CODE);
        assertThat(testFixedAssetItem.getFixedAssetDescription()).isEqualTo(UPDATED_FIXED_ASSET_DESCRIPTION);
        assertThat(testFixedAssetItem.getPurchaseDate()).isEqualTo(UPDATED_PURCHASE_DATE);
        assertThat(testFixedAssetItem.getPurchaseCost()).isEqualTo(UPDATED_PURCHASE_COST);
        assertThat(testFixedAssetItem.getPurchaseTransactionId()).isEqualTo(UPDATED_PURCHASE_TRANSACTION_ID);
        assertThat(testFixedAssetItem.getOwnershipDocumentId()).isEqualTo(UPDATED_OWNERSHIP_DOCUMENT_ID);
        assertThat(testFixedAssetItem.getAssetPicture()).isEqualTo(UPDATED_ASSET_PICTURE);
        assertThat(testFixedAssetItem.getAssetPictureContentType()).isEqualTo(UPDATED_ASSET_PICTURE_CONTENT_TYPE);

        // Validate the FixedAssetItem in Elasticsearch
        verify(mockFixedAssetItemSearchRepository, times(1)).save(testFixedAssetItem);
    }

    @Test
    @Transactional
    public void updateNonExistingFixedAssetItem() throws Exception {
        int databaseSizeBeforeUpdate = fixedAssetItemRepository.findAll().size();

        // Create the FixedAssetItem
        FixedAssetItemDTO fixedAssetItemDTO = fixedAssetItemMapper.toDto(fixedAssetItem);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixedAssetItemMockMvc.perform(put("/api/fixed-asset-items")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetItemDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetItem in the database
        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FixedAssetItem in Elasticsearch
        verify(mockFixedAssetItemSearchRepository, times(0)).save(fixedAssetItem);
    }

    @Test
    @Transactional
    public void deleteFixedAssetItem() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);

        int databaseSizeBeforeDelete = fixedAssetItemRepository.findAll().size();

        // Delete the fixedAssetItem
        restFixedAssetItemMockMvc.perform(delete("/api/fixed-asset-items/{id}", fixedAssetItem.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FixedAssetItem> fixedAssetItemList = fixedAssetItemRepository.findAll();
        assertThat(fixedAssetItemList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FixedAssetItem in Elasticsearch
        verify(mockFixedAssetItemSearchRepository, times(1)).deleteById(fixedAssetItem.getId());
    }

    @Test
    @Transactional
    public void searchFixedAssetItem() throws Exception {
        // Initialize the database
        fixedAssetItemRepository.saveAndFlush(fixedAssetItem);
        when(mockFixedAssetItemSearchRepository.search(queryStringQuery("id:" + fixedAssetItem.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fixedAssetItem), PageRequest.of(0, 1), 1));
        // Search the fixedAssetItem
        restFixedAssetItemMockMvc.perform(get("/api/_search/fixed-asset-items?query=id:" + fixedAssetItem.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetItem.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].assetCategoryId").value(hasItem(DEFAULT_ASSET_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].fixedAssetSerialCode").value(hasItem(DEFAULT_FIXED_ASSET_SERIAL_CODE)))
            .andExpect(jsonPath("$.[*].fixedAssetDescription").value(hasItem(DEFAULT_FIXED_ASSET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].purchaseDate").value(hasItem(DEFAULT_PURCHASE_DATE.toString())))
            .andExpect(jsonPath("$.[*].purchaseCost").value(hasItem(DEFAULT_PURCHASE_COST.intValue())))
            .andExpect(jsonPath("$.[*].purchaseTransactionId").value(hasItem(DEFAULT_PURCHASE_TRANSACTION_ID.intValue())))
            .andExpect(jsonPath("$.[*].ownershipDocumentId").value(hasItem(DEFAULT_OWNERSHIP_DOCUMENT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPictureContentType").value(hasItem(DEFAULT_ASSET_PICTURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].assetPicture").value(hasItem(Base64Utils.encodeToString(DEFAULT_ASSET_PICTURE))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetItem.class);
        FixedAssetItem fixedAssetItem1 = new FixedAssetItem();
        fixedAssetItem1.setId(1L);
        FixedAssetItem fixedAssetItem2 = new FixedAssetItem();
        fixedAssetItem2.setId(fixedAssetItem1.getId());
        assertThat(fixedAssetItem1).isEqualTo(fixedAssetItem2);
        fixedAssetItem2.setId(2L);
        assertThat(fixedAssetItem1).isNotEqualTo(fixedAssetItem2);
        fixedAssetItem1.setId(null);
        assertThat(fixedAssetItem1).isNotEqualTo(fixedAssetItem2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetItemDTO.class);
        FixedAssetItemDTO fixedAssetItemDTO1 = new FixedAssetItemDTO();
        fixedAssetItemDTO1.setId(1L);
        FixedAssetItemDTO fixedAssetItemDTO2 = new FixedAssetItemDTO();
        assertThat(fixedAssetItemDTO1).isNotEqualTo(fixedAssetItemDTO2);
        fixedAssetItemDTO2.setId(fixedAssetItemDTO1.getId());
        assertThat(fixedAssetItemDTO1).isEqualTo(fixedAssetItemDTO2);
        fixedAssetItemDTO2.setId(2L);
        assertThat(fixedAssetItemDTO1).isNotEqualTo(fixedAssetItemDTO2);
        fixedAssetItemDTO1.setId(null);
        assertThat(fixedAssetItemDTO1).isNotEqualTo(fixedAssetItemDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fixedAssetItemMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fixedAssetItemMapper.fromId(null)).isNull();
    }
}
