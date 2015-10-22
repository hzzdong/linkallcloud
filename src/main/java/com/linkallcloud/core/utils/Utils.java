package com.linkallcloud.core.utils;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;

import org.nutz.lang.Strings;
import org.springframework.util.Assert;

/**
 * @author zhoudong(hzzdong@gmail.com)
 * 
 */
public class Utils {
    private static final String ASSERT_NULL_FORMAT = "参数[%s]不能为空!";

    /**
     * 验证参数是否为NULL
     * 
     * @param parameter
     * @param parameterName
     */
    public static void assertNotNull(Object parameter, String parameterName) {
        Assert.notNull(parameter, String.format(ASSERT_NULL_FORMAT, parameterName));
    }

    /**
     * 验证参数是否为空
     * 
     * @param objArray
     * @param objArrayName
     */
    public static void assertNotEmpty(Object[] objArray, String objArrayName) {
        Assert.notEmpty(objArray, String.format(ASSERT_NULL_FORMAT, objArrayName));
    }

    /**
     * 验证参数是否为空
     * 
     * @param collection
     * @param collectionName
     */
    public static void assertNotEmpty(Collection<?> collection, String collectionName) {
        Assert.notEmpty(collection, String.format(ASSERT_NULL_FORMAT, collectionName));
    }

    /**
     * 往StringBuffer中append，前后是否加入空格
     * 
     * @param buffer
     * @param cs
     * @param headWhiteSpace
     * @param tailWhiteSpace
     */
    public static void append(StringBuffer buffer, CharSequence cs) {
        append(buffer, cs, false, false);
    }

    /**
     * 往StringBuffer中append，前后是否加入空格
     * 
     * @param buffer
     * @param cs
     * @param headWhiteSpace
     * @param tailWhiteSpace
     */
    public static void append(StringBuffer buffer, CharSequence cs, boolean headWhiteSpace, boolean tailWhiteSpace) {
        if (null != buffer) {
            if (headWhiteSpace) {
                buffer.append(IConstants.WHITE_SPACE);
            }
            buffer.append(cs);
            if (tailWhiteSpace) {
                buffer.append(IConstants.WHITE_SPACE);
            }
        }
    }

    /**
     * 往StringBuffer中append，前后是否加入空格
     * 
     * @param buffer
     * @param cs
     * @param headWhiteSpace
     * @param tailWhiteSpace
     */
    public static void append(StringBuffer buffer, char cs) {
        append(buffer, cs, false, false);
    }

    /**
     * 往StringBuffer中append，前后是否加入空格
     * 
     * @param buffer
     * @param cs
     * @param headWhiteSpace
     * @param tailWhiteSpace
     */
    public static void append(StringBuffer buffer, char cs, boolean headWhiteSpace, boolean tailWhiteSpace) {
        if (null != buffer) {
            if (headWhiteSpace) {
                buffer.append(IConstants.WHITE_SPACE);
            }
            buffer.append(cs);
            if (tailWhiteSpace) {
                buffer.append(IConstants.WHITE_SPACE);
            }
        }
    }

    /**
     * Returns a printable String corresponding to a byte array.
     * 
     * @param b
     * @return rand String
     */
    public static synchronized String printable(byte[] b) {
        final char[] alphabet =
                (IConstants.SMALL_LETTERS + IConstants.CAPITAL_LETTERS + IConstants.NUMBERS).toCharArray();
        char[] out = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            int index = b[i] % alphabet.length;
            if (index < 0) {
                index += alphabet.length;
            }
            out[i] = alphabet[index];
        }
        return new String(out);
    }

    /**
     * Returns a printable String corresponding to a byte array.
     * 
     * @param b
     * @return number string
     */
    public static synchronized String printableNumber(byte[] b) {
        final char[] alphabet = IConstants.NUMBERS.toCharArray();
        char[] out = new char[b.length];
        for (int i = 0; i < b.length; i++) {
            int index = b[i] % alphabet.length;
            if (index < 0) {
                index += alphabet.length;
            }
            out[i] = alphabet[index];
        }
        return new String(out);
    }

    /**
     * get rand id
     * 
     * @param length
     * @return random id
     */
    public static String getRandomID(int length) {
        // produce the random transaction ID
        byte[] b = new byte[length];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(b);
        return Utils.printable(b);
    }

    /**
     * get numberic rand id
     * 
     * @param length
     * @return random id
     */
    public static String getNumericRandomID(int length) {
        // produce the random transaction ID
        byte[] b = new byte[length];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(b);
        return Utils.printableNumber(b);
    }

    /**
     * 
     * @param ipStr
     * @param size
     * @return ip value
     */
    public static String parse2ipVal(String ipStr, int size) {
        StringBuffer sbf = new StringBuffer();
        String[] ipArr = ipStr.replace(".", String.valueOf(IConstants.COLON)).split(String.valueOf(IConstants.COLON));
        for (String str : ipArr) {
            int length = size - str.length();
            for (int i = 0; i < length; i++) {
                sbf.append('0');
            }
            sbf.append(str);
        }
        return sbf.toString();
    }

    /**
     * Considers an Object array passed into a varargs parameter as collection of arguments rather than as single
     * argument.
     * 
     * @param varArgs
     * @return Object[]
     */
    public static Object[] getArguments(Object[] varArgs) {
        if (varArgs.length == 1 && varArgs[0] instanceof Object[]) {
            return (Object[]) varArgs[0];
        } else {
            return varArgs;
        }
    }

    /**
     * 如果uuid为空或者格式无效，则返回空
     * 
     * @param uuid
     * @return
     */
    public static UUID getUUIDFromString(String uuid) {
        UUID uuidObj = null;
        if (!Strings.isBlank(uuid)) {
            try {
                uuidObj = UUID.fromString(uuid);
            } catch (Exception e) {
            }
        }
        return uuidObj;
    }

    public static Long[] parseIds(Map<String, Long> uuidIds) {
        Long[] ids = new Long[uuidIds.size()];
        int i = 0;
        for (String uuid : uuidIds.keySet()) {
            if (null != Utils.getUUIDFromString(uuid)) {// 过滤格式不正确的UUID对应的ID
                ids[i++] = uuidIds.get(uuid);
            }
        }
        return ids;
    }
}
