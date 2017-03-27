package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.FavDAO;

public class FavDAOTest {

	private static FavDAO favDao;
	private static TestHelper helper;

	@BeforeClass
	public static void beforeClass() {
		favDao = new FavDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
	}

	@Before
	public void setUp() {
		helper.dropFavsSequence();
		helper.dropMangaSequence();
		helper.dropUsersSequence();
		helper.dropFavsTable();
		helper.dropUsersTable();
		helper.dropMangaTable();
		helper.createMangaTable();
		helper.createUsersTable();
		helper.createFavsTable();
		helper.createUsersSequence();
		helper.createMangaSequence();
		helper.createFavsSequence();
	}

	@Test
	public void test_listFavs_ReturnsTwo_WhenDaikonIsUser() {
		// ARRANGE
		List<Manga> listFavs = new ArrayList<Manga>();
		int expected = 2;
		// ACT
		listFavs = favDao.listFavsByUsername("daikon");
		// ASSERT
		assertEquals(expected, listFavs.size());
	}

	@Test
	public void test_listFavsByUsername_ReturnsZero_WhenUsernameIsInvalid() {
		// ARRANGE
		List<Manga> listFavs = new ArrayList<Manga>();
		int expected = 0;
		// ACT
		listFavs = favDao.listFavsByUsername("Alien");
		// ASSERT
		assertEquals(expected, listFavs.size());
	}

	@Test
	public void test_listFavsByUsername_ReturnsNull_WhenDatabaseDoesNotExist() {
		// ARRANGE
		List<Manga> listFavs = new ArrayList<Manga>();
		// ACT
		helper.dropFavsTable();
		listFavs = favDao.listFavsByUsername("daikon");
		// ASSERT
		assertNull(listFavs);
	}

	@Test
	public void test_addFav_ReturnsOne_WhenPotatoesAddsOneMangaToFavs() {
		// ARRANGE
		List<Manga> listFavs = new ArrayList<Manga>();
		int expected = 1;
		// ACT
		favDao.addFav(21, 21);
		listFavs = favDao.listFavsByUsername("potatoes");
		// ASSERT
		assertEquals(expected, listFavs.size());
	}

	@Test
	public void test_removeFav_Returns1_WhenDaikonRemovesOneFav() {
		// ARRANGE
		List<Manga> listFavs = new ArrayList<Manga>();
		int expected = 1;
		// ACT
		favDao.removeFav(24);
		listFavs = favDao.listFavsByUsername("daikon");
		// ASSERT
		assertEquals(expected, listFavs.size());
	}

	@AfterClass
	public static void closeConnection() {
		Connections.CloseConnection();
	}

}
