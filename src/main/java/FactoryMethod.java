// not actually mentioned in g.o.f. as such

// wholesale object creation. sometimes constructors are too limited, so rather than overloading them 100 times,
// create a factory.  which could be
// can be:
    // a separate function (Factory Method)
    // that may exist in a separate class (Factory) (often static)
    // and even have a heirarchy of factories (Abstract Factory)

// A Factory is a component responsible solely for the wholesale (not piecewise) creation of objects
public class FactoryMethod {

    public static void main(String[] args) {

        Point examplePoint = Point.Factory.newCartesianPoint(5, 8);
        // by making the constructor private, it guides the coder to the factory methods we want them to use
        System.out.println(examplePoint);
    }
}

// bleurgh
enum CoordinateSystem {
    CARTESIAN,
    POLAR
}
@SuppressWarnings("all")
class Point {
    private double x, y;

    private Point(double x, double y) {
        this.x = x;
        this.y = y;
    }
    // ok for points, what about polar co-ordinates ?

    // this is illegal, same signature as the first constructor... so what do we do
//    public Point(double rho, double theta) {
//        this.x = rho;
//        this.y = theta;
//    }

    // we introduce an ENUM to switch with...
    // but now our constructor just takes "a" or "b"... what does that mean?
    // so we create some documentation...?

    /**
     * ... if Cartesian, then.... or if Polar then... ... this stinks...
     * @param a if this then that
     * @param b blah
     * @param type blah
     */
    // so we make it private and just use it internally.
    private Point(double a, double b, CoordinateSystem type) {

        switch (type) {

            case CARTESIAN: this.x = a; this.y = b;
                break;
            case POLAR: this.x = a * Math.cos(b); this.y = a * Math.sin(b);
                break;
        }
    }

    // so we create some STATIC classes that can be our "Factories".
    // Note that we now only use the most basic constructor and no longer require the pointless enum

    // we wrap these methods in a public static class so taht we don't need to do Point.newCart... which is somewhat confusing.
    // if we put this class in it's own file we would not be able to access the Point constructor.
    // (unless we made it public again, meaning it could be instantiated by either Factory or directly... even more confusing!)

    // HOWEVER - this would only be if you have access to the source code.  likely you'll be creating a factory
    // as a utility to streamline existing code

    public static class Factory {

        public static Point newCartesianPoint(double x, double y) {
            return new Point(x, y);
        }

        public static Point newPolarPoint(double rho, double theta) {
            double x = rho * Math.cos(theta);
            double y = rho * Math.sin(theta);
            return new Point(x, y);
        }
    }

}






