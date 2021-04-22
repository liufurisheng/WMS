package com.wms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wms.entity.Result;
import com.wms.entity.User;
import com.wms.entity.Exception.ExceptionEnum;
import com.wms.utils.ResultUtil;
//测试代码，不用理会
@RestController
@RequestMapping("/result")
public class ResultController {
	@Autowired
	private ExceptionHandle exceptionHandle;

	
	     /* 返回体测试
	      * @param name
	     * @param pwd
	      * @return
	     */
	@RequestMapping(value = "/getResult",method = RequestMethod.POST)
	public Result getResult(@RequestParam("username") String username, @RequestParam("password") String password){
		Result result = ResultUtil.success();
		try {
			if (username.equals("管理员")){
				 result =  ResultUtil.success(10, new User());
			}else if (username.equals("用户")){
				result =  ResultUtil.error(ExceptionEnum.USER_NOT_FIND);
			}else{
				int i = 1/0;
			}
		}catch (Exception e){
			result =  exceptionHandle.exceptionGet(e);
		}
		return result;
	}
}

