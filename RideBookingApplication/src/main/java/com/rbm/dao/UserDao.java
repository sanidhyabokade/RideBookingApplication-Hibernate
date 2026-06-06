package com.rbm.dao;

import com.rbm.entity.User;

public interface UserDao {
	
	void saveUser(User user);
	
	User findById(int userId);
	
	User findByEmail(String email);
	
	User login(String email, String password);
	
	void updateUser(User user);
	
	void deleteUser(int userId);
}
