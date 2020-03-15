package Structural.Proxy;

// dynamic proxies are used everywhere.
// they are generated at run-time rather than compile-time


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class dDynamicProxyForLogging {
    public static void main(String[] args) {
        Persona person = new Persona();
        Human loggedPerson = withLogging(person, Human.class);
        loggedPerson.talk();
        loggedPerson.talk();
        loggedPerson.walk();
        System.out.println(loggedPerson); // << todo: debug: why does it log "toString" too... it should break out.
    }

    // utility method to create dynamic proxy for any object not only a person
    @SuppressWarnings("unchecked")
    public static <T> T withLogging(T target, Class<T> itf) { // << itf being indicator of the return value type..(?)

        return (T) Proxy.newProxyInstance(
                itf.getClassLoader(),
                // ^^ we could have more classes here for the below array... but we've only got Person
                new Class<?>[]{itf},
                new DynamicProxyAsLoggingHandler(target) // << I have no idea wtf is going on
        );

    }
}

// this is how we intercept methods from other classes. e.g. to make logs
class DynamicProxyAsLoggingHandler implements InvocationHandler {

    private final Object target; // << the thing we are creating a proxy for
    private Map<String, Integer> callCount = new HashMap<>(); // << map the names of methods to the number of times they are called

    public DynamicProxyAsLoggingHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String name = method.getName();
        callCount.merge(name, 1, Integer::sum); // increment callCount for this method

        // what if the method being called is "toString"... we want to print this count out
        if (name.contains("toString")) {
            return callCount.toString();
        }

        return method.invoke(target, args); // << use the reflection API to actually invoke the method
    }
}


interface Human {
    void walk();
    void talk();
}

class Persona implements Human {

    @Override
    public void walk() {
        System.out.println("I am walking.");
    }

    @Override
    public void talk() {
        System.out.println("I am talking.");
    }
}