package com.fdmgroup.testclasses;

import java.sql.Date;

import static org.junit.Assert.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Post;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.PostDAO;

public class PostDAOTest {

	private static PostDAO postDao;
	private static TestHelper helper;
	
	@BeforeClass
	public static void beforeClass() {
		postDao = new PostDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
	}

	@Before
	public void setUp() {
		helper.dropPostsSequence();
		helper.dropUsersSequence();
		helper.dropPostsTable();
		helper.dropUsersTable();
		helper.createUsersTable();
		helper.createPostsTable();
		helper.createUsersSequence();
		helper.createPostsSequence();
	}

	@Test
	public void test_listAllPosts_ReturnsNull_WhenDatabaseDoesntExist() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		// ACT
		helper.dropPostsTable();
		listPosts = postDao.listAllPosts();
		// ASSERT
		assertNull(listPosts);
	}

	@Test
	public void test_listAllPosts_ReturnsZero_WhenEntriesAreRemoved() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		int expected = 0;
		// ACT
		postDao.deletePost(31);
		postDao.deletePost(32);
		postDao.deletePost(33);
		postDao.deletePost(34);
		listPosts = postDao.listAllPosts();
		// ASSERT
		assertEquals(expected, listPosts.size());
	}

	@Test
	public void test_listAllPosts_ShowsAllEntriesInDatabase() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		int expected = 4;
		// ACT
		listPosts = postDao.listAllPosts();
		// ASSERT
		assertEquals(expected, listPosts.size());
	}

	@Test
	public void test_ListPostsByUser_ReturnsTwo_WhenUserIsPotatoes() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		int expected = 2;
		// ACT
		listPosts = postDao.listPostsByUsername("potatoes");	
		// ASSERT
		assertEquals(expected, listPosts.size());

	}
	
	@Test
	public void test_ListPostsByUser_ReturnsZero_WhenUsernameIsInvalid(){
		//ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		int expected = 0;
		//ACT		
		listPosts = postDao.listPostsByUsername("Alien");	
		//ASSERT
		assertEquals(expected, listPosts.size());
	}
	
	@Test
	public void test_ListPostsByUser_ReturnsNull_WhenDatabaseDoesNotExist(){
		//ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		//ACT		
		helper.dropPostsTable();
		listPosts = postDao.listPostsByUsername("daikon");
		//ASSERT
		assertNull(listPosts);
	}

	@Test
	public void test_addPost_IncreasesTotalNumberOfEntriesInDatabase() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		LocalDateTime localDateTime = LocalDateTime.of(2014, 05, 30, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		postDao.addPost(23, date);
		listPosts = postDao.listAllPosts();
		// ASSERT
		assertEquals(5, listPosts.size());
	}

	@Test
	public void test_addPost_IncreasesTotalNumberOfPostsByPotatoes() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		LocalDateTime localDateTime = LocalDateTime.of(2014, 05, 30, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		postDao.addPost(21, date);
		listPosts = postDao.listPostsByUsername("potatoes");
		// ASSERT
		assertEquals(3, listPosts.size());
	}
	
	@Test
	public void test_deletePost_RemovesOneEntryFromDataBase() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		// ACT
		postDao.deletePost(33);
		listPosts = postDao.listAllPosts();
		// ASSERT
		assertEquals(3, listPosts.size());
	}
	
	@Test
	public void test_deletePost_RemovesOneEntry_FromPotatoes() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		// ACT
		postDao.deletePost(33);
		listPosts = postDao.listPostsByUsername("potatoes");
		// ASSERT
		assertEquals(1, listPosts.size());
	}

	@Test
	public void test_findPostByID() {
		// ARRANGE
		Post post = new Post();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		LocalDateTime localDateTime = LocalDateTime.of(2014, 04, 25, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		int exUserID = 22;		
		// ACT
		post = postDao.findPost(32);
		// ASSERT
		assertEquals(exUserID, post.getUserID());
		assertEquals(dateformat.format(date), dateformat.format(post.getPostDate()));
		
	}

	@Test
	public void test_findPostByDate() {
		// ARRANGE
		List<Post> listPosts = new ArrayList<Post>();
		LocalDateTime localDateTime = LocalDateTime.of(2016, 12, 15, 0, 0, 0);
		ZonedDateTime zonedDateTime =
		localDateTime.atZone(ZoneId.systemDefault());
		java.util.Date date = new
		Date(zonedDateTime.toInstant().toEpochMilli());
		// ACT
		listPosts = postDao.findPostByDate(date);
		// ASSERT
		assertEquals(1, listPosts.size());
	}
	
	@AfterClass
	public static void closeConnection() {
		Connections.CloseConnection();
	}

}
