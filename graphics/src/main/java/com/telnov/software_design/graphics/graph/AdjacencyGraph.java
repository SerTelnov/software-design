package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.DrawingApi;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.List;

@ParametersAreNonnullByDefault
public class AdjacencyGraph extends AbstractGraph {

    @Nonnull
    private final List<Edge> edges;

    public AdjacencyGraph(DrawingApi drawingApi, Collection<Edge> edges) {
        super(drawingApi);
        this.edges = List.copyOf(edges);
    }

    @Override
    public void doDraw() {
        edges.forEach(this::drawEdge);
    }

    @Override
    protected int getNumberOfVertices() {
        return (int) edges.stream()
                .flatMap(Edge::toStream)
                .distinct()
                .count();
    }
}
