package io.github.assets.service.dto;

import java.io.Serializable;
import java.util.Objects;
import io.github.jhipster.service.Criteria;
import io.github.jhipster.service.filter.BooleanFilter;
import io.github.jhipster.service.filter.DoubleFilter;
import io.github.jhipster.service.filter.Filter;
import io.github.jhipster.service.filter.FloatFilter;
import io.github.jhipster.service.filter.IntegerFilter;
import io.github.jhipster.service.filter.LongFilter;
import io.github.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link io.github.assets.domain.Employee} entity. This class is used
 * in {@link io.github.assets.web.rest.EmployeeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /employees?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class EmployeeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter employeeName;

    private StringFilter serviceOutletCode;

    private StringFilter employeeRole;

    private StringFilter employeeStaffCode;

    private StringFilter employeeEmail;

    public EmployeeCriteria(){
    }

    public EmployeeCriteria(EmployeeCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.employeeName = other.employeeName == null ? null : other.employeeName.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.employeeRole = other.employeeRole == null ? null : other.employeeRole.copy();
        this.employeeStaffCode = other.employeeStaffCode == null ? null : other.employeeStaffCode.copy();
        this.employeeEmail = other.employeeEmail == null ? null : other.employeeEmail.copy();
    }

    @Override
    public EmployeeCriteria copy() {
        return new EmployeeCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(StringFilter employeeName) {
        this.employeeName = employeeName;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public StringFilter getEmployeeRole() {
        return employeeRole;
    }

    public void setEmployeeRole(StringFilter employeeRole) {
        this.employeeRole = employeeRole;
    }

    public StringFilter getEmployeeStaffCode() {
        return employeeStaffCode;
    }

    public void setEmployeeStaffCode(StringFilter employeeStaffCode) {
        this.employeeStaffCode = employeeStaffCode;
    }

    public StringFilter getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(StringFilter employeeEmail) {
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
        final EmployeeCriteria that = (EmployeeCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(employeeName, that.employeeName) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(employeeRole, that.employeeRole) &&
            Objects.equals(employeeStaffCode, that.employeeStaffCode) &&
            Objects.equals(employeeEmail, that.employeeEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        employeeName,
        serviceOutletCode,
        employeeRole,
        employeeStaffCode,
        employeeEmail
        );
    }

    @Override
    public String toString() {
        return "EmployeeCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (employeeName != null ? "employeeName=" + employeeName + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (employeeRole != null ? "employeeRole=" + employeeRole + ", " : "") +
                (employeeStaffCode != null ? "employeeStaffCode=" + employeeStaffCode + ", " : "") +
                (employeeEmail != null ? "employeeEmail=" + employeeEmail + ", " : "") +
            "}";
    }

}
