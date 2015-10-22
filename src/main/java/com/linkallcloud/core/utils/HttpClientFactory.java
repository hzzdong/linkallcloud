/*
 * com.qc.util.HttpClientFactory.java
 * Oct 28, 2011 
 */
package com.linkallcloud.core.utils;

import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;

/**
 * Oct 28, 2011
 * 
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class HttpClientFactory {
    public static HttpClient getHttpClient() {
        return Holder.httpClient;
    }

    private static class Holder {
        private static HttpClient httpClient;

        static {

            /**
             * 最大连接数
             */
            int MAX_TOTAL_CONNECTIONS = 800;
            /**
             * 每个路由最大连接数
             */
            int MAX_ROUTE_CONNECTIONS = 400;
            /**
             * 连接超时时间
             */
            int CONNECT_TIMEOUT = 10000;
            /**
             * 读取超时时间
             */
            int READ_TIMEOUT = 10000;

            HttpParams params = new BasicHttpParams();
            // 版本
            HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);

            // Activates 'Expect: 100-continue' handshake for the entity enclosing methods.
            // HttpProtocolParams.setUseExpectContinue(params, true);
            // 超时
            // 设置连接超时时间
            HttpConnectionParams.setConnectionTimeout(params, CONNECT_TIMEOUT);
            // 设置读取超时时间
            HttpConnectionParams.setSoTimeout(params, READ_TIMEOUT);

            ThreadSafeClientConnManager cm = new ThreadSafeClientConnManager();
            cm.setMaxTotal(MAX_TOTAL_CONNECTIONS);
            cm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS);

            httpClient = new DefaultHttpClient(cm, params);
        }
    }
}
