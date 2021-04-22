package com.wms.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wms.utils.ExcelColumn;

import lombok.Data;

@Data
public class OutStorage implements Serializable {
	
	@ExcelColumn(value = "出库ID", col = 1)
	 private Integer id;
	 
	 @ExcelColumn(value = "顾客ID", col = 1)
	 private Integer customerId;
	 
	 @ExcelColumn(value = "顾客", col = 1)
	 private String customerName;
	 
	 @ExcelColumn(value = "货物ID", col = 1)
	 private Integer goodId;
	 
	 @ExcelColumn(value = "货物", col = 1)
	 private String goodName;
	 
	 @ExcelColumn(value = "仓库ID", col = 1)
	 private Integer warehouseId;
	 
	 @ExcelColumn(value = "货物数量", col = 1)
	 private long number;
	 
	 @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    
	 @ExcelColumn(value = "订单处理时间", col = 1)
	 private Date time;
	 
	 @ExcelColumn(value = "操作者", col = 1)
	 private String person;// 经手人
}
