package com.demo.other;

import java.io.*;

public class SerializableTest {
    public static void main(String[] args) throws Exception {
        FileOutputStream fos = new FileOutputStream("temp.out");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        TestObject testObject = new TestObject();
        testObject.setN(33);
        oos.writeObject(testObject);
        oos.flush();
        oos.close();

        FileInputStream fis = new FileInputStream("temp.out");
        ObjectInputStream ois = new ObjectInputStream(fis);
        TestObject deTest = (TestObject) ois.readObject();
        System.out.println(deTest.testValue);
        System.out.println(deTest.parentValue);
        System.out.println(deTest.innerObject.innerValue);
        System.out.println(deTest.getN());

    }
}

class Parent implements Externalizable {

    private static final long serialVersionUID = -4963266899668807475L;

    public int parentValue = 100;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}

class InnerObject implements Externalizable {


    private static final long serialVersionUID = 5704957411985783570L;

    public transient int innerValue = 200;

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
//        out.writeObject();

    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    }
}

class TestObject extends Parent implements Externalizable {

    private static final long serialVersionUID = -3186721026267206914L;

    public int testValue = 300;


    public int n;

    public InnerObject innerObject = new InnerObject();

    public TestObject() {
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(n);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
       n = (Integer) in.readObject();
    }
}