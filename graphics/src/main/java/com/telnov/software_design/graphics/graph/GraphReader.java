package com.telnov.software_design.graphics.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public final class GraphReader {

    private GraphReader() {
    }

    public static Collection<Vertex> readLabeling(Collection<String> graphStr) {
        List<int[]> graph = graphStr.stream()
                .map(line -> line.split(" "))
                .map(line -> Arrays.stream(line).mapToInt(Integer::parseInt).toArray())
                .collect(Collectors.toList());

        Map<Integer, VertexInfo> mapVertex = new HashMap<>();
        List<Vertex> vertices = new ArrayList<>();
        for (int i = 0; i != graph.size(); i++) {
            VertexInfo vertexi = getVertex(mapVertex, i);
            List<VertexInfo> neighbors = new ArrayList<>();
            for (int j = 0; j != graph.get(i).length; j++) {
                if (graph.get(i)[j] == 1) {
                    neighbors.add(getVertex(mapVertex, j));
                }
            }
            vertices.add(new Vertex(vertexi, neighbors));
        }

        return vertices;
    }

    public static Collection<Edge> readAdjacency(Collection<String> graphStr) {
        return graphStr.stream()
                .map(line -> line.split(" "))
                .map(line -> Edge.of(
                        line[0],
                        line[1])
                ).collect(Collectors.toList());
    }

    private static VertexInfo getVertex(Map<Integer, VertexInfo> mapVertex, int id) {
        if (mapVertex.containsKey(id)) {
            return mapVertex.get(id);
        } else {
            VertexInfo vertexInfo = new VertexInfo(id);
            mapVertex.put(id, vertexInfo);
            return vertexInfo;
        }
    }
}
