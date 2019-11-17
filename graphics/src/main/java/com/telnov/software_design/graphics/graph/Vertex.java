package com.telnov.software_design.graphics.graph;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class Vertex implements Iterable<Edge> {

    private final VertexInfo info;
    private final List<VertexInfo> neighbors;

    public Vertex(VertexInfo info, List<VertexInfo> neighbors) {
        this.info = info;
        this.neighbors = neighbors;
    }

    public VertexInfo getInfo() {
        return info;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Vertex)) return false;
        Vertex vertex = (Vertex) o;
        return Objects.equals(info, vertex.info) &&
                Objects.equals(neighbors, vertex.neighbors);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info, neighbors);
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "info=" + info +
                ", neighbors=" + neighbors +
                '}';
    }

    @Nonnull
    @Override
    public Iterator<Edge> iterator() {
        return new VertexIterator(neighbors.iterator());
    }

    private class VertexIterator implements Iterator<Edge> {

        private final Iterator<VertexInfo> neighborsIterator;

        public VertexIterator(Iterator<VertexInfo> neighborsIterator) {
            this.neighborsIterator = neighborsIterator;
        }

        @Override
        public boolean hasNext() {
            return neighborsIterator.hasNext();
        }

        @Override
        public Edge next() {
            VertexInfo neighbor = neighborsIterator.next();
            return Edge.of(getInfo(), neighbor);
        }
    }
}
