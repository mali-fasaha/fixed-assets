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
 * Criteria class for the {@link io.github.assets.domain.ServiceOutlet} entity. This class is used
 * in {@link io.github.assets.web.rest.ServiceOutletResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /service-outlets?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ServiceOutletCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter serviceOutletCode;

    private StringFilter serviceOutletDesignation;

    private StringFilter description;

    private StringFilter location;

    public ServiceOutletCriteria(){
    }

    public ServiceOutletCriteria(ServiceOutletCriteria other){
        this.id = other.id == null ? null : other.id.copy();
        this.serviceOutletCode = other.serviceOutletCode == null ? null : other.serviceOutletCode.copy();
        this.serviceOutletDesignation = other.serviceOutletDesignation == null ? null : other.serviceOutletDesignation.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.location = other.location == null ? null : other.location.copy();
    }

    @Override
    public ServiceOutletCriteria copy() {
        return new ServiceOutletCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(StringFilter serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public StringFilter getServiceOutletDesignation() {
        return serviceOutletDesignation;
    }

    public void setServiceOutletDesignation(StringFilter serviceOutletDesignation) {
        this.serviceOutletDesignation = serviceOutletDesignation;
    }

    public StringFilter getDescription() {
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getLocation() {
        return location;
    }

    public void setLocation(StringFilter location) {
        this.location = location;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ServiceOutletCriteria that = (ServiceOutletCriteria) o;
        return
            Objects.equals(id, that.id) &&
            Objects.equals(serviceOutletCode, that.serviceOutletCode) &&
            Objects.equals(serviceOutletDesignation, that.serviceOutletDesignation) &&
            Objects.equals(description, that.description) &&
            Objects.equals(location, that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(
        id,
        serviceOutletCode,
        serviceOutletDesignation,
        description,
        location
        );
    }

    @Override
    public String toString() {
        return "ServiceOutletCriteria{" +
                (id != null ? "id=" + id + ", " : "") +
                (serviceOutletCode != null ? "serviceOutletCode=" + serviceOutletCode + ", " : "") +
                (serviceOutletDesignation != null ? "serviceOutletDesignation=" + serviceOutletDesignation + ", " : "") +
                (description != null ? "description=" + description + ", " : "") +
                (location != null ? "location=" + location + ", " : "") +
            "}";
    }

}
