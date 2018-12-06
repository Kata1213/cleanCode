package com.jingyli.thoughtworks.demo.cleanCode;

import java.util.Iterator;

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

    @Override
    public void set(Iterator<String> currentArgument) throws ArgumentException {

    }
}
