package SOLID;

public class D_InterfaceSegregationPrinciple {
    public static void main(String[] args) {
        System.out.println("meh");
    }
}

class Document {}

// a Machine might do any of the following things to a Document
interface Machine {
    void print(Document d);
    void fax(Document d);
    void scan(Document d);
}

// YAGNI = You Ain't Going To Need It!



// so this is great when it's a fancy printer that does actually do all these things...
class MultiFunctionPrinter implements Machine {
    @Override
    public void print(Document d) { /* yay I can print */ }

    @Override
    public void fax(Document d) { /* huzzah I can fax */ }

    @Override
    public void scan(Document d) { /* yeah man I can even scan! */ }
}

// but what about a plain old printer that simply prints... what about these extra methods?
class PlainPrinter implements Machine {
    @Override
    public void print(Document d) {/* yay I can print */}

    @Override
    public void fax(Document d) { /* err.. what's a fax? */ }

    @Override
    public void scan(Document d) { /* not today thanks... should I throw an exception maybe...?
     well this then means we have to add the exception specification to the exception signature, and deal with the
     propagation of this back up to the interface; thereby affecting any other implementations too... */ }
}


// SO - the rule is that you should not put into your interface, more than the client requires.
// as such, we'd need 3 interface classes to be mixed and matched as required  [[ OR, option 2 below: ]]
interface Printer { void print(Document d);}
interface Fax {void fax(Document d);}
interface Scan { void scan(Document d);}

// and implement them like:
class ScannerOnly implements Scan {
    @Override
    public void scan(Document d) {}
}

class AllBellsAndWhistles implements Printer, Fax, Scan {
    @Override
    public void print(Document d) {}

    @Override
    public void fax(Document d) {}

    @Override
    public void scan(Document d) {}
}

// OR, option 2: an interface which extends multiple interfaces
interface Magic extends Fax, Scan {}

class MakeMagic implements Magic {
    @Override
    public void fax(Document d) { }

    @Override
    public void scan(Document d) { }
}
