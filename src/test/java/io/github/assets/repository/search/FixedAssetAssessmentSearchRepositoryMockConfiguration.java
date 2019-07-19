package io.github.assets.repository.search;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Configuration;

/**
 * Configure a Mock version of {@link FixedAssetAssessmentSearchRepository} to test the
 * application without starting Elasticsearch.
 */
@Configuration
public class FixedAssetAssessmentSearchRepositoryMockConfiguration {

    @MockBean
    private FixedAssetAssessmentSearchRepository mockFixedAssetAssessmentSearchRepository;

}
