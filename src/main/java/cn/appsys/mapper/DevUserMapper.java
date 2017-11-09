package cn.appsys.mapper;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DevUser;

public interface DevUserMapper {
	
	/**
	 * 登陆
	 * @return
	 */
	DevUser loadBy(@Param("devCode")String userCode,@Param("devPassword")String userPassword);
}
