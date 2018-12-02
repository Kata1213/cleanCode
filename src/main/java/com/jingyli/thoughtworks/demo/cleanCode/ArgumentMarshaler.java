package com.jingyli.thoughtworks.demo.cleanCode;

public abstract class ArgumentMarshaler {
    private boolean booleanValue = false;
    private String stringValue ;
    private int integerValue;

    public void setBoolean(boolean value) {
        booleanValue = value;
    }

    public boolean getBoolean() {
        return booleanValue;
    }

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
}

