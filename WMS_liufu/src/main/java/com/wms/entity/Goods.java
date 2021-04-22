package com.wms.entity;

import java.io.Serializable;

import com.wms.utils.ExcelColumn;

import lombok.Data;

/**
 * 货物信息
 * @author Ken
 *
 */
@Data
public class Goods implements Serializable {
	
	@ExcelColumn(value = "货物ID", col = 1)
	private Integer id;// 货物ID
	
	@ExcelColumn(value = "货物名", col = 2)
	private String name;// 货物名
	
	@ExcelColumn(value = "货物类型", col = 3)
	private String type;// 货物类型
	
	@ExcelColumn(value = "货物规格", col = 4)
	private String size;// 货物规格
	
	@ExcelColumn(value = "货物价值", col = 5)
	private Double value;// 货物价值

	

}
