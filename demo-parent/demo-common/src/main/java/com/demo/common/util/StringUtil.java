package com.demo.common.util;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.demo.common.exception.DemoException;

public class StringUtil extends StringUtils {

	/**
	 * 默认分隔符英文逗号 ,
	 */
	public static final String defaultSeparator = ",";

	/**
	 * 验证字符串是否有效 空为无效
	 * 
	 * @param str
	 * @return 有效为ture 无效为false
	 */
	public static boolean isValidString(String str) {
		return null != str && 0 < str.trim().length();
	}

	/**
	 * 将集合中的字符串按照指定分隔符划分返回字符串
	 * 
	 * @param list
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String listToString(List<String> list, String separator) {
		if (!isValidString(separator))
			separator = defaultSeparator;
		StringBuilder sb = new StringBuilder();
		for (String str : list) {
			sb.append(str).append(separator);
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1); // 去除最后的分隔符
		}
		return sb.toString();
	}

	/**
	 * 将集合中的字符串按照指定分隔符划分返回字符串
	 * 
	 * @param list
	 * @return
	 */
	public static String listToString(List<Long> list) {
		StringBuilder sb = new StringBuilder();
		for (Long str : list) {
			sb.append(str).append(defaultSeparator);
		}
		if (sb.length() > 0) {
			sb.setLength(sb.length() - 1); // 去除最后的分隔符
		}
		return sb.toString();
	}

	/**
	 * 将list转为String 格式如： 'a','b','c','d'
	 * 
	 * @param sqlList
	 * @return
	 */
	public static String listToSQLString(Collection<String> sqlList) {
		if (null != sqlList && sqlList.size() > 0) {
			StringBuffer idLst = new StringBuffer("'");
			for (String obj : sqlList) {
				idLst.append(obj).append("','");
			}
			if (idLst.length() > 0) {
				idLst = idLst.deleteCharAt(idLst.length() - 1);
				idLst = idLst.deleteCharAt(idLst.length() - 1);
			}
			return idLst.toString();
		}
		return null;
	}

	/**
	 * 将string转换为List<Long>
	 * 
	 * @param str
	 * @return
	 */
	public static List<Long> stringToLongList(String str) {
		List<Long> ids = new ArrayList<Long>();
		String[] idsStr = str.split(defaultSeparator);
		for (String idStr : idsStr) {
			ids.add(Long.parseLong(idStr));
		}
		return ids;
	}

	

	/**
	 * 
	 * @param value
	 */
	public static String htmlEncode(String value) {
		if (value == null) {
			return null;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < value.length(); i++) {
			char c = value.charAt(i);
			switch (c) {
			case '<':
				buffer.append("&lt;");
				break;
			case '>':
				buffer.append("&gt;");
				break;
			case '&':
				buffer.append("&amp;");
				break;
			case '"':
				buffer.append("&quot;");
				break;
			case 10:
				break;
			case 13:
				break;
			default:
				buffer.append(c);
			}
		}
		value = buffer.toString();
		return value;
	}

	/**
	 * 用于oracle用in语句查询时，将list参数分组 1组输出格式：1,2,3,4 大于1组，输出格式 ：1,2) or in (3,4
	 * 
	 * @param ids
	 * @param count
	 *            每组的个数 最大1000
	 * @return
	 */
	public static String getOracleSQLIn(List<Long> ids, int count, String filed) {
		count = Math.min(count, 1000);
		int len = ids.size();
		int size = len % count;
		if (size == 0) {
			size = len / count;
		} else {
			size = (len / count) + 1;
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int fromIndex = i * count;
			int toIndex = Math.min(fromIndex + count, len);
			List<Long> tempList = ids.subList(fromIndex, toIndex);
			for (Long idStr : tempList) {
				sb.append(idStr).append(",");
			}
			if (sb.length() > 0) {
				sb.setLength(sb.length() - 1); // 去除最后的分隔符
			}
			if (i == 0 && (size - 1) > 0) {
				sb.append(")");
			}
			if (i != 0 && i != (size - 1)) {
				sb.append(")");
			}
			if (i == 0 && (size - 1) > 0) {
				sb.append(" or " + filed + " in (");
			}
			if (i != 0 && i != (size - 1)) {
				sb.append(" or " + filed + " in (");
			}
		}
		return sb.toString();
	}

	/**
	 * 是否有一个字符串不为空，只有有一个就为true
	 * 
	 * @param strArr
	 * @return
	 */
	public static boolean isOneNotBlank(CharSequence... strArr) {
		if (strArr == null || strArr.length == 0)
			return false;
		for (CharSequence str : strArr) {
			if (isNotBlank(str))
				return true;
		}
		return false;
	}

	/**
	 * 是否有一个字符串为空，只要有一个就为true
	 * 
	 * @param strArr
	 * @return
	 */
	public static boolean isOneBlank(CharSequence... strArr) {
		if (strArr == null || strArr.length == 0)
			return true;
		for (CharSequence str : strArr) {
			if (isBlank(str))
				return true;
		}
		return false;
	}

	/**
	 * 以分割符拼接字符串
	 * 
	 * @param colls
	 *            对象集合
	 * @param field
	 *            提取的字段名
	 * @param separator
	 *            分割符
	 * @return
	 */
	public static String join(Collection<?> colls, String field, String separator) {
		if (CollectionUtil.isEmpty(colls))
			return EMPTY;

		if (isBlank(field))
			throw new DemoException("Field not empty!");
		if (isBlank(separator))
			throw new DemoException("Separator not empty!");

		try {
			Method method = null;
			StringBuilder sb = null;
			String val = null;
			for (Object entity : colls) {
				if (entity == null) {
					val = EMPTY;
				} else {
					if (method == null) {
						PropertyDescriptor pd = new PropertyDescriptor(field, entity.getClass());
						method = pd.getReadMethod();
						if (!String.class.equals(method.getReturnType()))
							throw new DemoException("Return type not string!");
					}
					val = (String) method.invoke(entity);
				}
				if (sb == null)
					sb = new StringBuilder(val);
				else
					sb.append(separator + val);
			}
			return sb.toString();
		} catch (Exception e) {
			throw new DemoException("Collection for join string error!", e);
		}
	}

	/**
	 * 从某集合提取其中非blank的某字段集合
	 * 
	 * @param colls
	 * @param field
	 * @return
	 */
	public static List<String> extractStrNotBlank(Collection<?> colls, String field) {
		if (isBlank(field))
			throw new IllegalArgumentException("Field not blank！");

		List<String> list = new ArrayList<String>();

		if (colls == null)
			return null;

		if (colls.isEmpty())
			return list;

		try {
			Method method = null;
			String val = null;
			for (Object obj : colls) {
				if (obj == null)
					continue;
				if (method == null) {
					PropertyDescriptor pd = new PropertyDescriptor(field, obj.getClass());
					method = pd.getReadMethod();
					if (!String.class.equals(method.getReturnType()))
						throw new DemoException("Return type not string!");
				}

				val = (String) method.invoke(obj);
				if (isNotBlank(val))
					list.add(val);
			}
			return list;
		} catch (Exception e) {
			throw new DemoException("Extract string error!");
		}
	}

	/**
	 * 拼接字符串，排除null
	 * 
	 * @param strArr
	 * @return
	 */
	public static String joinExcludeNull(String... strArr) {
		StringBuilder sb = new StringBuilder();
		for (String str : strArr) {
			if (str != null) {
				sb.append(str);
			}
		}
		return sb.toString();
	}

	/**
	 * 默认字符串
	 * 
	 * @param obj
	 * @return
	 */
	public static String defaultString(Object obj) {
		return obj == null ? EMPTY : obj.toString();
	}

	/**
	 * null返回空字符串，否则返回对象本身
	 * 
	 * @param obj
	 * @return
	 */
	public static Object defaultObject(Object obj) {
		return obj == null ? EMPTY : obj;
	}

	/**
	 * 递增序列号，需要考虑001，转换为数字时会丢失0的问题
	 * 
	 * @param currindex
	 * @param increase
	 */
	public static String orgSequenceProgressIndex(String currindex, Integer increase) {
		int num = currindex.length();
		
		BigDecimal useIncrease = BigDecimal.ONE;
		if (increase != null) {
			useIncrease = new BigDecimal(increase);
		}
		
		BigDecimal newIndex = new BigDecimal(currindex).add(useIncrease);
		String newIndexStr = newIndex.toString();
		
		int newNum = newIndexStr.length();
		
		//生成的位数比原本位数，则补0
		if (newNum < num) {
			int addCount = num - newNum;
			for (int i = 0; i < addCount; i++) {
				newIndexStr = "0" + newIndexStr;
			}
		}
		
		return newIndexStr;
	}

	

	/**
	 * 字符转ASCII码
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal stringToAscii(String value) {
		StringBuffer sbu = new StringBuffer();
		if (StringUtil.isValidString(value)) {
			char[] chars = value.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				sbu.append((int) chars[i]);
			}
			return new BigDecimal(sbu.toString());
		} else {
			return new BigDecimal(-1);
		}
	}
}
