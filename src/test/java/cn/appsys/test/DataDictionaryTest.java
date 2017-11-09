package cn.appsys.test;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.service.DevUserService;

public class DataDictionaryTest {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
		DataDictionaryService dataService=(DataDictionaryService) context.getBean("dataDictionaryService");
		List<DataDictionary> datalist=dataService.findDataList("APP_STATUS");
		System.out.println(datalist.size());
				 
	}

}
