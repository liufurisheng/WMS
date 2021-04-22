package com.wms.entity;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    private int code;
    private String msg;
    private int count;
    private List<User> data;
}

