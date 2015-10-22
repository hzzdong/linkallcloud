/*
 * com.qc.security.DES.java 
 *
 * 2011-4-28
 *
 * Copyright (c) 2010 www.public.zj.cn
 */
package com.linkallcloud.core.security;

/**
 * 2011-4-28
 * 
 * @author <a href="mailto:hzzdong@gmail.com">ZhouDong</a>
 */
public class DES extends Encryptor {

    /**
     * @param keySeed
     */
    public DES(String keySeed) {
        super(Encryptor.ENC_DES, keySeed);
    }

    /**
     * @param keySeed
     * @param encoding
     */
    public DES(String keySeed, String encoding) {
        super(Encryptor.ENC_DES, keySeed, encoding);
    }

}
