package com.wms.entity.Exception;

public enum ExceptionEnum {
	
	UNKNOW_ERROR(-1,"未知错误,请检查你的操作信息或重新登录：可能是登录信息过期。还是异常，请查看一下所操作的信息类似：1.管理员的删除是否绑定了仓库2.货物的删除是否仓库中还有库存"),
	USER_NOT_FIND(-101,"操作失败");

	private Integer code;
	private String msg;
	ExceptionEnum(Integer code, String msg) {
	            this.code = code;
		        this.msg = msg;
		     }
	public Integer getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}

}
