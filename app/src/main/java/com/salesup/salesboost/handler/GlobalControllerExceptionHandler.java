package com.salesup.salesboost.handler;

import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
class GlobalControllerExceptionHandler {
  public static final String DEFAULT_ERROR_VIEW = "error";

  @ExceptionHandler(value = Exception.class)
  public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) throws Exception {
    //    // If the exception is annotated with @ResponseStatus rethrow it and let
    //    // the framework handle it - like the OrderNotFoundException example
    //    // at the start of this post.
    //    // AnnotationUtils is a Spring Framework utility class.
    //    if (AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class) != null) throw e;

    // Otherwise setup and send the user to a default error-view.
    ModelAndView mav = new ModelAndView();
    mav.addObject(
        "error", AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class).reason());
    mav.addObject(
        "status", AnnotationUtils.findAnnotation(e.getClass(), ResponseStatus.class).code());
    mav.addObject("message", e.getMessage());
    mav.addObject("timestamp", new Date());
    mav.setViewName(DEFAULT_ERROR_VIEW);
    return mav;
  }
}
