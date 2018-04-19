package com.salesup.salesboost.exception;

import java.text.MessageFormat;
import org.springframework.http.HttpStatus;

public class ApplicationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final ExceptionType exceptionType;
  private final Object[] detailedMessageArguments;

  public ApplicationException(
      ExceptionType exceptionType, Object... detailedMessageArguments) {
    super(MessageFormat.format(exceptionType.getBriefMessage(), detailedMessageArguments));
    this.exceptionType = exceptionType;
    this.detailedMessageArguments = detailedMessageArguments;
  }

  public ApplicationException(
      ExceptionType exceptionType, final Throwable cause, Object... detailedMessageArguments) {
    super(MessageFormat.format(exceptionType.getBriefMessage(), detailedMessageArguments), cause);
    this.exceptionType = exceptionType;
    this.detailedMessageArguments = detailedMessageArguments;
  }

  public ExceptionType getExceptionType() {
    return exceptionType;
  }

  public HttpStatus getHttpStatus() {
    return exceptionType.getStatus();
  }

  public String getWholeMessage() {
    return MessageFormat.format(exceptionType.getBriefMessage(), detailedMessageArguments);
  }
}
