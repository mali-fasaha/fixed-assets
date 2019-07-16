package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.ScannedDocument;
import io.github.assets.repository.ScannedDocumentRepository;
import io.github.assets.repository.search.ScannedDocumentSearchRepository;
import io.github.assets.service.ScannedDocumentService;
import io.github.assets.service.dto.ScannedDocumentDTO;
import io.github.assets.service.mapper.ScannedDocumentMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.ScannedDocumentCriteria;
import io.github.assets.service.ScannedDocumentQueryService;

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
import org.springframework.util.Base64Utils;
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
 * Integration tests for the {@Link ScannedDocumentResource} REST controller.
 */
@EmbeddedKafka
@SpringBootTest(classes = FixedAssetsApp.class)
public class ScannedDocumentResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final byte[] DEFAULT_APPROVAL_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_APPROVAL_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_INVOICE_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_INVOICE_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_LPO_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LPO_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_LPO_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LPO_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_REQUISITION_DOCUMENT = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_REQUISITION_DOCUMENT = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE = "image/png";

    private static final byte[] DEFAULT_OTHER_DOCUMENTS = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_OTHER_DOCUMENTS = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_OTHER_DOCUMENTS_CONTENT_TYPE = "image/png";

    @Autowired
    private ScannedDocumentRepository scannedDocumentRepository;

    @Autowired
    private ScannedDocumentMapper scannedDocumentMapper;

    @Autowired
    private ScannedDocumentService scannedDocumentService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.ScannedDocumentSearchRepositoryMockConfiguration
     */
    @Autowired
    private ScannedDocumentSearchRepository mockScannedDocumentSearchRepository;

    @Autowired
    private ScannedDocumentQueryService scannedDocumentQueryService;

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

    private MockMvc restScannedDocumentMockMvc;

    private ScannedDocument scannedDocument;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final ScannedDocumentResource scannedDocumentResource = new ScannedDocumentResource(scannedDocumentService, scannedDocumentQueryService);
        this.restScannedDocumentMockMvc = MockMvcBuilders.standaloneSetup(scannedDocumentResource)
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
    public static ScannedDocument createEntity(EntityManager em) {
        ScannedDocument scannedDocument = new ScannedDocument()
            .description(DEFAULT_DESCRIPTION)
            .approvalDocument(DEFAULT_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)
            .invoiceDocument(DEFAULT_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)
            .lpoDocument(DEFAULT_LPO_DOCUMENT)
            .lpoDocumentContentType(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(DEFAULT_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .otherDocuments(DEFAULT_OTHER_DOCUMENTS)
            .otherDocumentsContentType(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE);
        return scannedDocument;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ScannedDocument createUpdatedEntity(EntityManager em) {
        ScannedDocument scannedDocument = new ScannedDocument()
            .description(UPDATED_DESCRIPTION)
            .approvalDocument(UPDATED_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE)
            .invoiceDocument(UPDATED_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE)
            .lpoDocument(UPDATED_LPO_DOCUMENT)
            .lpoDocumentContentType(UPDATED_LPO_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(UPDATED_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .otherDocuments(UPDATED_OTHER_DOCUMENTS)
            .otherDocumentsContentType(UPDATED_OTHER_DOCUMENTS_CONTENT_TYPE);
        return scannedDocument;
    }

    @BeforeEach
    public void initTest() {
        scannedDocument = createEntity(em);
    }

    @Test
    @Transactional
    public void createScannedDocument() throws Exception {
        int databaseSizeBeforeCreate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);
        restScannedDocumentMockMvc.perform(post("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isCreated());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeCreate + 1);
        ScannedDocument testScannedDocument = scannedDocumentList.get(scannedDocumentList.size() - 1);
        assertThat(testScannedDocument.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testScannedDocument.getApprovalDocument()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT);
        assertThat(testScannedDocument.getApprovalDocumentContentType()).isEqualTo(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getInvoiceDocument()).isEqualTo(DEFAULT_INVOICE_DOCUMENT);
        assertThat(testScannedDocument.getInvoiceDocumentContentType()).isEqualTo(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getLpoDocument()).isEqualTo(DEFAULT_LPO_DOCUMENT);
        assertThat(testScannedDocument.getLpoDocumentContentType()).isEqualTo(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getRequisitionDocument()).isEqualTo(DEFAULT_REQUISITION_DOCUMENT);
        assertThat(testScannedDocument.getRequisitionDocumentContentType()).isEqualTo(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getOtherDocuments()).isEqualTo(DEFAULT_OTHER_DOCUMENTS);
        assertThat(testScannedDocument.getOtherDocumentsContentType()).isEqualTo(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).save(testScannedDocument);
    }

    @Test
    @Transactional
    public void createScannedDocumentWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument with an existing ID
        scannedDocument.setId(1L);
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);

        // An entity with an existing ID cannot be created, so this API call must fail
        restScannedDocumentMockMvc.perform(post("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeCreate);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(0)).save(scannedDocument);
    }


    @Test
    @Transactional
    public void getAllScannedDocuments() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].approvalDocumentContentType").value(hasItem(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT))))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].lpoDocumentContentType").value(hasItem(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lpoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_LPO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherDocumentsContentType").value(hasItem(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocuments").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENTS))));
    }
    
    @Test
    @Transactional
    public void getScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/{id}", scannedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(scannedDocument.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.approvalDocumentContentType").value(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.approvalDocument").value(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT)))
            .andExpect(jsonPath("$.invoiceDocumentContentType").value(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.invoiceDocument").value(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT)))
            .andExpect(jsonPath("$.lpoDocumentContentType").value(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.lpoDocument").value(Base64Utils.encodeToString(DEFAULT_LPO_DOCUMENT)))
            .andExpect(jsonPath("$.requisitionDocumentContentType").value(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE))
            .andExpect(jsonPath("$.requisitionDocument").value(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT)))
            .andExpect(jsonPath("$.otherDocumentsContentType").value(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE))
            .andExpect(jsonPath("$.otherDocuments").value(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENTS)));
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description equals to DEFAULT_DESCRIPTION
        defaultScannedDocumentShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the scannedDocumentList where description equals to UPDATED_DESCRIPTION
        defaultScannedDocumentShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultScannedDocumentShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the scannedDocumentList where description equals to UPDATED_DESCRIPTION
        defaultScannedDocumentShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllScannedDocumentsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        // Get all the scannedDocumentList where description is not null
        defaultScannedDocumentShouldBeFound("description.specified=true");

        // Get all the scannedDocumentList where description is null
        defaultScannedDocumentShouldNotBeFound("description.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultScannedDocumentShouldBeFound(String filter) throws Exception {
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].approvalDocumentContentType").value(hasItem(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT))))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].lpoDocumentContentType").value(hasItem(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lpoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_LPO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherDocumentsContentType").value(hasItem(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocuments").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENTS))));

        // Check, that the count call also returns 1
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultScannedDocumentShouldNotBeFound(String filter) throws Exception {
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingScannedDocument() throws Exception {
        // Get the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/scanned-documents/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        int databaseSizeBeforeUpdate = scannedDocumentRepository.findAll().size();

        // Update the scannedDocument
        ScannedDocument updatedScannedDocument = scannedDocumentRepository.findById(scannedDocument.getId()).get();
        // Disconnect from session so that the updates on updatedScannedDocument are not directly saved in db
        em.detach(updatedScannedDocument);
        updatedScannedDocument
            .description(UPDATED_DESCRIPTION)
            .approvalDocument(UPDATED_APPROVAL_DOCUMENT)
            .approvalDocumentContentType(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE)
            .invoiceDocument(UPDATED_INVOICE_DOCUMENT)
            .invoiceDocumentContentType(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE)
            .lpoDocument(UPDATED_LPO_DOCUMENT)
            .lpoDocumentContentType(UPDATED_LPO_DOCUMENT_CONTENT_TYPE)
            .requisitionDocument(UPDATED_REQUISITION_DOCUMENT)
            .requisitionDocumentContentType(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE)
            .otherDocuments(UPDATED_OTHER_DOCUMENTS)
            .otherDocumentsContentType(UPDATED_OTHER_DOCUMENTS_CONTENT_TYPE);
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(updatedScannedDocument);

        restScannedDocumentMockMvc.perform(put("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isOk());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeUpdate);
        ScannedDocument testScannedDocument = scannedDocumentList.get(scannedDocumentList.size() - 1);
        assertThat(testScannedDocument.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testScannedDocument.getApprovalDocument()).isEqualTo(UPDATED_APPROVAL_DOCUMENT);
        assertThat(testScannedDocument.getApprovalDocumentContentType()).isEqualTo(UPDATED_APPROVAL_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getInvoiceDocument()).isEqualTo(UPDATED_INVOICE_DOCUMENT);
        assertThat(testScannedDocument.getInvoiceDocumentContentType()).isEqualTo(UPDATED_INVOICE_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getLpoDocument()).isEqualTo(UPDATED_LPO_DOCUMENT);
        assertThat(testScannedDocument.getLpoDocumentContentType()).isEqualTo(UPDATED_LPO_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getRequisitionDocument()).isEqualTo(UPDATED_REQUISITION_DOCUMENT);
        assertThat(testScannedDocument.getRequisitionDocumentContentType()).isEqualTo(UPDATED_REQUISITION_DOCUMENT_CONTENT_TYPE);
        assertThat(testScannedDocument.getOtherDocuments()).isEqualTo(UPDATED_OTHER_DOCUMENTS);
        assertThat(testScannedDocument.getOtherDocumentsContentType()).isEqualTo(UPDATED_OTHER_DOCUMENTS_CONTENT_TYPE);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).save(testScannedDocument);
    }

    @Test
    @Transactional
    public void updateNonExistingScannedDocument() throws Exception {
        int databaseSizeBeforeUpdate = scannedDocumentRepository.findAll().size();

        // Create the ScannedDocument
        ScannedDocumentDTO scannedDocumentDTO = scannedDocumentMapper.toDto(scannedDocument);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restScannedDocumentMockMvc.perform(put("/api/scanned-documents")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(scannedDocumentDTO)))
            .andExpect(status().isBadRequest());

        // Validate the ScannedDocument in the database
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeUpdate);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(0)).save(scannedDocument);
    }

    @Test
    @Transactional
    public void deleteScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);

        int databaseSizeBeforeDelete = scannedDocumentRepository.findAll().size();

        // Delete the scannedDocument
        restScannedDocumentMockMvc.perform(delete("/api/scanned-documents/{id}", scannedDocument.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<ScannedDocument> scannedDocumentList = scannedDocumentRepository.findAll();
        assertThat(scannedDocumentList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the ScannedDocument in Elasticsearch
        verify(mockScannedDocumentSearchRepository, times(1)).deleteById(scannedDocument.getId());
    }

    @Test
    @Transactional
    public void searchScannedDocument() throws Exception {
        // Initialize the database
        scannedDocumentRepository.saveAndFlush(scannedDocument);
        when(mockScannedDocumentSearchRepository.search(queryStringQuery("id:" + scannedDocument.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(scannedDocument), PageRequest.of(0, 1), 1));
        // Search the scannedDocument
        restScannedDocumentMockMvc.perform(get("/api/_search/scanned-documents?query=id:" + scannedDocument.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(scannedDocument.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].approvalDocumentContentType").value(hasItem(DEFAULT_APPROVAL_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].approvalDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_APPROVAL_DOCUMENT))))
            .andExpect(jsonPath("$.[*].invoiceDocumentContentType").value(hasItem(DEFAULT_INVOICE_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].invoiceDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_INVOICE_DOCUMENT))))
            .andExpect(jsonPath("$.[*].lpoDocumentContentType").value(hasItem(DEFAULT_LPO_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].lpoDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_LPO_DOCUMENT))))
            .andExpect(jsonPath("$.[*].requisitionDocumentContentType").value(hasItem(DEFAULT_REQUISITION_DOCUMENT_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].requisitionDocument").value(hasItem(Base64Utils.encodeToString(DEFAULT_REQUISITION_DOCUMENT))))
            .andExpect(jsonPath("$.[*].otherDocumentsContentType").value(hasItem(DEFAULT_OTHER_DOCUMENTS_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].otherDocuments").value(hasItem(Base64Utils.encodeToString(DEFAULT_OTHER_DOCUMENTS))));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScannedDocument.class);
        ScannedDocument scannedDocument1 = new ScannedDocument();
        scannedDocument1.setId(1L);
        ScannedDocument scannedDocument2 = new ScannedDocument();
        scannedDocument2.setId(scannedDocument1.getId());
        assertThat(scannedDocument1).isEqualTo(scannedDocument2);
        scannedDocument2.setId(2L);
        assertThat(scannedDocument1).isNotEqualTo(scannedDocument2);
        scannedDocument1.setId(null);
        assertThat(scannedDocument1).isNotEqualTo(scannedDocument2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScannedDocumentDTO.class);
        ScannedDocumentDTO scannedDocumentDTO1 = new ScannedDocumentDTO();
        scannedDocumentDTO1.setId(1L);
        ScannedDocumentDTO scannedDocumentDTO2 = new ScannedDocumentDTO();
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO2.setId(scannedDocumentDTO1.getId());
        assertThat(scannedDocumentDTO1).isEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO2.setId(2L);
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
        scannedDocumentDTO1.setId(null);
        assertThat(scannedDocumentDTO1).isNotEqualTo(scannedDocumentDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(scannedDocumentMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(scannedDocumentMapper.fromId(null)).isNull();
    }
}
