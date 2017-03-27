package com.fdmgroup.models.interfaces;

import java.util.List;

import com.fdmgroup.classes.User;

public interface Users {
	
	void addUsers(User user);
	
	User findByUsername(String username);
	
	User findByEmail(String email);
	
	void removeUser(String username);
	
	void updateUser (User user);
	
	List<User> listUsers();
}
