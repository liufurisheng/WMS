package com.wms.controller;


import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.wms.annotation.MyLog;

import com.wms.entity.Result;
import com.wms.entity.Wms_supplier;

import com.wms.repository.SupplierRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;


/**
 * 供应商信息管理请求 Handler
 *
 * @author Ken
 */
@RequestMapping(value = "/supplierManage")
@Controller
public class SupplierManageController {

	  @Autowired
	  private SupplierRepository supplierRepository;
	 
	
	  /**
	     * 导出供应商信息
	     *
	     * @param Type 查找类型
	     * @param key    查找关键字
	     * @param response   HttpServletResponse
	 * @throws IOException 
	     */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
           
	        // 根据查询类型进行查询
	        List<Wms_supplier>  wms_suppliers = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 wms_suppliers=supplierRepository.findById(0, 10000, id);
            }else if(type.equals("name")) {
	    		
	    		wms_suppliers=supplierRepository.findByName(0, 10000, key);
	    	}else {
	    		 wms_suppliers=supplierRepository.findAll(0, 10000);
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, wms_suppliers, Wms_supplier.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	    }
	   
		//postman携带file文件传参可以进，然后进去类封装
	    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> readExcel(MultipartFile file){

	        long t1 = System.currentTimeMillis();
	        List<Wms_supplier> list = ExcelUtils.readExcel("", Wms_supplier.class, file);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
	       
	        Wms_supplier supplier = new Wms_supplier();
	        for (Wms_supplier object : list) { 
	        	supplier.setName(object.getName());
	        	supplier.setAddress(object.getAddress());
	        	supplier.setEmail(object.getEmail());
	        	supplier.setPerson(object.getPerson());
	        	supplier.setTel(object.getTel());
	        	supplierRepository.insert(supplier);
	        }
	        Map map =new HashMap();
	        map.put("msg", "success");
			return map;
	    }
	  
	  
	  
	    
	    @GetMapping("/findAll")
		@ResponseBody
		public Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
				,@RequestParam("type") String type,@RequestParam("key") String key,HttpSession session){
	    	 session.setAttribute("type",type);
	    	 session.setAttribute("key",key);
	    	int index =(page-1)*limit;
	    	List<Wms_supplier> wms_suppliers = null ;
            if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 wms_suppliers=supplierRepository.findById(index, limit, id);

	    		
	    	}else if(type.equals("name")) {
	    		
	    		wms_suppliers=supplierRepository.findByName(index, limit, key);
	    	}else {
	    		 wms_suppliers=supplierRepository.findAll(index, limit);
	    	}
	    	    int count=supplierRepository.count();
		    	//Wms_supplierVo wms_supplierVo=new Wms_supplierVo(0,"",count,wms_suppliers);
		    	Result result = ResultUtil.success();
		    	result =  ResultUtil.success(count, wms_suppliers);
		    	
		        return result;
			
		    }
	    
	    
	    @MyLog(value = "添加供应商信息")
	    @PostMapping("/save")
		 public String save( Wms_supplier wms_supplier) {
	    	supplierRepository.insert(wms_supplier);
			return "redirect:/supplierManage/redirect/wms_supplierManagement";
			 
		 }
	    
	    //@ResponseBody
	    @GetMapping("/findById/{id}")
	    public String findById(@PathVariable("id") long id,Model model){
	    	Wms_supplier wms_supplier=supplierRepository.find_ById(id);
	        model.addAttribute("wms_supplier",wms_supplier);
	        return "wms_update_supplier";
	    }
	 @MyLog(value = "修改供应商信息")
	 @PostMapping("/update")
	    public String update(Wms_supplier wms_supplier){
		 supplierRepository.update(wms_supplier);
	        return "redirect:/supplierManage/redirect/wms_supplierManagement";
	    }
	 @MyLog(value = "删除供应商信息")
	 @GetMapping("/deleteById/{id}")
	  public String deleteById(@PathVariable("id") int id){
	        
		 supplierRepository.deleteById(id);
	        
	        return "redirect:/supplierManage/redirect/wms_supplierManagement";
	       
	    }
	    
	    
	    
		 @GetMapping("/redirect/{location}")
			public String redirect(@PathVariable("location")String location) {
				return location;
			}
}
