package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.ServiceOutlet;
import io.github.assets.repository.ServiceOutletRepository;
import io.github.assets.repository.search.ServiceOutletSearchRepository;
import io.github.assets.service.ServiceOutletService;
import io.github.assets.service.dto.ServiceOutletDTO;
import io.github.assets.service.mapper.ServiceOutletMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.ServiceOutletCriteria;
import io.github.assets.service.ServiceOutletQueryService;

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
 * Integration tests for the {@Link ServiceOutletResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class ServiceOutletResourceIT {

    private static final String DEFAULT_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET_DESIGNATION = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_DESIGNATION = "BBBBBBBBBB";

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION = "BBBBBBBBBB";

    @Autowired
    private ServiceOutletRepository serviceOutletRepository;

    @Autowired
    private ServiceOutletMapper serviceOutletMapper;

    @Autowired
    private ServiceOutletService serviceOutletService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.ServiceOutletSearchRepositoryMockConfiguration
     */
    @Autowired
    private ServiceOutletSearchRepository mockServiceOutletSearchRepository;

    @Autowired
    private ServiceOutletQueryService serviceOutletQueryService;

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

    private MockMvc restServiceOutletMockMvc;

    private ServiceOutlet serviceOutlet;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ServiceOutletResource serviceOutletResource = new ServiceOutletResource(serviceOutletService, serviceOutletQueryService);
        this.restServiceOutletMockMvc = MockMvcBuilders.standaloneSetup(serviceOutletResource)
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
    public static ServiceOutlet createEntity(EntityManager em) {
        ServiceOutlet serviceOutlet = new ServiceOutlet()
            .serviceOutletCode(DEFAULT_SERVICE_OUTLET_CODE)
            .serviceOutletDesignation(DEFAULT_SERVICE_OUTLET_DESIGNATION)
            .description(DEFAULT_DESCRIPTION)
            .location(DEFAULT_LOCATION);
        return serviceOutlet;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ServiceOutlet createUpdatedEntity(EntityManager em) {
        ServiceOutlet serviceOutlet = new ServiceOutlet()
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .serviceOutletDesignation(UPDATED_SERVICE_OUTLET_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION);
        return serviceOutlet;
    }

    @BeforeEach
    public void initTest() {
        serviceOutlet = createEntity(em);
    }

    @Test
    @Transactional
    public void createServiceOutlet() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);
        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isCreated());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeCreate + 1);
        ServiceOutlet testServiceOutlet = serviceOutletList.get(serviceOutletList.size() - 1);
        assertThat(testServiceOutlet.getServiceOutletCode()).isEqualTo(DEFAULT_SERVICE_OUTLET_CODE);
        assertThat(testServiceOutlet.getServiceOutletDesignation()).isEqualTo(DEFAULT_SERVICE_OUTLET_DESIGNATION);
        assertThat(testServiceOutlet.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testServiceOutlet.getLocation()).isEqualTo(DEFAULT_LOCATION);

        // Validate the ServiceOutlet in Elasticsearch
        verify(mockServiceOutletSearchRepository, times(1)).save(testServiceOutlet);
    }

    @Test
    @Transactional
    public void createServiceOutletWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet with an existing ID
        serviceOutlet.setId(1L);
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        // An entity with an existing ID cannot be created, so this API call must fail
        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeCreate);

        // Validate the ServiceOutlet in Elasticsearch
        verify(mockServiceOutletSearchRepository, times(0)).save(serviceOutlet);
    }


    @Test
    @Transactional
    public void checkServiceOutletCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletRepository.findAll().size();
        // set the field null
        serviceOutlet.setServiceOutletCode(null);

        // Create the ServiceOutlet, which fails.
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkServiceOutletDesignationIsRequired() throws Exception {
        int databaseSizeBeforeTest = serviceOutletRepository.findAll().size();
        // set the field null
        serviceOutlet.setServiceOutletDesignation(null);

        // Create the ServiceOutlet, which fails.
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        restServiceOutletMockMvc.perform(post("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllServiceOutlets() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletDesignation").value(hasItem(DEFAULT_SERVICE_OUTLET_DESIGNATION.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.toString())));
    }
    
    @Test
    @Transactional
    public void getServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get the serviceOutlet
        restServiceOutletMockMvc.perform(get("/api/service-outlets/{id}", serviceOutlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(serviceOutlet.getId().intValue()))
            .andExpect(jsonPath("$.serviceOutletCode").value(DEFAULT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.serviceOutletDesignation").value(DEFAULT_SERVICE_OUTLET_DESIGNATION.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.toString()));
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode equals to DEFAULT_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldBeFound("serviceOutletCode.equals=" + DEFAULT_SERVICE_OUTLET_CODE);

        // Get all the serviceOutletList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.equals=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode in DEFAULT_SERVICE_OUTLET_CODE or UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldBeFound("serviceOutletCode.in=" + DEFAULT_SERVICE_OUTLET_CODE + "," + UPDATED_SERVICE_OUTLET_CODE);

        // Get all the serviceOutletList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.in=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletCode is not null
        defaultServiceOutletShouldBeFound("serviceOutletCode.specified=true");

        // Get all the serviceOutletList where serviceOutletCode is null
        defaultServiceOutletShouldNotBeFound("serviceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletDesignationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletDesignation equals to DEFAULT_SERVICE_OUTLET_DESIGNATION
        defaultServiceOutletShouldBeFound("serviceOutletDesignation.equals=" + DEFAULT_SERVICE_OUTLET_DESIGNATION);

        // Get all the serviceOutletList where serviceOutletDesignation equals to UPDATED_SERVICE_OUTLET_DESIGNATION
        defaultServiceOutletShouldNotBeFound("serviceOutletDesignation.equals=" + UPDATED_SERVICE_OUTLET_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletDesignationIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletDesignation in DEFAULT_SERVICE_OUTLET_DESIGNATION or UPDATED_SERVICE_OUTLET_DESIGNATION
        defaultServiceOutletShouldBeFound("serviceOutletDesignation.in=" + DEFAULT_SERVICE_OUTLET_DESIGNATION + "," + UPDATED_SERVICE_OUTLET_DESIGNATION);

        // Get all the serviceOutletList where serviceOutletDesignation equals to UPDATED_SERVICE_OUTLET_DESIGNATION
        defaultServiceOutletShouldNotBeFound("serviceOutletDesignation.in=" + UPDATED_SERVICE_OUTLET_DESIGNATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByServiceOutletDesignationIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where serviceOutletDesignation is not null
        defaultServiceOutletShouldBeFound("serviceOutletDesignation.specified=true");

        // Get all the serviceOutletList where serviceOutletDesignation is null
        defaultServiceOutletShouldNotBeFound("serviceOutletDesignation.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where description equals to DEFAULT_DESCRIPTION
        defaultServiceOutletShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the serviceOutletList where description equals to UPDATED_DESCRIPTION
        defaultServiceOutletShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultServiceOutletShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the serviceOutletList where description equals to UPDATED_DESCRIPTION
        defaultServiceOutletShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where description is not null
        defaultServiceOutletShouldBeFound("description.specified=true");

        // Get all the serviceOutletList where description is null
        defaultServiceOutletShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByLocationIsEqualToSomething() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where location equals to DEFAULT_LOCATION
        defaultServiceOutletShouldBeFound("location.equals=" + DEFAULT_LOCATION);

        // Get all the serviceOutletList where location equals to UPDATED_LOCATION
        defaultServiceOutletShouldNotBeFound("location.equals=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByLocationIsInShouldWork() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where location in DEFAULT_LOCATION or UPDATED_LOCATION
        defaultServiceOutletShouldBeFound("location.in=" + DEFAULT_LOCATION + "," + UPDATED_LOCATION);

        // Get all the serviceOutletList where location equals to UPDATED_LOCATION
        defaultServiceOutletShouldNotBeFound("location.in=" + UPDATED_LOCATION);
    }

    @Test
    @Transactional
    public void getAllServiceOutletsByLocationIsNullOrNotNull() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        // Get all the serviceOutletList where location is not null
        defaultServiceOutletShouldBeFound("location.specified=true");

        // Get all the serviceOutletList where location is null
        defaultServiceOutletShouldNotBeFound("location.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultServiceOutletShouldBeFound(String filter) throws Exception {
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].serviceOutletDesignation").value(hasItem(DEFAULT_SERVICE_OUTLET_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));

        // Check, that the count call also returns 1
        restServiceOutletMockMvc.perform(get("/api/service-outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultServiceOutletShouldNotBeFound(String filter) throws Exception {
        restServiceOutletMockMvc.perform(get("/api/service-outlets?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restServiceOutletMockMvc.perform(get("/api/service-outlets/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingServiceOutlet() throws Exception {
        // Get the serviceOutlet
        restServiceOutletMockMvc.perform(get("/api/service-outlets/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        int databaseSizeBeforeUpdate = serviceOutletRepository.findAll().size();

        // Update the serviceOutlet
        ServiceOutlet updatedServiceOutlet = serviceOutletRepository.findById(serviceOutlet.getId()).get();
        // Disconnect from session so that the updates on updatedServiceOutlet are not directly saved in db
        em.detach(updatedServiceOutlet);
        updatedServiceOutlet
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .serviceOutletDesignation(UPDATED_SERVICE_OUTLET_DESIGNATION)
            .description(UPDATED_DESCRIPTION)
            .location(UPDATED_LOCATION);
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(updatedServiceOutlet);

        restServiceOutletMockMvc.perform(put("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isOk());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeUpdate);
        ServiceOutlet testServiceOutlet = serviceOutletList.get(serviceOutletList.size() - 1);
        assertThat(testServiceOutlet.getServiceOutletCode()).isEqualTo(UPDATED_SERVICE_OUTLET_CODE);
        assertThat(testServiceOutlet.getServiceOutletDesignation()).isEqualTo(UPDATED_SERVICE_OUTLET_DESIGNATION);
        assertThat(testServiceOutlet.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testServiceOutlet.getLocation()).isEqualTo(UPDATED_LOCATION);

        // Validate the ServiceOutlet in Elasticsearch
        verify(mockServiceOutletSearchRepository, times(1)).save(testServiceOutlet);
    }

    @Test
    @Transactional
    public void updateNonExistingServiceOutlet() throws Exception {
        int databaseSizeBeforeUpdate = serviceOutletRepository.findAll().size();

        // Create the ServiceOutlet
        ServiceOutletDTO serviceOutletDTO = serviceOutletMapper.toDto(serviceOutlet);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restServiceOutletMockMvc.perform(put("/api/service-outlets")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(serviceOutletDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ServiceOutlet in the database
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ServiceOutlet in Elasticsearch
        verify(mockServiceOutletSearchRepository, times(0)).save(serviceOutlet);
    }

    @Test
    @Transactional
    public void deleteServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);

        int databaseSizeBeforeDelete = serviceOutletRepository.findAll().size();

        // Delete the serviceOutlet
        restServiceOutletMockMvc.perform(delete("/api/service-outlets/{id}", serviceOutlet.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<ServiceOutlet> serviceOutletList = serviceOutletRepository.findAll();
        assertThat(serviceOutletList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ServiceOutlet in Elasticsearch
        verify(mockServiceOutletSearchRepository, times(1)).deleteById(serviceOutlet.getId());
    }

    @Test
    @Transactional
    public void searchServiceOutlet() throws Exception {
        // Initialize the database
        serviceOutletRepository.saveAndFlush(serviceOutlet);
        when(mockServiceOutletSearchRepository.search(queryStringQuery("id:" + serviceOutlet.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(serviceOutlet), PageRequest.of(0, 1), 1));
        // Search the serviceOutlet
        restServiceOutletMockMvc.perform(get("/api/_search/service-outlets?query=id:" + serviceOutlet.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(serviceOutlet.getId().intValue())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].serviceOutletDesignation").value(hasItem(DEFAULT_SERVICE_OUTLET_DESIGNATION)))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutlet.class);
        ServiceOutlet serviceOutlet1 = new ServiceOutlet();
        serviceOutlet1.setId(1L);
        ServiceOutlet serviceOutlet2 = new ServiceOutlet();
        serviceOutlet2.setId(serviceOutlet1.getId());
        assertThat(serviceOutlet1).isEqualTo(serviceOutlet2);
        serviceOutlet2.setId(2L);
        assertThat(serviceOutlet1).isNotEqualTo(serviceOutlet2);
        serviceOutlet1.setId(null);
        assertThat(serviceOutlet1).isNotEqualTo(serviceOutlet2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ServiceOutletDTO.class);
        ServiceOutletDTO serviceOutletDTO1 = new ServiceOutletDTO();
        serviceOutletDTO1.setId(1L);
        ServiceOutletDTO serviceOutletDTO2 = new ServiceOutletDTO();
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
        serviceOutletDTO2.setId(serviceOutletDTO1.getId());
        assertThat(serviceOutletDTO1).isEqualTo(serviceOutletDTO2);
        serviceOutletDTO2.setId(2L);
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
        serviceOutletDTO1.setId(null);
        assertThat(serviceOutletDTO1).isNotEqualTo(serviceOutletDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(serviceOutletMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(serviceOutletMapper.fromId(null)).isNull();
    }
}
