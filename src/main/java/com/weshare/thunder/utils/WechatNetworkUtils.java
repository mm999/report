package com.weshare.thunder.utils;

import org.apache.http.*;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Administrator on 2015/5/12.
 */
public class WechatNetworkUtils {
    private static final Logger logger = LoggerFactory.getLogger(WechatNetworkUtils.class);

    private WechatConfig weChat = new WechatConfig();

    public class TransportHttpClient {

        private CloseableHttpClient mHttpclient;
        private HttpHost mProxy;
        private HttpUriRequest mReq = null;
        public TransportHttpClient() {
            mHttpclient = HttpClients.createDefault();
        }

        public void setProxy(String host, String port) {
            mProxy = new HttpHost(host, new Integer(port).intValue());
        }

        public String sendHttpRequest(Request request) throws Exception {
            String url = request.getUrl();
            if (request.getMethod().equals(Request.METHOD_TYPE_GET)) {
                mReq = new HttpGet(request.getUrl());
                if(mProxy!=null) {
                    RequestConfig config = RequestConfig.custom()
                            .setProxy(mProxy)
                            .build();
                    ((HttpGet)mReq).setConfig(config);
                }
            } else {
                mReq = new HttpPost(request.getUrl());
                if(request.getPostParamterStr() != null) {
                    StringEntity se = new StringEntity(request.getPostParamterStr(), "utf-8");
                    ((HttpPost) mReq).setEntity(se);
                    mReq.addHeader(HTTP.CONTENT_TYPE,"application/json;encoding=utf-8");
                } else if (request.getPostParameter() != null
                        && request.getPostParameter().size() > 0) {
                    UrlEncodedFormEntity entity = new UrlEncodedFormEntity(
                            request.getPostParameter(), "utf-8");
                    ((HttpPost) mReq).setEntity(entity);
                }
            }

                       // set header
            for (Map.Entry<String, String> m : request.getHeaders().entrySet()) {
                mReq.setHeader(m.getKey(), m.getValue());
            }

            HttpEntity resEntity = null;

            try {
                HttpResponse response = mHttpclient.execute(mReq);
                if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    resEntity = response.getEntity();
                    return resEntity != null ? EntityUtils.toString(resEntity,"utf-8") : null;
                }
                return null;
            } finally {
                if (resEntity != null)
                    EntityUtils.consume(resEntity);
                mHttpclient.close();
            }
        }

        public void cancelRequest() {

            if (mReq != null) {
                mReq.abort();
            }
        }
    }

    public class Request {
        public static final String METHOD_TYPE_GET = "GET";
        public static final String METHOD_TYPE_POST = "POST";

        private String mPath;
        private String mMethod = METHOD_TYPE_GET;

        private Map<String, String> mHeaders = new HashMap<String, String>();
        public List<NameValuePair> mPostParam = new ArrayList<NameValuePair>();
        public List<NameValuePair> mGetParam = new ArrayList<NameValuePair>();
        public String mPostParamStr = null;

        public Request() {
        }

        public void setPath(String path) {
            this.mPath = path;
        }

        public String getPath() {
            return mPath;
        }

         public void setMethod(String method) {
            this.mMethod = method;
        }

        public String getMethod() {
            return mMethod;
        }

        public void setWechatAppidAndSecret(int id) {
            addGetParameter("appid",weChat.getWeChatConfig(id).APP_ID);
            addGetParameter("secret", weChat.getWeChatConfig(id).APP_SECRET);
        }

        public void setAccessToken(String token) {
            addGetParameter("access_token",token);
        }

        public void setOpenId(String openid) {
            addGetParameter("openid",openid);
        }

        public void addHeader(String name, String value) {
            mHeaders.put(name, value);
        }
        public Map<String, String> getHeaders() {
            return mHeaders;
        }

        public String getHost() {
            StringBuffer sb = new StringBuffer();
            sb.append(mPath);
            return sb.toString();
        }

        public String getUrl() {

            StringBuffer sb = new StringBuffer();

            sb.append(mPath);
            if (mGetParam.size() > 0) {
                String param = URLEncodedUtils.format(mGetParam, "UTF-8");
                sb.append("?");

                sb.append(param);
            }
            return sb.toString();
        }

        public void addPostParameter(String name, String value) {
            mPostParam.add(new BasicNameValuePair(name, value));
        }

        public String getPostParameter(String name) {
            for (NameValuePair nameValuePair : mPostParam) {
                if (nameValuePair.getName().equals(name)) {
                    return nameValuePair.getValue();
                }
            }
            return null;
        }

        public void setPostParameterStr(String poststr) {
            mPostParamStr = poststr;
        }

        public String getPostParamterStr() {
            return mPostParamStr;
        }

        public List<NameValuePair> getPostParameter() {
            return mPostParam;
        }

        public List<NameValuePair> getReqGetPara() {
            return mGetParam;
        }

        public void addGetParameter(String name, String value) {
            mGetParam.add(new BasicNameValuePair(name, value));

        }
    }
}
