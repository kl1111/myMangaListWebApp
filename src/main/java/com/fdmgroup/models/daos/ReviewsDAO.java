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
import java.util.List;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.classes.Review;
import com.fdmgroup.classes.ReviewView;
import com.fdmgroup.models.interfaces.Reviews;

import java.util.ArrayList;
import java.util.Date;
import org.apache.log4j.Logger;

public class ReviewsDAO implements Reviews {

	private Connection connection = null;
	static Logger log = Logger.getLogger(ReviewsDAO.class);

	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public ReviewsDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<ReviewView> setReviews(ResultSet resultSet, List<ReviewView> listMangaRevs) throws ParseException, SQLException{
		int reviewID = resultSet.getInt("REV_REV_ID");
		int mangaID = resultSet.getInt("REV_MANGA_ID");
		int postID = resultSet.getInt("REV_POST_ID");
		int rating = resultSet.getInt("REV_RATING");
		String content = resultSet.getString("REV_CONTENT");
		String username = resultSet.getString("REV_USERNAME");
		String title = resultSet.getString("REV_TITLE");
		Date postDate = this.dateFormat.parse(resultSet.getString("REV_DATE_POSTED"));
		

		Review review = new Review(reviewID, mangaID, postID, rating, content);
		listMangaRevs.add(new ReviewView(review, title, postDate, username));
		return listMangaRevs;
}

	public void addReview(Review review, int user_id) {
		PostDAO postDao = new PostDAO(connection);
		LocalDateTime localDateTime = LocalDateTime.now();
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		Date date = new Date(zonedDateTime.toInstant().toEpochMilli());
		int postId = postDao.addPost(user_id, date);
		if (postId > 0) {
			try {
				CallableStatement stmt = connection.prepareCall("{CALL ADD_REVIEWS(?,?,?,?)}");
				stmt.setInt(1, review.getMangaID());
				stmt.setInt(2, postId);
				stmt.setInt(3, review.getRating());
				stmt.setString(4, review.getReviewContent());
				stmt.execute();
			} catch (SQLException sqle) {
				sqle.printStackTrace();
				postDao.deletePost(postId);
				log.warn("Failed to add review, error with SQL");
			} 
		}

	}

	public void deleteReview(int review_id, int post_id) {
		try {
			CallableStatement cstmt = connection.prepareCall("{CALL DELETE_REVIEWS(?)}");
			cstmt.setInt(1, review_id);
			cstmt.executeUpdate();
			
			PostDAO postDao = new PostDAO(connection);
			postDao.deletePost(post_id);
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to delete review, error with SQL");
		}
	}

	public void updateReview(Review review) {
		try {
			CallableStatement stmt = connection.prepareCall("{CALL UPDATE_REVIEWS(?,?,?,?)}");
			stmt.setInt(1, review.getReviewID());
			stmt.setInt(2, review.getMangaID());
			stmt.setInt(3, review.getRating());
			stmt.setString(4, review.getReviewContent());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to update review, error with SQL");
		} 	
	}
	
	public Review getReviewById(int review_id) {
		Review review = new Review();
		try {
			CallableStatement cstmt = connection.prepareCall("{CALL FIND_REVIEW_BY_ID(?,?,?,?,?,?)}");
			cstmt.setInt(1, review_id);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(4, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(5, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);

			cstmt.execute();

			int reviewID = cstmt.getInt(2);
			int mangaID = cstmt.getInt(3);
			int postID = cstmt.getInt(4);
			int rating = cstmt.getInt(5);
			String reviewContent = cstmt.getString(6);
			
			review = new Review(reviewID, mangaID, postID, rating, reviewContent);

			if (review.getReviewID() > 0){
				return review;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find review, error with SQL");
		}
		return null;
	}

	public List<ReviewView> getReviewsByManga(String title) {
		List<ReviewView> listMangaRevs = new ArrayList<ReviewView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT REVIEWS.REVIEW_ID AS REV_REV_ID, REVIEWS.MANGA_ID AS REV_MANGA_ID, REVIEWS.POST_ID AS REV_POST_ID, REVIEWS.RATING AS REV_RATING, REVIEWS.REVIEW_CONTENT AS REV_CONTENT, MANGA.TITLE AS REV_TITLE, USERS.USERNAME AS REV_USERNAME, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS REV_DATE_POSTED FROM MANGA INNER JOIN REVIEWS ON REVIEWS.MANGA_ID = MANGA.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = REVIEWS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE MANGA.TITLE=?");
			pstmt.setString(1, title);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				setReviews(resultSet, listMangaRevs);
			}
			return listMangaRevs;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to get list of reviews by manga, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding reviews, error with Date data type");
		} return null;
	}

	public List<ReviewView> getReviewsByUsername(String username) {
		List<ReviewView> listMangaRevs = new ArrayList<ReviewView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT REVIEWS.REVIEW_ID AS REV_REV_ID, REVIEWS.MANGA_ID AS REV_MANGA_ID, REVIEWS.POST_ID AS REV_POST_ID, REVIEWS.RATING AS REV_RATING, REVIEWS.REVIEW_CONTENT AS REV_CONTENT, MANGA.TITLE AS REV_TITLE, USERS.USERNAME AS REV_USERNAME, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS REV_DATE_POSTED FROM MANGA INNER JOIN REVIEWS ON REVIEWS.MANGA_ID = MANGA.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = REVIEWS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE USERS.USERNAME=?");
			pstmt.setString(1, username);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				setReviews(resultSet, listMangaRevs);
			}
			return listMangaRevs;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to get list of reviews by username, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding reviews, error with Date data type");
		} return null;
	}

	public List<ReviewView> getReviewsByRating(int rating) {
		List<ReviewView> listMangaRevs = new ArrayList<ReviewView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT REVIEWS.REVIEW_ID AS REV_REV_ID, REVIEWS.MANGA_ID AS REV_MANGA_ID, REVIEWS.POST_ID AS REV_POST_ID, REVIEWS.RATING AS REV_RATING, REVIEWS.REVIEW_CONTENT AS REV_CONTENT , MANGA.TITLE AS REV_TITLE, USERS.USERNAME AS REV_USERNAME, TO_CHAR(POSTS.DATE_POSTED, 'YYYY-MM-DD') AS REV_DATE_POSTED FROM MANGA INNER JOIN REVIEWS ON REVIEWS.MANGA_ID = MANGA.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = REVIEWS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID WHERE REVIEWS.RATING=?");
			pstmt.setInt(1, rating);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				setReviews(resultSet, listMangaRevs);
			}
			return listMangaRevs;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to get list of reviews by rating, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding reviews, error with Date data type");
		} return null;
	}
	
	public List<ReviewView> getTopThreeReviewsByDate() {
		List<ReviewView> listMangaRevs = new ArrayList<ReviewView>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT REV_REV_ID, REV_MANGA_ID, REV_POST_ID, REV_RATING, REV_CONTENT, REV_TITLE, REV_USERNAME, TO_CHAR(REV_DATEPOSTED, 'YYYY-MM-DD') AS REV_DATE_POSTED FROM (SELECT REVIEWS.REVIEW_ID AS REV_REV_ID, REVIEWS.MANGA_ID AS REV_MANGA_ID, REVIEWS.POST_ID AS REV_POST_ID, REVIEWS.RATING AS REV_RATING, REVIEWS.REVIEW_CONTENT AS REV_CONTENT, MANGA.TITLE AS REV_TITLE, USERS.USERNAME AS REV_USERNAME, POSTS.DATE_POSTED AS REV_DATEPOSTED FROM MANGA INNER JOIN REVIEWS ON MANGA.MANGA_ID = REVIEWS.MANGA_ID INNER JOIN POSTS ON POSTS.POST_ID = REVIEWS.POST_ID INNER JOIN USERS ON USERS.USER_ID = POSTS.USER_ID ORDER BY POSTS.DATE_POSTED DESC) WHERE ROWNUM <= 3");
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				
				setReviews(resultSet, listMangaRevs);
			}
			return listMangaRevs;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to top 3 reviews by rating, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Error finding reviews, error with Date data type");
		} return null;
	}
}
