package com.hqyj.ShiroDemo1;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

public class MC {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
		SecurityManager securityManager = factory.getInstance();
		SecurityUtils.setSecurityManager(securityManager);

		Subject sub = SecurityUtils.getSubject();
		Session session = sub.getSession();
		session.setAttribute("key", "value");

		
		if (!sub.isAuthenticated()) {
			UsernamePasswordToken upt = new UsernamePasswordToken("jane", "123");
			upt.setRememberMe(true);
			try {
				sub.login(upt);
				System.out.println("当前登陆的用户为：" + sub.getPrincipal());
				
				if (sub.hasRole("超级管理员")) {
					System.out.println("该用户具备超级管理员角色");
				} else {
					System.out.println("该用户不具备超级管理员角色");
				}
				
				if(sub.isPermitted("增加")){
					System.out.println("该用户有增加权限");
				}else{
					System.out.println("该用户没有增加权限");
				}
			} catch (Exception e) {
				System.out.println("用户名密码错误");
			}
			
		}

		
	}

}