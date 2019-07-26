package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.FileUpload;
import io.github.assets.repository.FileUploadRepository;
import io.github.assets.repository.search.FileUploadSearchRepository;
import io.github.assets.service.FileUploadService;
import io.github.assets.service.dto.FileUploadDTO;
import io.github.assets.service.mapper.FileUploadMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.FileUploadCriteria;
import io.github.assets.service.FileUploadQueryService;

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
 * Integration tests for the {@Link FileUploadResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class FileUploadResourceIT {

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FILE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_PERIOD_FROM = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_FROM = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_PERIOD_TO = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_PERIOD_TO = LocalDate.now(ZoneId.systemDefault());

    private static final Long DEFAULT_FILE_TYPE_ID = 1L;
    private static final Long UPDATED_FILE_TYPE_ID = 2L;

    private static final byte[] DEFAULT_DATA_FILE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_DATA_FILE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_DATA_FILE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_DATA_FILE_CONTENT_TYPE = "image/png";

    private static final Boolean DEFAULT_UPLOAD_SUCCESSFUL = false;
    private static final Boolean UPDATED_UPLOAD_SUCCESSFUL = true;

    private static final Boolean DEFAULT_UPLOAD_PROCESSED = false;
    private static final Boolean UPDATED_UPLOAD_PROCESSED = true;

    private static final String DEFAULT_UPLOAD_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_UPLOAD_TOKEN = "BBBBBBBBBB";

    @Autowired
    private FileUploadRepository fileUploadRepository;

    @Autowired
    private FileUploadMapper fileUploadMapper;

    @Autowired
    private FileUploadService fileUploadService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.FileUploadSearchRepositoryMockConfiguration
     */
    @Autowired
    private FileUploadSearchRepository mockFileUploadSearchRepository;

    @Autowired
    private FileUploadQueryService fileUploadQueryService;

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

    private MockMvc restFileUploadMockMvc;

    private FileUpload fileUpload;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileUploadResource fileUploadResource = new FileUploadResource(fileUploadService, fileUploadQueryService);
        this.restFileUploadMockMvc = MockMvcBuilders.standaloneSetup(fileUploadResource)
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
    public static FileUpload createEntity(EntityManager em) {
        FileUpload fileUpload = new FileUpload()
            .description(DEFAULT_DESCRIPTION)
            .fileName(DEFAULT_FILE_NAME)
            .periodFrom(DEFAULT_PERIOD_FROM)
            .periodTo(DEFAULT_PERIOD_TO)
            .fileTypeId(DEFAULT_FILE_TYPE_ID)
            .dataFile(DEFAULT_DATA_FILE)
            .dataFileContentType(DEFAULT_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(DEFAULT_UPLOAD_SUCCESSFUL)
            .uploadProcessed(DEFAULT_UPLOAD_PROCESSED)
            .uploadToken(DEFAULT_UPLOAD_TOKEN);
        return fileUpload;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileUpload createUpdatedEntity(EntityManager em) {
        FileUpload fileUpload = new FileUpload()
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fileTypeId(UPDATED_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        return fileUpload;
    }

    @BeforeEach
    public void initTest() {
        fileUpload = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileUpload() throws Exception {
        int databaseSizeBeforeCreate = fileUploadRepository.findAll().size();

        // Create the FileUpload
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);
        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isCreated());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeCreate + 1);
        FileUpload testFileUpload = fileUploadList.get(fileUploadList.size() - 1);
        assertThat(testFileUpload.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);
        assertThat(testFileUpload.getFileName()).isEqualTo(DEFAULT_FILE_NAME);
        assertThat(testFileUpload.getPeriodFrom()).isEqualTo(DEFAULT_PERIOD_FROM);
        assertThat(testFileUpload.getPeriodTo()).isEqualTo(DEFAULT_PERIOD_TO);
        assertThat(testFileUpload.getFileTypeId()).isEqualTo(DEFAULT_FILE_TYPE_ID);
        assertThat(testFileUpload.getDataFile()).isEqualTo(DEFAULT_DATA_FILE);
        assertThat(testFileUpload.getDataFileContentType()).isEqualTo(DEFAULT_DATA_FILE_CONTENT_TYPE);
        assertThat(testFileUpload.isUploadSuccessful()).isEqualTo(DEFAULT_UPLOAD_SUCCESSFUL);
        assertThat(testFileUpload.isUploadProcessed()).isEqualTo(DEFAULT_UPLOAD_PROCESSED);
        assertThat(testFileUpload.getUploadToken()).isEqualTo(DEFAULT_UPLOAD_TOKEN);

        // Validate the FileUpload in Elasticsearch
        verify(mockFileUploadSearchRepository, times(1)).save(testFileUpload);
    }

    @Test
    @Transactional
    public void createFileUploadWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileUploadRepository.findAll().size();

        // Create the FileUpload with an existing ID
        fileUpload.setId(1L);
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeCreate);

        // Validate the FileUpload in Elasticsearch
        verify(mockFileUploadSearchRepository, times(0)).save(fileUpload);
    }


    @Test
    @Transactional
    public void checkDescriptionIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileUploadRepository.findAll().size();
        // set the field null
        fileUpload.setDescription(null);

        // Create the FileUpload, which fails.
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);

        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileUploadRepository.findAll().size();
        // set the field null
        fileUpload.setFileName(null);

        // Create the FileUpload, which fails.
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);

        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileTypeIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileUploadRepository.findAll().size();
        // set the field null
        fileUpload.setFileTypeId(null);

        // Create the FileUpload, which fails.
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);

        restFileUploadMockMvc.perform(post("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isBadRequest());

        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileUploads() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList
        restFileUploadMockMvc.perform(get("/api/file-uploads?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME.toString())))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].fileTypeId").value(hasItem(DEFAULT_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN.toString())));
    }
    
    @Test
    @Transactional
    public void getFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get the fileUpload
        restFileUploadMockMvc.perform(get("/api/file-uploads/{id}", fileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileUpload.getId().intValue()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()))
            .andExpect(jsonPath("$.fileName").value(DEFAULT_FILE_NAME.toString()))
            .andExpect(jsonPath("$.periodFrom").value(DEFAULT_PERIOD_FROM.toString()))
            .andExpect(jsonPath("$.periodTo").value(DEFAULT_PERIOD_TO.toString()))
            .andExpect(jsonPath("$.fileTypeId").value(DEFAULT_FILE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.dataFileContentType").value(DEFAULT_DATA_FILE_CONTENT_TYPE))
            .andExpect(jsonPath("$.dataFile").value(Base64Utils.encodeToString(DEFAULT_DATA_FILE)))
            .andExpect(jsonPath("$.uploadSuccessful").value(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue()))
            .andExpect(jsonPath("$.uploadProcessed").value(DEFAULT_UPLOAD_PROCESSED.booleanValue()))
            .andExpect(jsonPath("$.uploadToken").value(DEFAULT_UPLOAD_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getAllFileUploadsByDescriptionIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where description equals to DEFAULT_DESCRIPTION
        defaultFileUploadShouldBeFound("description.equals=" + DEFAULT_DESCRIPTION);

        // Get all the fileUploadList where description equals to UPDATED_DESCRIPTION
        defaultFileUploadShouldNotBeFound("description.equals=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByDescriptionIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where description in DEFAULT_DESCRIPTION or UPDATED_DESCRIPTION
        defaultFileUploadShouldBeFound("description.in=" + DEFAULT_DESCRIPTION + "," + UPDATED_DESCRIPTION);

        // Get all the fileUploadList where description equals to UPDATED_DESCRIPTION
        defaultFileUploadShouldNotBeFound("description.in=" + UPDATED_DESCRIPTION);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByDescriptionIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where description is not null
        defaultFileUploadShouldBeFound("description.specified=true");

        // Get all the fileUploadList where description is null
        defaultFileUploadShouldNotBeFound("description.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileNameIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileName equals to DEFAULT_FILE_NAME
        defaultFileUploadShouldBeFound("fileName.equals=" + DEFAULT_FILE_NAME);

        // Get all the fileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultFileUploadShouldNotBeFound("fileName.equals=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileNameIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileName in DEFAULT_FILE_NAME or UPDATED_FILE_NAME
        defaultFileUploadShouldBeFound("fileName.in=" + DEFAULT_FILE_NAME + "," + UPDATED_FILE_NAME);

        // Get all the fileUploadList where fileName equals to UPDATED_FILE_NAME
        defaultFileUploadShouldNotBeFound("fileName.in=" + UPDATED_FILE_NAME);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileName is not null
        defaultFileUploadShouldBeFound("fileName.specified=true");

        // Get all the fileUploadList where fileName is null
        defaultFileUploadShouldNotBeFound("fileName.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodFromIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodFrom equals to DEFAULT_PERIOD_FROM
        defaultFileUploadShouldBeFound("periodFrom.equals=" + DEFAULT_PERIOD_FROM);

        // Get all the fileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultFileUploadShouldNotBeFound("periodFrom.equals=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodFromIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodFrom in DEFAULT_PERIOD_FROM or UPDATED_PERIOD_FROM
        defaultFileUploadShouldBeFound("periodFrom.in=" + DEFAULT_PERIOD_FROM + "," + UPDATED_PERIOD_FROM);

        // Get all the fileUploadList where periodFrom equals to UPDATED_PERIOD_FROM
        defaultFileUploadShouldNotBeFound("periodFrom.in=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodFromIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodFrom is not null
        defaultFileUploadShouldBeFound("periodFrom.specified=true");

        // Get all the fileUploadList where periodFrom is null
        defaultFileUploadShouldNotBeFound("periodFrom.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodFromIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodFrom greater than or equals to DEFAULT_PERIOD_FROM
        defaultFileUploadShouldBeFound("periodFrom.greaterOrEqualThan=" + DEFAULT_PERIOD_FROM);

        // Get all the fileUploadList where periodFrom greater than or equals to UPDATED_PERIOD_FROM
        defaultFileUploadShouldNotBeFound("periodFrom.greaterOrEqualThan=" + UPDATED_PERIOD_FROM);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodFromIsLessThanSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodFrom less than or equals to DEFAULT_PERIOD_FROM
        defaultFileUploadShouldNotBeFound("periodFrom.lessThan=" + DEFAULT_PERIOD_FROM);

        // Get all the fileUploadList where periodFrom less than or equals to UPDATED_PERIOD_FROM
        defaultFileUploadShouldBeFound("periodFrom.lessThan=" + UPDATED_PERIOD_FROM);
    }


    @Test
    @Transactional
    public void getAllFileUploadsByPeriodToIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodTo equals to DEFAULT_PERIOD_TO
        defaultFileUploadShouldBeFound("periodTo.equals=" + DEFAULT_PERIOD_TO);

        // Get all the fileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultFileUploadShouldNotBeFound("periodTo.equals=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodToIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodTo in DEFAULT_PERIOD_TO or UPDATED_PERIOD_TO
        defaultFileUploadShouldBeFound("periodTo.in=" + DEFAULT_PERIOD_TO + "," + UPDATED_PERIOD_TO);

        // Get all the fileUploadList where periodTo equals to UPDATED_PERIOD_TO
        defaultFileUploadShouldNotBeFound("periodTo.in=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodToIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodTo is not null
        defaultFileUploadShouldBeFound("periodTo.specified=true");

        // Get all the fileUploadList where periodTo is null
        defaultFileUploadShouldNotBeFound("periodTo.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodToIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodTo greater than or equals to DEFAULT_PERIOD_TO
        defaultFileUploadShouldBeFound("periodTo.greaterOrEqualThan=" + DEFAULT_PERIOD_TO);

        // Get all the fileUploadList where periodTo greater than or equals to UPDATED_PERIOD_TO
        defaultFileUploadShouldNotBeFound("periodTo.greaterOrEqualThan=" + UPDATED_PERIOD_TO);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByPeriodToIsLessThanSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where periodTo less than or equals to DEFAULT_PERIOD_TO
        defaultFileUploadShouldNotBeFound("periodTo.lessThan=" + DEFAULT_PERIOD_TO);

        // Get all the fileUploadList where periodTo less than or equals to UPDATED_PERIOD_TO
        defaultFileUploadShouldBeFound("periodTo.lessThan=" + UPDATED_PERIOD_TO);
    }


    @Test
    @Transactional
    public void getAllFileUploadsByFileTypeIdIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileTypeId equals to DEFAULT_FILE_TYPE_ID
        defaultFileUploadShouldBeFound("fileTypeId.equals=" + DEFAULT_FILE_TYPE_ID);

        // Get all the fileUploadList where fileTypeId equals to UPDATED_FILE_TYPE_ID
        defaultFileUploadShouldNotBeFound("fileTypeId.equals=" + UPDATED_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileTypeIdIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileTypeId in DEFAULT_FILE_TYPE_ID or UPDATED_FILE_TYPE_ID
        defaultFileUploadShouldBeFound("fileTypeId.in=" + DEFAULT_FILE_TYPE_ID + "," + UPDATED_FILE_TYPE_ID);

        // Get all the fileUploadList where fileTypeId equals to UPDATED_FILE_TYPE_ID
        defaultFileUploadShouldNotBeFound("fileTypeId.in=" + UPDATED_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileTypeIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileTypeId is not null
        defaultFileUploadShouldBeFound("fileTypeId.specified=true");

        // Get all the fileUploadList where fileTypeId is null
        defaultFileUploadShouldNotBeFound("fileTypeId.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileTypeIdIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileTypeId greater than or equals to DEFAULT_FILE_TYPE_ID
        defaultFileUploadShouldBeFound("fileTypeId.greaterOrEqualThan=" + DEFAULT_FILE_TYPE_ID);

        // Get all the fileUploadList where fileTypeId greater than or equals to UPDATED_FILE_TYPE_ID
        defaultFileUploadShouldNotBeFound("fileTypeId.greaterOrEqualThan=" + UPDATED_FILE_TYPE_ID);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByFileTypeIdIsLessThanSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where fileTypeId less than or equals to DEFAULT_FILE_TYPE_ID
        defaultFileUploadShouldNotBeFound("fileTypeId.lessThan=" + DEFAULT_FILE_TYPE_ID);

        // Get all the fileUploadList where fileTypeId less than or equals to UPDATED_FILE_TYPE_ID
        defaultFileUploadShouldBeFound("fileTypeId.lessThan=" + UPDATED_FILE_TYPE_ID);
    }


    @Test
    @Transactional
    public void getAllFileUploadsByUploadSuccessfulIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadSuccessful equals to DEFAULT_UPLOAD_SUCCESSFUL
        defaultFileUploadShouldBeFound("uploadSuccessful.equals=" + DEFAULT_UPLOAD_SUCCESSFUL);

        // Get all the fileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultFileUploadShouldNotBeFound("uploadSuccessful.equals=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadSuccessfulIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadSuccessful in DEFAULT_UPLOAD_SUCCESSFUL or UPDATED_UPLOAD_SUCCESSFUL
        defaultFileUploadShouldBeFound("uploadSuccessful.in=" + DEFAULT_UPLOAD_SUCCESSFUL + "," + UPDATED_UPLOAD_SUCCESSFUL);

        // Get all the fileUploadList where uploadSuccessful equals to UPDATED_UPLOAD_SUCCESSFUL
        defaultFileUploadShouldNotBeFound("uploadSuccessful.in=" + UPDATED_UPLOAD_SUCCESSFUL);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadSuccessfulIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadSuccessful is not null
        defaultFileUploadShouldBeFound("uploadSuccessful.specified=true");

        // Get all the fileUploadList where uploadSuccessful is null
        defaultFileUploadShouldNotBeFound("uploadSuccessful.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadProcessedIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadProcessed equals to DEFAULT_UPLOAD_PROCESSED
        defaultFileUploadShouldBeFound("uploadProcessed.equals=" + DEFAULT_UPLOAD_PROCESSED);

        // Get all the fileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultFileUploadShouldNotBeFound("uploadProcessed.equals=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadProcessedIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadProcessed in DEFAULT_UPLOAD_PROCESSED or UPDATED_UPLOAD_PROCESSED
        defaultFileUploadShouldBeFound("uploadProcessed.in=" + DEFAULT_UPLOAD_PROCESSED + "," + UPDATED_UPLOAD_PROCESSED);

        // Get all the fileUploadList where uploadProcessed equals to UPDATED_UPLOAD_PROCESSED
        defaultFileUploadShouldNotBeFound("uploadProcessed.in=" + UPDATED_UPLOAD_PROCESSED);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadProcessedIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadProcessed is not null
        defaultFileUploadShouldBeFound("uploadProcessed.specified=true");

        // Get all the fileUploadList where uploadProcessed is null
        defaultFileUploadShouldNotBeFound("uploadProcessed.specified=false");
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadTokenIsEqualToSomething() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadToken equals to DEFAULT_UPLOAD_TOKEN
        defaultFileUploadShouldBeFound("uploadToken.equals=" + DEFAULT_UPLOAD_TOKEN);

        // Get all the fileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultFileUploadShouldNotBeFound("uploadToken.equals=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadTokenIsInShouldWork() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadToken in DEFAULT_UPLOAD_TOKEN or UPDATED_UPLOAD_TOKEN
        defaultFileUploadShouldBeFound("uploadToken.in=" + DEFAULT_UPLOAD_TOKEN + "," + UPDATED_UPLOAD_TOKEN);

        // Get all the fileUploadList where uploadToken equals to UPDATED_UPLOAD_TOKEN
        defaultFileUploadShouldNotBeFound("uploadToken.in=" + UPDATED_UPLOAD_TOKEN);
    }

    @Test
    @Transactional
    public void getAllFileUploadsByUploadTokenIsNullOrNotNull() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        // Get all the fileUploadList where uploadToken is not null
        defaultFileUploadShouldBeFound("uploadToken.specified=true");

        // Get all the fileUploadList where uploadToken is null
        defaultFileUploadShouldNotBeFound("uploadToken.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultFileUploadShouldBeFound(String filter) throws Exception {
        restFileUploadMockMvc.perform(get("/api/file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].fileTypeId").value(hasItem(DEFAULT_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));

        // Check, that the count call also returns 1
        restFileUploadMockMvc.perform(get("/api/file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultFileUploadShouldNotBeFound(String filter) throws Exception {
        restFileUploadMockMvc.perform(get("/api/file-uploads?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restFileUploadMockMvc.perform(get("/api/file-uploads/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingFileUpload() throws Exception {
        // Get the fileUpload
        restFileUploadMockMvc.perform(get("/api/file-uploads/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        int databaseSizeBeforeUpdate = fileUploadRepository.findAll().size();

        // Update the fileUpload
        FileUpload updatedFileUpload = fileUploadRepository.findById(fileUpload.getId()).get();
        // Disconnect from session so that the updates on updatedFileUpload are not directly saved in db
        em.detach(updatedFileUpload);
        updatedFileUpload
            .description(UPDATED_DESCRIPTION)
            .fileName(UPDATED_FILE_NAME)
            .periodFrom(UPDATED_PERIOD_FROM)
            .periodTo(UPDATED_PERIOD_TO)
            .fileTypeId(UPDATED_FILE_TYPE_ID)
            .dataFile(UPDATED_DATA_FILE)
            .dataFileContentType(UPDATED_DATA_FILE_CONTENT_TYPE)
            .uploadSuccessful(UPDATED_UPLOAD_SUCCESSFUL)
            .uploadProcessed(UPDATED_UPLOAD_PROCESSED)
            .uploadToken(UPDATED_UPLOAD_TOKEN);
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(updatedFileUpload);

        restFileUploadMockMvc.perform(put("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isOk());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeUpdate);
        FileUpload testFileUpload = fileUploadList.get(fileUploadList.size() - 1);
        assertThat(testFileUpload.getDescription()).isEqualTo(UPDATED_DESCRIPTION);
        assertThat(testFileUpload.getFileName()).isEqualTo(UPDATED_FILE_NAME);
        assertThat(testFileUpload.getPeriodFrom()).isEqualTo(UPDATED_PERIOD_FROM);
        assertThat(testFileUpload.getPeriodTo()).isEqualTo(UPDATED_PERIOD_TO);
        assertThat(testFileUpload.getFileTypeId()).isEqualTo(UPDATED_FILE_TYPE_ID);
        assertThat(testFileUpload.getDataFile()).isEqualTo(UPDATED_DATA_FILE);
        assertThat(testFileUpload.getDataFileContentType()).isEqualTo(UPDATED_DATA_FILE_CONTENT_TYPE);
        assertThat(testFileUpload.isUploadSuccessful()).isEqualTo(UPDATED_UPLOAD_SUCCESSFUL);
        assertThat(testFileUpload.isUploadProcessed()).isEqualTo(UPDATED_UPLOAD_PROCESSED);
        assertThat(testFileUpload.getUploadToken()).isEqualTo(UPDATED_UPLOAD_TOKEN);

        // Validate the FileUpload in Elasticsearch
        verify(mockFileUploadSearchRepository, times(1)).save(testFileUpload);
    }

    @Test
    @Transactional
    public void updateNonExistingFileUpload() throws Exception {
        int databaseSizeBeforeUpdate = fileUploadRepository.findAll().size();

        // Create the FileUpload
        FileUploadDTO fileUploadDTO = fileUploadMapper.toDto(fileUpload);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileUploadMockMvc.perform(put("/api/file-uploads")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileUploadDTO)))
            .andExpect(status().isBadRequest());

        // Validate the FileUpload in the database
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FileUpload in Elasticsearch
        verify(mockFileUploadSearchRepository, times(0)).save(fileUpload);
    }

    @Test
    @Transactional
    public void deleteFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);

        int databaseSizeBeforeDelete = fileUploadRepository.findAll().size();

        // Delete the fileUpload
        restFileUploadMockMvc.perform(delete("/api/file-uploads/{id}", fileUpload.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FileUpload> fileUploadList = fileUploadRepository.findAll();
        assertThat(fileUploadList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FileUpload in Elasticsearch
        verify(mockFileUploadSearchRepository, times(1)).deleteById(fileUpload.getId());
    }

    @Test
    @Transactional
    public void searchFileUpload() throws Exception {
        // Initialize the database
        fileUploadRepository.saveAndFlush(fileUpload);
        when(mockFileUploadSearchRepository.search(queryStringQuery("id:" + fileUpload.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fileUpload), PageRequest.of(0, 1), 1));
        // Search the fileUpload
        restFileUploadMockMvc.perform(get("/api/_search/file-uploads?query=id:" + fileUpload.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileUpload.getId().intValue())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fileName").value(hasItem(DEFAULT_FILE_NAME)))
            .andExpect(jsonPath("$.[*].periodFrom").value(hasItem(DEFAULT_PERIOD_FROM.toString())))
            .andExpect(jsonPath("$.[*].periodTo").value(hasItem(DEFAULT_PERIOD_TO.toString())))
            .andExpect(jsonPath("$.[*].fileTypeId").value(hasItem(DEFAULT_FILE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].dataFileContentType").value(hasItem(DEFAULT_DATA_FILE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].dataFile").value(hasItem(Base64Utils.encodeToString(DEFAULT_DATA_FILE))))
            .andExpect(jsonPath("$.[*].uploadSuccessful").value(hasItem(DEFAULT_UPLOAD_SUCCESSFUL.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadProcessed").value(hasItem(DEFAULT_UPLOAD_PROCESSED.booleanValue())))
            .andExpect(jsonPath("$.[*].uploadToken").value(hasItem(DEFAULT_UPLOAD_TOKEN)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileUpload.class);
        FileUpload fileUpload1 = new FileUpload();
        fileUpload1.setId(1L);
        FileUpload fileUpload2 = new FileUpload();
        fileUpload2.setId(fileUpload1.getId());
        assertThat(fileUpload1).isEqualTo(fileUpload2);
        fileUpload2.setId(2L);
        assertThat(fileUpload1).isNotEqualTo(fileUpload2);
        fileUpload1.setId(null);
        assertThat(fileUpload1).isNotEqualTo(fileUpload2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileUploadDTO.class);
        FileUploadDTO fileUploadDTO1 = new FileUploadDTO();
        fileUploadDTO1.setId(1L);
        FileUploadDTO fileUploadDTO2 = new FileUploadDTO();
        assertThat(fileUploadDTO1).isNotEqualTo(fileUploadDTO2);
        fileUploadDTO2.setId(fileUploadDTO1.getId());
        assertThat(fileUploadDTO1).isEqualTo(fileUploadDTO2);
        fileUploadDTO2.setId(2L);
        assertThat(fileUploadDTO1).isNotEqualTo(fileUploadDTO2);
        fileUploadDTO1.setId(null);
        assertThat(fileUploadDTO1).isNotEqualTo(fileUploadDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(fileUploadMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(fileUploadMapper.fromId(null)).isNull();
    }
}
