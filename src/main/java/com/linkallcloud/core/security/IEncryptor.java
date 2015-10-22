/*
 * com.qc.security.IEncryptor.java 
 *
 * 2011-4-27
 *
 * Copyright (c) 2010 www.public.zj.cn
 */
package com.linkallcloud.core.security;

/**
 * 2011-5-9
 * 
 * @author <a href="mailto:hzzdong@gmail.com">ZhouDong</a>
 */
public interface IEncryptor extends ISimpleEncryptor {

    /**
     * 加密后转base64
     * 
     * @param src
     * @return
     */
    public String encryptAndBase64(final String src);

    /**
     * base64转换后解密
     * 
     * @param src
     * @return
     */
    public String decryptAndBase64(final String src);
}
