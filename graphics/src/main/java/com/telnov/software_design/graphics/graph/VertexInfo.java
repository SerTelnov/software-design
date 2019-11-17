package com.telnov.software_design.graphics.graph;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

@ParametersAreNonnullByDefault
public class VertexInfo {

    @Nonnull
    private final String name;

    public VertexInfo(String name) {
        this.name = name;
    }

    public VertexInfo(int i) {
        this("Vertex #" + i);
    }

    @Nonnull
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VertexInfo)) return false;
        VertexInfo that = (VertexInfo) o;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
}
