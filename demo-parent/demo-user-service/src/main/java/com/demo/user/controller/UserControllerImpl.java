package com.demo.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.user.api.client.UserApiService;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.req.QueryUserReq;
import com.demo.user.api.dto.rsp.UserDto;
import com.demo.user.service.UserService;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

@ApiModel("用户管理")
@RestController
public class UserControllerImpl implements UserApiService {

	@Autowired
	private UserService UserService;
	
	@ApiOperation(value="查询用户信息",notes="查询用户信息")
	@Override
	public ResultPageBean<List<UserDto>> queryUserDto(@RequestBody QueryUserReq req) {
	
		return UserService.queryUserDto(req);
	}

	@ApiOperation(value="根据用户编号查询用户信息",notes="根据用户编号查询用户信息")
	@Override
	public ResultBean<UserDto> queryUserById(@RequestBody QueryShopById req) {
	
		return UserService.queryUserById(req);
	}

}
