package com.telnov.software_design.testing_methods.model;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

/**
 * Data transfer object for hash tah statistic per hour.
 */
@ParametersAreNonnullByDefault
public class HashTagInfoDTO {

    /**
     * Hour of hash tag information.
     */
    private final int hour;

    /**
     * Number of hash tags in this hour.
     */
    private final long count;

    public HashTagInfoDTO(int hour, long count) {
        this.hour = hour;
        this.count = count;
    }

    public int getHour() {
        return hour;
    }

    public long getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTagInfoDTO that = (HashTagInfoDTO) o;
        return hour == that.hour &&
                count == that.count;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hour, count);
    }

    @Override
    public String toString() {
        return "HashTagInfoDTO{" +
                "hour=" + hour +
                ", count=" + count +
                '}';
    }
}
