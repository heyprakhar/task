package com.taskflow.taskk.exceptions;

import com.taskflow.taskk.common.response.BaseApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseApiResponse<Void> handleBusinessException(BusinessException ex) {

        HttpStatus status = ex.getStatus() != null
                ? ex.getStatus()
                : HttpStatus.INTERNAL_SERVER_ERROR;

        return BaseApiResponse.<Void>builder()
                .status(status.value())
                .message(ex.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public BaseApiResponse<Void> handleValidationException(MethodArgumentNotValidException ex) {

        String reason = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(field -> field.getField() + ": " + field.getDefaultMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(
                ErrorCode.VALIDATION_ERROR,
                reason
        );
    }

    @ExceptionHandler(BindException.class)
    public BaseApiResponse<Void> handleBindException(BindException ex) {

        String reason = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        return buildResponse(
                ErrorCode.BINDING_ERROR,
                reason
        );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public BaseApiResponse<Void> handleConstraintViolation(ConstraintViolationException ex) {

        String reason = ex.getConstraintViolations()
                .stream()
                .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                .collect(Collectors.joining(", "));

        return buildResponse(
                ErrorCode.CONSTRAINT_VIOLATION,
                reason
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {

        String message = "Database constraint violation.";

        if (ex.getMostSpecificCause().getMessage().contains("Duplicate entry")) {
            message = "Resource already exists.";
        }

        BaseApiResponse<Void> response = BaseApiResponse.<Void>builder()
                .status(HttpStatus.CONFLICT.value())
                .message(message)
                .data(null)
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public BaseApiResponse<Void> handleUnreadableRequest(HttpMessageNotReadableException ex) {

        return buildResponse(
                ErrorCode.UNREADABLE_REQUEST,
                ex.getMostSpecificCause().getMessage()
        );
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<BaseApiResponse<Void>> handleMissingHeader(MissingRequestHeaderException ex) {

        ErrorCode errorCode = ErrorCode.MISSING_REQUEST_HEADER;

        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        BaseApiResponse.<Void>builder()
                                .status(errorCode.getHttpStatus().value())
                                .message(errorCode.getDefaultMessage())
                                .data(null)
                                .build()
                );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public BaseApiResponse<Void> handleMissingParameter(MissingServletRequestParameterException ex) {

        return buildResponse(
                ErrorCode.BAD_REQUEST,
                "Required request parameter '" + ex.getParameterName() + "' is missing."
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public BaseApiResponse<Void> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {

        return buildResponse(
                ErrorCode.INVALID_ARGUMENT,
                "Invalid value '" + ex.getValue() + "' for parameter '" + ex.getName() + "'."
        );
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public BaseApiResponse<Void> handleMethodNotAllowed(HttpRequestMethodNotSupportedException ex) {

        return buildResponse(
                ErrorCode.METHOD_NOT_ALLOWED,
                ex.getMessage()
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public BaseApiResponse<Void> handleNoResourceFound(NoResourceFoundException ex) {

        return buildResponse(ErrorCode.RESOURCE_NOT_FOUND, "No resource found for " + ex.getResourcePath());
    }

    @ExceptionHandler(Exception.class)
    public BaseApiResponse<Void> handleException(Exception ex) {

        ex.printStackTrace();

        return buildResponse(
                ErrorCode.INTERNAL_SERVER_ERROR,
                ex.getMessage()
        );
    }

    private BaseApiResponse<Void> buildResponse(ErrorCode errorCode, String reason) {

        return BaseApiResponse.<Void>builder()
                .status(errorCode.getHttpStatus().value())
                .message(errorCode.getDefaultMessage())
                .data(null)
                .build();
    }
}