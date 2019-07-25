package io.github.assets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link AssetDisposalSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class AssetDisposalSearchRepositoryMockConfiguration {

    @MockBean
    private AssetDisposalSearchRepository mockAssetDisposalSearchRepository;

}
