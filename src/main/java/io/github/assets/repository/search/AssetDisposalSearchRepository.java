package io.github.assets.repository.search;

import io.github.assets.domain.AssetDisposal;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AssetDisposal} entity.
 */
public interface AssetDisposalSearchRepository extends ElasticsearchRepository<AssetDisposal, Long> {
}
