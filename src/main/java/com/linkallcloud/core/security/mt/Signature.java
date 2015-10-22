package com.linkallcloud.core.security.mt;

import org.nutz.lang.Strings;

import com.linkallcloud.core.exception.BaseException;
import com.linkallcloud.core.security.Idigest;

public class Signature {

    private String identity;// 签名者身份标识
    private String algorithm;// 签名算法
    private String value;// 签名内容

    public Signature() {
        this.algorithm = Idigest.ALG_SHA1;
    }

    /**
     * Signature构造器
     * 
     * @param identity
     *            签名者身份标识
     * @throws BaseException
     */
    public Signature(String identity) throws BaseException {
        this(identity, null);
    }

    /**
     * Signature构造器
     * 
     * @param identity
     *            签名者身份标识
     * @param algorithm
     *            签名算法
     * @param value
     *            签名内容
     * @throws BaseException
     */
    public Signature(String identity, String algorithm) throws BaseException {
        this.setIdentity(identity);
        this.setAlgorithm(algorithm);
    }

    /**
     * @return the algorithm
     */
    public String getAlgorithm() {
        return algorithm;
    }

    /**
     * @param algorithm
     *            the algorithm to set
     */
    public void setAlgorithm(String algorithm) {
        this.algorithm =
                (Strings.isBlank(algorithm) || !Idigest.ALG_MD5.equals(algorithm)) ? Idigest.ALG_SHA1 : Idigest.ALG_MD5;
    }

    /**
     * @return the value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     *            the value to set
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * @return the identity
     */
    public String getIdentity() {
        return identity;
    }

    /**
     * @param identity
     *            the identity to set
     * @throws BaseException
     */
    public void setIdentity(String identity) throws BaseException {
        if (Strings.isBlank(identity)) {
            throw new BaseException("e.signature.identity.blank", "Identity must not null.");
        }
        this.identity = identity;
    }

}
