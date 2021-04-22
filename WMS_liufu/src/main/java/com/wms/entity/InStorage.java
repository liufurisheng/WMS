package com.wms.entity;



import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wms.utils.ExcelColumn;

import lombok.Data;
//货物入库记录
@Data
public class InStorage {
	
	@ExcelColumn(value = "ID", col = 1)
	private Integer id;
	
	@ExcelColumn(value = "供应商编号", col = 1)
	private Integer supplierId;
	
	@ExcelColumn(value = "供应商名称", col = 1)
	private String supplierName;
	 
	@ExcelColumn(value = "货物编号", col = 1)
	private Integer goodId;
	
	@ExcelColumn(value = "货物名称", col = 1)
	private String goodName;
	
	@ExcelColumn(value = "仓库编号", col = 1)
	private Integer warehouseId;
	 
	@ExcelColumn(value = "数量", col = 1)
	private long number;
	
	@ExcelColumn(value = "操作时间", col = 1)
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    
	private Date time;
	//操作者
	@ExcelColumn(value = "执行人", col = 1)
	private String person;
}
