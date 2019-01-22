package com.demo.framework.utils;

import javax.servlet.ServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.JSONSerializer;
import com.alibaba.fastjson.serializer.PropertyPreFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * 对象转换为jons，偏向于打印日志使用
 * 
 * @author Qiaoxin.Hong
 *
 */
public class JsonLogUtil {
	
	/**
	 * 过滤器，对象转换为jons，偏向于打印日志使用
	 */
	private static final SerializeFilter[] jsonLogFilters = {new PropertyPreFilter() {

		@Override
		public boolean apply(JSONSerializer serializer, Object object, String name) {
			if (object != null) {
				if (object instanceof ServletResponse) {
					return false;
				}
        	}
            return true;  
		}
    }};
	
	/**
	 * 对象转换为jons，偏向于打印日志使用
	 * @param obj
	 * @return
	 */
	public static String toJsonLog(Object obj) {
		String json = JSON.toJSONString(obj, SerializeConfig.globalInstance, jsonLogFilters, "yyyy-MM-dd HH:mm:ss.SSS"
				, JSON.DEFAULT_GENERATE_FEATURE
				, SerializerFeature.DisableCircularReferenceDetect);
		return json;
	}
}
