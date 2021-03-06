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

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
import com.wms.entity.Goods;
import com.wms.entity.InStorage;

import com.wms.entity.Result;
import com.wms.entity.Storage;

import com.wms.entity.User;
import com.wms.entity.Warehouse;
import com.wms.entity.Wms_supplier;
import com.wms.repository.CustomerRepository;
import com.wms.repository.GoodsRepository;
import com.wms.repository.InstorageRepository;
import com.wms.repository.StorageRepository;
import com.wms.repository.SupplierRepository;
import com.wms.repository.WarehouseRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/InStorageManage")
@Controller
public class InStorageController {
	
	Map<String,  Object> map=new HashMap<String, Object>();
	
	@Autowired
	private InstorageRepository instorageRepository;
	
	@Autowired
	private StorageRepository storageRepository;
	
	@Autowired
	private WarehouseRepository warehouseRepository;
	
	@Autowired
	private GoodsRepository goodsRepository;
	
	@Autowired
	private SupplierRepository supplierRepository;
	
	//??????
	 @MyLog(value = "????????????")  //???????????????AOP??????????????????
     @PostMapping("/save")
	 @ResponseBody
	 public Map<String,Object> save( InStorage inStorage ,HttpSession session) {
		 Map<String,Object> map=new HashMap<String, Object>();
		//?????????????????????????????????????????????????????????
	   synchronized(this){
         int addNumber =(int) inStorage.getNumber();
		 int goodId=inStorage.getGoodId();
		 int warehouseId=inStorage.getWarehouseId();
		//?????????????????????
		 Storage haveOrNo=storageRepository.findNumberBoolean(goodId, warehouseId);
		 
		 if(haveOrNo !=null) {
		   Long OriginalNumber=haveOrNo.getNumber();
		   int FinalNumber= (int) (OriginalNumber+addNumber);
		   Storage storage = new Storage();
		   storage.setGoodsId(goodId);
		   storage.setWarehouseId(warehouseId);
		   storage.setNumber((long) FinalNumber);
		   storageRepository.update(storage);
		   
		   System.out.println("???????????????"+OriginalNumber+"????????????"+addNumber+"??????????????????"+FinalNumber);
		   
		   String Info=("????????????:"+"????????????+("+addNumber+")");
		   map.put("msg", Info);
		}else {
			   Storage storage = new Storage();
			   storage.setGoodsId(goodId);
			   storage.setWarehouseId(warehouseId);
			   storage.setNumber((long) addNumber);
			   storageRepository.insert(storage);
			   System.out.println("???????????????");
			   
			   String Info="????????????";
			   map.put("msg", Info);
		}
		 //??????????????????????????????
		 User user = (User) SecurityUtils.getSubject().getPrincipal();
		 String username=null;
		 if(user==null) {
			 username="??????,????????????session????????????????????????";
		 }else {
			 username=user.getUsername();
		 }
		 
		 inStorage.setPerson(username);
		  Date time =new Date();
		 inStorage.setTime(time);
		 instorageRepository.insert(inStorage);
		
	 }return map;
	}
	
	   @GetMapping("/findAllInfo")
	   @ResponseBody
	    public Map<String, Object>findAllWarehouseId(){
		   
		   List<Wms_supplier> suppliers= supplierRepository.find_All();
			List<Goods> goods = goodsRepository.find_All();
		    List<Warehouse> allWarehouses=warehouseRepository.findAll_Id();
			map.put("allWarehouses", allWarehouses);
			map.put("goods", goods);
			map.put("suppliers",suppliers);
	        return map;
	    }
	  
	   
	   /*?????????????????????
	      */
		   @SuppressWarnings("unchecked")
		    @GetMapping("/exportExcel")
		    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
	         
		        // ??????????????????????????????
		        List<InStorage>  inStorage = null;
		        
		        String type=(String)session.getAttribute("type");
		       
		        String key=(String)session.getAttribute("key");
		       
		        int WarehouseId=(int)session.getAttribute("warehouseId");
		       
		        Date beganTime=(Date) session.getAttribute("beganTime");
	 	    	Date endTime=(Date) session.getAttribute("endTime");
		        
		        if(type.equals("id")) {
	 	    		 int id = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllById(0, 1000, id,WarehouseId,beganTime,endTime);
	 	
	 	    	}else if(type.equals("goodId")) {
	 	    		int goodId = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllByGoodId(0, 1000, goodId,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("goodName")) {
	 	    		inStorage=instorageRepository.findAllByGoodName(0, 1000, key,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("supplierId")) {
	 	    		int supplierId = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllBySupplierId(0, 1000, supplierId,WarehouseId,beganTime,endTime);
	 		    }else if(type.equals("supplierName")) {
	 		    	inStorage=instorageRepository.findAllBySupplierName(0, 1000, key,WarehouseId,beganTime,endTime);
	 		    }else {
	 		    	inStorage=instorageRepository.findAllAndWarehouseId(0, 1000,WarehouseId,beganTime,endTime);
	 	    	}
		        long t1 = System.currentTimeMillis();
		        ExcelUtils.writeExcel(response, inStorage, InStorage.class);
		        long t2 = System.currentTimeMillis();
		        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
		        
		    }
	   
	   
	   
	   //??????????????????//????????????????????????????????????,???????????????string??????????????????
	   @GetMapping("/findAll")
	 		@ResponseBody
	 		public  Result findAll(@RequestParam("page") int page, @RequestParam("limit") int limit
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
	 	    	List<InStorage> inStorage = null ;
	          
	 	    	if(type.equals("id")) {
	 	    		 int id = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllById(index, limit, id,WarehouseId,beganTime,endTime);
	 	
	 	    	}else if(type.equals("goodId")) {
	 	    		int goodId = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllByGoodId(index, limit, goodId,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("goodName")) {
	 	    		inStorage=instorageRepository.findAllByGoodName(index, limit, key,WarehouseId,beganTime,endTime);
	 	    	}else if(type.equals("supplierId")) {
	 	    		int supplierId = Integer.parseInt(key);
	 	    		inStorage=instorageRepository.findAllBySupplierId(index, limit, supplierId,WarehouseId,beganTime,endTime);
	 		    }else if(type.equals("supplierName")) {
	 		    	inStorage=instorageRepository.findAllBySupplierName(index, limit, key,WarehouseId,beganTime,endTime);
	 		    }else {
	 		    	inStorage=instorageRepository.findAllAndWarehouseId(index, limit,WarehouseId,beganTime,endTime);
	 	    	}
	 	    	    int count=instorageRepository.count();
	 	    	   // InStorageVo inStorageVo=new InStorageVo(0,"",count,inStorage);
	 	    	    Result result = ResultUtil.success();
			    	result =  ResultUtil.success(count, inStorage);
	 		        return result;
	 			}
	   
	   
	   @MyLog(value = "??????????????????")
		  @PostMapping("/delAll")
	      @ResponseBody  //???string?????????id???????????????????????????
		  public Map<String,Object> deleteById(String isStr){
	    	  Map map=new HashMap();
	    	  //???????????????null
	    	  System.out.println("?????????????????????id"+isStr);
	    	  if (isStr!=null &&!isStr.equals("")) {
	    		  String[] ids = isStr.split(",");
	    		  for (String id : ids) {
	    			 //?????????????????????????????????
	    			  if(id!=null &&!id.equals("")){
	    				  instorageRepository.deleteById(id);
	    			  }
	    		  }map.put("msg", "success");
	    	  }else {map.put("msg", "error");}
	    	  
		         return map;
		       
		    }
	   
	   
	   
	   
	   @GetMapping("/redirect/{location}")
		public String redirect(@PathVariable("location")String location) {
			return location;
		}
	  
	   @InitBinder//????????????????????????,???????????????string??????????????????
	    public void initBinder(WebDataBinder binder, WebRequest request) {
	        //???????????? ??????????????????????????????????????????????????????????????? ???2015-9-9 ????????????yyyy-MM-dd
	        DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));// CustomDateEditor???????????????????????????
	    }
}
