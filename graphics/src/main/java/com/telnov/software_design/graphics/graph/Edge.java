package com.telnov.software_design.graphics.graph;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
public class Edge {

    @Nonnull
    private final VertexInfo first;
    @Nonnull
    private final VertexInfo second;

    private Edge(VertexInfo first, VertexInfo second) {
        this.first = first;
        this.second = second;
    }

    public static Edge of(VertexInfo a, VertexInfo b) {
        return new Edge(a, b);
    }

    public static Edge of(String a, String b) {
        return new Edge(
                new VertexInfo(a),
                new VertexInfo(b)
        );
    }

    public static Edge of(int a, int b) {
        return of(Integer.toString(a), Integer.toString(b));
    }

    @Nonnull
    public VertexInfo getFirst() {
        return first;
    }

    @Nonnull
    public VertexInfo getSecond() {
        return second;
    }

    @Nonnull
    public Stream<VertexInfo> toStream() {
        return Stream.of(first, second);
    }

    @Override
    public String toString() {
        return "Edge{" +
                "first=" + first +
                ", second=" + second +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Edge)) return false;
        Edge edge = (Edge) o;
        return first.equals(edge.first) &&
                second.equals(edge.second);
    }

    @Override
    public int hashCode() {
        return Objects.hash(first, second);
    }
}
