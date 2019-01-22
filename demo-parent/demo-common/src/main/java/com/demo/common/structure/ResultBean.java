package com.demo.common.structure;

import java.util.Collection;

import com.demo.common.constant.MsgEnum;
import com.demo.common.exception.DemoServiceException;



/**
 * 控制器结果
 * 
 * @author xitao
 */
public class ResultBean<T> implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 154788359768857726L;
	/** 操作代码 - 成功 */
	public static final String CODE_SUCCESS = "0";

	private String resultCode;

	private String resultKey;

	private String resultMsg;

	private T data;

	/**
	 * 记录下MsgEnum的参数列表，便重新构造结果集使用
	 */
	private String[] msgEnumArgs;

	/**
	 * 构建一个默认成功的结果集（即resultCode == SUCCESS）
	 */
	public ResultBean() {
		setResultStatus(MsgEnum.SUCCESS_OPERATION);
	}

	/**
	 * 构建一个默认成功的结果集（即resultCode == SUCCESS），并封装数据
	 * 
	 * @param data
	 */
	public ResultBean(T data) {
		this();
		setData(data);
	}

	public void setResultStatus(MsgEnum en, String... str) {
		this.resultCode = en.getCode();
		this.resultKey = en.getKey();
		this.resultMsg = resolvePlaceholders(en.getMsg(), str);
		msgEnumArgs = str;
	}

	public String resolvePlaceholders(String msg, String... param) {
		if (param == null) {
			return msg;
		}
		for (int i = 0; i < param.length; i++) {
			msg = msg.replace("{" + (i + 1) + "}", param[i]);
		}
		return msg;
	}

	/**
	 * 创建一个默认失败的结果集
	 * 
	 * @return
	 */
	public static <T> ResultBean<T> createSuccessResult() {
		return new ResultBean<T>();
	}

	/**
	 * 创建一个默认失败的结果集
	 * 
	 * @return
	 */
	public static <T> ResultBean<T> createSuccessResult(T data) {
		return new ResultBean<T>(data);
	}

	/**
	 * 创建一个默认失败的结果集
	 * 
	 * @return
	 */
	public static <T> ResultBean<T> createFailedResult() {
		ResultBean<T> result = new ResultBean<T>();
		result.setResultStatus(MsgEnum.FAILED_OPERATION);
		return result;
	}

	/**
	 * 创建一个失败的结果集
	 * 
	 * @return
	 */
	public static <T> ResultBean<T> createFailedResult(MsgEnum msgEnum, String... str) {
		ResultBean<T> result = new ResultBean<T>();
		result.setResultStatus(msgEnum, str);
		return result;
	}

	/**
	 * 是否操作成功，即resultCode = SUCCESS
	 * 
	 * @return
	 */
	public boolean judgeSuccess() {
		return CODE_SUCCESS.equals(resultCode);
	}

	/**
	 * 是否操作成功，即resultCode = SUCCESS，且并对结果进行非空判断，失败则结果当做失败
	 * 
	 * @return
	 */
	public boolean judgeSuccessExists() {
		boolean result = judgeSuccess();
		if (result) {
			if (data == null) {
				result = false;
			} else {
				if (data instanceof Collection<?> && ((Collection<?>) data).isEmpty()) {
					result = false;
				}
			}
		}
		return result;
	}

	/**
	 * 是否操作成功，即resultCode != SUCCESS
	 * 
	 * @return
	 */
	public boolean judgeFailed() {
		return !judgeSuccess();
	}

	/**
	 * <pre>
	 * 用于在一个地方调用service的方法时，需对该service方法进行操作是否成功，及取数据时使用，目的再于简化调用的一个流程
	 * 操作失败即 resultCode != SUCCESS时，会抛出一个异常给调用的地方，该地方再对异常进行相应处理，
	 * 操作成功即 resultCode == SUCCESS时，直接返回结果数据
	 * </pre>
	 * 
	 * @return
	 */
	public T judgeDataService() {
		if (judgeFailed()) {
			throw new DemoServiceException(resultKey, msgEnumArgs);
		}
		return data;
	}

	/**
	 * <pre>
	 * 用于在一个地方调用service的方法时，需对该service方法进行操作是否成功，及取数据时使用，目的再于简化调用的一个流程，
	 * 并对结果进行非空判断
	 * 操作失败即 resultCode != SUCCESS时，会抛出一个异常给调用的地方，该地方再对异常进行相应处理，
	 * 操作成功即 resultCode == SUCCESS时，直接返回结果数据
	 * </pre>
	 * 
	 * @return
	 */
	public T judgeDataExists() {
		if (judgeFailed()) {
			throw new DemoServiceException(resultKey, msgEnumArgs);
		}
		if (data == null || (data instanceof Collection<?> && ((Collection<?>) data).isEmpty())) {
			throw new DemoServiceException(MsgEnum.CALL_RESULT_IS_ENPTY);
		}
		return data;
	}

	public String getResultCode() {
		return resultCode;
	}

	public String getResultMsg() {
		return resultMsg;
	}

	public void setData(T data) {
		this.data = data;
	}

	public T getData() {
		return data;
	}

	public String getResultKey() {
		return resultKey;
	}

	public void setResultKey(String resultKey) {
		this.resultKey = resultKey;
	}

	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}

	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}

	@Override
	public String toString() {
		return "ResultBean [resultCode=" + resultCode + ", resultMsg=" + resultMsg + "]";
	}

}
