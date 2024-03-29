package com.weshare.thunder.dao;

import com.weshare.thunder.model.UserLightning;
import com.weshare.thunder.model.UserLightningExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserLightningMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int countByExample(UserLightningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int deleteByExample(UserLightningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int insert(UserLightning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int insertSelective(UserLightning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    List<UserLightning> selectByExample(UserLightningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    UserLightning selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserLightning record, @Param("example") UserLightningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserLightning record, @Param("example") UserLightningExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserLightning record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_lightning
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserLightning record);

    UserLightning selectByGid(String gid);

    List<String> getAllInvitationCode();

}