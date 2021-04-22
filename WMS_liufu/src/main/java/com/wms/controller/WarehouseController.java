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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.wms.annotation.MyLog;
import com.wms.entity.Customer;
import com.wms.entity.Result;
import com.wms.entity.Warehouse;

import com.wms.repository.WarehouseRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/warehouseManage")
@Controller
public class WarehouseController {
	 @Autowired
	  private WarehouseRepository warehouseRepository;
	 
	
	  /*导出供应商信息
       */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
            // 根据查询类型进行查询
	        List<Warehouse>  warehouses = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 warehouses=warehouseRepository.findById(0, 5, id);
           }else if(type.equals("name")) {
	    		
        	   warehouses=warehouseRepository.findByName(0, 5, key);
	    	}else {
	    		warehouses=warehouseRepository.findAll(0, 5);
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, warehouses, Warehouse.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	    }
	   
	 //postman携带file文件传参可以进，然后进去类封装
	    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> readExcel(MultipartFile file){

	        long t1 = System.currentTimeMillis();
	        List<Warehouse> list = ExcelUtils.readExcel("", Warehouse.class, file);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
	       
	        Warehouse warehouse =new Warehouse();
	        for(Warehouse object:list) {
	        	warehouse.setAddress(object.getAddress());
	        	warehouse.setArea(object.getArea());
	        	warehouse.setDesc(object.getDesc());
	        	warehouse.setStatus(object.getStatus());
	        	warehouseRepository.insert(warehouse);
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
	    	List<Warehouse> warehouses = null ;
            
	    	if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 warehouses=warehouseRepository.findById(index, limit, id);

	    		
	    	}else if(type.equals("name")) {
	    		
	    		warehouses=warehouseRepository.findByName(index, limit, key);
	    	}else {
	    		warehouses=warehouseRepository.findAll(index, limit);
	    	}
	    	    int count=warehouseRepository.count();
		    	//WarehouseVo warehouseVo=new WarehouseVo(0,"",count,warehouses);
		    	Result result = ResultUtil.success();
		    	result =  ResultUtil.success(count, warehouses);
		        return result;
			
		    }
	    
	    
	    @MyLog(value = "添加仓库信息")
	    @PostMapping("/save")
		 public String save( Warehouse warehouse) {
	    	warehouseRepository.insert(warehouse);
			return "redirect:/warehouseManage/redirect/wms_warehouseManagement";
			 
		 }
	    
	   
	    @GetMapping("/findById/{id}")
	    public String findById(@PathVariable("id") long id,Model model){
	    	Warehouse warehouse=warehouseRepository.find_ById(id);
	        model.addAttribute("warehouse",warehouse);
	        return "wms_update_warehouse";
	    }
	 
	 @MyLog(value = "修改仓库信息")
	 @PostMapping("/update")
	    public String update(Warehouse warehouse){
		 warehouseRepository.update(warehouse);
	        return "redirect:/warehouseManage/redirect/wms_warehouseManagement";
	    }
	 @MyLog(value = "删除仓库信息")
	 @GetMapping("/deleteById/{id}")
	  public String deleteById(@PathVariable("id") int id){
	        
		 warehouseRepository.deleteById(id);
	        
	        return "redirect:/warehouseManage/redirect/wms_warehouseManagement";
	       
	    }
	
	/* 看看返回那样的json内容的
	 *  @GetMapping("/try")
	  @ResponseBody
	 public List<Object> findAllId(Model model){
	        
		 List<Object> warehouse_id= warehouseRepository.findAllId();
		 model.addAttribute("warehouse_id",warehouse_id);
	     return warehouse_id;
	     }*/
	
	    
	    
		 @GetMapping("/redirect/{location}")
			public String redirect(@PathVariable("location")String location) {
				return location;
			}
}

