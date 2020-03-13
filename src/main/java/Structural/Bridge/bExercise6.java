package Structural.Bridge;

public class bExercise6 {
    public static void main(String[] args) {
        System.out.println(new Triangle(new RasterRenderer2()).toString());
        System.out.println(new Square(new VectorRenderer2()).toString());
    }
}

abstract class Shape2 {
    public Renderer2 renderer;

    public Shape2(Renderer2 whichRenderer) {
        this.renderer = whichRenderer;
    }

    public abstract String getName();
}

class Triangle extends Shape2 {
    public Triangle(Renderer2 whichRenderer) {
        super(whichRenderer);
    }

    @Override
    public String getName() {
        return "Triangle";
    }

    @Override
    public String toString() {
        return String.format("Drawing %s as %s", getName(), renderer.whatToRenderAs());
    }
}

class Square extends Shape2 {
    public Square(Renderer2 whichRenderer) {
        super(whichRenderer);
    }

    @Override
    public String getName() {
        return "Square";
    }

    @Override
    public String toString() {
        return String.format("Drawing %s as %s", getName(), renderer.whatToRenderAs());
    }
}

interface Renderer2 {
    public String whatToRenderAs();
}

class VectorRenderer2 implements Renderer2 {
    @Override
    public String whatToRenderAs() {
        return "lines";
    }
}
class RasterRenderer2 implements Renderer2 {
    @Override
    public String whatToRenderAs() {
        return "pixels";
    }
}