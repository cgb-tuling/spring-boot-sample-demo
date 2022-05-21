package org.example.annotation;

import java.lang.annotation.*;

/**
 * @author wurenhai
 * @since 2019/1/11 9:00
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TimeMeasure {

    /**
     * expression: target=#{#target}, a0=#{#a0}, result=#{#result}
     */
    String value() default "";

}