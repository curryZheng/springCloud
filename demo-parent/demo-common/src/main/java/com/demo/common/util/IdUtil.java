package com.demo.common.util;

import java.util.UUID;

public class IdUtil {
	/**
	 * 系统生成不带“-”的UUID 作為表中code主鍵的值
	 * @return UUID
	 */
	public static String generateUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
	
	/**
	 * 生成6位随机数,作为短信验证码
	 */
	public static String generateSmsRandomCode()
	{
		return String.valueOf((int)((Math.random()*9+1)*100000));
	}
}
