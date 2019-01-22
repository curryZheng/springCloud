package com.demo.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.shop.api.client.ShopApiService;
import com.demo.shop.api.dto.req.queryShopReq;
import com.demo.shop.api.dto.rsp.ShopDto;
import com.demo.shop.service.ShopService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

/**
 * 商品信息控制器
 * @author leiZheng
 *
 *2019年1月17日
 */
@ApiModel("商品管理")
@RestController
public class ShopControllerImpl implements ShopApiService {

	@Autowired
	private ShopService ShopService;
	
	@ApiOperation(value="查询商品信息",notes="查询商品信息")
	@Override
	public ResultPageBean<List<ShopDto>> queryShop(@RequestBody queryShopReq req) {
		
		return ShopService.queryShop(req);
	}

	@ApiOperation(value="拦截器error异常处理",notes="拦截器error异常处理")
	@Override
	public ResultBean<Void> operError() {
		int a =8/0;
	
		return  new ResultBean<>();
	}

}
