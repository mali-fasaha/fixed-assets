package io.github.assets.repository.search;

import io.github.assets.domain.AssetAcquisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AssetAcquisition} entity.
 */
public interface AssetAcquisitionSearchRepository extends ElasticsearchRepository<AssetAcquisition, Long> {
}
