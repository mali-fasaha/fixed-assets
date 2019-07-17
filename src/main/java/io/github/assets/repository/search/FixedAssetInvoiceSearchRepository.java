package io.github.assets.repository.search;

import io.github.assets.domain.FixedAssetInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link FixedAssetInvoice} entity.
 */
public interface FixedAssetInvoiceSearchRepository extends ElasticsearchRepository<FixedAssetInvoice, Long> {
}
