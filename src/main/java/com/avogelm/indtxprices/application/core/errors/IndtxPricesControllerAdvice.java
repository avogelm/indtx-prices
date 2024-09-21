package com.avogelm.indtxprices.application.core.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
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

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(Exception e) {

        logger.error(e.getMessage());

        HttpStatus status = HttpStatus.NOT_FOUND;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                HttpStatusCode.valueOf(status.value())
        );
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorResponse> handleMissingParamException(Exception e) {

        logger.error(e.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                HttpStatusCode.valueOf(status.value())
        );
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleControllerTypeErrorException(Exception e) {

        logger.error(e.getMessage());

        HttpStatus status = HttpStatus.BAD_REQUEST;

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        e.getMessage()
                ),
                HttpStatusCode.valueOf(status.value())
        );
    }

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<ErrorResponse> handleHttpErrorException(Exception e) {

        logger.error(e.getMessage());

        ResponseStatusException exception = (ResponseStatusException) e;
        HttpStatus status = HttpStatus.valueOf(exception.getStatusCode().value());

        return new ResponseEntity<>(
                new ErrorResponse(
                        status,
                        exception.getReason()
                ),
                exception.getStatusCode()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleMiscErrorException(Exception e) {

        logger.error(e.getMessage());

        // For miscelaneous errors, we don't want to provide any details on the
        // response message. TODO: All information will be internally logged.
        return new ResponseEntity<>(
                new ErrorResponse(),
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}
