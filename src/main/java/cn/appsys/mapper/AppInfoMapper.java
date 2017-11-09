package cn.appsys.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppInfo;

public interface AppInfoMapper {
	
	/**
	 * app信息列表
	 * @return
	 */
	List<AppInfo> findAppList(Map<String,Object> map);
	
	/**
	 * 查询数量
	 * @param map
	 * @return
	 */
	Integer countAppInfo(Map<String,Object> map);
	
	/**
	 * 查询APK
	 *
	 *创建时间: 2017年11月2日 下午1:26:15
	 *@author: Angelo yin
	 *@return
	 */
	Integer findAPKName(@Param("APKName")String APKName);
	
	/**
	 *添加
	 *
	 *创建时间: 2017年11月2日 下午2:18:42
	 *@author: Angelo yin
	 *@param appinfo
	 *@return
	 */
	Integer add(AppInfo appinfo);
	
	/**
	 * 查看详细
	 *
	 *创建时间: 2017年11月2日 下午3:41:18
	 *@author: Angelo yin
	 *@return
	 */
	AppInfo getAppInfoView(@Param("id")Integer id);
	
	/**
	 * 修改
	 *
	 *创建时间: 2017年11月2日 下午4:56:42
	 *@author: Angelo yin
	 *@param appinfo
	 *@return
	 */
	Integer modify(AppInfo appinfo);
	
	/**
	 * 删除Logo
	 *
	 *创建时间: 2017年11月2日 下午5:21:04
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	Integer deleteAppLogo(@Param("id")Integer id);
	
	/**
	 * 删除appInfo信息
	 *
	 *创建时间: 2017年11月3日 上午9:14:54
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	Integer deleteAppInfoById(@Param("id")Integer id);
	
	/**
	 * 修改状态
	 *
	 *创建时间: 2017年11月3日 下午3:56:04
	 *@author: Angelo yin
	 *@param id
	 *@return
	 */
	Integer updateSatus(@Param("status")Integer status, @Param("id")Integer id);
}
