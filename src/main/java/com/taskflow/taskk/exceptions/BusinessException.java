package com.taskflow.taskk.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

import java.io.Serial;

@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2457189159617736175L;

    private HttpStatus status;
    private String message;
    private ErrorCode code;
    private String reason;

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    public BusinessException(ErrorCode code) {
        super(code.getDefaultMessage());
        this.code = code;
        this.message = code.getDefaultMessage();
        this.status = code.getHttpStatus();
    }

    public BusinessException(ErrorCode code, String reason) {
        super(reason);
        this.code = code;
        this.reason = reason;
        this.message = reason;
        this.status = code.getHttpStatus();
    }

    public BusinessException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public BusinessException(HttpStatus status, String message, String reason) {
        super(message);
        this.status = status;
        this.message = message;
        this.reason = reason;
    }

    public BusinessException(HttpStatus status, String message, ErrorCode code, String reason) {
        super(message);
        this.status = status;
        this.message = message;
        this.code = code;
        this.reason = reason;
    }

    public BusinessException(ErrorCode code, String message, String reason) {
        super(message);
        this.code = code;
        this.message = message;
        this.reason = reason;
        this.status = code.getHttpStatus();
    }
}
