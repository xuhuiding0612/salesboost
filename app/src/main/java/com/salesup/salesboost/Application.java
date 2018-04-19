package com.salesup.salesboost;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

@SpringBootApplication
public class Application {
  public static void main(String[] args) {
    ApplicationContext context = SpringApplication.run(Application.class, args);
    DispatcherServlet dispatcherServlet = (DispatcherServlet) context.getBean("dispatcherServlet");
    dispatcherServlet.setThrowExceptionIfNoHandlerFound(true);
  }
}
