package com.telnov.software_design.testing_methods.model;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.time.Instant;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class NewsFeed {

    private final long id;
    @Nonnull
    private final Instant instant;

    public NewsFeed(long id, Instant instant) {
        this.id = id;
        this.instant = instant;
    }

    public long getId() {
        return id;
    }

    @Nonnull
    public Instant getInstant() {
        return instant;
    }

    @Override
    public String toString() {
        return "NewsFeed{" +
                "id=" + id +
                ", instant=" + instant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NewsFeed newsFeed = (NewsFeed) o;
        return id == newsFeed.id &&
                instant.equals(newsFeed.instant);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, instant);
    }
}
