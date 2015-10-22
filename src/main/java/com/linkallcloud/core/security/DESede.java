/*
 * com.qc.security.DESede.java 
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
public class DESede extends Encryptor {

    /**
     * @param keySeed
     */
    public DESede(String keySeed) {
        super(Encryptor.ENC_DESede, keySeed);
    }

    /**
     * @param keySeed
     * @param encoding
     */
    public DESede(String keySeed, String encoding) {
        super(Encryptor.ENC_DESede, keySeed, encoding);
    }

}
