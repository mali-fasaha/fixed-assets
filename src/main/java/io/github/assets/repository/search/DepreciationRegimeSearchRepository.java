package io.github.assets.repository.search;

import io.github.assets.domain.DepreciationRegime;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link DepreciationRegime} entity.
 */
public interface DepreciationRegimeSearchRepository extends ElasticsearchRepository<DepreciationRegime, Long> {
}
