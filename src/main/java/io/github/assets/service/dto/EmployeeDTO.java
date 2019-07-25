package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the {@link io.github.assets.domain.Employee} entity.
 */
public class EmployeeDTO implements Serializable {

    private Long id;

    @NotNull
    private String employeeName;

    private String serviceOutletCode;

    private String employeeRole;

    private String employeeStaffCode;

    @Lob
    private byte[] employeeSignature;

    private String employeeSignatureContentType;
    private String employeeEmail;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }

    public String getEmployeeStaffCode() {
        return employeeStaffCode;
    }

    public void setEmployeeStaffCode(String employeeStaffCode) {
        this.employeeStaffCode = employeeStaffCode;
    }

    public byte[] getEmployeeSignature() {
        return employeeSignature;
    }

    public void setEmployeeSignature(byte[] employeeSignature) {
        this.employeeSignature = employeeSignature;
    }

    public String getEmployeeSignatureContentType() {
        return employeeSignatureContentType;
    }

    public void setEmployeeSignatureContentType(String employeeSignatureContentType) {
        this.employeeSignatureContentType = employeeSignatureContentType;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        EmployeeDTO employeeDTO = (EmployeeDTO) o;
        if (employeeDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), employeeDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "EmployeeDTO{" +
            "id=" + getId() +
            ", employeeName='" + getEmployeeName() + "'" +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", employeeRole='" + getEmployeeRole() + "'" +
            ", employeeStaffCode='" + getEmployeeStaffCode() + "'" +
            ", employeeSignature='" + getEmployeeSignature() + "'" +
            ", employeeEmail='" + getEmployeeEmail() + "'" +
            "}";
    }
}
