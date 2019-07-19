package io.github.assets.domain;


import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;

import org.springframework.data.elasticsearch.annotations.FieldType;
import java.io.Serializable;
import java.util.Objects;

/**
 * A ServiceOutlet.
 */
@Entity
@Table(name = "service_outlet")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@org.springframework.data.elasticsearch.annotations.Document(indexName = "serviceoutlet")
public class ServiceOutlet implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @org.springframework.data.elasticsearch.annotations.Field(type = FieldType.Keyword)
    private Long id;

    @NotNull
    @Column(name = "service_outlet_code", nullable = false, unique = true)
    private String serviceOutletCode;

    @NotNull
    @Column(name = "service_outlet_designation", nullable = false, unique = true)
    private String serviceOutletDesignation;

    @Column(name = "description")
    private String description;

    @Column(name = "location")
    private String location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public ServiceOutlet serviceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
        return this;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getServiceOutletDesignation() {
        return serviceOutletDesignation;
    }

    public ServiceOutlet serviceOutletDesignation(String serviceOutletDesignation) {
        this.serviceOutletDesignation = serviceOutletDesignation;
        return this;
    }

    public void setServiceOutletDesignation(String serviceOutletDesignation) {
        this.serviceOutletDesignation = serviceOutletDesignation;
    }

    public String getDescription() {
        return description;
    }

    public ServiceOutlet description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public ServiceOutlet location(String location) {
        this.location = location;
        return this;
    }

    public void setLocation(String location) {
        this.location = location;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ServiceOutlet)) {
            return false;
        }
        return id != null && id.equals(((ServiceOutlet) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "ServiceOutlet{" +
            "id=" + getId() +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", serviceOutletDesignation='" + getServiceOutletDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
