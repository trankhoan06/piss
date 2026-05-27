package com.app.pis.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ReflectionMapping {
    String fieldName();
    boolean isEntity () default false;
    Class<?> entityClass() default Void.class;
    Class<?> repositoryClass() default Void.class;
}
