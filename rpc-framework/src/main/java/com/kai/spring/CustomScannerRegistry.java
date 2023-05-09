package com.kai.spring;

import com.kai.annotation.RpcScan;
import com.kai.annotation.RpcService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.StandardAnnotationMetadata;
import org.springframework.stereotype.Component;


/**
 * Register the Rpc service classes in the project.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@Slf4j
public class CustomScannerRegistry implements ImportBeanDefinitionRegistrar, ResourceLoaderAware {

    private static final String SPRING_BEAN_BASE_PACKAGE = "github.javaguide";

    private static final String BASE_PACKAGE_ATTRIBUTE_NAME = "basePackage";
    private ResourceLoader resourceLoader;

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 得到RpcScan的所有属性和属性值
        AnnotationAttributes annotationAttributes = AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(RpcScan.class.getName()));
        String[] rpcScanBasePackages = new String[0];
        if (annotationAttributes != null) {
            rpcScanBasePackages = annotationAttributes.getStringArray(BASE_PACKAGE_ATTRIBUTE_NAME);
        }
        if (rpcScanBasePackages.length == 0) {
            rpcScanBasePackages = new String[]{((StandardAnnotationMetadata) importingClassMetadata).getIntrospectedClass().getPackage().getName()};
        }

        CustomScanner rpcServiceScanner = new CustomScanner(registry, RpcService.class);
        CustomScanner componentScanner = new CustomScanner(registry, Component.class);
        if (resourceLoader != null) {
            rpcServiceScanner.setResourceLoader(resourceLoader);
            componentScanner.setResourceLoader(resourceLoader);
        }
        int rpcServiceCount = rpcServiceScanner.scan(rpcScanBasePackages);
        log.info("扫描到RPC Service类的数目为[{}]", rpcServiceCount);
        int componentCount = rpcServiceScanner.scan(SPRING_BEAN_BASE_PACKAGE);
        log.info("扫描到Spring Component类的数目为[{}]", componentCount);
    }
}
