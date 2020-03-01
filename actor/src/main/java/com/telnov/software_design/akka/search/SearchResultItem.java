package com.telnov.software_design.akka.search;

import java.beans.ConstructorProperties;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SearchResultItem {

    @Nonnull
    private final String url;
    @Nonnull
    private final String title;
    @Nonnull
    private final String text;

    @ConstructorProperties({
            "url",
            "title",
            "text"
    })
    public SearchResultItem(String url, String title, String text) {
        this.url = url;
        this.title = title;
        this.text = text;
    }

    @Nonnull
    public String getUrl() {
        return url;
    }

    @Nonnull
    public String getTitle() {
        return title;
    }

    @Nonnull
    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "SearchResult{" +
                "url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
