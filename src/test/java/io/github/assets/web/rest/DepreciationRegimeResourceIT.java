package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.DepreciationRegime;
import io.github.assets.repository.DepreciationRegimeRepository;
import io.github.assets.repository.search.DepreciationRegimeSearchRepository;
import io.github.assets.service.DepreciationRegimeService;
import io.github.assets.service.dto.DepreciationRegimeDTO;
import io.github.assets.service.mapper.DepreciationRegimeMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.DepreciationRegimeCriteria;
import io.github.assets.service.DepreciationRegimeQueryService;

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

import io.github.assets.domain.enumeration.AssetDecayType;
/**
 * Integration tests for the {@Link DepreciationRegimeResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class DepreciationRegimeResourceIT {

    private static final AssetDecayType DEFAULT_ASSET_DECAY_TYPE = AssetDecayType.STRAIGHT_LINE;
    private static final AssetDecayType UPDATED_ASSET_DECAY_TYPE = AssetDecayType.DECLINING_BALANCE;

    private static final Double DEFAULT_DEPRECIATION_RATE = 1D;
    private static final Double UPDATED_DEPRECIATION_RATE = 2D;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private DepreciationRegimeRepository depreciationRegimeRepository;

    @Autowired
    private DepreciationRegimeMapper depreciationRegimeMapper;

    @Autowired
    private DepreciationRegimeService depreciationRegimeService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.DepreciationRegimeSearchRepositoryMockConfiguration
     */
    @Autowired
    private DepreciationRegimeSearchRepository mockDepreciationRegimeSearchRepository;

    @Autowired
    private DepreciationRegimeQueryService depreciationRegimeQueryService;

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

    private MockMvc restDepreciationRegimeMockMvc;

    private DepreciationRegime depreciationRegime;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final DepreciationRegimeResource depreciationRegimeResource = new DepreciationRegimeResource(depreciationRegimeService, depreciationRegimeQueryService);
        this.restDepreciationRegimeMockMvc = MockMvcBuilders.standaloneSetup(depreciationRegimeResource)
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
    public static DepreciationRegime createEntity(EntityManager em) {
        DepreciationRegime depreciationRegime = new DepreciationRegime()
            .assetDecayType(DEFAULT_ASSET_DECAY_TYPE)
            .depreciationRate(DEFAULT_DEPRECIATION_RATE)
            .description(DEFAULT_DESCRIPTION);
        return depreciationRegime;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DepreciationRegime createUpdatedEntity(EntityManager em) {
        DepreciationRegime depreciationRegime = new DepreciationRegime()
            .assetDecayType(UPDATED_ASSET_DECAY_TYPE)
            .depreciationRate(UPDATED_DEPRECIATION_RATE)
            .description(UPDATED_DESCRIPTION);
        return depreciationRegime;
    }

    @BeforeEach
    public void initTest() {
        depreciationRegime = createEntity(em);
    }

    @Test
    @Transactional
    public void createDepreciationRegime() throws Exception {
        int databaseSizeBeforeCreate = depreciationRegimeRepository.findAll().size();

        // Create the DepreciationRegime
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(depreciationRegime);
        restDepreciationRegimeMockMvc.perform(post("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isCreated());

        // Validate the DepreciationRegime in the database
        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeCreate + 1);
        DepreciationRegime testDepreciationRegime = depreciationRegimeList.get(depreciationRegimeList.size() - 1);
        assertThat(testDepreciationRegime.getAssetDecayType()).isEqualTo(DEFAULT_ASSET_DECAY_TYPE);
        assertThat(testDepreciationRegime.getDepreciationRate()).isEqualTo(DEFAULT_DEPRECIATION_RATE);
        assertThat(testDepreciationRegime.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the DepreciationRegime in Elasticsearch
        verify(mockDepreciationRegimeSearchRepository, times(1)).save(testDepreciationRegime);
    }

    @Test
    @Transactional
    public void createDepreciationRegimeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = depreciationRegimeRepository.findAll().size();

        // Create the DepreciationRegime with an existing ID
        depreciationRegime.setId(1L);
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(depreciationRegime);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDepreciationRegimeMockMvc.perform(post("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationRegime in the database
        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeCreate);

        // Validate the DepreciationRegime in Elasticsearch
        verify(mockDepreciationRegimeSearchRepository, times(0)).save(depreciationRegime);
    }


    @Test
    @Transactional
    public void checkAssetDecayTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = depreciationRegimeRepository.findAll().size();
        // set the field null
        depreciationRegime.setAssetDecayType(null);

        // Create the DepreciationRegime, which fails.
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(depreciationRegime);

        restDepreciationRegimeMockMvc.perform(post("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkDepreciationRateIsRequired() throws Exception {
        int databaseSizeBeforeTest = depreciationRegimeRepository.findAll().size();
        // set the field null
        depreciationRegime.setDepreciationRate(null);

        // Create the DepreciationRegime, which fails.
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(depreciationRegime);

        restDepreciationRegimeMockMvc.perform(post("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isBadRequest());

        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimes() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetDecayType").value(hasItem(DEFAULT_ASSET_DECAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getDepreciationRegime() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get the depreciationRegime
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes/{id}", depreciationRegime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(depreciationRegime.getId().intValue()))
            .andExpect(jsonPath("$.assetDecayType").value(DEFAULT_ASSET_DECAY_TYPE.toString()))
            .andExpect(jsonPath("$.depreciationRate").value(DEFAULT_DEPRECIATION_RATE.doubleValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByAssetDecayTypeIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where assetDecayType equals to DEFAULT_ASSET_DECAY_TYPE
        defaultDepreciationRegimeShouldBeFound("assetDecayType.equals=" + DEFAULT_ASSET_DECAY_TYPE);

        // Get all the depreciationRegimeList where assetDecayType equals to UPDATED_ASSET_DECAY_TYPE
        defaultDepreciationRegimeShouldNotBeFound("assetDecayType.equals=" + UPDATED_ASSET_DECAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByAssetDecayTypeIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where assetDecayType in DEFAULT_ASSET_DECAY_TYPE or UPDATED_ASSET_DECAY_TYPE
        defaultDepreciationRegimeShouldBeFound("assetDecayType.in=" + DEFAULT_ASSET_DECAY_TYPE + "," + UPDATED_ASSET_DECAY_TYPE);

        // Get all the depreciationRegimeList where assetDecayType equals to UPDATED_ASSET_DECAY_TYPE
        defaultDepreciationRegimeShouldNotBeFound("assetDecayType.in=" + UPDATED_ASSET_DECAY_TYPE);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByAssetDecayTypeIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where assetDecayType is not null
        defaultDepreciationRegimeShouldBeFound("assetDecayType.specified=true");

        // Get all the depreciationRegimeList where assetDecayType is null
        defaultDepreciationRegimeShouldNotBeFound("assetDecayType.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDepreciationRateIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where depreciationRate equals to DEFAULT_DEPRECIATION_RATE
        defaultDepreciationRegimeShouldBeFound("depreciationRate.equals=" + DEFAULT_DEPRECIATION_RATE);

        // Get all the depreciationRegimeList where depreciationRate equals to UPDATED_DEPRECIATION_RATE
        defaultDepreciationRegimeShouldNotBeFound("depreciationRate.equals=" + UPDATED_DEPRECIATION_RATE);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDepreciationRateIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where depreciationRate in DEFAULT_DEPRECIATION_RATE or UPDATED_DEPRECIATION_RATE
        defaultDepreciationRegimeShouldBeFound("depreciationRate.in=" + DEFAULT_DEPRECIATION_RATE + "," + UPDATED_DEPRECIATION_RATE);

        // Get all the depreciationRegimeList where depreciationRate equals to UPDATED_DEPRECIATION_RATE
        defaultDepreciationRegimeShouldNotBeFound("depreciationRate.in=" + UPDATED_DEPRECIATION_RATE);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDepreciationRateIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where depreciationRate is not null
        defaultDepreciationRegimeShouldBeFound("depreciationRate.specified=true");

        // Get all the depreciationRegimeList where depreciationRate is null
        defaultDepreciationRegimeShouldNotBeFound("depreciationRate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where description equals to DEFAULT_DESCRIPTION
        defaultDepreciationRegimeShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the depreciationRegimeList where description equals to UPDATED_DESCRIPTION
        defaultDepreciationRegimeShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultDepreciationRegimeShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the depreciationRegimeList where description equals to UPDATED_DESCRIPTION
        defaultDepreciationRegimeShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllDepreciationRegimesByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        // Get all the depreciationRegimeList where description is not null
        defaultDepreciationRegimeShouldBeFound("description.specified=true");

        // Get all the depreciationRegimeList where description is null
        defaultDepreciationRegimeShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDepreciationRegimeShouldBeFound(String filter) throws Exception {
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetDecayType").value(hasItem(DEFAULT_ASSET_DECAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));

        // Check, that the count call also returns 1
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDepreciationRegimeShouldNotBeFound(String filter) throws Exception {
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDepreciationRegime() throws Exception {
        // Get the depreciationRegime
        restDepreciationRegimeMockMvc.perform(get("/api/depreciation-regimes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDepreciationRegime() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        int databaseSizeBeforeUpdate = depreciationRegimeRepository.findAll().size();

        // Update the depreciationRegime
        DepreciationRegime updatedDepreciationRegime = depreciationRegimeRepository.findById(depreciationRegime.getId()).get();
        // Disconnect from session so that the updates on updatedDepreciationRegime are not directly saved in db
        em.detach(updatedDepreciationRegime);
        updatedDepreciationRegime
            .assetDecayType(UPDATED_ASSET_DECAY_TYPE)
            .depreciationRate(UPDATED_DEPRECIATION_RATE)
            .description(UPDATED_DESCRIPTION);
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(updatedDepreciationRegime);

        restDepreciationRegimeMockMvc.perform(put("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isOk());

        // Validate the DepreciationRegime in the database
        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeUpdate);
        DepreciationRegime testDepreciationRegime = depreciationRegimeList.get(depreciationRegimeList.size() - 1);
        assertThat(testDepreciationRegime.getAssetDecayType()).isEqualTo(UPDATED_ASSET_DECAY_TYPE);
        assertThat(testDepreciationRegime.getDepreciationRate()).isEqualTo(UPDATED_DEPRECIATION_RATE);
        assertThat(testDepreciationRegime.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the DepreciationRegime in Elasticsearch
        verify(mockDepreciationRegimeSearchRepository, times(1)).save(testDepreciationRegime);
    }

    @Test
    @Transactional
    public void updateNonExistingDepreciationRegime() throws Exception {
        int databaseSizeBeforeUpdate = depreciationRegimeRepository.findAll().size();

        // Create the DepreciationRegime
        DepreciationRegimeDTO depreciationRegimeDTO = depreciationRegimeMapper.toDto(depreciationRegime);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDepreciationRegimeMockMvc.perform(put("/api/depreciation-regimes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(depreciationRegimeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the DepreciationRegime in the database
        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the DepreciationRegime in Elasticsearch
        verify(mockDepreciationRegimeSearchRepository, times(0)).save(depreciationRegime);
    }

    @Test
    @Transactional
    public void deleteDepreciationRegime() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);

        int databaseSizeBeforeDelete = depreciationRegimeRepository.findAll().size();

        // Delete the depreciationRegime
        restDepreciationRegimeMockMvc.perform(delete("/api/depreciation-regimes/{id}", depreciationRegime.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<DepreciationRegime> depreciationRegimeList = depreciationRegimeRepository.findAll();
        assertThat(depreciationRegimeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the DepreciationRegime in Elasticsearch
        verify(mockDepreciationRegimeSearchRepository, times(1)).deleteById(depreciationRegime.getId());
    }

    @Test
    @Transactional
    public void searchDepreciationRegime() throws Exception {
        // Initialize the database
        depreciationRegimeRepository.saveAndFlush(depreciationRegime);
        when(mockDepreciationRegimeSearchRepository.search(queryStringQuery("id:" + depreciationRegime.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(depreciationRegime), PageRequest.of(0, 1), 1));
        // Search the depreciationRegime
        restDepreciationRegimeMockMvc.perform(get("/api/_search/depreciation-regimes?query=id:" + depreciationRegime.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(depreciationRegime.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetDecayType").value(hasItem(DEFAULT_ASSET_DECAY_TYPE.toString())))
            .andExpect(jsonPath("$.[*].depreciationRate").value(hasItem(DEFAULT_DEPRECIATION_RATE.doubleValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationRegime.class);
        DepreciationRegime depreciationRegime1 = new DepreciationRegime();
        depreciationRegime1.setId(1L);
        DepreciationRegime depreciationRegime2 = new DepreciationRegime();
        depreciationRegime2.setId(depreciationRegime1.getId());
        assertThat(depreciationRegime1).isEqualTo(depreciationRegime2);
        depreciationRegime2.setId(2L);
        assertThat(depreciationRegime1).isNotEqualTo(depreciationRegime2);
        depreciationRegime1.setId(null);
        assertThat(depreciationRegime1).isNotEqualTo(depreciationRegime2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(DepreciationRegimeDTO.class);
        DepreciationRegimeDTO depreciationRegimeDTO1 = new DepreciationRegimeDTO();
        depreciationRegimeDTO1.setId(1L);
        DepreciationRegimeDTO depreciationRegimeDTO2 = new DepreciationRegimeDTO();
        assertThat(depreciationRegimeDTO1).isNotEqualTo(depreciationRegimeDTO2);
        depreciationRegimeDTO2.setId(depreciationRegimeDTO1.getId());
        assertThat(depreciationRegimeDTO1).isEqualTo(depreciationRegimeDTO2);
        depreciationRegimeDTO2.setId(2L);
        assertThat(depreciationRegimeDTO1).isNotEqualTo(depreciationRegimeDTO2);
        depreciationRegimeDTO1.setId(null);
        assertThat(depreciationRegimeDTO1).isNotEqualTo(depreciationRegimeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(depreciationRegimeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(depreciationRegimeMapper.fromId(null)).isNull();
    }
}
