package com.kai.annotation;

import java.lang.annotation.*;

/**
 * Mark the rpc field at client endpoint.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface RpcReference {

    /**
     * service version, default empty;
     */
    String version();

    /**
     * service group, default empty;
     */
    String group();
}
