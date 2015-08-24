package com.weshare.thunder.service;

/**
 * Created by JiMeng on 2015/6/26.
 */
public interface StatisticService {
    public void getProductTransInfo(int type, int startTimestamp, int endTimestamp);
    public void getProductList();
    public void getTimeChartInfo(int type, int startTimestamp, int endTimestamp);
    public void getUserPurchase(int type, int startTimestamp, int endTimestamp, int productId);
    public void getMonthlyDepositAmount(int timestamp);
    public void getMonthlyTaskInfo(int startTimestamp, int endTimestamp);
    public void getUserStatistic(int startTimestamp, int endTimestamp);
    public void doUserInfoQuery();
}
