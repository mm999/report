package com.weshare.thunder.dao;

import com.weshare.thunder.model.UserChannelWechat;
import com.weshare.thunder.model.UserChannelWechatExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserChannelWechatMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int countByExample(UserChannelWechatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int deleteByExample(UserChannelWechatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int insert(UserChannelWechat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int insertSelective(UserChannelWechat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    List<UserChannelWechat> selectByExample(UserChannelWechatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    UserChannelWechat selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") UserChannelWechat record, @Param("example") UserChannelWechatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") UserChannelWechat record, @Param("example") UserChannelWechatExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(UserChannelWechat record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table user_channel_wechat
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(UserChannelWechat record);
}