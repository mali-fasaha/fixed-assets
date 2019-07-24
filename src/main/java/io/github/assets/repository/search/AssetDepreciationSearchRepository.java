package io.github.assets.repository.search;

import io.github.assets.domain.AssetDepreciation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AssetDepreciation} entity.
 */
public interface AssetDepreciationSearchRepository extends ElasticsearchRepository<AssetDepreciation, Long> {
}
