package Structural.Decorator;

// we have a circle and a square. we want to be able to add color or transparency (Eg) without changing those classes
// (open close principle)

public class cDynamicDecoratorComposition {

    public static void main(String[] args) {
        Circle circle = new Circle(10);
        System.out.println(circle.info());

        ColoredShape blueSquare = new ColoredShape(new Square(4), "blue");
        System.out.println(blueSquare.info());

        // both decorators in combination:
        // green, transparent circle with radius 5
        TransparentShape greenCleareCircle = new TransparentShape(
                                                    new ColoredShape(
                                                            new Circle(5),
                                                            "green"), 90);
        System.out.println(greenCleareCircle.info());

    }

}


// the decorators
class ColoredShape implements Shape {

    private Shape shape;
    private String color;

    public ColoredShape(Shape shape, String color) {
        this.shape = shape;
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " with the color " + this.color;
    }
}

class TransparentShape implements Shape {

    private Shape shape;
    private int transparency;

    public TransparentShape(Shape shape, int transparency) {
        this.shape = shape;
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " has " + this.transparency + "% transparency";
    }
}

// original code

interface Shape {
    String info(); // deliberately not using toString
}

class Circle implements Shape {
    private float radius;

    public Circle(float radius) {
        this.radius = radius;
    }

    public Circle() {
    }

    public void resize(int factor) {
        this.radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + this.radius;
    }
}

class Square implements Shape {
    private int side;

    public Square(int side) {
        this.side = side;
    }

    public Square() {
    }

    @Override
    public String info() {
        return "A square of size " + this.side;
    }
}


