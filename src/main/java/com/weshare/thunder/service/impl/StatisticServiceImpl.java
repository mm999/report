package com.weshare.thunder.service.impl;

import com.weshare.thunder.dao.*;
import com.weshare.thunder.except.StatisticException;
import com.weshare.thunder.except.SuccessException;
import com.weshare.thunder.model.*;
import com.weshare.thunder.service.Constants;
import com.weshare.thunder.service.StatisticService;
import com.weshare.thunder.daow.UserInfoMapper;
import com.weshare.thunder.model.UserInfo;
import com.weshare.thunder.utils.ErrorCode;
import com.weshare.thunder.utils.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by JiMeng on 2015/6/26.
 */
@Service
public class StatisticServiceImpl implements StatisticService {

    private static int SECONDS_OF_ONE_DAY = 24 * 60 * 60;

    @Autowired
    private DepositProductMapper mDepositProductMapper;

    @Autowired
    private RecordDepositMapper mRecordDepositMapper;

    @Autowired
    private TransWithdrawMapper mTransWithdrawMapper;

    @Autowired
    private UserChannelWechatMapper mUserChannelWechatMapper;

    @Autowired
    private UserLightningMapper mUserLightningMapper;

    @Autowired
    private UserInfoMapper mUserInfoMapper;

    @Autowired
    private UserInfoQueryMapper mUserInfoQueryMapper;

    public  interface ProductTransInfoCellType {
        public static byte DAY = 0;
        public static byte WEEK = 1;
        public static byte MONTH = 2;
    }

    private static int getDayStartTime(int timePoint) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long) timePoint) * 1000));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        long ret = ca.getTime().getTime()/1000;
        return (int) ret;
    }

    private static int getDayEndTime(int timePoint){
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long) timePoint) * 1000));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        long ret = ca.getTime().getTime()/1000;
        return (int) ret;
    }

    private static int getWeekStartTime(int timePoint) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long)timePoint)*1000));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        long ret = ca.getTime().getTime()/1000;
        return (int) ret;
    }

    private static int getWeekEndTime(int timePoint){
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long)timePoint)*1000));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        ca.add(Calendar.DATE, 7);
        long ret = ca.getTime().getTime()/1000;
        return (int) ret;
    }

    private static int getMonthStartTime(int timePoint) {
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long)timePoint)*1000));
        ca.set(Calendar.HOUR_OF_DAY, 0);
        ca.set(Calendar.MINUTE, 0);
        ca.set(Calendar.SECOND, 0);
        ca.set(Calendar.DAY_OF_MONTH, 1);
        long ret = ca.getTime().getTime()/1000;
        return (int)ret;
    }

    private static int getMonthEndTime(int timePoint){
        Calendar ca = new GregorianCalendar();
        ca.setTime(new Date(((long)timePoint)*1000));
        ca.set(Calendar.HOUR_OF_DAY, 23);
        ca.set(Calendar.MINUTE, 59);
        ca.set(Calendar.SECOND, 59);
        ca.set(Calendar.DATE, 1);
        ca.roll(Calendar.DATE, -1);
        long ret = ca.getTime().getTime()/1000;
        return (int)ret;
    }

    private int getMonthStepByCurrentTime(int type, int currentTime) {
        return getMonthEndTime(currentTime) - getMonthStartTime(currentTime) + 1;
    }

    private int getAlignedStartTime(int type, int startTime) {
        if (ProductTransInfoCellType.DAY == type) {
            return getDayStartTime(startTime);
        } else if (ProductTransInfoCellType.WEEK == type) {
            return getWeekStartTime(startTime);
        } else {
            return getMonthStartTime(startTime);
        }
    }

    private int getAlignedEndTime(int type, int endTime) {
        if (ProductTransInfoCellType.DAY == type) {
            return getDayEndTime(endTime);
        } else if (ProductTransInfoCellType.WEEK == type) {
            return getWeekEndTime(endTime);
        } else {
            return getMonthEndTime(endTime);
        }
    }

    private int getTimeStep(int type, int currentTime) {
        if (ProductTransInfoCellType.DAY == type) {
            return SECONDS_OF_ONE_DAY;
        } else if (ProductTransInfoCellType.WEEK == type) {
            return SECONDS_OF_ONE_DAY * 7;
        } else {
            return getMonthStepByCurrentTime(type, currentTime);
        }
    }

    private class ProductInfo {
        public Integer id;
        public String name;
        public BigDecimal depositAmount;
    }

    private class TransInfoCell {
        public int cellType;
        public int startTime;
        public int endTime;
        public BigDecimal incremental;
        public BigDecimal depositTotal;
        public Map<String, Object> productInfo;
        public BigDecimal totalInitAmount;
    }

    @Override
    public void getProductTransInfo(int type, int startTime, int endTime) {
        ArrayList<TransInfoCell> transInfoCellList = new ArrayList<>();

        int startTimeAligned = getAlignedStartTime(type, startTime);
        int endTimeAligned = getAlignedEndTime(type, endTime);
        List<DepositProduct> productList = mDepositProductMapper.selectByExample(null);

        BigDecimal total = getHistoryTotalDepositAmount(startTimeAligned, productList);
        for (int time = startTimeAligned; time < endTimeAligned; ) {
            int cellTimeStep = getTimeStep(type, time);

            TransInfoCell transInfoCell = new TransInfoCell();
            transInfoCell.cellType = type;
            transInfoCell.startTime = time;
            transInfoCell.endTime = time + cellTimeStep - 1;

            Map<String, Object> info = getRecordMap(transInfoCell.startTime, transInfoCell.endTime, productList);
            transInfoCell.incremental = (BigDecimal) info.get("amount");
            transInfoCell.productInfo = info;
            total = total.add(transInfoCell.incremental);
            transInfoCell.depositTotal = total;
            transInfoCellList.add(transInfoCell);
            transInfoCell.totalInitAmount=(BigDecimal)info.get("amountInit");

            time += cellTimeStep;
        }

        TransInfoCell transInfoCell = new TransInfoCell();
        transInfoCell.cellType = type;
        transInfoCell.startTime = getAlignedStartTime(type, Utility.getCurrentTimeStamp());
        transInfoCell.endTime = getAlignedEndTime(type, Utility.getCurrentTimeStamp());

        if (!hasExist(transInfoCell, transInfoCellList)) {
            Map<String, Object> map = getRecordMap(transInfoCell.startTime, transInfoCell.endTime, productList);
            transInfoCell.incremental = (BigDecimal) map.get("amount");
            transInfoCell.depositTotal = getHistoryTotalDepositAmount(transInfoCell.startTime, productList)
                    .add(transInfoCell.incremental);
            transInfoCell.productInfo = map;
            transInfoCell.totalInitAmount=(BigDecimal)map.get("amountInit");

            transInfoCellList.add(transInfoCell);
        }

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("transInfoCellList", transInfoCellList);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private Map<Integer, Integer> getProductCountMap(List<DepositProduct> productList) {
        Map<Integer, Integer> map = new HashMap<>();
        for (DepositProduct dp : productList) {
            map.put(dp.getProductId(), 0);
        }
        return map;
    }

    private BigDecimal getFixToFundAmount(Integer productId, int startTime, int endTime) {
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria()
                .andProductIdEqualTo(productId)
                .andDepositTypeEqualTo(Constants.DepositType.FIRST_TIME)
                .andDepositStateEqualTo(Constants.DepositState.DEPOSIT_DONE)
                .andCreateTimeBetween(startTime, endTime);
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RecordDeposit rd : recordDepositList) {
            totalAmount = totalAmount.add(rd.getAmountInitial().setScale(2, BigDecimal.ROUND_DOWN));
            totalAmount = totalAmount.add(rd.getRealEarning().setScale(2, BigDecimal.ROUND_DOWN));
        }
        return totalAmount;
    }

    private BigDecimal getFTFAmountByProductIdAndStartEndTime(Integer productId, int startTime, int endTime) {
        List<Byte> depositStates = new ArrayList<>();
        depositStates.add(Constants.DepositState.DEPOSITING);
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria().andProductIdEqualTo(productId)
                        .andDepositTypeEqualTo(Constants.DepositType.FIRST_TIME)
                        .andDepositSrcEqualTo(Constants.DepositSource.FUND)
                        .andDepositStateIn(depositStates)
                        .andCreateTimeBetween(startTime, endTime);
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RecordDeposit rd : recordDepositList) {
            totalAmount = totalAmount.add(rd.getAmountInitial().setScale(2, BigDecimal.ROUND_DOWN));
        }
        return totalAmount;
    }

    private boolean hasExist(TransInfoCell transInfoCell, List<TransInfoCell> transInfoCellList) {
        for (TransInfoCell tic : transInfoCellList) {
            if (tic.cellType == transInfoCell.cellType &&
                    tic.startTime == transInfoCell.startTime &&
                    tic.endTime == transInfoCell.endTime) {
                return true;
            }
        }
        return false;
    }

    private BigDecimal getTotalDepositAmountByProductIdAndStartEndTime(int productId, int startTime, int endTime) {
        List<Byte> depositStates = new ArrayList<>();
        depositStates.add(Constants.DepositState.DEPOSITING);
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria().andProductIdEqualTo(productId)
                .andDepositStateIn(depositStates)
                .andCreateTimeBetween(startTime, endTime);
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RecordDeposit rd : recordDepositList) {
            totalAmount = totalAmount.add(rd.getAmountInitial().setScale(2, BigDecimal.ROUND_DOWN));
        }
        return totalAmount;
    }

    private Map<String, Object> getRecordMap(int startTime, int endTime, List<DepositProduct> productList) {
        Map<String, Object> map = new HashMap<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        ReFundTransDeduction fundTransDeduction = mRecordDepositMapper.selectFundTransDeductionByUpdateTime(startTime, endTime);
        List<ReFixTermTransDeduction> fixTermTransDeductions = mRecordDepositMapper.selectFixTermTransDeductionByUpdateTime(startTime, endTime);
        ReBigUserFund bigUserFund = mRecordDepositMapper.selectBigUserFundByCreateTime(startTime, endTime);
        List<ReBigUserFix> bigUserFixes = mRecordDepositMapper.selectBigUserFixByCreateTime(startTime, endTime);
        ReMigrate migrate = mRecordDepositMapper.selectMigrateByCreateTime(startTime, endTime);
        List<ReFundToFix> fundToFixes = mRecordDepositMapper.selectFundToFixByCreateTime(startTime, endTime);
        List<ReFixToFund> fixToFunds = mRecordDepositMapper.selectFixToFundByUpdateTime(startTime, endTime);
        ReTransWithdraw transWithdraw = mRecordDepositMapper.selectTransWithdrawByUpdateTime(startTime, endTime);
        ReTransInitAmount transInitAmount = mRecordDepositMapper.selectTransInitAmount(startTime, endTime);

        Map<Integer, String> productMap = new HashMap<>();
        for (DepositProduct dp : productList) {
            productMap.put(dp.getProductId(), dp.getProductName());
        }

        List<ProductInfo> inFixTermFromFund = new ArrayList<>();
        for (ReFundToFix re : fundToFixes) {
            ProductInfo pi = new ProductInfo();
            pi.id = re.getProductId();
            pi.name = productMap.get(re.getProductId());
            pi.depositAmount = re.getAmount();
            inFixTermFromFund.add(pi);
        }
        List<ProductInfo> inFixTermFromBank = new ArrayList<>();
        for (ReFixTermTransDeduction re : fixTermTransDeductions) {
            ProductInfo pi = new ProductInfo();
            pi.id = re.getProductId();
            pi.name = productMap.get(re.getProductId());
            pi.depositAmount = re.getAmount();
            totalAmount = totalAmount.add(pi.depositAmount);
            inFixTermFromBank.add(pi);
        }
        List<ProductInfo> inFixTermFromBigUser = new ArrayList<>();
        for (ReBigUserFix re : bigUserFixes) {
            ProductInfo pi = new ProductInfo();
            pi.id = re.getProductId();
            pi.name = productMap.get(re.getProductId());
            pi.depositAmount = re.getAmount();
            totalAmount = totalAmount.add(pi.depositAmount);
            inFixTermFromBigUser.add(pi);
        }
        List<ProductInfo> inFundFromFixTerm = new ArrayList<>();
        for (ReFixToFund re : fixToFunds) {
            ProductInfo pi = new ProductInfo();
            pi.id = re.getProductId();
            pi.name = productMap.get(re.getProductId());
            pi.depositAmount = re.getAmount();
            inFundFromFixTerm.add(pi);
        }

        totalAmount = totalAmount.add(fundTransDeduction.getAmount());
        totalAmount = totalAmount.add(bigUserFund.getAmount());
        totalAmount = totalAmount.add(migrate.getAmount());
        totalAmount = totalAmount.subtract(transWithdraw.getAmount());

        map.put("inFixTermFromFundList", inFixTermFromFund);
        map.put("inFundFromFixTermList", inFundFromFixTerm);
        map.put("inFixTermFromBankList", inFixTermFromBank);
        map.put("inFixTermFromBigUserList", inFixTermFromBigUser);
        map.put("inFundTransDeductionAmount", fundTransDeduction.getAmount());
        map.put("inBigUserFundAmount", bigUserFund.getAmount());
        map.put("inMigrateAmount", migrate.getAmount());
        map.put("outTransWithdrawAmount", transWithdraw.getAmount());
        map.put("outFixTermToFundList", inFundFromFixTerm);
        map.put("outFundToFixTermList", inFixTermFromFund);
        map.put("amount", totalAmount);
        map.put("amountInit",transInitAmount.getAmount());
        return map;
    }

    private BigDecimal getHistoryTotalDepositAmount(int endTime, List<DepositProduct> productList) {
        Map<String, Object> map = getRecordMap(0, endTime, productList);
        return (BigDecimal) map.get("amount");
    }

    private BigDecimal getHistoryTotalDepositAmountOld(int endTime) {
        List<Byte> depositStates = new ArrayList<>();
        depositStates.add(Constants.DepositState.DEPOSITING);
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria()
                .andDepositSrcEqualTo((byte) 0)
                .andUpdateTimeLessThan(endTime)
                .andDepositStateIn(depositStates);
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);

        RecordDepositExample exampleDel = new RecordDepositExample();
        exampleDel.createCriteria()
                .andDepositSrcEqualTo((byte) 1)
                .andDepositTypeEqualTo((byte) 1)
                .andUpdateTimeLessThan(endTime)
                .andDepositStateIn(depositStates);
        List<RecordDeposit> recordDepositListDel = mRecordDepositMapper.selectByExample(exampleDel);

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RecordDeposit rd : recordDepositList) {
            totalAmount = totalAmount.add(rd.getAmountInitial());
        }
        for (RecordDeposit rd : recordDepositListDel) {
            totalAmount = totalAmount.add(rd.getAmountInitial());
        }
        BigDecimal withDrawTotal = getHistroyTtoalWithdrawAmountByEndTime(endTime);
        return totalAmount.subtract(withDrawTotal).setScale(2, BigDecimal.ROUND_DOWN);
    }

    private BigDecimal getTotalWithdrawAmountByStartEndTime(int startTime, int endTime) {
        List<Byte> transWithdrawStates = new ArrayList<>();
        transWithdrawStates.add(Constants.TransStatus.SUCCESS);
        TransWithdrawExample example = new TransWithdrawExample();
        example.createCriteria().andWithdrawStateIn(transWithdrawStates)
                .andUpdateTimeBetween(startTime, endTime);
        List<TransWithdraw> transWithdrawList = mTransWithdrawMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TransWithdraw tw : transWithdrawList) {
            totalAmount = totalAmount.add(tw.getWithdrawAmount());
        }
        return totalAmount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    private BigDecimal getHistroyTtoalWithdrawAmountByEndTime(int endTime) {
        List<Byte> transWithdrawStates = new ArrayList<>();
        transWithdrawStates.add(Constants.TransStatus.SUCCESS);
        TransWithdrawExample example = new TransWithdrawExample();
        example.createCriteria()
                .andWithdrawStateIn(transWithdrawStates)
                .andUpdateTimeLessThan(endTime);
        List<TransWithdraw> transWithdrawsList = mTransWithdrawMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (TransWithdraw tw : transWithdrawsList) {
            totalAmount = totalAmount.add(tw.getWithdrawAmount());
        }
        return totalAmount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    @Override
    public void getProductList() {
        List<DepositProduct> depositProductList = mDepositProductMapper.selectByExample(null);
        ArrayList<ProductInfo> productInfoList = new ArrayList<>();
        for (DepositProduct dp : depositProductList) {
            ProductInfo pi = new ProductInfo();
            pi.id = dp.getProductId();
            pi.name = dp.getProductName();
            productInfoList.add(pi);
        }

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("productList", productInfoList);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private class TimeChartInfoSubCell {
        public int startTime;
        public int endTime;
        public BigDecimal depositAmount;
    }

    private class TimeChartInfoCell {
        public int startTime;
        public int endTime;
        public BigDecimal maxDepositAmount;
        public BigDecimal minDepositAmount;
        public BigDecimal averageDepositAmount;
        public BigDecimal nowDepositAmount;
        public List<TimeChartInfoSubCell> timeChartInfoSubCellList = new ArrayList<>();
    }

    @Override
    public void getTimeChartInfo(int type, int startTimestamp, int endTimestamp) {
        int startTimePoint = Utility.getDayStartTime(Utility.getCurrentTimeStamp());
        int endTimePoint = Utility.getDayEndTime(Utility.getCurrentTimeStamp());

        List<TimeChartInfoCell> timeChartInfoCellList = new ArrayList<>();
        for (int time = startTimePoint; time < endTimePoint; time += Constants.SECONDS_OF_ONE_HOUR) {
            TimeChartInfoCell timeChartInfoCell = new TimeChartInfoCell();
            timeChartInfoCell.startTime = time;
            timeChartInfoCell.endTime = time + Constants.SECONDS_OF_ONE_HOUR - 1;
            timeChartInfoCell.timeChartInfoSubCellList = makeTimeChartInfoSubCellList(type, startTimestamp, endTimestamp, timeChartInfoCell);
            timeChartInfoCell.nowDepositAmount = getTotalDepositAmountByStartEndTime(timeChartInfoCell.startTime, timeChartInfoCell.endTime);
            timeChartInfoCell.maxDepositAmount = getMaxDepositAmount(timeChartInfoCell.timeChartInfoSubCellList);
            timeChartInfoCell.minDepositAmount = getMinDepositAmount(timeChartInfoCell.timeChartInfoSubCellList);
            timeChartInfoCell.averageDepositAmount = getAverageDepositAmount(timeChartInfoCell.timeChartInfoSubCellList);
            timeChartInfoCellList.add(timeChartInfoCell);
        }

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("timeChartInfoCellList", timeChartInfoCellList);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private BigDecimal getMaxDepositAmount(List<TimeChartInfoSubCell> timeChartInfoSubCellList) {
        if (timeChartInfoSubCellList.isEmpty()) {
            return null;
        }
        BigDecimal maxDepositAmount = timeChartInfoSubCellList.get(0).depositAmount;
        for (TimeChartInfoSubCell timeChartInfoSubCell : timeChartInfoSubCellList) {
            if (timeChartInfoSubCell.depositAmount.compareTo(maxDepositAmount) > 0) {
                maxDepositAmount = timeChartInfoSubCell.depositAmount;
            }
        }
        return maxDepositAmount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    private BigDecimal getMinDepositAmount(List<TimeChartInfoSubCell> timeChartInfoSubCellList) {
        if (timeChartInfoSubCellList.isEmpty()) {
            return null;
        }
        BigDecimal minDepositAmount = timeChartInfoSubCellList.get(0).depositAmount;
        for (TimeChartInfoSubCell timeChartInfoSubCell : timeChartInfoSubCellList) {
            if (timeChartInfoSubCell.depositAmount.compareTo(minDepositAmount) < 0) {
                minDepositAmount = timeChartInfoSubCell.depositAmount;
            }
        }
        return minDepositAmount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    private BigDecimal getAverageDepositAmount(List<TimeChartInfoSubCell> timeChartInfoSubCellList) {
        if (timeChartInfoSubCellList.isEmpty()) {
            return null;
        }
        BigDecimal totalDepositAmount = BigDecimal.ZERO;
        for (TimeChartInfoSubCell timeChartInfoSubCell : timeChartInfoSubCellList) {
            totalDepositAmount = totalDepositAmount.add(timeChartInfoSubCell.depositAmount);
        }
        return totalDepositAmount.divide(new BigDecimal(timeChartInfoSubCellList.size()), 0).setScale(2, BigDecimal.ROUND_DOWN);
    }

    public  interface  TimeChartInfoCellType {
        public static byte DAY = 0;
        public static byte ALL = 1;
        public static byte CUSTOMIZE = 2;
    }

    private BigDecimal getTotalDepositAmountByStartEndTime(int startTime, int endTime) {
        List<Byte> depositStates = new ArrayList<>();
        depositStates.add(Constants.DepositState.DEPOSITING);
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria().andDepositStateIn(depositStates)
                .andCreateTimeBetween(startTime, endTime);
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        BigDecimal totalAmount = BigDecimal.ZERO;
        for (RecordDeposit rd : recordDepositList) {
            totalAmount = totalAmount.add(rd.getAmountInitial());
        }
        return totalAmount.setScale(2, BigDecimal.ROUND_DOWN);
    }

    private  int getFirstDepositRecordTime() {
        List<Byte> depositStates = new ArrayList<>();
        depositStates.add(Constants.DepositState.DEPOSITING);
        RecordDepositExample example = new RecordDepositExample();
        example.createCriteria().andDepositStateIn(depositStates);
        example.setOrderByClause("update_time asc");
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        if (recordDepositList.isEmpty()) {
            return 0;
        }
        return recordDepositList.get(0).getUpdateTime();
    }

    private List<TimeChartInfoSubCell> makeTimeChartInfoSubCellListByDayStartEndTime(int dayStartTime, int dayEndTime, TimeChartInfoCell timeChartInfoCell) {
        List<TimeChartInfoSubCell> timeChartInfoSubCellList = new ArrayList<>();
        for (int time=dayStartTime ; time<dayEndTime ; time += Constants.SECONDS_OF_ONE_DAY) {
            TimeChartInfoSubCell timeChartInfoSubCell = new TimeChartInfoSubCell();
            timeChartInfoSubCell.startTime = time + (timeChartInfoCell.startTime - Utility.getDayStartTime(Utility.getCurrentTimeStamp()));
            timeChartInfoSubCell.endTime = time + (timeChartInfoCell.endTime - Utility.getDayStartTime(Utility.getCurrentTimeStamp()));
            timeChartInfoSubCell.depositAmount = getTotalDepositAmountByStartEndTime(timeChartInfoSubCell.startTime, timeChartInfoSubCell.endTime);
            timeChartInfoSubCellList.add(timeChartInfoSubCell);
        }
        return timeChartInfoSubCellList;
    }

    private List<TimeChartInfoSubCell> makeTimeChartInfoSubCellList(int type, int startTimestamp, int endTimestamp, TimeChartInfoCell timeChartInfoCell) {
        if (TimeChartInfoCellType.DAY == type) {
            int dayStartTime = Utility.getDayStartTime(Utility.getCurrentTimeStamp()) - Constants.SECONDS_OF_ONE_DAY * (7 - 1);
            int dayEndTime = Utility.getDayEndTime(Utility.getCurrentTimeStamp());
            return makeTimeChartInfoSubCellListByDayStartEndTime(dayStartTime, dayEndTime, timeChartInfoCell);
        } else if (TimeChartInfoCellType.ALL == type) {
            int dayStartTime = Utility.getDayStartTime(getFirstDepositRecordTime());
            int dayEndTime = Utility.getDayEndTime(Utility.getCurrentTimeStamp());
            return makeTimeChartInfoSubCellListByDayStartEndTime(dayStartTime, dayEndTime, timeChartInfoCell);
        } else if (TimeChartInfoCellType.CUSTOMIZE == type) {
            int dayStartTime = Utility.getDayStartTime(startTimestamp);
            int dayEndTime = Utility.getDayEndTime(endTimestamp);
            return makeTimeChartInfoSubCellListByDayStartEndTime(dayStartTime, dayEndTime, timeChartInfoCell);
        }
        return null;
    }

    @Override
    public void getMonthlyDepositAmount(int timestamp) {
        int monthStartTime = getMonthStartTime(timestamp);
        int monthEndTime = getMonthEndTime(timestamp);

        BigDecimal monthlyDepositAmount = getTotalDepositAmountByStartEndTime(monthStartTime, monthEndTime);

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("monthlyDepositAmount", monthlyDepositAmount.setScale(2, BigDecimal.ROUND_DOWN));

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    @Override
    public void getUserPurchase(int type, int startTimestamp, int endTimestamp, int productId) {
        switch (type) {
            case Constants.UserPurchaseType.TRADE_AVERAGE:
                getUserPurchaseByTradeAverage(startTimestamp, endTimestamp, productId);
                break;
            case Constants.UserPurchaseType.TRADE_TOTAL:
                getUserPurchaseByTradeTotal(startTimestamp, endTimestamp, productId);
                break;
            case Constants.UserPurchaseType.TRADE_PERCENT:
                getUserPurchaseByTradePercent(startTimestamp, endTimestamp, productId);
                break;
            default:
                throw new StatisticException(ErrorCode.SYS_PARAMETER_ERROR);
        }
    }

    @Override
    public void getMonthlyTaskInfo(int startTimestamp, int endTimestamp) {
        BigDecimal attentions = getNewAttentions(startTimestamp, endTimestamp);
        BigDecimal tradeUsers = getNewTradeUsers(startTimestamp, endTimestamp);
        BigDecimal monthlyDepositAmount = getTotalDepositAmountByStartEndTime(startTimestamp, endTimestamp);

        BigDecimal estimateAttentions = getEstimateAttentions(startTimestamp);
        BigDecimal estimateTradeUsers = getEstimateTradeUsers(startTimestamp);
        BigDecimal estimateTotalTrade = getEstimateTotalTrade(startTimestamp);

        Map<String, Object> content = new HashMap<>();
        content.put("attentions", attentions);
        content.put("tradeUsers", tradeUsers);
        content.put("totalTrade", monthlyDepositAmount.setScale(2, BigDecimal.ROUND_DOWN));
        content.put("estimateAttentions", estimateAttentions);
        content.put("estimateTradeUsers", estimateTradeUsers);
        content.put("estimateTotalTrade", estimateTotalTrade.setScale(2, BigDecimal.ROUND_DOWN));
        
        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    @Override
    public void doUserInfoQuery() {
        int currentTimestamp = getLastDayTimestamp();
        int start = getDayStartTime(currentTimestamp);
        int end = getDayEndTime(currentTimestamp);
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andDateBetween(start, end);
        List<UserInfo> userInfoList = mUserInfoMapper.selectByExample(example);
        if (!userInfoList.isEmpty()) {
            return;
        }
        UserInfoQuery userInfoQuery = mUserInfoQueryMapper.selectByCurrent();
        UserInfo ui = chageToUserInfo(userInfoQuery);
        ui.setDate(getLastDayTimestamp());
        mUserInfoMapper.insert(ui);
    }

    private UserInfo chageToUserInfo(UserInfoQuery userInfoQuery) {
        UserInfo ui = new UserInfo();
        ui.setCertified(userInfoQuery.getCertified());
        ui.setBindMobile(userInfoQuery.getBindMobile());
        ui.setBindCard(userInfoQuery.getBindCard());
        ui.setActivited(userInfoQuery.getActivited());
        ui.setDeposited(userInfoQuery.getDeposited());
        ui.setNotActivited(userInfoQuery.getNotActivited());
        ui.setNotFollowTotal(userInfoQuery.getNotFollowTotal());
        ui.setTotal(userInfoQuery.getTotal());
        ui.setTotalFinancePeoples(userInfoQuery.getTotalFinancePeoples());
        return ui;
    }

    private Integer getLastDayTimestamp() {
        int current = Utility.getCurrentTimeStamp();
        return current - 60*60*24;
    }

    @Override
    public void getUserStatistic(int startTimestamp, int endTimestamp) {
        int start = getDayStartTime(startTimestamp);
        int end = getDayEndTime(endTimestamp);

        List<UserInfo> list = getUserInfoListByStartEndTime(start, end);
        UserInfo historyDay = getUserHisteryDay(start);

//        List<UserInfo> currentList = getUserInfoCurrentList();

        Map<String, Object> content = new HashMap<String, Object>();
        content.put("infoList", list);
        content.put("historyTotal", historyDay);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private List<UserInfo> getUserInfoCurrentList() {
        UserInfoQuery userInfoQuery = mUserInfoQueryMapper.selectByCurrent();
        UserInfo ui = chageToUserInfo(userInfoQuery);
        ui.setDate(getLastDayTimestamp());
        List<UserInfo> list = new ArrayList<UserInfo>();
        list.add(ui);
        return list;
    }

    private UserInfo getUserHisteryDay(int start) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andDateBetween(start - 24*60*60, start);
        List<UserInfo> infoList = mUserInfoMapper.selectByExample(example);
        return infoList.get(0);
    }

    private UserInfo getUserInfoHistoryTotal(int endTimestamp) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andDateLessThan(endTimestamp);
        List<UserInfo> userInfoList = mUserInfoMapper.selectByExample(example);

        UserInfo userInfo = new UserInfo();
        userInfo.setTotal(0);
        userInfo.setNotActivited(0);
        userInfo.setNotFollowTotal(0);
        userInfo.setDeposited(0);
        userInfo.setActivited(0);
        userInfo.setBindCard(0);
        userInfo.setBindMobile(0);
        userInfo.setCertified(0);

        for (UserInfo ui : userInfoList) {
            userInfo.setDeposited(userInfo.getDeposited() + ui.getDeposited());
            userInfo.setBindCard(userInfo.getBindCard() + ui.getBindCard());
            userInfo.setCertified(userInfo.getCertified() + ui.getCertified());
            userInfo.setBindMobile(userInfo.getBindMobile() + ui.getBindMobile());
            userInfo.setActivited(userInfo.getActivited() + ui.getActivited());
            userInfo.setNotActivited(userInfo.getNotActivited() + ui.getNotActivited());
            userInfo.setTotal(userInfo.getTotal() + ui.getTotal());
            userInfo.setNotFollowTotal(userInfo.getNotFollowTotal() + ui.getNotFollowTotal());
        }
        return userInfo;
    }

    private List<UserInfo> getUserInfoListByStartEndTime(int start, int end) {
        UserInfoExample example = new UserInfoExample();
        example.createCriteria().andDateBetween(start, end);
        List<UserInfo> userInfoList = mUserInfoMapper.selectByExample(example);
        UserInfoQuery uiq = mUserInfoQueryMapper.selectByCurrent();
        UserInfo ui = chageToUserInfo(uiq);
        ui.setDate(Utility.getCurrentTimeStamp());
        userInfoList.add(ui);
        return userInfoList;
    }

    private BigDecimal getNewTradeUsers(int startTimestamp, int endTimestamp) {
        UserLightningExample example = new UserLightningExample();
        example.createCriteria()
                .andFirstTryTimeBetween(startTimestamp, endTimestamp);
        List<UserLightning> list = mUserLightningMapper.selectByExample(example);
        if (list == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(list.size());
    }

    private BigDecimal getNewAttentions(int startTimestamp, int endTimestamp) {
        UserChannelWechatExample example = new UserChannelWechatExample();
        example.createCriteria()
                .andUpdateTimeBetween(startTimestamp, endTimestamp)
                .andIsFollowedEqualTo(true);
        List<UserChannelWechat> list = mUserChannelWechatMapper.selectByExample(example);
        if (list == null) {
            return BigDecimal.ZERO;
        }
        return new BigDecimal(list.size());
    }

    private BigDecimal getEstimateTotalTrade(int startTimestamp) {
        return new BigDecimal(2000000);
    }

    private BigDecimal getEstimateTradeUsers(int startTimestamp) {
        return new BigDecimal(1000);
    }

    private BigDecimal getEstimateAttentions(int startTimestamp) {
        return new BigDecimal(100000);
    }

    private void getUserPurchaseByTradePercent(int startTimestamp, int endTimestamp, int productId) {
        RecordDepositExample example = new RecordDepositExample();
        if (productId == 0) { // no productId
            example.createCriteria()
                    .andDepositStateEqualTo((byte)1)
                    .andCreateTimeBetween(startTimestamp, endTimestamp);
        } else {
            example.createCriteria()
                    .andDepositStateEqualTo((byte) 1)
                    .andCreateTimeBetween(startTimestamp, endTimestamp)
                    .andProductIdEqualTo(productId);
        }
        List<UserCount> userCount = mRecordDepositMapper.selectCountUserByExample(example);
        List<UserCount> onceCount = getCount(userCount, Constants.UserCountType.ONCE);
        List<UserCount> twiceCount = getCount(userCount, Constants.UserCountType.TWICE);
        List<UserCount> threeCount = getCount(userCount, Constants.UserCountType.THREE);
        List<UserCount> fourCount = getCount(userCount, Constants.UserCountType.FOUR);
        List<UserCount> fiveAndMore = getCount(userCount, Constants.UserCountType.FIVE_AND_MORE);

        Map<String, Object> content = new HashMap<>();
        content.put("once", onceCount.size());
        content.put("twice", twiceCount.size());
        content.put("three", threeCount.size());
        content.put("four", fourCount.size());
        content.put("five", fiveAndMore.size());

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private void getUserPurchaseByTradeTotal(int startTimestamp, int endTimestamp, int productId) {
        RecordDepositExample example = new RecordDepositExample();
        if (productId == 0) { // no productId
            example.createCriteria()
                    .andDepositStateEqualTo((byte)1)
                    .andCreateTimeBetween(startTimestamp, endTimestamp);
        } else {
            example.createCriteria()
                    .andCreateTimeBetween(startTimestamp, endTimestamp)
                    .andDepositStateEqualTo((byte) 1)
                    .andProductIdEqualTo(productId);
        }
        List<UserCount> userCount = mRecordDepositMapper.selectCountUserByExample(example);
        List<UserCount> onceCount = getCount(userCount, Constants.UserCountType.ONCE);
        List<UserCount> twiceCount = getCount(userCount, Constants.UserCountType.TWICE);
        List<UserCount> threeCount = getCount(userCount, Constants.UserCountType.THREE);
        List<UserCount> fourCount = getCount(userCount, Constants.UserCountType.FOUR);
        List<UserCount> fiveAndMore = getCount(userCount, Constants.UserCountType.FIVE_AND_MORE);

        List<RecordDeposit> onceRecordDeposit = getRecordDepositByCountList(onceCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> twiceRecordDeposit = getRecordDepositByCountList(twiceCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> threeRecordDeposit = getRecordDepositByCountList(threeCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> fourRecordDeposit = getRecordDepositByCountList(fourCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> fiveRecordDeposit = getRecordDepositByCountList(fiveAndMore, productId, startTimestamp, endTimestamp);

        Integer onceTotal = getAmountInitialTotal(onceRecordDeposit);
        Integer twiceTotal = getAmountInitialTotal(twiceRecordDeposit);
        Integer threeTotal = getAmountInitialTotal(threeRecordDeposit);
        Integer fourTotal = getAmountInitialTotal(fourRecordDeposit);
        Integer fiveTotal = getAmountInitialTotal(fiveRecordDeposit);

        Map<String, Object> content = new HashMap<>();
        content.put("once", onceTotal);
        content.put("twice", twiceTotal);
        content.put("three", threeTotal);
        content.put("four", fourTotal);
        content.put("five", fiveTotal);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private Integer getAmountInitialTotal(List<RecordDeposit> recordDepositList) {
        Integer total = 0;

        if (recordDepositList.size() == 0) {
            return 0;
        }

        for (int i = 0; i < recordDepositList.size(); i++) {
            total += recordDepositList.get(i).getAmountInitial().toBigInteger().intValue();
        }

        return total;
    }

    private void getUserPurchaseByTradeAverage(int startTimestamp, int endTimestamp, int productId) {
        RecordDepositExample example = new RecordDepositExample();
        if (productId == 0) { // no productId
            example.createCriteria()
                    .andDepositStateEqualTo((byte)1)
                    .andCreateTimeBetween(startTimestamp, endTimestamp);
        } else {
            example.createCriteria()
                    .andCreateTimeBetween(startTimestamp, endTimestamp)
                    .andDepositStateEqualTo((byte)1)
                    .andProductIdEqualTo(productId);
        }
        List<UserCount> userCount = mRecordDepositMapper.selectCountUserByExample(example);
        if (userCount == null) {
            userCount = new ArrayList<>();
        }
        List<UserCount> onceCount = getCount(userCount, Constants.UserCountType.ONCE);
        List<UserCount> twiceCount = getCount(userCount, Constants.UserCountType.TWICE);
        List<UserCount> threeCount = getCount(userCount, Constants.UserCountType.THREE);
        List<UserCount> fourCount = getCount(userCount, Constants.UserCountType.FOUR);
        List<UserCount> fiveAndMore = getCount(userCount, Constants.UserCountType.FIVE_AND_MORE);

        List<RecordDeposit> onceRecordDeposit = getRecordDepositByCountList(onceCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> twiceRecordDeposit = getRecordDepositByCountList(twiceCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> threeRecordDeposit = getRecordDepositByCountList(threeCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> fourRecordDeposit = getRecordDepositByCountList(fourCount, productId, startTimestamp, endTimestamp);
        List<RecordDeposit> fiveRecordDeposit = getRecordDepositByCountList(fiveAndMore, productId, startTimestamp, endTimestamp);

        Integer onceAverage = getAmountInitialAverage(onceRecordDeposit);
        Integer twiceAverage = getAmountInitialAverage(twiceRecordDeposit);
        Integer threeAverage = getAmountInitialAverage(threeRecordDeposit);
        Integer fourAverage = getAmountInitialAverage(fourRecordDeposit);
        Integer fiveAverage = getAmountInitialAverage(fiveRecordDeposit);

        Map<String, Object> content = new HashMap<>();
        content.put("once", onceAverage);
        content.put("twice", twiceAverage);
        content.put("three", threeAverage);
        content.put("four", fourAverage);
        content.put("five", fiveAverage);

        SuccessException se = new SuccessException(ErrorCode.SYS_SUCCESS);
        se.setContent(content);
        throw se;
    }

    private Integer getAmountInitialAverage(List<RecordDeposit> onceRecordDepositList) {
        Integer total = 0;

        if (onceRecordDepositList.size() == 0) {
            return 0;
        }

        for (int i = 0; i < onceRecordDepositList.size(); i++) {
            total += onceRecordDepositList.get(i).getAmountInitial().toBigInteger().intValue();
        }

        return total/onceRecordDepositList.size();
    }

    private List<RecordDeposit> getRecordDepositByCountList(List<UserCount> countList, Integer productId,
                                                            Integer startTimestamp, Integer endTimestamp) {
        List<String> userGids = getGidsFromCountList(countList);
        if (userGids == null || userGids.size() == 0) {
            return new ArrayList<>();
        }
        RecordDepositExample example = new RecordDepositExample();
        if (productId == 0) {
            example.createCriteria()
                    .andCreateTimeBetween(startTimestamp, endTimestamp)
                    .andDepositStateEqualTo((byte)1)
                    .andUserGidIn(userGids);
        } else {
            example.createCriteria()
                    .andCreateTimeBetween(startTimestamp, endTimestamp)
                    .andDepositStateEqualTo((byte)1)
                    .andUserGidIn(userGids)
                    .andProductIdEqualTo(productId);
        }
        List<RecordDeposit> recordDepositList = mRecordDepositMapper.selectByExample(example);
        if (recordDepositList == null) {
            return new ArrayList<>();
        } else {
            return recordDepositList;
        }
    }

    private List<String> getGidsFromCountList(List<UserCount> countList) {
        List<String> list = new ArrayList<>();
        for (int i = 0; i < countList.size(); i++) {
            list.add(countList.get(i).getUserGid());
        }
        return list;
    }

    private List<UserCount> getCount(List<UserCount> userCount, byte countType) {
        List<UserCount> list = new ArrayList<UserCount>();
        switch (countType) {
            case Constants.UserCountType.ONCE:
                for (UserCount uc : userCount) {
                    if (uc.getCount() == 1) {
                        list.add(uc);
                    }
                }
                break;
            case Constants.UserCountType.TWICE:
                for (UserCount uc : userCount) {
                    if (uc.getCount() == 2) {
                        list.add(uc);
                    }
                }
                break;
            case Constants.UserCountType.THREE:
                for (UserCount uc : userCount) {
                    if (uc.getCount() == 3) {
                        list.add(uc);
                    }
                }
                break;
            case Constants.UserCountType.FOUR:
                for (UserCount uc : userCount) {
                    if (uc.getCount() == 4) {
                        list.add(uc);
                    }
                }
                break;
            case Constants.UserCountType.FIVE_AND_MORE:
                for (UserCount uc : userCount) {
                    if (uc.getCount() >= 5) {
                        list.add(uc);
                    }
                }
                break;
            default:
                break;
        }
        return list;
    }
}
