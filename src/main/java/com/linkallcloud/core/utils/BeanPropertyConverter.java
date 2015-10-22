/**
 * Copyright (c) 2010 www.public.zj.cn
 *
 * com.qc.lang.util.BeanUtilNumberConverter.java 
 * Created at 2010-5-21 by zhoudong(hzzdong@gmail.com)
 * 
 */
package com.linkallcloud.core.utils;

import org.apache.commons.beanutils.Converter;

/**
 * @author zhoudong(hzzdong@gmail.com)
 * 
 */
public class BeanPropertyConverter implements Converter {
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.apache.commons.beanutils.Converter#convert(java.lang.Class,
	 * java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	public Object convert(Class arg0, Object arg1) {
		if (arg1 == null) {
			return null;
		} else if (arg1 instanceof String && "".equals((String) arg1)) {
			return null;
		} else {
			return arg1;
		}
	}
}
