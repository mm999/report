package com.weshare.thunder.controller;

import com.weshare.thunder.except.StatisticException;
import com.weshare.thunder.except.SuccessException;
import com.weshare.thunder.model.LightningResponse;
import com.weshare.thunder.service.Constants;
import com.weshare.thunder.service.StatisticService;
import com.weshare.thunder.service.WechatService;
import com.weshare.thunder.utils.Utility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class ReportController {
    private static final Logger logger = LoggerFactory.getLogger(ReportController.class);

    @Autowired
    private StatisticService mStatisticService;
    @Autowired
    private WechatService weChatService;

    @Autowired
    private ReloadableResourceBundleMessageSource messageSource;

    private static class ProductTransInfoReq {
        public int type;
        public int startTimestamp;
        public int endTimestamp;
        public String userId;

        @Override
        public String toString() {
            return "ProductTransInfoReq{" +
                    "type='" + type + '\'' +
                    "startTimestamp='" + startTimestamp + '\'' +
                    "endTimestamp='" + endTimestamp + '\'' +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }

    private static class UserPurchaseInfoReq {
        public int type;
        public int startTimestamp;
        public int endTimestamp;
        public int productId;
        public String userId;
        @Override
        public String toString() {
            return "UserGidUpReq{" +
                    "type='" + type + '\'' +
                    "startTimestamp='" + startTimestamp + '\'' +
                    "endTimestamp='" + endTimestamp + '\'' +
                    "productId='" + productId + '\'' +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }

    @RequestMapping(value="/statistic/product_transinfo", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public LightningResponse getProductTransInfo(@RequestBody ProductTransInfoReq req) {
        logger.info("statistic:getProductTransInfo(), type ={}, startTimestamp ={}, endTimestamp ={}", req.type, req.startTimestamp, req.endTimestamp);
        LightningResponse response = new LightningResponse();

        try {
            boolean flag = weChatService.checkUserIsScope(req.userId);
            if(flag){
                mStatisticService.getProductTransInfo(req.type, req.startTimestamp, req.endTimestamp);
            }else{
                response.setStatus(Constants.ResponseStatus.FAILURE);
            }
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/statistic/product_list", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public LightningResponse getProductList() {
        logger.info("statistic:getProductList()");

        LightningResponse response = new LightningResponse();

        try {
            mStatisticService.getProductList();
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        }
        return response;
    }

    private static class TimeChartInfoReq {
        public int type;
        public int startTimestamp;
        public int endTimestamp;
        public String userId;
        @Override
        public String toString() {
            return "TimeChartInfoReq{" +
                    "type='" + type + '\'' +
                    "startTimestamp='" + startTimestamp + '\'' +
                    "endTimestamp='" + endTimestamp + '\'' +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }

    @RequestMapping(value="/statistic/time_chart", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public LightningResponse getTimeChartInfo(@RequestBody TimeChartInfoReq req) {
        logger.info("statistic:getTimeChartInfo(), type ={}, startTimestamp ={}, endTimestamp ={}", req.type, req.startTimestamp, req.endTimestamp);
        LightningResponse response = new LightningResponse();

        try {
            boolean flag = weChatService.checkUserIsScope(req.userId);
            if(flag){
                mStatisticService.getTimeChartInfo(req.type, req.startTimestamp, req.endTimestamp);
            }else{
                response.setStatus(Constants.ResponseStatus.FAILURE);
            }
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static class MonthlyDepositAmountReq {
        public int timestamp;

        @Override
        public String toString() {
            return "MonthlyDepositAmountReq{" +
                    "timestamp='" + timestamp + '\'' +
                    '}';
        }
    }

    @RequestMapping(value="/statistic/monthly_deposit_amount", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
//    @ResponseBody
    public LightningResponse getMonthlyDepositAmount(@RequestBody MonthlyDepositAmountReq req) {
        logger.info("statistic:getMonthlyDepositAmount(), timestamp ={}", req.timestamp);

        LightningResponse response = new LightningResponse();

        try {
            mStatisticService.getMonthlyDepositAmount(req.timestamp);
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        }
        return response;
    }

    @RequestMapping(value="/statistic/user_purchase", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public  LightningResponse getUserPurchase(@RequestBody UserPurchaseInfoReq req) {
        logger.info("statistic:getUserPurchase()");
        LightningResponse response = new LightningResponse();

        Integer startTimestamp = Utility.getDayStartTime(req.startTimestamp);
        Integer endTimesamp = Utility.getDayEndTime(req.endTimestamp);

        try {
            boolean flag = weChatService.checkUserIsScope(req.userId);
            if(flag){
                mStatisticService.getUserPurchase(req.type, startTimestamp, endTimesamp, req.productId);
            }else{
                response.setStatus(Constants.ResponseStatus.FAILURE);
            }
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static class MonthlyTaskReq {
        public int startTimestamp;
        public int endTimestamp;
        public String userId;

        @Override
        public String toString() {
            return "MonthlyTaskReq{" +
                    "startTimestamp='" + startTimestamp + '\'' +
                    "endTimestamp='" + endTimestamp + '\'' +
                    "userId='" + userId + '\'' +
                    '}';
        }
    }

    @RequestMapping(value="/statistic/monthly_task", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public LightningResponse getMonthlyTaskInfo(@RequestBody MonthlyTaskReq req) {
        logger.info("statistic:getMonthlyTaskInfo()");
        LightningResponse response = new LightningResponse();

        try {
            boolean flag = weChatService.checkUserIsScope(req.userId);
            if(flag){
                mStatisticService.getMonthlyTaskInfo(req.startTimestamp, req.endTimestamp);
            }else{
                response.setStatus(Constants.ResponseStatus.FAILURE);
            }
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private static class UserStatisticReq {
        public int startTimestamp;
        public int endTimestamp;
        public int type;
        public String userId;

        @Override
        public String toString() {
            return "UserstatisticReq{" +
                    "type='" + type + '\'' +
                    " startTimestamp='" + startTimestamp + '\'' +
                    " endTimestamp='" + endTimestamp + '\'' +
                    " userId='" + userId + '\'' +
                    '}';
        }
    }

    private static class UserInfo{
        public String code;

        public String toString() {
            return "UserInfo{" +
                    "code='" + code + '\'' +
                    '}';
        }
    }

    @RequestMapping(value="/statistic/user_statistic", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public LightningResponse getUserStatisticInfo(@RequestBody UserStatisticReq req) {
        logger.info("statistic:getUserStatisticInfo()");
        LightningResponse response = new LightningResponse();

        try {
            boolean flag = weChatService.checkUserIsScope(req.userId);
            if(flag){
                mStatisticService.getUserStatistic(req.startTimestamp, req.endTimestamp);
            }else{
                response.setStatus(Constants.ResponseStatus.FAILURE);
            }
        } catch (StatisticException ex) {
            response = Utility.getErrorResponse(ex.getErrorCode(), messageSource);
        } catch (SuccessException ex) {
            response = new LightningResponse();
            response.setStatus(Constants.ResponseStatus.SUCCESS);
            response.setContent(ex.getContent());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/statistic/getUserInfo", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public LightningResponse getUserInfo(@RequestBody UserInfo req) {
        logger.info("statistic:getUserInfo()");
        LightningResponse response = new LightningResponse();
        String userId = "";
        try {
            String  accessToken = weChatService.getValidAccessToken();
            response = weChatService.getUserInfo(accessToken,req.code);
            //获取访问者信息
            if(response != null){
                Map map = (Map) response.getContent();
                if (map.containsKey("UserId")) {
                    userId = (String) map.get("UserId");
                    response.setContent(userId);
                }
            }
        } catch (Exception ex) {
            response = new LightningResponse();
            ex.printStackTrace();
        }

        return response;
    }


    @RequestMapping(value="/statistic/test_task", method= RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public void testTask() {
        mStatisticService.doUserInfoQuery();
    }
}
