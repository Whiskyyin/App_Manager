package cn.appsys.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.BackendUserMapper;
import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BackendUserService;

@Service("backendUserService")
public class BackendUserServiceImpl implements BackendUserService {

	@Resource
	private BackendUserMapper backendUserMapper;
	public void setBackendUserMapper(BackendUserMapper backendUserMapper) {
		this.backendUserMapper = backendUserMapper;
	}


	public BackendUser getLoginUser(String userCode, String password) {
		// TODO Auto-generated method stub
		return backendUserMapper.getLoginUser(userCode, password);
	}

}
