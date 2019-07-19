package io.github.assets.repository.search;

import io.github.assets.domain.FixedAssetItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetItem} entity.
 */
public interface FixedAssetItemSearchRepository extends ElasticsearchRepository<FixedAssetItem, Long> {
}
