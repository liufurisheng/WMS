package com.wms.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wms.utils.ExcelColumn;

@Data
public class User {
	
	@ExcelColumn(value = "管理员编号", col = 1)
    private long id;
	@ExcelColumn(value = "管理员姓名", col = 2)
    private String username;
	
    private String password;
	
    private String nickname;
	
    private String gender;
    
    @ExcelColumn(value = "管理员电话", col = 3)
    private String telephone;
    
    @ExcelColumn(value = "注册时间", col = 4)
    private Date registerdate;
   
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")    
    @ExcelColumn(value = "管理员地址", col = 5)
    private String address;
    
    @ExcelColumn(value = "管理员角色", col = 6)
    private String role;
   
    @ExcelColumn(value = "管理员权限", col = 7)
    private String perms;
    
    private String salt;
   
    @ExcelColumn(value = "管理员所属仓库", col = 8)
    private Integer  w_admin_wId;
}
