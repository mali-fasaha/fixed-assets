package io.github.assets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AssetDepreciationSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AssetDepreciationSearchRepositoryMockConfiguration {

    @MockBean
    private AssetDepreciationSearchRepository mockAssetDepreciationSearchRepository;

}
