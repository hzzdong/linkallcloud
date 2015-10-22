package com.linkallcloud.core.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import javax.net.ssl.HttpsURLConnection;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.linkallcloud.core.security.ssl.DummySSLSocketFactory;


/**
 * URL相关一些帮助函数。
 * 
 */
public class Urls {
    private static Log log = Logs.getLog(Urls.class);

    /**
     * Retrieve the contents from the given URL as a String, assuming the URL's server matches what we expect it to
     * match.
     */
    public static String retrieveHttpUrl(String url) throws IOException {
        URL u = new URL(url);
        return retrieveHttpURL(u, false);
    }

    /**
     * Retrieve the contents from the given URL as a String, assuming the URL's server matches what we expect it to
     * match.
     * 
     * @param u
     * @return String
     * @throws IOException
     */
    public static String retrieveHttpURL(URL u) throws IOException {
        return retrieveHttpURL(u, false);
    }

    /**
     * Retrieve the contents from the given URL as a String, assuming the URL's server matches what we expect it to
     * match.
     * 
     * @param u
     * @return String
     * @throws IOException
     */
    private static String retrieveHttpURL(URL u, boolean dummy) throws IOException {
        if (log.isTraceEnabled()) {
            log.trace("entering retrieve(" + u + ")");
        }
        BufferedReader reader = null;
        try {
            URLConnection uc = u.openConnection();
            if (dummy) {
                ((HttpsURLConnection) uc).setSSLSocketFactory(new DummySSLSocketFactory());
            }
            uc.setRequestProperty("Connection", "close");
            reader = new BufferedReader(new InputStreamReader(uc.getInputStream()));
            String line;
            StringBuffer buf = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buf.append(line + "\n");
            }
            return buf.toString();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException ex) {
                // ignore
            }
        }
    }

    /**
     * Retrieve the contents from the given secure URL as a String, assuming the URL's server matches what we expect it
     * to match.
     */
    public static String retrieveHttpsUrl(String url) throws IOException {
        URL u = new URL(url);
        return retrieveHttpsURL(u, false);
    }

    /**
     * Retrieve the contents from the given secure URL as a String, assuming the URL's server matches what we expect it
     * to match.
     */
    public static String retrieveHttpsUrl(String url, boolean dummy) throws IOException {
        URL u = new URL(url);
        return retrieveHttpsURL(u, dummy);
    }

    /**
     * Retrieve the contents from the given secure URL as a String, assuming the URL's server matches what we expect it
     * to match.
     * 
     * @param url
     * @param dummy
     * @return String
     * @throws IOException
     */
    public static String retrieveHttpsURL(URL u) throws IOException {
        if (u == null || !u.getProtocol().equals("https")) {
            throw new IOException("only 'https' URLs are valid for this method");
        }
        return retrieveHttpURL(u, false);
    }

    /**
     * Retrieve the contents from the given secure URL as a String, assuming the URL's server matches what we expect it
     * to match.
     * 
     * @param url
     * @param dummy
     * @return String
     * @throws IOException
     */
    public static String retrieveHttpsURL(URL u, boolean dummy) throws IOException {
        if (u == null || !u.getProtocol().equals("https")) {
            throw new IOException("only 'https' URLs are valid for this method");
        }
        return retrieveHttpURL(u, dummy);
    }

    /**
     * parse the server domain or ip address from http url
     * 
     * @param url
     * @return Server
     */
    public static String parseServerFromUrl(String url) {
        if (Strings.isBlank(url) || !url.startsWith(IConstants.HTTP)) {
            return null;
        } else {
            String tmp =
                    url.startsWith(IConstants.HTTPS) ? url.substring(IConstants.HTTPS_REQUEST_FREFIX.length()) : url
                            .substring(IConstants.HTTP_REQUEST_FREFIX.length());
            int idx = tmp.indexOf(IConstants.COLON);
            if (idx > 0) {
                return tmp.substring(0, idx);
            } else {
                int idx2 = tmp.indexOf(IConstants.LEFT_SLASH);
                return idx2 > 0 ? tmp.substring(0, idx2) : tmp;
            }
        }
    }

    /**
     * 
     * @param url
     * @param perameterName
     * @param perameterValue
     * @return url
     * @throws UnsupportedEncodingException
     */
    public static String urlAppend(String url, String perameterName, String perameterValue)
            throws UnsupportedEncodingException {
        if (url.indexOf("?") == -1) {
            url += "?";
        } else {
            url += "&";
        }
        return url + perameterName + "=" + URLEncoder.encode(perameterValue, "UTF-8");
    }

}
