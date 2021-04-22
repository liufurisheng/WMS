package com.wms.entity;

import java.util.List;

import lombok.Data;

@Data
public class Result<T> {

	

	

	//  error_code 状态值：0 极为成功，其他数值代表失败
    private int code;
    
   // error_msg 错误信息，若status为0时，为success
    private String msg;
    
    private int count;
    
   //  content 返回体报文的出参，使用泛型兼容不同的类型
    private T data;


}
