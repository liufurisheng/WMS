package com.wms.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import com.wms.annotation.MyLog;
import com.wms.entity.Customer;
import com.wms.entity.Result;
import com.wms.entity.SysLog;
import com.wms.repository.SysLogRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;


@RequestMapping(value = "/sysLogManage")
@Controller
public class SysLogController {
	 @Autowired
	 private SysLogRepository sysLogRepository;
	 
	 /*导出供应商信息
      */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
         
	      
	        // 根据查询类型进行查询
	        List<SysLog>  sysLogs = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 sysLogs=sysLogRepository.findById(id);
          }else if(type.equals("name")) {
	    		
        	  sysLogs=sysLogRepository.findByName(key);
	    	}else {
	    		sysLogs=sysLogRepository.findAll();
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, sysLogs, SysLog.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	        
	      
	    }
	   
	    @GetMapping("/findAll")
		@ResponseBody
		public Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
				,@RequestParam("type") String type,@RequestParam("key") String key
				,@RequestParam("beganTime") Date beganTime,@RequestParam("endTime") Date endTime,
				HttpSession session){
		     
		     session.setAttribute("type",type);
	    	 session.setAttribute("key",key);
	    	 
	    	int index =(page-1)*limit;
	    	List<SysLog> sysLogs = null ;
           
	    	if(type.equals("operation")) {
	    		
	    		 sysLogs=sysLogRepository.findByOperationPage(index, limit, key,beganTime,endTime);

	    		
	    	}else if(type.equals("username")) {
	    		
	    		sysLogs=sysLogRepository.findByNamePage(index, limit, key,beganTime,endTime);
	    	}else {
	    		sysLogs=sysLogRepository.findAllPage(index, limit,beganTime,endTime);
	    	}
	    	    int count=sysLogRepository.count();
	    	    Result result = ResultUtil.success();
	    	    result =  ResultUtil.success(count, sysLogs);
		    	//CustomerVo customerVo=new CustomerVo(0,"",count,customers);
		        return  result;
			}
	   
	      @MyLog(value = "删除系统记录")
		  @PostMapping("/delAll")
	      @ResponseBody  //传string类型的id集合用逗号分隔开的
		  public Map<String,Object> deleteById(String isStr){
	    	  Map map=new HashMap();
	    	  //不为空不为null
	    	  System.out.println("传过来要删除的id"+isStr);
	    	  if (isStr!=null &&!isStr.equals("")) {
	    		  String[] ids = isStr.split(",");
	    		  for (String id : ids) {
	    			 //前端最前个逗号前是空的
	    			  if(id!=null &&!id.equals("")){
	    				  sysLogRepository.deleteById(id);
	    			  }
	    		  }map.put("msg", "success");
	    	  }else {map.put("msg", "error");}
	    	  
		         return map;
		       
		    }
	   
	  
	   @InitBinder//局部日期转换方法
	    public void initBinder(WebDataBinder binder, WebRequest request) {
	        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
	        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	    }
	   
	   @GetMapping("/redirect/{location}")
		public String redirect(@PathVariable("location")String location) {
			return location;
		}
}
