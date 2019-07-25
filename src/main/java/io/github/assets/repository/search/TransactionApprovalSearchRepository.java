package io.github.assets.repository.search;

import io.github.assets.domain.TransactionApproval;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link TransactionApproval} entity.
 */
public interface TransactionApprovalSearchRepository extends ElasticsearchRepository<TransactionApproval, Long> {
}
