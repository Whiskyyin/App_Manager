package cn.appsys.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.appsys.mapper.DataDictionaryMapper;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.DataDictionaryService;

@Service("dataDictionaryService")
public class DataDictionaryServiceImpl implements DataDictionaryService {

	@Resource
	private DataDictionaryMapper dataDictionaryMapper;
	public void setDataMapper(DataDictionaryMapper dataMapper) {
		this.dataDictionaryMapper = dataMapper;
	}


	public List<DataDictionary> findDataList(String typeCode) {
		
		return dataDictionaryMapper.findDataList(typeCode);
	}

}
