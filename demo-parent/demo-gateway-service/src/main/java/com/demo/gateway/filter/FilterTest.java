package com.demo.gateway.filter;

import java.io.IOException;
import java.nio.charset.Charset;

import org.springframework.util.StreamUtils;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * 拦截器 
 * @author leiZheng
 *
 *2019年1月22日
 */
public class FilterTest extends ZuulFilter {

	@Override
	public boolean shouldFilter() {
	
		return true;
	}

	@Override
	public Object run() {
		
		RequestContext ctx = RequestContext.getCurrentContext();
		try {
			String body = StreamUtils.copyToString(ctx.getResponseDataStream(), Charset.forName("UTF-8"));
		
			System.out.println(body);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public String filterType() {
	
		return "post";
	}

	@Override
	public int filterOrder() {
		
		return 0;
	}

}
