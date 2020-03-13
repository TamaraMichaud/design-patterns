package Structural.Adapter;

import java.util.*;

public class Vector_Rasta_WithCaching {

    public static void main(String[] args) {
        draw(); // we create 8 lines here (2 rectangles)
        draw(); // we create ANOTHER 8 lines here... but ideally we want to verify that we're duplicating lines
        // and just return the one we made earlier...
    }
    // we want to be able to draw an image; either with multiple pixel Points, or some vector Lines

    // suppose we have an api which allows us to draw things only using Point...
    static List<VectorObject2> vectorObjects = new ArrayList<>(Arrays.asList(
            new VectorRectangle2(1, 1, 10, 10),
            new VectorRectangle2(3, 3, 6, 6)));

    // ^^ how to convert this to be consumed by the below method...? let's create an interface!
    public static void drawPoint(Point2 point) {
        System.out.println(String.format("Printing point at location x=%d, y=%d",
                point.x, point.y));
    }

    // vv this is the adapter implementation
    private static void draw() {
        for (VectorObject2 vo : vectorObjects) {
            System.out.println("Drawing Vector Object: " + vo);
            for (Line2 line : vo) {
                System.out.println("  Object Line: " + line);
                LineToPointAdapter2 points = new LineToPointAdapter2(line);
                points.forEach(Vector_Rasta::drawPoint);
            }
        }

    }
}

// this class should take a Line and convert it into a list of Points
class LineToPointAdapter2 extends ArrayList<Point2> {

    private static int counter = 0;
    private static Map<Integer, List<Point2>> cache = new HashMap<>(); // << this is to hold the new hashCode() values per point
    private int hash;

    // hold onto your hats kids!!
    public LineToPointAdapter2(Line2 line) {

        hash = line.hashCode();
        if (cache.get(hash) != null) return; // we already have this one in the list!

        System.out.println(++counter + ") Deconstructing line: " + line);
        System.out.println("(with caching)"); // << let's do it!

        int left = Math.min(line.start.x, line.end.x);
        int right = Math.max(line.start.x, line.end.x);
        int bottom = Math.min(line.start.y, line.end.y);
        int top = Math.max(line.start.y, line.end.y);
        // ^^ line could be vertical or horizontal
        // vv one of these will be zero...
        int dx = right - left;
        int dy = top - bottom;

        ArrayList<Point2> tmpList = new ArrayList<>();

        if (dx == 0) { // vertical line
            for (int y = bottom; y <= top; y++) {
                Point2 newPoint = new Point2(left, y);
                tmpList.add(newPoint);
            }
        } else if (dy == 0) {
            for (int x = left; x <= right; x++) {
                Point2 newPoint = new Point2(x, bottom);
                tmpList.add(newPoint);
            }
        }

        cache.put(hash, tmpList);
    }
}


class VectorObject2 extends ArrayList<Line2> {
}

class VectorRectangle2 extends VectorObject2 {
    public VectorRectangle2(int x, int y, int width, int height) {
        this.add(new Line2(new Point2(x, y), new Point2(x + width, y)));
        this.add(new Line2(new Point2(x + width, y), new Point2(x + width, y + height)));
        this.add(new Line2(new Point2(x + width, y + height), new Point2(x, y + height)));
        this.add(new Line2(new Point2(x, y), new Point2(x, y + height)));
        System.out.println("Created Rectangle: " + this);
    }

}


class Point2 {
    public int x, y;

    public Point2(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point2 point2 = (Point2) o;
        return x == point2.x &&
                y == point2.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

class Line2 {
    public Point2 start, end;

    public Line2(Point2 start, Point2 end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line2 line2 = (Line2) o;
        return Objects.equals(start, line2.start) &&
                Objects.equals(end, line2.end);
    }

    @Override
    public int hashCode() {
        return Objects.hash(start, end);
    }

    @Override
    public String toString() {
        return "Line{" +
                "start=" + start +
                ", end=" + end +
                '}';
    }
}