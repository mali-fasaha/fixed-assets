package io.github.assets.web.rest;

import io.github.assets.FixedAssetsApp;
import io.github.assets.domain.Employee;
import io.github.assets.repository.EmployeeRepository;
import io.github.assets.repository.search.EmployeeSearchRepository;
import io.github.assets.service.EmployeeService;
import io.github.assets.service.dto.EmployeeDTO;
import io.github.assets.service.mapper.EmployeeMapper;
import io.github.assets.web.rest.errors.ExceptionTranslator;
import io.github.assets.service.dto.EmployeeCriteria;
import io.github.assets.service.EmployeeQueryService;

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
 * Integration tests for the {@Link EmployeeResource} REST controller.
 */
@SpringBootTest(classes = FixedAssetsApp.class)
public class EmployeeResourceIT {

    private static final String DEFAULT_EMPLOYEE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_SERVICE_OUTLET_CODE = "AAAAAAAAAA";
    private static final String UPDATED_SERVICE_OUTLET_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_ROLE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_ROLE = "BBBBBBBBBB";

    private static final String DEFAULT_EMPLOYEE_STAFF_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_STAFF_CODE = "BBBBBBBBBB";

    private static final byte[] DEFAULT_EMPLOYEE_SIGNATURE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_EMPLOYEE_SIGNATURE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_EMPLOYEE_SIGNATURE_CONTENT_TYPE = "image/png";

    private static final String DEFAULT_EMPLOYEE_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMPLOYEE_EMAIL = "BBBBBBBBBB";

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private EmployeeService employeeService;

    /**
     * This repository is mocked in the io.github.assets.repository.search test package.
     *
     * @see io.github.assets.repository.search.EmployeeSearchRepositoryMockConfiguration
     */
    @Autowired
    private EmployeeSearchRepository mockEmployeeSearchRepository;

    @Autowired
    private EmployeeQueryService employeeQueryService;

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

    private MockMvc restEmployeeMockMvc;

    private Employee employee;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final EmployeeResource employeeResource = new EmployeeResource(employeeService, employeeQueryService);
        this.restEmployeeMockMvc = MockMvcBuilders.standaloneSetup(employeeResource)
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
    public static Employee createEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeName(DEFAULT_EMPLOYEE_NAME)
            .serviceOutletCode(DEFAULT_SERVICE_OUTLET_CODE)
            .employeeRole(DEFAULT_EMPLOYEE_ROLE)
            .employeeStaffCode(DEFAULT_EMPLOYEE_STAFF_CODE)
            .employeeSignature(DEFAULT_EMPLOYEE_SIGNATURE)
            .employeeSignatureContentType(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE)
            .employeeEmail(DEFAULT_EMPLOYEE_EMAIL);
        return employee;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Employee createUpdatedEntity(EntityManager em) {
        Employee employee = new Employee()
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .employeeRole(UPDATED_EMPLOYEE_ROLE)
            .employeeStaffCode(UPDATED_EMPLOYEE_STAFF_CODE)
            .employeeSignature(UPDATED_EMPLOYEE_SIGNATURE)
            .employeeSignatureContentType(UPDATED_EMPLOYEE_SIGNATURE_CONTENT_TYPE)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL);
        return employee;
    }

    @BeforeEach
    public void initTest() {
        employee = createEntity(em);
    }

    @Test
    @Transactional
    public void createEmployee() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isCreated());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate + 1);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(DEFAULT_EMPLOYEE_NAME);
        assertThat(testEmployee.getServiceOutletCode()).isEqualTo(DEFAULT_SERVICE_OUTLET_CODE);
        assertThat(testEmployee.getEmployeeRole()).isEqualTo(DEFAULT_EMPLOYEE_ROLE);
        assertThat(testEmployee.getEmployeeStaffCode()).isEqualTo(DEFAULT_EMPLOYEE_STAFF_CODE);
        assertThat(testEmployee.getEmployeeSignature()).isEqualTo(DEFAULT_EMPLOYEE_SIGNATURE);
        assertThat(testEmployee.getEmployeeSignatureContentType()).isEqualTo(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE);
        assertThat(testEmployee.getEmployeeEmail()).isEqualTo(DEFAULT_EMPLOYEE_EMAIL);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).save(testEmployee);
    }

    @Test
    @Transactional
    public void createEmployeeWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = employeeRepository.findAll().size();

        // Create the Employee with an existing ID
        employee.setId(1L);
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // An entity with an existing ID cannot be created, so this API call must fail
        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeCreate);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(0)).save(employee);
    }


    @Test
    @Transactional
    public void checkEmployeeNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = employeeRepository.findAll().size();
        // set the field null
        employee.setEmployeeName(null);

        // Create the Employee, which fails.
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        restEmployeeMockMvc.perform(post("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllEmployees() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME.toString())))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeRole").value(hasItem(DEFAULT_EMPLOYEE_ROLE.toString())))
            .andExpect(jsonPath("$.[*].employeeStaffCode").value(hasItem(DEFAULT_EMPLOYEE_STAFF_CODE.toString())))
            .andExpect(jsonPath("$.[*].employeeSignatureContentType").value(hasItem(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].employeeSignature").value(hasItem(Base64Utils.encodeToString(DEFAULT_EMPLOYEE_SIGNATURE))))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL.toString())));
    }
    
    @Test
    @Transactional
    public void getEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(employee.getId().intValue()))
            .andExpect(jsonPath("$.employeeName").value(DEFAULT_EMPLOYEE_NAME.toString()))
            .andExpect(jsonPath("$.serviceOutletCode").value(DEFAULT_SERVICE_OUTLET_CODE.toString()))
            .andExpect(jsonPath("$.employeeRole").value(DEFAULT_EMPLOYEE_ROLE.toString()))
            .andExpect(jsonPath("$.employeeStaffCode").value(DEFAULT_EMPLOYEE_STAFF_CODE.toString()))
            .andExpect(jsonPath("$.employeeSignatureContentType").value(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE))
            .andExpect(jsonPath("$.employeeSignature").value(Base64Utils.encodeToString(DEFAULT_EMPLOYEE_SIGNATURE)))
            .andExpect(jsonPath("$.employeeEmail").value(DEFAULT_EMPLOYEE_EMAIL.toString()));
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNameIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName equals to DEFAULT_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.equals=" + DEFAULT_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName equals to UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.equals=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNameIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName in DEFAULT_EMPLOYEE_NAME or UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldBeFound("employeeName.in=" + DEFAULT_EMPLOYEE_NAME + "," + UPDATED_EMPLOYEE_NAME);

        // Get all the employeeList where employeeName equals to UPDATED_EMPLOYEE_NAME
        defaultEmployeeShouldNotBeFound("employeeName.in=" + UPDATED_EMPLOYEE_NAME);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeName is not null
        defaultEmployeeShouldBeFound("employeeName.specified=true");

        // Get all the employeeList where employeeName is null
        defaultEmployeeShouldNotBeFound("employeeName.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByServiceOutletCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceOutletCode equals to DEFAULT_SERVICE_OUTLET_CODE
        defaultEmployeeShouldBeFound("serviceOutletCode.equals=" + DEFAULT_SERVICE_OUTLET_CODE);

        // Get all the employeeList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultEmployeeShouldNotBeFound("serviceOutletCode.equals=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByServiceOutletCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceOutletCode in DEFAULT_SERVICE_OUTLET_CODE or UPDATED_SERVICE_OUTLET_CODE
        defaultEmployeeShouldBeFound("serviceOutletCode.in=" + DEFAULT_SERVICE_OUTLET_CODE + "," + UPDATED_SERVICE_OUTLET_CODE);

        // Get all the employeeList where serviceOutletCode equals to UPDATED_SERVICE_OUTLET_CODE
        defaultEmployeeShouldNotBeFound("serviceOutletCode.in=" + UPDATED_SERVICE_OUTLET_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByServiceOutletCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where serviceOutletCode is not null
        defaultEmployeeShouldBeFound("serviceOutletCode.specified=true");

        // Get all the employeeList where serviceOutletCode is null
        defaultEmployeeShouldNotBeFound("serviceOutletCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeRoleIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeRole equals to DEFAULT_EMPLOYEE_ROLE
        defaultEmployeeShouldBeFound("employeeRole.equals=" + DEFAULT_EMPLOYEE_ROLE);

        // Get all the employeeList where employeeRole equals to UPDATED_EMPLOYEE_ROLE
        defaultEmployeeShouldNotBeFound("employeeRole.equals=" + UPDATED_EMPLOYEE_ROLE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeRoleIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeRole in DEFAULT_EMPLOYEE_ROLE or UPDATED_EMPLOYEE_ROLE
        defaultEmployeeShouldBeFound("employeeRole.in=" + DEFAULT_EMPLOYEE_ROLE + "," + UPDATED_EMPLOYEE_ROLE);

        // Get all the employeeList where employeeRole equals to UPDATED_EMPLOYEE_ROLE
        defaultEmployeeShouldNotBeFound("employeeRole.in=" + UPDATED_EMPLOYEE_ROLE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeRoleIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeRole is not null
        defaultEmployeeShouldBeFound("employeeRole.specified=true");

        // Get all the employeeList where employeeRole is null
        defaultEmployeeShouldNotBeFound("employeeRole.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStaffCodeIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStaffCode equals to DEFAULT_EMPLOYEE_STAFF_CODE
        defaultEmployeeShouldBeFound("employeeStaffCode.equals=" + DEFAULT_EMPLOYEE_STAFF_CODE);

        // Get all the employeeList where employeeStaffCode equals to UPDATED_EMPLOYEE_STAFF_CODE
        defaultEmployeeShouldNotBeFound("employeeStaffCode.equals=" + UPDATED_EMPLOYEE_STAFF_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStaffCodeIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStaffCode in DEFAULT_EMPLOYEE_STAFF_CODE or UPDATED_EMPLOYEE_STAFF_CODE
        defaultEmployeeShouldBeFound("employeeStaffCode.in=" + DEFAULT_EMPLOYEE_STAFF_CODE + "," + UPDATED_EMPLOYEE_STAFF_CODE);

        // Get all the employeeList where employeeStaffCode equals to UPDATED_EMPLOYEE_STAFF_CODE
        defaultEmployeeShouldNotBeFound("employeeStaffCode.in=" + UPDATED_EMPLOYEE_STAFF_CODE);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeStaffCodeIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeStaffCode is not null
        defaultEmployeeShouldBeFound("employeeStaffCode.specified=true");

        // Get all the employeeList where employeeStaffCode is null
        defaultEmployeeShouldNotBeFound("employeeStaffCode.specified=false");
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeEmail equals to DEFAULT_EMPLOYEE_EMAIL
        defaultEmployeeShouldBeFound("employeeEmail.equals=" + DEFAULT_EMPLOYEE_EMAIL);

        // Get all the employeeList where employeeEmail equals to UPDATED_EMPLOYEE_EMAIL
        defaultEmployeeShouldNotBeFound("employeeEmail.equals=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeEmailIsInShouldWork() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeEmail in DEFAULT_EMPLOYEE_EMAIL or UPDATED_EMPLOYEE_EMAIL
        defaultEmployeeShouldBeFound("employeeEmail.in=" + DEFAULT_EMPLOYEE_EMAIL + "," + UPDATED_EMPLOYEE_EMAIL);

        // Get all the employeeList where employeeEmail equals to UPDATED_EMPLOYEE_EMAIL
        defaultEmployeeShouldNotBeFound("employeeEmail.in=" + UPDATED_EMPLOYEE_EMAIL);
    }

    @Test
    @Transactional
    public void getAllEmployeesByEmployeeEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        // Get all the employeeList where employeeEmail is not null
        defaultEmployeeShouldBeFound("employeeEmail.specified=true");

        // Get all the employeeList where employeeEmail is null
        defaultEmployeeShouldNotBeFound("employeeEmail.specified=false");
    }
    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultEmployeeShouldBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME)))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].employeeRole").value(hasItem(DEFAULT_EMPLOYEE_ROLE)))
            .andExpect(jsonPath("$.[*].employeeStaffCode").value(hasItem(DEFAULT_EMPLOYEE_STAFF_CODE)))
            .andExpect(jsonPath("$.[*].employeeSignatureContentType").value(hasItem(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].employeeSignature").value(hasItem(Base64Utils.encodeToString(DEFAULT_EMPLOYEE_SIGNATURE))))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL)));

        // Check, that the count call also returns 1
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultEmployeeShouldNotBeFound(String filter) throws Exception {
        restEmployeeMockMvc.perform(get("/api/employees?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restEmployeeMockMvc.perform(get("/api/employees/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingEmployee() throws Exception {
        // Get the employee
        restEmployeeMockMvc.perform(get("/api/employees/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Update the employee
        Employee updatedEmployee = employeeRepository.findById(employee.getId()).get();
        // Disconnect from session so that the updates on updatedEmployee are not directly saved in db
        em.detach(updatedEmployee);
        updatedEmployee
            .employeeName(UPDATED_EMPLOYEE_NAME)
            .serviceOutletCode(UPDATED_SERVICE_OUTLET_CODE)
            .employeeRole(UPDATED_EMPLOYEE_ROLE)
            .employeeStaffCode(UPDATED_EMPLOYEE_STAFF_CODE)
            .employeeSignature(UPDATED_EMPLOYEE_SIGNATURE)
            .employeeSignatureContentType(UPDATED_EMPLOYEE_SIGNATURE_CONTENT_TYPE)
            .employeeEmail(UPDATED_EMPLOYEE_EMAIL);
        EmployeeDTO employeeDTO = employeeMapper.toDto(updatedEmployee);

        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isOk());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);
        Employee testEmployee = employeeList.get(employeeList.size() - 1);
        assertThat(testEmployee.getEmployeeName()).isEqualTo(UPDATED_EMPLOYEE_NAME);
        assertThat(testEmployee.getServiceOutletCode()).isEqualTo(UPDATED_SERVICE_OUTLET_CODE);
        assertThat(testEmployee.getEmployeeRole()).isEqualTo(UPDATED_EMPLOYEE_ROLE);
        assertThat(testEmployee.getEmployeeStaffCode()).isEqualTo(UPDATED_EMPLOYEE_STAFF_CODE);
        assertThat(testEmployee.getEmployeeSignature()).isEqualTo(UPDATED_EMPLOYEE_SIGNATURE);
        assertThat(testEmployee.getEmployeeSignatureContentType()).isEqualTo(UPDATED_EMPLOYEE_SIGNATURE_CONTENT_TYPE);
        assertThat(testEmployee.getEmployeeEmail()).isEqualTo(UPDATED_EMPLOYEE_EMAIL);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).save(testEmployee);
    }

    @Test
    @Transactional
    public void updateNonExistingEmployee() throws Exception {
        int databaseSizeBeforeUpdate = employeeRepository.findAll().size();

        // Create the Employee
        EmployeeDTO employeeDTO = employeeMapper.toDto(employee);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restEmployeeMockMvc.perform(put("/api/employees")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(employeeDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Employee in the database
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeUpdate);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(0)).save(employee);
    }

    @Test
    @Transactional
    public void deleteEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);

        int databaseSizeBeforeDelete = employeeRepository.findAll().size();

        // Delete the employee
        restEmployeeMockMvc.perform(delete("/api/employees/{id}", employee.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database is empty
        List<Employee> employeeList = employeeRepository.findAll();
        assertThat(employeeList).hasSize(databaseSizeBeforeDelete - 1);

        // Validate the Employee in Elasticsearch
        verify(mockEmployeeSearchRepository, times(1)).deleteById(employee.getId());
    }

    @Test
    @Transactional
    public void searchEmployee() throws Exception {
        // Initialize the database
        employeeRepository.saveAndFlush(employee);
        when(mockEmployeeSearchRepository.search(queryStringQuery("id:" + employee.getId()), PageRequest.of(0, 20)))
            .thenReturn(new PageImpl<>(Collections.singletonList(employee), PageRequest.of(0, 1), 1));
        // Search the employee
        restEmployeeMockMvc.perform(get("/api/_search/employees?query=id:" + employee.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(employee.getId().intValue())))
            .andExpect(jsonPath("$.[*].employeeName").value(hasItem(DEFAULT_EMPLOYEE_NAME)))
            .andExpect(jsonPath("$.[*].serviceOutletCode").value(hasItem(DEFAULT_SERVICE_OUTLET_CODE)))
            .andExpect(jsonPath("$.[*].employeeRole").value(hasItem(DEFAULT_EMPLOYEE_ROLE)))
            .andExpect(jsonPath("$.[*].employeeStaffCode").value(hasItem(DEFAULT_EMPLOYEE_STAFF_CODE)))
            .andExpect(jsonPath("$.[*].employeeSignatureContentType").value(hasItem(DEFAULT_EMPLOYEE_SIGNATURE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].employeeSignature").value(hasItem(Base64Utils.encodeToString(DEFAULT_EMPLOYEE_SIGNATURE))))
            .andExpect(jsonPath("$.[*].employeeEmail").value(hasItem(DEFAULT_EMPLOYEE_EMAIL)));
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Employee.class);
        Employee employee1 = new Employee();
        employee1.setId(1L);
        Employee employee2 = new Employee();
        employee2.setId(employee1.getId());
        assertThat(employee1).isEqualTo(employee2);
        employee2.setId(2L);
        assertThat(employee1).isNotEqualTo(employee2);
        employee1.setId(null);
        assertThat(employee1).isNotEqualTo(employee2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(EmployeeDTO.class);
        EmployeeDTO employeeDTO1 = new EmployeeDTO();
        employeeDTO1.setId(1L);
        EmployeeDTO employeeDTO2 = new EmployeeDTO();
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO2.setId(employeeDTO1.getId());
        assertThat(employeeDTO1).isEqualTo(employeeDTO2);
        employeeDTO2.setId(2L);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
        employeeDTO1.setId(null);
        assertThat(employeeDTO1).isNotEqualTo(employeeDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(employeeMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(employeeMapper.fromId(null)).isNull();
    }
}
