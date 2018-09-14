package com.upay.batch.dispatch;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ReferenceConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import com.pactera.dipper.core.IDipperHandler;


/**
 * 用于缓存冲正的原子服务类
 * 
 * @author WUDUFENG
 * @since 2014年7月8日
 */
public class DipperHandlerContainer {

    private Map<String, ReferenceConfig<IDipperHandler<Object>>> dipperHandlerMap =
            new ConcurrentHashMap<String, ReferenceConfig<IDipperHandler<Object>>>();

    private ApplicationConfig applicationConfig;
    private List<RegistryConfig> registryConfigList;


    public DipperHandlerContainer(ApplicationConfig applicationConfig,
            List<RegistryConfig> registryConfigList) {
        this.applicationConfig = applicationConfig;
        this.registryConfigList = registryConfigList;
    }


    /**
     * 先从缓存获取，如果不存在，则从注册中心获取
     * 
     * @param key
     *            为IDipperHandler的GROUP名称
     * @param timeout
     *            远程调用超时时间(毫秒)
     * @return
     */
    public IDipperHandler<Object> getDipperHandler(String key, Integer timeout) {
        ReferenceConfig<IDipperHandler<Object>> reference = dipperHandlerMap.get(key);
        if (reference == null) {
            synchronized (dipperHandlerMap) {
                reference = dipperHandlerMap.get(key);
                if (reference == null) {
                    reference = new ReferenceConfig<IDipperHandler<Object>>(); // 此实例很重，封装了与注册中心的连接以及与提供者的连接，请自行缓存，否则可能造成内存和连接泄漏
                    reference.setApplication(applicationConfig);
                    reference.setRegistries(registryConfigList); // 多个注册中心可以用setRegistries()
                    reference.setInterface(IDipperHandler.class);
                    reference.setGroup(key);
                    reference.setTimeout(timeout);
                    reference.setRetries(0);
                    dipperHandlerMap.put(key, reference);
                }
            }
        }

        IDipperHandler<Object> stepExecutor = reference.get();
        if (stepExecutor == null) {
            reference.destroy();
            dipperHandlerMap.remove(key);
            return getDipperHandler(key, timeout);
        }

        return stepExecutor;
    }

}
