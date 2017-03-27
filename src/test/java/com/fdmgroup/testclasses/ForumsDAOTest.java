package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Forum;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.ForumsDAO;

public class ForumsDAOTest {

	private static ForumsDAO forumsDao;
	private static TestHelper helper;

	@BeforeClass
	public static void beforeClass() {
		forumsDao = new ForumsDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
	}

	@Before
	public void setUp() {
		helper.dropForumsSequence();
		helper.dropUsersSequence();
		helper.dropForumsTable();
		helper.dropUsersTable();
		helper.createUsersTable();
		helper.createForumsTable();
		helper.createUsersSequence();
		helper.createForumsSequence();
	}
	
	@Test
	public void findAllForums_ReturnsNull_WhenDatabaseDoesNotExist() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		helper.dropForumsTable();
		forumList = forumsDao.findAllForums();
		// ASSERT
		assertNull(forumList);

	}

	@Test
	public void findAllForums_ReturnsEmpty_WhenEntriesAreDeleted() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.deleteForum(2);
		forumsDao.deleteForum(3);
		forumsDao.deleteForum(4);
		forumList = forumsDao.findAllForums();
		// ASSERT
		assertEquals(0, forumList.size());

	}

	@Test
	public void findAllForums_ReturnsAllInDatabase() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumList = forumsDao.findAllForums();
		// ASSERT
		assertEquals(3, forumList.size());

	}

	@Test
	public void findForumByUsername_ReturnsTwo_WhenUserIsPotatoes() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumList = forumsDao.findForumByUsername("potatoes");
		// ASSERT
		assertEquals(2, forumList.size());
	}

	@Test
	public void findForumByUsername_ReturnsZero_WhenUserIsPotatoesAndEntriesAreRemoved() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.deleteForum(3);
		forumsDao.deleteForum(4);
		forumList = forumsDao.findForumByUsername("potatoes");
		// ASSERT
		assertEquals(0, forumList.size());
	}

	@Test
	public void findForumByDate_returnsEmpty_WhenEntriesAreDeleted() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		LocalDateTime localDateTime = LocalDateTime.of(2016, 12, 11, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		forumsDao.deleteForum(3);
		forumsDao.deleteForum(4);
		forumList = forumsDao.findForumByDate(date);
		// ASSERT
		assertEquals(0, forumList.size());
	}

	@Test
	public void findForumByDate_returnsTwo_WhenDateIs_2016_12_11() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		LocalDateTime localDateTime = LocalDateTime.of(2016, 12, 11, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		forumList = forumsDao.findForumByDate(date);
		// ASSERT
		assertEquals(2, forumList.size());
	}

	@Test
	public void startForum_EqualsFour_WhenOneNewForumIsStarted() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.startForum(21, "Boo");
		forumList = forumsDao.findAllForums();
		// ASSERT
		assertEquals(4, forumList.size());
	}

	@Test
	public void startForum_EqualsTwo_WhenTomatoesStartsNewForum() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.startForum(22, "Coffee");
		forumList = forumsDao.findForumByUsername("tomatoes");
		// ASSERT
		assertEquals(2, forumList.size());
	}

	@Test
	public void startForum_EqualsThree_WhenNewForumIsStartedOn_2016_12_12() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		LocalDateTime localDateTime = LocalDateTime.now();
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		forumsDao.startForum(22, "Coffee");
		forumList = forumsDao.findForumByDate(date);
		// ASSERT
		assertEquals(1, forumList.size());
	}

	@Test
	public void deleteForum_LeavesTwoInTotalDatabase_WhenOneEntryIsDeleted() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.deleteForum(4);
		forumList = forumsDao.findAllForums();
		// ASSERT
		assertEquals(2, forumList.size());
	}

	@Test
	public void deleteForum_LeavesOneEntryForPotatoes_WhenOneEntryIsDeleted() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		// ACT
		forumsDao.deleteForum(4);
		forumList = forumsDao.findForumByUsername("potatoes");
		// ASSERT
		assertEquals(1, forumList.size());
	}

	@Test
	public void deleteForum_ReturnsEmpty_WhenOneEntryIsDeletedFor_2016_12_05() {
		// ARRANGE
		List<Forum> forumList = new ArrayList<Forum>();
		LocalDateTime localDateTime = LocalDateTime.of(2016, 12, 05, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		// ACT
		forumsDao.deleteForum(2);
		forumList = forumsDao.findForumByDate(date);
		// ASSERT
		assertEquals(0, forumList.size());
	}

	@Test
	public void updateForum_ChangesNameOfForum4() {
		// arrange
		List<Forum> forumList = new ArrayList<Forum>();
		// act
		forumList = forumsDao.findForumByUsername("tomatoes");
		Forum forum = new Forum();
		forum = forumList.get(0);
		forum.setForumName("no way");
		forumsDao.updateForum(forum);
		forumList = forumsDao.findForumByUsername("tomatoes");
		// assert
		assertEquals("no way", forumList.get(0).getForumName());
	}

	@AfterClass
	public static void closeConnection() {
		Connections.CloseConnection();
	}
}
