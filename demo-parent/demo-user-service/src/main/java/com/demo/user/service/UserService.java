package com.demo.user.service;

import java.util.List;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.req.QueryUserReq;
import com.demo.user.api.dto.rsp.UserDto;

/**
 * 用户信息service接口
 * @author leiZheng
 *
 *2019年1月16日
 */
public interface UserService {

	
	ResultPageBean<List<UserDto>> queryUserDto(QueryUserReq req);

	ResultBean<UserDto> queryUserById(QueryShopById req);

}
