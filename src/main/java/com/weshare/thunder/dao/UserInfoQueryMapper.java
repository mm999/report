package com.weshare.thunder.dao;

import com.weshare.thunder.model.UserInfoQuery;
import com.weshare.thunder.model.UserInfoQueryExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserInfoQueryMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int countByExample(UserInfoQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int deleteByExample(UserInfoQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int insert(UserInfoQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int insertSelective(UserInfoQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    List<UserInfoQuery> selectByExample(UserInfoQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    UserInfoQuery selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserInfoQuery record, @Param("example") UserInfoQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserInfoQuery record, @Param("example") UserInfoQueryExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserInfoQuery record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_info
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserInfoQuery record);
    UserInfoQuery selectByCurrent();
}