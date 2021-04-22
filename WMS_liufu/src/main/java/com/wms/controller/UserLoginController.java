package com.wms.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wms.annotation.MyLog;
import com.wms.entity.User;
import com.wms.entity.Warehouse;
import com.wms.repository.UserRepository;
import com.wms.repository.WarehouseRepository;
import com.wms.utils.ReflectUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/account")
public class UserLoginController {
	  @Autowired
	    private UserRepository userRepository;
	  
	  @Autowired
	    private WarehouseRepository warehouseRepository;
	    
	    @MyLog(value = "登录记录")  //这里添加了AOP的自定义注解
	    @PostMapping("/login")
	    public String login(@RequestParam("username") String username, @RequestParam("password") String password, 
	    		@RequestParam("type") String type, HttpSession session, Model model,HttpServletResponse response){
	    	
	    	
	    	
	    	String target = null;
	    	Subject subject = SecurityUtils.getSubject();
			User ac = userRepository.findByName(username);
			if(ac!=null) {
				String salt = ac.getSalt();
				password = new SimpleHash("md5",password,salt,2).toString();
			}
			UsernamePasswordToken token = new UsernamePasswordToken(username,password);
			try {
				subject.login(token);
				User account = (User) subject.getPrincipal();
				  
				if(account == null){
			            target = "login";
			        }else{
			        	String role =account.getRole();
			        	if(role.equals("admin")&&type.equals("admin")) {
			        		 User admin = convertUser(account);
			        		 
				             session.setAttribute("admin",admin);
				           
				             target = "redirect:/account/redirect/main";
			        	}else if(role.equals("user")&&type.equals("user")) {
			        	
			        		 User user = convertUser(account);
				             session.setAttribute("admin",user);
				             target = "redirect:/account/redirect/main3";
			        	}else {
			        		target="login";
			        	}
			        }
				
				subject.getSession().setAttribute("account", account);
				
				return target;
			} catch (UnknownAccountException e) {
				e.printStackTrace();
				model.addAttribute("msg", "用户名不存在");
				return "login";
			} catch (IncorrectCredentialsException e) {
				e.printStackTrace();
				model.addAttribute("msg", "密码有误");
				return "login";
			}
	  }
	    @MyLog(value = "退出")
	    @GetMapping("/logout")
	    public String logout(HttpSession session){
	        session.invalidate();
	        return "login";
	    }

	    @RequestMapping("/redirect/{target}")
	    public String redirect(@PathVariable("target") String target){
	        return target;
	    }

	    private User convertUser(User account){
	        User user = new User();
	        user.setUsername(ReflectUtils.getFieldValue(account,"username")+"");
	        user.setPassword(ReflectUtils.getFieldValue(account,"password")+"");
	        user.setGender(ReflectUtils.getFieldValue(account,"gender")+"");
	        user.setId((long)(ReflectUtils.getFieldValue(account,"id")));
	        user.setNickname(ReflectUtils.getFieldValue(account,"nickname")+"");
	        user.setRegisterdate((Date)(ReflectUtils.getFieldValue(account,"registerdate")));
	        user.setTelephone(ReflectUtils.getFieldValue(account,"telephone")+"");
	        user.setRole(ReflectUtils.getFieldValue(account,"role")+"");
	        user.setPerms(ReflectUtils.getFieldValue(account,"perms")+"");
	        user.setSalt(ReflectUtils.getFieldValue(account,"salt")+"");
	        user.setSalt(ReflectUtils.getFieldValue(account,"w_admin_wId")+"");
	        return user;
	    }

	 
	    
	   
	    
	}
    


