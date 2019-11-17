package com.telnov.software_design.graphics;

import com.telnov.software_design.graphics.drawing.DrawingApi;
import com.telnov.software_design.graphics.drawing.DrawingAwtApi;
import com.telnov.software_design.graphics.drawing.DrawingJavaFxApi;
import com.telnov.software_design.graphics.graph.AbstractGraph;
import com.telnov.software_design.graphics.graph.AdjacencyGraph;
import com.telnov.software_design.graphics.graph.Edge;
import com.telnov.software_design.graphics.graph.LabelingGraph;
import com.telnov.software_design.graphics.graph.Vertex;
import com.telnov.software_design.graphics.graph.VertexInfo;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        if (args.length == 1 && args[0].equals("help")) {
            System.err.println("awt/javafx <path-to-graph> labeling/adjacency");
        } else if (args.length != 3) {
            System.err.println("Usage: <drawing-api> <path-to-graph> <graph-presenting>");
        } else {
            DrawingApi api = getDrawingApi(args[0]);
            AbstractGraph graph = readGraph(api, args[1], args[2].equals("labeling"));
            graph.drawGraph();
        }
    }

    private static DrawingApi getDrawingApi(String apiName) {
        if ("awt".equals(apiName)) {
            return new DrawingAwtApi();
        } else if ("javafx".equals(apiName)) {
            return new DrawingJavaFxApi();
        } else {
            throw new IllegalArgumentException("Invalid draw api name " + apiName);
        }
    }

    private static AbstractGraph readGraph(DrawingApi api, String pathToGraph, boolean isLabeling) {
        if (isLabeling) {
            return new LabelingGraph(api, readLabeling(pathToGraph));
        } else {
            return new AdjacencyGraph(api, readAdjacency(pathToGraph));
        }
    }

    private static Collection<Vertex> readLabeling(String path) {
        try {
            List<int[]> graph = Files.lines(Paths.get(path))
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
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
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

    private static Collection<Edge> readAdjacency(String path) {
        try {
            return Files.lines(Paths.get(path))
                    .map(line -> line.split(" "))
                    .map(line -> Edge.of(
                            line[0],
                            line[1])
                    ).collect(Collectors.toList());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
