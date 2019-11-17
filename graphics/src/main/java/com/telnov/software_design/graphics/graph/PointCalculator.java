package com.telnov.software_design.graphics.graph;

import com.telnov.software_design.graphics.drawing.Point;

import static com.telnov.software_design.graphics.drawing.DrawingUtils.CIRCLE_LENGTH;
import static com.telnov.software_design.graphics.drawing.DrawingUtils.MARGIN;

public class PointCalculator {

    private static final int NUMBER_OF_LINES = 4;

    private final int numberOfVertexOnLine;
    private final double coordDiff;
    private double currentX;
    private double currentY;
    private int currentVertexNumber;

    public PointCalculator(double width, double height, int numberOfVertex) {
        double lineLength = Math.min(width, height) - MARGIN * 2;
        this.numberOfVertexOnLine = (int) Math.ceil(((double) numberOfVertex) / NUMBER_OF_LINES);
        this.coordDiff = lineLength / numberOfVertexOnLine;

        this.currentX = MARGIN;
        this.currentY = MARGIN;
    }

    public static Point calcCenterPoint(Point p) {
        final double v = CIRCLE_LENGTH / 2;
        return Point.of(p.x + v, p.y + v);
    }

    public Point nextPoint() {
        Point currentPoint = Point.of(currentX, currentY);

        currentX += calcCoord(true);
        currentY += calcCoord(false);
        currentVertexNumber++;

        return currentPoint;
    }

    private double calcCoord(boolean isX) {
        int currentDirectionNumber = currentVertexNumber / numberOfVertexOnLine;
        switch (currentDirectionNumber) {
            case 0:
                return isX ? coordDiff : 0;
            case 1:
                return isX ? 0 : coordDiff;
            case 2:
                return isX ? -coordDiff : 0;
            case 3:
                return isX ? 0 : -coordDiff;
            default:
                throw new RuntimeException("Invalid count of vertex");
        }
    }
}
