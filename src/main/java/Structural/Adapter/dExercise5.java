package Structural.Adapter;

public class dExercise5 {
    public static void main(String[] args) {
        Square square = new Square(4);
        Rectangle someRect = new SquareToRectangleAdapter(square);
        System.out.println(someRect.getArea());
    }
}

class Square
{
    public int side;

    public Square(int side)
    {
        this.side = side;
    }
}

interface Rectangle
{
    int getWidth();
    int getHeight();

    default int getArea()
    {
        return getWidth() * getHeight();
    }
}

class SquareToRectangleAdapter implements Rectangle
{
    private int side;

    public SquareToRectangleAdapter(Square square)
    {
        this.side = square.side;
    }

    @Override
    public int getWidth() {
        return side;
    }

    @Override
    public int getHeight() {
        return side;
    }
}