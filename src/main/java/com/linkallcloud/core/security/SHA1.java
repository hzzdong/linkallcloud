/**
 * Copyright (c) 2010 www.public.zj.cn
 *
 * com.qc.security.SHA1.java 
 * 2010-10-12 zhoudong(hzzdong@gmail.com)
 * 
 */
package com.linkallcloud.core.security;

/**
 * @author zhoudong(hzzdong@gmail.com)
 * 
 */
public class SHA1 extends Digester {
    private static SHA1 me = new SHA1();

    public SHA1() {
        this(null);
    }

    public SHA1(String encoding) {
        super(Idigest.ALG_SHA1, encoding);
    }

    /**
     * SHA1后进行Hex编码，默认编码UTF-8
     * 
     * @param src
     * @return String
     */
    public static String digest(String src) {
        return me.encrypt(src);
    }

    /**
     * SHA1后进行Hex编码
     * 
     * @param src
     * @param encoding
     * @return String
     */
    public static String digest(String src, String encoding) {
        return me.encrypt(src, encoding);
    }

    /**
     * SHA1后进行Base64编码，默认编码UTF-8
     * 
     * @param src
     * @return String
     */
    public static String digestBase64(String src) {
        return me.encryptAndBase64(src);
    }

    /**
     * SHA1后进行Base64编码
     * 
     * @param src
     * @param encoding
     * @return String
     */
    public static String digestBase64(String src, String encoding) {
        return me.encryptAndBase64(src, encoding);
    }

}
