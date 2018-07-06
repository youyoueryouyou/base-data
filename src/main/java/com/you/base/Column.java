package com.you.base;

import java.lang.annotation.*;

/**
 * @author shicz
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Column {
    String value() default "";
    boolean key() default false;
}
