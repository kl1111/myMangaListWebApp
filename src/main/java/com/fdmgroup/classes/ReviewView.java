package com.fdmgroup.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ReviewView {

	private Review review;
	private String mangaTitle;
	private String datePosted;
	private String username;
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
	

	public ReviewView(Review review, String mangaTitle, Date datePosted, String username) {
		this.review = review;
		this.mangaTitle = mangaTitle;
		this.datePosted = this.dateformat.format(datePosted);
		this.username = username;
		
	}

	public Review getReview() {
		return review;
	}

	public void setReview(Review review) {
		this.review = review;
	}

	public String getMangaTitle() {
		return mangaTitle;
	}

	public void setMangaTitle(String mangaTitle) {
		this.mangaTitle = mangaTitle;
	}

	public String getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(Date datePosted) {
		this.datePosted = this.dateformat.format(datePosted);
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}
