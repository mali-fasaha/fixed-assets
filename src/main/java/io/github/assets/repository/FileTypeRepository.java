package io.github.assets.repository;

import io.github.assets.domain.FileType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the FileType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FileTypeRepository extends JpaRepository<FileType, Long> {

}
