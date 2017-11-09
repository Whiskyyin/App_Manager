package cn.appsys.controller.backend;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.tools.Page;

@Controller
@RequestMapping("/manager/backend/app")
public class AppCheckController {

	@Resource
	private AppInfoService appInfoService;
	public void setAppService(AppInfoService appService) {
		this.appInfoService = appService;
	}

	@Resource
	private DataDictionaryService dataDictionaryService;
	public void setDataService(DataDictionaryService dataService) {
		this.dataDictionaryService = dataService;
	}

	@Resource
	private AppCategoryService appCategoryService;
	public void setCategoryService(AppCategoryService categoryService) {
		this.appCategoryService = categoryService;
	}

	@Resource
	private AppVersionService appVersionService;
	public void setVersionService(AppVersionService versionService) {
		this.appVersionService = versionService;
	}
	
	
	/**
	 * 获取app列表
	 *
	 *创建时间: 2017年11月4日 下午12:32:47
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/list")
	public String appList(Model model,
			@RequestParam(value="querySoftwareName",required=false)String softwareName,@RequestParam(value="queryStatus",required=false)Integer queryStatus,
			@RequestParam(value="queryFlatformId",required=false)Integer flatformId,@RequestParam(value="queryCategoryLevel1",required=false)Integer categoryLevel1,
			@RequestParam(value="queryCategoryLevel2",required=false)Integer categoryLevel2,@RequestParam(value="queryCategoryLevel3",required=false)Integer categoryLevel3,
			@RequestParam(value="pageIndex",required=false)Integer index){
		
		Map<String,Object> map=new HashMap<String,Object>();
		//当前页码
		if(index==null||"".equals(index)){
			index=1;
		}
		//软件名称
		if(softwareName!=null&&!"".equals(softwareName)){
			map.put("softwareName", softwareName);
		}
		//状态
		if(queryStatus!=null&&!"".equals(queryStatus)){
			map.put("status", queryStatus);
		}
		//所属平台
		if(flatformId!=null&&!"".equals(flatformId)){
			map.put("flatformId", flatformId);
		}
		//一级分类
		if(categoryLevel1!=null&&!"".equals(categoryLevel1)){
			map.put("categoryLevel1", categoryLevel1);
		}
		//二级分类
		if(categoryLevel2!=null&&!"".equals(categoryLevel2)){
			map.put("categoryLevel2", categoryLevel2);
		}
		//三级分类
		if(categoryLevel3!=null&&!"".equals(categoryLevel3)){
			map.put("categoryLevel3", categoryLevel3);
		}
		
		map.put("index", (index-1)*Page.SIZE);
		map.put("size", Page.SIZE);
		
		//程序调用
		Page<AppInfo> page=appInfoService.getAppList(map);
		List<DataDictionary> statusList=dataDictionaryService.findDataList("APP_STATUS");
		List<DataDictionary> flatFormList=dataDictionaryService.findDataList("APP_FLATFORM");
		List<AppCategory> categoryLevel1List=appCategoryService.appCategoryList(null);
		List<AppCategory> categoryLevel2List=null;
		List<AppCategory> categoryLevel3List=null;
		 if ((categoryLevel2 != null) && (!categoryLevel2.equals("")))
		  {
		   categoryLevel2List = appCategoryService.appCategoryList(categoryLevel1.toString());
		  }
		  if ((categoryLevel3 != null) && (!categoryLevel3.equals("")))
		  {
		    categoryLevel3List = appCategoryService.appCategoryList(categoryLevel2.toString());
		  }
		
		//传页码
		page.setCurrentPageNo(index);
		
		//传参
		model.addAttribute("appInfoList", page.getList());
		model.addAttribute("statusList", statusList);
		model.addAttribute("flatFormList", flatFormList);
		model.addAttribute("categoryLevel1List", categoryLevel1List);
		model.addAttribute("categoryLevel2List", categoryLevel2List);
		model.addAttribute("categoryLevel3List", categoryLevel3List);
		
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryFlatformId", flatformId);
		model.addAttribute("querySoftwareName", softwareName);
		model.addAttribute("queryCategoryLevel1", categoryLevel1);
		model.addAttribute("queryCategoryLevel2", categoryLevel2);
		model.addAttribute("queryCategoryLevel3", categoryLevel3);
		
		model.addAttribute("pages", page);
		
		return "/backend/applist";
	}
	
	/**
	 * 分级查询
	 *
	 *创建时间: 2017年11月4日 下午12:37:05
	 *@author: Angelo yin
	 *@param pid
	 *@return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET,produces="application/json")
	@ResponseBody
	public Object categorylevellist(@RequestParam("pid")String pid){
		
		JSONArray json=new JSONArray();
		List<AppCategory> categoryLevelList=null;
		if(pid!=null||!"".equals(pid)){
			categoryLevelList=appCategoryService.appCategoryList(pid);
		}
		if(pid==null||"".equals(pid)){
			categoryLevelList=appCategoryService.appCategoryList(null);
		}
		for (AppCategory appCategory : categoryLevelList) {
			JSONObject object=new JSONObject();
			object.put("id", appCategory.getId());
			object.put("categoryName", appCategory.getCategoryName());
			json.add(object);
		}
		
		return json;
	}
	
	/**
	 * 审核（跳转）
	 *
	 *创建时间: 2017年11月4日 下午1:08:12
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/check")
	public String check(@RequestParam("aid")Integer aid,
			@RequestParam("vid")Integer vid,Model model){
		
		AppInfo appInfo=appInfoService.getAppInfoView(aid);
		AppVersion appVersion=appVersionService.getAppVersionById(vid);
		model.addAttribute("appInfo", appInfo);
		model.addAttribute("appVersion", appVersion);
		return "/backend/appcheck";
	}
	
	/**
	 * 修改审核状态
	 *
	 *创建时间: 2017年11月4日 下午1:56:30
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/checksave")
	public String checkSave(@RequestParam("status")String status,@RequestParam(value="id",required=false)String aid){
		
		appInfoService.updateSatus(Integer.parseInt(status), Integer.parseInt(aid));
	
		return "redirect:/manager/backend/app/list";
	}
}
