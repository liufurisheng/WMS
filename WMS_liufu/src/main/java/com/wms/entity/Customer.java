package com.wms.entity;

import java.io.Serializable;

import com.wms.utils.ExcelColumn;

import lombok.Data;

/**
 * 客户信息
 * @author Ken
 *
 */

@Data
public class Customer implements Serializable {
	
	@ExcelColumn(value = "客户ID", col = 1)
	private Integer id;// 客户ID
	
	@ExcelColumn(value = "客户名", col = 2)
	private String name;// 客户名
	
	@ExcelColumn(value = "负责人", col = 3)
	private String person;// 负责人
	
	@ExcelColumn(value = "联系电话", col = 4)
	private String tel;// 联系电话
	
	@ExcelColumn(value = "电子邮箱", col = 5)
	private String email;// 电子邮件
	
	@ExcelColumn(value = "地址", col = 6)
	private String address;// 地址

	

}
