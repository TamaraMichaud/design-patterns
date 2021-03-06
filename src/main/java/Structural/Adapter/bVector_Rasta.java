package Structural.Adapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class bVector_Rasta {

    public static void main(String[] args) {
        draw(); // we create 8 lines here (2 rectangles)
        draw(); // we create ANOTHER 8 lines here... but ideally we want to verify that we're duplicating lines
        // and just return the one we made earlier...
    }
    // we want to be able to draw an image; either with multiple pixel Points, or some vector Lines

    // suppose we have an api which allows us to draw things only using Point...
    static List<VectorObject> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle(1, 1, 10, 10),
            new VectorRectangle(3, 3, 6, 6)));

    // ^^ how to convert this to be consumed by the below method...? let's create an interface!
    public static void drawPoint(Point point) {
        System.out.println(String.format("Printing point at location x=%d, y=%d",
                point.x, point.y));
    }

    // vv this is the adapter implementation
    private static void draw() {
        for (VectorObject vo : vectorObjects) {
            System.out.println("Drawing Vector Object: " + vo);
            for (Line line : vo) {
                System.out.println("  Object Line: " + line);
                LineToPointAdapter points = new LineToPointAdapter(line);
                points.forEach(bVector_Rasta::drawPoint);
            }
        }

    }
}

// this class should take a Line and convert it into a list of Points
class LineToPointAdapter extends ArrayList<Point> {

    private static int counter = 0;

    // hold onto your hats kids!!
    public LineToPointAdapter(Line line) {

        System.out.println(++counter + ") Deconstructing line: " + line);
        System.out.println("(no caching)"); // << relevant apparently...
        // to be explained later hopefully...

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int bottom = Math.min(line.start.y, line.end.y);
        int top = Math.max(line.start.y, line.end.y);
        // ^^ line could be vertical or horizontal
        // vv one of these will be zero...
        int dx = right - left;
        int dy = top - bottom;

        if (dx == 0) { // vertical line
            for (int y = bottom; y <= top; y++) {
                this.add(new Point(left, y));
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; x++) {
                this.add(new Point(x, bottom));
            }
        }
    }
}


class VectorObject extends ArrayList<Line> {
}

class VectorRectangle extends VectorObject {
    public VectorRectangle(int x, int y, int width, int height) {
        this.add(new Line(new Point(x, y), new Point(x + width, y)));
        this.add(new Line(new Point(x + width, y), new Point(x + width, y + height)));
        this.add(new Line(new Point(x + width, y + height), new Point(x, y + height)));
        this.add(new Line(new Point(x, y), new Point(x, y + height)));
        System.out.println("Created Rectangle: " + this);
    }

}


class Point {
    public int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line {
    public Point start, end;

    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}