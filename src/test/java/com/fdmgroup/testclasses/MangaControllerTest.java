package com.fdmgroup.testclasses;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.classes.MangaController;
import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.MangaDAO;

public class MangaControllerTest {

	private static MangaController controller;
	private MangaDAO mangaDao;
	private static TestHelper helper;
	
	@BeforeClass
	public static void beforeClass() {
		controller = new MangaController();
		helper = new TestHelper(Connections.getConnection());
	}

	@Before
	public void setUp() {
		mangaDao = new MangaDAO(Connections.getConnection());
		helper.dropMangaSequence();
		helper.dropMangaTable();
		helper.createMangaTable();
		helper.createMangaSequence();
	}

	@Test
	public void test_sortByID_arrangesInOrderOfTitle() {
		// ARRANGE
		List<Manga> unsortedMangaList;
		unsortedMangaList = mangaDao.listManga();	
		// ACT
		List<Manga> sortedList = controller.sortByID(Manga.Comparators.title);
		// ASSERT
		assertEquals(unsortedMangaList.get(1).getTitle(), sortedList.get(0).getTitle());
		assertEquals(unsortedMangaList.get(2).getTitle(), sortedList.get(1).getTitle());
		assertEquals(unsortedMangaList.get(0).getTitle(), sortedList.get(2).getTitle());	
	}

	@Test
	public void test_sortByID_arrangesInOrderOfAuthor() {
		// ARRANGE
		List<Manga> unsortedMangaList;
		unsortedMangaList = mangaDao.listManga();	
		// ACT
		List<Manga> sortedList = controller.sortByID(Manga.Comparators.author);
		// ASSERT
		assertEquals(unsortedMangaList.get(0).getAuthor(), sortedList.get(0).getAuthor());
		assertEquals(unsortedMangaList.get(2).getAuthor(), sortedList.get(1).getAuthor());
		assertEquals(unsortedMangaList.get(1).getAuthor(), sortedList.get(2).getAuthor());	
	}
	
	@Test
	public void test_sortByID_arrangesInOrderOfGenre() {
		// ARRANGE
		List<Manga> unsortedMangaList;
		unsortedMangaList = mangaDao.listManga();	
		// ACT
		List<Manga> sortedList = controller.sortByID(Manga.Comparators.genre);
		// ASSERT
		assertEquals(unsortedMangaList.get(1).getGenre(), sortedList.get(0).getGenre());
		assertEquals(unsortedMangaList.get(0).getGenre(), sortedList.get(1).getGenre());
		assertEquals(unsortedMangaList.get(2).getGenre(), sortedList.get(2).getGenre());	
	}
	
	@Test
	public void test_sortByID_arrangesInOrderOfDate() {
		// ARRANGE
		List<Manga> unsortedMangaList;
		unsortedMangaList = mangaDao.listManga();	
		// ACT
		List<Manga> sortedList = controller.sortByID(Manga.Comparators.date);
		// ASSERT
		assertEquals(unsortedMangaList.get(0).getPubDate(), sortedList.get(0).getPubDate());
		assertEquals(unsortedMangaList.get(1).getPubDate(), sortedList.get(1).getPubDate());
		assertEquals(unsortedMangaList.get(2).getPubDate(), sortedList.get(2).getPubDate());	
	}
	
	@Test
	public void test_sortByID_ChecksForNull() {
		// ARRANGE
		List<Manga> unsortedMangaList;
		unsortedMangaList = mangaDao.listManga();	
		// ACT
		List<Manga> sortedList = controller.sortByID(null);
		// ASSERT
		assertEquals(unsortedMangaList.get(0).getMangaID(), sortedList.get(0).getMangaID());
		assertEquals(unsortedMangaList.get(1).getMangaID(), sortedList.get(1).getMangaID());
		assertEquals(unsortedMangaList.get(2).getMangaID(), sortedList.get(2).getMangaID());	
	}
}
