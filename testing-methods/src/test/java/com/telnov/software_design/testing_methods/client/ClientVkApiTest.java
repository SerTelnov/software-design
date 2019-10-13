package com.telnov.software_design.testing_methods.client;

import com.telnov.software_design.testing_methods.FunctionalTest;
import com.telnov.software_design.testing_methods.model.NewsFeed;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


class ClientVkApiTest extends FunctionalTest {

    private static final String BASE_URL = "http://lala.ru/api/";
    private static final String PATH_URL = "newsfeed.search";
    private static final String HASH_TAG_TXT = "Apple";
    private static String responseData;

    private ClientVkApiImpl clientApi;
    private MockRestServiceServer mockService;

    @Autowired
    private RestTemplate restTemplate;

    @BeforeAll
    static void setupTestData() throws Exception {
        Path path = Paths.get(ClientVkApiTest.class
                .getResource("/response/newsfeed.json")
                .toURI()
        );
        responseData = Files.lines(path)
                .collect(Collectors.joining());
    }

    @BeforeEach
    void setup() {
        this.mockService = MockRestServiceServer.createServer(restTemplate);
        this.clientApi = new ClientVkApiImpl(restTemplate, BASE_URL);
    }

    @Test
    void findByHashTagTest() {
        String url = getUrl(HASH_TAG_TXT, 24);

        mockService.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(responseData)
                );

        List<NewsFeed> newsFeeds = clientApi.getNewsFeedsByHashTag(HASH_TAG_TXT);
        mockService.verify();

        assertThat(newsFeeds).hasSize(4);
        assertThat(newsFeeds).contains(
                new NewsFeed(
                        2483,
                        Instant.ofEpochSecond(1570986720)
                ),
                new NewsFeed(
                        33618,
                        Instant.ofEpochSecond(1570986407)
                ),
                new NewsFeed(
                        1519,
                        Instant.ofEpochSecond(1570986080)
                ),
                new NewsFeed(
                        4727,
                        Instant.ofEpochSecond(1570986008)
                )
        );
    }

    @Test
    void findByHashTagAndTime() {
        String url = getUrl(HASH_TAG_TXT, 0);

        String jsonResponse = /*language=JSON*/ "" +
                "{" +
                "  \"response\": {" +
                "    \"items\": []," +
                "    \"count\": 0," +
                "    \"total_count\": 42," +
                "    \"next_from\": \"30/-84319108_431\"" +
                "  }" +
                "}";

        mockService.expect(requestTo(url))
                .andExpect(method(HttpMethod.GET))
                .andRespond(withStatus(HttpStatus.OK)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(jsonResponse)
                );

        List<NewsFeed> newsFeeds = clientApi.getNewsFeedsByHashTag(HASH_TAG_TXT, 0);
        assertThat(newsFeeds).isEmpty();
    }

    private String getUrl(String hashTag, int hourInterval) {
        final int startTime = (int) Instant.now()
                .minusSeconds(hourInterval * 60 * 60)
                .getEpochSecond();

        return getUrlBuilder()
                .queryParam("q", "#" + hashTag)
                .queryParam("start_time", startTime)
                .toUriString();
    }

    private UriComponentsBuilder getUrlBuilder() {
        return UriComponentsBuilder.fromHttpUrl(BASE_URL)
                .path(PATH_URL)
                .queryParam("v", null)
                .queryParam("uid", null)
                .queryParam("access_token", null);
    }
}
