package com.jnsdevs.liteflow.service;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Autor Jairo Nascimento
 * @Created 21/10/2024 - 09:38
 */
@Component
public class AopProxyUtils implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    /**
     * Implements the setApplicationContext method of ApplicationContextAware interface,
     * used to inject ApplicationContext.
     */
    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        applicationContext = context;
    }

    /**
     * Gets the proxy object of the specified class, suitable for scenarios requiring transactions or other AOP enhancements.
     *
     * @param clazz Class of the object to be retrieved
     * @param <T>   Generic type indicator
     * @return Proxy object instance
     */
    public static <T> T getProxyBean(Class<T> clazz) {
        if (applicationContext == null) {
            throw new IllegalStateException("ApplicationContext not initialized.");
        }
        return applicationContext.getBean(clazz);
    }

    public static Object getProxyBean(String name) {
        return applicationContext.getBean(name);
    }
}
