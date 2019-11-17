package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.Point;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

public class PointCalculatorTest {

    private PointCalculator pointCalculator;

    static Stream<Arguments> nextPointArgs() {
        return Stream.of(
                Arguments.of(
                        1,
                        Collections.singletonList(Point.of(50, 50))
                ),
                Arguments.of(
                        2,
                        Arrays.asList(
                                Point.of(50, 50),
                                Point.of(350, 50)
                        )
                ),
                Arguments.of(
                        3,
                        Arrays.asList(
                                Point.of(50, 50),
                                Point.of(350, 50),
                                Point.of(350, 350)
                        )
                ),
                Arguments.of(
                        4,
                        Arrays.asList(
                                Point.of(50, 50),
                                Point.of(350, 50),
                                Point.of(350, 350),
                                Point.of(50, 350)
                        )
                ),
                Arguments.of(
                        5,
                        Arrays.asList(
                                Point.of(50, 50),
                                Point.of(200, 50),
                                Point.of(350, 50),
                                Point.of(350, 200),
                                Point.of(350, 350)
                        )
                ),
                Arguments.of(
                        6,
                        Arrays.asList(
                                Point.of(50, 50),
                                Point.of(200, 50),
                                Point.of(350, 50),
                                Point.of(350, 200),
                                Point.of(350, 350),
                                Point.of(200, 350)
                        )
                )
        );
    }

    @MethodSource("nextPointArgs")
    @ParameterizedTest(name = "{0} vertex")
    public void testNextPoint(
            int numberOfVertex,
            List<Point> expectedPoints
    ) {
        pointCalculator = new PointCalculator(
                600,
                400,
                numberOfVertex
        );

        List<Point> points = IntStream.range(0, numberOfVertex)
                .mapToObj(i -> pointCalculator.nextPoint())
                .collect(Collectors.toList());

        List<Matcher<? super Point>> pointMatcher = expectedPoints.stream()
                .map(Matchers::equalTo)
                .collect(Collectors.toList());
        assertThat(points, Matchers.contains(pointMatcher));
    }
}
