package com.linkallcloud.core.exception;

import javax.servlet.ServletException;

import com.linkallcloud.core.utils.ResBundle;

public class BaseServletException extends ServletException {
	private static final long serialVersionUID = 978522466775303394L;

	private String code;

	public BaseServletException() {
		super();
	}

	public BaseServletException(String code) {
		super(ResBundle.getMessage(code));
		this.code = code;
	}

	public BaseServletException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BaseServletException(String code, Throwable rootCause) {
		super(ResBundle.getMessage(code), rootCause);
		this.code = code;
	}

	public BaseServletException(String code, String message, Throwable rootCause) {
		super(message, rootCause);
		this.code = code;
	}

	public BaseServletException(Throwable rootCause) {
		super(rootCause);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

}
