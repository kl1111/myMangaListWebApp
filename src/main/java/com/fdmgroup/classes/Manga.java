package com.fdmgroup.classes;

import java.util.Comparator;
import java.util.Date;

public class Manga implements Comparable<Manga> {

	private int mangaID;

	private String title;

	private String author;

	private String genre;

	private String summary;

	private Date pubDate;
	
	private String url;

	public Manga() {
	}

	public Manga(int mangaID, String title, String author, String genre, String summary, Date pubDate, String url) {
		this.mangaID = mangaID;
		this.title = title;
		this.author = author;
		this.genre = genre;
		this.summary = summary;
		this.pubDate = pubDate;
		this.url = url;
	}

	public int getMangaID() {
		return mangaID;
	}

	public void setMangaID(int mangaID) {
		this.mangaID = mangaID;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public Date getPubDate() {
		return pubDate;
	}

	public void setPubDate(Date pubDate) {
		this.pubDate = pubDate;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int compareTo(Manga o) {
		return Comparators.id.compare(this, o);
	}

	public static class Comparators {

		public static Comparator<Manga> title = new Comparator<Manga>() {
			public int compare(Manga o1, Manga o2) {
				return o1.getTitle().compareTo(o2.getTitle());
			}
		};

		public static Comparator<Manga> author = new Comparator<Manga>() {
			public int compare(Manga o1, Manga o2) {
				return o1.getAuthor().toLowerCase().compareTo(o2.getAuthor().toLowerCase());
			}
		};

		public static Comparator<Manga> genre = new Comparator<Manga>() {
			public int compare(Manga o1, Manga o2) {
				return o1.getGenre().compareTo(o2.getGenre());
			}
		};

		public static Comparator<Manga> date = new Comparator<Manga>() {
			public int compare(Manga o1, Manga o2) {
				return o1.getPubDate().compareTo(o2.getPubDate());
			}
		};
		public static Comparator<Manga> id = new Comparator<Manga>() {
			public int compare(Manga o1, Manga o2) {
				return o1.getMangaID() - o2.getMangaID();
			}
		};
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
