package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.DrawingApi;
import com.telnov.software_design.graphics.drawing.Point;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.telnov.software_design.graphics.drawing.DrawingUtils.CIRCLE_LENGTH;

public abstract class AbstractGraph {

    private final Map<VertexInfo, Point> drawnVertices;
    private final Set<Edge> drawnEdges;
    private final DrawingApi drawingApi;
    private PointCalculator calculator;

    public AbstractGraph(DrawingApi drawingApi) {
        this.drawingApi = drawingApi;
        this.drawnVertices = new HashMap<>();
        this.drawnEdges = new HashSet<>();
    }

    public void drawGraph() {
        calculator = new PointCalculator(
                drawingApi.getDrawingAreaWidth(),
                drawingApi.getDrawingAreaHeight(),
                getNumberOfVertices()
        );
        doDraw();
        drawingApi.visualize();
    }

    protected Point drawVertex(VertexInfo info) {
        if (drawnVertices.containsKey(info))
            return drawnVertices.get(info);

        Point p = calculator.nextPoint();
        drawnVertices.put(info, p);
        drawingApi.drawCircle(p, CIRCLE_LENGTH, CIRCLE_LENGTH);

        return p;
    }

    protected void drawEdge(Edge edge) {
        if (drawnEdges.contains(edge))
            return;

        Point p1 = drawVertex(edge.getFirst());
        Point p2 = drawVertex(edge.getSecond());

        drawingApi.drawLine(p1, p2);
        drawnEdges.add(edge);
    }

    protected abstract void doDraw();
    protected abstract int getNumberOfVertices();
}
