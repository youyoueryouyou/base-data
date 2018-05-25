package com.you.base;

import java.lang.annotation.*;

/**
 * Created by shicz on 2018/5/21.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface Table {
    String value() default "";
}
