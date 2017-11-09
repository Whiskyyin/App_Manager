package cn.appsys.test;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.DevUserService;

public class DevUserTest {

	public static void main(String[] args) {
		ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext-mybatis.xml");
		DevUserService devuser=(DevUserService) context.getBean("devUserService");
				DevUser user=devuser.loadBy("test001", "123456");
			System.out.println(user.getDevCode());

	}

}
