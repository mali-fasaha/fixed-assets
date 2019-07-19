package io.github.assets.repository.search;

import io.github.assets.domain.ServiceOutlet;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the {@link ServiceOutlet} entity.
 */
public interface ServiceOutletSearchRepository extends ElasticsearchRepository<ServiceOutlet, Long> {
}
