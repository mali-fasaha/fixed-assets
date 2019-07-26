package io.github.assets.repository.search;

import io.github.assets.domain.MessageToken;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link MessageToken} entity.
 */
public interface MessageTokenSearchRepository extends ElasticsearchRepository<MessageToken, Long> {
}
