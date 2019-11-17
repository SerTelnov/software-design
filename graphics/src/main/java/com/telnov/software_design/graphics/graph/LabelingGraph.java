package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.DrawingApi;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Set;

@ParametersAreNonnullByDefault
public class LabelingGraph extends AbstractGraph {

    @Nonnull
    private final Set<Vertex> vertices;

    public LabelingGraph(DrawingApi drawingApi, Collection<Vertex> vertices) {
        super(drawingApi);
        this.vertices = Set.copyOf(vertices);
    }

    @Override
    public void doDraw() {
        vertices.forEach(vertex -> {
            drawVertex(vertex.getInfo());
            vertex.forEach(this::drawEdge);
        });
    }

    @Override
    protected int getNumberOfVertices() {
        return vertices.size();
    }
}
