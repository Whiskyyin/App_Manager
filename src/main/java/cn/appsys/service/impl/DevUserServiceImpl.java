package cn.appsys.service.impl;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;
@Service("devUserService")
public class DevUserServiceImpl implements DevUserService {

	@Resource
	private DevUserMapper devUserMapper;
	public void setDevUser(DevUserMapper devUser) {
		this.devUserMapper = devUser;
	}



	public DevUser loadBy(String userCode, String userPassword) {
		
		DevUser user=devUserMapper.loadBy(userCode, userPassword);
			return user;
		
	}

}
