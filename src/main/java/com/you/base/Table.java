package com.you.base;

import java.lang.annotation.*;

/**
 * @author shicz
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Table {
    String value() default "";
}
