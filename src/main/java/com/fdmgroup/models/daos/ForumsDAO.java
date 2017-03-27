package com.fdmgroup.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Forum;
import com.fdmgroup.models.interfaces.Forums;
import org.apache.log4j.Logger;

public class ForumsDAO implements Forums {

	private Connection connection = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static Logger log = Logger.getLogger(ForumsDAO.class);

	public ForumsDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Forum> setForums(ResultSet resultSet, List<Forum> forumList) throws ParseException, SQLException{
			int forumID = resultSet.getInt("FORUMS_ID");
			int userID = resultSet.getInt("FORUMS_USER_ID");
			Date startDates = dateFormat.parse(resultSet.getString("FORUMS_STARTDATE"));
			String forumName = resultSet.getString("FORUMS_FORUM_NAME");
		
			Forum forum = new Forum(forumID, userID, startDates, forumName);
			forumList.add(forum);
			return forumList;
	}

	public void startForum(int userID, String forumName) {
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		Date date = new Date(zonedDateTime.toInstant().toEpochMilli());

		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL ADD_FORUMS(?,?,?)}");
			stmt.setInt(1, userID);
			stmt.setString(2, this.dateFormat.format(date));
			stmt.setString(3, forumName);
			stmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to create new Forum, error with SQL");
		}
	}

	public void deleteForum(int forum_id) {
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL DELETE_FORUMS(?)}");
			cstmt.setInt(1, forum_id);
			cstmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to deleting Forum, error with SQL");
		}
	}

	public void updateForum(Forum forum) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL UPDATE_FORUMS(?,?)}");
			stmt.setInt(1, forum.getForumID());
			stmt.setString(2, forum.getForumName());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to update Forum, error with SQL");
		}
	}

	public List<Forum> findForumByUsername(String username) {
		List<Forum> forumList = new ArrayList<Forum>();
		try {
			PreparedStatement statement = this.connection.prepareStatement(
					"SELECT FORUMS.FORUM_ID AS FORUMS_ID, FORUMS.USER_ID AS FORUMS_USER_ID, TO_CHAR(FORUMS.START_DATE, 'YYYY-MM-DD') AS FORUMS_STARTDATE, FORUMS.FORUM_NAME AS FORUMS_FORUM_NAME FROM FORUMS INNER JOIN USERS ON FORUMS.USER_ID = USERS.USER_ID WHERE USERS.USERNAME=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				setForums(resultSet, forumList);
			}
			return forumList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to find forums by username, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find forums by username, error with Date date type");
		}
		return null;
	}

	public List<Forum> findForumByDate(Date startDate) {
		List<Forum> forumList = new ArrayList<Forum>();
		try {
			PreparedStatement statement = this.connection.prepareStatement(
					"SELECT FORUMS.FORUM_ID AS FORUMS_ID, FORUMS.USER_ID AS FORUMS_USER_ID, TO_CHAR(FORUMS.START_DATE, 'YYYY-MM-DD') AS FORUMS_STARTDATE, FORUMS.FORUM_NAME AS FORUMS_FORUM_NAME FROM FORUMS WHERE FORUMS.START_DATE=TO_DATE(?, 'YYYY-MM-DD')");
			statement.setString(1, startDate.toString());
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				setForums(resultSet, forumList);
			}
			return forumList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to find forums by date, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find forums by date, error with Date date type");
		}
		return null;
	}

	public List<Forum> findAllForums() {
		List<Forum> forumList = new ArrayList<Forum>();
		try {
			Statement statement = this.connection.createStatement();
			ResultSet resultSet = statement.executeQuery(
					"SELECT FORUMS.FORUM_ID AS FORUMS_ID, FORUMS.USER_ID AS FORUMS_USER_ID, TO_CHAR(FORUMS.START_DATE, 'YYYY-MM-DD') AS FORUMS_STARTDATE, FORUMS.FORUM_NAME AS FORUMS_FORUM_NAME FROM FORUMS");

			while (resultSet.next()) {
				setForums(resultSet, forumList);
			}
			return forumList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to find forums, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find forums, error with Date date type");
		}
		return null;
	}

}
