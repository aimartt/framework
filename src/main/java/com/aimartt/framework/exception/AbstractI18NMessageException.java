package com.aimartt.framework.exception;

/**
 * 异常抽象类,将错误信息国际化
 * @author pu.zhang
 */
public abstract class AbstractI18NMessageException extends RuntimeException {
	
	private static final long serialVersionUID = 2148374270769534530L;

	static final int DEFAULT_ERROR_CODE = 0;

	/** 错误代码,默认为未知错误 */
	private int errorCode;
	/** 调试信息 */
	protected String errorMessage;

	public AbstractI18NMessageException(String errorMessage) {
		this(errorMessage, null);
	}

	public AbstractI18NMessageException(int errorCode, String errorMessage) {
		this(errorCode, errorMessage, null);
	}

	public AbstractI18NMessageException(String errorMessage, Throwable cause) {
		this(DEFAULT_ERROR_CODE, errorMessage, cause);
	}

	public AbstractI18NMessageException(int errorCode, Throwable cause) {
		this(errorCode, null, cause);
	}

	public AbstractI18NMessageException(int errorCode, String errorMessage, Throwable cause) {
		super(errorMessage, cause);
		this.errorCode = errorCode;
	}

	public int getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(int errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

}