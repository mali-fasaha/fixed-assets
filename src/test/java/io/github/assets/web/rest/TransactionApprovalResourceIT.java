package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.TransactionApproval;
import io.github.assets.repository.TransactionApprovalRepository;
import io.github.assets.repository.search.TransactionApprovalSearchRepository;
import io.github.assets.service.TransactionApprovalService;
import io.github.assets.service.dto.TransactionApprovalDTO;
import io.github.assets.service.mapper.TransactionApprovalMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.TransactionApprovalCriteria;
import io.github.assets.service.TransactionApprovalQueryService;

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
 * Integration tests for the {@Link TransactionApprovalResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class TransactionApprovalResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_REQUESTED_BY = 1L;
    private static final Long UPDATED_REQUESTED_BY = 2L;

    private static final String DEFAULT_RECOMMENDED_BY = "AAAAAAAAAA";
    private static final String UPDATED_RECOMMENDED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_REVIEWED_BY = "AAAAAAAAAA";
    private static final String UPDATED_REVIEWED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_FIRST_APPROVER = "AAAAAAAAAA";
    private static final String UPDATED_FIRST_APPROVER = "BBBBBBBBBB";

    private static final String DEFAULT_SECOND_APPROVER = "AAAAAAAAAA";
    private static final String UPDATED_SECOND_APPROVER = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_APPROVER = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_APPROVER = "BBBBBBBBBB";

    private static final String DEFAULT_FOURTH_APPROVER = "AAAAAAAAAA";
    private static final String UPDATED_FOURTH_APPROVER = "BBBBBBBBBB";

    @Autowired
    private TransactionApprovalRepository transactionApprovalRepository;

    @Autowired
    private TransactionApprovalMapper transactionApprovalMapper;

    @Autowired
    private TransactionApprovalService transactionApprovalService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.TransactionApprovalSearchRepositoryMockConfiguration
     */
    @Autowired
    private TransactionApprovalSearchRepository mockTransactionApprovalSearchRepository;

    @Autowired
    private TransactionApprovalQueryService transactionApprovalQueryService;

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

    private MockMvc restTransactionApprovalMockMvc;

    private TransactionApproval transactionApproval;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final TransactionApprovalResource transactionApprovalResource = new TransactionApprovalResource(transactionApprovalService, transactionApprovalQueryService);
        this.restTransactionApprovalMockMvc = MockMvcBuilders.standaloneSetup(transactionApprovalResource)
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
    public static TransactionApproval createEntity(EntityManager em) {
        TransactionApproval transactionApproval = new TransactionApproval()
            .description(DEFAULT_DESCRIPTION)
            .requestedBy(DEFAULT_REQUESTED_BY)
            .recommendedBy(DEFAULT_RECOMMENDED_BY)
            .reviewedBy(DEFAULT_REVIEWED_BY)
            .firstApprover(DEFAULT_FIRST_APPROVER)
            .secondApprover(DEFAULT_SECOND_APPROVER)
            .thirdApprover(DEFAULT_THIRD_APPROVER)
            .fourthApprover(DEFAULT_FOURTH_APPROVER);
        return transactionApproval;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TransactionApproval createUpdatedEntity(EntityManager em) {
        TransactionApproval transactionApproval = new TransactionApproval()
            .description(UPDATED_DESCRIPTION)
            .requestedBy(UPDATED_REQUESTED_BY)
            .recommendedBy(UPDATED_RECOMMENDED_BY)
            .reviewedBy(UPDATED_REVIEWED_BY)
            .firstApprover(UPDATED_FIRST_APPROVER)
            .secondApprover(UPDATED_SECOND_APPROVER)
            .thirdApprover(UPDATED_THIRD_APPROVER)
            .fourthApprover(UPDATED_FOURTH_APPROVER);
        return transactionApproval;
    }

    @BeforeEach
    public void initTest() {
        transactionApproval = createEntity(em);
    }

    @Test
    @Transactional
    public void createTransactionApproval() throws Exception {
        int databaseSizeBeforeCreate = transactionApprovalRepository.findAll().size();

        // Create the TransactionApproval
        TransactionApprovalDTO transactionApprovalDTO = transactionApprovalMapper.toDto(transactionApproval);
        restTransactionApprovalMockMvc.perform(post("/api/transaction-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionApprovalDTO)))
            .andExpect(status().isCreated());

        // Validate the TransactionApproval in the database
        List<TransactionApproval> transactionApprovalList = transactionApprovalRepository.findAll();
        assertThat(transactionApprovalList).hasSize(databaseSizeBeforeCreate + 1);
        TransactionApproval testTransactionApproval = transactionApprovalList.get(transactionApprovalList.size() - 1);
        assertThat(testTransactionApproval.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testTransactionApproval.getRequestedBy()).isEqualTo(DEFAULT_REQUESTED_BY);
        assertThat(testTransactionApproval.getRecommendedBy()).isEqualTo(DEFAULT_RECOMMENDED_BY);
        assertThat(testTransactionApproval.getReviewedBy()).isEqualTo(DEFAULT_REVIEWED_BY);
        assertThat(testTransactionApproval.getFirstApprover()).isEqualTo(DEFAULT_FIRST_APPROVER);
        assertThat(testTransactionApproval.getSecondApprover()).isEqualTo(DEFAULT_SECOND_APPROVER);
        assertThat(testTransactionApproval.getThirdApprover()).isEqualTo(DEFAULT_THIRD_APPROVER);
        assertThat(testTransactionApproval.getFourthApprover()).isEqualTo(DEFAULT_FOURTH_APPROVER);

        // Validate the TransactionApproval in Elasticsearch
        verify(mockTransactionApprovalSearchRepository, times(1)).save(testTransactionApproval);
    }

    @Test
    @Transactional
    public void createTransactionApprovalWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = transactionApprovalRepository.findAll().size();

        // Create the TransactionApproval with an existing ID
        transactionApproval.setId(1L);
        TransactionApprovalDTO transactionApprovalDTO = transactionApprovalMapper.toDto(transactionApproval);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTransactionApprovalMockMvc.perform(post("/api/transaction-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionApproval in the database
        List<TransactionApproval> transactionApprovalList = transactionApprovalRepository.findAll();
        assertThat(transactionApprovalList).hasSize(databaseSizeBeforeCreate);

        // Validate the TransactionApproval in Elasticsearch
        verify(mockTransactionApprovalSearchRepository, times(0)).save(transactionApproval);
    }


    @Test
    @Transactional
    public void getAllTransactionApprovals() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY.intValue())))
            .andExpect(jsonPath("$.[*].recommendedBy").value(hasItem(DEFAULT_RECOMMENDED_BY.toString())))
            .andExpect(jsonPath("$.[*].reviewedBy").value(hasItem(DEFAULT_REVIEWED_BY.toString())))
            .andExpect(jsonPath("$.[*].firstApprover").value(hasItem(DEFAULT_FIRST_APPROVER.toString())))
            .andExpect(jsonPath("$.[*].secondApprover").value(hasItem(DEFAULT_SECOND_APPROVER.toString())))
            .andExpect(jsonPath("$.[*].thirdApprover").value(hasItem(DEFAULT_THIRD_APPROVER.toString())))
            .andExpect(jsonPath("$.[*].fourthApprover").value(hasItem(DEFAULT_FOURTH_APPROVER.toString())));
    }
    
    @Test
    @Transactional
    public void getTransactionApproval() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get the transactionApproval
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals/{id}", transactionApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(transactionApproval.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.requestedBy").value(DEFAULT_REQUESTED_BY.intValue()))
            .andExpect(jsonPath("$.recommendedBy").value(DEFAULT_RECOMMENDED_BY.toString()))
            .andExpect(jsonPath("$.reviewedBy").value(DEFAULT_REVIEWED_BY.toString()))
            .andExpect(jsonPath("$.firstApprover").value(DEFAULT_FIRST_APPROVER.toString()))
            .andExpect(jsonPath("$.secondApprover").value(DEFAULT_SECOND_APPROVER.toString()))
            .andExpect(jsonPath("$.thirdApprover").value(DEFAULT_THIRD_APPROVER.toString()))
            .andExpect(jsonPath("$.fourthApprover").value(DEFAULT_FOURTH_APPROVER.toString()));
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where description equals to DEFAULT_DESCRIPTION
        defaultTransactionApprovalShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the transactionApprovalList where description equals to UPDATED_DESCRIPTION
        defaultTransactionApprovalShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultTransactionApprovalShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the transactionApprovalList where description equals to UPDATED_DESCRIPTION
        defaultTransactionApprovalShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where description is not null
        defaultTransactionApprovalShouldBeFound("description.specified=true");

        // Get all the transactionApprovalList where description is null
        defaultTransactionApprovalShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRequestedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where requestedBy equals to DEFAULT_REQUESTED_BY
        defaultTransactionApprovalShouldBeFound("requestedBy.equals=" + DEFAULT_REQUESTED_BY);

        // Get all the transactionApprovalList where requestedBy equals to UPDATED_REQUESTED_BY
        defaultTransactionApprovalShouldNotBeFound("requestedBy.equals=" + UPDATED_REQUESTED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRequestedByIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where requestedBy in DEFAULT_REQUESTED_BY or UPDATED_REQUESTED_BY
        defaultTransactionApprovalShouldBeFound("requestedBy.in=" + DEFAULT_REQUESTED_BY + "," + UPDATED_REQUESTED_BY);

        // Get all the transactionApprovalList where requestedBy equals to UPDATED_REQUESTED_BY
        defaultTransactionApprovalShouldNotBeFound("requestedBy.in=" + UPDATED_REQUESTED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRequestedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where requestedBy is not null
        defaultTransactionApprovalShouldBeFound("requestedBy.specified=true");

        // Get all the transactionApprovalList where requestedBy is null
        defaultTransactionApprovalShouldNotBeFound("requestedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRequestedByIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where requestedBy greater than or equals to DEFAULT_REQUESTED_BY
        defaultTransactionApprovalShouldBeFound("requestedBy.greaterOrEqualThan=" + DEFAULT_REQUESTED_BY);

        // Get all the transactionApprovalList where requestedBy greater than or equals to UPDATED_REQUESTED_BY
        defaultTransactionApprovalShouldNotBeFound("requestedBy.greaterOrEqualThan=" + UPDATED_REQUESTED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRequestedByIsLessThanSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where requestedBy less than or equals to DEFAULT_REQUESTED_BY
        defaultTransactionApprovalShouldNotBeFound("requestedBy.lessThan=" + DEFAULT_REQUESTED_BY);

        // Get all the transactionApprovalList where requestedBy less than or equals to UPDATED_REQUESTED_BY
        defaultTransactionApprovalShouldBeFound("requestedBy.lessThan=" + UPDATED_REQUESTED_BY);
    }


    @Test
    @Transactional
    public void getAllTransactionApprovalsByRecommendedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where recommendedBy equals to DEFAULT_RECOMMENDED_BY
        defaultTransactionApprovalShouldBeFound("recommendedBy.equals=" + DEFAULT_RECOMMENDED_BY);

        // Get all the transactionApprovalList where recommendedBy equals to UPDATED_RECOMMENDED_BY
        defaultTransactionApprovalShouldNotBeFound("recommendedBy.equals=" + UPDATED_RECOMMENDED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRecommendedByIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where recommendedBy in DEFAULT_RECOMMENDED_BY or UPDATED_RECOMMENDED_BY
        defaultTransactionApprovalShouldBeFound("recommendedBy.in=" + DEFAULT_RECOMMENDED_BY + "," + UPDATED_RECOMMENDED_BY);

        // Get all the transactionApprovalList where recommendedBy equals to UPDATED_RECOMMENDED_BY
        defaultTransactionApprovalShouldNotBeFound("recommendedBy.in=" + UPDATED_RECOMMENDED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByRecommendedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where recommendedBy is not null
        defaultTransactionApprovalShouldBeFound("recommendedBy.specified=true");

        // Get all the transactionApprovalList where recommendedBy is null
        defaultTransactionApprovalShouldNotBeFound("recommendedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByReviewedByIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where reviewedBy equals to DEFAULT_REVIEWED_BY
        defaultTransactionApprovalShouldBeFound("reviewedBy.equals=" + DEFAULT_REVIEWED_BY);

        // Get all the transactionApprovalList where reviewedBy equals to UPDATED_REVIEWED_BY
        defaultTransactionApprovalShouldNotBeFound("reviewedBy.equals=" + UPDATED_REVIEWED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByReviewedByIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where reviewedBy in DEFAULT_REVIEWED_BY or UPDATED_REVIEWED_BY
        defaultTransactionApprovalShouldBeFound("reviewedBy.in=" + DEFAULT_REVIEWED_BY + "," + UPDATED_REVIEWED_BY);

        // Get all the transactionApprovalList where reviewedBy equals to UPDATED_REVIEWED_BY
        defaultTransactionApprovalShouldNotBeFound("reviewedBy.in=" + UPDATED_REVIEWED_BY);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByReviewedByIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where reviewedBy is not null
        defaultTransactionApprovalShouldBeFound("reviewedBy.specified=true");

        // Get all the transactionApprovalList where reviewedBy is null
        defaultTransactionApprovalShouldNotBeFound("reviewedBy.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFirstApproverIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where firstApprover equals to DEFAULT_FIRST_APPROVER
        defaultTransactionApprovalShouldBeFound("firstApprover.equals=" + DEFAULT_FIRST_APPROVER);

        // Get all the transactionApprovalList where firstApprover equals to UPDATED_FIRST_APPROVER
        defaultTransactionApprovalShouldNotBeFound("firstApprover.equals=" + UPDATED_FIRST_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFirstApproverIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where firstApprover in DEFAULT_FIRST_APPROVER or UPDATED_FIRST_APPROVER
        defaultTransactionApprovalShouldBeFound("firstApprover.in=" + DEFAULT_FIRST_APPROVER + "," + UPDATED_FIRST_APPROVER);

        // Get all the transactionApprovalList where firstApprover equals to UPDATED_FIRST_APPROVER
        defaultTransactionApprovalShouldNotBeFound("firstApprover.in=" + UPDATED_FIRST_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFirstApproverIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where firstApprover is not null
        defaultTransactionApprovalShouldBeFound("firstApprover.specified=true");

        // Get all the transactionApprovalList where firstApprover is null
        defaultTransactionApprovalShouldNotBeFound("firstApprover.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsBySecondApproverIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where secondApprover equals to DEFAULT_SECOND_APPROVER
        defaultTransactionApprovalShouldBeFound("secondApprover.equals=" + DEFAULT_SECOND_APPROVER);

        // Get all the transactionApprovalList where secondApprover equals to UPDATED_SECOND_APPROVER
        defaultTransactionApprovalShouldNotBeFound("secondApprover.equals=" + UPDATED_SECOND_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsBySecondApproverIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where secondApprover in DEFAULT_SECOND_APPROVER or UPDATED_SECOND_APPROVER
        defaultTransactionApprovalShouldBeFound("secondApprover.in=" + DEFAULT_SECOND_APPROVER + "," + UPDATED_SECOND_APPROVER);

        // Get all the transactionApprovalList where secondApprover equals to UPDATED_SECOND_APPROVER
        defaultTransactionApprovalShouldNotBeFound("secondApprover.in=" + UPDATED_SECOND_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsBySecondApproverIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where secondApprover is not null
        defaultTransactionApprovalShouldBeFound("secondApprover.specified=true");

        // Get all the transactionApprovalList where secondApprover is null
        defaultTransactionApprovalShouldNotBeFound("secondApprover.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByThirdApproverIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where thirdApprover equals to DEFAULT_THIRD_APPROVER
        defaultTransactionApprovalShouldBeFound("thirdApprover.equals=" + DEFAULT_THIRD_APPROVER);

        // Get all the transactionApprovalList where thirdApprover equals to UPDATED_THIRD_APPROVER
        defaultTransactionApprovalShouldNotBeFound("thirdApprover.equals=" + UPDATED_THIRD_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByThirdApproverIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where thirdApprover in DEFAULT_THIRD_APPROVER or UPDATED_THIRD_APPROVER
        defaultTransactionApprovalShouldBeFound("thirdApprover.in=" + DEFAULT_THIRD_APPROVER + "," + UPDATED_THIRD_APPROVER);

        // Get all the transactionApprovalList where thirdApprover equals to UPDATED_THIRD_APPROVER
        defaultTransactionApprovalShouldNotBeFound("thirdApprover.in=" + UPDATED_THIRD_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByThirdApproverIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where thirdApprover is not null
        defaultTransactionApprovalShouldBeFound("thirdApprover.specified=true");

        // Get all the transactionApprovalList where thirdApprover is null
        defaultTransactionApprovalShouldNotBeFound("thirdApprover.specified=false");
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFourthApproverIsEqualToSomething() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where fourthApprover equals to DEFAULT_FOURTH_APPROVER
        defaultTransactionApprovalShouldBeFound("fourthApprover.equals=" + DEFAULT_FOURTH_APPROVER);

        // Get all the transactionApprovalList where fourthApprover equals to UPDATED_FOURTH_APPROVER
        defaultTransactionApprovalShouldNotBeFound("fourthApprover.equals=" + UPDATED_FOURTH_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFourthApproverIsInShouldWork() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where fourthApprover in DEFAULT_FOURTH_APPROVER or UPDATED_FOURTH_APPROVER
        defaultTransactionApprovalShouldBeFound("fourthApprover.in=" + DEFAULT_FOURTH_APPROVER + "," + UPDATED_FOURTH_APPROVER);

        // Get all the transactionApprovalList where fourthApprover equals to UPDATED_FOURTH_APPROVER
        defaultTransactionApprovalShouldNotBeFound("fourthApprover.in=" + UPDATED_FOURTH_APPROVER);
    }

    @Test
    @Transactional
    public void getAllTransactionApprovalsByFourthApproverIsNullOrNotNull() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        // Get all the transactionApprovalList where fourthApprover is not null
        defaultTransactionApprovalShouldBeFound("fourthApprover.specified=true");

        // Get all the transactionApprovalList where fourthApprover is null
        defaultTransactionApprovalShouldNotBeFound("fourthApprover.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultTransactionApprovalShouldBeFound(String filter) throws Exception {
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY.intValue())))
            .andExpect(jsonPath("$.[*].recommendedBy").value(hasItem(DEFAULT_RECOMMENDED_BY)))
            .andExpect(jsonPath("$.[*].reviewedBy").value(hasItem(DEFAULT_REVIEWED_BY)))
            .andExpect(jsonPath("$.[*].firstApprover").value(hasItem(DEFAULT_FIRST_APPROVER)))
            .andExpect(jsonPath("$.[*].secondApprover").value(hasItem(DEFAULT_SECOND_APPROVER)))
            .andExpect(jsonPath("$.[*].thirdApprover").value(hasItem(DEFAULT_THIRD_APPROVER)))
            .andExpect(jsonPath("$.[*].fourthApprover").value(hasItem(DEFAULT_FOURTH_APPROVER)));

        // Check, that the count call also returns 1
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultTransactionApprovalShouldNotBeFound(String filter) throws Exception {
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingTransactionApproval() throws Exception {
        // Get the transactionApproval
        restTransactionApprovalMockMvc.perform(get("/api/transaction-approvals/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionApproval() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        int databaseSizeBeforeUpdate = transactionApprovalRepository.findAll().size();

        // Update the transactionApproval
        TransactionApproval updatedTransactionApproval = transactionApprovalRepository.findById(transactionApproval.getId()).get();
        // Disconnect from session so that the updates on updatedTransactionApproval are not directly saved in db
        em.detach(updatedTransactionApproval);
        updatedTransactionApproval
            .description(UPDATED_DESCRIPTION)
            .requestedBy(UPDATED_REQUESTED_BY)
            .recommendedBy(UPDATED_RECOMMENDED_BY)
            .reviewedBy(UPDATED_REVIEWED_BY)
            .firstApprover(UPDATED_FIRST_APPROVER)
            .secondApprover(UPDATED_SECOND_APPROVER)
            .thirdApprover(UPDATED_THIRD_APPROVER)
            .fourthApprover(UPDATED_FOURTH_APPROVER);
        TransactionApprovalDTO transactionApprovalDTO = transactionApprovalMapper.toDto(updatedTransactionApproval);

        restTransactionApprovalMockMvc.perform(put("/api/transaction-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionApprovalDTO)))
            .andExpect(status().isOk());

        // Validate the TransactionApproval in the database
        List<TransactionApproval> transactionApprovalList = transactionApprovalRepository.findAll();
        assertThat(transactionApprovalList).hasSize(databaseSizeBeforeUpdate);
        TransactionApproval testTransactionApproval = transactionApprovalList.get(transactionApprovalList.size() - 1);
        assertThat(testTransactionApproval.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testTransactionApproval.getRequestedBy()).isEqualTo(UPDATED_REQUESTED_BY);
        assertThat(testTransactionApproval.getRecommendedBy()).isEqualTo(UPDATED_RECOMMENDED_BY);
        assertThat(testTransactionApproval.getReviewedBy()).isEqualTo(UPDATED_REVIEWED_BY);
        assertThat(testTransactionApproval.getFirstApprover()).isEqualTo(UPDATED_FIRST_APPROVER);
        assertThat(testTransactionApproval.getSecondApprover()).isEqualTo(UPDATED_SECOND_APPROVER);
        assertThat(testTransactionApproval.getThirdApprover()).isEqualTo(UPDATED_THIRD_APPROVER);
        assertThat(testTransactionApproval.getFourthApprover()).isEqualTo(UPDATED_FOURTH_APPROVER);

        // Validate the TransactionApproval in Elasticsearch
        verify(mockTransactionApprovalSearchRepository, times(1)).save(testTransactionApproval);
    }

    @Test
    @Transactional
    public void updateNonExistingTransactionApproval() throws Exception {
        int databaseSizeBeforeUpdate = transactionApprovalRepository.findAll().size();

        // Create the TransactionApproval
        TransactionApprovalDTO transactionApprovalDTO = transactionApprovalMapper.toDto(transactionApproval);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restTransactionApprovalMockMvc.perform(put("/api/transaction-approvals")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(transactionApprovalDTO)))
            .andExpect(status().isBadRequest());

        // Validate the TransactionApproval in the database
        List<TransactionApproval> transactionApprovalList = transactionApprovalRepository.findAll();
        assertThat(transactionApprovalList).hasSize(databaseSizeBeforeUpdate);

        // Validate the TransactionApproval in Elasticsearch
        verify(mockTransactionApprovalSearchRepository, times(0)).save(transactionApproval);
    }

    @Test
    @Transactional
    public void deleteTransactionApproval() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);

        int databaseSizeBeforeDelete = transactionApprovalRepository.findAll().size();

        // Delete the transactionApproval
        restTransactionApprovalMockMvc.perform(delete("/api/transaction-approvals/{id}", transactionApproval.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<TransactionApproval> transactionApprovalList = transactionApprovalRepository.findAll();
        assertThat(transactionApprovalList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the TransactionApproval in Elasticsearch
        verify(mockTransactionApprovalSearchRepository, times(1)).deleteById(transactionApproval.getId());
    }

    @Test
    @Transactional
    public void searchTransactionApproval() throws Exception {
        // Initialize the database
        transactionApprovalRepository.saveAndFlush(transactionApproval);
        when(mockTransactionApprovalSearchRepository.search(queryStringQuery("id:" + transactionApproval.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(transactionApproval), PageRequest.of(0, 1), 1));
        // Search the transactionApproval
        restTransactionApprovalMockMvc.perform(get("/api/_search/transaction-approvals?query=id:" + transactionApproval.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(transactionApproval.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].requestedBy").value(hasItem(DEFAULT_REQUESTED_BY.intValue())))
            .andExpect(jsonPath("$.[*].recommendedBy").value(hasItem(DEFAULT_RECOMMENDED_BY)))
            .andExpect(jsonPath("$.[*].reviewedBy").value(hasItem(DEFAULT_REVIEWED_BY)))
            .andExpect(jsonPath("$.[*].firstApprover").value(hasItem(DEFAULT_FIRST_APPROVER)))
            .andExpect(jsonPath("$.[*].secondApprover").value(hasItem(DEFAULT_SECOND_APPROVER)))
            .andExpect(jsonPath("$.[*].thirdApprover").value(hasItem(DEFAULT_THIRD_APPROVER)))
            .andExpect(jsonPath("$.[*].fourthApprover").value(hasItem(DEFAULT_FOURTH_APPROVER)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionApproval.class);
        TransactionApproval transactionApproval1 = new TransactionApproval();
        transactionApproval1.setId(1L);
        TransactionApproval transactionApproval2 = new TransactionApproval();
        transactionApproval2.setId(transactionApproval1.getId());
        assertThat(transactionApproval1).isEqualTo(transactionApproval2);
        transactionApproval2.setId(2L);
        assertThat(transactionApproval1).isNotEqualTo(transactionApproval2);
        transactionApproval1.setId(null);
        assertThat(transactionApproval1).isNotEqualTo(transactionApproval2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(TransactionApprovalDTO.class);
        TransactionApprovalDTO transactionApprovalDTO1 = new TransactionApprovalDTO();
        transactionApprovalDTO1.setId(1L);
        TransactionApprovalDTO transactionApprovalDTO2 = new TransactionApprovalDTO();
        assertThat(transactionApprovalDTO1).isNotEqualTo(transactionApprovalDTO2);
        transactionApprovalDTO2.setId(transactionApprovalDTO1.getId());
        assertThat(transactionApprovalDTO1).isEqualTo(transactionApprovalDTO2);
        transactionApprovalDTO2.setId(2L);
        assertThat(transactionApprovalDTO1).isNotEqualTo(transactionApprovalDTO2);
        transactionApprovalDTO1.setId(null);
        assertThat(transactionApprovalDTO1).isNotEqualTo(transactionApprovalDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(transactionApprovalMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(transactionApprovalMapper.fromId(null)).isNull();
    }
}
