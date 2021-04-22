package com.wms.repository;



import java.io.File;
import java.util.List;


import com.wms.entity.Warehouse;

/**
 * Supplier 映射器
 * @author Ken
 *
 */
public interface WarehouseRepository {
	public List<Warehouse> findAll(int index,int limit);
	public List<Warehouse> findByName(int index, int limit, String key);
	public List<Warehouse> findById(int index, int limit, int id);
	public Warehouse find_ById(long id);
	public int count();
    void update(Warehouse warehouse);
    void deleteById(Integer id);
    void insert(Warehouse warehouse);
	
    //找没被使用的仓库编号
    public List<Warehouse> findAllId();
  //找所有仓库编号
    public List<Warehouse> findAll_Id();
    
}