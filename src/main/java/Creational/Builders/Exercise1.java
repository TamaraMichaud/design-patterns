package Creational.Builders;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class Exercise1 {
    public static void main(String[] args) {

        new CodeBuilderTest().emptyTest();
        new CodeBuilderTest().personTest();

        CodeBuilder cb = new CodeBuilder("Person").addField("name", "String").addField("age", "int");
        System.out.println(cb);
    }
}

@SuppressWarnings("WeakerAccess")
class CodeBuilder {
    private SomeCode theCode;
    private final String indent = "  ";

    public CodeBuilder(String className) {
        this.theCode = new SomeCode(className);
    }

    public CodeBuilder addField(String name, String type) {
        this.theCode.setField(name, type);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("public class %s\n{\n", this.theCode.getClassName()));
        for (SomeField field : this.theCode.getFieldsList()) {
            sb.append(indent + field);
        }
        sb.append("}");
        return sb.toString();
    }
}

class SomeCode {

    private String className;
    private List<SomeField> fieldsList;

    SomeCode(String className) {
        this.className = className;
        this.fieldsList = new ArrayList<>();
    }

    void setField(String name, String type) {
        this.fieldsList.add(new SomeField().setField(name, type));
    }

    String getClassName() {
        return this.className;
    }

    List<SomeField> getFieldsList() {
        return this.fieldsList;
    }
}

class SomeField {
    private String name, type;

    public SomeField setField(String name, String type){
        this.name = name;
        this.type = type;
        return this;
    }

    @Override
    public String toString() {
        return String.format("public %s %s;\n", type, name);
    }
}


@SuppressWarnings("WeakerAccess")
class CodeBuilderTest {

    private String preprocess(String text) {
        return text.replace("\r\n", "\n").trim();
    }

    @Test
    public void emptyTest() {
        CodeBuilder cb = new CodeBuilder("Foo");
        assertEquals("public class Foo\n{\n}",
                preprocess(cb.toString()));
    }

    @Test
    public void personTest() {
        CodeBuilder cb = new CodeBuilder("Person")
                .addField("name", "String")
                .addField("age", "int");
        assertEquals("public class Person\n{\n" +
                        "  public String name;\n" +
                        "  public int age;\n}",
                preprocess(cb.toString()));
    }
}
