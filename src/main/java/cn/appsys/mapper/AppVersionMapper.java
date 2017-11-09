package cn.appsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

public interface AppVersionMapper {
	
	/**
	 * 查询app所有版本信息号
	 *
	 *创建时间: 2017年11月2日 下午4:12:33
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	List<AppVersion> getAppVersionList(@Param("appId")Integer id);
	
	/**
	 * 删除版本号
	 *
	 *创建时间: 2017年11月3日 上午9:21:29
	 *@author: Angelo yin
	 *@param appId
	 *@return
	 */
	Integer deleteVersionByAppId(@Param("appId")Integer appId);
	
	/**
	 * 查询app版本信息号
	 *
	 *创建时间: 2017年11月3日 上午9:36:18
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	AppVersion getAppVersionById(@Param("id")Integer id);
	
	/**
	 * 添加版本号
	 *
	 *创建时间: 2017年11月3日 上午10:42:41
	 *@author: Angelo yin
	 *@param appVerson
	 *@return
	 */
	Integer add(AppVersion appVerson);
	
	/**
	 * 修改版本号
	 *
	 *创建时间: 2017年11月3日 下午2:04:31
	 *@author: Angelo yin
	 *@param appVersion
	 *@return
	 */
	Integer modify(AppVersion appVersion);
	
	/**
	 * 修改logo图片吗路径
	 *
	 *创建时间: 2017年11月3日 下午2:10:17
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	Integer deleteApkFile(@Param("id")Integer id);
}
