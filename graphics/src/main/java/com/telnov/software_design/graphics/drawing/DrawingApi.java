package com.telnov.software_design.graphics.drawing;

public interface DrawingApi {

    double getDrawingAreaWidth();

    double getDrawingAreaHeight();

    void drawCircle(Point p, double radius);

    void drawLine(Point p1, Point p2);

    void visualize();
}
