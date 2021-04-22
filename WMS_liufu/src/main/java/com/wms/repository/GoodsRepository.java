package com.wms.repository;

import java.util.List;

import com.wms.entity.Customer;
import com.wms.entity.Goods;

public interface GoodsRepository {
	public List<Goods> findByName(int index, int limit, String key);
	public List<Goods> findById(int index, int limit, int id);
	public Goods find_ById(long id);
	public List<Goods> findAll(int index,int limit);
    public int count();
    void update(Goods goods);
    void deleteById(Integer id);
    void insert(Goods goods);
	
    public Goods findBy_Name(String goodName);
	public List<Goods> find_All();
	
	
}
