package com.jingyli.thoughtworks.demo.cleanCode;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class StringArgumentMarshaler extends ArgumentMarshaler {
    private String stringValue = "";


    @Override
    public Object get() {
        return stringValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgumentException {
        try {
            stringValue = currentArgument.next();
        } catch (NoSuchElementException e) {
            throw new ArgumentException();
        }
    }
}
