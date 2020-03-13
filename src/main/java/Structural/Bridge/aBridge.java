package Structural.Bridge;

// connecting components together through abstractions
// it prevents a 'cartesian product' complexity explosion
// eg: Base class ThreadScheduler
//      can be pre-emptive or cooperative
//      can run on windows or linux
// end up with a 2 x 2 scenario ->
//                        ThreadScheduler
//                       ^               ^
//                      /                 \
//          PreemptiveTS                 CooperativeTS
//          ^         ^                  ^           ^
//          |         |                  |           |
//    WindowsPTS    UnixPTS         WindowsCTS      UnixCTS
// ^^ ugly, many classes by the end

// Bridge pattern:
//
//      ThreadScheduler
//        ^         |
//        |         v
//        |         PlatformSchedulerInterface
//        |                             ^
//        |<- PreemptiveTS              | <- PreemptivePSI
//        |<- CooperativeTS             | <- CooperativePSI     // << these implement the interface
//
// much more easily expandable

// e.g. Shape -> Circle, Rectangle, Triangle
//      Renderer -> Vector, Raster, Imagination
// you'd need VectorCircleRenderer, VectorRectangleRenderer, Raster...
// with bridge method instead we're doing:

// interface Renderer
// VectorRenderer, RasterRenderer ^^ ...etc
// abstract Shape
// Circle extends Shape
// Triangle extends Shape ... etc

// this is quite clunky since you have to inject a renderer dependency to all shapes...
// however, if using a proper d-injection framework such as spring/google-juice, this is not so bad




public class aBridge {
    public static void main(String[] args) {
        VectorRenderer vectorRenderer = new VectorRenderer();
        RasterRenderer rasterRenderer = new RasterRenderer();
        Circle circle = new Circle(vectorRenderer, 5);
        circle.draw();
        circle.resize(2);
        circle.draw();
    }
}

interface Renderer {
    void renderCircle(int radius);
    void renderRectangle(int width, int height);
    void renderTriangle(String yeahRight);
}

class VectorRenderer implements Renderer {

    @Override
    public void renderCircle(int radius) {
        System.out.println(String.format("Drawing a circle of radius %d by vector", radius));
    }

    @Override
    public void renderRectangle(int width, int height) {
        System.out.println("Typing pointless text for no real reason...");
    }

    @Override
    public void renderTriangle(String yeahRight) {
        System.out.println("Yes, even more...");
    }
}

class RasterRenderer implements Renderer {

    @Override
    public void renderCircle(int radius) {
        System.out.println("Drawing pixels for a circle of radius " + radius);
    }

    @Override
    public void renderRectangle(int width, int height) {
        System.out.println(String.format("Drawing a rectangle %d pixels by %d pixels", width, height));
    }

    @Override
    public void renderTriangle(String yeahRight) {
        System.out.println("Drawing pixels for a TRIANGLE of some kind");
    }
}

abstract class Shape {
    protected Renderer renderer;

    public Shape(Renderer renderer) {
        this.renderer = renderer;
    }

    public abstract void draw();
    public abstract void resize(int factor);
}

class Circle extends Shape {

    public int radius;

    public Circle(Renderer renderer) {
        super(renderer);
    }

    public Circle(Renderer renderer, int radius) {
        super(renderer);
        this.radius = radius;
    }

    @Override
    public void draw() {
        renderer.renderCircle(radius);
    }

    @Override
    public void resize(int factor) {
        radius *= factor;
    }
}


