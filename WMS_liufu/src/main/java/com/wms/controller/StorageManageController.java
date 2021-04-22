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
import com.wms.entity.Storage;


import com.wms.entity.Warehouse;
import com.wms.repository.StorageRepository;
import com.wms.repository.WarehouseRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/storageManage")
@Controller
public class StorageManageController {
	  @Autowired
	  private StorageRepository storageRepository;
	  
	  @Autowired
	  private WarehouseRepository warehouseRepository;
	  
	  
	  /*导出供应商信息
	      */
		   @SuppressWarnings("unchecked")
		    @GetMapping("/exportExcel")
		    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
	         
		       // String fileName = "supplierInfo.xlsx";
		        // 根据查询类型进行查询
		        List<Storage>  storage = null;
		        
		        String type=(String)session.getAttribute("type");
		        System.out.println("type:"+type);
		        String key=(String)session.getAttribute("key");
		        System.out.println("key:"+key);
		        int WarehouseId=(int)session.getAttribute("warehouseId");
		        System.out.println("WarehouseId:"+WarehouseId);
		        if(type.equals("goodsId")) {
		    		 int id = Integer.parseInt(key);
		    		 storage=storageRepository.findBygoodsId(0, 1000, id,WarehouseId);
		
		    	}else if(type.equals("goodsName")) {
		    		storage=storageRepository.findBygoodsName(0, 1000, key,WarehouseId);
		    	}
		    	 else if(type.equals("goodsType")) {
		    		storage=storageRepository.findBygoodsType(0, 1000, key,WarehouseId);
			    }
		    	else {
		    		storage=storageRepository.selectAllAndWarehouseId(0, 1000,WarehouseId);
		    	}
		        long t1 = System.currentTimeMillis();
		        ExcelUtils.writeExcel(response, storage, Storage.class);
		        long t2 = System.currentTimeMillis();
		        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
		   }
		   
		 //postman携带file文件传参可以进，然后进去类封装
		    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
		    @ResponseBody
		    public Map<String,Object> readExcel(MultipartFile file){

		        long t1 = System.currentTimeMillis();
		        List<Storage> list = ExcelUtils.readExcel("", Storage.class, file);
		        long t2 = System.currentTimeMillis();
		        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
		       
		        Storage storage=new Storage();
		        for (Storage object : list) { 
		        	storage.setGoodsId(object.getGoodsId());
		        	storage.setNumber(object.getNumber());
		        	storage.setWarehouseId(object.getWarehouseId());
		        	storageRepository.insert(storage);
		        }
		        
		        Map map =new HashMap();
		        map.put("msg", "success");
				return map;
		    }
		   
		   
		   
		   
		   
		   
		   
	  
	  //前端ajax找到所有仓库编号放上页面
		   @GetMapping("/findAllWarehouseId")
		   @ResponseBody
		    public Map<String, Object> findById(){
				List<Warehouse> allWarehouses=warehouseRepository.findAll_Id();
				Map<String,  Object> map=new HashMap<String, Object>();
		        map.put("allWarehouses", allWarehouses);
		        return map;
		    }
	  
	  
	  
	  @GetMapping("/findAll")
		@ResponseBody
		public Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
				,@RequestParam("type") String type,@RequestParam("key") String key,
				@RequestParam("warehouseId") int WarehouseId,
				HttpSession session,Model model){
	    	 
		     session.setAttribute("type",type);
	    	 session.setAttribute("key",key);
	         session.setAttribute("warehouseId", WarehouseId);
	    	 
	    	int index =(page-1)*limit;
	    	List<Storage> storage = null ;
         
	    	if(type.equals("goodsId")) {
	    		 int id = Integer.parseInt(key);
	    		 storage=storageRepository.findBygoodsId(index, limit, id,WarehouseId);
	
	    	}else if(type.equals("goodsName")) {
	    		storage=storageRepository.findBygoodsName(index, limit, key,WarehouseId);
	    	}
	    	 else if(type.equals("goodsType")) {
		    	storage=storageRepository.findBygoodsType(index, limit, key,WarehouseId);
		    }
	    	else {
	    		storage=storageRepository.selectAllAndWarehouseId(index, limit,WarehouseId);
	    	}
	    	    int count=storageRepository.count();
	    	    //StorageVo storageVo=new StorageVo(0,"",count,storage);
	    	    Result result = ResultUtil.success();
		    	result =  ResultUtil.success(count, storage);
		        return result;
			}
	  
	    
	    /*@PostMapping("/save")
		 public String save( Storage storage) {
	    	storageRepository.insert(storage);
			return "redirect:/goodsManage/redirect/wms_goodsManagement";
			 
		 }*/
	    
	   
	    @GetMapping("/findById/{goodsId}/{warehouseId}")
	    public String findById(@PathVariable("goodsId") long goodsId,@PathVariable("warehouseId") long warehouseId,Model model){
	    	Storage storage=storageRepository.find_ById(goodsId,warehouseId);
	        model.addAttribute("storage",storage);
	        return "wms_update_storage";
	    }
	 
	 @MyLog(value = "修改库存信息")
	 @PostMapping("/update")
	    public String update(Storage storage){
		 storageRepository.update(storage);
	        return "redirect:/storageManage/redirect/wms_storageManagement";
	    }
	 
	 @MyLog(value = "删除库存信息")
	 @ResponseBody
	 @GetMapping("/deleteById/{goodsId}/{warehouseId}")
	  public Map<String,Object> deleteById(@PathVariable("goodsId") int goodsId,@PathVariable("warehouseId") int warehouseId){
	        Map map =new HashMap();
	        System.out.println(goodsId+"货物编号和仓库编号"+warehouseId);
	       
	        String x="success";
		    storageRepository.deleteByWarehouseIdAndGoodsId( warehouseId, goodsId);
		    map.put("msg", x);
	        
		    return map;
	       
	    }
	    
	    
	    
		 @GetMapping("/redirect/{location}")
			public String redirect(@PathVariable("location")String location) {
				return location;
			}
}
