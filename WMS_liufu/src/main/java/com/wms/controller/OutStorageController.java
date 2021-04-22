package com.wms.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CookieValue;
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
import com.wms.entity.Goods;
import com.wms.entity.InStorage;

import com.wms.entity.OutStorage;

import com.wms.entity.Result;
import com.wms.entity.Storage;
import com.wms.entity.User;
import com.wms.entity.Warehouse;
import com.wms.repository.CustomerRepository;
import com.wms.repository.GoodsRepository;
import com.wms.repository.OutStorageRepository;
import com.wms.repository.StorageRepository;
import com.wms.repository.WarehouseRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/OutStorageManage")
@Controller
public class OutStorageController {
	Map<String,  Object> map=new HashMap<String, Object>();
	@Autowired
	private OutStorageRepository outStorageRepository;
	
	@Autowired
	private StorageRepository storageRepository;
	
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private CustomerRepository customerRepository;
	
	// ,@CookieValue(value = "username",defaultValue = "username") String username
	//出库
	 @MyLog(value = "出库记录")  //这里添加了AOP的自定义注解
	 @PostMapping("/save")
	 @ResponseBody
	 public Map<String,Object> save( OutStorage outStorage
			,HttpSession session) {
	  synchronized(this){
		 //取执行此操作者的名字
		  User user = (User) SecurityUtils.getSubject().getPrincipal();
		 String username=null;
		 if(user==null) {
			 username="没人";
		 }else {
			 username=user.getUsername();
		 }
		 
		 Map<String,Object> map=new HashMap<String, Object>();
		
         int goodId=outStorage.getGoodId();
		 int warehouseId=outStorage.getWarehouseId();
		 int reduceNumber=(int) outStorage.getNumber();
		 
		 System.out.println("GoodId"+goodId+"WarehouseId"+warehouseId+"Number"+reduceNumber);
		 
		 Storage haveOrNo=storageRepository.findNumberBoolean(goodId, warehouseId);
		 //System.out.println("原GoodId"+haveOrNo.getGoodsId()+"原WarehouseId"+haveOrNo.getWarehouseId()+"原Number"+haveOrNo.getNumber());
		
		
		 if(haveOrNo != null) {
			     Long OriginalNumber=haveOrNo.getNumber();
			    if(OriginalNumber > reduceNumber) {
				
				 long finalNumber = OriginalNumber-reduceNumber;
				 //修改库存
				 haveOrNo.setNumber(finalNumber);
				 haveOrNo.setGoodsId(goodId);
				 haveOrNo.setWarehouseId(warehouseId);
				 storageRepository.update(haveOrNo);
				 //记录入出库信息表
				 outStorage.setPerson(username);
				 Date time =new Date();
				 outStorage.setTime(time);
				 outStorageRepository.insert(outStorage);
				 String Info="出库成功";
				  map.put("msg", Info);
			}else {
				 String Info="出库错误，请检查出库数量";
				  map.put("msg", Info);
			}
		}else {
			 
			 String Info="出库错误，请检查出库信息";
			  map.put("msg", Info);
		 }
		 
		return map;
	   }
     }
	 
	   @GetMapping("/findAllInfo")
	   @ResponseBody
	    public Map<String, Object>findAllWarehouseId(){
		   
		   List<Customer> customers= customerRepository.find_All();
			List<Goods> goods = goodsRepository.find_All();
		    List<Warehouse> allWarehouses=warehouseRepository.findAll_Id();
			map.put("allWarehouses", allWarehouses);
			map.put("goods", goods);
			map.put("customers",customers);
	        return map;
	    }
	   
	
	   
	   @GetMapping("/findCustomerByCustomerName")
	   @ResponseBody
	    public Map<String, Object>findCustomerByCustomerName(@RequestParam("customerName") String customerName){
		   Customer customer = customerRepository.findBy_Name(customerName);
			map.put("customer", customer);
	        return map;
	    }
	 
	   @GetMapping("/redirect/{location}")
		public String redirect(@PathVariable("location")String location) {
			return location;
		}
	 
	   
	   @InitBinder//局部日期转换方法,将前端传的string转为日期格式
	    public void initBinder(WebDataBinder binder, WebRequest request) {
	        //转换日期 注意这里的转化要和传进来的字符串的格式一直 如2015-9-9 就应该为yyyy-MM-dd
	        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor为自定义日期编辑器
	    }
	   
	   
	   /*导出供应商信息
	      */
		   @SuppressWarnings("unchecked")
		    @GetMapping("/exportExcel")
		    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
	         
		        // 根据查询类型进行查询
		        List<OutStorage>  outStorage = null;
		        
		        String type=(String)session.getAttribute("type");
		       
		        String key=(String)session.getAttribute("key");
		       
		        int WarehouseId=(int)session.getAttribute("warehouseId");
		       
		        Date beganTime=(Date) session.getAttribute("beganTime");
	 	    	Date endTime=(Date) session.getAttribute("endTime");
		        
	 	    	if(type.equals("id")) {
	 	    		 int id = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllById(0, 10000, id,WarehouseId,beganTime,endTime);
	 	
	 	    	}else if(type.equals("goodId")) {
	 	    		int goodId = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllByGoodId(0, 10000, goodId,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("goodName")) {
	 	    		outStorage =outStorageRepository.findAllByGoodName(0, 10000, key,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("supplierId")) {
	 	    		int supplierId = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllByCustomerId(0, 10000, supplierId,WarehouseId,beganTime,endTime);
	 		    }else if(type.equals("supplierName")) {
	 		    	outStorage =outStorageRepository.findAllByCustomerName(0, 10000, key,WarehouseId,beganTime,endTime);
	 		    }else {
	 		    	outStorage =outStorageRepository.findAllAndWarehouseId(0, 10000,WarehouseId,beganTime,endTime);
	 	    	}
		        long t1 = System.currentTimeMillis();
		        ExcelUtils.writeExcel(response, outStorage, OutStorage.class);
		        long t2 = System.currentTimeMillis();
		        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
		        
		    }
	   
	   
	   //入库记录查询
	   @GetMapping("/findAll")
	 		@ResponseBody
	 		public Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
	 				,@RequestParam("type") String type,@RequestParam("key") String key,
	 				@RequestParam("warehouseId") int WarehouseId,
	 				@RequestParam("beganTime") Date beganTime,@RequestParam("endTime") Date endTime,
	 				HttpSession session,Model model){
	 	    	 
		     session.setAttribute("type",type);
	    	 session.setAttribute("key",key);
	    	 session.setAttribute("warehouseId", WarehouseId);
	    	 session.setAttribute("beganTime", beganTime);
	    	 session.setAttribute("endTime", endTime);
	 	    
	 	    	 
	 	    	int index =(page-1)*limit;
	 	    	List<OutStorage> outStorage  = null ;
	          
	 	    	if(type.equals("id")) {
	 	    		 int id = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllById(index, limit, id,WarehouseId,beganTime,endTime);
	 	
	 	    	}else if(type.equals("goodId")) {
	 	    		int goodId = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllByGoodId(index, limit, goodId,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("goodName")) {
	 	    		outStorage =outStorageRepository.findAllByGoodName(index, limit, key,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("supplierId")) {
	 	    		int supplierId = Integer.parseInt(key);
	 	    		outStorage =outStorageRepository.findAllByCustomerId(index, limit, supplierId,WarehouseId,beganTime,endTime);
	 		    }else if(type.equals("supplierName")) {
	 		    	outStorage =outStorageRepository.findAllByCustomerName(index, limit, key,WarehouseId,beganTime,endTime);
	 		    }else {
	 		    	outStorage =outStorageRepository.findAllAndWarehouseId(index, limit,WarehouseId,beganTime,endTime);
	 	    	}
	 	    	    int count=outStorageRepository.count();
	 	    	    //OutStorageVo inStorageVo=new OutStorageVo(0,"",count,outStorage );
	 	    	    Result result = ResultUtil.success();
			    	result =  ResultUtil.success(count, outStorage);
	 		        return result;
	 			}   
	 
	   @MyLog(value = "删除出库记录")
		  @PostMapping("/delAll")
	      @ResponseBody  //传string类型的id集合用逗号分隔开的
		  public Map<String,Object> deleteById(String isStr){
	    	  Map map=new HashMap();
	    	  //不为空不为null
	    	  System.out.println("传过来要删除出库记录的id"+isStr);
	    	  if (isStr!=null &&!isStr.equals("")) {
	    		  String[] ids = isStr.split(",");
	    		  for (String id : ids) {
	    			 //前端最前个逗号前是空的
	    			  if(id!=null &&!id.equals("")){
	    				  outStorageRepository.deleteById(id);
	    			  }
	    		  }map.put("msg", "success");
	    	  }else {map.put("msg", "error");}
	    	  
		         return map;
		       
		    }
	 
	 
}
