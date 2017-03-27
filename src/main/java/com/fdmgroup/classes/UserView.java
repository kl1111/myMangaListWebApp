package com.fdmgroup.classes;

public class UserView {

	
	public static void main(String[] args) {
		UserController userController = new UserController();
		
		String enteredUsername = "Karen";
		String enteredPassword = "chowchow";
		
		int hasPassed = userController.checkUserDetails(enteredUsername, enteredPassword);
		
		if(hasPassed == 0) {
			System.out.println("Do Something");
		} 
		
	}
}
