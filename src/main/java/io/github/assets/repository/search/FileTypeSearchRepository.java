package io.github.assets.repository.search;

import io.github.assets.domain.FileType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FileType} entity.
 */
public interface FileTypeSearchRepository extends ElasticsearchRepository<FileType, Long> {
}
