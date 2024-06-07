package org.example.diploma_jwt.controllers;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.persistence.EntityExistsException;
import lombok.RequiredArgsConstructor;
import org.example.diploma_jwt.exceptions.AuthException;
import org.example.diploma_jwt.exceptions.EmptyDataException;
import org.example.diploma_jwt.models.usable.LogType;
import org.example.diploma_jwt.playload.response.ErrorMessageResponse;
import org.example.diploma_jwt.services.AuthService;
import org.example.diploma_jwt.services.model.UserLogService;
import org.springframework.http.HttpStatus;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RequiredArgsConstructor
@RestControllerAdvice
public class ExceptionsController {
    private final UserLogService userLogService;
    private final AuthService authService;

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

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(EmptyDataException.class)
    public ErrorMessageResponse emptyDataException(EmptyDataException ex) {
        userLogService.createAndSaveLog(authService.getCurrentUser(), "Got ERROR: " + ex.getMessage(), LogType.ERROR);

        return new ErrorMessageResponse(ex.getMessage());
    }

}
