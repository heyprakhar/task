package com.taskflow.taskk.exceptions;

public class EmailTemplateException extends RuntimeException {

    public EmailTemplateException(String message) {
        super(message);
    }

    public EmailTemplateException(String message, Throwable cause) {
        super(message, cause);
    }
}