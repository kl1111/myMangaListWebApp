package com.fdmgroup.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.fdmgroup.classes.User;
import com.fdmgroup.models.interfaces.Users;
import org.apache.log4j.Logger;

public class UsersDAO implements Users {

	private Connection connection = null;
	static Logger log = Logger.getLogger(UsersDAO.class);
	
	public UsersDAO(Connection connection) {
		this.connection = connection;
	}
	
	public void addUsers(User user) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL ADD_USER(?,?,?,?,?)}");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to add user, error with SQL");
		}
	}

	public User findByEmail(String email) {
		User user = new User();

		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL FIND_USER_BY_EMAIL(?,?,?,?,?,?,?)}");
			
			cstmt.setString(1, email);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			
			cstmt.executeQuery();
				
			int userID = cstmt.getInt(2);
			String usernames = cstmt.getString(3);
			String password = cstmt.getString(4);
			String firstName = cstmt.getString(5);
			String lastName = cstmt.getString(6);
			String emails = cstmt.getString(7);
				
			user = new User(userID, firstName, lastName, emails, password, usernames);		
			
			if (user.getUsername() != null) {
				return user;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find user by email, error with SQL");
		} 
		return null;
	}
	
	public User findByUsername(String username) {
		User user = new User();

		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL FIND_USER_BY_USERNAME(?,?,?,?,?,?,?)}");
			
			cstmt.setString(1, username);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			
			cstmt.executeQuery();
				
			int userID = cstmt.getInt(2);
			String usernames = cstmt.getString(3);
			String password = cstmt.getString(4);
			String firstName = cstmt.getString(5);
			String lastName = cstmt.getString(6);
			String email = cstmt.getString(7);
				
				user = new User(userID, firstName, lastName, email, password, usernames);
			
			if (user.getUsername() != null) {
				return user;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find user by username, error with SQL");
		}
		return null;
	}
	
	
	public void removeUser(String username) {
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL DELETE_USER_BY_USERNAME(?)}");
			cstmt.setString(1, username);
			cstmt.execute();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to delete user, error with SQL");
		} 
	}

	public void updateUser(User user) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL UPDATE_USERS(?,?,?,?,?)}");

			stmt.setString(1, user.getUsername());
			stmt.setString(2, user.getPassword());
			stmt.setString(3, user.getFirstName());
			stmt.setString(4, user.getLastName());
			stmt.setString(5, user.getEmail());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to update user details, error with SQL");
		} 
	}

	public List<User> listUsers() {
		List<User> userList = new ArrayList<User>();
		try {
			Statement statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT USER_ID, USERNAME, PASSWORD, FIRST_NAME, LAST_NAME, EMAIL FROM USERS");

			while (resultSet.next()) {
				User user = new User();
				int userID = resultSet.getInt("USER_ID");
				String usernames = resultSet.getString("USERNAME");
				String password = resultSet.getString("PASSWORD");
				String firstName = resultSet.getString("FIRST_NAME");
				String lastName = resultSet.getString("LAST_NAME");
				String email = resultSet.getString("EMAIL");
				
				user = new User(userID, firstName, lastName, email, password, usernames);
				userList.add(user);
			}
			return userList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to list all users, error with SQL");
		}
		return null;
	}
}
