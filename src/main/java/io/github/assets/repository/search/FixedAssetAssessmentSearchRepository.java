package io.github.assets.repository.search;

import io.github.assets.domain.FixedAssetAssessment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetAssessment} entity.
 */
public interface FixedAssetAssessmentSearchRepository extends ElasticsearchRepository<FixedAssetAssessment, Long> {
}
