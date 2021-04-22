package com.wms.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.wms.entity.Wms_supplier;
import com.wms.repository.SupplierRepository;
import com.wms.utils.ExcelUtils;

/**
	 * @author Abbot
	 * @description
	 * @date 2018/9/17 11:24
	 **/
   //swagger的@Api(value = "excel导入导出", tags = "excel导入导出", description = "excel导入导出")
	@RestController
	@RequestMapping("/excel")
	public class ExcelController {
		 @Autowired
		  private SupplierRepository supplierRepository;
		
		
	   // @Autowired
	 //   ExcelService excelService;

	    @RequestMapping(value = "/exportExcel", method = RequestMethod.GET)
	    public void exportExcel(HttpServletResponse response)  throws IOException {
	      
	    	List<Wms_supplier>  wms_suppliers = supplierRepository.findAll(0, 5);
           long t1 = System.currentTimeMillis();
	        ExcelUtils.writeExcel(response, wms_suppliers, Wms_supplier.class);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("write over! cost:%sms", (t2 - t1)));
	    }

	 //postman携带file文件传参可以进，然后进去类封装
	    @RequestMapping(value = "/readExcel", method = RequestMethod.POST)
	    public Map<String,Object> readExcel(MultipartFile file){

	        long t1 = System.currentTimeMillis();
	        List<Wms_supplier> list = ExcelUtils.readExcel("", Wms_supplier.class, file);
	        long t2 = System.currentTimeMillis();
	        System.out.println(String.format("read over! cost:%sms", (t2 - t1)));
	        Wms_supplier s1=list.get(0);
	        System.out.println("list[0]的名字"+s1.getName());
	        list.forEach(
	        		//模拟封装数据
	        		b -> System.out.println("模拟封装数据"+JSON.toJSONString(b))
	        );
	        Map map =new HashMap();
	        map.put("msg", "success");
			return map;
	    }
	}


	


