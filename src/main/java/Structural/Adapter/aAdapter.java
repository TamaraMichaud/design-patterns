package Structural.Adapter;

public class aAdapter {

    // this is all about getting the interface you want from the interface you were given..
    // just like a plug adapter; incoming voltage and/or pin-numbers can vary between countries
    // so your adapter helps convert the in/out pins (from 3 to 2 etc) and voltage accordingly.

    // a construct which adapts an existing interface X to conform to the required interface Y

    // sometimes when using this method your adapter class will build temporary objects,
    // so to save this duplication of memory, we can introduce caching.
    // in this case we implemented the hashCode() method on our pojos and used a hashmap to
    // store & check for previously-created temporary objects

    // summary:
    // determine the api that you have/must use - and the api you need
    // create a component which aggregates the adaptee (i.e. caching to avoid replication)
    // otherwise intermediate representations can pile up


}
