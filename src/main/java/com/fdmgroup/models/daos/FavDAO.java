package com.fdmgroup.models.daos;

import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.models.interfaces.Favs;
import org.apache.log4j.Logger;

public class FavDAO implements Favs {

	private Connection connection = null;
	static Logger log = Logger.getLogger(FavDAO.class);
	
	public FavDAO(Connection connection){
		this.connection = connection;
	}
	
	public void addFav(int user_id, int manga_id) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL ADD_FAVOURITES(?,?)}");
			stmt.setInt(1, user_id);
			stmt.setInt(2, manga_id);
			stmt.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to add fav, error with SQL");
		} 
	}

	public void removeFav(int fav_id) {
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL DELETE_FAVS(?)}");
			cstmt.setInt(1, fav_id);
			cstmt.executeUpdate();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to delete fav, error with SQL");
		} 
	}

	public List<Manga> listFavsByUsername(String username) {
		List<Manga> listFavs = new ArrayList<Manga>();
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT FAVOURITES.MANGA_ID AS FAV_MANGA_ID, MANGA.TITLE AS FAV_MANGA_TITLE, MANGA.AUTHOR AS FAV_MANGA_AUTHOR, MANGA.GENRE AS FAV_MANGA_GENRE, MANGA.SUMMARY AS FAV_MANGA_SUMMARY, TO_CHAR(MANGA.PUBLICATION_DATE, 'YYYY-MM-DD') AS FAV_MANGA_PUBDATE, MANGA.IMAGE_URL AS FAV_MANGA_URL FROM USERS INNER JOIN FAVOURITES ON USERS.USER_ID = FAVOURITES.USER_ID INNER JOIN MANGA ON FAVOURITES.MANGA_ID = MANGA.MANGA_ID WHERE USERS.USERNAME=?");
			statement.setString(1, username);
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int mangaID = resultSet.getInt("FAV_MANGA_ID");
				String title = resultSet.getString("FAV_MANGA_TITLE");
				String author = resultSet.getString("FAV_MANGA_AUTHOR");
				String genre = resultSet.getString("FAV_MANGA_GENRE");
				String summary = resultSet.getString("FAV_MANGA_SUMMARY");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date pubDate = null;
				try {
					pubDate = dateformat.parse(resultSet.getString("FAV_MANGA_PUBDATE"));
				} catch (ParseException e) {
					e.printStackTrace();
					log.warn("Failed to list favs, error with Date data type");
				}
				String  url = resultSet.getString("FAV_MANGA_URL");
				Manga manga = new Manga(mangaID, title, author, genre, summary, pubDate, url);				
				listFavs.add(manga);
			}
			return listFavs;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to list favs, error with SQL");
		}
		return null;
	}
	
	public List<Manga> top5FavMangas(){
		List<Manga> listFavs = new ArrayList<Manga>();
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT USERS_ID, FAV_MANGAID, FAV_TITLE, FAV_AUTHOR, FAV_GENRE, FAV_SUMMARY, TO_CHAR(MAN_PUBDATE, 'YYYY-MM-DD') AS FAV_PUDATE, FAV_URL FROM (SELECT COUNT(FAVOURITES.USER_ID) AS USERS_ID, FAVOURITES.MANGA_ID AS FAV_MANGAID, MANGA.TITLE AS FAV_TITLE, MANGA.AUTHOR AS FAV_AUTHOR, MANGA.GENRE AS FAV_GENRE, MANGA.SUMMARY AS FAV_SUMMARY, MANGA.PUBLICATION_DATE AS MAN_PUBDATE, MANGA.IMAGE_URL AS FAV_URL FROM MANGA INNER JOIN FAVOURITES ON FAVOURITES.MANGA_ID = MANGA.MANGA_ID GROUP BY FAVOURITES.MANGA_ID, MANGA.TITLE, MANGA.AUTHOR, MANGA.GENRE, MANGA.SUMMARY, MANGA.PUBLICATION_DATE, MANGA.IMAGE_URL ORDER BY USERS_ID DESC) WHERE ROWNUM <=5");
			ResultSet resultSet = statement.executeQuery();

			while (resultSet.next()) {
				int mangaID = resultSet.getInt("FAV_MANGAID");
				String title = resultSet.getString("FAV_TITLE");
				String author = resultSet.getString("FAV_AUTHOR");
				String genre = resultSet.getString("FAV_GENRE");
				String summary = resultSet.getString("FAV_SUMMARY");
				SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
				Date pubDate = null;
				try {
					pubDate = dateformat.parse(resultSet.getString("FAV_PUDATE"));
				} catch (ParseException e) {
					e.printStackTrace();
					log.warn("Failed to list favs, error with Date data type");
				}
				String url = resultSet.getString("FAV_URL");
				Manga manga = new Manga(mangaID, title, author, genre, summary, pubDate, url);				
				listFavs.add(manga);
			}
			return listFavs;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to list favs, error with SQL");
		}
		return null;
	}
}
