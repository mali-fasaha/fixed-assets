package io.github.assets.repository.search;

import io.github.assets.domain.CwipTransfer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link CwipTransfer} entity.
 */
public interface CwipTransferSearchRepository extends ElasticsearchRepository<CwipTransfer, Long> {
}
