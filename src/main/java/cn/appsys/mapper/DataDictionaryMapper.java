package cn.appsys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryMapper {
	
	/**
	 * 查询状态
	 * @return
	 */
	List<DataDictionary> findDataList(@Param("typeCode")String typeCode);
}
