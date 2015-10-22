/**
 * Copyright (c) 2010 www.public.zj.cn
 *
 * com.qc.lang.util.Utils.java 
 *
 * Created at 2010-7-25 by zhoudong(hzzdong@gmail.com)
 * 
 */
package com.linkallcloud.core.utils;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;


/**
 * @author zhoudong(hzzdong@gmail.com)
 * 
 */
public class Config {
    private static Log log = Logs.getLog(Config.class);

    private static ResourceBundle resources;

    private static void getBundle() {
        try {
            resources = ResourceBundle.getBundle("system", Locale.getDefault());
            if (resources.getString("debug").trim().toLowerCase().equals(IConstants.TRUE)) {
                resources = ResourceBundle.getBundle("system-dev", Locale.getDefault());
            }
        } catch (MissingResourceException mre) {
            log.error(mre.getMessage(), mre);
        }
    }

    private static boolean checkResources() {
        if (resources == null) {
            getBundle();
        } /*
           * else { try { if (resources.getString("debug").trim().toLowerCase().equals( "true")) { getBundle(); } }
           * catch (Exception e) { ; } }
           */
        return resources != null;
    }

    private static boolean changeToBoolean(String str) throws Exception {
        String tmp = str.toLowerCase();
        if (tmp.equals(IConstants.TRUE)) {
            return true;
        } else if (tmp.equals(IConstants.FALSE)) {
            return false;
        } else {
            throw new Exception("class not matching.");
        }
    }

    public static boolean getBoolean(String key) {
        String str = Config.getString(key);
        try {
            return Config.changeToBoolean(str);
        } catch (Throwable e) {
            return false;
        }
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        String str = Config.getString(key);
        try {
            return Config.changeToBoolean(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    private static int changeToInt(String str) throws Exception {
        return Integer.parseInt(str);
    }

    public static int getInt(String key) {
        String str = Config.getString(key);
        try {
            return Config.changeToInt(str);
        } catch (Throwable e) {
            return 0;
        }
    }

    public static int getInt(String key, int defaultValue) {
        String str = Config.getString(key);
        try {
            return Config.changeToInt(str);
        } catch (Throwable e) {
            return defaultValue;
        }
    }

    public static String getString(String key, String defaultValue) {
        String tmp = null;
        if (checkResources()) {
            try {
                tmp = resources.getString(key).trim();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return Strings.isBlank(tmp) ? defaultValue : tmp;
    }

    public static String getString(String key) {
        if (checkResources()) {
            try {
                return resources.getString(key).trim();
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        }
        return null;
    }

    public static String[] getStringArray(String key) {
        if (checkResources()) {
            return resources.getStringArray(key);
        }
        return null;
    }

    public static Enumeration<String> getKeys() {
        return resources.getKeys();
    }

}
