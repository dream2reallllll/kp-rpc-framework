package com.kai.conifg;

import lombok.*;

/**
 * The service configuration class.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
@AllArgsConstructor
@Getter
@ToString
public class ServiceConfig {

    private final String version;

    private final String group;

    private final Object service;

    public String getRpcServiceName() {
        return getServiceName() + "_" + group + "_" + version;
    }

    public String getServiceName() {
        // TODO 感觉这个Interface name可以从request里面获得
        return service.getClass().getInterfaces()[0].getCanonicalName();
    }
}
