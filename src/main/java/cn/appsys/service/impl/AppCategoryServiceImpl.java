package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.AppCategoryMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.service.AppCategoryService;

@Service("appCategoryService")
public class AppCategoryServiceImpl implements AppCategoryService {

	@Resource
	private AppCategoryMapper appCategoryMapper;
	public void setAppMapper(AppCategoryMapper appMapper) {
		this.appCategoryMapper = appMapper;
	}


	public List<AppCategory> appCategoryList(String parentId) {
		
		return appCategoryMapper.appCategoryList(parentId);
	}

}
