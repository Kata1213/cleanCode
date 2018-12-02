package com.jingyli.thoughtworks.demo.cleanCode;

public abstract class ArgumentMarshaler {
    protected boolean booleanValue = false;
    protected String stringValue ;
    protected int integerValue;

    public abstract void set(String s) throws ArgumentException;

    public abstract Object get();
}

