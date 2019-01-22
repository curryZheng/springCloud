package com.demo.shop.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.shop.api.dto.req.queryShopReq;
import com.demo.shop.entity.ShopEntity;

/**
 * 商品信息dao
 * @author leiZheng
 *
 *2019年1月17日
 */
public interface ShopDao {

	/**
	 * 查询商品信息
	 * @param req
	 * @return
	 */
	List<ShopEntity> queryShop(@Param("req") queryShopReq req);

}
