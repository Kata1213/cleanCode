package com.jingyli.thoughtworks.demo.cleanCode;

public abstract class ArgumentMarshaler {
    protected boolean booleanValue = false;
    private String stringValue ;
    private int integerValue;

    public String getStringValue() {
        return stringValue == null ? "" : stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }

    public int getIntegerValue() {
        return integerValue;
    }

    public void setIntegerValue(int integerValue) {
        this.integerValue = integerValue;
    }

    public abstract void set(String s);

    public abstract Object get();
}

