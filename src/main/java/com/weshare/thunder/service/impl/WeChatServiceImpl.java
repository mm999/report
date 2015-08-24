package com.weshare.thunder.service.impl;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mysql.jdbc.StringUtils;
import com.weshare.thunder.model.LightningResponse;
import com.weshare.thunder.service.WechatService;
import com.weshare.thunder.utils.WechatConfig;
import com.weshare.thunder.utils.WechatNetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WeChatServiceImpl implements WechatService {
    private static final Logger logger = LoggerFactory.getLogger(WeChatServiceImpl.class);

    /**
     * 检查访问的用户是否在企业应用可见范围之内
     * @param userId
     * @return
     * @throws Exception
     */
    public boolean checkUserIsScope(String userId) throws Exception {
        boolean flag = false;
        String accessToken = getValidAccessToken();
        LightningResponse appBaseInfoResponse = getEntAppBaseInfo(accessToken); //获取企业号某个应用的基本信息
        if (appBaseInfoResponse != null && appBaseInfoResponse.getStatus() == 0) {
            if (!StringUtils.isNullOrEmpty(userId)) {
                //获取企业可见范围人员列表
                Map entMap = (Map)appBaseInfoResponse.getContent();
                Map userInfoMap = (Map)entMap.get("allow_userinfos");
                List<Map> userList = (List)userInfoMap.get("user");
                for(Map userMap : userList){
                    if(userId.equals((String)userMap.get("userid"))){
                        flag = true;
                        break;
                    }
                }
            }
        }
        return flag;
    }


    //获取企业号某个应用的基本信息
    public LightningResponse getEntAppBaseInfo(String accessToken) throws Exception {
        WechatNetworkUtils networkutils = new WechatNetworkUtils();
        WechatNetworkUtils.TransportHttpClient httpclient = networkutils.new TransportHttpClient();
        WechatNetworkUtils.Request request = networkutils.new Request();
        WechatConfig config = new WechatConfig();
        request.setPath(WechatConfig.GET_ENT_GETAPPBASEINFO);
        request.setMethod(WechatNetworkUtils.Request.METHOD_TYPE_GET);
        request.addGetParameter("access_token", accessToken);
        request.addGetParameter("agentid",config.getValue("wechat.agentid"));
        String resstr;
        LightningResponse response = new LightningResponse();
        try {
            resstr = httpclient.sendHttpRequest(request);
            response = handleHttpResponse(resstr);
            if(response.getStatus()==0) {
                return response;
            } else {
                return null;
            }
        } catch(Exception e) {
            return response;
        }
    }

    //根据code获取成员信息（微信身份验证接口）
    public LightningResponse getUserInfo(String accessToken,String code) throws Exception {
        WechatNetworkUtils networkutils = new WechatNetworkUtils();
        WechatNetworkUtils.TransportHttpClient httpclient = networkutils.new TransportHttpClient();
        WechatNetworkUtils.Request request = networkutils.new Request();
        request.setPath(WechatConfig.GET_ENT_GETUSERINFO);
        request.setMethod(WechatNetworkUtils.Request.METHOD_TYPE_GET);
        request.addGetParameter("access_token", accessToken);
        request.addGetParameter("code",code);
        String resstr;
        LightningResponse response = new LightningResponse();
        try {
            resstr = httpclient.sendHttpRequest(request);
            response = handleHttpResponse(resstr);
            if(response.getStatus()==0) {
                return response;
            } else {
                return null;
            }
        } catch(Exception e) {
            return response;
        }
    }

    //获取accessToken
    public String getValidAccessToken(){
        WechatNetworkUtils networkutils = new WechatNetworkUtils();
        WechatNetworkUtils.TransportHttpClient httpclient = networkutils.new TransportHttpClient();
        WechatNetworkUtils.Request request = networkutils.new Request();
        request.setPath(WechatConfig.GET_ENT_ACCESSTOKEN);
        request.setMethod(WechatNetworkUtils.Request.METHOD_TYPE_GET);

        WechatConfig config = new WechatConfig();
        request.addGetParameter("corpid", config.getValue("wechat.corpid"));
        request.addGetParameter("corpsecret", config.getValue("wechat.corpsecret"));

        String resstr;
        try {
            resstr = httpclient.sendHttpRequest(request);
            LightningResponse response = handleHttpResponse(resstr);
            if(response.getStatus()==0) {
                Map map =  (Map<String,Object>)response.getContent();
                return (String)map.get("access_token");
            } else {
                return null;
            }
        } catch(Exception e) {
            return null;
        }
    }

    //处理响应结果
    private LightningResponse handleHttpResponse(String resstr) {
        LightningResponse response = new LightningResponse();
        if(resstr!=null) {
            Gson gson = new Gson();
            System.out.println("=================================微信接口返回的数据：" + resstr);
                Map<String,Object> parseResult  = gson.fromJson(resstr,new TypeToken<HashMap<String,Object>>(){}.getType());
                logger.info("response map is  {}", parseResult);
                if(parseResult.containsKey("errcode") && !"0.0".equals(parseResult.get("errcode").toString()) && !"0".equals(parseResult.get("errcode").toString()) ) {
                    if("43003".equals(parseResult.get("errcode"))) {
//                    ClearCachedandSharedToken(accountId);
                    }
                    response.setStatus(-1);
                    response.setMessage("weixin server return errcode");
                    response.setContent(parseResult);
                } else {
                    response.setStatus(0);
                    response.setMessage("access weixin server success");
                    response.setContent(parseResult);
                }
        } else {
            response.setStatus(-2);
            response.setMessage("access weixin server fail");
        }

        return response;
    }
}
