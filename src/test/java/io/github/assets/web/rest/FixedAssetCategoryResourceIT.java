package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.FixedAssetCategory;
import io.github.assets.repository.FixedAssetCategoryRepository;
import io.github.assets.repository.search.FixedAssetCategorySearchRepository;
import io.github.assets.service.FixedAssetCategoryService;
import io.github.assets.service.dto.FixedAssetCategoryDTO;
import io.github.assets.service.mapper.FixedAssetCategoryMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.FixedAssetCategoryCriteria;
import io.github.assets.service.FixedAssetCategoryQueryService;

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
 * Integration tests for the {@Link FixedAssetCategoryResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class FixedAssetCategoryResourceIT {

    private static final String DEFAULT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_DEPRECIATION_REGIME_ID = 1L;
    private static final Long UPDATED_DEPRECIATION_REGIME_ID = 2L;

    @Autowired
    private FixedAssetCategoryRepository fixedAssetCategoryRepository;

    @Autowired
    private FixedAssetCategoryMapper fixedAssetCategoryMapper;

    @Autowired
    private FixedAssetCategoryService fixedAssetCategoryService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.FixedAssetCategorySearchRepositoryMockConfiguration
     */
    @Autowired
    private FixedAssetCategorySearchRepository mockFixedAssetCategorySearchRepository;

    @Autowired
    private FixedAssetCategoryQueryService fixedAssetCategoryQueryService;

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

    private MockMvc restFixedAssetCategoryMockMvc;

    private FixedAssetCategory fixedAssetCategory;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FixedAssetCategoryResource fixedAssetCategoryResource = new FixedAssetCategoryResource(fixedAssetCategoryService, fixedAssetCategoryQueryService);
        this.restFixedAssetCategoryMockMvc = MockMvcBuilders.standaloneSetup(fixedAssetCategoryResource)
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
    public static FixedAssetCategory createEntity(EntityManager em) {
        FixedAssetCategory fixedAssetCategory = new FixedAssetCategory()
            .categoryCode(DEFAULT_CATEGORY_CODE)
            .categoryName(DEFAULT_CATEGORY_NAME)
            .categoryDescription(DEFAULT_CATEGORY_DESCRIPTION)
            .depreciationRegimeId(DEFAULT_DEPRECIATION_REGIME_ID);
        return fixedAssetCategory;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FixedAssetCategory createUpdatedEntity(EntityManager em) {
        FixedAssetCategory fixedAssetCategory = new FixedAssetCategory()
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .depreciationRegimeId(UPDATED_DEPRECIATION_REGIME_ID);
        return fixedAssetCategory;
    }

    @BeforeEach
    public void initTest() {
        fixedAssetCategory = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixedAssetCategory() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetCategoryRepository.findAll().size();

        // Create the FixedAssetCategory
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(fixedAssetCategory);
        restFixedAssetCategoryMockMvc.perform(post("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isCreated());

        // Validate the FixedAssetCategory in the database
        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeCreate + 1);
        FixedAssetCategory testFixedAssetCategory = fixedAssetCategoryList.get(fixedAssetCategoryList.size() - 1);
        assertThat(testFixedAssetCategory.getCategoryCode()).isEqualTo(DEFAULT_CATEGORY_CODE);
        assertThat(testFixedAssetCategory.getCategoryName()).isEqualTo(DEFAULT_CATEGORY_NAME);
        assertThat(testFixedAssetCategory.getCategoryDescription()).isEqualTo(DEFAULT_CATEGORY_DESCRIPTION);
        assertThat(testFixedAssetCategory.getDepreciationRegimeId()).isEqualTo(DEFAULT_DEPRECIATION_REGIME_ID);

        // Validate the FixedAssetCategory in Elasticsearch
        verify(mockFixedAssetCategorySearchRepository, times(1)).save(testFixedAssetCategory);
    }

    @Test
    @Transactional
    public void createFixedAssetCategoryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetCategoryRepository.findAll().size();

        // Create the FixedAssetCategory with an existing ID
        fixedAssetCategory.setId(1L);
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(fixedAssetCategory);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedAssetCategoryMockMvc.perform(post("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetCategory in the database
        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeCreate);

        // Validate the FixedAssetCategory in Elasticsearch
        verify(mockFixedAssetCategorySearchRepository, times(0)).save(fixedAssetCategory);
    }


    @Test
    @Transactional
    public void checkCategoryCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetCategoryRepository.findAll().size();
        // set the field null
        fixedAssetCategory.setCategoryCode(null);

        // Create the FixedAssetCategory, which fails.
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(fixedAssetCategory);

        restFixedAssetCategoryMockMvc.perform(post("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCategoryNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetCategoryRepository.findAll().size();
        // set the field null
        fixedAssetCategory.setCategoryName(null);

        // Create the FixedAssetCategory, which fails.
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(fixedAssetCategory);

        restFixedAssetCategoryMockMvc.perform(post("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategories() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE.toString())))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME.toString())))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].depreciationRegimeId").value(hasItem(DEFAULT_DEPRECIATION_REGIME_ID.intValue())));
    }
    
    @Test
    @Transactional
    public void getFixedAssetCategory() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get the fixedAssetCategory
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories/{id}", fixedAssetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fixedAssetCategory.getId().intValue()))
            .andExpect(jsonPath("$.categoryCode").value(DEFAULT_CATEGORY_CODE.toString()))
            .andExpect(jsonPath("$.categoryName").value(DEFAULT_CATEGORY_NAME.toString()))
            .andExpect(jsonPath("$.categoryDescription").value(DEFAULT_CATEGORY_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.depreciationRegimeId").value(DEFAULT_DEPRECIATION_REGIME_ID.intValue()));
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryCode equals to DEFAULT_CATEGORY_CODE
        defaultFixedAssetCategoryShouldBeFound("categoryCode.equals=" + DEFAULT_CATEGORY_CODE);

        // Get all the fixedAssetCategoryList where categoryCode equals to UPDATED_CATEGORY_CODE
        defaultFixedAssetCategoryShouldNotBeFound("categoryCode.equals=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryCodeIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryCode in DEFAULT_CATEGORY_CODE or UPDATED_CATEGORY_CODE
        defaultFixedAssetCategoryShouldBeFound("categoryCode.in=" + DEFAULT_CATEGORY_CODE + "," + UPDATED_CATEGORY_CODE);

        // Get all the fixedAssetCategoryList where categoryCode equals to UPDATED_CATEGORY_CODE
        defaultFixedAssetCategoryShouldNotBeFound("categoryCode.in=" + UPDATED_CATEGORY_CODE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryCode is not null
        defaultFixedAssetCategoryShouldBeFound("categoryCode.specified=true");

        // Get all the fixedAssetCategoryList where categoryCode is null
        defaultFixedAssetCategoryShouldNotBeFound("categoryCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryName equals to DEFAULT_CATEGORY_NAME
        defaultFixedAssetCategoryShouldBeFound("categoryName.equals=" + DEFAULT_CATEGORY_NAME);

        // Get all the fixedAssetCategoryList where categoryName equals to UPDATED_CATEGORY_NAME
        defaultFixedAssetCategoryShouldNotBeFound("categoryName.equals=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryNameIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryName in DEFAULT_CATEGORY_NAME or UPDATED_CATEGORY_NAME
        defaultFixedAssetCategoryShouldBeFound("categoryName.in=" + DEFAULT_CATEGORY_NAME + "," + UPDATED_CATEGORY_NAME);

        // Get all the fixedAssetCategoryList where categoryName equals to UPDATED_CATEGORY_NAME
        defaultFixedAssetCategoryShouldNotBeFound("categoryName.in=" + UPDATED_CATEGORY_NAME);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryName is not null
        defaultFixedAssetCategoryShouldBeFound("categoryName.specified=true");

        // Get all the fixedAssetCategoryList where categoryName is null
        defaultFixedAssetCategoryShouldNotBeFound("categoryName.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryDescription equals to DEFAULT_CATEGORY_DESCRIPTION
        defaultFixedAssetCategoryShouldBeFound("categoryDescription.equals=" + DEFAULT_CATEGORY_DESCRIPTION);

        // Get all the fixedAssetCategoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultFixedAssetCategoryShouldNotBeFound("categoryDescription.equals=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryDescription in DEFAULT_CATEGORY_DESCRIPTION or UPDATED_CATEGORY_DESCRIPTION
        defaultFixedAssetCategoryShouldBeFound("categoryDescription.in=" + DEFAULT_CATEGORY_DESCRIPTION + "," + UPDATED_CATEGORY_DESCRIPTION);

        // Get all the fixedAssetCategoryList where categoryDescription equals to UPDATED_CATEGORY_DESCRIPTION
        defaultFixedAssetCategoryShouldNotBeFound("categoryDescription.in=" + UPDATED_CATEGORY_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByCategoryDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where categoryDescription is not null
        defaultFixedAssetCategoryShouldBeFound("categoryDescription.specified=true");

        // Get all the fixedAssetCategoryList where categoryDescription is null
        defaultFixedAssetCategoryShouldNotBeFound("categoryDescription.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByDepreciationRegimeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where depreciationRegimeId equals to DEFAULT_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldBeFound("depreciationRegimeId.equals=" + DEFAULT_DEPRECIATION_REGIME_ID);

        // Get all the fixedAssetCategoryList where depreciationRegimeId equals to UPDATED_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldNotBeFound("depreciationRegimeId.equals=" + UPDATED_DEPRECIATION_REGIME_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByDepreciationRegimeIdIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where depreciationRegimeId in DEFAULT_DEPRECIATION_REGIME_ID or UPDATED_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldBeFound("depreciationRegimeId.in=" + DEFAULT_DEPRECIATION_REGIME_ID + "," + UPDATED_DEPRECIATION_REGIME_ID);

        // Get all the fixedAssetCategoryList where depreciationRegimeId equals to UPDATED_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldNotBeFound("depreciationRegimeId.in=" + UPDATED_DEPRECIATION_REGIME_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByDepreciationRegimeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where depreciationRegimeId is not null
        defaultFixedAssetCategoryShouldBeFound("depreciationRegimeId.specified=true");

        // Get all the fixedAssetCategoryList where depreciationRegimeId is null
        defaultFixedAssetCategoryShouldNotBeFound("depreciationRegimeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByDepreciationRegimeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where depreciationRegimeId greater than or equals to DEFAULT_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldBeFound("depreciationRegimeId.greaterOrEqualThan=" + DEFAULT_DEPRECIATION_REGIME_ID);

        // Get all the fixedAssetCategoryList where depreciationRegimeId greater than or equals to UPDATED_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldNotBeFound("depreciationRegimeId.greaterOrEqualThan=" + UPDATED_DEPRECIATION_REGIME_ID);
    }

    @Test
    @Transactional
    public void getAllFixedAssetCategoriesByDepreciationRegimeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        // Get all the fixedAssetCategoryList where depreciationRegimeId less than or equals to DEFAULT_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldNotBeFound("depreciationRegimeId.lessThan=" + DEFAULT_DEPRECIATION_REGIME_ID);

        // Get all the fixedAssetCategoryList where depreciationRegimeId less than or equals to UPDATED_DEPRECIATION_REGIME_ID
        defaultFixedAssetCategoryShouldBeFound("depreciationRegimeId.lessThan=" + UPDATED_DEPRECIATION_REGIME_ID);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFixedAssetCategoryShouldBeFound(String filter) throws Exception {
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].depreciationRegimeId").value(hasItem(DEFAULT_DEPRECIATION_REGIME_ID.intValue())));

        // Check, that the count call also returns 1
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFixedAssetCategoryShouldNotBeFound(String filter) throws Exception {
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFixedAssetCategory() throws Exception {
        // Get the fixedAssetCategory
        restFixedAssetCategoryMockMvc.perform(get("/api/fixed-asset-categories/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixedAssetCategory() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        int databaseSizeBeforeUpdate = fixedAssetCategoryRepository.findAll().size();

        // Update the fixedAssetCategory
        FixedAssetCategory updatedFixedAssetCategory = fixedAssetCategoryRepository.findById(fixedAssetCategory.getId()).get();
        // Disconnect from session so that the updates on updatedFixedAssetCategory are not directly saved in db
        em.detach(updatedFixedAssetCategory);
        updatedFixedAssetCategory
            .categoryCode(UPDATED_CATEGORY_CODE)
            .categoryName(UPDATED_CATEGORY_NAME)
            .categoryDescription(UPDATED_CATEGORY_DESCRIPTION)
            .depreciationRegimeId(UPDATED_DEPRECIATION_REGIME_ID);
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(updatedFixedAssetCategory);

        restFixedAssetCategoryMockMvc.perform(put("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isOk());

        // Validate the FixedAssetCategory in the database
        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeUpdate);
        FixedAssetCategory testFixedAssetCategory = fixedAssetCategoryList.get(fixedAssetCategoryList.size() - 1);
        assertThat(testFixedAssetCategory.getCategoryCode()).isEqualTo(UPDATED_CATEGORY_CODE);
        assertThat(testFixedAssetCategory.getCategoryName()).isEqualTo(UPDATED_CATEGORY_NAME);
        assertThat(testFixedAssetCategory.getCategoryDescription()).isEqualTo(UPDATED_CATEGORY_DESCRIPTION);
        assertThat(testFixedAssetCategory.getDepreciationRegimeId()).isEqualTo(UPDATED_DEPRECIATION_REGIME_ID);

        // Validate the FixedAssetCategory in Elasticsearch
        verify(mockFixedAssetCategorySearchRepository, times(1)).save(testFixedAssetCategory);
    }

    @Test
    @Transactional
    public void updateNonExistingFixedAssetCategory() throws Exception {
        int databaseSizeBeforeUpdate = fixedAssetCategoryRepository.findAll().size();

        // Create the FixedAssetCategory
        FixedAssetCategoryDTO fixedAssetCategoryDTO = fixedAssetCategoryMapper.toDto(fixedAssetCategory);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixedAssetCategoryMockMvc.perform(put("/api/fixed-asset-categories")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetCategoryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetCategory in the database
        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FixedAssetCategory in Elasticsearch
        verify(mockFixedAssetCategorySearchRepository, times(0)).save(fixedAssetCategory);
    }

    @Test
    @Transactional
    public void deleteFixedAssetCategory() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);

        int databaseSizeBeforeDelete = fixedAssetCategoryRepository.findAll().size();

        // Delete the fixedAssetCategory
        restFixedAssetCategoryMockMvc.perform(delete("/api/fixed-asset-categories/{id}", fixedAssetCategory.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FixedAssetCategory> fixedAssetCategoryList = fixedAssetCategoryRepository.findAll();
        assertThat(fixedAssetCategoryList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FixedAssetCategory in Elasticsearch
        verify(mockFixedAssetCategorySearchRepository, times(1)).deleteById(fixedAssetCategory.getId());
    }

    @Test
    @Transactional
    public void searchFixedAssetCategory() throws Exception {
        // Initialize the database
        fixedAssetCategoryRepository.saveAndFlush(fixedAssetCategory);
        when(mockFixedAssetCategorySearchRepository.search(queryStringQuery("id:" + fixedAssetCategory.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fixedAssetCategory), PageRequest.of(0, 1), 1));
        // Search the fixedAssetCategory
        restFixedAssetCategoryMockMvc.perform(get("/api/_search/fixed-asset-categories?query=id:" + fixedAssetCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].categoryCode").value(hasItem(DEFAULT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].categoryName").value(hasItem(DEFAULT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].categoryDescription").value(hasItem(DEFAULT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].depreciationRegimeId").value(hasItem(DEFAULT_DEPRECIATION_REGIME_ID.intValue())));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetCategory.class);
        FixedAssetCategory fixedAssetCategory1 = new FixedAssetCategory();
        fixedAssetCategory1.setId(1L);
        FixedAssetCategory fixedAssetCategory2 = new FixedAssetCategory();
        fixedAssetCategory2.setId(fixedAssetCategory1.getId());
        assertThat(fixedAssetCategory1).isEqualTo(fixedAssetCategory2);
        fixedAssetCategory2.setId(2L);
        assertThat(fixedAssetCategory1).isNotEqualTo(fixedAssetCategory2);
        fixedAssetCategory1.setId(null);
        assertThat(fixedAssetCategory1).isNotEqualTo(fixedAssetCategory2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetCategoryDTO.class);
        FixedAssetCategoryDTO fixedAssetCategoryDTO1 = new FixedAssetCategoryDTO();
        fixedAssetCategoryDTO1.setId(1L);
        FixedAssetCategoryDTO fixedAssetCategoryDTO2 = new FixedAssetCategoryDTO();
        assertThat(fixedAssetCategoryDTO1).isNotEqualTo(fixedAssetCategoryDTO2);
        fixedAssetCategoryDTO2.setId(fixedAssetCategoryDTO1.getId());
        assertThat(fixedAssetCategoryDTO1).isEqualTo(fixedAssetCategoryDTO2);
        fixedAssetCategoryDTO2.setId(2L);
        assertThat(fixedAssetCategoryDTO1).isNotEqualTo(fixedAssetCategoryDTO2);
        fixedAssetCategoryDTO1.setId(null);
        assertThat(fixedAssetCategoryDTO1).isNotEqualTo(fixedAssetCategoryDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fixedAssetCategoryMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fixedAssetCategoryMapper.fromId(null)).isNull();
    }
}
