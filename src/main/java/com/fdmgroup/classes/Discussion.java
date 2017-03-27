package com.fdmgroup.classes;


public class Discussion {

	private int forumPostID;

	private int postID;

	private int forumID;

	private int mangaID;

	private String postContent;
	
	private int likeContent;

	public Discussion() {

	}

	public Discussion(int forumPostID, int postID, int mangaID, int forumID, String postContent, int likeContent) {
		this.forumPostID = forumPostID;
		this.postID = postID;
		this.mangaID=mangaID;
		this.forumID=forumID;
		this.postContent=postContent;
		this.likeContent = likeContent;
	}

	public int getForumPostID() {
		return forumPostID;
	}

	public void setForumPostID(int forumPostID) {
		this.forumPostID = forumPostID;
	}
	
	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}

	public int getForumID() {
		return forumID;
	}

	public void setForumID(int forumID) {
		this.forumID = forumID;
	}

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public String getPostContent() {
		return postContent;
	}

	public void setPostContent(String postContent) {
		this.postContent = postContent;
	}

	public int getLikeContent() {
		return likeContent;
	}

	public void setLikeContent(int likeContent) {
		this.likeContent = likeContent;
	}
	
	

}
