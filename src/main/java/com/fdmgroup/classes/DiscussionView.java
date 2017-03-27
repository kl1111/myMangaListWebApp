package com.fdmgroup.classes;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscussionView {

	private Discussion discussion;
	private String mangaTitle;
	private String forumName;
	private String datePosted;
	private String username;
	private SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

	public DiscussionView(Discussion discussion, String mangaTitle, String forumName, Date datePosted, String username) {
		this.discussion = discussion;
		this.mangaTitle = mangaTitle;
		this.forumName = forumName;
		this.datePosted = this.dateformat.format(datePosted);
		this.username = username;
	}

	public Discussion getDiscussion() {
		return discussion;
	}

	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
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

	public SimpleDateFormat getDateformat() {
		return dateformat;
	}

	public void setDateformat(SimpleDateFormat dateformat) {
		this.dateformat = dateformat;
	}

	public String getForumName() {
		return forumName;
	}

	public void setForumName(String forumName) {
		this.forumName = forumName;
	}

}
