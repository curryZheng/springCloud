package com.demo.user.api.client;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.req.QueryUserReq;
import com.demo.user.api.dto.rsp.UserDto;
import com.demo.user.failback.UserCallBackImpl;
import io.swagger.annotations.ApiModel;

/**
 * 用户管理
 * @author leiZheng
 *
 *2019年1月15日
 */
@ApiModel("用户管理API")
@FeignClient(name = "demo-user-service", fallbackFactory = UserCallBackImpl.class)//声明式Feigh 服务名必须与eureka上服务名一致  不让找不到对应服务
@RequestMapping(value = "/user/",consumes=MediaType.APPLICATION_JSON_UTF8_VALUE,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public interface UserApiService {

	/**
	 * 查询用户信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value="queryUserDto",method = RequestMethod.POST)
 	public ResultPageBean<List<UserDto>>  queryUserDto(@RequestBody QueryUserReq req);
	
	/**
	 * 根据用户编号查询用户信息
	 * @param req
	 * @return
	 */
	@RequestMapping(value="queryUserById",method = RequestMethod.POST)
	public ResultBean<UserDto> queryUserById(@RequestBody QueryShopById req);
	
}
