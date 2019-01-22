package com.demo.framework.aspectj;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.demo.common.constant.BConst;
import com.demo.common.constant.MsgEnum;
import com.demo.common.exception.DemoServiceException;
import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.common.util.IdUtil;
import com.demo.common.util.StringUtil;


/**
 * 控制器层拦截器
 * @author leiZheng
 *
 *2019年1月17日
 */
@Aspect
@Configuration
public class ControllerInterceptorConfig {

	private final static Logger logger = LoggerFactory.getLogger(ControllerInterceptorConfig.class);
	
	/**
	 * spring上下文
	 */
	@Autowired
	protected ApplicationContext applicationContext;
	
	//定义切点 控制器
	@Pointcut("execution(* com.demo.*.controller.*Controller*.*(..))")
	public void executeService() {
		
	}
	
	@Around("executeService()") //拦截点
	public Object doAround(ProceedingJoinPoint pjp) throws Throwable {

		Signature signature = pjp.getSignature();
		RequestAttributes ra = RequestContextHolder.getRequestAttributes();
		ServletRequestAttributes sra = (ServletRequestAttributes) ra;
		HttpServletRequest request = sra.getRequest();
		long currTime = System.currentTimeMillis();
		String sId = IdUtil.generateUUID();
		String name = StringUtil.defaultString(signature.getDeclaringTypeName()) + BConst.PERIOD
				+ StringUtil.defaultString(signature.getName());
		
		logger.info("[sid : {}] begin call controller [method : {}] [ip : {}] [url : {}] [uri : {}]", sId, name
				, request.getRemoteAddr(), request.getRequestURL(), request.getRequestURI());
		
//		if (pjp.getArgs() != null) {
//			for (Object obj : pjp.getArgs()) {
//				if (obj != null) {
//					TimeZoneHandler.paramHandler(obj);
//				}
//			}
//		}
		
		Object result = null;
		
		try {			
			// result的值就是被拦截方法的返回值
			result = pjp.proceed();
			
			if (result != null && result instanceof ResultBean) {
				Object data = ((ResultBean) result).getData();
			
			}
		} catch (Exception e) {
			long callTime = System.currentTimeMillis() - currTime;
			logger.error("[sid : {}] end call controller [method : {}] [callTime : {}] [ip : {}] [url : {}] [uri : {}]", sId, name
					, callTime, request.getRemoteAddr(), request.getRequestURL(), request.getRequestURI());
			
			return controllerExceptionHandle(e, pjp);
		} finally {
			//RequestHead.resetRequestHead();
		}
		
		long callTime = System.currentTimeMillis() - currTime;
		logger.info("[sid : {}] end call controller [method : {}] [callTime : {}] [ip : {}] [url : {}] [uri : {}]", sId, name
				, callTime, request.getRemoteAddr(), request.getRequestURL(), request.getRequestURI());

		return result;
	}

	/**
	 * 返
	 * @param e
	 * @param pjp
	 * @return
	 */
	private Object controllerExceptionHandle(Exception e, ProceedingJoinPoint point) {
		
		ResultBean<?> resultBean = null;
		String msgKey = e.getMessage();

		// 生成不同的结果集对象
		Method method = ((MethodSignature) point.getSignature()).getMethod();
		if (ResultBean.class.equals(method.getReturnType())) {
			resultBean = new ResultBean<Object>();
		} else if (ResultPageBean.class.equals(method.getReturnType())) {
			resultBean = new ResultPageBean<Object>();
		} else {
			return null;
		}

		// 如果是TmsServiceException则可直接取MsgEnum和MsgArgs
		if (e instanceof DemoServiceException) {
			DemoServiceException tse = (DemoServiceException) e;
			resultBean.setResultStatus(tse.getMsgEnum(), tse.getMsgArgs());
		} else {
			// 生成MsgEnum
			MsgEnum msgEnum = null;
			if (StringUtil.isEmpty(msgKey)) {
				msgEnum = MsgEnum.UNKNOWN_ERROR;
			} else {
				msgEnum = MsgEnum.findMsgEnumByKey(msgKey);
				if (msgEnum == null) {
					msgEnum = MsgEnum.UNKNOWN_ERROR;
				}
			}
			resultBean.setResultStatus(msgEnum);
		}
		return resultBean;
	}
	
}
