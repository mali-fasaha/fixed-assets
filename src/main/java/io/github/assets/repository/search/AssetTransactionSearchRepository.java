package io.github.assets.repository.search;

import io.github.assets.domain.AssetTransaction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link AssetTransaction} entity.
 */
public interface AssetTransactionSearchRepository extends ElasticsearchRepository<AssetTransaction, Long> {
}
