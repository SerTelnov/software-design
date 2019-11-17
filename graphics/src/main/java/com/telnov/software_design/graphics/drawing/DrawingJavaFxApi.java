package com.telnov.software_design.graphics.drawing;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

import static com.telnov.software_design.graphics.drawing.DrawingUtils.HEIGHT;
import static com.telnov.software_design.graphics.drawing.DrawingUtils.WIDTH;

public class DrawingJavaFxApi implements DrawingApi {

    private static List<Shape> shapes = null;

    public DrawingJavaFxApi() {
        shapes = new ArrayList<>();
    }

    @Override
    public double getDrawingAreaWidth() {
        return WIDTH;
    }

    @Override
    public double getDrawingAreaHeight() {
        return HEIGHT;
    }

    @Override
    public void drawCircle(Point p, double radius) {
        shapes.add(new Circle(p.x, p.y, radius));
    }

    @Override
    public void drawLine(Point p1, Point p2) {
        Point dp1 = changeStartLinePoint(p1);
        Point dp2 = changeStartLinePoint(p2);
        shapes.add(new Line(dp1.x, dp1.y, dp2.x, dp2.y));
    }

    private static Point changeStartLinePoint(Point p) {
        return Point.of(p.x - DrawingUtils.CIRCLE_LENGTH / 2, p.y - DrawingUtils.CIRCLE_LENGTH / 2);
    }

    @Override
    public void visualize() {
        Application.launch(GraphApplication.class);
    }

    public static class GraphApplication extends Application {

        @Override
        public void start(Stage stage) {
            stage.setTitle("JavaFx Graph");
            Group root = new Group();

            shapes.forEach(shape -> root.getChildren().add(shape));

            stage.setScene(new Scene(root, WIDTH, HEIGHT));
            stage.show();
        }
    }
}
