package com.linkallcloud.core.exception;

import com.linkallcloud.core.utils.ResBundle;

public class DataBaseException extends BaseException {
	private static final long serialVersionUID = 488374255363624256L;

	public static final String DATABASE_EXCEPTION = "e.database";

	public DataBaseException() {
		super(DATABASE_EXCEPTION, ResBundle.getMessage(DATABASE_EXCEPTION));
	}

	public DataBaseException(String code, Throwable cause) {
		super(code, cause);
	}

	public DataBaseException(String code, String message, Throwable cause) {
		super(code, message, cause);
	}

	public DataBaseException(String code) {
		super(code);
	}

	public DataBaseException(String code, String message) {
		super(code, message);
	}

	public DataBaseException(Throwable cause) {
		super(ResBundle.getMessage(DATABASE_EXCEPTION), cause);
		super.setCode(DATABASE_EXCEPTION);
	}

}
