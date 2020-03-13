package Structural.Composite;

// e.g. in adobe you can move a snippet, or you can ctrl+click move a group of them...

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class bGeometricShapes {
    public static void main(String[] args) {
        GraphicObject drawing = new GraphicObject();
        drawing.setName("MyDrawing");
        drawing.children.add(new Square("red"));
        drawing.children.add(new Circle("yellow"));

        GraphicObject group = new GraphicObject();
        group.children.add(new Circle("blue"));
        group.children.add(new Circle("green"));

        drawing.children.add(group);

        System.out.println(drawing);

    }
}

class GraphicObject { // not abstract, you can instantiate it - so it can act as a group of objects...

    protected String name = "Group"; // << default name, this is meant to be a group of objects...
    public String color;
    public List<GraphicObject> children = new ArrayList<>(); //

    public GraphicObject() {
    }

//    public GraphicObject(String name) {
//        this.name = name;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        print(sb, 0);
        return sb.toString();
    }

    private void print(StringBuilder sb, int depth){
//        sb.append(String.join("" + Collections.nCopies(depth, "*")))
        sb.append(Collections.nCopies(depth, "*")
                        .toString().replace("[", "")
                                    .replace("]", "")
                                    .replace(",", "")) // << dunno why it formats as an array, clean it the long way cos im high
                .append(depth > 0 ? " " : "")
                .append((color == null || color.isEmpty()) ? "" : " ")
                .append(getName())
                .append("\n");
        for(GraphicObject child : children) {
            child.print(sb, depth+1); // << NNGHHH this hurts my head
        }
    }
}

class Circle extends GraphicObject {
    public Circle(String color) {
        name = "Circle";
        this.color = color;
    }
}

class Square extends GraphicObject {
    public Square(String color) {
        name = "Square";
        this.color = color;
    }
}
