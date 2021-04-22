package com.wms.utils;

import com.wms.entity.Result;
import com.wms.entity.Exception.ExceptionEnum;

public class ResultUtil {
	//返回成功，传入返回体具体出參
	public static Result success(int count,Object object){
		 Result result = new Result();
		 result.setCode(0);
		 //result.setMsg("success");
		 result.setMsg("success");
		 result.setCount(count);
		 result.setData(object);
		 return result;
	}
	
	//提供给部分不需要出參的接口
	public static Result success(int count){
		return success(count, null);//success( null)
	}
	
	//提供给部分不需要出參的接口
		public static Result success(){
			return success(0);//success( null)
		}
	
	//自定义错误信息
	public static Result error(Integer code,String msg){
		Result result = new Result();
		result.setCode(code);
		result.setMsg(msg);
		result.setCount(10);
		result.setData(null);
		return result;
	}
	
	//返回异常信息，在已知的范围内
	public static Result error(ExceptionEnum exceptionEnum){
		Result result = new Result();
		result.setCode(exceptionEnum.getCode());
		result.setMsg(exceptionEnum.getMsg());
		result.setCount(10);
		result.setData(null);
		return result;
	}

	

}
