package com.fdmgroup.classes;

import java.util.Date;

public class Forum {

	private int forumID;

	private int userID;
	
	private Date startDate;
	
	private String forumName;
	
	public Forum(){
		
	}
	
	public Forum(int forumID, int userID, Date startDate, String forumName){
		this.forumID = forumID;
		this.userID = userID;
		this.startDate = startDate;
		this.forumName = forumName;
	}

	public int getForumID() {
		return forumID;
	}

	public void setForumID(int forumID) {
		this.forumID = forumID;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}	
}
