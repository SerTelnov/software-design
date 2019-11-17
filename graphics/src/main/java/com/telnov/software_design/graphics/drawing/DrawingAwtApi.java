package com.telnov.software_design.graphics.drawing;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.List;
import java.util.ArrayList;

import static com.telnov.software_design.graphics.drawing.DrawingUtils.HEIGHT;
import static com.telnov.software_design.graphics.drawing.DrawingUtils.WIDTH;

public class DrawingAwtApi implements DrawingApi {

    private final List<Shape> shapes;

    public DrawingAwtApi() {
        this.shapes = new ArrayList<>();
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
        double x = radius * 2;
        shapes.add(new Ellipse2D.Double(p.x, p.y, x, x));
    }

    @Override
    public void drawLine(Point p1, Point p2) {
        shapes.add(new Line2D.Double(p1.x, p1.y, p2.x, p2.y));
    }

    @Override
    public void visualize() {
        Frame frame = new GraphFrame();
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        frame.setSize(WIDTH, HEIGHT);
        frame.setVisible(true);
    }

    private class GraphFrame extends Frame {

        public GraphFrame() {
            super("Awt Graph");
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Graphics2D g2 = (Graphics2D) g;
            ColorFactory colorFactory = new ColorFactory();
            shapes.forEach(shape -> {
                if (shape instanceof Line2D.Double) {
                    g2.setPaint(colorFactory.nextColor());
                    g2.draw(shape);
                } else {
                    g2.setPaint(Color.BLACK);
                    g2.fill(shape);
                }
            });
        }
    }

    private class ColorFactory {

        private final Color[] LINE_COLORS = {
                Color.LIGHT_GRAY, Color.BLUE, Color.ORANGE, Color.PINK, Color.YELLOW, Color.RED, Color.DARK_GRAY
        };

        private int colorIndex;

        public Color nextColor() {
            Color color = LINE_COLORS[colorIndex];
            colorIndex = (colorIndex + 1) % LINE_COLORS.length;
            return color;
        }
    }
}
