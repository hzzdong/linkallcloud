package com.linkallcloud.core.exception;

import com.linkallcloud.core.utils.ResBundle;

public class BaseRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 609281876732234454L;
	public static final String RUNTIME_EXCEPTION = "e.u";

	private String code;

	public BaseRuntimeException() {
		super(ResBundle.getMessage(RUNTIME_EXCEPTION));
		this.code = RUNTIME_EXCEPTION;
	}

	public BaseRuntimeException(String code) {
		super(ResBundle.getMessage(code));
		this.code = code;
	}

	public BaseRuntimeException(String code, String message) {
		super(message);
		this.code = code;
	}

	public BaseRuntimeException(String code, Throwable cause) {
		super(ResBundle.getMessage(code), cause);
		this.code = code;
	}

	public BaseRuntimeException(String code, String message, Throwable cause) {
		super(message, cause);
		this.code = code;
	}

	public BaseRuntimeException(Throwable cause) {
		super(ResBundle.getMessage(RUNTIME_EXCEPTION), cause);
		this.code = RUNTIME_EXCEPTION;
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
