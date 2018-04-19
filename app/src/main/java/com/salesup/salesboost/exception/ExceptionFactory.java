package com.salesup.salesboost.exception;

import java.text.MessageFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExceptionFactory {

  private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionFactory.class);

  public static ApplicationException create(
      final Throwable cause,
      final ExceptionType exceptionType,
      final Object... detailedMessageArguments) {
    LOGGER.error(
        MessageFormat.format(exceptionType.getBriefMessage(), detailedMessageArguments), cause);
    return new ApplicationException(exceptionType, cause, detailedMessageArguments);
  }

  public static ApplicationException create(
      final ExceptionType exceptionType, final Object... detailedMessageArguments) {
    LOGGER.error(MessageFormat.format(exceptionType.getBriefMessage(), detailedMessageArguments));
    // TODO: Store exception information in DB for tracking errors.
    return new ApplicationException(exceptionType, detailedMessageArguments);
  }
}
