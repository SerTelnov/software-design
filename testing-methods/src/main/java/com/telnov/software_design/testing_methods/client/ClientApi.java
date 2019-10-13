package com.telnov.software_design.testing_methods.client;

import com.telnov.software_design.testing_methods.model.NewsFeed;

import java.util.List;

/**
 * Client which will get information about NewsFeeds.
 */
public interface ClientApi {

    int MAX_HOURS_INTERVAL = 24;

    /**
     * Get NewsFeed by hash tag text.
     *
     * @param hashTagTxt text of hash tag (ex: "vk_api")
     */
    default List<NewsFeed> getNewsFeedsByHashTag(String hashTagTxt) {
        return getNewsFeedsByHashTag(hashTagTxt, MAX_HOURS_INTERVAL);
    }

    /**
     * Get NewsFeed by hash tag text.
     *
     * @param hashTagTxt text of hash tag (ex: "vk_api")
     * @param timeInterval interval of searching (from 1 hour to 24 hours)
     */
    List<NewsFeed> getNewsFeedsByHashTag(String hashTagTxt, int timeInterval);
}
