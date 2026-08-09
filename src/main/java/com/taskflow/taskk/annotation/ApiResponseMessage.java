package com.taskflow.taskk.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ApiResponseMessage {

    String success() default "";

    String error() default "Something went wrong.";
}