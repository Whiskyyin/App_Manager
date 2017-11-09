package cn.appsys.service;

import java.util.Map;


import cn.appsys.pojo.AppInfo;
import cn.appsys.tools.Page;

public interface AppInfoService {
	
	Page<AppInfo> getAppList(Map<String,Object> map);
	Integer findAPKName(String APKName);
	Integer add(AppInfo appinfo);
	AppInfo getAppInfoView(Integer id);
	Integer modify(AppInfo appinfo);
	Integer deleteAppLogo(Integer id);
	Integer deleteAppInfoById(Integer id);
	Integer updateSatus(Integer status,Integer id);
	Boolean updataStatusByAppInfo(AppInfo appinfo);
}
