package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.AppInfoMapper;
import cn.appsys.mapper.AppVersionMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.AppVersionService;

@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Resource
	private AppVersionMapper appVersionMapper;
	public void setVersionMapper(AppVersionMapper versionMapper) {
		this.appVersionMapper = versionMapper;
	}
	@Resource
	private AppInfoMapper appInfoMapper;
	public void setAppinfoMapper(AppInfoMapper appinfoMapper) {
		this.appInfoMapper = appinfoMapper;
	}


	public List<AppVersion> getAppVersionList(Integer id) {
		
		return appVersionMapper.getAppVersionList(id);
	}


	public Integer deleteVersionByAppId(Integer appId) {
		// TODO Auto-generated method stub
		return appVersionMapper.deleteVersionByAppId(appId);
	}


	public AppVersion getAppVersionById(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.getAppVersionById(id);
	}


	public Boolean add(AppVersion appVerson,AppInfo appInfo,Integer id) {
		boolean falg=false;
	
		if(appVersionMapper.add(appVerson)==1){
			appInfo.setId(appVerson.getAppId());
			appInfo.setVersionId(appVerson.getId());
			appInfo.setStatus(1);
			if(appInfoMapper.modify(appInfo)==1){
				falg=true;
			}
		}
		return falg;
	}


	public Integer modify(AppVersion appVersion) {
		// TODO Auto-generated method stub
		return appVersionMapper.modify(appVersion);
	}


	public Integer deleteApkFile(Integer id) {
		// TODO Auto-generated method stub
		return appVersionMapper.deleteApkFile(id);
	}

}
