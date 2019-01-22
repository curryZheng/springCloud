package com.demo.common.exception;

import com.demo.common.constant.MsgEnum;

public class DemoException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DemoException() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public DemoException(MsgEnum msgEnum) {
		super(msgEnum.getKey());
	}
	
	public DemoException(MsgEnum msgEnum, Throwable cause) {
		super(msgEnum.getKey(), cause);
	}

	public DemoException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		// TODO Auto-generated constructor stub
	}

	public DemoException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	public DemoException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	public DemoException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	
}
