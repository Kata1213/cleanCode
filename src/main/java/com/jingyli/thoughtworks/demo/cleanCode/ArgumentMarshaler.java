package com.jingyli.thoughtworks.demo.cleanCode;

import java.util.Iterator;

public abstract class ArgumentMarshaler {
    public abstract void set(String s) throws ArgumentException;

    public abstract Object get();

    public abstract void set(Iterator<String> currentArgument) throws ArgumentException;
}

