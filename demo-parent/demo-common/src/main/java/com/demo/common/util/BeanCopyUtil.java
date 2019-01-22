package com.demo.common.util;

import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.beanutils.PropertyUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.demo.common.exception.DemoException;







/**
 * 1.封装了BeanUtil类，将checked 异常变成runTime 异常 2.
 */
public class BeanCopyUtil implements Serializable {	
	private static final long serialVersionUID = 3319907875757761938L;
	private static final Log log = LogFactory.getLog(BeanCopyUtil.class);
	  static {
	        register();
	    }
	    /**
	     * 注册日期格式转换类 主要是为struts的BeanUtils的类，提供日期转换格式的插件。 否则，BeanUtils则将字符串转为日期时，会报错
	     */
	    private static void register() {
	        ConvertUtils.register(new DateConverter(), Date.class);
	    }
	
    public static void copyProperties(Object to, Object from) {
        try {
        	PropertyUtils.copyProperties(to, from);
        } catch (Exception e) {
        	throw new DemoException("Bean copy error!", e);
        }
    }

    @SuppressWarnings("unchecked")
	public static List copyCollection(List collection, Class aclass) {
        List newCollection = new ArrayList();
        if(collection == null){
    	   return newCollection;
        }      
        for (int m = 0; m < collection.size(); m++) {
            Object obj = collection.get(m);

            Object newObj = ClassUtil.newInstance(aclass);

            BeanCopyUtil.copyProperties(newObj, obj);

            newCollection.add(newObj);
        }

        return newCollection;
    }

    @SuppressWarnings("unchecked")
	public static Map describe(Object from) {
        try {
            return BeanUtils.describe(from);
        } catch (Exception e) {
        	throw new DemoException("Bean copy describe error!", e);
        }
    }

    /**
     * 批量将参数转换成类 参数必须是name + 下划线 + 数字下标 这样系统会自动进行转换对象，根据数字下标的数目，加入到List当中
     * 
     * @param params
     * @param aclass
     * @param paramNum
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List getBatchObjectFromParam(Map params, Class aclass, int paramNum) {

        PropertyUtilsBean bean = new PropertyUtilsBean();

        PropertyDescriptor[] origDescriptors = bean.getPropertyDescriptors(aclass);

        List ls = new ArrayList();
        Map properties = new HashMap();

        for (int m = -1; m < paramNum; m++) {
            Object obj = ClassUtil.newInstance(aclass);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }

                if (bean.isWriteable(obj, name) && !"pricetypes".equals(name)) {
                    String keyName = -1 == m ? name : (name + "_" + (m + 1));
                    Object paramObject = params.get(keyName);
                    if (null != paramObject)
                        properties.put(name, paramObject);
                }

            }

            if (!properties.isEmpty()
                && !"com.fangcang.hotel.base.persistence.HtlBinding".equals(aclass.getName())) {
                copyProperties(obj, properties);
                ls.add(obj);

                properties.clear();
            }
            if (!properties.isEmpty()
                && "com.fangcang.hotel.base.persistence.HtlBinding".equals(aclass.getName())) {
                Object priceObject = params.get("pricetypes");
                String[] priceTs;
                String priceT;
                if (priceObject instanceof String[]) {
                    priceTs = (String[]) priceObject;
                    properties.put("pricetypes", priceTs);

                } else {
                    priceT = (String) priceObject;
                    properties.put("pricetypes", priceT);
                }
                copyProperties(obj, properties);
                ls.add(obj);

                properties.clear();
            }
        }

        return ls;
    }

    /**
     * 批量将参数转换成类 参数必须是className+"."+name + 下划线 + 数字下标 这样系统会自动进行转换对象，根据数字下标的数目，加入到List当中
     * 
     * @param params
     * @param aclass
     * @param paramNum
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List getBatchObjectFromParamWithClassName(Map params, 
                                                            Class aclass, int paramNum) {

        PropertyUtilsBean bean = new PropertyUtilsBean();

        PropertyDescriptor[] origDescriptors = bean.getPropertyDescriptors(aclass);

        List ls = new ArrayList();
        Map properties = new HashMap();

        for (int m = -1; m < paramNum; m++) {
            Object obj = ClassUtil.newInstance(aclass);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }

                if (bean.isWriteable(obj, name)) {
                    String keyName = -1 == m ? name : (name + "_" + (m + 1));
                    log.info(aclass.getSimpleName() + "." + keyName);
                    Object paramObject = params.get(aclass.getSimpleName() + "." + keyName);
                    if (null != paramObject)
                        properties.put(name, paramObject);
                }

            }

            if (!properties.isEmpty()) {
                copyProperties(obj, properties);
                ls.add(obj);

                properties.clear();
            }
        }

        return ls;
    }

    /**
     * 屏蔽个别 类名.ID_1不能解析问题 批量将参数转换成类 参数必须是className+name + 下划线 + 数字下标
     * 这样系统会自动进行转换对象，根据数字下标的数目，加入到List当中
     * 
     * @param params
     * @param aclass
     * @param paramNum
     * @return
     */
    @SuppressWarnings("unchecked")
	public static List getBatchObjectFromParamWithClassNames(Map params, 
                                                             Class aclass, int paramNum) {

        PropertyUtilsBean bean = new PropertyUtilsBean();

        PropertyDescriptor[] origDescriptors = bean.getPropertyDescriptors(aclass);

        List ls = new ArrayList();
        Map properties = new HashMap();

        for (int m = -1; m < paramNum; m++) {
            Object obj = ClassUtil.newInstance(aclass);

            for (int i = 0; i < origDescriptors.length; i++) {
                String name = origDescriptors[i].getName();
                if ("class".equals(name)) {
                    continue; // No point in trying to set an object's class
                }

                if (bean.isWriteable(obj, name)) {
                    String keyName = -1 == m ? name : (name + "_" + (m + 1));
                    log.info(aclass.getSimpleName() + keyName);
                    Object paramObject = params.get(aclass.getSimpleName() + keyName);
                    if (null != paramObject)
                        properties.put(name, paramObject);
                }

            }

            if (!properties.isEmpty()) {
                copyProperties(obj, properties);
                ls.add(obj);

                properties.clear();
            }
        }

        return ls;
    }

    /**
     * 批量将参数转换成类 参数必须是name + 下划线 + 行 + 下划线 + 列 这样系统会自动进行转换对象，根据数字下标的数目，加入到List当中
     * 
     * @param params
     * @param aclass
     * @param paramNum
     * @return
     *  by guojun 2008-12-19 10:12
     */
    @SuppressWarnings("unchecked")
	public static List getBatchObjectRowColFromParam(Map params, Class aclass, int paramNum) {

        PropertyUtilsBean bean = new PropertyUtilsBean();

        PropertyDescriptor[] origDescriptors = bean.getPropertyDescriptors(aclass);

        List ls = new ArrayList();
        Map properties = new HashMap();

        for (int m = 0; m <= paramNum; m++) {
            for (int col = 1; 4 > col; col++) {
                Object obj = ClassUtil.newInstance(aclass);

                for (int i = 0; i < origDescriptors.length; i++) {
                    String name = origDescriptors[i].getName();
                    if ("class".equals(name)) {
                        continue; // No point in trying to set an object's class
                    }

                    if (bean.isWriteable(obj, name)) {
                        String keyName = 0 == m ? name : (name + "_" + m + "_" + col);
                        if (name.equals("roomTypeChoose")) {
                            keyName = name + "_" + m + "_1";
                        }
                        Object paramObject = params.get(keyName);
                        if (null != paramObject) {
                            properties.put(name, paramObject);
                        }
                    }
                }

                if (!properties.isEmpty()) {
                    copyProperties(obj, properties);
                    ls.add(obj);
                    properties.clear();
                }
            }
        }

        return ls;
    }

    /**
     * 返回obj的属性值
     * 
     * @param obj
     * @return
     */
    public static String printBeanProps(Object obj) throws Exception {
        if (null == obj) {
            return "";
        }
        PropertyUtilsBean bean = new PropertyUtilsBean();
        PropertyDescriptor[] origDescriptors = bean.getPropertyDescriptors(obj.getClass());
        StringBuffer buf;
        buf = new StringBuffer();
        for (int j = 0; j < origDescriptors.length; j++) {
            String name = origDescriptors[j].getName();
            if ("class".equals(name)) {
                continue;
            }
            if (bean.isReadable(obj, name)) {
                Object value = bean.getProperty(obj, name);
                if (null != value) {
                    buf.append("" + name + ":" + value + ",");
                }
            }

        }
        return buf.toString();
    }
}
