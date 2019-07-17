package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.FixedAssetInvoice;
import io.github.assets.domain.Dealer;
import io.github.assets.repository.FixedAssetInvoiceRepository;
import io.github.assets.repository.search.FixedAssetInvoiceSearchRepository;
import io.github.assets.service.FixedAssetInvoiceService;
import io.github.assets.service.dto.FixedAssetInvoiceDTO;
import io.github.assets.service.mapper.FixedAssetInvoiceMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.FixedAssetInvoiceCriteria;
import io.github.assets.service.FixedAssetInvoiceQueryService;

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
 * Integration tests for the {@Link FixedAssetInvoiceResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class FixedAssetInvoiceResourceIT {

    private static final String DEFAULT_INVOICE_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_REFERENCE = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_INVOICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_INVOICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final BigDecimal DEFAULT_INVOICE_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_INVOICE_AMOUNT = new BigDecimal(2);

    private static final Boolean DEFAULT_IS_PROFORMA = false;
    private static final Boolean UPDATED_IS_PROFORMA = true;

    private static final Boolean DEFAULT_IS_CREDIT_NOTE = false;
    private static final Boolean UPDATED_IS_CREDIT_NOTE = true;

    private static final byte[] DEFAULT_ATTACHMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_ATTACHMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_ATTACHMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_ATTACHMENTS_CONTENT_TYPE = "image/png";

    @Autowired
    private FixedAssetInvoiceRepository fixedAssetInvoiceRepository;

    @Autowired
    private FixedAssetInvoiceMapper fixedAssetInvoiceMapper;

    @Autowired
    private FixedAssetInvoiceService fixedAssetInvoiceService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.FixedAssetInvoiceSearchRepositoryMockConfiguration
     */
    @Autowired
    private FixedAssetInvoiceSearchRepository mockFixedAssetInvoiceSearchRepository;

    @Autowired
    private FixedAssetInvoiceQueryService fixedAssetInvoiceQueryService;

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

    private MockMvc restFixedAssetInvoiceMockMvc;

    private FixedAssetInvoice fixedAssetInvoice;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FixedAssetInvoiceResource fixedAssetInvoiceResource = new FixedAssetInvoiceResource(fixedAssetInvoiceService, fixedAssetInvoiceQueryService);
        this.restFixedAssetInvoiceMockMvc = MockMvcBuilders.standaloneSetup(fixedAssetInvoiceResource)
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
    public static FixedAssetInvoice createEntity(EntityManager em) {
        FixedAssetInvoice fixedAssetInvoice = new FixedAssetInvoice()
            .invoiceReference(DEFAULT_INVOICE_REFERENCE)
            .invoiceDate(DEFAULT_INVOICE_DATE)
            .invoiceAmount(DEFAULT_INVOICE_AMOUNT)
            .isProforma(DEFAULT_IS_PROFORMA)
            .isCreditNote(DEFAULT_IS_CREDIT_NOTE)
            .attachments(DEFAULT_ATTACHMENTS)
            .attachmentsContentType(DEFAULT_ATTACHMENTS_CONTENT_TYPE);
        return fixedAssetInvoice;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FixedAssetInvoice createUpdatedEntity(EntityManager em) {
        FixedAssetInvoice fixedAssetInvoice = new FixedAssetInvoice()
            .invoiceReference(UPDATED_INVOICE_REFERENCE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceAmount(UPDATED_INVOICE_AMOUNT)
            .isProforma(UPDATED_IS_PROFORMA)
            .isCreditNote(UPDATED_IS_CREDIT_NOTE)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        return fixedAssetInvoice;
    }

    @BeforeEach
    public void initTest() {
        fixedAssetInvoice = createEntity(em);
    }

    @Test
    @Transactional
    public void createFixedAssetInvoice() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetInvoiceRepository.findAll().size();

        // Create the FixedAssetInvoice
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = fixedAssetInvoiceMapper.toDto(fixedAssetInvoice);
        restFixedAssetInvoiceMockMvc.perform(post("/api/fixed-asset-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetInvoiceDTO)))
            .andExpect(status().isCreated());

        // Validate the FixedAssetInvoice in the database
        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeCreate + 1);
        FixedAssetInvoice testFixedAssetInvoice = fixedAssetInvoiceList.get(fixedAssetInvoiceList.size() - 1);
        assertThat(testFixedAssetInvoice.getInvoiceReference()).isEqualTo(DEFAULT_INVOICE_REFERENCE);
        assertThat(testFixedAssetInvoice.getInvoiceDate()).isEqualTo(DEFAULT_INVOICE_DATE);
        assertThat(testFixedAssetInvoice.getInvoiceAmount()).isEqualTo(DEFAULT_INVOICE_AMOUNT);
        assertThat(testFixedAssetInvoice.isIsProforma()).isEqualTo(DEFAULT_IS_PROFORMA);
        assertThat(testFixedAssetInvoice.isIsCreditNote()).isEqualTo(DEFAULT_IS_CREDIT_NOTE);
        assertThat(testFixedAssetInvoice.getAttachments()).isEqualTo(DEFAULT_ATTACHMENTS);
        assertThat(testFixedAssetInvoice.getAttachmentsContentType()).isEqualTo(DEFAULT_ATTACHMENTS_CONTENT_TYPE);

        // Validate the FixedAssetInvoice in Elasticsearch
        verify(mockFixedAssetInvoiceSearchRepository, times(1)).save(testFixedAssetInvoice);
    }

    @Test
    @Transactional
    public void createFixedAssetInvoiceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fixedAssetInvoiceRepository.findAll().size();

        // Create the FixedAssetInvoice with an existing ID
        fixedAssetInvoice.setId(1L);
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = fixedAssetInvoiceMapper.toDto(fixedAssetInvoice);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFixedAssetInvoiceMockMvc.perform(post("/api/fixed-asset-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetInvoice in the database
        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeCreate);

        // Validate the FixedAssetInvoice in Elasticsearch
        verify(mockFixedAssetInvoiceSearchRepository, times(0)).save(fixedAssetInvoice);
    }


    @Test
    @Transactional
    public void checkInvoiceReferenceIsRequired() throws Exception {
        int databaseSizeBeforeTest = fixedAssetInvoiceRepository.findAll().size();
        // set the field null
        fixedAssetInvoice.setInvoiceReference(null);

        // Create the FixedAssetInvoice, which fails.
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = fixedAssetInvoiceMapper.toDto(fixedAssetInvoice);

        restFixedAssetInvoiceMockMvc.perform(post("/api/fixed-asset-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetInvoiceDTO)))
            .andExpect(status().isBadRequest());

        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoices() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceReference").value(hasItem(DEFAULT_INVOICE_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceAmount").value(hasItem(DEFAULT_INVOICE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].isProforma").value(hasItem(DEFAULT_IS_PROFORMA.booleanValue())))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))));
    }
    
    @Test
    @Transactional
    public void getFixedAssetInvoice() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get the fixedAssetInvoice
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices/{id}", fixedAssetInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fixedAssetInvoice.getId().intValue()))
            .andExpect(jsonPath("$.invoiceReference").value(DEFAULT_INVOICE_REFERENCE.toString()))
            .andExpect(jsonPath("$.invoiceDate").value(DEFAULT_INVOICE_DATE.toString()))
            .andExpect(jsonPath("$.invoiceAmount").value(DEFAULT_INVOICE_AMOUNT.intValue()))
            .andExpect(jsonPath("$.isProforma").value(DEFAULT_IS_PROFORMA.booleanValue()))
            .andExpect(jsonPath("$.isCreditNote").value(DEFAULT_IS_CREDIT_NOTE.booleanValue()))
            .andExpect(jsonPath("$.attachmentsContentType").value(DEFAULT_ATTACHMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.attachments").value(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS)));
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceReferenceIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceReference equals to DEFAULT_INVOICE_REFERENCE
        defaultFixedAssetInvoiceShouldBeFound("invoiceReference.equals=" + DEFAULT_INVOICE_REFERENCE);

        // Get all the fixedAssetInvoiceList where invoiceReference equals to UPDATED_INVOICE_REFERENCE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceReference.equals=" + UPDATED_INVOICE_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceReferenceIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceReference in DEFAULT_INVOICE_REFERENCE or UPDATED_INVOICE_REFERENCE
        defaultFixedAssetInvoiceShouldBeFound("invoiceReference.in=" + DEFAULT_INVOICE_REFERENCE + "," + UPDATED_INVOICE_REFERENCE);

        // Get all the fixedAssetInvoiceList where invoiceReference equals to UPDATED_INVOICE_REFERENCE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceReference.in=" + UPDATED_INVOICE_REFERENCE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceReferenceIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceReference is not null
        defaultFixedAssetInvoiceShouldBeFound("invoiceReference.specified=true");

        // Get all the fixedAssetInvoiceList where invoiceReference is null
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceReference.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceDateIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceDate equals to DEFAULT_INVOICE_DATE
        defaultFixedAssetInvoiceShouldBeFound("invoiceDate.equals=" + DEFAULT_INVOICE_DATE);

        // Get all the fixedAssetInvoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceDate.equals=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceDateIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceDate in DEFAULT_INVOICE_DATE or UPDATED_INVOICE_DATE
        defaultFixedAssetInvoiceShouldBeFound("invoiceDate.in=" + DEFAULT_INVOICE_DATE + "," + UPDATED_INVOICE_DATE);

        // Get all the fixedAssetInvoiceList where invoiceDate equals to UPDATED_INVOICE_DATE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceDate.in=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceDate is not null
        defaultFixedAssetInvoiceShouldBeFound("invoiceDate.specified=true");

        // Get all the fixedAssetInvoiceList where invoiceDate is null
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceDate greater than or equals to DEFAULT_INVOICE_DATE
        defaultFixedAssetInvoiceShouldBeFound("invoiceDate.greaterOrEqualThan=" + DEFAULT_INVOICE_DATE);

        // Get all the fixedAssetInvoiceList where invoiceDate greater than or equals to UPDATED_INVOICE_DATE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceDate.greaterOrEqualThan=" + UPDATED_INVOICE_DATE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceDateIsLessThanSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceDate less than or equals to DEFAULT_INVOICE_DATE
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceDate.lessThan=" + DEFAULT_INVOICE_DATE);

        // Get all the fixedAssetInvoiceList where invoiceDate less than or equals to UPDATED_INVOICE_DATE
        defaultFixedAssetInvoiceShouldBeFound("invoiceDate.lessThan=" + UPDATED_INVOICE_DATE);
    }


    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceAmountIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceAmount equals to DEFAULT_INVOICE_AMOUNT
        defaultFixedAssetInvoiceShouldBeFound("invoiceAmount.equals=" + DEFAULT_INVOICE_AMOUNT);

        // Get all the fixedAssetInvoiceList where invoiceAmount equals to UPDATED_INVOICE_AMOUNT
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceAmount.equals=" + UPDATED_INVOICE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceAmountIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceAmount in DEFAULT_INVOICE_AMOUNT or UPDATED_INVOICE_AMOUNT
        defaultFixedAssetInvoiceShouldBeFound("invoiceAmount.in=" + DEFAULT_INVOICE_AMOUNT + "," + UPDATED_INVOICE_AMOUNT);

        // Get all the fixedAssetInvoiceList where invoiceAmount equals to UPDATED_INVOICE_AMOUNT
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceAmount.in=" + UPDATED_INVOICE_AMOUNT);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByInvoiceAmountIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where invoiceAmount is not null
        defaultFixedAssetInvoiceShouldBeFound("invoiceAmount.specified=true");

        // Get all the fixedAssetInvoiceList where invoiceAmount is null
        defaultFixedAssetInvoiceShouldNotBeFound("invoiceAmount.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsProformaIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isProforma equals to DEFAULT_IS_PROFORMA
        defaultFixedAssetInvoiceShouldBeFound("isProforma.equals=" + DEFAULT_IS_PROFORMA);

        // Get all the fixedAssetInvoiceList where isProforma equals to UPDATED_IS_PROFORMA
        defaultFixedAssetInvoiceShouldNotBeFound("isProforma.equals=" + UPDATED_IS_PROFORMA);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsProformaIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isProforma in DEFAULT_IS_PROFORMA or UPDATED_IS_PROFORMA
        defaultFixedAssetInvoiceShouldBeFound("isProforma.in=" + DEFAULT_IS_PROFORMA + "," + UPDATED_IS_PROFORMA);

        // Get all the fixedAssetInvoiceList where isProforma equals to UPDATED_IS_PROFORMA
        defaultFixedAssetInvoiceShouldNotBeFound("isProforma.in=" + UPDATED_IS_PROFORMA);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsProformaIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isProforma is not null
        defaultFixedAssetInvoiceShouldBeFound("isProforma.specified=true");

        // Get all the fixedAssetInvoiceList where isProforma is null
        defaultFixedAssetInvoiceShouldNotBeFound("isProforma.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsCreditNoteIsEqualToSomething() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isCreditNote equals to DEFAULT_IS_CREDIT_NOTE
        defaultFixedAssetInvoiceShouldBeFound("isCreditNote.equals=" + DEFAULT_IS_CREDIT_NOTE);

        // Get all the fixedAssetInvoiceList where isCreditNote equals to UPDATED_IS_CREDIT_NOTE
        defaultFixedAssetInvoiceShouldNotBeFound("isCreditNote.equals=" + UPDATED_IS_CREDIT_NOTE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsCreditNoteIsInShouldWork() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isCreditNote in DEFAULT_IS_CREDIT_NOTE or UPDATED_IS_CREDIT_NOTE
        defaultFixedAssetInvoiceShouldBeFound("isCreditNote.in=" + DEFAULT_IS_CREDIT_NOTE + "," + UPDATED_IS_CREDIT_NOTE);

        // Get all the fixedAssetInvoiceList where isCreditNote equals to UPDATED_IS_CREDIT_NOTE
        defaultFixedAssetInvoiceShouldNotBeFound("isCreditNote.in=" + UPDATED_IS_CREDIT_NOTE);
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByIsCreditNoteIsNullOrNotNull() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        // Get all the fixedAssetInvoiceList where isCreditNote is not null
        defaultFixedAssetInvoiceShouldBeFound("isCreditNote.specified=true");

        // Get all the fixedAssetInvoiceList where isCreditNote is null
        defaultFixedAssetInvoiceShouldNotBeFound("isCreditNote.specified=false");
    }

    @Test
    @Transactional
    public void getAllFixedAssetInvoicesByDealerIsEqualToSomething() throws Exception {
        // Initialize the database
        Dealer dealer = DealerResourceIT.createEntity(em);
        em.persist(dealer);
        em.flush();
        fixedAssetInvoice.setDealer(dealer);
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);
        Long dealerId = dealer.getId();

        // Get all the fixedAssetInvoiceList where dealer equals to dealerId
        defaultFixedAssetInvoiceShouldBeFound("dealerId.equals=" + dealerId);

        // Get all the fixedAssetInvoiceList where dealer equals to dealerId + 1
        defaultFixedAssetInvoiceShouldNotBeFound("dealerId.equals=" + (dealerId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFixedAssetInvoiceShouldBeFound(String filter) throws Exception {
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceReference").value(hasItem(DEFAULT_INVOICE_REFERENCE)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceAmount").value(hasItem(DEFAULT_INVOICE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].isProforma").value(hasItem(DEFAULT_IS_PROFORMA.booleanValue())))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))));

        // Check, that the count call also returns 1
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFixedAssetInvoiceShouldNotBeFound(String filter) throws Exception {
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFixedAssetInvoice() throws Exception {
        // Get the fixedAssetInvoice
        restFixedAssetInvoiceMockMvc.perform(get("/api/fixed-asset-invoices/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFixedAssetInvoice() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        int databaseSizeBeforeUpdate = fixedAssetInvoiceRepository.findAll().size();

        // Update the fixedAssetInvoice
        FixedAssetInvoice updatedFixedAssetInvoice = fixedAssetInvoiceRepository.findById(fixedAssetInvoice.getId()).get();
        // Disconnect from session so that the updates on updatedFixedAssetInvoice are not directly saved in db
        em.detach(updatedFixedAssetInvoice);
        updatedFixedAssetInvoice
            .invoiceReference(UPDATED_INVOICE_REFERENCE)
            .invoiceDate(UPDATED_INVOICE_DATE)
            .invoiceAmount(UPDATED_INVOICE_AMOUNT)
            .isProforma(UPDATED_IS_PROFORMA)
            .isCreditNote(UPDATED_IS_CREDIT_NOTE)
            .attachments(UPDATED_ATTACHMENTS)
            .attachmentsContentType(UPDATED_ATTACHMENTS_CONTENT_TYPE);
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = fixedAssetInvoiceMapper.toDto(updatedFixedAssetInvoice);

        restFixedAssetInvoiceMockMvc.perform(put("/api/fixed-asset-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetInvoiceDTO)))
            .andExpect(status().isOk());

        // Validate the FixedAssetInvoice in the database
        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeUpdate);
        FixedAssetInvoice testFixedAssetInvoice = fixedAssetInvoiceList.get(fixedAssetInvoiceList.size() - 1);
        assertThat(testFixedAssetInvoice.getInvoiceReference()).isEqualTo(UPDATED_INVOICE_REFERENCE);
        assertThat(testFixedAssetInvoice.getInvoiceDate()).isEqualTo(UPDATED_INVOICE_DATE);
        assertThat(testFixedAssetInvoice.getInvoiceAmount()).isEqualTo(UPDATED_INVOICE_AMOUNT);
        assertThat(testFixedAssetInvoice.isIsProforma()).isEqualTo(UPDATED_IS_PROFORMA);
        assertThat(testFixedAssetInvoice.isIsCreditNote()).isEqualTo(UPDATED_IS_CREDIT_NOTE);
        assertThat(testFixedAssetInvoice.getAttachments()).isEqualTo(UPDATED_ATTACHMENTS);
        assertThat(testFixedAssetInvoice.getAttachmentsContentType()).isEqualTo(UPDATED_ATTACHMENTS_CONTENT_TYPE);

        // Validate the FixedAssetInvoice in Elasticsearch
        verify(mockFixedAssetInvoiceSearchRepository, times(1)).save(testFixedAssetInvoice);
    }

    @Test
    @Transactional
    public void updateNonExistingFixedAssetInvoice() throws Exception {
        int databaseSizeBeforeUpdate = fixedAssetInvoiceRepository.findAll().size();

        // Create the FixedAssetInvoice
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO = fixedAssetInvoiceMapper.toDto(fixedAssetInvoice);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFixedAssetInvoiceMockMvc.perform(put("/api/fixed-asset-invoices")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fixedAssetInvoiceDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FixedAssetInvoice in the database
        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FixedAssetInvoice in Elasticsearch
        verify(mockFixedAssetInvoiceSearchRepository, times(0)).save(fixedAssetInvoice);
    }

    @Test
    @Transactional
    public void deleteFixedAssetInvoice() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);

        int databaseSizeBeforeDelete = fixedAssetInvoiceRepository.findAll().size();

        // Delete the fixedAssetInvoice
        restFixedAssetInvoiceMockMvc.perform(delete("/api/fixed-asset-invoices/{id}", fixedAssetInvoice.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FixedAssetInvoice> fixedAssetInvoiceList = fixedAssetInvoiceRepository.findAll();
        assertThat(fixedAssetInvoiceList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FixedAssetInvoice in Elasticsearch
        verify(mockFixedAssetInvoiceSearchRepository, times(1)).deleteById(fixedAssetInvoice.getId());
    }

    @Test
    @Transactional
    public void searchFixedAssetInvoice() throws Exception {
        // Initialize the database
        fixedAssetInvoiceRepository.saveAndFlush(fixedAssetInvoice);
        when(mockFixedAssetInvoiceSearchRepository.search(queryStringQuery("id:" + fixedAssetInvoice.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fixedAssetInvoice), PageRequest.of(0, 1), 1));
        // Search the fixedAssetInvoice
        restFixedAssetInvoiceMockMvc.perform(get("/api/_search/fixed-asset-invoices?query=id:" + fixedAssetInvoice.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fixedAssetInvoice.getId().intValue())))
            .andExpect(jsonPath("$.[*].invoiceReference").value(hasItem(DEFAULT_INVOICE_REFERENCE)))
            .andExpect(jsonPath("$.[*].invoiceDate").value(hasItem(DEFAULT_INVOICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].invoiceAmount").value(hasItem(DEFAULT_INVOICE_AMOUNT.intValue())))
            .andExpect(jsonPath("$.[*].isProforma").value(hasItem(DEFAULT_IS_PROFORMA.booleanValue())))
            .andExpect(jsonPath("$.[*].isCreditNote").value(hasItem(DEFAULT_IS_CREDIT_NOTE.booleanValue())))
            .andExpect(jsonPath("$.[*].attachmentsContentType").value(hasItem(DEFAULT_ATTACHMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].attachments").value(hasItem(Base64Utils.encodeToString(DEFAULT_ATTACHMENTS))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetInvoice.class);
        FixedAssetInvoice fixedAssetInvoice1 = new FixedAssetInvoice();
        fixedAssetInvoice1.setId(1L);
        FixedAssetInvoice fixedAssetInvoice2 = new FixedAssetInvoice();
        fixedAssetInvoice2.setId(fixedAssetInvoice1.getId());
        assertThat(fixedAssetInvoice1).isEqualTo(fixedAssetInvoice2);
        fixedAssetInvoice2.setId(2L);
        assertThat(fixedAssetInvoice1).isNotEqualTo(fixedAssetInvoice2);
        fixedAssetInvoice1.setId(null);
        assertThat(fixedAssetInvoice1).isNotEqualTo(fixedAssetInvoice2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FixedAssetInvoiceDTO.class);
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO1 = new FixedAssetInvoiceDTO();
        fixedAssetInvoiceDTO1.setId(1L);
        FixedAssetInvoiceDTO fixedAssetInvoiceDTO2 = new FixedAssetInvoiceDTO();
        assertThat(fixedAssetInvoiceDTO1).isNotEqualTo(fixedAssetInvoiceDTO2);
        fixedAssetInvoiceDTO2.setId(fixedAssetInvoiceDTO1.getId());
        assertThat(fixedAssetInvoiceDTO1).isEqualTo(fixedAssetInvoiceDTO2);
        fixedAssetInvoiceDTO2.setId(2L);
        assertThat(fixedAssetInvoiceDTO1).isNotEqualTo(fixedAssetInvoiceDTO2);
        fixedAssetInvoiceDTO1.setId(null);
        assertThat(fixedAssetInvoiceDTO1).isNotEqualTo(fixedAssetInvoiceDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fixedAssetInvoiceMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fixedAssetInvoiceMapper.fromId(null)).isNull();
    }
}
