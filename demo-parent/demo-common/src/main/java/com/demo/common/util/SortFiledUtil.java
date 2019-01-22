package com.demo.common.util;

import java.lang.reflect.Field;
import java.util.List;

import com.demo.common.annotation.SortField;
import com.demo.common.constant.BConst;
import com.demo.common.exception.DemoException;
import com.demo.common.structure.RequestPageOrderByReq;


/**
 * sql排序工具类
 * 
 * @author cxjy02
 *
 */
public final class SortFiledUtil {
	
	/**
	 * sql排序解析
	 * @param clazz
	 * @param orderByJson
	 * @return
	 */
	public static String sortFiledParse(Class<?> clazz, List<RequestPageOrderByReq> orderByList) {
		if (clazz == null || CollectionUtil.isEmpty(orderByList)) {
			return BConst.EMPTY;
		}
		try {
			//解析order by json
			StringBuilder sb = new StringBuilder();
			
			for (RequestPageOrderByReq dto : orderByList) {
				if (StringUtil.isBlank(dto.getField())) {
					continue;
				}

				//取字段注解的表字段名称
				Field field = clazz.getDeclaredField(dto.getField());
				if (field.isAnnotationPresent(SortField.class)) {
					SortField sortField = field.getAnnotation(SortField.class);
					String tableColumn = sortField.value();
					if (StringUtil.isBlank(tableColumn)) {
						continue;
					}

					if (sb.length() != 0) {
						sb.append(BConst.COMMA);
					} 
					sb.append(BConst.BLANK).append(tableColumn).append(BConst.BLANK).append(dto.getOrder());
				}
			}
			
			return sb.toString();
		} catch (Exception e) {
			throw new DemoException("sort filed parse", e);
		}
		
		
		//[{"filed" : "", "order" : "desc"}]
		
	}

}
