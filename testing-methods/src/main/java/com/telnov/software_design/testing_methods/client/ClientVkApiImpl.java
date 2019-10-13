package com.telnov.software_design.testing_methods.client;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.telnov.software_design.testing_methods.model.NewsFeed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@ParametersAreNonnullByDefault
public class ClientVkApiImpl implements ClientApi {

    private static final String VK_SEARCH_URL = "newsfeed.search";
    private static final int HOUR_TO_SECONDS = 3600;
    private static final String HASH_TAG_SYMBOL = "#";

    private final RestTemplate restTemplate;
    private final String vkApiUrl;

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.app.id}")
    private String uid;

    @Value("${vk.service.token}")
    private String token;

    @Autowired
    public ClientVkApiImpl(
            RestTemplate restTemplate,
            @Value("${vk.base.url}")
            String vkApiUrl
    ) {
        this.restTemplate = restTemplate;
        this.vkApiUrl = vkApiUrl;
    }

    @Override
    public List<NewsFeed> getNewsFeedsByHashTag(String hashTagTxt, int timeInterval) {
        String url = buildUrl(hashTagTxt, timeInterval);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            if (!response.getStatusCode().equals(HttpStatus.OK)) {
                throw new RuntimeException("Response was with status: " + response.getStatusCode());
            } else if (response.getBody() != null) {
                return parseJsonToNewFeeds(response.getBody());
            }
        } catch (RestClientException ex) {
            throw new RuntimeException("Can't get information for hashTag='" + hashTagTxt + "'");
        }
        return Collections.emptyList();
    }

    private List<NewsFeed> parseJsonToNewFeeds(String json) {
        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);
        JsonArray jsonArray = jsonObject
                .getAsJsonObject("response")
                .getAsJsonArray("items");

        List<NewsFeed> newsFeeds = new ArrayList<>();

        jsonArray.forEach(jsonElement -> {
            JsonObject currObject = jsonElement.getAsJsonObject();
            NewsFeed newsFeed = new NewsFeed(
                    currObject.get("id").getAsInt(),
                    Instant.ofEpochSecond(currObject.get("date").getAsInt())
            );

            newsFeeds.add(newsFeed);
        });

        return newsFeeds;
    }

    private String buildUrl(String hashTagTxt, int timeInterval) {
        return UriComponentsBuilder.fromHttpUrl(vkApiUrl)
                .path(VK_SEARCH_URL)
                .queryParam("v", apiVersion)
                .queryParam("uid", uid)
                .queryParam("access_token", token)
                .queryParam("q", HASH_TAG_SYMBOL + hashTagTxt)
                .queryParam("start_time", convertTimeIntervalToUnixTime(timeInterval))
                .toUriString();
    }

    private static int convertTimeIntervalToUnixTime(int timeInterval) {
        return (int) Instant.now()
                .minusSeconds(timeInterval * HOUR_TO_SECONDS)
                .getEpochSecond();
    }
}
