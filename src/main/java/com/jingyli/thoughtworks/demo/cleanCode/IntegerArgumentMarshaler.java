package com.jingyli.thoughtworks.demo.cleanCode;


import java.util.Iterator;

public class IntegerArgumentMarshaler extends ArgumentMarshaler{
    private int intValue=0;

    @Override
    public void set(String s) throws ArgumentException {
        try{
            intValue=Integer.parseInt(s);
        }catch (NumberFormatException e){
            throw new ArgumentException();
        }
    }

    @Override
    public Object get() {
        return intValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgumentException {

    }
}
