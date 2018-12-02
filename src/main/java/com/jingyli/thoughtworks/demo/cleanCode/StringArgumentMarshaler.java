package com.jingyli.thoughtworks.demo.cleanCode;

public class StringArgumentMarshaler extends ArgumentMarshaler{
    private String stringValue="";

    @Override
    public void set(String s) {
        stringValue=s;
    }

    @Override
    public Object get() {
        return stringValue;
    }
}
