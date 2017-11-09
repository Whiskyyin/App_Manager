package cn.appsys.service;


import cn.appsys.pojo.BackendUser;

public interface BackendUserService {
	
	BackendUser getLoginUser(String userCode,String password);
}
