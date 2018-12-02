package com.jingyli.thoughtworks.demo.cleanCode;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {
    private boolean booleanValue=false;

    @Override
    public void set(String s) {
        booleanValue = true;
    }

    @Override
    public Object get() {
        return booleanValue;
    }
}
