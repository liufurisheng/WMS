package com.wms.controller;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.wms.annotation.MyLog;
import com.wms.entity.Customer;
import com.wms.entity.Goods;
import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.entity.UserVo;
import com.wms.entity.Warehouse;
import com.wms.entity.Wms_supplier;

import com.wms.repository.UserRepository;
import com.wms.repository.WarehouseRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@Controller
@RequestMapping("/user")
public class UserHandler {

    @Autowired
    private UserRepository userRepository;
   
    @Autowired
	  private WarehouseRepository warehouseRepository;//查询仓库id用
   
    /*导出供应商信息
     */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
        
	       // String fileName = "supplierInfo.xlsx";
	        // 根据查询类型进行查询
	        List<User>  users = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 users=userRepository.findByIdPage(0, 5, id);
         }else if(type.equals("name")) {
	    		
        	 users=userRepository.findByNamePage(0, 5, key);
	    	}else {
	    		users=userRepository.findAll(0, 5);
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, users, User.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	        
	       /* if (wms_suppliers == null) {
	        	
	        }else {
	        	
	        }
	        // 获取生成的文件
	        File file = ejConvertor.excelWriter(Wms_supplier.class, wms_suppliers);
	       // 写出文件
	        if (file != null) {
	            // 设置响应头
	            response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
	            FileInputStream inputStream = new FileInputStream(file);
	            OutputStream outputStream = response.getOutputStream();
	            byte[] buffer = new byte[8192];

	            int len;
	            while ((len = inputStream.read(buffer, 0, buffer.length)) > 0) {
	                outputStream.write(buffer, 0, len);
	                outputStream.flush();
	            }

	            inputStream.close();
	            outputStream.close();
	        }*/
	       
	    }
   /* @GetMapping("/findAll")
    @ResponseBody
    public UserVo findAll(@RequestParam("page") int page, @RequestParam("limit") int limit){
    	  int index =(page-1)*limit;
    	  
    	  List<User> data=userRepository.findAll(index, limit);
	      int count=userRepository.count();
	      UserVo userVo=new UserVo();
	      userVo.setCode(0);
	      userVo.setMsg("");
	      userVo.setCount(count);
	      userVo.setData(data);
	    
	      return userVo;
       
    }*/
    
    @GetMapping("/findAll")
  		@ResponseBody
  		public Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
  				,@RequestParam("type") String type,@RequestParam("key") String key,HttpSession session){
  	    	
    	    session.setAttribute("type",type);
  	    	session.setAttribute("key",key);
  	    	int index =(page-1)*limit;
  	    	List<User> users = null ;
            if(type.equals("id")) {
  	    		 int id = Integer.parseInt(key);
  	    		 users=userRepository.findByIdPage(index, limit, id);
	        }else if(type.equals("name")) {
  	    		users=userRepository.findByNamePage(index, limit, key);
  	    	}else {
  	    		users=userRepository.findAll(index, limit);
  	    	}
  	    	    int count=userRepository.count();
  	    	    Result result = ResultUtil.success();
		    	result =  ResultUtil.success(count, users);
  	    	    //UserVo userVo=new UserVo();
  		        //userVo.setCode(0);
  		       // userVo.setMsg("");
  		       // userVo.setCount(count);
  		      //  userVo.setData(users);
  		        return result;
  			
  		    }
    @MyLog(value = "添加管理员信息")
    @PostMapping("/save")
    public String save(User user){
        
        user.setRegisterdate(new Date());
        String salt = new SecureRandomNumberGenerator().nextBytes().toString();
		String password= new SimpleHash("md5",user.getPassword(),salt,2).toString();
		user.setPassword(password);
		user.setSalt(salt);
		user.setRole("user");
		user.setPerms("perms");
		user.setW_admin_wId(null);
		userRepository.save(user);
        return "redirect:/user/redirect/wms_userManagement";
        //return "redirect:/user/redirect/user_manage";
    }
    
    @GetMapping("/findById/{id}")
    public String findById(@PathVariable("id") long id,Model model){
    	User user=userRepository.findById(id);
    	
    	//发现空置的仓库id与本身的userRepository.xx
    	
    	List<Warehouse> warehouses=warehouseRepository.findAllId();
    	
        model.addAttribute("user",user);
        model.addAttribute("warehouses",warehouses);
        return "wms_update_user";
     
    }
    @MyLog(value = "删除管理员")
    @GetMapping("/deleteById/{id}")
    public String deleteById(@PathVariable("id") long id){
       
    	 userRepository.deleteById(id);
        return "redirect:/user/redirect/wms_userManagement";
    }
    @MyLog(value = "修改管理员信息")
    @PostMapping("/update")
    public String update(User user){
    	userRepository.update(user);
        return "redirect:/user/redirect/wms_userManagement";
    }
    
    
   
    @GetMapping("/redirect/{location}")
	public String redirect(@PathVariable("location")String location) {
		return location;
	}
    @MyLog(value = "管理员密码修改验证")
    @PostMapping("/verifyPassword")
    public String login(@RequestParam("id") String id,@RequestParam("username") String username, @RequestParam("password") String password1,  HttpSession session, Model model){
    	 String target = null;
    	Subject subject = SecurityUtils.getSubject();
		User ac = userRepository.findByName(username);
		String password=null;
		String verifyPassword=ac.getPassword();
		if(ac!=null) {
			String salt = ac.getSalt();
			 password = new SimpleHash("md5",password1,salt,2).toString();
		}
	//	UsernamePasswordToken token = new UsernamePasswordToken(username,password);
		try {
			//subject.login(token);
			User account = (User) subject.getPrincipal();
			  
			if(account == null){
		            target = "login";
		        }else if(verifyPassword.equals(password)){
		        	
		        	target = "redirect:/user/findUserById/"+id;
		        }else {
		        	 target = "wms_update_self";
		        }
		//	subject.getSession().setAttribute("account", account);
			
		} catch (UnknownAccountException e) {
			e.printStackTrace();
			model.addAttribute("msg", "用户名不存在");
			return "wms_update_self";
		} catch (IncorrectCredentialsException e) {
			e.printStackTrace();
			model.addAttribute("msg", "密码有误");
			return "wms_update_self";
		}return target;
  }
    
    @GetMapping("/findUserById/{id}")
    public String findUserById(@PathVariable("id") long id,Model model){
    	User user=userRepository.findById(id);
    	 model.addAttribute("user",user);
      
        return "wms_update_selfPassword";
     
    }
    @MyLog(value = "管理员修改密码")
    @PostMapping("/updatePassword")
    public String updatePassword(User user){
        long id=user.getId();
    	String salt=userRepository.findSalt(id);
        String password= new SimpleHash("md5",user.getPassword(),salt,2).toString();
		user.setPassword(password);
		userRepository.updatePassword(user);
        return "redirect:/user/redirect/wms_update_self";
        
    }
    
}
