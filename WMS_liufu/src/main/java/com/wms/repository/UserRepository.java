package com.wms.repository;

import java.util.List;

import com.wms.entity.User;

public interface UserRepository {
    public User login(String username,String password);
    public String GetPassword(String username);
    public String GetRole(String username);
    public User findByName(String username);
    public void save(User user);
    
    
    
    public int count();
    public User findById(long id);
    public void update(User user);
    public void deleteById(long id);
    
    
    
    public List<User> findAll(int page,int limit);
	public List<User> findByNamePage(int index, int limit, String key);
	public List<User> findByIdPage(int index, int limit, int id);
	
	public void updatePassword(User user);
	public String findSalt(long id);
	
	
   
   
   
}
