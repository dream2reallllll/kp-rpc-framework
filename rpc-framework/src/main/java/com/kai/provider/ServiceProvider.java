package com.kai.provider;


import com.kai.conifg.ServiceConfig;

/**
 * The identification of service provider. The purpose is to find the service object.
 *
 * @author wangkaiping
 * @date 2023.4.26
 */
public interface ServiceProvider {

    /**
     * store the service in the provider
     * @param serviceConfig the config object of service
     */
    public void addService(ServiceConfig serviceConfig);

    /**
     * get service from provider by name
     * @param serviceName the name of target service
     * @return service object
     */
    public Object getService(String serviceName);

    /**
     * publish the service, so that client can find and invoke
     * @param serviceConfig the config object of service
     */
    public void publishService(ServiceConfig serviceConfig);
}
