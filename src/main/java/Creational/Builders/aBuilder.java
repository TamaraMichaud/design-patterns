package Creational.Builders;

// simple objects can be created with a single constructor call. e.g. "Point(x,y)"
// other objects require a lot of ceremony. e.g. constructing a string from a dozen different places, you want a stringBuilder
//  having a constructor with 12 args is not productive
//  - so go for piecewise construction

// when piecewise construction is overly complicated,
// use builder pattern to provide an api for doing it succinctly

import java.util.ArrayList;
import java.util.Collections;

// e.g. making an application for serving web pages.  SUPER BASIC... has worth?
public class aBuilder {
    public static void main(String[] args) {
        new Builder101().helloWorld();
        new Builder101().helloEndlessText();
    }

}

@SuppressWarnings("WeakerAccess")
class Builder101 {

    public void helloWorld() {
        String hello = "Hello";
        System.out.println(String.format("<p>%s</p>", hello)); // fine for a single value but what about multiple

        String[] words = {"Hello", "World"};
        System.out.println("<ul>\n" + "<li>" + words[0] + "</li><li> ..."); // this stinks...
    }

    public void helloEndlessText() {
        String[] words = {"Hello", "World"};
        // so we use a builder.  in this case the jdk built-in: StringBuilder
        StringBuilder sb = new StringBuilder();
        sb.append("<ul>\n");
        for (String word : words) {
            sb.append(String.format("  <li>%s</li>\n", word));
        }
        sb.append("</ul>"); // beautiful
        System.out.println(sb);
    }
}


// in 101 we just used a list of strings and string builder. but what if we wanted to store our web elements as objects...
class BuilderSmart {

    public static void main(String[] args) {
        HtmlBuilder hb = new HtmlBuilder("ul");
        hb.addChild("li", "item1");
        hb.addChild("li", "item2");
        hb.addChild("li", "item3");

        System.out.println(hb);

        HtmlFluentBuilder hb2 = new HtmlFluentBuilder("ul");
        hb2.addChild("li", "item1")
            .addChild("li", "item2")
            .addChild("li", "item3");

        System.out.println(hb2);

    }
}

@SuppressWarnings({"WeakerAccess", "FieldCanBeLocal"})
class HtmlElement {
    public String name, text; // e.g. li, provided-text
    public ArrayList<HtmlElement> elements = new ArrayList<>();
    private final int indentSize = 2;
    private final String newLine = System.lineSeparator();

    public HtmlElement(String name, String text) {
        this.name = name; // element type, e.g. li / ul
        this.text = text; // text content if applicable
    }

    public HtmlElement() {
    }

    @Override
    public String toString() { // this can have nested elements so we need it super recursive...
        return toStringImpl(0); // first level elements have no indent
    }

    private String toStringImpl(int indent) {
        StringBuilder sb = new StringBuilder();

        String i = String.join("", Collections.nCopies(indent * indentSize, " "));
        sb.append(String.format("%s<%s>%s", i, name, newLine)); // open outer tag

        if (text != null && !text.isEmpty()) {
            String ind = String.join("", Collections.nCopies((indent + 1) * indentSize, " "));
            sb.append(String.format("%s%s%s", ind, text, newLine)); // add text if applicable
        }
        for (HtmlElement element : elements) { // loop inner elements outer/inner tags etc
            sb.append(element.toStringImpl(indent + 1));
        }

        sb.append(String.format("%s</%s>%s", i, name, newLine)); // close outer tag

        return String.valueOf(sb);
    }
}

@SuppressWarnings({"WeakerAccess", "unused"})
class HtmlBuilder {
    // holds the root html element, which contains everything.
    // any time anyone interacts with this object they are manipulating this element
    private String rootName;
    private HtmlElement rootElement = new HtmlElement();

    public HtmlBuilder(String rootName) {
        this.rootName = rootName; // ul / li / p etc
        rootElement.name = rootName;
    }

    public void addChild(String childName, String childText) {
        HtmlElement e = new HtmlElement(childName, childText);
        rootElement.elements.add(e);
    }

    public void clear() {
        rootElement = new HtmlElement();
        rootElement.name = rootName;
    }

    @Override
    public String toString() {
        return rootElement.toString();
    }


}

@SuppressWarnings("WeakerAccess, unused")
class HtmlFluentBuilder {
    // holds the root html element, which contains everything.
    // any time anyone interacts with this object they are manipulating this element
    private String rootName;
    private HtmlElement rootElement = new HtmlElement();

    public HtmlFluentBuilder(String rootName) {
        this.rootName = rootName; // ul / li / p etc
        rootElement.name = rootName;
    }

    public HtmlFluentBuilder addChild(String childName, String childText) {
        HtmlElement e = new HtmlElement(childName, childText);
        rootElement.elements.add(e);
        return this;
    }

    public void clear() {
        rootElement = new HtmlElement();
        rootElement.name = rootName;
    }

    @Override
    public String toString() {
        return rootElement.toString();
    }


}

