package com.demo.framework.base;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.demo.common.constant.MsgEnum;
import com.demo.common.exception.DemoException;
import com.demo.common.structure.RequestPageHead;
import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.common.util.BeanConvertUtils;
import com.demo.common.util.CollectionUtil;
import com.demo.common.util.SortFiledUtil;
import com.demo.common.util.StringUtil;
import com.demo.framework.utils.ApplicationContextUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @author leiZheng
 *
 *2019年1月15日
 */
public class BaseService {
  
	/**
	 * 日志记录器
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	/**
	 * 用于存放新事务的状态容器
	 */
	private final static ThreadLocal<TransactionStatus> transactionStatusThreadLocal = new ThreadLocal<>(); 
	
	/**
	 * 启动分页
	 * @param page
	 */
	protected void startPage(RequestPageHead page) {
		startPage(page, null);
	}
	
	/**
	 * 启动分页，设置排序参数 排序参数为 orderBy ="filed1 desc, filed2 desc, filed3"
	 * 
	 * @param page
	 * @param orderBy 表字段的排序规则
	 * 
	 */
	protected void startPageForTableColumn(RequestPageHead page, String orderBy) {
		startPage(page);
		if (StringUtil.isValidString(orderBy)) {
			PageHelper.orderBy(orderBy);
		}
	}
	
	/**
	 * 启动分页
	 * @param page 排序对象
	 * @param tableColumnOrderBy 表字段的排序规则
	 */
	protected <T extends RequestPageHead> void startPage(T page, String tableColumnOrderBy) {
		startPage(page.getPageIndex(), page.getPageSize(), page.getTotalForExcludeQueryCount());

		try {
			//如果有类字段的排序规则，则优先使用
			if (CollectionUtil.isNotEmpty(page.getOrderByList())) {
				String rule = SortFiledUtil.sortFiledParse(page.getClass(), page.getOrderByList());
				if (StringUtil.isNotBlank(rule)) {
					PageHelper.orderBy(rule);
				}
			} else {
				//如果有表字段的排序规则，则使用
				if (StringUtil.isNotBlank(tableColumnOrderBy)) {
					PageHelper.orderBy(tableColumnOrderBy);
				}
			}
		} catch (Exception e) {
			logger.error("start page sort order by error!", e);
		}
	}
	
	/**
	 * 启动分页
	 * @param pageIndex
	 * @param pageSize
	 */
	protected void startPage(int pageIndex, int pageSize, Long totalForExcludeQueryCount) {
		//启用规避掉二次查询总数量
		if (totalForExcludeQueryCount != null && totalForExcludeQueryCount >= 0) {
			
		}

		if (pageIndex < 0) {
			throw new DemoException("The page number must not be less than 0!");
		}
		if (pageSize < 0) {
			throw new DemoException("The number of rows per page must not be less than 0!");
		}
			
		PageHelper.startPage(pageIndex, pageSize);
	}

	/**
	 * 启动分页
	 * @param pageIndex
	 * @param pageSize
	 */
	protected void startPage(int pageIndex, int pageSize) {
		startPage(pageIndex, pageSize, null)   ;
	}


	/**
	 * 封装分页结果集
	 * @param list
	 * @return
	 */
	protected <T> ResultPageBean<List<T>> packPageResult(List<T> list) {
		ResultPageBean<List<T>> resultPageBean = new ResultPageBean<List<T>>();
		packPageInfo(resultPageBean, list);
		resultPageBean.setData(list);
		return resultPageBean;
	}
	
	/**
	 * 封装默认成功的结果集
	 * @param t
	 * @return
	 */
	protected <T> ResultBean<T> packResult() {
		return new ResultBean<T>();
	}
	
	/**
	 * 封装结果集
	 * @param t
	 * @return
	 */
	protected <T> ResultBean<T> packResult(T data) {
		return new ResultBean<T>(data);
	}
	
	/**
	 * 封装结果集
	 * @param t
	 * @return
	 */
	protected <T> ResultBean<T> packResult(Object data, Class<T> dtoClass) {
		return new ResultBean<T>(convertBean(data, dtoClass));
	}
	
	/**
	 * 将类转换为另一个类
	 * @param from
	 * @param toClass
	 * @return
	 */
	protected <T> T convertBean(Object from, Class<T> toClass) {
		return BeanConvertUtils.convertBean(from, toClass);
	}
	
	/**
	 * 将一个类的list转换为另一个类的list
	 * @param fromList
	 * @param toClass
	 * @return
	 */
	protected <T> List<T> convertBeanList(List<?> fromList, Class<T> toClass) {
		return BeanConvertUtils.convertBeanList(fromList, toClass);
	}
	
	/**
	 * 将一个类的set转换为另一个类的set
	 * @param fromSet
	 * @param toClass
	 * @return
	 */
	protected <T> Set<T> convertBeanSet(Set<?> fromSet, Class<T> toClass) {
		return BeanConvertUtils.convertBeanSet(fromSet, toClass);
	}
	
	/**
	 * 将entity的list转换为dto的list，并封装分页结果集
	 * @param entityList
	 * @param dtoClass
	 * @return
	 */
	protected <T> ResultPageBean<List<T>> packPageResult(List<?> entityList, Class<T> dtoClass) {
		if (dtoClass == null)
			throw new DemoException("Class can't be empty!");
		
		ResultPageBean<List<T>> resultPageBean = new ResultPageBean<List<T>>();
		packPageInfo(resultPageBean, entityList);
		resultPageBean.setData(convertBeanList(entityList, dtoClass));
		return resultPageBean;
	}
	
	/**
	 * 封装分页结果集
	 * @param pageList 带分页信息数据列表
	 * @param dataList 需要返回的数据列表
	 * @return
	 */
	protected <T> ResultPageBean<List<T>> packPageResult(List<?> pageList, List<T> dataList) {
		ResultPageBean<List<T>> resultPageBean = new ResultPageBean<List<T>>();
		packPageInfo(resultPageBean, pageList);
		resultPageBean.setData(dataList);
		return resultPageBean;
	}
	
	/**
	 * 封装分页的基础信息
	 * @param resultPageBean
	 * @param pageInfo
	 */
	protected void packPageInfo(ResultPageBean<?> resultPageBean, List<?> pageInfo) {
		PageInfo<?> resultPage = new PageInfo<>(pageInfo);
		resultPageBean.setPageNum(resultPage.getPageNum());
		resultPageBean.setPages(resultPage.getPages());
		resultPageBean.setPageSize(resultPage.getPageSize());
		resultPageBean.setTotal(resultPage.getTotal());
	}
	
	/**
	 * 创建一个默认失败的结果集
	 * @return
	 */
	protected <T> ResultBean<T> createFailedResult() {
		return ResultBean.createFailedResult();
	}
	/**
	 * 创建一个异常失败时，返回的带MsgEnum信息的结果集。
	 * 创建自定义异常时请传入MsgEnum，如：throw  new TmsException(MsgEnum.FAILED_NOT_ALLOW_NEGATIVE);
	 * 
	 * 在catch中,捕获该异常时用key = e.getMessage()
	 * catch (Exception e) {
	 *   return createFailedResult(e);
	 * }
	 * @return ResultBean<T>
	 * 
	 * 当在 MsgEnum 中找不到定义的信息，会抛出一个默认的 MsgEnum.FAILED_OPERATION
	 * 
	 */
	protected <T> ResultBean<T> createFailedResult(Exception e) {
		String key = e.getMessage();
		ResultBean<T> result = new ResultBean<T>();
		result.setResultStatus(MsgEnum.findMsgEnumByKey(key)==null?MsgEnum.FAILED_OPERATION:MsgEnum.findMsgEnumByKey(key));
		return result;
	}
	
	/**
	 * 创建一个默认失败的分页结果集
	 * @return
	 */
	protected <T> ResultPageBean<T> createFailedPageResult(RequestPageHead page) {
		ResultPageBean<T> result = new ResultPageBean<T>();
		result.setResultStatus(MsgEnum.FAILED_OPERATION);
		if (page != null) {
			result.setPageNum(page.getPageIndex());
			result.setPageSize(page.getPageSize());
		}
		return result;
	}
	
	/**
	 * 手动回滚事务
	 */
	protected void rollback() {
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
	
	/**
	 * 开启一个新事务，后可用{@link #submitTransactional()}来手动提交事务
	 */
	protected void startNewTransactional() {
		ApplicationContext context = ApplicationContextUtils.getContext();
		DataSourceTransactionManager transactionManager = context.getBean(DataSourceTransactionManager.class);
		
		DefaultTransactionDefinition def = new DefaultTransactionDefinition();
		def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
		TransactionStatus status = transactionManager.getTransaction(def);
		
		transactionStatusThreadLocal.set(status);
	}
	
	/**
	 * 用于{@link #startNewTransactional()}之后，手动提交事务
	 */
	protected void submitTransactional() {
		TransactionStatus status = transactionStatusThreadLocal.get();
		if (status == null) {
			return;
		}
		
		ApplicationContext context = ApplicationContextUtils.getContext();
		DataSourceTransactionManager transactionManager = context.getBean(DataSourceTransactionManager.class);
		transactionManager.commit(status);
	}
	
	
}
