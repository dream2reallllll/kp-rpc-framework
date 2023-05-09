package com.kai.annotation;


import java.lang.annotation.*;

/**
 * mark the service implementation classes
 *
 * @author wangkaiping
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcService {

    String version() default "";

    String group() default "";
}
