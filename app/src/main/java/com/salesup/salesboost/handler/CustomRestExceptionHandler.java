package com.salesup.salesboost.handler;

import com.salesup.salesboost.domain.ApiError;
import com.salesup.salesboost.exception.ApplicationException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {
  @Override
  protected ResponseEntity<Object> handleMethodArgumentNotValid(
      MethodArgumentNotValidException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    List<String> errors = new ArrayList<String>();
    for (FieldError error : ex.getBindingResult().getFieldErrors()) {
      errors.add(error.getField() + ": " + error.getDefaultMessage());
    }
    for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
      errors.add(error.getObjectName() + ": " + error.getDefaultMessage());
    }

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors);
    return handleExceptionInternal(ex, apiError, headers, apiError.getStatus(), request);
  }

  @Override
  protected ResponseEntity<Object> handleMissingServletRequestParameter(
      MissingServletRequestParameterException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    String error = ex.getParameterName() + " parameter is missing";

    ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleNoHandlerFoundException(
      NoHandlerFoundException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
    String error = "No handler found for " + ex.getHttpMethod() + " " + ex.getRequestURL();

    ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(), error);
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(
      HttpRequestMethodNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getMethod());
    builder.append(" method is not supported for this request. Supported methods are ");
    ex.getSupportedHttpMethods().forEach(t -> builder.append(t + " "));

    ApiError apiError =
        new ApiError(HttpStatus.METHOD_NOT_ALLOWED, ex.getLocalizedMessage(), builder.toString());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @Override
  protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(
      HttpMediaTypeNotSupportedException ex,
      HttpHeaders headers,
      HttpStatus status,
      WebRequest request) {
    StringBuilder builder = new StringBuilder();
    builder.append(ex.getContentType());
    builder.append(" media type is not supported. Supported media types are ");
    ex.getSupportedMediaTypes().forEach(t -> builder.append(t + ", "));

    ApiError apiError =
        new ApiError(
            HttpStatus.UNSUPPORTED_MEDIA_TYPE,
            ex.getLocalizedMessage(),
            builder.substring(0, builder.length() - 2));
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<Object> handleAll(Exception ex, WebRequest request) {
    ApiError apiError =
        new ApiError(
            HttpStatus.INTERNAL_SERVER_ERROR,
            ex.getLocalizedMessage(),
            ex.getClass().getSimpleName());
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }

  @ExceptionHandler({ApplicationException.class})
  public ResponseEntity<Object> handleApplicationException(
      ApplicationException ex, WebRequest request) {
    HttpStatus status = ex.getExceptionType().getStatus();
    String error = ex.getExceptionType().name();
    String message = ex.getWholeMessage();
    ApiError apiError = new ApiError(status, message, error);
    return new ResponseEntity<Object>(apiError, new HttpHeaders(), apiError.getStatus());
  }
}
