package Structural.Composite;

import java.util.*;
import java.util.function.Consumer;

public class dExercise7 {
    public static void main(String[] args) {
        SingleValue val1 = new SingleValue(8);
        SingleValue val2 = new SingleValue(9);
        ManyValues man = new ManyValues();
        man.add(2);
        man.add(1);
        System.out.println(new MyList(List.of(man, val1, val2)).sum());
    }
}

interface ValueContainer extends Iterable<Integer> {}

class SingleValue implements ValueContainer
{
    public int value;

    // please leave this constructor as-is
    public SingleValue(int value)
    {
        this.value = value;
    }

    @Override
    public Iterator<Integer> iterator() {
        return Collections.singleton(this.value).iterator();
    }

    @Override
    public void forEach(Consumer<? super Integer> action) {
        action.accept(this.value);
    }

    @Override
    public Spliterator<Integer> spliterator() {
        return Collections.singleton(this.value).spliterator();
    }
}

class ManyValues extends ArrayList<Integer> implements ValueContainer
{
}


class MyList extends ArrayList<ValueContainer>
{
    // please leave this constructor as-is
    public MyList(Collection<? extends ValueContainer> c)
    {
        super(c);
    }

    public int sum()
    {
        int total = 0;
        for(ValueContainer vc : this) {
            for(int val : vc) {
                System.out.println("Add " + val);
                total += val;
            }
        }
        return total;
    }
}