package com.telnov.software_design.akka.search;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class SearchClientStub implements SearchClient {

    private static final int NUMBER_OF_ITEM_IN_REQUEST = 15;

    private final SearchAggregator searchAggregator;

    public SearchClientStub(SearchAggregator searchAggregator) {
        this.searchAggregator = searchAggregator;
    }

    @Override
    public SearchResult search(String searchRequest) {
        final var response = IntStream.range(0, NUMBER_OF_ITEM_IN_REQUEST)
                .mapToObj(i -> new SearchResultItem(
                        generateUrl(i),
                        generateTitle(i),
                        generateText(i)
                )).collect(Collectors.toList());

        return new SearchResult(response, searchAggregator);
    }

    private static String generateUrl(final int index) {
        return String.format("http://url_number_%1$d/some/path/number/%1$d", index);
    }

    private static String generateTitle(final int index) {
        return "Some response title number " + index;
    }

    private static String generateText(final int index) {
        return "Some cool text in response #" + index;
    }
}
