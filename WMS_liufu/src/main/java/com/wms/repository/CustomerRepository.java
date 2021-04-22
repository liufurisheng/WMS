package com.wms.repository;

import java.util.List;

import com.wms.entity.Customer;


public interface CustomerRepository {

	public List<Customer> findByName(int index, int limit, String key);
	public List<Customer> findById(int index, int limit, int id);
	public Customer find_ById(long id);
	public List<Customer> findAll(int index,int limit);
    public int count();
    void update(Customer customer);
    void deleteById(Integer id);
    void insert(Customer customer);
	public Customer findBy_Name(String customerName);
	public List<Customer> find_All();
	
	
}
