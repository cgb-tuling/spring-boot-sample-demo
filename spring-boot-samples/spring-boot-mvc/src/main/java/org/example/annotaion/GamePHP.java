package org.example.annotaion;

import java.lang.annotation.*;

/**
 * @author MSI-NB
 */
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface GamePHP {
}