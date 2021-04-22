package com.wms.repository;

import java.util.List;

import com.wms.entity.Storage;



public interface StorageRepository {
	public List<Storage> selectAllAndWarehouseId(int index, int limit, int warehouseId);
	public List<Storage> findBygoodsId(int index, int limit, int goodsId, int warehouseId);
	public List<Storage> findBygoodsName(int index, int limit, String goodsName, int warehouseId);
	public List<Storage> findBygoodsType(int index, int limit, String goodsType, int warehouseId);
	public int count();
	
	//public void insert(Storage storage);
	public Storage find_ById(long id, long warehouseId);
	public void update(Storage storage);
	
	
	public void deleteByWarehouseIdAndGoodsId(int warehouseId,int goodsId);
	public void deleteByWarehouseId(int warehouseId);
	public void deleteByGoodsId(int goodsId);
	
	//入仓时查找是否有存货,数量多少
	public int findNumber(int goodId, int warehouseId);
	public Storage findNumberBoolean(int goodId, int warehouseId);
	public void insert(Storage storage);

}
	
