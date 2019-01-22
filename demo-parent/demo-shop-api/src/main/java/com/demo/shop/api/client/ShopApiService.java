package com.demo.shop.api.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.shop.api.dto.req.queryShopReq;
import com.demo.shop.api.dto.rsp.ShopDto;

import io.swagger.annotations.ApiModel;

/**
 *商品信息API
 * @author leiZheng
 *
 *2019年1月17日
 */
@ApiModel("商品信息API")
@FeignClient("demo-shop-service")
@RequestMapping(value = "/shop/", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface ShopApiService {

	/**
	 * 查询商品信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value="queryShop",method=RequestMethod.POST)
	ResultPageBean<List<ShopDto>> queryShop(queryShopReq req);

	
	@RequestMapping(value="operError",method=RequestMethod.GET)
	ResultBean<Void> operError();
	
	@RequestMapping(value="queryTest",method=RequestMethod.GET)
	ResultBean<ShopDto> queryTest();
}
