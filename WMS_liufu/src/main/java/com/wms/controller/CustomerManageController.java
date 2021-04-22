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
import com.wms.entity.Customer;
import com.wms.entity.Goods;
import com.wms.entity.Result;
import com.wms.entity.Storage;
import com.wms.entity.Wms_supplier;

import com.wms.repository.CustomerRepository;
import com.wms.repository.SupplierRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/customerManage")
@Controller
public class CustomerManageController {
	 @Autowired
	  private CustomerRepository customerRepository;
	 
	
	  /*导出供应商信息
       */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
            // 根据查询类型进行查询
	        List<Customer>  customers = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 customers=customerRepository.findById(0, 10000, id);
           }else if(type.equals("name")) {
	    		
        	   customers=customerRepository.findByName(0, 10000, key);
	    	}else {
	    		customers=customerRepository.findAll(0, 10000);
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, customers, Customer.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	    
	   }
	   
	 //postman携带file文件传参可以进，然后进去类封装
	    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> readExcel(MultipartFile file){

	        long t1 = System.currentTimeMillis();
	        List<Customer> list = ExcelUtils.readExcel("", Customer.class, file);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
	        Customer customer =new Customer();
	        for (Customer object : list) { 
	        	customer.setName(object.getName());
	        	customer.setAddress(object.getAddress());
	        	customer.setEmail(object.getEmail());
	        	customer.setPerson(object.getPerson());
	        	customer.setTel(object.getTel());
	        	customerRepository.insert(customer);
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
	    	List<Customer> customers = null ;
            
	    	if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 customers=customerRepository.findById(index, limit, id);

	    		
	    	}else if(type.equals("name")) {
	    		
	    		customers=customerRepository.findByName(index, limit, key);
	    	}else {
	    		customers=customerRepository.findAll(index, limit);
	    	}
	    	    int count=customerRepository.count();
	    	    Result result = ResultUtil.success();
	    	    result =  ResultUtil.success(count, customers);
		    	//CustomerVo customerVo=new CustomerVo(0,"",count,customers);
		        return  result;
			
		    }
	    
	    
	    @MyLog(value = "添加新客户")  //这里添加了AOP的自定义注解
	    @PostMapping("/save")
		 public String save( Customer customer) {
	    	customerRepository.insert(customer);
			return "redirect:/customerManage/redirect/wms_customerManagement";
		}
	    
	   
	    @GetMapping("/findById/{id}")
	    public String findById(@PathVariable("id") long id,Model model){
	    	Customer customer=customerRepository.find_ById(id);
	        model.addAttribute("customer",customer);
	        return "wms_update_customer";
	    }
	 
	 @MyLog(value = "修改客户信息")  //这里添加了AOP的自定义注解
	 @PostMapping("/update")
	    public String update(Customer customer){
		 customerRepository.update(customer);
	        return "redirect:/customerManage/redirect/wms_customerManagement";
	    }
	 
	 @MyLog(value = "删除客户信息")
	 @GetMapping("/deleteById/{id}")
	  public String deleteById(@PathVariable("id") int id){
	        
		 customerRepository.deleteById(id);
	        
	        return "redirect:/customerManage/redirect/wms_customerManagement";
	       
	    }
	    
	    
	    
		 @GetMapping("/redirect/{location}")
			public String redirect(@PathVariable("location")String location) {
				return location;
			}
}


