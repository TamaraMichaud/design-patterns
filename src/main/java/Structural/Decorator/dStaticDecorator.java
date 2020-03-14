package Structural.Decorator;

// where previously we were able to build as many decorators as we wanted,
// what if we know we're always going to need a certain combination of them
// and want to group them together preserving type inheritance etc too

// but even here we still cannot access the circle's resize method...

import java.util.function.Supplier;

public class dStaticDecorator {
    public static void main(String[] args) {

        // now we can bake the types into the implementation directly here
        ColouredShape2<Square2> blueSquare = new ColouredShape2<>(
                () -> new Square2(20), // << how to do the weird call we created - empty lambda
                "blue");
        System.out.println(blueSquare.info());

        // and a transparent coloured shape...
        TransparentShape2<ColouredShape2<Circle2>> fml = new TransparentShape2<>(
                () -> new ColouredShape2<>(
                        () -> new Circle2(9), // << how to do the weird call we created - empty lambda
                        "green"),
                75);
        System.out.println(fml.info());

    }
}

// static decorator
class ColouredShape2 <T extends Shape2> implements Shape2 {

    private Shape2 shape;
    private String color;

    public ColouredShape2(Supplier<? extends T> ctor, String color) { // << this is a cheat... which i dont understand at all :(
        // instead of passing a specific type to this constructor, we are pushing the constructor to that type..?
        // ^^ this syntax is used for lambda functions
        this.shape = ctor.get();
        this.color = color;
    }

    @Override
    public String info() {
        return shape.info() + " still has the color " + color;
    }
}

class TransparentShape2 <T extends Shape2> implements Shape2 {

    private Shape2 shape;
    private int transparency;

    public TransparentShape2(Supplier<? extends T> ctor, int transparency) { // << this is a cheat... which i dont understand at all :(
        // instead of passing a specific type to this constructor, we are pushing the constructor to that type..?
        // ^^ this syntax is used for lambda functions
        this.shape = ctor.get();
        this.transparency = transparency;
    }

    @Override
    public String info() {
        return shape.info() + " is still " + transparency + "% transparent";
    }
}



// original code

interface Shape2 {
    String info(); // deliberately not using toString
}

class Circle2 implements Shape2 {
    private float radius;

    public Circle2(float radius) {
        this.radius = radius;
    }

    public void resize(int factor) {
        this.radius *= factor;
    }

    @Override
    public String info() {
        return "A circle of radius " + this.radius;
    }
}

class Square2 implements Shape2 {
    private int side;

    public Square2(int side) {
        this.side = side;
    }

    @Override
    public String info() {
        return "A square of size " + this.side;
    }
}


