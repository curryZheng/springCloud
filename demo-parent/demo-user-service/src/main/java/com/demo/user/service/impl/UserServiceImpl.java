package com.demo.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.framework.base.BaseService;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.req.QueryUserReq;
import com.demo.user.api.dto.rsp.UserDto;
import com.demo.user.dao.UserDao;
import com.demo.user.entity.UserEntity;
import com.demo.user.service.UserService;

/**
 * 用户信息service
 * 
 * @author leiZheng
 *
 *         2019年1月16日
 */
@Service
public class UserServiceImpl extends BaseService implements UserService {

	@Autowired
	private UserDao userDao;

	/**
	 * 查询用户信息
	 */
	@Override
	public ResultPageBean<List<UserDto>> queryUserDto(QueryUserReq req) {
		startPage(req);
		List<UserEntity> userDtoList = userDao.queryUser(convertBean(req, UserEntity.class));

		return packPageResult(userDtoList, UserDto.class);
	}

	/**
	 * 根据用户编号查询用户信息
	 */
	@Override
	public ResultBean<UserDto> queryUserById(QueryShopById req) {

		UserEntity entity = userDao.queryUserById(req.getId());

		return packResult(entity, UserDto.class);
	}

}
