package com.demo.shop.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.structure.ResultPageBean;
import com.demo.framework.base.BaseService;
import com.demo.shop.api.dto.req.queryShopReq;
import com.demo.shop.api.dto.rsp.ShopDto;
import com.demo.shop.dao.ShopDao;
import com.demo.shop.entity.ShopEntity;
import com.demo.shop.service.ShopService;
import com.demo.user.api.client.UserApiService;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.rsp.UserDto;

@Service
public class ShopServiceImpl extends BaseService implements ShopService {

	@Autowired
	private UserApiService userApiService;

	@Autowired
	private ShopDao ShopServiceDao;

	@Override
	public ResultPageBean<List<ShopDto>> queryShop(queryShopReq req) {
		startPage(req);

		List<ShopEntity> entityList = ShopServiceDao.queryShop(req);
		
        List<ShopDto> shopDtoList=new ArrayList<>();
		
        if (entityList != null && entityList.size() > 0) {
			for (ShopEntity entity : entityList) {

				UserDto userDto = userApiService.queryUserById(new QueryShopById(entity.getUserCode())).getData();
				ShopDto shopDto = convertBean(entity, ShopDto.class);
				shopDto.setUserName(userDto!=null?userDto.getUserName():"");
				
				shopDtoList.add(shopDto);
			}

		}

        return packPageResult(entityList,shopDtoList );
	}

}
