package com.fdmgroup.classes;

import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.UsersDAO;

public class UserController {
	
	public int checkUserDetails(String username, String password){
		UsersDAO usersDao = new UsersDAO(Connections.getConnection());
		User user = usersDao.findByUsername(username);
		Connections.CloseConnection();
		if(user != null) {
			if(user.getPassword().equals(password)) {
				// login successful
				return 0;
			} else {
				// incorrect password
				return 1;
			}
		} else {
			// user does not exist
			return 2;
		}
	}
	
	
}
