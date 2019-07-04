package com.biwork.util;

import java.util.Locale;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 获取 bean工具类
 * Author: skyline{http://my.oschina.net/skyline520}
 * Created: 13-6-12 上午7:44
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext context = null;
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static <T> T getBean(String beanName){
        return (T) context.getBean(beanName);
    }

    public static String getMessage(String key){
        return context.getMessage(key, null, Locale.getDefault());
    }


}
