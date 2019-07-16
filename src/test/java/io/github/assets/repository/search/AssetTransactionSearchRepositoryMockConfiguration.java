package io.github.assets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AssetTransactionSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AssetTransactionSearchRepositoryMockConfiguration {

    @MockBean
    private AssetTransactionSearchRepository mockAssetTransactionSearchRepository;

}
