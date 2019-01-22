package com.demo.common.exception;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.demo.common.constant.MsgEnum;

/**
 * demoServer异常
 * 
 * @author leiZheng
 *
 *         2019年1月15日
 */
public class DemoServiceException  extends DemoException implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * msg枚举值
	 */
	private MsgEnum msgEnum;
	
	/**
	 * msg占位符所对应的参数列表
	 */
	private String[] msgArgs;

	public DemoServiceException() {
		super();
		setMsgEnum(getMessage());
	}

	public DemoServiceException(MsgEnum msgEnum) {
		super(msgEnum);
		this.msgEnum = msgEnum;
	}
	
	public DemoServiceException(MsgEnum msgEnum, String...msgArgs) {
		super(msgEnum.getKey());
		this.msgEnum = msgEnum;
		this.msgArgs = msgArgs;
	}
	
	public DemoServiceException(MsgEnum msgEnum, Throwable cause, String...msgArgs) {
		super(msgEnum, cause);
		this.msgEnum = msgEnum;
		this.msgArgs = msgArgs;
		
	}

	public DemoServiceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
		setMsgEnum(message);
	}

	public DemoServiceException(String message, Throwable cause) {
		super(message, cause);
		setMsgEnum(message);
	}
	
	public DemoServiceException(String message, String...msgArgs) {
		super(message);
		setMsgEnum(message);
		this.msgArgs = msgArgs;
	}
	
	public DemoServiceException(String message, Throwable cause, String...msgArgs) {
		super(message, cause);
		setMsgEnum(message);
		this.msgArgs = msgArgs;
	}

	public DemoServiceException(String message) {
		super(message);
		setMsgEnum(message);
	}

	public DemoServiceException(Throwable cause) {
		super(cause);
		setMsgEnum(getMessage());
	}
	
	/**
	 * 由msgKey生成MsgEnum枚举值
	 * @param msgKey
	 */
	protected void setMsgEnum(String msgKey) {
		if (StringUtils.isBlank(msgKey))
			this.msgEnum = MsgEnum.UNKNOWN_ERROR;
		else {
			this.msgEnum = MsgEnum.findMsgEnumByKey(msgKey);
			if (this.msgEnum == null) {
				this.msgEnum = MsgEnum.UNKNOWN_ERROR;
			}
		}
	}
	
	public String[] getMsgArgs() {
		return msgArgs;
	}
	
	public MsgEnum getMsgEnum() {
		return msgEnum;
	}

}
