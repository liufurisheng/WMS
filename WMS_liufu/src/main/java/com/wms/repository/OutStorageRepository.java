package com.wms.repository;

import java.util.Date;
import java.util.List;

import com.wms.entity.OutStorage;

public interface OutStorageRepository {
   void insert(OutStorage outStorage);
   void update(OutStorage outStorage);
  
     List<OutStorage> findAllById(int index, int limit, int id, int warehouseId, Date beganTime, Date endTime);
    int count();
    List<OutStorage> findAllByGoodId(int index, int limit, int goodId, int warehouseId, Date beganTime, Date endTime);
    List<OutStorage> findAllByGoodName(int index, int limit, String key, int warehouseId, Date beganTime, Date endTime);
    List<OutStorage> findAllByCustomerId(int index, int limit, int supplierId, int warehouseId, Date beganTime,
		Date endTime);
    List<OutStorage> findAllByCustomerName(int index, int limit, String key, int warehouseId, Date beganTime, Date endTime);
    List<OutStorage> findAllAndWarehouseId(int index, int limit, int warehouseId, Date beganTime, Date endTime);
	void deleteById(String id);

}
