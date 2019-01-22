package com.demo.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.demo.user.entity.UserEntity;

public interface UserDao {

	
	List<UserEntity> queryUser(UserEntity entity);

	int insertUser(UserEntity entity);
	
	int updateUser(UserEntity entity);

	UserEntity queryUserById(@Param("id") String id);
}
