package com.ctet.services;

import java.util.List;

import com.ctet.data.User;

public interface UserService {

//	public void addUser(User user);
//
//	public void updateUser(User user);
//
	public List<User> findByCompany(String companyId);
	
	public User findByPrimaryKey(String email);

//
	public User authenticate(String user, String password, String company);

	public void saveOrUpdate(User user);

	public User findByPrimaryKey(String email, String companyId);

//	new 
//	public void saveOrUpdateUser(User user);

//	

}
