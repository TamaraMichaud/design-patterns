package SOLID;

// you should be able to substitute a base class for a sub-class with no code change...

public class C_LiskovSubstitutionPrinciple {
    public static void main(String[] args) {
        System.out.println("boo");
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
}

class Square extends Rectangle {

    // [fields are inherited from Rectangle]
    // constructors
    public Square() {}
    public Square(int size) {
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