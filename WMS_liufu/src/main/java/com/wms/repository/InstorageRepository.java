package com.wms.repository;


import java.util.Date;
import java.util.List;

import com.wms.entity.InStorage;

public interface InstorageRepository {
    //入库记录
	void insert(InStorage inStorage);

	void update(InStorage inStorage);

	
	//查询
	List<InStorage> findAllAndWarehouseId(int index, int limit, int warehouseId, Date beganTime, Date endTime);

	int count();

	List<InStorage> findAllById(int index, int limit, int id, int warehouseId, Date beganTime, Date endTime);

	List<InStorage> findAllByGoodId(int index, int limit, int goodId, int warehouseId, Date beganTime, Date endTime);

	List<InStorage> findAllBySupplierId(int index, int limit, int supplierId, int warehouseId, Date beganTime,
			Date endTime);

	List<InStorage> findAllByGoodName(int index, int limit, String key, int warehouseId, Date beganTime, Date endTime);
    List<InStorage> findAllBySupplierName(int index, int limit, String key, int warehouseId, Date beganTime,
			Date endTime);

	void deleteById(String id);
	
}
