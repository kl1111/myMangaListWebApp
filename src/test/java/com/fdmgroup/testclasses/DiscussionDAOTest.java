package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fdmgroup.classes.Discussion;
import com.fdmgroup.classes.DiscussionView;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.DiscussionsDAO;

public class DiscussionDAOTest {

	private static DiscussionsDAO discussionsDao;
	private static TestHelper helper;
	
	@Before
	public void setUp() {
		discussionsDao = new DiscussionsDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
		helper.dropDiscussionsSequence();
		helper.dropForumsSequence();
		helper.dropPostsSequence();
		helper.dropMangaSequence();
		helper.dropUsersSequence();
		helper.dropDiscussionsTable();
		helper.dropMangaTable();
		helper.dropPostsTable();
		helper.dropUsersTable();
		helper.dropForumsTable();
		helper.createUsersTable();
		helper.createMangaTable();
		helper.createPostsTable();
		helper.createForumsTable();
		helper.createDiscussionsTable();
		helper.createUsersSequence();
		helper.createMangaSequence();
		helper.createPostsSequence();
		helper.createForumsSequence();
		helper.createDiscussionsSequence();
	}

//	@Test
//	public void getDiscussionsByUsername_ReturnsNull_WhenDatabaseDoesNotExist() {
//		// ARRANGE
//		List<Discussion> discussionList = new ArrayList<Discussion>();
//		// ACT
//		helper.dropDiscussionsTable();
//		discussionList = discussionsDao.getDiscussionsByUsername("potatoes");
//		// ASSERT
//		assertNull(discussionList);
//	}

//	@Test
//	public void getDiscussionsByUsername_ReturnsEmpty_WhenPotatosEntriesAreDeleted() {
//		// ARRANGE
//		List<Discussion> discussionList = new ArrayList<Discussion>();
//		// ACT
//		discussionsDao.deleteDiscussion(2, 31);
//		discussionsDao.deleteDiscussion(4, 33);
//		discussionList = discussionsDao.getDiscussionsByUsername("potatoes");
//
//		// ASSERT
//		assertEquals(0, discussionList.size());
//	}

//	@Test
//	public void getDiscussionsByUsername_ReturnsTwo_WhenUserIsPotatoes() {
//		// ARRANGE
//		List<Discussion> discussionList = new ArrayList<Discussion>();
//
//		// ACT
//		discussionList = discussionsDao.getDiscussionsByUsername("potatoes");
//
//		// ASSERT
//		assertEquals(2, discussionList.size());
//	}

	@Test
	public void getDiscussionsByForum_ReturnsNull_WhenDatabaseDoesNotExist() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		helper.dropDiscussionsTable();
		discussionList = discussionsDao.getDiscussionByForum("you suck");
		// ASSERT
		assertNull(discussionList);
	}

	@Test
	public void getDiscussionByForum_ReturnsEmpty_WhenForumIDTwoIsDeleted() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		discussionsDao.deleteDiscussion(2, 31);
		discussionsDao.deleteDiscussion(3, 32);
		discussionList = discussionsDao.getDiscussionByForum("you suck");
		// ASSERT
		assertEquals(0, discussionList.size());
	}

	@Test
	public void getDiscussionByForum_ReturnsTwo_WhenForumIDIsTwo() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		discussionList = discussionsDao.getDiscussionByForum("you suck");
		// ASSERT
		assertEquals(2, discussionList.size());
	}

	@Test
	public void getDiscussionsByManga_ReturnsNull_WhenDatabaseDoesNotExist() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		helper.dropDiscussionsTable();
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		// ASSERT
		assertNull(discussionList);
	}

	@Test
	public void getDiscussionByManga_ReturnsEmpty_WhenOrangeMarmaladeEntriesAreDeleted() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		discussionsDao.deleteDiscussion(4, 33);
		discussionsDao.deleteDiscussion(5, 34);
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		// ASSERT
		assertEquals(0, discussionList.size());
	}

	@Test
	public void getDiscussionByManga_ReturnsTwo_WhenMangaIsOrangeMarmalade() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		// ACT
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		// ASSERT
		assertEquals(2, discussionList.size());
	}

	@Test
	public void addMangaDiscussion_IncreasesArraySizeTo3_WhenNewOrangeMarmaladeDiscussionIsWritten() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		int expected = 3;
		// ACT
		discussionsDao.addMangaDiscussion("Hey there", 22, 23);
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		// ASSERT
		assertEquals(expected, discussionList.size());

	}

	@Test
	public void addForumDiscussion_ReturnsThree_WhenNewForumDiscussionIsCreated() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		int expected = 3;
		// ACT
		discussionsDao.addForumDiscussion("mandem", 23, 2);
		discussionList = discussionsDao.getDiscussionByForum("you suck");
		// ASSERT
		assertEquals(expected, discussionList.size());
	}

	@Test
	public void deleteDiscussion_ReturnsOne_WhenOneEntryISDeletedFromForumID2() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		int expected = 1;
		// ACT
		discussionsDao.deleteDiscussion(2, 31);
		discussionList = discussionsDao.getDiscussionByForum("you suck");
		// ASSERT
		assertEquals(expected, discussionList.size());
	}

	@Test
	public void deleteDiscussion_ReturnsOne_WhenOneEntryISDeletedFromOrangeMarmalade() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		int expected = 1;
		// ACT
		discussionsDao.deleteDiscussion(4, 33);
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		// ASSERT
		assertEquals(expected, discussionList.size());
	}

	@Test
	public void updateDiscussion_UpdatesPostForOrangeMarmalade() {
		// ARRANGE
		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		Discussion discussion = new Discussion();
		discussion = discussionList.get(0).getDiscussion();
		// act
		discussion.setPostContent("WTH changed to 'I don't like this'");
		discussion.setLikeContent(5);
		discussionsDao.updateDiscussion(discussion);
		discussionList = discussionsDao.getDiscussionByManga("Orange Marmalade");
		discussion = discussionList.get(0).getDiscussion();
		// assert
		assertEquals("WTH changed to 'I don't like this'", discussion.getPostContent());
		assertEquals(5, discussion.getLikeContent());
		
	}
	
//	@Test
//	public void getTopThreeMangaDiscussionsByDate_GetTopThree() {
//		// ARRANGE
//		List<DiscussionView> discussionList = new ArrayList<DiscussionView>();
//		// act
//		discussionList = discussionsDao.getTopThreeMangaDiscussionsByDate();
//		Discussion discussion = discussionList.get(0).getDiscussion();
//		// assert
//		assertEquals("WTH changed to 'I don't like this'", discussion.getPostContent());
//		assertEquals(5, discussion.getLikeContent());
//		
//	}


	
	@After
	public void closeConnection() {
		Connections.CloseConnection();
	}

}
