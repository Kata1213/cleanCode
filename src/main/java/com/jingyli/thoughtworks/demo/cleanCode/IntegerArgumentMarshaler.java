package com.jingyli.thoughtworks.demo.cleanCode;


import java.util.Iterator;
import java.util.NoSuchElementException;

public class IntegerArgumentMarshaler extends ArgumentMarshaler {
    private int intValue = 0;

    @Override
    public Object get() {
        return intValue;
    }

    @Override
    public void set(Iterator<String> currentArgument) throws ArgumentException {
        String parameter;
        try {
            parameter = currentArgument.next();
            intValue = Integer.parseInt(parameter);
        } catch (NoSuchElementException e) {
            throw new ArgumentException();
        } catch (NumberFormatException e) {
            throw new ArgumentException();
        }
    }
}
