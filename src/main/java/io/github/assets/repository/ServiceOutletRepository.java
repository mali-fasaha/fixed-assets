package io.github.assets.repository;

import io.github.assets.domain.ServiceOutlet;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the ServiceOutlet entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ServiceOutletRepository extends JpaRepository<ServiceOutlet, Long>, JpaSpecificationExecutor<ServiceOutlet> {

}
