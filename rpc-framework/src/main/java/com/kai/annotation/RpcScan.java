package com.kai.annotation;


import com.kai.spring.CustomScannerRegistry;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * scan custom annotations
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Import(CustomScannerRegistry.class)
@Documented
public @interface RpcScan {

    String[] basePackage();

}
