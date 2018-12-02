package com.jingyli.thoughtworks.demo.cleanCode;

public abstract class ArgumentMarshaler {
    public abstract void set(String s) throws ArgumentException;
    public abstract Object get();
}

