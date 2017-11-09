package cn.appsys.mapper;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.BackendUser;

public interface BackendUserMapper {
	
	/**
	 * 登陆
	 *
	 *创建时间: 2017年11月3日 下午4:43:47
	 *@author: Angelo yin
	 *@return
	 */
	BackendUser getLoginUser(@Param("userCode")String userCode,@Param("userPassword")String password);
}
