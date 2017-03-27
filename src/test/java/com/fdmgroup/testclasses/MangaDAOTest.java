package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.MangaDAO;

public class MangaDAOTest {

	private static MangaDAO mangaDao;
	private static TestHelper helper;

	@BeforeClass
	public static void beforeClass() {
		mangaDao = new MangaDAO(Connections.getConnection());
		helper = new TestHelper(Connections.getConnection());
	}

	@Before
	public void setUp() {
		helper.dropMangaSequence();
		helper.dropMangaTable();
		helper.createMangaTable();
		helper.createMangaSequence();
	}

	@Test
	public void test_listManga_CheckEmptyDatabase() {
		// ARRANGE
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 0;
		// ACT
		mangaDao.removeMangaByTitle("Untouchable");
		mangaDao.removeMangaByTitle("Cheese in the Trap");
		mangaDao.removeMangaByTitle("Orange Marmalade");

		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@Test
	public void test_ListManga_RetrieveCorrectManga() {
		// arrange
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 3;
		// act
		mangaList = mangaDao.listManga();
		// assert
		assertEquals(expected, mangaList.size());
	}

	// This test works for listMangaByTitle, listMangaByGenre and
	// listMangaByAuthor
	@Test
	public void test_listManga_ReturnsNull() {
		// ARRANGE
		List<Manga> mangaList = new ArrayList<Manga>();
		// ACT
		helper.dropMangaTable();
		mangaList = mangaDao.listManga();
		// ASSERT
		assertNull(mangaList);
	}

	@Test
	public void test_findManga_ReturnsNull_IfDatabaseIsEmpty() {
		// ARRANGE
		List<Manga> mangaList;
		// ACT
		helper.dropMangaTable();
		mangaList = mangaDao.listManga();
		// ASSERT
		assertNull(mangaList);
	}

	@Test
	public void test_findMangaByTitle() {
		// ARRANGE
		Manga manga;
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		LocalDateTime localDateTime = LocalDateTime.of(2014, 07, 01, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);
		int exID = 22;
		String exTitle = "Cheese in the Trap";
		String exAuthor = "soonkki";
		String exGenre = "Drama";
		// ACT
		manga = mangaDao.findMangaByTitle("Cheese in the Trap");
		// ASSERT
		assertEquals(exID, manga.getMangaID());
		assertEquals(exTitle, manga.getTitle());
		assertEquals(exAuthor, manga.getAuthor());
		assertEquals(exGenre, manga.getGenre());
		assertEquals(dateformat.format(date), dateformat.format(manga.getPubDate()));
	}

	@Test
	public void test_findMangaByAuthor() {
		// ARRANGE
		List<Manga> mangaList;
		int expected = 1;
		// ACT
		mangaList = mangaDao.findMangaByAuthor("soonkki");
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@Test
	public void test_findMangaByGenre() {
		// ARRANGE
		List<Manga> mangaList;
		int expected = 2;
		// ACT
		mangaList = mangaDao.findMangaByGenre("Romance");
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@Test
	public void test_removeMangaByTitle_DeletesUntouchable() {
		// ARRANGE
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 2;
		// ACT
		mangaDao.removeMangaByTitle("Untouchable");
		mangaList = mangaDao.listManga();
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@Test
	public void test_removeMangaByTitle_InvalidTitleHasNoEffectOnArrayListSize() {
		// ARRANGE
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 3;
		// ACT
		mangaDao.removeMangaByTitle("Random");
		mangaList = mangaDao.listManga();
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	// UpdateManga method only works by title.
	@Test
	public void test_updateManga() {
		// ARRANGE
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");

		LocalDateTime localDateTime = LocalDateTime.of(2014, 07, 01, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);

		Manga manga = new Manga();
		manga.setTitle("Untouchable");
		manga.setAuthor("instantmiso");
		manga.setGenre("Romance");
		manga.setSummary("N/A");
		manga.setPubDate(date);
		manga.setUrl("url");
		// ACT
		mangaDao.updateManga(manga);
		manga = mangaDao.findMangaByTitle("Untouchable");

		// ASSERT
		assertEquals(21, manga.getMangaID());
		assertEquals("Untouchable", manga.getTitle());
		assertEquals("instantmiso", manga.getAuthor());
		assertEquals("Romance", manga.getGenre());
		assertEquals("N/A", manga.getSummary());
		assertEquals(dateformat.format(date), dateformat.format(manga.getPubDate()));
	}

	@Test
	public void test_addManga_IncreasesArraySize() {
		// arrange
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 4;
		Manga manga = new Manga();

		LocalDateTime localDateTime = LocalDateTime.of(2014, 07, 01, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);

		manga.setTitle("asdbasd");
		manga.setAuthor("sdfs");
		manga.setGenre("Romance");
		manga.setSummary("N/A");
		manga.setPubDate(date);

		// ACT
		mangaDao.addManga(manga);
		mangaList = mangaDao.listManga();
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@Test
	public void test_addManga_DupeMangaLeavesArraySizeUnchanged() {
		// ARRANGE
		List<Manga> mangaList = new ArrayList<Manga>();
		int expected = 3;
		Manga manga = new Manga();

		LocalDateTime localDateTime = LocalDateTime.of(2014, 07, 01, 0, 0, 0);
		java.util.Date date = TestHelper.getDate(localDateTime);

		manga.setTitle("Untouchable");
		manga.setPubDate(date);
		// ACT
		mangaDao.addManga(manga);
		mangaList = mangaDao.listManga();
		// ASSERT
		assertEquals(expected, mangaList.size());
	}

	@AfterClass
	public static void closeConnection() {
		Connections.CloseConnection();
	}
}
