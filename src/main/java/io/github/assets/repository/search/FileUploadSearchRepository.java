package io.github.assets.repository.search;

import io.github.assets.domain.FileUpload;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FileUpload} entity.
 */
public interface FileUploadSearchRepository extends ElasticsearchRepository<FileUpload, Long> {
}
