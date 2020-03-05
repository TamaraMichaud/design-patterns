package SOLID;

// you should be able to substitute a derived class for a base class ...

public class C_LiskovSubstitutionPrinciple {
    public static void main(String[] args) {

        Rectangle rectangle = new Rectangle(6, 8);
        new Demo().useIt(rectangle);
        // ^^ all good.  we get 80 both times
        // but what about if we use a Square.  should work fine since it's an extension of Rectangle...
        Rectangle square = new BadSquare(5);
        new Demo().useIt(square);
        // ^^ oopsie!! our "set" methods in Square are doing unexpected things...

        // so then, do we really need a square at all?  perhaps simply "isSquare() { return width == height; }" method in Rect is ok?
        // or, using Factory design:
        Rectangle barelyUnderstand = RectangleFactory.newSquare(5);
        new Demo().useIt(barelyUnderstand);
        // ^^ much better!
        //- create RectangleFactory.newSquare -> h = 5, w = 5
        // Demo.useIt() -> SET LOCAL "width" = width, THEN set height = 10 ( makes width = 10 too)
    }
}

class RectangleFactory {
    public static Rectangle newRectangle(int width, int height) {
        return new Rectangle(width, height);
    }

    public static Rectangle newSquare(int side) {
        return new Rectangle(side, side);
    }

}

// BASIC: empty and full constructor, getters, setters.  and a getArea() method.
class Rectangle {
    // fields
    public int height, width;
    // constructors
    public Rectangle() {
    }
    public Rectangle(int height, int width) {
        this.height = height;
        this.width = width;
    }
    // custom methods
    public int getArea() {
        return width * height;
    }
    // getter + setter
    public int getHeight() {
        return height;
    }
    public void setHeight(int height) {
        this.height = height;
    }
    public int getWidth() {
        return width;
    }
    public void setWidth(int width) {
        this.width = width;
    }

    boolean isSquare() { return width == height; }
}

class BadSquare extends Rectangle {

    // [fields are inherited from Rectangle]
    // constructors
    public BadSquare() {}
    public BadSquare(int size) {
        width = height = size;
    }

    @Override
    public void setHeight(int height){
        super.setHeight(height);
        super.setWidth(height);
    }
    @Override
    public void setWidth(int width){
        super.setHeight(width);
        super.setWidth(width);
    }
}

class Demo {
    void useIt(Rectangle r) {

        // grab the current width
        int width = r.getWidth();
        // update the height
        r.setHeight(10);
        // area = w * 10
        System.out.println("Expected area of: " + width * 10);
        System.out.println("Got area of :     " + r.getArea());
    }

}