/**
 * Copyright (c) 2010 www.public.zj.cn
 *
 * com.qc.util.NumberPropertyConverter.java 
 * 2010-8-25 zhoudong(hzzdong@gmail.com)
 * 
 */
package com.linkallcloud.core.utils;

/**
 * @author zhoudong(hzzdong@gmail.com)
 * 
 */
public class NumberPropertyConverter extends BeanPropertyConverter {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.qc.util.BeanPropertyConverter#convert(java.lang.Class,
	 * java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
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
