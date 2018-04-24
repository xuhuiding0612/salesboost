package com.salesup.salesboost.config.domain;

import com.salesup.salesboost.domain.BasicDomain;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** Customized annotation for getting salt as class name from domain classes. */
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface JsonSerializationSalt {
  public Class<? extends BasicDomain> saltClass();
}
