package com.demo.framework.aspectj;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import com.demo.common.constant.BConst;
import com.demo.common.constant.MsgEnum;
import com.demo.common.exception.DemoServiceException;
import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.common.util.IdUtil;
import com.demo.common.util.StringUtil;
import com.demo.framework.utils.JsonLogUtil;

/**
 * service层拦截器
 * 
 * @author Qiaoxin.Hong
 *
 */
@Configuration
@Aspect
@Order(999)
public class ServiceInterceptorConfig {

	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 切点
	 */
	@Pointcut("execution(* com.demo.*.services.impl.*.*(..))")
	public void executeService() {
	}

	/**
	 * 环绕通知
	 * 
	 * @param pjp
	 * @return
	 * @throws Throwable
	 */
	@Around("executeService()")
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {
		long currTime = System.currentTimeMillis();

		Signature signature = pjp.getSignature();
		String sId = IdUtil.generateUUID();
		String name = StringUtil.defaultString(signature.getDeclaringTypeName()) + BConst.PERIOD
				+ StringUtil.defaultString(signature.getName());

		Object result = null;
		boolean isError = false;
		Exception exception = null;

		// 入参转json
		String argsJson = JsonLogUtil.toJsonLog(pjp.getArgs());
		logger.info("[sid : {}] begin call service [method : {}] [args : {}]", sId, name, argsJson);
		try {

			// 执行方法
			result = pjp.proceed();
		} catch (Exception e) {
			exception = e;
			isError = true;
			result = serviceExceptionHandle(e, pjp);
		}

		// 断定service方法请求的结果
		isError = judgeCall(pjp, result, isError);

		long callTime = System.currentTimeMillis() - currTime;
		// 请求失败
		if (isError) {
			logger.error("[sid : {}] end call service [method : {}] [callTime : {}]", sId, name, callTime, exception);
		} else { // 请求成功
			logger.info("[sid : {}] end call service [method : {}] [callTime : {}]", sId, name, callTime);
		}

		return result;
	}

	/**
	 * 断定service方法请求的结果
	 * 
	 * @param point
	 * @param result
	 * @param isError
	 *            是否已经错误了
	 */
	private boolean judgeCall(ProceedingJoinPoint point, Object result, boolean isError) {
		// 非已出现异常，则进行ResultBean结果是否成功的判断
		if (result != null && !isError) {
			if (result instanceof ResultBean<?>) {
				ResultBean<?> resultBean = (ResultBean<?>) result;
				// ResultBean结果为失败
				if (resultBean.judgeFailed()) {
					isError = false;
				}
			}
		}

		return isError;
	}

	/**
	 * 主要作用于service，可对service抛出的异常进行处理，最终还是会返回一个结果集对象，保证外部调用不会得到一些异常信息，
	 * 并可放开service的异常捕捉，来实现事务的自动回滚
	 * 
	 * @param e
	 * @param point
	 * @return
	 */
	private Object serviceExceptionHandle(Exception e, ProceedingJoinPoint point) {
		ResultBean<?> resultBean = null;
		String msgKey = e.getMessage();

		// 生成不同的结果集对象
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		if (ResultBean.class.equals(method.getReturnType()))
			resultBean = new ResultBean<Object>();
		else if (ResultPageBean.class.equals(method.getReturnType()))
			resultBean = new ResultPageBean<Object>();
		else
			return null;

		// 如果是TmsServiceException则可直接取MsgEnum和MsgArgs
		if (e instanceof DemoServiceException) {
			DemoServiceException tse = (DemoServiceException) e;
			resultBean.setResultStatus(tse.getMsgEnum(), tse.getMsgArgs());
		} else {
			// 生成MsgEnum
			MsgEnum msgEnum = null;
			if (StringUtils.isBlank(msgKey))
				msgEnum = MsgEnum.UNKNOWN_ERROR;
			else {
				msgEnum = MsgEnum.findMsgEnumByKey(msgKey);
				if (msgEnum == null)
					msgEnum = MsgEnum.UNKNOWN_ERROR;
			}
			resultBean.setResultStatus(msgEnum);
		}
		return resultBean;
	}

}
