package com.jingyli.thoughtworks.demo.cleanCode;

import java.util.Iterator;

public class BooleanArgumentMarshaler extends ArgumentMarshaler {
    private boolean booleanValue = false;

    @Override
    public Object get() {
        return booleanValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgumentException {
        booleanValue = true;
    }
}
