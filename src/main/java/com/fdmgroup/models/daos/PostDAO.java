package com.fdmgroup.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Post;
import com.fdmgroup.models.interfaces.Posts;
import org.apache.log4j.Logger;

public class PostDAO implements Posts {

	private Connection connection = null;
	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static Logger log = Logger.getLogger(PostDAO.class);

	public PostDAO(Connection connection) {
		this.connection = connection;
	}

	public List<Post> setPosts(ResultSet resultSet, List<Post> listPosts) throws ParseException, SQLException {
		int postID = resultSet.getInt("POSTS_POST_ID");
		Date postDate = this.dateFormat.parse(resultSet.getString("POSTS_DATE_POSTED"));
		int userID = resultSet.getInt("POSTS_USER_ID");
		Post post = new Post(postID, userID, postDate);
		listPosts.add(post);
		return listPosts;
	}

	public int addPost(int user_id, Date postDate) {
		int postId = -1;
		try {
			CallableStatement stmt = connection.prepareCall("{CALL ADD_POSTS(?,?,?)}");
			stmt.setInt(1, user_id);
			stmt.setString(2, this.dateFormat.format(postDate));
			stmt.registerOutParameter(3, java.sql.Types.INTEGER);
			stmt.executeUpdate();

			postId = stmt.getInt(3);

			if (postId != 0) {
				return postId;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error adding post, error with SQL");
		}
		return -1;
	}

	public void deletePost(int post_id) {
		try {
			CallableStatement cstmt = connection.prepareCall("{CALL DELETE_POSTS(?)}");
			cstmt.setInt(1, post_id);
			cstmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Error deleting post, error with SQL");
		}
	}

	public List<Post> listAllPosts() {
		List<Post> listPosts = new ArrayList<Post>();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT POSTS.USER_ID AS POSTS_USER_ID, POSTS.POST_ID AS POSTS_POST_ID, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS POSTS_DATE_POSTED FROM POSTS");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				setPosts(resultSet, listPosts);
			}
			return listPosts;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Error obtaining list of posts, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error obtaining list of posts, error with Date data type");
		}
		return null;
	}

	public List<Post> listPostsByUsername(String username) {
		List<Post> listPosts = new ArrayList<Post>();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT POSTS.USER_ID AS POSTS_USER_ID, POSTS.POST_ID AS POSTS_POST_ID, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS POSTS_DATE_POSTED FROM USERS INNER JOIN POSTS ON POSTS.USER_ID = USERS.USER_ID WHERE USERS.USERNAME =?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				setPosts(resultSet, listPosts);
			}
			return listPosts;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Error obtaining list of posts by username, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error obtaining list of posts by username, error with Date data type");
		}
		return null;
	}

	public Post findPost(int post_id) {
		Post post = new Post();
		try {
			CallableStatement cstmt = connection.prepareCall("{CALL FIND_POST(?,?,?,?)}");
			cstmt.setInt(1, post_id);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.execute();

			int postID = cstmt.getInt(2);
			int userID = cstmt.getInt(3);
			Date postDate = null;
			try {
				postDate = this.dateFormat.parse(cstmt.getString(4));
			} catch (ParseException e) {
				e.printStackTrace();
				log.warn("Error finding post, error with Date data type");
			}
			post = new Post(postID, userID, postDate);

			if (post.getPostID() == post_id) {
				return post;
			}

		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Error finding post, error with SQL");
		}
		return null;
	}

	public List<Post> findPostByDate(Date postDate) {
		List<Post> listPosts = new ArrayList<Post>();
		try {
			PreparedStatement statement = connection.prepareStatement(
					"SELECT POSTS.USER_ID AS POSTS_USER_ID, POSTS.POST_ID AS POSTS_POST_ID, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS POSTS_DATE_POSTED FROM POSTS WHERE DATE_POSTED=TO_DATE(?, 'YYYY-MM-DD')");
			statement.setString(1, postDate.toString());
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				setPosts(resultSet, listPosts);
			}
			return listPosts;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Error finding post, error with SQL");			
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding post, error with Date data type");
		}
		return null;
	}

}
