package com.demo.common.util;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.demo.common.exception.DemoException;

/**
 * 类与类相互转换的工具类
 * 
 * @author Qiaoxin.Hong
 *
 */
public class BeanConvertUtils {
	
	/**
	 * 将类转换为另一个类
	 * @param from
	 * @param toClass
	 * @return
	 */
	public static <T> T convertBean(Object from, Class<T> toClass) {
		if (toClass == null)
			throw new DemoException("Class can't be empty!");
		
		if (from == null)
			return null;
		
		try {
			T to = toClass.newInstance();
			BeanCopyUtil.copyProperties(to, from);
			return to;
		} catch (Exception e) {
			throw new DemoException("Convert bean error！", e);
		}
	}
	
	/**
	 * 将一个类的list转换为另一个类的list
	 * @param fromList
	 * @param toClass
	 * @return
	 */
	public static <T> List<T> convertBeanList(List<?> fromList, Class<T> toClass) {
		if (toClass == null)
			throw new DemoException("Class can't be empty!");
		
		List<T> toList = new ArrayList<T>();
		
		if (CollectionUtil.isNotEmpty(fromList)) {
			for (Object from : fromList)
				toList.add(convertBean(from, toClass));
		}
		return toList;
	}
	
	/**
	 * 将一个类的set转换为另一个类的set
	 * @param fromSet
	 * @param toClass
	 * @return
	 */
	public static <T> Set<T> convertBeanSet(Set<?> fromSet, Class<T> toClass) {
		if (toClass == null)
			throw new DemoException("Class can't be empty!");
		
		Set<T> toList = new HashSet<T>();
		if (fromSet != null && !fromSet.isEmpty()) {
			for (Object from : fromSet)
				toList.add(convertBean(from, toClass));
		}
		return toList;
	}

	/**
	 * 初始化實體 BigDecimal 值為零
	 * @param obj
     */
	public static void initBigDecimal(Object obj) {
		try {
			Method[] methods  = obj.getClass().getMethods();
			for(Method m : methods){
				if(m.getName().startsWith("set")){
					Class[] types = m.getParameterTypes();
					if(types.length == 1 && "java.math.BigDecimal".equals(types[0].getName())){
						m.invoke(obj, BigDecimal.ZERO);
					}
				}
			}
		} catch (Exception e) {
			//不可能出的錯誤
			throw new DemoException(e.getMessage());
		}
	}

}
