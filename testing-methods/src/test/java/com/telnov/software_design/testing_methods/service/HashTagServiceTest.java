package com.telnov.software_design.testing_methods.service;

import com.telnov.software_design.testing_methods.FunctionalTest;
import com.telnov.software_design.testing_methods.client.ClientApi;
import com.telnov.software_design.testing_methods.model.HashTagInfoDTO;
import com.telnov.software_design.testing_methods.model.NewsFeed;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;

class HashTagServiceTest extends FunctionalTest {

    @Mock
    private ClientApi clientApi;
    private HashTagService service;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        service = new HashTagService(clientApi);
    }

    @Test
    void getHashTagInfoTest() {
        Mockito.when(clientApi.getNewsFeedsByHashTag(anyString(), anyInt()))
                .thenReturn(
                        Arrays.asList(
                                new NewsFeed(1L, Instant.parse("2019-10-13T17:59:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T17:30:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T16:00:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T15:49:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T15:59:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T14:59:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T14:59:00.333Z")),
                                new NewsFeed(1L, Instant.parse("2019-10-13T14:20:00.333Z"))
                        )
                );

        List<HashTagInfoDTO> actual = service.getHashTagPerHour("best_hashtag", 6);
        assertThat(actual).hasSize(4);
        assertThat(actual).containsExactly(
            new HashTagInfoDTO(17, 2),
            new HashTagInfoDTO(16, 1),
            new HashTagInfoDTO(15, 2),
            new HashTagInfoDTO(14, 3)
        );
    }
}
