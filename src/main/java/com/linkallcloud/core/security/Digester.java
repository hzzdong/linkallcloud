package com.linkallcloud.core.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.nutz.lang.Strings;
import org.nutz.log.Logs;
import org.springframework.util.Assert;

import com.linkallcloud.core.utils.EncodeUtils;

public class Digester implements Idigest {
    public static final String ALG_SHA1 = "SHA-1";
    public static final String ALG_MD5 = "MD5";

    protected MessageDigest messageDigest;
    private String en;// 默认编码，可设置默认

    public Digester() {
        super();
        this.en = "utf-8";
    }

    /**
     * 
     * @param digestAlgorithm
     * @param encoding
     */
    public Digester(String digestAlgorithm, String encoding) {
        Assert.notNull(digestAlgorithm, "digestAlgorithm参数不能为空。");
        try {
            this.messageDigest = MessageDigest.getInstance(digestAlgorithm);
        } catch (NoSuchAlgorithmException e) {
        }
        this.en = Strings.isBlank(encoding) ? "utf-8" : encoding;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.Idigest#encrypt(java.lang.String)
     */
    public String encrypt(String src) {
        return encrypt(src, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.Idigest#encrypt(java.lang.String, java.lang.String)
     */
    public String encrypt(String src, String encoding) {
        Assert.notNull(src, "参数不能为空。");
        return EncodeUtils.hexEncode(digestEncrypt(src, encoding));
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.Idigest#encryptAndBase64(java.lang.String)
     */
    public String encryptAndBase64(String src) {
        return encryptAndBase64(src, null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.Idigest#encryptAndBase64(java.lang.String, java.lang.String)
     */
    public String encryptAndBase64(String src, String encoding) {
        Assert.notNull(src, "参数不能为空。");
        return EncodeUtils.base64Encode(digestEncrypt(src, encoding));
    }

    /**
     * digest
     * 
     * @param src
     * @param encoding
     * @return
     */
    private byte[] digestEncrypt(String src, String encoding) {
        try {
            if (null != messageDigest) {
                byte[] digest = null;
                synchronized (messageDigest) {
                    messageDigest.update(src.getBytes(Strings.isBlank(encoding) ? en : encoding));
                    digest = messageDigest.digest();                    
                }
                return digest;
            }
        } catch (Exception e) {
            Logs.getLog(getClass()).error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * @return the encoding
     */
    public String getEncoding() {
        return en;
    }

    /**
     * @param encoding
     *            the encoding to set
     */
    public void setEncoding(String encoding) {
        this.en = encoding;
    }

    /**
     * @return the messageDigest
     */
    public MessageDigest getMessageDigest() {
        return messageDigest;
    }

    /**
     * @param messageDigest
     *            the messageDigest to set
     */
    public void setMessageDigest(MessageDigest messageDigest) {
        this.messageDigest = messageDigest;
    }

}
