package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.Point;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.hamcrest.MatcherAssert.assertThat;

class PointCalculatorTest {

    private PointCalculator pointCalculator;

    @Test
    void test() {
        pointCalculator = new PointCalculator(
                600,
                400,
                4
        );

        List<Point> points = IntStream.range(0, 4)
                .mapToObj(i -> pointCalculator.nextPoint())
                .collect(Collectors.toList());
        assertThat(
                points,
                Matchers.contains(
                        Point.of(100, 100),
                        Point.of(300, 100),
                        Point.of(300, 300),
                        Point.of(100, 300)
                )
        );
    }
}