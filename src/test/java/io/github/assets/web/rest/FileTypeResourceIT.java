package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.FileType;
import io.github.assets.repository.FileTypeRepository;
import io.github.assets.repository.search.FileTypeSearchRepository;
import io.github.assets.service.FileTypeService;
import io.github.assets.web.rest.errors.ExceptionTranslator;

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

import io.github.assets.domain.enumeration.FileMediumTypes;
/**
 * Integration tests for the {@Link FileTypeResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class FileTypeResourceIT {

    private static final String DEFAULT_FILE_TYPE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_FILE_TYPE_NAME = "BBBBBBBBBB";

    private static final FileMediumTypes DEFAULT_FILE_MEDIUM_TYPE = FileMediumTypes.EXCEL;
    private static final FileMediumTypes UPDATED_FILE_MEDIUM_TYPE = FileMediumTypes.PDF;

    private static final String DEFAULT_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_DESCRIPTION = "BBBBBBBBBB";

    @Autowired
    private FileTypeRepository fileTypeRepository;

    @Autowired
    private FileTypeService fileTypeService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.FileTypeSearchRepositoryMockConfiguration
     */
    @Autowired
    private FileTypeSearchRepository mockFileTypeSearchRepository;

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

    private MockMvc restFileTypeMockMvc;

    private FileType fileType;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FileTypeResource fileTypeResource = new FileTypeResource(fileTypeService);
        this.restFileTypeMockMvc = MockMvcBuilders.standaloneSetup(fileTypeResource)
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
    public static FileType createEntity(EntityManager em) {
        FileType fileType = new FileType()
            .fileTypeName(DEFAULT_FILE_TYPE_NAME)
            .fileMediumType(DEFAULT_FILE_MEDIUM_TYPE)
            .description(DEFAULT_DESCRIPTION);
        return fileType;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FileType createUpdatedEntity(EntityManager em) {
        FileType fileType = new FileType()
            .fileTypeName(UPDATED_FILE_TYPE_NAME)
            .fileMediumType(UPDATED_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION);
        return fileType;
    }

    @BeforeEach
    public void initTest() {
        fileType = createEntity(em);
    }

    @Test
    @Transactional
    public void createFileType() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isCreated());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate + 1);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getFileTypeName()).isEqualTo(DEFAULT_FILE_TYPE_NAME);
        assertThat(testFileType.getFileMediumType()).isEqualTo(DEFAULT_FILE_MEDIUM_TYPE);
        assertThat(testFileType.getDescription()).isEqualTo(DEFAULT_DESCRIPTION);

        // Validate the FileType in Elasticsearch
        verify(mockFileTypeSearchRepository, times(1)).save(testFileType);
    }

    @Test
    @Transactional
    public void createFileTypeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = fileTypeRepository.findAll().size();

        // Create the FileType with an existing ID
        fileType.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeCreate);

        // Validate the FileType in Elasticsearch
        verify(mockFileTypeSearchRepository, times(0)).save(fileType);
    }


    @Test
    @Transactional
    public void checkFileTypeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileTypeRepository.findAll().size();
        // set the field null
        fileType.setFileTypeName(null);

        // Create the FileType, which fails.

        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkFileMediumTypeIsRequired() throws Exception {
        int databaseSizeBeforeTest = fileTypeRepository.findAll().size();
        // set the field null
        fileType.setFileMediumType(null);

        // Create the FileType, which fails.

        restFileTypeMockMvc.perform(post("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllFileTypes() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get all the fileTypeList
        restFileTypeMockMvc.perform(get("/api/file-types?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileTypeName").value(hasItem(DEFAULT_FILE_TYPE_NAME.toString())))
            .andExpect(jsonPath("$.[*].fileMediumType").value(hasItem(DEFAULT_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION.toString())));
    }
    
    @Test
    @Transactional
    public void getFileType() throws Exception {
        // Initialize the database
        fileTypeRepository.saveAndFlush(fileType);

        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", fileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(fileType.getId().intValue()))
            .andExpect(jsonPath("$.fileTypeName").value(DEFAULT_FILE_TYPE_NAME.toString()))
            .andExpect(jsonPath("$.fileMediumType").value(DEFAULT_FILE_MEDIUM_TYPE.toString()))
            .andExpect(jsonPath("$.description").value(DEFAULT_DESCRIPTION.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingFileType() throws Exception {
        // Get the fileType
        restFileTypeMockMvc.perform(get("/api/file-types/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFileType() throws Exception {
        // Initialize the database
        fileTypeService.save(fileType);
        // As the test used the service layer, reset the Elasticsearch mock repository
        reset(mockFileTypeSearchRepository);

        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Update the fileType
        FileType updatedFileType = fileTypeRepository.findById(fileType.getId()).get();
        // Disconnect from session so that the updates on updatedFileType are not directly saved in db
        em.detach(updatedFileType);
        updatedFileType
            .fileTypeName(UPDATED_FILE_TYPE_NAME)
            .fileMediumType(UPDATED_FILE_MEDIUM_TYPE)
            .description(UPDATED_DESCRIPTION);

        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedFileType)))
            .andExpect(status().isOk());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate);
        FileType testFileType = fileTypeList.get(fileTypeList.size() - 1);
        assertThat(testFileType.getFileTypeName()).isEqualTo(UPDATED_FILE_TYPE_NAME);
        assertThat(testFileType.getFileMediumType()).isEqualTo(UPDATED_FILE_MEDIUM_TYPE);
        assertThat(testFileType.getDescription()).isEqualTo(UPDATED_DESCRIPTION);

        // Validate the FileType in Elasticsearch
        verify(mockFileTypeSearchRepository, times(1)).save(testFileType);
    }

    @Test
    @Transactional
    public void updateNonExistingFileType() throws Exception {
        int databaseSizeBeforeUpdate = fileTypeRepository.findAll().size();

        // Create the FileType

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFileTypeMockMvc.perform(put("/api/file-types")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(fileType)))
            .andExpect(status().isBadRequest());

        // Validate the FileType in the database
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the FileType in Elasticsearch
        verify(mockFileTypeSearchRepository, times(0)).save(fileType);
    }

    @Test
    @Transactional
    public void deleteFileType() throws Exception {
        // Initialize the database
        fileTypeService.save(fileType);

        int databaseSizeBeforeDelete = fileTypeRepository.findAll().size();

        // Delete the fileType
        restFileTypeMockMvc.perform(delete("/api/file-types/{id}", fileType.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<FileType> fileTypeList = fileTypeRepository.findAll();
        assertThat(fileTypeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the FileType in Elasticsearch
        verify(mockFileTypeSearchRepository, times(1)).deleteById(fileType.getId());
    }

    @Test
    @Transactional
    public void searchFileType() throws Exception {
        // Initialize the database
        fileTypeService.save(fileType);
        when(mockFileTypeSearchRepository.search(queryStringQuery("id:" + fileType.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(fileType), PageRequest.of(0, 1), 1));
        // Search the fileType
        restFileTypeMockMvc.perform(get("/api/_search/file-types?query=id:" + fileType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fileType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fileTypeName").value(hasItem(DEFAULT_FILE_TYPE_NAME)))
            .andExpect(jsonPath("$.[*].fileMediumType").value(hasItem(DEFAULT_FILE_MEDIUM_TYPE.toString())))
            .andExpect(jsonPath("$.[*].description").value(hasItem(DEFAULT_DESCRIPTION)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FileType.class);
        FileType fileType1 = new FileType();
        fileType1.setId(1L);
        FileType fileType2 = new FileType();
        fileType2.setId(fileType1.getId());
        assertThat(fileType1).isEqualTo(fileType2);
        fileType2.setId(2L);
        assertThat(fileType1).isNotEqualTo(fileType2);
        fileType1.setId(null);
        assertThat(fileType1).isNotEqualTo(fileType2);
    }
}
