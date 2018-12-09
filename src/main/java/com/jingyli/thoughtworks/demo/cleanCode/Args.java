package com.jingyli.thoughtworks.demo.cleanCode;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.util.*;


public class Args {
    private String schema;
    private Map<Character, ArgumentMarshaler> marshalerMap = new HashMap<Character, ArgumentMarshaler>();
    private Set<Character> argsFound = new HashSet<Character>();
    private Iterator<String> currentArgument;
    private List<String> argsList;

    public Args(String schema, String[] args) throws ArgumentException, ArgsException, ParseException {
        this.schema = schema;
        argsList = Arrays.asList(args);
        parse();
    }

    private void parse() throws ArgumentException, ParseException, ArgsException {
        parseSchema();
        parseArguments();
    }

    private boolean parseSchema() throws ArgumentException, ParseException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                parseSchemaElement(element.trim());
            }
        }
        return true;
    }

    private void parseSchemaElement(String element) throws ArgumentException, ParseException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if(elementTail.length()==0)
            marshalerMap.put(elementId,new BooleanArgumentMarshaler());
        else if (elementTail.equals("*"))
            marshalerMap.put(elementId, new StringArgumentMarshaler());
        else if (elementTail.equals("#"))
            marshalerMap.put(elementId,new IntegerArgumentMarshaler());
        else
            throw new ArgumentException(elementId,elementTail,ErrorCode.INVALID_FORMAT);
    }

    private void validateSchemaElementId(char elementId) throws ParseException {
        if (!Character.isLetter(elementId)) {
            throw new ParseException(
                    "Bad character:" + elementId + "in Args format: " + schema, 0);
        }
    }

    private boolean parseArguments() throws ArgsException, ArgumentException {
        for (currentArgument = argsList.iterator(); currentArgument.hasNext(); ) {
            String arg = currentArgument.next();
            parseArgument(arg);
        }
        return true;
    }

    private void parseArgument(String arg) throws ArgsException, ArgumentException {
        if (arg.startsWith("-"))
            parseElements(arg);
    }

    private void parseElements(String arg) throws ArgsException, ArgumentException {
        for (int i = 1; i < arg.length(); i++)
            parseElement(arg.charAt(i));
    }

    private void parseElement(char argChar) throws ArgumentException, ArgsException {
        if (setArgument(argChar))
            argsFound.add(argChar);
        else {
            throw  new ArgumentException(argChar,null,ErrorCode.UNEXPECTED_ARGUMENT);
        }
    }

    private boolean setArgument(char argChar) throws ArgsException, ArgumentException {
        ArgumentMarshaler am = marshalerMap.get(argChar);
        if (am == null) {
            return false;
        }
        try {
            am.set(currentArgument);
            return true;
        } catch (ArgumentException e) {
            e.setErrorArgumentId(argChar);
            throw e;
        }
    }


    public int cardinality() {
        return argsFound.size();
    }

    public String usage() {
        if (schema.length() > 0)
            return "-[" + schema + "]";
        else
            return "";
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshalerMap.get(arg);
        try {
            return am == null ? "" : (String) am.get();
        } catch (ClassCastException e) {
            return "";
        }
    }

    public int getInt(char arg) {
        ArgumentMarshaler am = marshalerMap.get(arg);
        try {
            return am == null ? 0 : (Integer) am.get();
        } catch (ClassCastException e) {
            return 0;
        }
    }

    public boolean getBoolean(char arg) {
        ArgumentMarshaler am = marshalerMap.get(arg);
        try {
            return am != null && (Boolean) am.get();
        } catch (ClassCastException e) {
            return false;
        }
    }

    public boolean has(char arg) {
        return argsFound.contains(arg);
    }

    private class ArgsException extends Exception {
    }
}
