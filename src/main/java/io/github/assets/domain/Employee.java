package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A Employee.
 */
@Entity
@Table(name = "employee")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "employee")
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "employee_name", nullable = false)
    private String employeeName;

    @Column(name = "service_outlet_code")
    private String serviceOutletCode;

    @Column(name = "employee_role")
    private String employeeRole;

    @Column(name = "employee_staff_code")
    private String employeeStaffCode;

    @Lob
    @Column(name = "employee_signature")
    private byte[] employeeSignature;

    @Column(name = "employee_signature_content_type")
    private String employeeSignatureContentType;

    @Column(name = "employee_email")
    private String employeeEmail;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Employee employeeName(String employeeName) {
        this.employeeName = employeeName;
        return this;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public Employee serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public Employee employeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
        return this;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeeStaffCode() {
        return employeeStaffCode;
    }

    public Employee employeeStaffCode(String employeeStaffCode) {
        this.employeeStaffCode = employeeStaffCode;
        return this;
    }

    public void setEmployeeStaffCode(String employeeStaffCode) {
        this.employeeStaffCode = employeeStaffCode;
    }

    public byte[] getEmployeeSignature() {
        return employeeSignature;
    }

    public Employee employeeSignature(byte[] employeeSignature) {
        this.employeeSignature = employeeSignature;
        return this;
    }

    public void setEmployeeSignature(byte[] employeeSignature) {
        this.employeeSignature = employeeSignature;
    }

    public String getEmployeeSignatureContentType() {
        return employeeSignatureContentType;
    }

    public Employee employeeSignatureContentType(String employeeSignatureContentType) {
        this.employeeSignatureContentType = employeeSignatureContentType;
        return this;
    }

    public void setEmployeeSignatureContentType(String employeeSignatureContentType) {
        this.employeeSignatureContentType = employeeSignatureContentType;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public Employee employeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
        return this;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Employee)) {
            return false;
        }
        return id != null && id.equals(((Employee) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Employee{" +
            "id=" + getId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", employeeRole='" + getEmployeeRole() + "'" +
            ", employeeStaffCode='" + getEmployeeStaffCode() + "'" +
            ", employeeSignature='" + getEmployeeSignature() + "'" +
            ", employeeSignatureContentType='" + getEmployeeSignatureContentType() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            "}";
    }
}
