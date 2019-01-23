package com.demo.gateway.filter;

import java.io.IOException;
import java.nio.charset.Charset;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StreamUtils;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 拦截器
 * 
 * @author leiZheng
 *
 *         2019年1月22日
 */
public class DemoFilterPost extends ZuulFilter {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public boolean shouldFilter() {

		return true;
	}

	@Override
	public Object run() {

		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			String body = StreamUtils.copyToString(ctx.getResponseDataStream(), Charset.forName("UTF-8"));
		
	        logger.info("responseDate{}",body);
	        ctx.setResponseBody(body);			
		} catch (IOException e) {
			
			e.printStackTrace();
		}

		return ctx;
	}

	@Override
	public String filterType() {

		return "post";//post表示后置处理 
	}

	@Override
	public int filterOrder() {

		return 0;  //优先级别 数越大级别越低
	}

}
