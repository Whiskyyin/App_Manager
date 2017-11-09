package cn.appsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;

public interface AppCategoryMapper {
	
	List<AppCategory> appCategoryList(@Param("parentId")String parentId);
}
