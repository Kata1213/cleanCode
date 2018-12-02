package com.jingyli.thoughtworks.demo.cleanCode;

public abstract class ArgumentMarshaler {
    protected boolean booleanValue = false;
    protected String stringValue ;
    private int integerValue;

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public abstract void set(String s);

    public abstract Object get();
}

