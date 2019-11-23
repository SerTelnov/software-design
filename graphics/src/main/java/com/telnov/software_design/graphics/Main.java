package com.telnov.software_design.graphics;

import com.telnov.software_design.graphics.drawing.DrawingApi;
import com.telnov.software_design.graphics.drawing.DrawingAwtApi;
import com.telnov.software_design.graphics.drawing.DrawingJavaFxApi;
import com.telnov.software_design.graphics.graph.AbstractGraph;
import com.telnov.software_design.graphics.graph.AdjacencyGraph;
import com.telnov.software_design.graphics.graph.LabelingGraph;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;

import static com.telnov.software_design.graphics.graph.GraphReader.readAdjacency;
import static com.telnov.software_design.graphics.graph.GraphReader.readLabeling;

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
        Collection<String> graphStr = readGraph(pathToGraph);
        if (isLabeling) {
            return new LabelingGraph(api, readLabeling(graphStr));
        } else {
            return new AdjacencyGraph(api, readAdjacency(graphStr));
        }
    }

    private static Collection<String> readGraph(String pathToGraph) {
        try {
            return Files.readAllLines(Paths.get(pathToGraph));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
