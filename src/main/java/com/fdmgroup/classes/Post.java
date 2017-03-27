package com.fdmgroup.classes;

import java.util.Date;

public class Post {

	private int postID;
	
	private int userID;
	
	private Date postDate;
	
	public Post(){
		
	}
	
	public Post(int postID, int userID, Date postDate){
		this.postID = postID;
		this.userID = userID;
		this.postDate = postDate;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public Date getPostDate() {
		return postDate;
	}

	public void setPostDate(Date postDate) {
		this.postDate = postDate;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
