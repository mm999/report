package com.weshare.thunder.service.task;

import com.weshare.thunder.service.StatisticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jimeng on 2015/7/21.
 */
@Component
public class UserInfoTask {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoTask.class);

    @Autowired
    private StatisticService mStatisticService;
    @Scheduled(cron = "0 0 0 * * *")
    public void doUserInfoQuery() {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        logger.debug("doUserInfoQuery: triggered at: " + df.format(new Date()));

        mStatisticService.doUserInfoQuery();
    }
}
