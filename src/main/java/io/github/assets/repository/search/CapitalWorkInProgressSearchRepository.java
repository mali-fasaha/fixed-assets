package io.github.assets.repository.search;

import io.github.assets.domain.CapitalWorkInProgress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CapitalWorkInProgress} entity.
 */
public interface CapitalWorkInProgressSearchRepository extends ElasticsearchRepository<CapitalWorkInProgress, Long> {
}
