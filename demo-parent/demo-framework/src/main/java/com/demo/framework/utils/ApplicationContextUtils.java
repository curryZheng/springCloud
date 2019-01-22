package com.demo.framework.utils;

import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * spring上下文管理工具
 * 
 * @author leiZheng
 *
 *         2019年1月11日
 */
public class ApplicationContextUtils {

	private static ApplicationContext context;

	public static ApplicationContext getContext() {
		return context;
	}

	public static void setContext(ApplicationContext context) {
		ApplicationContextUtils.context = context;
	}

	/**
	 * 取得HttpServletResponse
	 * 
	 * @return
	 */
	public static HttpServletResponse getResponse() {
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getResponse();
		;
		return response;
	}

}
