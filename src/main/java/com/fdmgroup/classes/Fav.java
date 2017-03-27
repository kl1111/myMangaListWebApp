package com.fdmgroup.classes;

public class Fav {

	private int favID;

	private int mangaID;

	private int userID;

	public Fav() { 
		
	}

	public Fav(int favID, int mangaID, int userID) {
		this.favID = favID;
		this.mangaID = mangaID;
		this.userID = userID;
	}

	public int getFavID() {
		return favID;
	}

	public void setFavID(int favID) {
		this.favID = favID;
	}

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public int getUserID() {
		return userID;
	}

	public void setUserID(int userID) {
		this.userID = userID;
	}

}
