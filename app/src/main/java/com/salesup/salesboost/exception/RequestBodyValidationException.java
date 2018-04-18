package com.salesup.salesboost.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Request Body Validation Error")
public class RequestBodyValidationException extends RuntimeException {
  public RequestBodyValidationException(String message) {
    super(message);
  }

  public RequestBodyValidationException(String message, Throwable throwable) {
    super(message, throwable);
  }
}
