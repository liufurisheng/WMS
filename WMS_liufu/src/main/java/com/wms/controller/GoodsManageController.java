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

import com.wms.entity.Goods;

import com.wms.entity.Result;

import com.wms.repository.GoodsRepository;
import com.wms.utils.ExcelUtils;
import com.wms.utils.ResultUtil;

@RequestMapping(value = "/goodsManage")
@Controller
public class GoodsManageController {
	 @Autowired
	  private GoodsRepository goodsRepository;
	 
	
	  /*导出供应商信息
      */
	   @SuppressWarnings("unchecked")
	    @GetMapping("/exportExcel")
	    public void exportSupplier(HttpServletResponse response,HttpSession session) throws IOException{
         
	       // String fileName = "supplierInfo.xlsx";
	        // 根据查询类型进行查询
	        List<Goods>  goods = null;
	        
	        String type=(String)session.getAttribute("type");
	        String key=(String)session.getAttribute("key");
	        
	        if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 goods=goodsRepository.findById(0, 10000, id);
          }else if(type.equals("name")) {
	    		
        	  goods=goodsRepository.findByName(0, 10000, key);
	    	}else {
	    		goods=goodsRepository.findAll(0, 10000);
	    	} 
	        long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, goods, Goods.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	    }
	   
	 //postman携带file文件传参可以进，然后进去类封装
	    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	    @ResponseBody
	    public Map<String,Object> readExcel(MultipartFile file){

	        long t1 = System.currentTimeMillis();
	        List<Goods> list = ExcelUtils.readExcel("", Goods.class, file);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
	        Goods good=new Goods();
	        for(Goods object:list) {
	        	good.setName(object.getName());
	        	good.setSize(object.getSize());
	        	good.setType(object.getType());
	        	good.setValue(object.getValue());
	        	goodsRepository.insert(good);
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
	    	List<Goods> goods = null ;
           
	    	if(type.equals("id")) {
	    		 int id = Integer.parseInt(key);
	    		 goods=goodsRepository.findById(index, limit, id);

	    		
	    	}else if(type.equals("name")) {
	    		
	    		goods=goodsRepository.findByName(index, limit, key);
	    	}else {
	    		goods=goodsRepository.findAll(index, limit);
	    	}
	    	    int count=goodsRepository.count();
	    	    Result result = ResultUtil.success();
	    	    result =  ResultUtil.success(count, goods);
		    	//GoodsVo goodsVo=new GoodsVo(0,"",count,goods);用封装类来代替这些没用的
		        return result;
			
		    }
	 
	    
	   @MyLog(value = "添加货物信息")
	    @PostMapping("/save")
		 public String save( Goods goods) {
	    	goodsRepository.insert(goods);
			return "redirect:/goodsManage/redirect/wms_goodsManagement";
			 
		 }
	    
	   
	    @GetMapping("/findById/{id}")
	    public String findById(@PathVariable("id") long id,Model model){
	    	Goods goods=goodsRepository.find_ById(id);
	        model.addAttribute("goods",goods);
	        return "wms_update_goods";
	    }
	 
	 @MyLog(value = "修改货物信息")
	 @PostMapping("/update")
	    public String update(Goods goods){
		 goodsRepository.update(goods);
	        return "redirect:/goodsManage/redirect/wms_goodsManagement";
	    }
	 @MyLog(value = "删除货物信息")
	 @GetMapping("/deleteById/{id}")
	  public String deleteById(@PathVariable("id") int id){
	        
		 goodsRepository.deleteById(id);
	        
	        return "redirect:/goodsManage/redirect/wms_goodsManagement";
	       
	    }
	    
	    
	    
		 @GetMapping("/redirect/{location}")
			public String redirect(@PathVariable("location")String location) {
				return location;
			}
}


