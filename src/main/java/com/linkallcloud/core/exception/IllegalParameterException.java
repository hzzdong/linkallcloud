package com.linkallcloud.core.exception;

import com.linkallcloud.core.utils.ResBundle;

public class IllegalParameterException extends BaseException {
	private static final long serialVersionUID = 4734450290170172472L;

	public static final String PARAMETER_EXCEPTION = "e.parameter";

	public IllegalParameterException() {
		super(PARAMETER_EXCEPTION, ResBundle.getMessage(PARAMETER_EXCEPTION));
	}

	public IllegalParameterException(String code) {
		super(code);
	}

	public IllegalParameterException(String code, String message) {
		super(code, message);
	}

	public IllegalParameterException(String code, String message,
			Throwable cause) {
		super(code, message, cause);
	}

	public IllegalParameterException(String code, Throwable cause) {
		super(code, cause);
	}

	public IllegalParameterException(Throwable cause) {
		super(ResBundle.getMessage(PARAMETER_EXCEPTION), cause);
		super.setCode(PARAMETER_EXCEPTION);
	}

}
