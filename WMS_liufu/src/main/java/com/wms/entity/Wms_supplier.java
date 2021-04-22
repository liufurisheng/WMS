package com.wms.entity;

import java.io.Serializable;

import com.wms.utils.ExcelColumn;

import lombok.Data;

@Data
public class Wms_supplier implements Serializable{
	
	@ExcelColumn(value = "供应商编号", col = 1)
    private Integer id;// 供应商ID
	
	@ExcelColumn(value = "供应商名称", col = 2)
	private String name;// 供应商名
	
	@ExcelColumn(value = "负责人", col = 3)
	private String person;// 负责人
	
	@ExcelColumn(value = "联系电话", col = 4)
	private String tel;// 联系电话
	
	@ExcelColumn(value = "电子邮件", col = 5)
	private String email;// 电子邮件
	
	@ExcelColumn(value = "供应商地址", col = 6)
	private String address;// 供应商地址
}
