package cn.appsys.controller.developer;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.AppVersionService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.tools.Page;


@Controller
@RequestMapping("/dev/flatform/app/")
public class AppController {
	
	private Logger logger = Logger.getLogger(AppController.class);
	
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
	 * App列表
	 * @return
	 */
	@RequestMapping(value="/Applist.html",method=RequestMethod.GET)
	public String getAppList(Model model,
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
		
		return "/developer/appinfolist";
	}
	
	
	/**
	 * 分级查询（json）
	 * @return
	 * 		json数据
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
	 * 新增app(跳转)
	 *
	 *创建时间: 2017年11月2日 上午11:48:50
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/appinfoadd.html")
	public String appinfoAdd(@ModelAttribute("appinfo")AppInfo appinfo){
		
		return "developer/appinfoadd";
	}
	
	/**
	 * 加载平台
	 *
	 *创建时间: 2017年11月2日 上午11:54:14
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/datadictionarylist.json")
	@ResponseBody
	public Object datadictionarylist(@RequestParam("tcode")String typeName){
		JSONArray json=new JSONArray();
		List<DataDictionary> flatFormList=dataDictionaryService.findDataList(typeName);
		for (DataDictionary dataDictionary : flatFormList) {
			JSONObject object=new JSONObject();
			object.put("valueId", dataDictionary.getValueId());
			object.put("valueName", dataDictionary.getValueName());
			json.add(object);
		}
		return json;
	}
	
	/**
	 * 查询是否存在APKName
	 *
	 *创建时间: 2017年11月2日 下午1:34:08
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/apkexist.json")
	@ResponseBody
	public Object apkexist(@RequestParam("APKName")String APKName){
		JSONObject object=new JSONObject();
		Integer count=5;
		if(APKName!=null&&!"".equals(APKName)){
			count=appInfoService.findAPKName(APKName);
		}
		if(APKName.equals("")){
			object.put("APKName", "empty");
		}else if(count>=1){
			object.put("APKName", "exist");
		}else if(count==0){
			object.put("APKName", "noexist");
		}
		return object;
	}
	
	/**
	 * 修改上下架状态
	 *
	 *创建时间: 2017年11月3日 下午4:00:43
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/{appid}/sale",method=RequestMethod.PUT)
	@ResponseBody
	public Object saleStatus(@PathVariable Integer appid,HttpSession session){
		
		JSONObject object=new JSONObject();
		object.put("errorCode", "0");
		if(appid>0){
			try {
				DevUser devUser = (DevUser)session.getAttribute("devUserSession");
				AppInfo appInfo = new AppInfo();
				appInfo.setId(appid);
				appInfo.setModifyBy(devUser.getId());
				if(appInfoService.updataStatusByAppInfo(appInfo)){
					if(appInfoService.modify(appInfo)==1){
						object.put("resultMsg", "success");
					}else{
						object.put("resultMsg", "failed");
					}
				}else{
					object.put("resultMsg", "failed");
				}		
			} catch (Exception e) {
				object.put("errorCode", "exception000001");
			}
		}else{
			//errorCode:0为正常
			object.put("errorCode", "param000001");
		}
		
		/*
		 * resultMsg:success/failed
		 * errorCode:exception000001
		 * appId:appId
		 * errorCode:param000001
		 */
		return object;
		
	}
	
	
	/**
	 * APP信息添加
	 *
	 *创建时间: 2017年11月2日 下午1:48:22
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/appinfoaddsave.html",method=RequestMethod.POST)
	public String appinfoaddsave(AppInfo appInfo,
			HttpSession session, HttpServletRequest request, 
			@RequestParam(value="a_logoPicPath", required=false) MultipartFile attach){
		
		//相对路径
		String logoPicPath=null;
		//绝对路径
		String logoLocPath=null;
		//文件不为空
		if(!attach.isEmpty()){
			//获取上传文件的路径
			String path=request.getSession().getServletContext().getRealPath("/statics"+File.separator+"uploadfiles");
			//获取原文件名
			String oldFileName=attach.getOriginalFilename();
			//原文件名后缀
			String suffix=FilenameUtils.getExtension(oldFileName);
			//最大文件大小
			Integer fileSize=500000;
			
			//当上传文件过大时
			if(attach.getSize()>fileSize){
				request.setAttribute("fileUploadError", "上传大小不得超过500KB");
				return "/developer/appinfoadd";
			}
			//相反的没超过时
			else if(suffix.equalsIgnoreCase("jpg")||suffix.equalsIgnoreCase("png")
					||suffix.equalsIgnoreCase("jpeg")||suffix.equalsIgnoreCase("pneg")){
				
//				String fileName=System.currentTimeMillis()+"_AppInfo.jpg";
				//上传LOGO图片命名:apk名称.apk
				String fileName=appInfo.getAPKName()+".jpg";
				File file=new File(path,fileName);
				if(!file.exists()){
					file.mkdir();
				}
				//保存
				try {
					attach.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "上传失败！");
					return "/developer/appinfoadd";
				} 
				 logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
			     logoLocPath = path + File.separator + fileName;
			     
			     logger.info("相对路径："+logoPicPath);
			     logger.info("绝对路径："+logoLocPath);
			//上传图片格式不正确
			}else{
				request.setAttribute("fileUploadError", "上传文件格式不正确！");
				return "/developer/appinfoadd";
			}	
		}
		
		//传参
		appInfo.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setCreationDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		appInfo.setDevId(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setStatus(Integer.valueOf(1));
		
		if(appInfoService.add(appInfo)==1){
			return "redirect:/dev/flatform/app/Applist.html";
		}else{
			return "/developer/appinfoadd";
		}
	}

	/**
	 * 查看AppInfo详细信息
	 *
	 *创建时间: 2017年11月2日 下午3:51:29
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/appview.html/{id}")
	public String appview(@PathVariable Integer id,Model model){
		AppInfo appinfo=appInfoService.getAppInfoView(id);
		List<AppVersion> appVersionList=appVersionService.getAppVersionList(id);
		model.addAttribute("appInfo", appinfo);
		model.addAttribute("appVersionList", appVersionList);
		return "/developer/appinfoview";
	}

	/**
	 * 修改appinfo信息（跳转）
	 *
	 *创建时间: 2017年11月2日 下午4:52:02
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/appinfomodify")
	public String appinfomodify(@RequestParam("id")Integer id,Model model,
			@RequestParam(value="error",required= false)String fileUploadError){
		AppInfo appInfo=appInfoService.getAppInfoView(id);
		//错误信息
		if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = "上传文件大于500KB，请重新选择！";
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= "上传失败！";
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = "上传文件格式不正确！";
		}else if(null != fileUploadError && fileUploadError.equals("error4")){
			fileUploadError = "";
		}
		
		model.addAttribute("fileUploadError", fileUploadError);
		model.addAttribute("appInfo", appInfo);
		return "/developer/appinfomodify";
	}
	
	/**
	 * 修改AppInfo
	 *
	 *创建时间: 2017年11月3日 下午3:08:18
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/appinfomodifysave.html",method=RequestMethod.POST)
	public String appinfomodifysave(AppInfo appInfo,
			HttpSession session, HttpServletRequest request, 
			@RequestParam(value="attach", required=false) MultipartFile attach){
		
		//相对路径
		String logoPicPath=null;
		//绝对路径
		String logoLocPath=null;
		if(!attach.isEmpty()){
			//获取上传文件的路径
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//获取原文件名
			String oldFileName=attach.getOriginalFilename();
			//原文件名后缀
			String suffix=FilenameUtils.getExtension(oldFileName);
			//最大文件大小
			Integer fileSize=500000;
			
			//当上传文件过大时
			if(attach.getSize()>fileSize){
				return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
				 +"&error=error1";
			}else if(suffix.equalsIgnoreCase("jpg")||suffix.equalsIgnoreCase("png")
					||suffix.equalsIgnoreCase("jpeg")||suffix.equalsIgnoreCase("pneg")){
				
//				String fileName=System.currentTimeMillis()+"_AppInfo.jpg";
				//上传LOGO图片命名:apk名称.apk
				String fileName=appInfo.getAPKName()+".jpg";
				File file=new File(path,fileName);
				if(!file.exists()){
					file.mkdir();
				}
				//保存
				try {
					attach.transferTo(file);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
					 +"&error=error2";
				} 
				//相对路径
				 logoPicPath = request.getContextPath() + "/statics/uploadfiles/" + fileName;
				 //绝对路径
			     logoLocPath = path + File.separator + fileName;
			     
			     logger.info("相对路径："+logoPicPath);
			     logger.info("绝对路径："+logoLocPath);
			//上传图片格式不正确
			}else{
				return "redirect:/dev/flatform/app/appinfomodify?id="+appInfo.getId()
				 +"&error=error3";
			}	
			
		
		}
		
		//传参
		appInfo.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appInfo.setModifyDate(new Date());
		appInfo.setLogoLocPath(logoLocPath);
		appInfo.setLogoPicPath(logoPicPath);
		
		if(appInfoService.modify(appInfo)==1){
			return "redirect:/dev/flatform/app/Applist.html";
		}
		
	return "/developer/appinfomodify";
	}
	
	/**
	 * 删除Logo图片
	 *
	 *创建时间: 2017年11月2日 下午5:11:20
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/delfile.json")
	@ResponseBody
	public Object delfile(@RequestParam(value="flag", required=false) String flag, 
			@RequestParam(value="id", required=false) Integer id){
		
		JSONObject object=new JSONObject();
		//获取路径
		String fileLocPath=null;
		
		//判断是否传参进来
		if(id==null||"".equals(id)||flag==null||flag.equals("")){
			object.put("result", "failed");
		}else if(flag.equals("logo")){
			//获取图片的绝对路径
			fileLocPath=appInfoService.getAppInfoView(id).getLogoLocPath();
			File file=new File(fileLocPath);
			;
			//判断服务器存储的物理文件是否存在
			if(file.exists())
			     if(file.delete()){//删除服务器存储的物理文件
						if(appInfoService.deleteAppLogo(id)==1){//更新表
							object.put("result", "success");
						 }
			}
		}else if(flag.equals("apk")){
			fileLocPath = (appVersionService.getAppVersionById(id)).getApkLocPath();
			File file = new File(fileLocPath);
		    if(file.exists())
		     if(file.delete()){//删除服务器存储的物理文件
					if(appVersionService.deleteApkFile(id)==1){//更新表
						object.put("result", "success");
					 }
		    }
		}
		logger.info(fileLocPath);
		return object;
	}
	
	/**
	 * 删除App及其所有版本
	 *
	 *创建时间: 2017年11月3日 上午9:18:16
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object delapp(@RequestParam("id")Integer id){
		JSONObject object=new JSONObject();
		int count1=appVersionService.deleteVersionByAppId(id);
		int count2=appInfoService.deleteAppInfoById(id);
		if(id.equals("")||id==null){
			object.put("delResult", "notexist");
		}
		if(count2==1){
			object.put("delResult", "true");
		}else{
			object.put("delResult", "false");
		}
		return object;
	}
	
	/**
	 * 新增版本（跳转）
	 *
	 *创建时间: 2017年11月3日 上午9:32:06
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/appversionadd")
	public String appversionadd(@RequestParam("id")Integer id,Model model,
			AppVersion appversion,@RequestParam(value="error",required= false)String fileUploadError){
		
		List<AppVersion> appVersionList=appVersionService.getAppVersionList(id);
		appversion.setAppId(id);
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute(appversion);
		
		//发送错误信息
		if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = "没找到APK，检查该应用是否有APK！";
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= "上传失败！";
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = "APK格式有误！";
		}
		
		model.addAttribute("fileUploadError", fileUploadError);
		return "/developer/appversionadd";
	}
	
	/**
	 * 新增版本
	 *
	 *创建时间: 2017年11月3日 上午10:01:04
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/addversionsave",method=RequestMethod.POST)
	public String addversionsave(AppVersion appversion,HttpSession session,HttpServletRequest request,
			@RequestParam(value="a_downloadLink",required= false) MultipartFile attach,Model model,AppInfo appinfo){
		
		//下载路径
		String downloadLink =  null;
		//绝对路径
		String apkLocPath = null;
		//APK名称
		String apkFileName = null;
		
		if(!attach.isEmpty()){
			//获取路径
			String path=request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");
			//获取原文件名
			String oldFileName= attach.getOriginalFilename();
			//获取文件后缀名
			String suffix=FilenameUtils.getExtension(oldFileName);
			
			if(suffix.equalsIgnoreCase("apk")){//apk文件命名：apk名称+版本号+.apk
				//获取apk名称
				String apkName=appInfoService.getAppInfoView(appversion.getAppId()).getAPKName();;
				
				//apxName是否为空
				 if(apkName == null || "".equals(apkName)){
					 return "redirect:/dev/flatform/app/appversionadd?id="+appversion.getAppId()
					 +"&error=error1";
				 }
				 apkFileName = apkName + "-" +appversion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appversionadd?id="+appversion.getAppId()
					 +"&error=error2";
				} 
				 //路径传参
				 downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				 apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/dev/flatform/app/appversionadd?id="+appversion.getAppId()
				 +"&error=error3";
			}
			
			//传参
			appversion.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
			appversion.setCreationDate(new Date());
			appversion.setDownloadLink(downloadLink);
			appversion.setApkLocPath(apkLocPath);
			appversion.setApkFileName(apkFileName);
			Integer devId=((DevUser)session.getAttribute("devUserSession")).getId();
			//新增版本并修改AppInfo表
			if(appVersionService.add(appversion,appinfo,devId)){
				
				return "redirect:/dev/flatform/app/Applist.html";
			}
		}
	 return "redirect:/dev/flatform/app/appversionadd?id="+appversion.getAppId();
	}
	
	/**
	 * 修改版本(跳转)
	 *
	 *创建时间: 2017年11月3日 下午12:22:17
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("appversionmodify")
	public String appversionmodify(@RequestParam("vid")Integer vid,@RequestParam("aid")Integer aid,
			@RequestParam(value="error",required= false)String fileUploadError,Model model){
		List<AppVersion> appVersionList=appVersionService.getAppVersionList(aid);
		AppVersion appVersion=appVersionService.getAppVersionById(vid);
		//错误信息提示
		if(null != fileUploadError && fileUploadError.equals("error1")){
			fileUploadError = "没找到APK，检查该应用是否有APK！";
		}else if(null != fileUploadError && fileUploadError.equals("error2")){
			fileUploadError	= "上传失败！";
		}else if(null != fileUploadError && fileUploadError.equals("error3")){
			fileUploadError = "APK格式有误！";
		}
		
		model.addAttribute("fileUploadError", fileUploadError);
		model.addAttribute("appVersionList", appVersionList);
		model.addAttribute("appVersion", appVersion);
		return "/developer/appversionmodify";
	}
	
	/**
	 * 修改版本并保存
	 *
	 *创建时间: 2017年11月3日 下午1:29:39
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/appversionmodifysave",method=RequestMethod.POST)
	public String appversionmodifysave(AppVersion appVersion,
			HttpSession session,HttpServletRequest request,
			@RequestParam(value="attach",required= false) MultipartFile attach ){
		//下载地址
		String downloadLink =  null;
		//绝对路径
		String apkLocPath = null;
		//APK名称
		String apkFileName = null;
		//判断是否是空文件
		if(!attach.isEmpty()){
			//设置路径
			String path = request.getSession().getServletContext().getRealPath("statics"+File.separator+"uploadfiles");	
			String oldFileName = attach.getOriginalFilename();//原文件名
			String suffix = FilenameUtils.getExtension(oldFileName);//原文件后缀
			
			if(suffix.equalsIgnoreCase("apk")){
				String apkName=null;
				try {
					apkName = ((AppInfo) appInfoService.getAppInfoView(appVersion.getAppId())).getAPKName();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				//如果没有获取到APKName
				if(apkName==null||"".equals(apkName)){
					return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
					 +"&aid="+appVersion.getAppId()
					 +"&error=error1";
				}
				//定义APK名称
				 apkFileName = apkName + "-" +appVersion.getVersionNo() + ".apk";
				 File targetFile = new File(path,apkFileName);
				 if(!targetFile.exists()){
					 targetFile.mkdirs();
				 }
				 try {
					 //保存
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
					 +"&aid="+appVersion.getAppId()
					 +"&error=error2";
				} 
				 downloadLink = request.getContextPath()+"/statics/uploadfiles/"+apkFileName;
				 apkLocPath = path+File.separator+apkFileName;
			}else{
				return "redirect:/dev/flatform/app/appversionmodify?vid="+appVersion.getId()
				 +"&aid="+appVersion.getAppId()
				 +"&error=error3";
			}			
			
			appVersion.setDownloadLink(downloadLink);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setApkFileName(apkFileName);
		}
		
		//传参，修改AppVersion表
		appVersion.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		appVersion.setModifyDate(new Date());
		
			if(appVersionService.modify(appVersion)==1){
				return "redirect:/dev/flatform/app/Applist.html";
			}
		
		return "developer/appversionmodify";
	}
}
