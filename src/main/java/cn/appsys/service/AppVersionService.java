package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;

public interface AppVersionService {
	
	List<AppVersion> getAppVersionList(Integer id);
	Integer deleteVersionByAppId(Integer appId);
	AppVersion getAppVersionById(Integer id);
	Boolean add(AppVersion appVerson,AppInfo appinfo,Integer id);
	Integer modify(AppVersion appVersion);
	Integer deleteApkFile(Integer id);
}
