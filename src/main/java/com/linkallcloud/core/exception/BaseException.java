package com.linkallcloud.core.exception;

import com.linkallcloud.core.utils.ResBundle;

public class BaseException extends Exception {
	private static final long serialVersionUID = 1478755518958782626L;

	private String code;

	public BaseException() {
		super();
	}

	public BaseException(String code) {
		super(ResBundle.getMessage(code));
		this.code = code;
	}

	public BaseException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BaseException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BaseException(String code, Throwable cause) {
		super(ResBundle.getMessage(code), cause);
		this.code = code;
	}

	public BaseException(Throwable cause) {
		super(cause);
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
