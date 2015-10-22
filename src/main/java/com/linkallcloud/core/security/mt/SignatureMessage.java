package com.linkallcloud.core.security.mt;

import java.util.Date;

import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.Strings;

import com.linkallcloud.core.exception.BaseException;
import com.linkallcloud.core.security.Encryptor;
import com.linkallcloud.core.security.IEncryptor;
import com.linkallcloud.core.security.ISimpleEncryptor;
import com.linkallcloud.core.security.Idigest;
import com.linkallcloud.core.security.MD5;
import com.linkallcloud.core.security.SHA1;
import com.linkallcloud.core.utils.Dates;


/**
 * 消息签名对象。支持消息自动加解密，暂时只支持DES,DESede,Blowfish。消息签名算法支持SHA-1和MD5，默认SHA-1。
 * 
 */
public class SignatureMessage implements ISignatureMessage {
    public static final long DEFAULT_TIMEOUT = 10000;// 默认超时时间：10秒

    private String message;// 原始消息内容
    private String messageEncAlg;// 消息加解密算法，暂时支持DES,DESede,Blowfish。
    private String timeStamp;// 时间戳
    private Signature signature;// 签名

    public SignatureMessage() {
        super();
    }

    /**
     * 把收到的完整的安全消息包转换成SignatureMessage对象
     * 
     * @param signatureMessage
     *            string
     * @return SignatureMessage object
     */
    public static SignatureMessage from(String signatureMessage) {
        return Json.fromJson(SignatureMessage.class, signatureMessage);
    }

    /**
     * 构造消息签名对象，消息不加密，签名算法默认SHA-1。
     * 
     * @param messageObj
     *            原始消息对象
     * @param signatureIdentity
     *            签名者身份标识
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String signatureIdentity) throws BaseException {
        this(messageObj, null, null, signatureIdentity, null);
    }

    /**
     * 构造消息签名对象，消息不加密。
     * 
     * @param messageObj
     *            原始消息对象
     * @param signatureIdentity
     *            签名者身份标识
     * @param signatureAlg
     *            签名算法
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String signatureIdentity, String signatureAlg) throws BaseException {
        this(messageObj, null, null, signatureIdentity, signatureAlg);
    }

    /**
     * 构造消息签名对象，签名算法默认SHA-1。
     * 
     * @param messageObj
     *            原始消息对象
     * @param messageEncAlg
     *            消息加密算法，目前只支持：DES,DESede,Blowfish三种算法。
     * @param messageEncKey
     *            消息加密KEY
     * @param signatureIdentity
     *            签名者身份标识
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String messageEncAlg, String messageEncKey, String signatureIdentity)
            throws BaseException {
        this(messageObj, messageEncAlg, messageEncKey, signatureIdentity, null);
    }

    /**
     * 构造消息签名对象
     * 
     * @param messageObj
     *            原始消息对象
     * @param messageEncAlg
     *            消息加密算法，目前只支持：DES,DESede,Blowfish三种算法。
     * @param messageEncKey
     *            消息加密KEY
     * @param signatureIdentity
     *            签名者身份标识
     * @param signatureAlg
     *            签名算法
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String messageEncAlg, String messageEncKey, String signatureIdentity,
            String signatureAlg) throws BaseException {
        this.setMessageEncAlg(messageEncAlg);
        this.message = encryptMessage(messageObj, messageEncKey, null);
        // this.timeStamp = Dates.formatDate(new Date());//时间戳在签名的时候自动设置
        this.signature = new Signature(signatureIdentity, signatureAlg);
    }

    /**
     * 构造消息签名对象，消息根据自定义加解密器进行加解密，签名算法默认SHA-1。
     * 
     * @param messageObj
     *            原始消息对象
     * @param messageEncAlg
     *            消息加密算法，目前只支持：DES,DESede,Blowfish三种算法。
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @param signatureIdentity
     *            签名者身份标识
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String messageEncAlg, String messageEncKey, ISimpleEncryptor encryptor,
            String signatureIdentity) throws BaseException {
        this(messageObj, messageEncAlg, messageEncKey, encryptor, signatureIdentity, null);
    }

    /**
     * 构造消息签名对象，消息根据自定义加解密器进行加解密。
     * 
     * @param messageObj
     *            原始消息对象
     * @param messageEncAlg
     *            消息加密算法，目前只支持：DES,DESede,Blowfish三种算法。
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @param signatureIdentity
     *            签名者身份标识
     * @param signatureAlg
     *            签名算法
     * @throws BaseException
     */
    public SignatureMessage(Object messageObj, String messageEncAlg, String messageEncKey, ISimpleEncryptor encryptor,
            String signatureIdentity, String signatureAlg) throws BaseException {
        this.setMessageEncAlg(messageEncAlg);
        this.message = encryptMessage(messageObj, messageEncKey, encryptor);
        // this.timeStamp = Dates.formatDate(new Date());//时间戳在签名的时候自动设置
        this.signature = new Signature(signatureIdentity, signatureAlg);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#isTimeout()
     */
    public boolean isTimeout() {
        return isTimeout(DEFAULT_TIMEOUT);
    }

    /* (non-Javadoc)
     * @see com.qc.security.mt.ISignatureMessage#isTimeout(long)
     */
    public boolean isTimeout(long period) {
        try {
            Date messageTime = Dates.parseDate(getTimeStamp());
            return new Date().getTime() - messageTime.getTime() > period;
        } catch (BaseException e) {
            return true;
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#sign(java.lang.String)
     */
    public void sign(String signatureKey) throws BaseException {
        if (Strings.isBlank(message)) {
            throw new BaseException("e.error.blank.message", "\"message\" must not null.");
        }

        reSetTime();// 时间设置为当前时刻

        String hv = digestHMAC(signatureKey + getMessage() + getTimeStamp());
        getSignature().setValue(hv);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#verify()
     */
    public void verify(String signatureKey) throws BaseException {
        if (Strings.isBlank(signatureKey)) {
            throw new BaseException("e.error.blank.signatureKey", "\"signatureKey\" must not null.");
        }
        if (Strings.isBlank(message)) {
            throw new BaseException("e.error.blank.message", "\"message\" must not null.");
        }
        if (Strings.isBlank(timeStamp)) {
            throw new BaseException("e.error.blank.timeStamp", "\"timeStamp\" must not null.");
        }
        if (Strings.isBlank(getSignatureValue())) {
            throw new BaseException("e.error.no.signature", "Message must be signed.");
        }

        String hv = digestHMAC(signatureKey + getMessage() + getTimeStamp());
        if (Strings.isBlank(hv) || !hv.equals(getSignatureValue())) {
            throw new BaseException("e.error.signature", "Signature verfy failure.");
        }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#packageMessage()
     */
    public String packMessage() throws BaseException {
        if (Strings.isBlank(getSignatureValue())) {
            throw new BaseException("e.error.no.signature", "Message must be signed first.");
        }
        return Json.toJson(this, JsonFormat.nice());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#packageMessage(java.lang.String)
     */
    public String packMessage(String signatureKey) throws BaseException {
        sign(signatureKey);
        return packMessage();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#unpackMessage(java.lang.Class)
     */
    public Object unpackMessage(Class<?> msgObjClass) throws BaseException {
        return unpackMessage(msgObjClass, null, (ISimpleEncryptor) null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#unpackMessage(java.lang.Class, java.lang.String)
     */
    public Object unpackMessage(Class<?> msgObjClass, String messageEncKey) throws BaseException {
        return unpackMessage(msgObjClass, messageEncKey, (ISimpleEncryptor) null);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#unpackMessage(java.lang.Class, java.lang.String,
     * com.qc.security.IISimpleEncryptor)
     */
    public Object unpackMessage(Class<?> msgObjClass, String messageEncKey, ISimpleEncryptor encryptor)
            throws BaseException {
        if (Strings.isBlank(message)) {
            return null;
        }
        if (null != messageEncKey) {
            decryptMessage(messageEncKey, encryptor);// 消息解码
        }
        return Json.fromJson(msgObjClass, message);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#unpackMessage(java.lang.Class, java.lang.String,
     * java.lang.String)
     */
    public Object unpackMessage(Class<?> msgObjClass, String signatureKey, String messageEncKey) throws BaseException {
        return unpackMessage(msgObjClass, signatureKey, messageEncKey, null);// 解包
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#unpackMessage(java.lang.Class, java.lang.String,
     * java.lang.String, com.qc.security.IISimpleEncryptor)
     */
    public Object unpackMessage(Class<?> msgObjClass, String signatureKey, String messageEncKey,
            ISimpleEncryptor encryptor) throws BaseException {
        verify(signatureKey);// 验证签名
        return unpackMessage(msgObjClass, messageEncKey, encryptor);// 解包
    }

    /**
     * 消息编码
     * 
     * @param messageObj
     *            原始消息对象
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @return 加密后的消息
     * @throws BaseException
     */
    private String encryptMessage(Object messageObj, String messageEncKey, ISimpleEncryptor encryptor)
            throws BaseException {
        String jsonMessage = Json.toJson(messageObj, JsonFormat.nice());// JSON格式的原始消息
        String msgEncAlg = getMessageEncAlg();
        if (Strings.isBlank(msgEncAlg)) {
            return jsonMessage;
        } else if (null != encryptor) {
            return encryptor.encrypt(jsonMessage);
        } else if (Encryptor.ENC_DES.equals(msgEncAlg) || Encryptor.ENC_DESede.equals(msgEncAlg)
                || Encryptor.ENC_Blowfish.equals(msgEncAlg)) {
            IEncryptor en = new Encryptor(getMessageEncAlg(), messageEncKey);
            return en.encrypt(jsonMessage);
        } else {
            throw new BaseException("e.message.encrypt.algorithm",
                    "Auto message encryptor is only support for DES,DESede or Blowfish.");
        }
    }

    /**
     * 消息解码
     * 
     * @param messageEncKey
     *            消息加密KEY
     * @param encryptor
     *            自定义的消息加解密器
     * @throws BaseException
     */
    private void decryptMessage(String messageEncKey, ISimpleEncryptor encryptor) throws BaseException {
        if (Strings.isBlank(getMessageEncAlg())) {
            return;
        } else if (null != encryptor) {
            this.message = encryptor.decrypt(message);
        } else if (Encryptor.ENC_DES.equals(messageEncAlg) || Encryptor.ENC_DESede.equals(messageEncAlg)
                || Encryptor.ENC_Blowfish.equals(messageEncAlg)) {
            IEncryptor en = new Encryptor(getMessageEncAlg(), messageEncKey);
            this.message = en.decrypt(message);
        } else {
            throw new BaseException("e.message.encrypt.algorithm",
                    "Auto message encryptor is only support for DES,DESede or Blowfish.");
        }
    }

    /**
     * 签名
     * 
     * @param m
     * @return String
     */
    private String digestHMAC(String m) {
        return Idigest.ALG_MD5.equals(getSignatureMethodAlgorithm()) ? MD5.digest(m) : SHA1.digest(m);
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#getMessage()
     */
    public String getMessage() {
        return message;
    }

    /**
     * @param message
     *            the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * @return the messageEncAlg
     */
    public String getMessageEncAlg() {
        return messageEncAlg;
    }

    /**
     * @param messageEncAlg
     *            the messageEncAlg to set
     * @throws BaseException
     */
    private void setMessageEncAlg(String messageEncAlg) throws BaseException {
        if (Strings.isBlank(messageEncAlg)) {
            this.messageEncAlg = null;
        } else {
            this.messageEncAlg = messageEncAlg;
        }
        // else if (Encryptor.ENC_DES.equals(messageEncAlg) || Encryptor.ENC_DESede.equals(messageEncAlg)
        // || Encryptor.ENC_Blowfish.equals(messageEncAlg)) {
        // this.messageEncAlg = messageEncAlg;
        // } else {
        // throw new BaseException("e.message.encryptor",
        // "Message encrypt is only support for DES,DESede or Blowfish.");
        // }
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#getTimeStamp()
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     * @return the signature
     */
    public Signature getSignature() {
        if (null == signature) {
            signature = new Signature();
        }
        return signature;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#getSignatureIdentity()
     */
    public String getSignatureIdentity() {
        return getSignature().getIdentity();
    }

    public void reSetTime() {
        this.timeStamp = Dates.formatDate(new Date());
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#getSignatureMethodAlgorithm()
     */
    public String getSignatureMethodAlgorithm() {
        return getSignature().getAlgorithm();
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.qc.security.mt.ISignatureMessage#getSignatureValue()
     */
    public String getSignatureValue() {
        return getSignature().getValue();
    }

}
