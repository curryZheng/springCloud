package com.demo.common.constant;

/**
 * 枚举
 * @author leiZheng
 *
 *2019年1月15日
 */
public enum MsgEnum {
	
	UNKNOWN_ERROR("10015", "UNKNOWN_ERROR", "未知錯誤"),
	
	/** 操作異常 */
	FAILED_OPERATION("10013", "FAILED", "操作異常！"),
	
	/** 操作成功! */
	SUCCESS_OPERATION("0", "SUCCESS", "操作成功!"),
	
	/** 接口調用結果為空 */
	CALL_RESULT_IS_ENPTY("12003", "CALL_RESULT_IS_ENPTY", "接口調用結果為空");
	
	private String key;
	private String msg;
	private String code;

	/**
	 */
	private MsgEnum(String code, String key, String msg) {
		this.key = key;
		this.msg = msg;
		this.code = code;
	}

	public static MsgEnum findMsgEnumByKey(String key) {
		if (key == null) {
			return null;
		}

		for (MsgEnum msg : values()) {
			if (key.equals(msg.getKey())) {
				return msg;
			}
		}
		return null;
	}

	public static MsgEnum findMsgEnumByCode(String code) {
		if (code == null) {
			return null;
		}

		for (MsgEnum msg : values()) {
			if (code.equals(msg.getCode())) {
				return msg;
			}
		}
		return null;
	}

	public String getKey() {
		return key;
	}

	public String getMsg() {
		return msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
