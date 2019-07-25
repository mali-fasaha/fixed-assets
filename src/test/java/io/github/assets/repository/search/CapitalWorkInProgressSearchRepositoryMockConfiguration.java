package io.github.assets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link CapitalWorkInProgressSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class CapitalWorkInProgressSearchRepositoryMockConfiguration {

    @MockBean
    private CapitalWorkInProgressSearchRepository mockCapitalWorkInProgressSearchRepository;

}
