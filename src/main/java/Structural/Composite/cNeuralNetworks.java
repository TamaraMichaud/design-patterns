package Structural.Composite;

// machine learning

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class cNeuralNetworks {
    public static void main(String[] args) {
        Neuron neuron = new Neuron();
        Neuron neuron2 = new Neuron();
        NeuronLayer layer = new NeuronLayer();
        NeuronLayer layer2 = new NeuronLayer();

//        neuron.connectTo(neuron2);
//        neuron.connectTo(layer);
//        layer.connectTo(neuron);
//        layer2.connectTo(layer);
        // ^^ the last 3 wont work, we'd need to implement connectTo() in the layer class. and if we had more, it would get worse.
        // so... we have an object, and a list of that object - we need an individual and a list to behave in the same way
        // this is the composite design in action: implement an interface of Iterable<object> that both other classes use

        neuron.connectTo2(neuron2);
        neuron.connectTo2(layer);
        layer.connectTo2(neuron);
        layer2.connectTo2(layer);
    }
}

interface SomeNeurons extends Iterable<Neuron> {
// since this is an interface, we dont need to imeplement Iterable (interface too) methods here, but rather in the implementation
    // which in this case is only Neuron, even though NeuronLayer extends this too - becuase NeuronLayer is only
    // a List<Neuron> - List already being iterable anyway, T is the missing link

    // by implementing Iterable in this interface we have enabled our object to handle "connectTo()" well now,
    // but we need to use it!
    default void connectTo2(SomeNeurons other) { // << ugly, not ooo, should be an abstract class really... haha
        if (this == other) return; // can't connect to ourselves
        for (Neuron from : this) { // << same as previous but now we loop, if theres only one neuron, only one loop
            for (Neuron to : other) {
                from.out.add(to);
                to.in.add(from);
            }
        }
    }

}

class Neuron implements SomeNeurons {
    // has connections to other neurons
    public ArrayList<Neuron> in = new ArrayList<>();
    public ArrayList<Neuron> out = new ArrayList<>();

    @Override
    public Iterator<Neuron> iterator() {
        return Collections.singleton(this).iterator();
    }

    @Override
    public void forEach(Consumer<? super Neuron> action) {
        action.accept(this);
    }

    @Override
    public Spliterator<Neuron> spliterator() {
        return Collections.singleton(this).spliterator();
    }

    // now we want to connect neurons to other neurons...
    // but this wont work for List<Neuron> so we need the interface
//    public void connectTo(Neuron other){
//        out.add(other);
//        other.in.add(this);
//    }
}

class NeuronLayer extends ArrayList<Neuron> implements SomeNeurons {
}

