package cn.appsys.controller.developer;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;

@Controller
@RequestMapping("/dev")
public class DevLoginController {
	
	@Resource
	private DevUserService devUserService;
	public void setDevUser(DevUserService devUser) {
		this.devUserService = devUser;
	}

	/**
	 * 登陆(跳转)
	 * @return
	 */
	@RequestMapping("/login.html")
	public String login(){
		
		return "devlogin";
	}
	
	/**
	 * 登陆
	 * @return
	 */
	@RequestMapping(value="/dologin.html",method=RequestMethod.POST)
	public String loginDo(HttpSession session,Model model,
			@RequestParam("devCode")String userCode,@RequestParam("devPassword")String password){
		DevUser user=devUserService.loadBy(userCode, password);
		if(user!=null){
			session.setAttribute("devUserSession", user);
			return "/developer/main";
		}else{
			model.addAttribute("error", "密码错误！");
			return "devlogin";
		}
	}
	
	/**
	 * 退出
	 * @return
	 */
	@RequestMapping("/logout.html")
	public String loginout(HttpSession session){
		session.removeAttribute("devUserSession");
		return "../../index";
	}
}
