package com.weshare.thunder.dao;

import com.weshare.thunder.model.DepositProduct;
import com.weshare.thunder.model.DepositProductExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepositProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int countByExample(DepositProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int deleteByExample(DepositProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int insert(DepositProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int insertSelective(DepositProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    List<DepositProduct> selectByExample(DepositProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    DepositProduct selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") DepositProduct record, @Param("example") DepositProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") DepositProduct record, @Param("example") DepositProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(DepositProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table deposit_product
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(DepositProduct record);

    //Customer Definition
    DepositProduct selectByProductId(int productid);

}