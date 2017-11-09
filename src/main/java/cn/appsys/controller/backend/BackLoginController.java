package cn.appsys.controller.backend;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.BackendUser;
import cn.appsys.service.BackendUserService;

@Controller
@RequestMapping("/manager")
public class BackLoginController {

	@Resource
	private BackendUserService backendUserService;
	public void setBackendUserService(BackendUserService backendUserService) {
		this.backendUserService = backendUserService;
	}

	/**
	 * 登陆（跳转）
	 *
	 *创建时间: 2017年11月3日 下午4:51:22
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping("/login.html")
	public String login(){
		
		return "backendlogin";
	}
	
	/**
	 * 登陆
	 *
	 *创建时间: 2017年11月3日 下午5:04:05
	 *@author: Angelo yin
	 *@return
	 */
	@RequestMapping(value="/dologin",method=RequestMethod.POST)
	public String doLogin(HttpSession session,Model model,
			@RequestParam("userCode")String userCode,@RequestParam("userPassword")String password){
		BackendUser user=backendUserService.getLoginUser(userCode, password);
		if(user!=null){
			session.setAttribute("userSession", user);
			return "/backend/main";
		}else{
			model.addAttribute("error", "密码错误！");
			return "backendlogin";
		}
		
	}

	/**
	 * 退出
	 *
	 *创建时间: 2017年11月3日 下午5:17:36
	 *@author: Angelo yin
	 *@param session
	 *@return
	 */
	@RequestMapping("/logout")
	public String loginout(HttpSession session){
		session.removeAttribute("userSession");
		return "../../index";
	}
	
	
}
