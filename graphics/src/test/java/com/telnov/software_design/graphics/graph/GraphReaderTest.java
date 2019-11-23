package com.telnov.software_design.graphics.graph;

import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.hamcrest.MatcherAssert.assertThat;

class GraphReaderTest {

    @Test
    void testReadAdjacency() {
        Collection<Edge> edges = GraphReader.readAdjacency(
                Arrays.asList(
                        "1 2",
                        "1 3",
                        "2 3",
                        "3 4"
                )
        );

        List<Matcher<? super Edge>> edgeMatchers = Stream.of(
                Edge.of(1, 2),
                Edge.of(1, 3),
                Edge.of(2, 3),
                Edge.of(3, 4)
        )
                .map(Matchers::equalTo)
                .collect(Collectors.toList());

        assertThat(edges, Matchers.contains(edgeMatchers));
    }

    @Test
    void testReadLabeling() {
        Collection<Vertex> vertices = GraphReader.readLabeling(
                Arrays.asList(
                        "0 1 1 0",
                        "1 0 1 0",
                        "1 1 0 1",
                        "0 0 1 0"
                )
        );

        List<Matcher<? super Vertex>> vertexMatchers = Stream.of(
                Vertex.of(0, List.of(1, 2)),
                Vertex.of(1, List.of(0, 2)),
                Vertex.of(2, List.of(0, 1, 3)),
                Vertex.of(3, List.of(2))
        )
                .map(Matchers::equalTo)
                .collect(Collectors.toList());

        assertThat(vertices, Matchers.contains(vertexMatchers));
    }
}