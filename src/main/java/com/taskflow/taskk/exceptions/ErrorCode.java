package com.taskflow.taskk.exceptions;


import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    // ─── Generic / HTTP ───────────────────────────────────────────────────────
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request payload"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "An unexpected error occurred"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "HTTP method not allowed"),
    UNREADABLE_REQUEST(HttpStatus.BAD_REQUEST, "Malformed JSON or invalid request payload"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request" ),

    // ─── Authentication / Authorization ───────────────────────────────────────
    INVALID_ACCESS(HttpStatus.UNAUTHORIZED, "Unauthorized access"),
    INVALID_USER_HEADER(HttpStatus.BAD_REQUEST, "Missing or invalid user identification header"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Authentication token has expired"),
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "You do not have permission to perform this action"),

    // ─── Validation ───────────────────────────────────────────────────────────
    VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Request validation failed"),
    CONSTRAINT_VIOLATION(HttpStatus.BAD_REQUEST, "Data constraint violation"),
    BINDING_ERROR(HttpStatus.BAD_REQUEST, "Request parameter binding error"),
    MISSING_REQUEST_HEADER(HttpStatus.BAD_REQUEST, "Required request header is missing"),
    INVALID_ARGUMENT(HttpStatus.BAD_REQUEST, "Invalid argument provided"),
    INVALID_STATE(HttpStatus.BAD_REQUEST, "Invalid system state"),
    INVALID_STAGE_TRANSITION(HttpStatus.BAD_REQUEST, "Invalid appointment stage transition"),

    // ─── Resource ─────────────────────────────────────────────────────────────
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "User not found"),
    RESOURCE_NOT_FOUND(HttpStatus.NOT_FOUND, "Requested resource not found"),
    RESOURCE_NOT_CREATED(HttpStatus.INTERNAL_SERVER_ERROR, "Failed to create resource"),
    DUPLICATE_RESOURCE(HttpStatus.CONFLICT, "Resource already exists");

    private final HttpStatus httpStatus;
    private final String defaultMessage;

    ErrorCode(HttpStatus httpStatus, String defaultMessage) {
        this.httpStatus = httpStatus;
        this.defaultMessage = defaultMessage;
    }
}
