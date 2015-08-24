package com.weshare.thunder.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Created by Administrator on 2015/5/11.
 */
public class WechatConfig {
    private static final Logger logger = LoggerFactory.getLogger(WechatConfig.class);

    Properties props = new Properties();
    InputStream inputStream = null;
    public WechatConfig() {
        try {
            inputStream = getClass().getResourceAsStream("/properties/wechat.properties");
            props.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
//        String URL_TAG = props.getProperty("wechat.urlTag");
//        String OPEN_ID = props.getProperty("wechat.openId");
//        String APP_ID = props.getProperty("wechat.appId");
//        String APP_SECRET = props.getProperty("wechat.appSecret");
//        String WEIXIN_TOKEN = props.getProperty("wechat.weixinToken");
//        String WHITE_ENABLE = props.getProperty("wechat.whitelistenable");
//
//        if(Utility.isEmpty(URL_TAG) || Utility.isEmpty(OPEN_ID) || Utility.isEmpty(APP_ID)
//                || Utility.isEmpty(APP_SECRET) || Utility.isEmpty(WEIXIN_TOKEN) || Utility.isEmpty(WHITE_ENABLE)) {
//            logger.error("Wechat Properties Configuration Error");
//            throw new RuntimeException("Wechat Properties Configuration Error");
//        } else {
//            URL_TAG = URL_TAG.trim();
//            OPEN_ID = OPEN_ID.trim();
//            APP_ID = APP_ID.trim();
//            APP_SECRET = APP_SECRET.trim();
//            WEIXIN_TOKEN = WEIXIN_TOKEN.trim();
//            WHITE_ENABLE = WHITE_ENABLE.trim();
//        }
//
//        WHITE_NAME_LIST_ENABLE = "true".equalsIgnoreCase(WHITE_ENABLE);
//
//        WxServerInfo wxInfo = new WxServerInfo(URL_TAG, OPEN_ID, APP_ID, APP_SECRET, WEIXIN_TOKEN);
//        WXSERVERINFOS.add(wxInfo);
    }

    public String getValue(String key){
        return props.getProperty(key);
    }

    private List<WxServerInfo> WXSERVERINFOS = new ArrayList<WxServerInfo>();
    public boolean WHITE_NAME_LIST_ENABLE = true;

    public  WxServerInfo getWeChatConfig(int accountindex) {
        return WXSERVERINFOS.get(accountindex);
    }

    public int getWechatConfigNum() {
        return WXSERVERINFOS.size();
    }

    public static class WxServerInfo {
        public String URL_TAG;
        public String OPEN_ID;
        public String APP_ID;
        public String APP_SECRET;
        public String WEIXIN_TOKEN;
        public String REDIS_TOKEN_KEY;
        public String REDIS_JSAPI_KEY;
        public String DISTRIBUTED_LOCK_FOR_TOKEN;
        public String DISTRIBUTED_LOCK_FOR_TICKET;

        WxServerInfo(
                String urltag,
                String openid,
                String appid,
                String appsecret,
                String token
        ) {
            URL_TAG = urltag;
            APP_ID = appid;
            OPEN_ID = openid;
            APP_SECRET = appsecret;
            WEIXIN_TOKEN = token;
            REDIS_TOKEN_KEY = urltag.concat("_AccessToken");
            REDIS_JSAPI_KEY = urltag.concat("_JSApiTicket");
            DISTRIBUTED_LOCK_FOR_TOKEN = urltag.concat("_accessToken_lock");
            DISTRIBUTED_LOCK_FOR_TICKET = urltag.concat("_jsapiTicket_lock");
        }
    }


    public static final String WHITE_SCENE_ID = "888888";  //1-100000

    public static final boolean NEED_SIGNATURE_CHECK = true;
    public static final int ACCOUNT_SDLC_INDEX = 0;
    public static final long MEM_TTL_AT_JT = 60L; //second, memttl for AccessToken and JsapiTicket
    public static final long REDIS_TTL_CACHE_AT_JT = 200L; //second, redis ttl cache for AccessToken and JsapiTicket

    public static final int DISTRIBUTED_LOCK_TIMEOUT = 60;

    public static final String GET_GET_UNIONID = "https://api.weixin.qq.com/sns/oauth2/access_token";
    public static final String POST_CREATE_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create";
    public static final String GET_GET_ACCESSTOKEN = "https://api.weixin.qq.com/cgi-bin/token";
    public static final String GET_GET_USERINFO = "https://api.weixin.qq.com/cgi-bin/user/info";
    public static final String POST_UPLOAD_FILE = "http://file.api.weixin.qq.com/cgi-bin/media/upload";
    public static final String POST_CREATE_GROUP = "https://api.weixin.qq.com/cgi-bin/groups/create";
    public static final String POST_CREATE_TICKET = "https://api.weixin.qq.com/cgi-bin/qrcode/create";
    public static final String POST_UPDATE_MEMBERS = "https://api.weixin.qq.com/cgi-bin/groups/members/update";
    public static final String POST_SEND_TEMPLATE_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/template/send";
    public static final String POST_SEND_MESSAGE = "https://api.weixin.qq.com/cgi-bin/message/custom/send";
    public static final String GET_SET_JSAPI_TICKET = "https://api.weixin.qq.com/cgi-bin/ticket/getticket";
    public static final String GET_ENT_ACCESSTOKEN = "https://qyapi.weixin.qq.com/cgi-bin/gettoken";
    public static final String GET_ENT_GETUSERINFO = "https://qyapi.weixin.qq.com/cgi-bin/user/getuserinfo";
    public static final String GET_ENT_GETAPPBASEINFO = "https://qyapi.weixin.qq.com/cgi-bin/agent/get";

/*
// Zhangshangjinrong
    {
        "button":[
        {
            "type":"view",
            "name":"闪电理财",
            "url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf27c11a1e6ae199a&redirect_uri=http://tbreeze.weshare.com.cn/index.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
        },
        {
            "name":"大事件",
            "sub_button":[
            {
                "type":"view",
                "name":"第一届闪粉节",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA5NDM2MDYzNQ==&mid=220729300&idx=1&sn=02e3b2283325966b93fc5472d020890c#rd"
            },
            {
                "type":"view",
                "name":"315金融创新奖",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA5NDM2MDYzNQ==&mid=213007753&idx=4&sn=efcc9c2e40948b90dd20e3b321f04e78#rd"
            },
            {
                "type":"view",
                "name":"融资千万美元",
                    "url":"http://mp.weixin.qq.com/s?__biz=MzA5NDM2MDYzNQ==&mid=227145953&idx=1&sn=678cf45f0c71fc4841b001a5758087a5#rd"
            },
            {
                "type":"view",
                "name":"金融创新论坛创新奖",
                    "url":"http://mp.weixin.qq.com/s?__biz=MzA5NDM2MDYzNQ==&mid=227146927&idx=1&sn=26dcc0f7dc6935ecab7393d30150aa32#rd"
            }]
        },
        {
            "name":"我的服务",
            "sub_button":[
            {
                "type":"view",
                "name":"闪电社区",
                "url":"http://sdlcbbs.weshare.com.cn/forum.php"
            },
            {
                "type":"view",
                "name":"关于我们",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208000085&idx=1&sn=13ca1d41511b1b6f329cca2c5930dd45#rd"
            },
            {
                "type":"view",
                "name":"意见反馈",
                "url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxf27c11a1e6ae199a&redirect_uri=http://licai.weshare.com.cn/feedback.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
            },
            {
                "type":"click",
                "name":"联系客服",
                "key":"CLICK_ContactUs"
            }]
        }]
    }
*/

/*
// Shandianlicai
 {
        "button":[
        {
            "type":"view",
            "name":"闪电理财",
            "url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6a312e634485d3cb&redirect_uri=https://licai.weshare.com.cn/index.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
        },
        {
            "name":"大事件",
            "sub_button":[
            {
                "type":"view",
                "name":"第一届闪粉节",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208291415&idx=1&sn=8f68cbf1dd2a151e25b6eae3b7079021#rd"
            },
            {
                "type":"view",
                "name":"315金融创新奖",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208292617&idx=2&sn=b331d90477e1ef593940e6595f4aab5f#rd"
            },
            {
                "type":"view",
                "name":"融资千万美元",
                    "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208292308&idx=1&sn=bfb2c8e813f05fce1116c58b92b84de7#rd"
            },
            {
                "type":"view",
                "name":"金融创新论坛创新奖",
                    "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208292617&idx=1&sn=1c5dc6aae143d61d33ff5cf2dab9449f#rd"
            }]
        },
        {
            "name":"我的服务",
            "sub_button":[
            {
                "type":"view",
                "name":"闪电社区",
                "url":"http://sdlcbbs.weshare.com.cn/forum.php"
            },
            {
                "type":"view",
                "name":"关于我们",
                "url":"http://mp.weixin.qq.com/s?__biz=MzA4Mjc3ODY5MQ==&mid=208293686&idx=1&sn=93745ccb551182e1770c2ace3f20c3e3#rd"
            },
            {
                "type":"view",
                "name":"意见反馈",
                "url":"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx6a312e634485d3cb&redirect_uri=https://licai.weshare.com.cn/feedback.html&response_type=code&scope=snsapi_base&state=1#wechat_redirect"
            },
            {
                "type":"click",
                "name":"联系客服",
                "key":"CLICK_ContactUs"
            }]
        }]
    }
*/

}

