package cn.appsys.service;

import java.util.List;

import cn.appsys.pojo.DataDictionary;

public interface DataDictionaryService {
	
	List<DataDictionary> findDataList(String typeCode);
}
