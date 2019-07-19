package io.github.assets.repository.search;

import io.github.assets.domain.FixedAssetCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetCategory} entity.
 */
public interface FixedAssetCategorySearchRepository extends ElasticsearchRepository<FixedAssetCategory, Long> {
}
