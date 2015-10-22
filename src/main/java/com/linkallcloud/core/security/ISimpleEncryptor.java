/*
 * com.qc.security.ISimpleEncryptor.java 
 *
 * 2011-5-3
 *
 * Copyright (c) 2010 www.public.zj.cn
 */
package com.linkallcloud.core.security;

/**
 * 消息签名的自定义加解密算法接口
 * 
 * 2011-5-3
 * 
 * @author <a href="mailto:hzzdong@gmail.com">ZhouDong</a>
 */
public interface ISimpleEncryptor {

    /**
     * 加密
     * 
     * @param src
     * @return
     */
    public String encrypt(final String src);

    /**
     * 解密
     * 
     * @param src
     * @return
     */
    public String decrypt(final String src);

}
