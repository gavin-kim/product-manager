package com.kwan.a4.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;


@Target({TYPE, METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Mapping {

    // mapping a path
    String value() default "";

    HttpMethod method() default HttpMethod.GET;
}
