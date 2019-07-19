package io.github.assets.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link io.github.assets.domain.ServiceOutlet} entity.
 */
public class ServiceOutletDTO implements Serializable {

    private Long id;

    @NotNull
    private String serviceOutletCode;

    @NotNull
    private String serviceOutletDesignation;

    private String description;

    private String location;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getServiceOutletCode() {
        return serviceOutletCode;
    }

    public void setServiceOutletCode(String serviceOutletCode) {
        this.serviceOutletCode = serviceOutletCode;
    }

    public String getServiceOutletDesignation() {
        return serviceOutletDesignation;
    }

    public void setServiceOutletDesignation(String serviceOutletDesignation) {
        this.serviceOutletDesignation = serviceOutletDesignation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
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

        ServiceOutletDTO serviceOutletDTO = (ServiceOutletDTO) o;
        if (serviceOutletDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), serviceOutletDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ServiceOutletDTO{" +
            "id=" + getId() +
            ", serviceOutletCode='" + getServiceOutletCode() + "'" +
            ", serviceOutletDesignation='" + getServiceOutletDesignation() + "'" +
            ", description='" + getDescription() + "'" +
            ", location='" + getLocation() + "'" +
            "}";
    }
}
