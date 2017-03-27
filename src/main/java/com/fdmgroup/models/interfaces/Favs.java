package com.fdmgroup.models.interfaces;

import java.util.List;

import com.fdmgroup.classes.Manga;

public interface Favs {
	
	void addFav(int user_id, int manga_id);
	
	void removeFav(int fav_id);
	
	List<Manga> listFavsByUsername(String username);
	
	List<Manga> top5FavMangas();
}
