package com.salesup.salesboost.exception;

import org.springframework.http.HttpStatus;

public enum ExceptionType {
  REQUEST_BODY_VALIDATION_ERROR(HttpStatus.BAD_REQUEST, "Illegal field(s) in request body: {0}");
  // Specify more customized exception types...

  private HttpStatus status;
  /**
   * Brief Message: {@link String} Defined in ExceptionType enum. This message is a preview
   * statement of what kind or type of error it can be.
   */
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
