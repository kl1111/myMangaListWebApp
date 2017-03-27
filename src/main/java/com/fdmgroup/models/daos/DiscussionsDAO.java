package com.fdmgroup.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Discussion;
import com.fdmgroup.classes.DiscussionView;
import com.fdmgroup.models.interfaces.Discussions;
import org.apache.log4j.Logger;

public class DiscussionsDAO implements Discussions {

	private Connection connection = null;
	static Logger log = Logger.getLogger(DiscussionsDAO.class);
	
	public DiscussionsDAO(Connection connection){
		this.connection = connection;
	}

	public void addMangaDiscussion(String postContent, int user_id, int manga_id) {
		PostDAO postDao = new PostDAO(Connections.getConnection());
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		Date date = new Date(zonedDateTime.toInstant().toEpochMilli());
		int postId = postDao.addPost(user_id, date);
		if (postId > 0) {
			try {
				CallableStatement stmt = connection.prepareCall("{CALL ADD_DISCUSSIONS(?,?,?,?)}");
				stmt.setInt(1, postId);
				stmt.setNull(2, 1);
				stmt.setInt(3, manga_id);
				stmt.setString(4, postContent);
				stmt.execute();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				postDao.deletePost(postId);
				log.warn("Failed to add manga discussion, error with SQL");
			} 
		}
	}

	public void addForumDiscussion(String postContent, int user_id, int forum_id) {
		PostDAO postDao = new PostDAO(Connections.getConnection());
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		Date date = new Date(zonedDateTime.toInstant().toEpochMilli());
		int postId = postDao.addPost(user_id, date);
		if (postId > 0) {
			try {
				CallableStatement stmt = connection.prepareCall("{CALL ADD_DISCUSSIONS(?,?,?,?)}");
				stmt.setInt(1, postId);
				stmt.setInt(2, forum_id);
				stmt.setNull(3, 1);
				stmt.setString(4, postContent);
				stmt.execute();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				postDao.deletePost(postId);
				log.warn("Failed to add forum discussion, error with SQL");
			} 
		}
	}

	public void deleteDiscussion(int forum_post_id, int post_id) {
		try {
			CallableStatement cstmt = connection.prepareCall("{CALL DELETE_DISCUSSIONS(?)}");
			cstmt.setInt(1, forum_post_id);
			cstmt.executeUpdate();
			PostDAO postDao = new PostDAO(Connections.getConnection());
			postDao.deletePost(post_id);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to delete discussion, error with SQL");
		} 
	}

	public void updateDiscussion(Discussion discussion) {
		try {
			CallableStatement stmt = connection.prepareCall("{CALL UPDATE_DISCUSSIONS(?,?,?,?,?,?)}");
			stmt.setInt(1, discussion.getForumPostID());
			stmt.setInt(2, discussion.getPostID());

			if (discussion.getForumID() != -1) {
				stmt.setInt(3, discussion.getForumID());
			} else {
				stmt.setNull(3, 1);
			}

			if (discussion.getMangaID() != -1) {
				stmt.setInt(4, discussion.getMangaID());
			} else {
				stmt.setNull(4, 1);
			}

			stmt.setString(5, discussion.getPostContent());
			stmt.setInt(6, discussion.getLikeContent());
			stmt.executeUpdate();
			
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to update discussion, error with SQL");
		} 
	}

	//Method from getDiscussionsByUsername, byManga and byForum
	public List<DiscussionView> addToList(List<DiscussionView> discussionList, ResultSet resultSet, Boolean isForum)
			throws SQLException, ParseException {
		// discussions

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		int forumPostID = resultSet.getInt("DIS_FORUM_POST_ID");
		String postContent = resultSet.getString("DIS_CONTENT");
		int likes = resultSet.getInt("DIS_LIKES");
		String username = resultSet.getString("DIS_USERNAME");

		// posts
		int postID = resultSet.getInt("DIS_POST_ID");
		Date postDate = dateFormat.parse(resultSet.getString("DIS_DATE_POSTED"));

		boolean bothNull = false;
		int mangaID = -1;
		int forumID = -1;
		int nullCount = 0;
		String mangaTitle = null;
		String forumName = null;

		if (isForum == null || !isForum) {

			// manga
			mangaID = resultSet.getInt("DIS_MANGA_ID");
			mangaTitle = resultSet.getString("DIS_TITLE");
			if (resultSet.wasNull()) {
				if (isForum != null) {
					bothNull = true;
				} else {
					nullCount++;
				}
			}
		}

		if (isForum == null || isForum) {

			// forum
			forumID = resultSet.getInt("DIS_FORUM_ID");
			forumName = resultSet.getString("DIS_FORUM_NAME");
			if (resultSet.wasNull()) {
				if (isForum != null) {
					bothNull = true;
				} else {
					nullCount++;
				}
			}
		}

		if (!bothNull || nullCount >= 2) {
			Discussion discussion = new Discussion(forumPostID, postID, mangaID, forumID, postContent, likes);
			
			discussionList.add(new DiscussionView(discussion, mangaTitle, forumName, postDate, username));
		}
		return discussionList;
	}

//	public List<Discussion> getDiscussionsByUsername(String username) {
//		List<Discussion> discussionList = new ArrayList<Discussion>();
//		try {
//			PreparedStatement pstmt = connection.prepareStatement(
//					"SELECT DISCUSSIONS.FORUM_POST_ID AS DIS_FORUM_POST_ID, DISCUSSIONS.POST_ID AS DIS_POST_ID, DISCUSSIONS.FORUM_ID AS DIS_FORUM_ID, DISCUSSIONS.MANGA_ID AS DIS_MANGA_ID, DISCUSSIONS.POST_CONTENT AS DIS_CONTENT, DISCUSSIONS.LIKE_CONTENT AS DIS_LIKES FROM DISCUSSIONS INNER JOIN POSTS ON POSTS.POST_ID = DISCUSSIONS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE USERS.USERNAME=?");
//
//			pstmt.setString(1, username);
//			ResultSet resultSet = pstmt.executeQuery();
//
//			while (resultSet.next()) {
//				discussionList = addToList(discussionList, resultSet, null);
//			}
//			return discussionList;
//		} catch (SQLException sqle) {
//			sqle.printStackTrace();
//			log.warn("Failed to list discussions by username, error with SQL");
//		} return null;
//	}

	public List<DiscussionView> getDiscussionByForum(String forumName) {
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT DISCUSSIONS.FORUM_POST_ID AS DIS_FORUM_POST_ID, DISCUSSIONS.POST_ID AS DIS_POST_ID, DISCUSSIONS.FORUM_ID AS DIS_FORUM_ID, DISCUSSIONS.POST_CONTENT AS DIS_CONTENT, DISCUSSIONS.LIKE_CONTENT AS DIS_LIKES, FORUMS.FORUM_NAME AS DIS_FORUM_NAME, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS DIS_DATE_POSTED, USERS.USERNAME AS DIS_USERNAME FROM FORUMS INNER JOIN DISCUSSIONS ON DISCUSSIONS.FORUM_ID = FORUMS.FORUM_ID INNER JOIN POSTS ON POSTS.POST_ID = DISCUSSIONS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE FORUMS.FORUM_NAME=?");

			pstmt.setString(1, forumName);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				discussionList = addToList(discussionList, resultSet, true);
			}
			return discussionList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to list discussions by forum, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding list of forum discussions, error with Date data type");
		} return null;
	}

	public List<DiscussionView> getDiscussionByManga(String title) {
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT DISCUSSIONS.FORUM_POST_ID AS DIS_FORUM_POST_ID, DISCUSSIONS.POST_ID AS DIS_POST_ID, DISCUSSIONS.MANGA_ID AS DIS_MANGA_ID, DISCUSSIONS.POST_CONTENT AS DIS_CONTENT, DISCUSSIONS.LIKE_CONTENT AS DIS_LIKES, MANGA.TITLE AS DIS_TITLE, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS DIS_DATE_POSTED, USERS.USERNAME AS DIS_USERNAME  FROM MANGA INNER JOIN DISCUSSIONS ON MANGA.MANGA_ID = DISCUSSIONS.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = DISCUSSIONS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE MANGA.TITLE=?");

			pstmt.setString(1, title);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				discussionList = addToList(discussionList, resultSet, false);
			}
			return discussionList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to list discussions by manga, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding list of manga discussions, error with Date data type");
		} return null;
	}
	
	public List<DiscussionView> getTopThreeForumDiscussionsByDate() {
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT DIS_FORUM_POST_ID, DIS_POST_ID, DIS_FORUM_ID, DIS_CONTENT, DIS_LIKES, DIS_FORUM_NAME, TO_CHAR(DIS_DATEPOSTED, 'YYYY-MM-DD') AS DIS_DATE_POSTED, DIS_USERNAME FROM (SELECT DISCUSSIONS.FORUM_POST_ID AS DIS_FORUM_POST_ID, DISCUSSIONS.POST_ID AS DIS_POST_ID, DISCUSSIONS.FORUM_ID AS DIS_FORUM_ID, DISCUSSIONS.POST_CONTENT AS DIS_CONTENT, DISCUSSIONS.LIKE_CONTENT AS DIS_LIKES, FORUMS.FORUM_NAME AS DIS_FORUM_NAME, POSTS.DATE_POSTED AS DIS_DATEPOSTED, USERS.USERNAME AS DIS_USERNAME FROM FORUMS INNER JOIN DISCUSSIONS ON FORUMS.FORUM_ID = DISCUSSIONS.FORUM_ID INNER JOIN POSTS ON POSTS.POST_ID = DISCUSSIONS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE DISCUSSIONS.MANGA_ID IS NULL ORDER BY DISCUSSIONS.FORUM_POST_ID DESC) WHERE ROWNUM <= 3");
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				discussionList = addToList(discussionList, resultSet, true);
			}
			return discussionList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to list top forum discussions by date, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding list of forum discussions, error with Date data type");
		} return null;
	}
	
	public List<DiscussionView> getTopThreeMangaDiscussionsByDate() {
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT DIS_FORUM_POST_ID, DIS_POST_ID, DIS_MANGA_ID, DIS_TITLE, DIS_CONTENT, DIS_LIKES, TO_CHAR(DIS_DATEPOSTED, 'YYYY-MM-DD') AS DIS_DATE_POSTED, DIS_USERNAME FROM (SELECT DISCUSSIONS.FORUM_POST_ID AS DIS_FORUM_POST_ID, DISCUSSIONS.POST_ID AS DIS_POST_ID, DISCUSSIONS.MANGA_ID AS DIS_MANGA_ID, DISCUSSIONS.POST_CONTENT AS DIS_CONTENT, DISCUSSIONS.LIKE_CONTENT AS DIS_LIKES, MANGA.TITLE AS DIS_TITLE, POSTS.DATE_POSTED AS DIS_DATEPOSTED, USERS.USERNAME AS DIS_USERNAME FROM MANGA INNER JOIN DISCUSSIONS ON MANGA.MANGA_ID = DISCUSSIONS.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = DISCUSSIONS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE DISCUSSIONS.FORUM_ID IS NULL ORDER BY DISCUSSIONS.FORUM_POST_ID DESC) WHERE ROWNUM <= 3");
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				discussionList = addToList(discussionList, resultSet, false);
			}
			return discussionList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to list top manga discussions by date, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding list of manga discussions, error with Date data type");
		} return null;
	}
}
