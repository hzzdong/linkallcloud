/**
 * Copyright (c) 2011 www.public.zj.cn
 *
 * com.qc.util.HttpUtils.java 
 *
 * 2012-9-6
 * 
 */
package com.linkallcloud.core.utils;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linkallcloud.core.exception.BaseException;

/**
 * 2012-9-6
 * 
 * @author <a href="mailto:hzzdong@gmail.com">ZhouDong</a>
 * 
 */
public class HttpUtils {
    private static Log log = Logs.getLog(HttpUtils.class);

    public static final String DEFAULT_ENCODING = HTTP.UTF_8;

    public static final String CONTENTTYPE_DEFAULT = "application/x-www-form-urlencoded";
    public static final String CONTENTTYPE_JSON = "application/json";
    public static final String CONTENTTYPE_JSON_RPC = "application/json-rpc";
    public static final String X_Requested_With = "X-Requested-With";
    public static final String X_Requested_With_AJAX = "XMLHttpRequest";
    public static final String USER_AGENT = "User-Agent";

    public static final String UNKNOWN = "unknown";

    /**
     * 判断是否是Ajax请求
     * 
     * @param req
     * @return boolean
     */
    public static boolean isAjax(HttpServletRequest req) {
        return X_Requested_With_AJAX.equals(req.getHeader(X_Requested_With));
    }

    /**
     * 判断contentType是否json
     * 
     * @param contentType
     * @return boolean
     */
    public static boolean isJson(String contentType) {
        return CONTENTTYPE_JSON.equalsIgnoreCase(contentType);
    }

    /**
     * 判断contentType是否jsonRpc
     * 
     * @param contentType
     * @return boolean
     */
    public static boolean isJsonRpc(String contentType) {
        return CONTENTTYPE_JSON_RPC.equalsIgnoreCase(contentType);
    }

    /**
     * 得到contentType
     * 
     * @param req
     * @return boolean
     */
    public static String getContentType(HttpServletRequest req) {
        String contentType = req.getHeader("content-type");
        if (contentType != null) {
            int iSemicolonIdx;
            if ((iSemicolonIdx = contentType.indexOf(";")) != -1)
                contentType = contentType.substring(0, iSemicolonIdx);
        }
        return contentType;
    }

    /**
     * 设置contentType
     * 
     * @param resp
     * @param contentType
     */
    public static void setContentType(HttpServletResponse resp, String contentType) {
        resp.setContentType(contentType + ";charset=" + DEFAULT_ENCODING);
    }

    /**
     * 获取客户端的ip地址
     * 
     * @param request
     * @return ip
     */
    public static String getClientIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (Strings.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (Strings.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (Strings.isBlank(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 
     * @param url
     * @return
     * @throws BaseException
     */
    public static String httpGet(String url) throws BaseException {
        try {
            HttpGet httpRequest = new HttpGet(url);
            httpRequest.addHeader("Content-Type", "text/xml");
            HttpResponse response = HttpClientFactory.getHttpClient().execute(httpRequest);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } else {
                throw new BaseException("e.httpGet", "远程调用" + url + "失败，状态码:" + statusCode);
            }
        } catch (Throwable e) {
            log.error("调用远程接口失败:", e);
            throw new BaseException("e.httpGet", "远程调用" + url + "失败:" + e.getMessage());
        }
    }

    /**
     * 
     * @param url
     * @param data
     * @return
     * @throws BaseException
     */
    public static String httpPost(String url, String data) throws BaseException {
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "text/xml");
            if (!Strings.isBlank(data)) {
                StringEntity myEntity = new StringEntity(data, HTTP.UTF_8);
                httppost.setEntity(myEntity);
            }
            HttpResponse response = HttpClientFactory.getHttpClient().execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } else {
                throw new BaseException("e.httpPost", "远程调用" + url + "失败，状态码:" + statusCode);
            }
        } catch (Throwable e) {
            log.error("调用远程接口失败:", e);
            throw new BaseException("e.httpPost", "远程调用" + url + "失败:" + e.getMessage());
        }
    }

    /**
     * 
     * @param url
     * @param params
     * @return
     * @throws BaseException
     */
    public static String httpPost(String url, List<NameValuePair> params) throws BaseException {
        try {
            HttpPost httppost = new HttpPost(url);
            httppost.addHeader("Content-Type", "text/xml");
            if (params != null && !params.isEmpty()) {
                httppost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));
            }
            HttpResponse response = HttpClientFactory.getHttpClient().execute(httppost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                return EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
            } else {
                throw new BaseException("e.httpPost", "远程调用" + url + "失败，状态码:" + statusCode);
            }
        } catch (Throwable e) {
            log.error("调用远程接口失败:", e);
            throw new BaseException("e.httpPost", "远程调用" + url + "失败:" + e.getMessage());
        }
    }
}
