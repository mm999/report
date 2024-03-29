package com.weshare.thunder.dao;

import com.weshare.thunder.model.TransWithdraw;
import com.weshare.thunder.model.TransWithdrawExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TransWithdrawMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int countByExample(TransWithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int deleteByExample(TransWithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int insert(TransWithdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int insertSelective(TransWithdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    List<TransWithdraw> selectByExample(TransWithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    TransWithdraw selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") TransWithdraw record, @Param("example") TransWithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") TransWithdraw record, @Param("example") TransWithdrawExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(TransWithdraw record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table trans_withdraw
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(TransWithdraw record);

    //Customer Functions
    //offset begin 0
    List<TransWithdraw> selectByExampleLimit(@Param("example") TransWithdrawExample example,
                                             @Param("offset") int offet, @Param("limit")int limit);
}