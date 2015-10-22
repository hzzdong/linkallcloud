/*
 * com.qc.security.MD6.java 
 *
 * 2011-4-27
 *
 * Copyright (c) 2010 www.public.zj.cn
 */
package com.linkallcloud.core.security;

/**
 * 2011-4-27
 * 
 * @author <a href="mailto:hzzdong@gmail.com">ZhouDong</a>
 */
public class MD5 extends Digester {
    private static MD5 me = new MD5();

    public MD5() {
        this(null);
    }

    public MD5(String encoding) {
        super(Idigest.ALG_MD5, encoding);
    }

    /**
     * MD5后进行Hex编码，默认编码UTF-8
     * 
     * @param src
     * @return
     */
    public static String digest(String src) {
        return me.encrypt(src);
    }

    /**
     * MD5后进行Hex编码
     * 
     * @param src
     * @param encoding
     * @return
     */
    public static String digest(String src, String encoding) {
        return me.encrypt(src, encoding);
    }

    /**
     * MD5后进行Base64编码，默认编码UTF-8
     * 
     * @param src
     * @return
     */
    public static String digestBase64(String src) {
        return me.encryptAndBase64(src);
    }

    /**
     * MD5后进行Base64编码
     * 
     * @param src
     * @param encoding
     * @return
     */
    public static String digestBase64(String src, String encoding) {
        return me.encryptAndBase64(src, encoding);
    }

}
