package com.jingyli.thoughtworks.demo.cleanCode;

import java.text.ParseException;
import java.util.*;


public class Args {
    private String schema;
    private String[] args;
    private boolean valid = true;
    private Set<Character> unexpectedArguments = new TreeSet<Character>();
    private Map<Character, ArgumentMarshaler> marshalerMap = new HashMap<Character, ArgumentMarshaler>();
    private Set<Character> argsFound = new HashSet<Character>();
    private int currentArgument;
    private char errorArgumentId = '\0';
    private String errorParameter = "TILT";
    private ErrorCode errorCode = ErrorCode.OK;

    private enum ErrorCode {
        OK, MISSING_STRING, MISSING_INTEGER, INVALID_INTEGER, UNEXPECTED_ARGUMENT}

    public Args(String schema, String[] args) throws ParseException, ArgumentException {
        this.schema = schema;
        this.args = args;
        valid = parse();
    }

    private boolean parse() throws ParseException, ArgumentException {
        if (schema.length() == 0 && args.length == 0)
            return true;
        parseSchema();
        try {
            parseArguments();
        } catch (ArgsException e) {
        }
        return valid;
    }

    private boolean parseSchema() throws ParseException {
        for (String element : schema.split(",")) {
            if (element.length() > 0) {
                String trimmedElement = element.trim();
                parseSchemaElement(trimmedElement);
            }
        }
        return true;
    }

    private void parseSchemaElement(String element) throws ParseException {
        char elementId = element.charAt(0);
        String elementTail = element.substring(1);
        validateSchemaElementId(elementId);
        if (isBooleanSchemaElement(elementTail))
            marshalerMap.put(elementId,new BooleanArgumentMarshaler());
        else if (isStringSchemaElement(elementTail))
            marshalerMap.put(elementId,new StringArgumentMarshaler());
        else if (isIntegerSchemaElement(elementTail)) {
            marshalerMap.put(elementId, new IntegerArgumentMarshaler());
        } else {
            throw new ParseException(
                    String.format("Argument: %c has invalid format: %s.",
                            elementId, elementTail), 0);
        }
    }

    private void validateSchemaElementId(char elementId) throws ParseException {
        if (!Character.isLetter(elementId)) {
            throw new ParseException(
                    "Bad character:" + elementId + "in Args format: " + schema, 0);
        }
    }

    private boolean isStringSchemaElement(String elementTail) {
        return elementTail.equals("*");
    }

    private boolean isBooleanSchemaElement(String elementTail) {
        return elementTail.length() == 0;
    }

    private boolean isIntegerSchemaElement(String elementTail) {
        return elementTail.equals("#");
    }

    private boolean parseArguments() throws ArgsException, ArgumentException {
        for (currentArgument = 0; currentArgument < args.length; currentArgument++)
        {
            String arg = args[currentArgument];
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

    private void parseElement(char argChar) throws ArgsException, ArgumentException {
        if (setArgument(argChar))
            argsFound.add(argChar);
        else {
            unexpectedArguments.add(argChar);
            errorCode = ErrorCode.UNEXPECTED_ARGUMENT;
            valid = false;
        }
    }

    private boolean setArgument(char argChar) throws ArgsException, ArgumentException {
        ArgumentMarshaler am = marshalerMap.get(argChar);
        if(am instanceof BooleanArgumentMarshaler)
            setBooleanArg(am);
        else if(am instanceof StringArgumentMarshaler)
            setStringArg(am);
        else if(am instanceof IntegerArgumentMarshaler)
            setIntArg(am);
        else return false;
        return true;
    }

    private void setIntArg(ArgumentMarshaler argumentMarshaler) throws ArgsException, ArgumentException {
        currentArgument++;
        String parameter = null;
        try {
            parameter = args[currentArgument];
            argumentMarshaler.set(parameter);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorCode = ErrorCode.MISSING_INTEGER;
            throw new ArgsException();
        } catch (ArgumentException e) {
            errorParameter = parameter;
            errorCode = ErrorCode.INVALID_INTEGER;
            throw e;
        }
    }

    private void setStringArg(ArgumentMarshaler argumentMarshaler) throws ArgsException, ArgumentException {
        currentArgument++;
        try {
            argumentMarshaler.set(args[currentArgument]);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorCode = ErrorCode.MISSING_STRING;
            throw new ArgsException();
        }
    }

    private void setBooleanArg(ArgumentMarshaler argumentMarshaler) throws ArgumentException {
        argumentMarshaler.set("true");
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

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: Should not get here.");
            case UNEXPECTED_ARGUMENT:
                return unexpectedArgumentMessage();
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c.",
                        errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -%c expects an integer but was '%s'.",
                        errorArgumentId, errorParameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c.",
                        errorArgumentId);
        }
        return "";
    }

    private String unexpectedArgumentMessage() {
        StringBuffer message = new StringBuffer("Argument(s) -");
        for (char c : unexpectedArguments) {
            message.append(c);
        }
        message.append(" unexpected.");
        return message.toString();
    }

    private boolean falseIfNull(Boolean b) {
        return b != null && b;
    }

    private int zeroIfNull(Integer i) {
        return i == null ? 0 : i;
    }

    private String blankIfNull(String s) {
        return s == null ? "" : s;
    }

    public String getString(char arg) {
        ArgumentMarshaler am = marshalerMap.get(arg);
        try{
        return am == null ? "" : (String) am.get();
        }catch (ClassCastException e){
            return "";
        }
    }

    public int getInt(char arg) {
        ArgumentMarshaler am = marshalerMap.get(arg);
        try {
        return am == null ? 0 : (Integer) am.get();
        }catch (ClassCastException e){
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

    public boolean isValid() {
        return valid;
    }

    private class ArgsException extends Exception {
    }
}
