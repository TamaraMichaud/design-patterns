package Structural.Decorator;

// combination of adapter and decorator

public class eAdapterDecorator {
    public static void main(String[] args) {
        MyStringBuilder myStringBuilder = new MyStringBuilder();
        myStringBuilder.append("hello");
        myStringBuilder.appendLine(" banana,");
        myStringBuilder.append("how are you?");

        System.out.println(myStringBuilder.toString());
    }
}

@SuppressWarnings("all")
class MyStringBuilder {

    private StringBuilder sb;

    public MyStringBuilder() {
        this.sb = new StringBuilder();
    }

    public MyStringBuilder(String string) {
        this.sb = new StringBuilder();
        this.sb.append(string);
    }


    // custom methods
    public MyStringBuilder appendLine(String str) {
        this.sb.append(str)
                .append(System.lineSeparator());
        return this;
    }

    public String toString() {
        return sb.toString();
    }
    //delegate methods. we have some problems here.
    // for example we want a fluent interface.  so all the append() methods should return "MyStringBuilder"...
    // vv original delegated methods... had to manually (regex) change all the others :/
//    public StringBuilder append(Object obj) {
//        return sb.append(obj);
//    }


    // delegetaed methods

    public MyStringBuilder append(Object obj) {
        sb.append(obj);
        return this;
    }

    public MyStringBuilder append(String str) {
        sb.append(str);
        return this;
    }

    public int compareTo(MyStringBuilder another) {
        return sb.compareTo(another.sb);
    }

    // deleted the other delegated methods - took us to 269 for no good reason!

}