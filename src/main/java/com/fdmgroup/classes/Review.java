package com.fdmgroup.classes;

public class Review {
	
	private int reviewID;
	
	private int mangaID;
	
	private int postID;
	
	private int rating;
	
	private String reviewContent;
	
	public Review(){
		
	}
	
	public Review(int reviewID, int mangaID, int postID, int rating, String reviewContent){
		this.reviewID = reviewID;
		this.mangaID = mangaID;
		this.postID = postID;
		this.rating = rating;
		this.reviewContent = reviewContent;		
	}

	public int getReviewID() {
		return reviewID;
	}

	public void setReviewID(int reviewID) {
		this.reviewID = reviewID;
	}
	
	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public String getReviewContent() {
		return reviewContent;
	}

	public void setReviewContent(String reviewContent) {
		this.reviewContent = reviewContent;
	}

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public int getPostID() {
		return postID;
	}

	public void setPostID(int postID) {
		this.postID = postID;
	}
}
