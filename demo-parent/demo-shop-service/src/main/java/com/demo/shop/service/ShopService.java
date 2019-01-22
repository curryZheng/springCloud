package com.demo.shop.service;

import java.util.List;

import com.demo.common.structure.ResultPageBean;
import com.demo.shop.api.dto.req.queryShopReq;
import com.demo.shop.api.dto.rsp.ShopDto;

public interface ShopService {

	ResultPageBean<List<ShopDto>> queryShop(queryShopReq req);

}
