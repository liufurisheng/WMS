package com.wms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
    // 返回视图页面
    @RequestMapping("index")
    public String index(){
        return "index";
    }
    @RequestMapping("/")
    public String x(){
        return "login";
    }
    
    @RequestMapping("wms_outStorageManagement")
    public String wms_outStorageManagement(){
        return "wms_outStorageManagement";
    }
  
    
    
    @RequestMapping("wms_update_user")
    public String wms_update_user(){
        return "wms_update_user";
    }
    
    @RequestMapping("wms_update_supplier")
    public String wms_update_supplier(){
        return "wms_update_supplier";
    }
    
    @RequestMapping("user_add")
    public String user_add(){
        return "user_add";
    }
    
    @RequestMapping("user_manage")
    public String user_manage(){
        return "user_manage";
    }
  @RequestMapping("login")
    public String login(){
        return "login";
    }
    @RequestMapping("main")
    public String main(){
        return "main";
    }
   
   
    
}
