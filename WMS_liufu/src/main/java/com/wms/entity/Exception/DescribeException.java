package com.wms.entity.Exception;

public class DescribeException extends RuntimeException{
	private Integer code;
	//继承exception，加入错误状态值
	 public DescribeException(ExceptionEnum exceptionEnum) {
		 super(exceptionEnum.getMsg());
		 this.code = exceptionEnum.getCode();
	 }
	 //自定义错误信息
	 public DescribeException(String message, Integer code) {
		 super(message);
		 this.code = code;
	 }
	 //
	 public Integer getCode() {
		 return code;
	 }
	 public void setCode(Integer code) {
		 this.code = code;
	 }

}
