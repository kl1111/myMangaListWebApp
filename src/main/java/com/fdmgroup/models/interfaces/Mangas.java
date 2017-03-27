package com.fdmgroup.models.interfaces;

import java.util.List;

import com.fdmgroup.classes.Manga;

public interface Mangas {

	List<Manga> listManga();

	List<Manga> findMangaByAuthor(String author);

	List<Manga> findMangaByGenre(String genre);
	
	Manga findMangaByID(int mangaID);
	
	Manga findMangaByTitle(String title);
	
	void removeMangaByTitle(String title);
	
	void updateManga(Manga manga);
	
	void addManga(Manga manga);
}
