package com.avogelm.indtxprices.application.core.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@ControllerAdvice
public class IndtxPricesControllerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(IndtxPricesControllerAdvice.class);

    private ResponseEntity<ErrorResponse> buildErrorResponse(HttpStatus status, String message) {
        return new ResponseEntity<>(
                new ErrorResponse(status, message),
                status
        );
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NoResourceFoundException e) {
        logger.error(e.getMessage());
        return buildErrorResponse(HttpStatus.NOT_FOUND, "The requested resource could not be found.");
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParamException(MissingServletRequestParameterException e) {
        logger.error(e.getMessage());
        String message = String.format("The parameter '%s' is missing.", e.getParameterName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleControllerTypeErrorException(MethodArgumentTypeMismatchException e) {
        logger.error(e.getMessage());
        String message = String.format("Parameter '%s' should be of type '%s'.",
                e.getName(), e.getRequiredType().getSimpleName());
        return buildErrorResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleHttpErrorException(Exception e) {
        logger.error(e.getMessage());

        ResponseStatusException exception = (ResponseStatusException) e;
        return buildErrorResponse(
                HttpStatus.valueOf(exception.getStatusCode().value()),
                exception.getReason()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleMiscErrorException(Exception e) {
        logger.error(e.getMessage());
        return buildErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "An unexpected error occurred"
        );
    }
}