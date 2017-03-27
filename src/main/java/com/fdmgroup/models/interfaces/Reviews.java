package com.fdmgroup.models.interfaces;

import java.util.List;

import com.fdmgroup.classes.Review;
import com.fdmgroup.classes.ReviewView;

public interface Reviews {
	
	void addReview(Review review, int user_id);
	
	void deleteReview(int review_id, int post_id);
	
	void updateReview(Review review);
	
	List<ReviewView> getReviewsByManga(String title);
	
	List<ReviewView> getReviewsByUsername(String username);
	
	List<ReviewView> getReviewsByRating(int rating);
	
	List<ReviewView> getTopThreeReviewsByDate();
	
	Review getReviewById(int review_id);
}
