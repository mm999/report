package com.weshare.thunder.service;

import com.weshare.thunder.model.LightningResponse;

public interface WechatService {
    //获取accessToken
    public String getValidAccessToken();
    //根据code获取成员信息（微信身份验证接口）
    public LightningResponse getUserInfo(String accessToken,String code) throws Exception;
    //获取企业号某个应用的基本信息
    public LightningResponse getEntAppBaseInfo(String accessToken)throws Exception;
    //检查访问的用户是否在企业应用可见范围之内
    public boolean checkUserIsScope(String userId)throws Exception;
}
