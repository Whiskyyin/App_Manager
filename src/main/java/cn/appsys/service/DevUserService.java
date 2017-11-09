package cn.appsys.service;

import javax.servlet.http.HttpSession;

import cn.appsys.pojo.DevUser;

public interface DevUserService {
	
	DevUser loadBy(String userCode,String userPassword);
}
