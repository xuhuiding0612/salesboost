package com.salesup.salesboost.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
  REQUEST_BODY_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Illegal field(s) in request body: {0}");
  // you can specify your own exception types...

  private HttpStatus status;
  private String briefMessage;

  ExceptionType(HttpStatus status, String briefMessage) {
    this.status = status;
    this.briefMessage = briefMessage;
  }

  public HttpStatus getStatus() {
    return status;
  }

  public String getBriefMessage() {
    return briefMessage;
  }
}
