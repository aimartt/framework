package com.aimartt.framework.exception;

/**
 * 标记事务可回滚的业务异常,配合声明式事务使用
 * 业务系统可以根据业务需求，继承该类定义具体业务相关的业务。如：NoFoundException, ParameterInvaidException 等。
 * @author pu.zhang
 */
public class BusinessException extends AbstractI18NMessageException {

	private static final long serialVersionUID = -9018571104185955115L;

	public BusinessException(int errorCode, String errorMessage) {
		super(errorCode, errorMessage);
	}

	public BusinessException(int errorCode, String errorMessage, Throwable cause) {
		super(errorCode, errorMessage, cause);
	}

	public BusinessException(int errorCode, Throwable cause) {
		super(errorCode, cause);
	}

	public BusinessException(String errorMessage, Throwable cause) {
		super(errorMessage, cause);
	}

	public BusinessException(String errorMessage) {
		super(errorMessage);
	}

}