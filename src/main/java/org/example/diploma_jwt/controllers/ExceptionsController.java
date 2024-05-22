package org.example.diploma_jwt.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import org.example.diploma_jwt.exceptions.AuthException;
import org.example.diploma_jwt.playload.response.ErrorMessageResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestControllerAdvice
public class ExceptionsController {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ErrorMessageResponse methodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return new ErrorMessageResponse(Objects.requireNonNull(ex.getFieldError()).getDefaultMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(AuthException.class)
    public ErrorMessageResponse AuthException(AuthException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ResponseBody
    @ExceptionHandler(EntityExistsException.class)
    public ErrorMessageResponse EntityDoesntExistsException(EntityExistsException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ErrorMessageResponse methodArgumentNotAllowedException(HttpRequestMethodNotSupportedException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.REQUEST_TIMEOUT)
    @ResponseBody
    @ExceptionHandler(ExpiredJwtException.class)
    public ErrorMessageResponse expiredJWTException(ExpiredJwtException ex) {
        return new ErrorMessageResponse(ex.getMessage());
    }

}
