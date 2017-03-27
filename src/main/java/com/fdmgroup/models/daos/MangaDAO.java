package com.fdmgroup.models.daos;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.fdmgroup.classes.Manga;
import com.fdmgroup.models.interfaces.Mangas;
import org.apache.log4j.Logger;

public class MangaDAO implements Mangas {

	private Connection connection = null;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	static Logger log = Logger.getLogger(MangaDAO.class);

	public MangaDAO(Connection connection) {
		this.connection = connection;
	}
	
	public List<Manga> setManga(ResultSet resultSet, List<Manga> mangaList) throws ParseException, SQLException{
		int mangaID = resultSet.getInt("MANGA_ID");
		String title = resultSet.getString("TITLE");
		String author = resultSet.getString("AUTHOR");
		String genre = resultSet.getString("GENRE");
		String summary = resultSet.getString("SUMMARY");
		Date date = this.dateFormat.parse(resultSet.getString("PUB_DATE"));
		String url = resultSet.getString("IMAGE_URL");
		Manga manga = new Manga(mangaID, title, author, genre, summary, date, url);
		mangaList.add(manga);
		return mangaList;
}

	public List<Manga> listManga() {
		List<Manga> mangaList = new ArrayList<Manga>();
		try {
			Statement statement = this.connection.createStatement();
			ResultSet resultSet = statement
					.executeQuery("SELECT MANGA_ID, TITLE, AUTHOR, GENRE, SUMMARY, TO_CHAR(PUBLICATION_DATE, 'YYYY-MM-DD') AS PUB_DATE, IMAGE_URL FROM MANGA");
			while (resultSet.next()) {
				setManga(resultSet, mangaList);
			}
			return mangaList;
		} catch (SQLException e) {
			e.printStackTrace();
			log.warn("Failed to get list of all manga, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to get list of all manga, error with Date data type");
		}
		return null;
	}
	
	public Manga findMangaByID(int mangaID) {
		Manga manga = new Manga();
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL FIND_MANGA_BY_ID(?,?,?,?,?,?,?,?)}");

			cstmt.setInt(1, mangaID);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			cstmt.executeQuery();

			int mangaIDs = cstmt.getInt(2);
			String titles = cstmt.getString(3);
			String author = cstmt.getString(4);
			String genre = cstmt.getString(5);
			String summary = cstmt.getString(6);
			Date pubDate = this.dateFormat.parse(cstmt.getString(7));
			String url = cstmt.getString(8);

			manga = new Manga(mangaIDs, titles, author, genre, summary, pubDate, url);

			if (manga.getTitle() != null) {
				return manga;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find manga by title, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find manga by title, error with Date date type");
		}
		return null;
	}

	public Manga findMangaByTitle(String title) {
		Manga manga = new Manga();
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL FIND_MANGA_BY_TITLE(?,?,?,?,?,?,?,?)}");

			cstmt.setString(1, title);
			cstmt.registerOutParameter(2, java.sql.Types.INTEGER);
			cstmt.registerOutParameter(3, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(4, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			cstmt.registerOutParameter(8, java.sql.Types.VARCHAR);

			cstmt.executeQuery();

			int mangaID = cstmt.getInt(2);
			String titles = cstmt.getString(3);
			String author = cstmt.getString(4);
			String genre = cstmt.getString(5);
			String summary = cstmt.getString(6);
			Date pubDate = this.dateFormat.parse(cstmt.getString(7));
			String url = cstmt.getString(8);

			manga = new Manga(mangaID, titles, author, genre, summary, pubDate, url);

			if (manga.getTitle() != null) {
				return manga;
			}
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find manga by title, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find manga by title, error with Date date type");
		}
		return null;
	}
	
	public List<Manga> findMangaByAuthor(String author) {
		List<Manga> mangaList = new ArrayList<Manga>();
		try {
			PreparedStatement pstmt = connection.prepareStatement(
					"SELECT MANGA_ID, TITLE, AUTHOR, GENRE, SUMMARY, TO_CHAR(PUBLICATION_DATE,'YYYY-MM-DD') AS PUB_DATE, IMAGE_URL FROM MANGA WHERE AUTHOR=?");
			pstmt.setString(1, author);
			ResultSet resultSet = pstmt.executeQuery();

			while (resultSet.next()) {
				setManga(resultSet, mangaList);
			}
			return mangaList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find manga by author, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find manga by author, error with Date date type");
		}
		return null;
	}

	public List<Manga> findMangaByGenre(String genre) {
		List<Manga> mangaList = new ArrayList<Manga>();
		try {
			PreparedStatement pstmt = this.connection.prepareStatement(
					"SELECT MANGA_ID, TITLE, AUTHOR, GENRE, SUMMARY, TO_CHAR(PUBLICATION_DATE,'YYYY-MM-DD') AS PUB_DATE, IMAGE_URL FROM MANGA WHERE GENRE=?");
			pstmt.setString(1, genre);
			ResultSet resultSet = pstmt.executeQuery();
			while (resultSet.next()) {
				setManga(resultSet, mangaList);
			}
			return mangaList;
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to find manga by genre, error with SQL");
		} catch (ParseException e) {
			e.printStackTrace();
			log.warn("Failed to find manga by genre, error with Date date type");
		}
		return null;
	}

	public void removeMangaByTitle(String title) {
		try {
			CallableStatement cstmt = this.connection.prepareCall("{CALL DELETE_MANGA_BY_TITLE(?)}");
			cstmt.setString(1, title);
			cstmt.execute();

		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to remove manga, error with SQL");
		}
	}

	public void updateManga(Manga manga) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL UPDATE_MANGA(?,?,?,?,?,?)}");

			stmt.setString(1, manga.getTitle());
			stmt.setString(2, manga.getAuthor());
			stmt.setString(3, manga.getGenre());
			stmt.setString(4, manga.getSummary());
			stmt.setString(5, manga.getPubDate().toString());
			stmt.setString(6, manga.getUrl());
			stmt.executeUpdate();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to update manga details, error with SQL");
		}
	}

	public void addManga(Manga manga) {
		try {
			CallableStatement stmt = this.connection.prepareCall("{CALL ADD_MANGA(?,?,?,?,?,?)}");

			stmt.setString(1, manga.getTitle());
			stmt.setString(2, manga.getAuthor());
			stmt.setString(3, manga.getGenre());
			stmt.setString(4, manga.getSummary());
			stmt.setString(5, manga.getPubDate().toString());
			stmt.setString(6, manga.getUrl());
			stmt.execute();
		} catch (SQLException sqle) {
			sqle.printStackTrace();
			log.warn("Failed to add manga, error with SQL");
		}
	}

}
