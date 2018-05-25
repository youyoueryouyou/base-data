package com.you.base;

import java.lang.annotation.*;

/**
 * Created by shicz on 2018/5/21.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Column {
    String value() default "";
    boolean key() default false;
}
