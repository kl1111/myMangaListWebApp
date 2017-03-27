package com.fdmgroup.classes;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.fdmgroup.models.daos.Connections;
import com.fdmgroup.models.daos.MangaDAO;

public class MangaController {	

	public List<Manga> sortByID(Comparator<Manga> sortItem) {
		MangaDAO mangaDao = new MangaDAO(Connections.getConnection());
		List<Manga> mangaList = mangaDao.listManga();
		Connections.CloseConnection();
		if (sortItem != null) {
			Collections.sort(mangaList, sortItem);
		} else {
			Collections.sort(mangaList);
		}			
		for (Manga manga : mangaList) {
			if (sortItem == null) {
				System.out.println(manga.getMangaID() + " " + manga.getTitle());
			} else if (sortItem.equals(Manga.Comparators.title)) {
				System.out.println(manga.getMangaID() + " " + manga.getTitle());
			} else if (sortItem.equals(Manga.Comparators.author)) {
				System.out.println(manga.getAuthor() + " " + manga.getTitle());
			} else if (sortItem.equals(Manga.Comparators.genre)) {
				System.out.println(manga.getGenre() + " " + manga.getTitle());
			} else if (sortItem.equals(Manga.Comparators.date)) {
				System.out.println(manga.getPubDate() + " " + manga.getTitle());
			} else {
				System.out.println(manga.getMangaID() + " " + manga.getTitle());
			}
		}
		return mangaList;
	}
}
