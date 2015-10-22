package com.linkallcloud.core.exception;

public class UnknowException extends BaseException {
    private static final long serialVersionUID = 1517915921538760778L;

    public UnknowException() {
        super("e.unkonw", "Unknow Exception.");
    }

    public UnknowException(String code) {
        super(code);
    }

    public UnknowException(String code, String message) {
        super(code, message);
    }

    public UnknowException(String code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public UnknowException(String code, Throwable cause) {
        super(code, cause);
    }

    public UnknowException(Throwable cause) {
        super("e.unkonw", "Unknow Exception.", cause);
    }

}
