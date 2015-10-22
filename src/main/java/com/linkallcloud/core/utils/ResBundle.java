package com.linkallcloud.core.utils;

import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.nutz.lang.Strings;


/**
 * @author <a href="mailto:touchnan@gmail.com">chengqiang.han</a>
 * 
 */
public class ResBundle {
    private static Log log = LogFactory.getLog(ResBundle.class);

    private static ResourceBundle resources;

    private static void getBundle() {
        try {
            resources = ResourceBundle.getBundle("resource", Locale.getDefault());
        } catch (Throwable mre) {
        }
        try {
            if (null == resources) {
                resources = ResourceBundle.getBundle("res/resource", Locale.getDefault());
            }
        } catch (Throwable e) {
            log.error("No resource property file in the classpath or in the res folder.", e);
        }
    }

    private static boolean checkResources() {
        if (resources == null) {
            getBundle();
        }
        return resources != null;
    }

    public static String getMessage(String key) {
        return getString(key);
    }

    public static String getMessage(String key, String defaultValue) {
        return getString(key, defaultValue);
    }

    public static String getString(String key) {
        return getString(key, null);
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

}
