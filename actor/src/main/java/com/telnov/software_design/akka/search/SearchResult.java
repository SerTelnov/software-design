package com.telnov.software_design.akka.search;

import java.util.List;

public class SearchResult {

    private final List<SearchResultItem> searchResult;
    private final SearchAggregator aggregator;

    public SearchResult(List<SearchResultItem> searchResult, SearchAggregator aggregator) {
        this.searchResult = searchResult;
        this.aggregator = aggregator;
    }

    public List<SearchResultItem> getSearchResult() {
        return searchResult;
    }

    public SearchAggregator getAggregator() {
        return aggregator;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "searchResult=" + searchResult +
                ", aggregator=" + aggregator +
                '}';
    }
}
