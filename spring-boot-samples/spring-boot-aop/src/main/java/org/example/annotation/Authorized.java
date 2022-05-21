package org.example.annotation;

import java.lang.annotation.*;

/**
 * 安全认证
 * @author yct
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorized {

    String value() default "";

}