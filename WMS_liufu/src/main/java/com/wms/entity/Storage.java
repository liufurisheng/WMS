package com.wms.entity;

import java.io.Serializable;
import java.util.Date;

import com.wms.utils.ExcelColumn;

import lombok.Data;

/**
 * 仓库库存
 * 
 * @author Ken
 *
 */
@Data
public class Storage implements Serializable {
	
	@ExcelColumn(value = "货物ID", col = 1)
	private Integer goodsId;// 货物ID
	
	
	@ExcelColumn(value = "货物名称", col = 2)
	private String goodsName;// 货物名称
	
	
	@ExcelColumn(value = "货物规格", col = 3)
	private String goodsSize;// 货物规格
	
	
	@ExcelColumn(value = "货物类型", col = 4)
	private String goodsType;// 货物类型
	
	
	@ExcelColumn(value = "货物价值", col = 5)
	private Double goodsValue;// 货物价值
	
	
	@ExcelColumn(value = "仓库ID", col = 6)
	private Integer warehouseId;// 仓库ID
	
	
	@ExcelColumn(value = "库存数量", col = 7)
	private Long number;// 库存数量


}
