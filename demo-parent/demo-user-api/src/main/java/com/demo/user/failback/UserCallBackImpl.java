package com.demo.user.failback;

import java.util.List;

import org.springframework.stereotype.Component;

import com.demo.common.constant.MsgEnum;
import com.demo.common.structure.ResultBean;
import com.demo.common.structure.ResultPageBean;
import com.demo.user.api.client.UserApiService;
import com.demo.user.api.dto.req.QueryShopById;
import com.demo.user.api.dto.req.QueryUserReq;
import com.demo.user.api.dto.rsp.UserDto;

import feign.hystrix.FallbackFactory;

/**
 * 服務降級熔斷統一處理
 * @author leiZheng
 *
 *2019年1月23日
 */
@Component
public class UserCallBackImpl implements FallbackFactory<UserApiService>{

	@Override
	public UserApiService create(Throwable cause) {
		
		return new UserApiService() {
			
			@Override
			public ResultPageBean<List<UserDto>> queryUserDto(QueryUserReq req) {
				
				ResultPageBean<List<UserDto>> resultPageBean = new ResultPageBean<>();

				resultPageBean.setResultStatus(MsgEnum.CONNECTION_EXCEPTION);

				return resultPageBean;
			}
			
			@Override
			public ResultBean<UserDto> queryUserById(QueryShopById req) {
				
				return ResultBean.createFailedResult(MsgEnum.CONNECTION_EXCEPTION);
			}
		};
		
		
	}

	


}
