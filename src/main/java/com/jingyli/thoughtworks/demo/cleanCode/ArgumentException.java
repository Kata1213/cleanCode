package com.jingyli.thoughtworks.demo.cleanCode;

public class ArgumentException extends Exception {
    private char errorArgumentId = '\0';
    private String errorPatameter = "TLLT";
    private ErrorCode errorCode = ErrorCode.OK;


    public ArgumentException() {
    }

    public ArgumentException(String message) {
        super(message);
    }

    public ArgumentException(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public ArgumentException(String errorPatameter, ErrorCode errorCode) {
        this.errorPatameter = errorPatameter;
        this.errorCode = errorCode;
    }

    public ArgumentException(char errorArgumentId, String errorPatameter, ErrorCode errorCode) {
        this.errorArgumentId = errorArgumentId;
        this.errorPatameter = errorPatameter;
        this.errorCode = errorCode;
    }

    public char getErrorArgumentId() {
        return errorArgumentId;
    }

    public void setErrorArgumentId(char errorArgumentId) {
        this.errorArgumentId = errorArgumentId;
    }

    public String getErrorPatameter() {
        return errorPatameter;
    }

    public void setErrorPatameter(String errorPatameter) {
        this.errorPatameter = errorPatameter;
    }

    public ErrorCode getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(ErrorCode errorCode) {
        this.errorCode = errorCode;
    }

    public String errorMessage() throws Exception {
        switch (errorCode) {
            case OK:
                throw new Exception("TILT: should not get here.");
            case UNEXPECTED_ARGUMENT:
                return String.format("Argument -%c unexpected.", errorArgumentId);
            case MISSING_STRING:
                return String.format("Could not find string parameter for -%c.", errorArgumentId);
            case INVALID_INTEGER:
                return String.format("Argument -%c excepcts an integer but was '%s'.", errorArgumentId, errorPatameter);
            case MISSING_INTEGER:
                return String.format("Could not find integer parameter for -%c.", errorArgumentId);
        }
        return "";
    }

}
