package com.demo.gateway.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class DemoFilterPre extends ZuulFilter {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	final static Map<String, String> tokeyMap = new HashMap<>();
	static {
		tokeyMap.put("demo-shop-service", "1");
		tokeyMap.put("demo-user-service", "2");
	}

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();

		HttpServletRequest request = ctx.getRequest();
		
		logger.info("send {} to {}", request.getMethod(), request.getRequestURL().toString());
        
		//由于的前置拦截器，会在调用服务之前进来，判断是否请求头有无token 
//		 if(null == request.getHeader("token")){
//			 logger.error("demo token is null ...");
//	            ctx.setSendZuulResponse(false);
//	            ctx.setResponseStatusCode(401);
//	        }else{
//	        	logger.info("access token is {}!!!",request.getHeader("token"));
//	        }

		
		return null;
	}

	@Override
	public String filterType() {

		return "pre"; // 方法执行前
	}

	@Override
	public int filterOrder() {

		return 0;
	}

}
