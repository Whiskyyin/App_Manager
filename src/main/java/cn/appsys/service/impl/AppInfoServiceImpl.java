package cn.appsys.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.AppInfoMapper;
import cn.appsys.pojo.AppInfo;
import cn.appsys.service.AppInfoService;
import cn.appsys.tools.Page;

@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Resource
	private AppInfoMapper appInfoMapper;
	public void setAppMapper(AppInfoMapper appMapper) {
		this.appInfoMapper = appMapper;
	}


	public Page<AppInfo> getAppList(Map<String, Object> map) {
		Page<AppInfo> page=new Page<AppInfo>();
		page.setTotalCount(appInfoMapper.countAppInfo(map));
		page.setList(appInfoMapper.findAppList(map));
		return page;
	}


	public Integer findAPKName(String APKName) {
		
		return appInfoMapper.findAPKName(APKName);
	}


	public Integer add(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.add(appinfo);
	}


	public AppInfo getAppInfoView(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.getAppInfoView(id);
	}


	public Integer modify(AppInfo appinfo) {
		// TODO Auto-generated method stub
		return appInfoMapper.modify(appinfo);
	}


	public Integer deleteAppLogo(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.deleteAppLogo(id);
	}


	public Integer deleteAppInfoById(Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.deleteAppInfoById(id);
	}


	public Integer updateSatus(Integer status,Integer id) {
		// TODO Auto-generated method stub
		return appInfoMapper.updateSatus(status,id);
	}


	public Boolean updataStatusByAppInfo(AppInfo appinfo) {
		
		boolean flag=false;
		//状态
		Integer status=((AppInfo)appInfoMapper.getAppInfoView(appinfo.getId())).getStatus();
		
		Integer aid=appinfo.getId();
		if(status==4||status.equals("4")){
			status=5;
		}else if(status==5||status.equals("5")){
			status=4;
		}else if(status==2||status.equals("2")){
			status=4;
		}
		
		System.out.println(status);
		if(appInfoMapper.updateSatus(status, aid)==1){
			flag=true;
		}
		return flag;
	}

}
