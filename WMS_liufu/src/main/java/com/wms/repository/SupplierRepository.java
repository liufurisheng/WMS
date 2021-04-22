package com.wms.repository;



import java.io.File;
import java.util.List;


import com.wms.entity.Wms_supplier;

/**
 * Supplier 映射器
 * @author Ken
 *
 */
public interface SupplierRepository {
	public List<Wms_supplier> findByName(int index, int limit, String key);
	public List<Wms_supplier> findById(int index, int limit, int id);
	public Wms_supplier find_ById(long id);
	public List<Wms_supplier> findAll(int index,int limit);
    public int count();
    void update(Wms_supplier supplier);
    void deleteById(Integer id);
	
	/**
	 * 选择全部的 Supplier
	 * @return 返回所有的供应商
	 */
	List<Wms_supplier> selectAll();
	
	
	/**
	 * 选择指定 id 的 Supplier
	 * @param id 供应商ID
	 * @return 返回指定ID对应的供应商
	 */
	Wms_supplier selectById(Integer id);
	
	/**
	 * 选择指定 supplier name 的 Supplier
	 * @param supplierName 供应商名称
	 * @return 返回supplierName对应的供应商
	 */
	Wms_supplier selectBuName(String supplierName);
	
	/**
	 * 选择指定 supplier name 的 Supplier
	 * 与 selectBuName 方法的区别在于本方法将返回相似匹配的结果
	 * @param supplierName 供应商名
	 * @return 返回所有模糊匹配指定supplierName的供应商
	 */
	List<Wms_supplier> selectApproximateByName(String supplierName);
	
	/**
	 * 插入 Supplier 到数据库中
	 * 不需要指定 Supplier 的主键，采用的数据库 AI 方式
	 * @param supplier Supplier 实例
	 */
	void insert(Wms_supplier supplier);
	
	/**
	 * 批量插入 Supplier 到数据库中
	 * @param suppliers 存放 Supplier 实例的 Lists
	 */
	void insertBatch(List<Wms_supplier> suppliers);
	
	/**
	 * 更新 Supplier 到数据库
	 * 该 Supplier 必须已经存在于数据库中，即已经分配主键，否则将更新失败
	 * @param supplier Supplier 实例
	 */
	
	/**
	 * 删除指定 id 的Supplier
	 * @param id 供应商ID
	 */
	
	
	/**
	 * 删除指定 supplierName 的 Supplier
	 * @param supplierName 供应商名称
	 */
	void deleteByName(String supplierName);
	public List<Wms_supplier> find_All();
	
	
}
