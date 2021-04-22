package com.wms.repository;

import java.util.Date;
import java.util.List;

import com.wms.entity.SysLog;

public interface SysLogRepository {

	void save(SysLog sysLog);

	int count();

	
   
   
    List<SysLog> findById(int id);
    List<SysLog> findByName(String key);
    List<SysLog> findAll();

	List<SysLog> findAllPage(int index, int limit, Date beganTime, Date endTime);

	List<SysLog> findByNamePage(int index, int limit, String key, Date beganTime, Date endTime);

	List<SysLog> findByOperationPage(int index, int limit, String key, Date beganTime, Date endTime);

	void deleteById(String id);

}
