package com.weshare.thunder.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Properties;

/**
 * Created by lishaoyan on 2015/4/21.
 *
 * query the version and build information of webapp. It should be filled automatically by the build
 * script with information, such as build time, git revision, build host, etc.
 */
@RestController
public class AppInfoController {
    private static final Logger logger = LoggerFactory.getLogger(AppInfoController.class);

    @Resource(name="buildInfo")
    private Properties buildInfo;

    @RequestMapping("/appver")
    public HashMap<String,String> appVersion() {
        logger.debug("fetch build info");
        HashMap<String,String> appver = new HashMap<String, String>(10);
        for (Object key: buildInfo.keySet()) {
            appver.put(key.toString(), buildInfo.getProperty(key.toString()));
        }
        return appver;
    }
}
