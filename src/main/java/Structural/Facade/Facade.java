package Structural.Facade;

// build a facade to provide a simplified api over a set of classes / subsystem
// you may wish to optionally expose internals through the facade (we have done so in the below)
// this is typically done for the power users


// a simple interface to wrap a large/complex code base (?)

// stock market data... imagine multiple monitors with multiple consoles containing viewports showing buffered data

import java.util.ArrayList;
import java.util.List;

public class Facade {

    public static void main(String[] args) {

        // currently the console is very low-level:
        Buffer buffer = new Buffer(30, 20);
        ViewPort viewPort = new ViewPort(buffer, 20, 30, 1, 1);
        Console console = new Console(30, 20);
        console.addViewPort(viewPort);
        console.render();
        // ^^ lots of stuff here we don't really care about.
        // so this is where the facade pattern will provide an overarching api to take care of all of it for us

        // by adding our new static facade method we can do easy:
        Console console1 = Console.newConsole(30, 20, 1, 1);
        console1.render();

    }
}


// for a user, they don't want all this junk. they just want to be able to spin up a
// console and push in some data

class Buffer {

    private char[] characters;
    private int lineWidth;

    public Buffer(int lineHeight, int lineWidth) {
        this.lineWidth = lineWidth;
        this.characters = new char[lineHeight * lineWidth];
    }

    public char charAt(int x, int y){
        return characters[y*lineWidth+x];

    }
}

class ViewPort {
    private final Buffer buffer;
    private final int width;
    private final int height;
    private final int offsetX;
    private final int offsetY;

    public ViewPort(Buffer buffer, int width, int height,
                    int offsetX, int offsetY){

        this.buffer = buffer;
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public char charAt(int x, int y){
        return buffer.charAt(x + offsetX, y + offsetY);
    }
}

class Console {
    private List<ViewPort> viewPorts = new ArrayList<>();
    int width, height;

    public Console(int width, int height) {
        this.width = width;
        this.height = height;
    }

    // let's make it much better, add this method:
    public static Console newConsole(int height, int width, int offsetX, int offsetY) {
        Buffer buffer = new Buffer(height, width);
        ViewPort viewPort = new ViewPort(buffer, width, height, offsetX, offsetY);
        Console console = new Console(width, height);
        console.addViewPort(viewPort);
        return console;
    }

    public void addViewPort(ViewPort viewPort){
        this.viewPorts.add(viewPort);
    }

    public void render(){
        for(int y = 0; y < height; ++y){
            for(int x = 0; x < width; ++x){
                for (ViewPort vp : viewPorts) {
                    System.out.println(vp.charAt(x, y));
                }
            }
        }
    }
}