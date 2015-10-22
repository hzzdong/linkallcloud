package com.linkallcloud.core.exception;

import com.linkallcloud.core.utils.ResBundle;

public class IllegalParameterRuntimeException extends BaseRuntimeException {
	private static final long serialVersionUID = 9064029361633903337L;

	public static final String PARAMETER_RUNTIME_EXCEPTION = "e.u.parameter";

	public IllegalParameterRuntimeException() {
		super(PARAMETER_RUNTIME_EXCEPTION, ResBundle
				.getMessage(PARAMETER_RUNTIME_EXCEPTION));
	}

	public IllegalParameterRuntimeException(String code) {
		super(code);
	}

	public IllegalParameterRuntimeException(String code, String message) {
		super(code, message);
	}

	public IllegalParameterRuntimeException(String code, Throwable cause) {
		super(code, cause);
	}

	public IllegalParameterRuntimeException(String code, String message,
			Throwable cause) {
		super(code, message, cause);
	}

	public IllegalParameterRuntimeException(Throwable cause) {
		super(ResBundle.getMessage(PARAMETER_RUNTIME_EXCEPTION), cause);
		super.setCode(PARAMETER_RUNTIME_EXCEPTION);
	}

}
