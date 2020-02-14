package SOLID;

// example, filter Products by Colour and/or Size... but what if we add more filters...?
// in order to modify the implementation which uses class "BadFilter"; developers would need
// to modify this *already-completed* class...
// PRINCIPLE STATES: code should be closed for development but open for expansion...

// we are using the "Specification" pattern here; a pattern of enterprise engineering.
import java.util.List;
import java.util.stream.Stream;

public class B_OpenClosePrinciple {

    public static void main(String[] args) {

        Product apple = new Product("Apple", Color.RED, Size.SMALL);
        Product tree = new Product("Tree", Color.GREEN, Size.LARGE);
        Product house = new Product("House", Color.BLUE, Size.LARGE);

        List<Product> products = List.of(apple, tree, house);

        // bad implementation
        BadFilter bf = new BadFilter();
        System.out.println("[bad] All GREEN things: ");
        bf.filterByColor(products, Color.GREEN)
                .forEach(p -> System.out.println(" - " + p.name));

        // good implementation
        GoodFilter gf = new GoodFilter();
        System.out.println("[good] All LARGE things: ");
        //SizeSpec sizeSpec = new SizeSpec(Size.LARGE);
        gf.filter(products, new SizeSpec(Size.LARGE))
                .forEach(p -> System.out.println(" - " + p.name));

        // good implementation with two criteria:
        System.out.println("[good] All LARGE, BLUE things: ");
        gf.filter(products, new AndSpecification<>(
                                        new SizeSpec(Size.LARGE),
                                        new ColorSpec(Color.BLUE)
                )).forEach(p -> System.out.println(" - " + p.name));



    }
}


enum Color { RED, GREEN, BLUE }
enum Size { SMALL, MEDIUM, LARGE }

class Product {

    public String name;
    public Color color;
    public Size size;

    public Product(String name, Color color, Size size) {
        this.name = name;
        this.color = color;
        this.size = size;
    }
}

class BadFilter {

    public Stream<Product> filterByColor(List<Product> products,
                                         Color color) {
        return products.stream().filter(p -> p.color == color);
    }

    public Stream<Product> filterBySize(List<Product> products,
                                        Size size) {
        return products.stream().filter(p -> p.size == size);
    }

    public Stream<Product> filterBySizeANDColor(List<Product> products,
                                                Size size,
                                                Color color) {
        return products.stream().filter(p -> p.size == size && p.color == color);
    }

    // ^^ if we add a third criteria, e.g. price; we'd end up with 7 methods here for all combinations; etc.
    // we want this "Filter" class to be far more generic

}

// with these 2 interfaces we have made very generic foundation for "does this object satisfy a criteria"...
interface Specification<T> {
    boolean isSatisfied(T item);
}
interface Filter<T> {
    Stream<T> filter(List<T> items, Specification<T> spec);
}

// this implementation for "Color" will never change, but we can create more/new for different types as they are added...
class ColorSpec implements Specification<Product> {

    private Color color;

    public ColorSpec(Color color) {
        this.color = color;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.color == this.color;
    }
}

class SizeSpec implements Specification<Product> {

    private Size size;

    public SizeSpec(Size size) {
        this.size = size;
    }

    @Override
    public boolean isSatisfied(Product item) {
        return item.size == this.size;
    }
}

// so now we can create a super generic "Filter" class that can handle all/future "xSpec" classes that implement Specification!

class GoodFilter implements Filter<Product> {

    @Override
    public Stream<Product> filter(List<Product> items, Specification<Product> spec) {
        return items.stream().filter(p -> spec.isSatisfied(p));
    }
}

// and what about 2, or more, criteria?
class AndSpecification<T> implements Specification<T> {

    private Specification<Product> first, second;

    public AndSpecification(Specification<Product> first, Specification<Product> second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public boolean isSatisfied(T item) {
        return first.isSatisfied((Product) item) && second.isSatisfied((Product) item);
    }
}


