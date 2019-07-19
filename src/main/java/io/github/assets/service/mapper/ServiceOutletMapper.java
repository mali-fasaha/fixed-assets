package io.github.assets.service.mapper;

import io.github.assets.domain.*;
import io.github.assets.service.dto.ServiceOutletDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity {@link ServiceOutlet} and its DTO {@link ServiceOutletDTO}.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ServiceOutletMapper extends EntityMapper<ServiceOutletDTO, ServiceOutlet> {



    default ServiceOutlet fromId(Long id) {
        if (id == null) {
            return null;
        }
        ServiceOutlet serviceOutlet = new ServiceOutlet();
        serviceOutlet.setId(id);
        return serviceOutlet;
    }
}
