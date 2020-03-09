package Creational.PrototypeMaybe;

// implement Line.deepcopy()

public class Exercise {

    public static void main(String[] args) {
        Line line1 = new Line(new Point(4, 5), new Point(6, 9));
        Line line2 = line1.deepCopy();
    }
}

@SuppressWarnings("WeakerAccess")
class Point
{
    public int x, y;

    public Point(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}

@SuppressWarnings("WeakerAccess")
class Line
{
    public Point start, end;

    public Line(Point start, Point end)
    {
        this.start = start;
        this.end = end;
    }

    public Line deepCopy()
    {
        Point copyPointStart = new Point(this.start.x, this.start.y);
        Point copyPointEnd = new Point(this.end.x, this.end.y);
        return new Line(copyPointStart, copyPointEnd);
    }
}
