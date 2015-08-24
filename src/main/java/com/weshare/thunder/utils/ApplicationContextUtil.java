package com.weshare.thunder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by lishaoyan on 2015/6/9.
 *
 * the class is used to obtain the spring application context. Especially, the non-component class use it
 * to access the bean in spring container
 */
@Component
public class ApplicationContextUtil implements ApplicationContextAware {
    private final static Logger logger = LoggerFactory.getLogger(ApplicationContextUtil.class);

    private static ApplicationContextUtil instance;

    private ApplicationContext applicationContext;

    private static synchronized ApplicationContextUtil getInstance(){
        if(instance == null){
            instance = new ApplicationContextUtil();
        }
        return instance;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        if(getInstance().applicationContext == null){
            getInstance().applicationContext = applicationContext;
        }
        logger.debug("init the spring application context");
    }

    /**
     * obtain the spring application context
     * @return the spring application context
     */
    public static ApplicationContext getApplicationContext(){
        return getInstance().applicationContext;
    }
}

