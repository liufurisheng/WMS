package com.wms.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wms.utils.ExcelColumn;

import lombok.Data;


@Data
public class SysLog implements Serializable {
	
	@ExcelColumn(value = "ID", col = 1)
	private Long id;
	
	@ExcelColumn(value = "用户名", col = 2)
    private String username; //用户名

	@ExcelColumn(value = "操作", col = 3)
    private String operation; //

	@ExcelColumn(value = "方法名", col = 4)
    private String method; //方法名

	@ExcelColumn(value = "参数", col = 5)
    private String params; //参数

	@ExcelColumn(value = "ip地址", col = 6)
    private String ip; //ip地址
   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8") 
    @ExcelColumn(value = "操作时间", col = 7)
    private Date createDate; //操作时间
    //创建getter和setter方法
    @ExcelColumn(value = "操作结果", col = 8)
    private String resultType;
}