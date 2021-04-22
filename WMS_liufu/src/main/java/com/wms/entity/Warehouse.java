package com.wms.entity;

import java.io.Serializable;

import com.wms.utils.ExcelColumn;

import lombok.Data;

/**
 * 仓库信息
 * @author Ken
 *
 */
@Data
public class Warehouse implements Serializable {
	
	@ExcelColumn(value = "仓库ID", col = 1)
	private Integer id;// 仓库ID
	
	@ExcelColumn(value = "仓库地址", col = 2)
	private String address;// 仓库地址
	
	@ExcelColumn(value = "仓库状态", col = 3)
	private String status;// 仓库状态
	
	@ExcelColumn(value = "仓库面积", col = 4)
	private String area;// 仓库面积
	
	@ExcelColumn(value = "仓库描述", col = 5)
	private String desc;// 仓库描述
	
	@ExcelColumn(value = "仓库管理员ID", col = 6)
	private Integer adminID;//仓库管理员ID
	
	@ExcelColumn(value = "仓库管理员名字", col = 7)
	private String adminName; //仓库管理员名字

	

}
