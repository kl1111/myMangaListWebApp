package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Review;
import com.fdmgroup.classes.ReviewView;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.ReviewsDAO;

public class ReviewsDAOTest {
	
	private static ReviewsDAO reviewsDao;
	private static TestHelper helper;
	
	@Before
	public void setUp() {
		reviewsDao = new ReviewsDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
		helper.dropReviewsSequence();
		helper.dropPostsSequence();
		helper.dropMangaSequence();
		helper.dropUsersSequence();
		helper.dropReviewsTable();
		helper.dropMangaTable();
		helper.dropPostsTable();
		helper.dropUsersTable();
		helper.createUsersTable();
		helper.createMangaTable();
		helper.createPostsTable();
		helper.createReviewsTable();
		helper.createUsersSequence();
		helper.createMangaSequence();
		helper.createPostsSequence();
		helper.createReviewsSequence();
	}

	@Test
	public void addReview_Returns3_WhenPotatoesWritesNewReview(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected=3;
		
		Review review = new Review();
		review.setReviewID(15);
		review.setMangaID(21);
		review.setRating(7);
		review.setReviewContent("Maybe not");
		//ACT
		reviewsDao.addReview(review, 21);
		listReviews = reviewsDao.getReviewsByUsername("potatoes");
		//ASSERT
		assertEquals(expected, listReviews.size());
		
	}
	
	@Test
	public void deleteReview_ReturnsOneReviewInArray_WhenReviewByPotatoesIsDeleted(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 1;
		//ACT
		reviewsDao.deleteReview(13, 33);
		listReviews = reviewsDao.getReviewsByUsername("potatoes");
		//ASSERT	
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void updateReview(){	
		List<ReviewView> listReview = new ArrayList<ReviewView>();
		listReview = reviewsDao.getReviewsByManga("Untouchable");
	//	assertNotNull(listReview);
		Review review = new Review();
		review = listReview.get(0).getReview();
		//ACT
		review.setRating(10);
		review.setReviewContent("Woooow");
		reviewsDao.updateReview(review);
		listReview = reviewsDao.getReviewsByManga("Untouchable");
		review = listReview.get(0).getReview();
		//ASSERT		
		assertEquals(10, review.getRating());
		assertEquals("Woooow", review.getReviewContent());		
		
	}
	
	@Test
	public void getReviewsByManga_ReturnsNull_IfDatabaseDoesNotExist(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		helper.dropReviewsTable();
		listReviews = reviewsDao.getReviewsByManga("Untouchable");
		//ASSERT	
		assertNull(listReviews);
	}
	
	@Test
	public void getReviewsByManga_ReturnsTwo_WhenMangaIsUntouchable(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 2;	
		//ACT
		listReviews = reviewsDao.getReviewsByManga("Untouchable");
		//ASSERT	
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void getReviewsByMangaUntouchable_ReturnsZero_IfAllReviewsForUntouchableAreDeleted(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 0;
		//ACT
		reviewsDao.deleteReview(11, 31);
		reviewsDao.deleteReview(12, 32);
		listReviews = reviewsDao.getReviewsByManga("Untouchable");
		//ASSERT	
		assertEquals(expected, listReviews.size());
	}
	

	@Test
	public void getReviewsByUsername_ReturnsNull_IfDatabaseDoesNotExist(){
		//ARRANGE		
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		helper.dropReviewsTable();
		listReviews = reviewsDao.getReviewsByUsername("potatoes");
		//ASSERT	
		assertNull(listReviews);	
	}
	
	@Test
	public void getReviewsByUsername_ReturnsTwo_WhenUserIsPotatoes(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 2;	
		//ACT
		listReviews = reviewsDao.getReviewsByUsername("potatoes");
		//ASSERT	
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void getReviewsByUsername_ReturnsZero_IfAllReviewsForPotatoesAreDeleted(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 0;
		//ACT
		reviewsDao.deleteReview(11, 31);
		reviewsDao.deleteReview(13, 33);
		listReviews = reviewsDao.getReviewsByUsername("potatoes");
		//ASSERT	
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void getReviewsByRating_ReturnsNull_IfDatabaseDoesNotExist(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		helper.dropReviewsTable();
		listReviews = reviewsDao.getReviewsByRating(0);
		//ASSERT		
		assertNull(listReviews);
	}
	
	@Test
	public void getReviewsByRating_ReturnsTwo_WhenRatingIs8(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 2;
		//ACT
		listReviews = reviewsDao.getReviewsByRating(8);
		//ASSERT		
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void getReviewsByRating_ReturnsEmptyList_WhenRatingIs8AndAllEntriesAreDeleted(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		int expected = 0 ;
		//ACT
		reviewsDao.deleteReview(12, 32);
		reviewsDao.deleteReview(13, 33);
		listReviews = reviewsDao.getReviewsByRating(8);
		//ASSERT		
		assertEquals(expected, listReviews.size());
	}
	
	@Test
	public void getTopThreeReviewsByDate_ReturnsNull_WhenDatabaseIsNonExistent(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		helper.dropReviewsTable();
		listReviews = reviewsDao.getTopThreeReviewsByDate();
		//ASSERT
		assertNull(listReviews);
	}
	
	@Test
	public void getTopThreeReviewsByDate_ReturnsCorrectArray_WhenEntriesInDatabaseAreSearched(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		listReviews = reviewsDao.getTopThreeReviewsByDate();
		
		Review review1 = new Review();
		review1 = listReviews.get(0).getReview();
			
		Review review2 = new Review();
		review2 = listReviews.get(1).getReview();		
		
		Review review3 = new Review();
		review3 = listReviews.get(2).getReview();	

		//ASSERT
		assertEquals(13, review1.getReviewID());
		assertEquals(11, review2.getReviewID());
		assertEquals(14, review3.getReviewID());
	}
	
	@Test
	public void getTopThreeReviewsByDate_ReturnsTopThree_WhenSomeEntriesEntriesAreDeleted(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		reviewsDao.deleteReview(11, 31);		
		listReviews = reviewsDao.getTopThreeReviewsByDate();
		
		Review review1 = new Review();
		review1 = listReviews.get(0).getReview();
			
		Review review2 = new Review();
		review2 = listReviews.get(1).getReview();
		
		Review review3 = new Review();
		review3 = listReviews.get(2).getReview();
		//ASSERT
		assertEquals(13, review1.getReviewID());
		assertEquals(14, review2.getReviewID());
		assertEquals(12, review3.getReviewID());
	}
	
	@Test
	public void getTopThreeReviewsByDate_ReturnsMostRecentReviewsByDate_WhenDatabaseExpandsInSize(){
		//ARRANGE
		List<ReviewView> listReviews = new ArrayList<ReviewView>();
		//ACT
		Review review1 = new Review();
		review1.setReviewID(15);
		review1.setMangaID(21);
		review1.setRating(7);
		review1.setReviewContent("Maybe not");
		review1.getPostID();			
		reviewsDao.addReview(review1, 21);		

		listReviews = reviewsDao.getTopThreeReviewsByDate();
		review1 = listReviews.get(0).getReview();
		Review review2 = new Review();
		Review review3 = new Review();
		review2 = listReviews.get(1).getReview();
		review3 = listReviews.get(2).getReview();
	
		//ASSERT
		assertEquals(15, review1.getReviewID());
		assertEquals(13, review2.getReviewID());
		assertEquals(11, review3.getReviewID());
	}

	@After
	public void closeConnection() {
		Connections.CloseConnection();
	}
}
