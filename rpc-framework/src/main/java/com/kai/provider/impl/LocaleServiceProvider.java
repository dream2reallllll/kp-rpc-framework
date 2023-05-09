package com.kai.provider.impl;


import com.kai.conifg.ServiceConfig;
import com.kai.provider.ServiceProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * The implementation of provider in local mode. The service
 * is published in the local memory.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@Slf4j
@Component
public class LocaleServiceProvider implements ServiceProvider {

    private final Map<String, Object> serviceMap;

    public LocaleServiceProvider() {
        serviceMap = new HashMap<>();
    }

    @Override
    public void addService(ServiceConfig serviceConfig) {
        String serviceName = serviceConfig.getRpcServiceName();
        if (!serviceMap.containsKey(serviceName)) {
            serviceMap.put(serviceName, serviceConfig.getService());
            log.info("注册新的服务:{}, 实现的接口为:{}", serviceName, serviceConfig.getService().getClass().getInterfaces());
        }
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if (null == service) {
            // TODO 抛出异常 RpcException
        }
        return service;
    }

    @Override
    public void publishService(ServiceConfig serviceConfig) {
        addService(serviceConfig);
    }
}
