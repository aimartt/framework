package com.aimartt.framework.exception;

/**
 * 一般业务异常（非事务异常）
 * @author pu.zhang
 */
public class GeneralException extends AbstractI18NMessageException {

	private static final long serialVersionUID = -6910756266634262775L;

	public GeneralException(int errorCode, String errorMessage, Throwable cause) {
		super(errorCode, errorMessage, cause);
	}

	public GeneralException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public GeneralException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public GeneralException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public GeneralException(String errorMessage) {
		super(errorMessage);
	}

}