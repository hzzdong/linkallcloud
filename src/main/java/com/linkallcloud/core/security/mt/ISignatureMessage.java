package com.linkallcloud.core.security.mt;

import com.linkallcloud.core.exception.BaseException;
import com.linkallcloud.core.security.ISimpleEncryptor;


/**
 * 消息签名对象。支持消息自动加解密(暂时只支持DES,DESede,Blowfish)，其它加解密算法请自行提供加解密器。 消息签名算法支持SHA-1和MD5，默认SHA-1。
 * 
 */
public interface ISignatureMessage {

    /**
     * 得到原始消息内容
     * 
     * @return
     */
    String getMessage();

    /**
     * 得到时间戳
     * 
     * @return
     */
    String getTimeStamp();

    /**
     * 得到签名算法
     * 
     * @return
     */
    String getSignatureMethodAlgorithm();

    /**
     * 得到签名者身份标识
     * 
     * @return
     */
    String getSignatureIdentity();

    /**
     * 得到签名内容
     * 
     * @return
     */
    String getSignatureValue();

    /**
     * 是否超时:有效期默认为10秒
     * 
     * @return
     */
    boolean isTimeout();

    /**
     * 是否超时：有效期为period
     * 
     * @return
     */
    boolean isTimeout(long period);

    /**
     * 签名
     * 
     * @param signatureKey
     *            签名的共享密钥
     * @throws BaseException
     */
    void sign(String signatureKey) throws BaseException;

    /**
     * 验证签名
     * 
     * @param signatureKey
     *            签名的共享密钥
     * @throws BaseException
     */
    void verify(String signatureKey) throws BaseException;

    /**
     * 签名后调用此函数打包成完整消息包
     * 
     * @return
     * @throws BaseException
     */
    String packMessage() throws BaseException;

    /**
     * 未签名，可直接调用此函数自动签名并打包成完整消息包
     * 
     * @param signatureKey
     *            签名的共享密钥
     * @return
     * @throws BaseException
     */
    String packMessage(String signatureKey) throws BaseException;

    /**
     * 验证成功后调用此函数解包出消息并转化成java对象，本方法只适用于消息没有加密的情况
     * 
     * @param msgObjClass
     *            消息对象类型
     * @return
     * @throws BaseException
     */
    Object unpackMessage(Class<?> msgObjClass) throws BaseException;

    /**
     * 验证成功后调用此函数解包出消息并转化成java对象
     * 
     * @param msgObjClass
     *            消息对象类型
     * @param messageEncKey
     *            消息加密KEY
     * @return
     * @throws BaseException
     */
    Object unpackMessage(Class<?> msgObjClass, String messageEncKey) throws BaseException;

    /**
     * 验证成功后调用此函数中的自定义消息加解密器encryptor解包出消息并转化成java对象
     * 
     * @param msgObjClass
     *            消息对象类型
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @return
     * @throws BaseException
     */
    Object unpackMessage(Class<?> msgObjClass, String messageEncKey, ISimpleEncryptor encryptor) throws BaseException;

    /**
     * 未验证，可调用此函数自动验证、解包出原始消息并转化成java对象
     * 
     * @param msgObjClass
     *            消息对象类型
     * @param signatureKey
     *            签名的共享密钥
     * @param messageEncKey
     *            消息加密KEY
     * @return
     * @throws BaseException
     */
    Object unpackMessage(Class<?> msgObjClass, String signatureKey, String messageEncKey) throws BaseException;

    /**
     * 未验证，可调用此函数自动验证、用自定义消息加解密器encryptor解包出原始消息并转化成java对象
     * 
     * @param msgObjClass
     *            消息对象类型
     * @param signatureKey
     *            签名的共享密钥
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @return
     * @throws BaseException
     */
    Object unpackMessage(Class<?> msgObjClass, String signatureKey, String messageEncKey, ISimpleEncryptor encryptor)
            throws BaseException;

}
