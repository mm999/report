package com.weshare.thunder.dao;

import com.weshare.thunder.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RecordDepositMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int countByExample(RecordDepositExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int deleteByExample(RecordDepositExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int insert(RecordDeposit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int insertSelective(RecordDeposit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    List<RecordDeposit> selectByExample(RecordDepositExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    RecordDeposit selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") RecordDeposit record, @Param("example") RecordDepositExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") RecordDeposit record, @Param("example") RecordDepositExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(RecordDeposit record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table record_deposit
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(RecordDeposit record);

    //Customer Definition
    RecordDeposit selectByGid(String gid);
    //offset begin 0
    List<RecordDeposit> selectByExampleLimit(@Param("example") RecordDepositExample example,
                                             @Param("offset") int offet, @Param("limit") int limit);

    List<UserCount> selectCountUserByExample(RecordDepositExample example);

    ReFundTransDeduction selectFundTransDeductionByUpdateTime(@Param("startTime") Integer startTime,
                                                                    @Param("endTime") Integer endTime);

    List<ReFixTermTransDeduction> selectFixTermTransDeductionByUpdateTime(@Param("startTime") Integer startTime,
                                                                          @Param("endTime") Integer endTime);

    ReTransWithdraw selectTransWithdrawByUpdateTime(@Param("startTime") Integer startTime,
                                                          @Param("endTime") Integer endTime);

    ReBigUserFund selectBigUserFundByCreateTime(@Param("startTime") Integer startTime,
                                                      @Param("endTime") Integer endTime);

    List<ReBigUserFix> selectBigUserFixByCreateTime(@Param("startTime") Integer startTime,
                                                    @Param("endTime") Integer endTime);

    ReMigrate selectMigrateByCreateTime(@Param("startTime") Integer startTime,
                                              @Param("endTime") Integer endTime);

    List<ReFundToFix> selectFundToFixByCreateTime(@Param("startTime") Integer startTime,
                                                  @Param("endTime") Integer endTime);

    List<ReFixToFund> selectFixToFundByUpdateTime(@Param("startTime") Integer startTime,
                                                  @Param("endTime") Integer endTime);

    ReTransInitAmount selectTransInitAmount(@Param("startTime") Integer startTime,
                                            @Param("endTime") Integer endTime);

}