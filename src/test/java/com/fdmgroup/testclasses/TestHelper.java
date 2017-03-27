package com.fdmgroup.testclasses;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestHelper {
	
	private Connection connection;
	
	public TestHelper(Connection connection) {
		this.connection = connection;
	}

	public static java.util.Date getDate(LocalDateTime localDateTime){
		ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
		return new Date(zonedDateTime.toInstant().toEpochMilli());
	}
	
	public void dropUsersTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE USERS CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void dropMangaTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE MANGA CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public void dropFavsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE FAVOURITES CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void dropPostsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE POSTS CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropReviewsTable() {
		try {
			Statement statement = this.connection.createStatement();	
			statement.execute("DROP TABLE REVIEWS CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropForumsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE FORUMS CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void dropDiscussionsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP TABLE DISCUSSIONS CASCADE CONSTRAINTS");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createUsersTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(
					"create table USERS(user_id number(2) primary key, username VARCHAR2(30) unique, password VARCHAR2(20), first_Name VARCHAR2(30), last_Name VARCHAR2(30), email VARCHAR2(30) unique)");
			statement.execute("INSERT INTO USERS VALUES (21, 'potatoes', 'vegetables', 'anon', 'anon', 'anon@fdm')");
			statement.execute(
					"INSERT INTO USERS VALUES (22, 'tomatoes', 'vegetables', 'Lewis-Chan', 'Luong', 'lewis@luong')");
			statement.execute(
					"INSERT INTO USERS VALUES (23, 'daikon', 'vegetables', 'Robert-Chan', 'Chung', 'robert@chung')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createMangaTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(
					"CREATE TABLE MANGA(MANGA_ID NUMBER(4) PRIMARY KEY, TITLE VARCHAR2(40) UNIQUE, AUTHOR VARCHAR2(40), GENRE VARCHAR2(20),SUMMARY VARCHAR2(4000),PUBLICATION_DATE date, IMAGE_URL VARCHAR2(255))");
			statement.execute(
					"INSERT INTO MANGA VALUES (21, 'Untouchable', 'massstar', 'Romance', 'Sia Lee is a modern day vampire who absorbs energy from humans by touching them instead of drinking their blood. She has been desperate to touch Jiho since the day he moved in next door. However, he is a germaphobe. Will Sia best his mysophobia and touch him?', to_date('2014-07-01', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg')");
			statement.execute(
					"INSERT INTO MANGA VALUES (22, 'Cheese in the Trap', 'soonkki', 'Drama', 'Seol Hong is a hard-working student who has returned to college after a long break. Jung Yu is a senior at the college known as Mr. Perfect. Seol feels like her life took a turn for the worse since she got involved with Jung. Is Jung intentionally turning her life?', to_date('2014-07-01', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg')");
			statement.execute(
					"INSERT INTO MANGA VALUES (23, 'Orange Marmalade', 'Seokwoo', 'Romance', 'People are no longer afraid of vampires but that does not stop humans from discriminating against them. Can Mari Baek hide her identity when she falls in love with a popular boy who hates vampires?', to_date('2014-07-01', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createFavsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(
					"CREATE TABLE FAVOURITES(FAV_ID NUMBER(4) PRIMARY KEY, USER_ID NUMBER(4), MANGA_ID NUMBER(4), constraint fk_fav_manga_id foreign key (manga_id) references manga(manga_id), constraint fk_fav_user_id foreign key (user_id) references users(user_id))");
			statement.execute("INSERT INTO FAVOURITES VALUES (23, 23, 21)");
			statement.execute("INSERT INTO FAVOURITES VALUES (24, 23, 22)");
			statement.execute("INSERT INTO FAVOURITES VALUES (25, 22, 21)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createPostsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.executeQuery(
					"CREATE TABLE POSTS(POST_ID NUMBER(5) PRIMARY KEY, USER_ID NUMBER(4), DATE_POSTED DATE, CONSTRAINT FK_POSTS_USER_ID FOREIGN KEY (USER_ID) REFERENCES USERS(USER_ID))");
			statement.execute("INSERT INTO POSTS VALUES (31, 21, to_date('2016-12-09','YYYY-MM-DD'))");
			statement.execute("INSERT INTO POSTS VALUES (32, 22, to_date('2014-04-25','YYYY-MM-DD'))");
			statement.execute("INSERT INTO POSTS VALUES (33, 21, to_date('2016-12-15','YYYY-MM-DD'))");
			statement.execute("INSERT INTO POSTS VALUES (34, 22, to_date('2016-10-14','YYYY-MM-DD'))");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createReviewsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.executeQuery(
					"CREATE TABLE REVIEWS(REVIEW_ID NUMBER(3) PRIMARY KEY, MANGA_ID NUMBER(4), POST_ID NUMBER(5), RATING NUMBER(2), REVIEW_CONTENT VARCHAR2(4000), CONSTRAINT FK_REVIEWS_MANGA_ID FOREIGN KEY(MANGA_ID) REFERENCES MANGA(MANGA_ID), CONSTRAINT FK_REVIEWS_POST_ID FOREIGN KEY(POST_ID) REFERENCES POSTS(POST_ID))");
			statement.execute("INSERT INTO REVIEWS VALUES (11, 21, 31, 10, 'HELLO')");
			statement.execute("INSERT INTO REVIEWS VALUES (12, 21, 32, 8, 'GOODBYE')");
			statement.execute("INSERT INTO REVIEWS VALUES (13, 22, 33, 8, 'WTH')");
			statement.execute("INSERT INTO REVIEWS VALUES (14, 23, 34, 10, 'COME AGAIN')");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createForumsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.execute(
					"CREATE TABLE FORUMS(FORUM_ID NUMBER(3) PRIMARY KEY, USER_ID NUMBER(4), START_DATE DATE, FORUM_NAME VARCHAR2(255), CONSTRAINT FK_FORUMS_USER_ID FOREIGN KEY(USER_ID) REFERENCES USERS(USER_ID))");
			statement.execute("INSERT INTO FORUMS VALUES (2, 22,	to_date('2016-12-05', 'YYYY-MM-DD'), 'you suck')");
			statement.execute("INSERT INTO FORUMS VALUES (3, 21,	to_date('2016-12-11', 'YYYY-MM-DD'), 'Goodbye')");
			statement.execute("INSERT INTO FORUMS VALUES (4, 21,	to_date('2016-12-11', 'YYYY-MM-DD'), 'Hi')");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void createDiscussionsTable() {
		try {
			Statement statement = this.connection.createStatement();
			statement.executeQuery(
					"CREATE TABLE DISCUSSIONS(FORUM_POST_ID NUMBER(3) PRIMARY KEY, POST_ID NUMBER(5), FORUM_ID NUMBER(3), MANGA_ID NUMBER(4), POST_CONTENT VARCHAR2(4000), LIKE_CONTENT NUMBER(3), CONSTRAINT FK_DIS_POST_ID FOREIGN KEY(POST_ID) REFERENCES POSTS(POST_ID), CONSTRAINT FK_DIS_FORUM_ID FOREIGN KEY (FORUM_ID) REFERENCES FORUMS(FORUM_ID), CONSTRAINT FK_DIS_MANGA_ID FOREIGN KEY (MANGA_ID) REFERENCES MANGA(MANGA_ID))");
			statement.execute("INSERT INTO DISCUSSIONS VALUES (2, 31, 2, NULL, 'HELLO', 2)");
			statement.execute("INSERT INTO DISCUSSIONS VALUES (3, 32, 2, NULL, 'GOODBYE', -3)");
			statement.execute("INSERT INTO DISCUSSIONS VALUES (4, 33, NULL, 23, 'WTH', 1)");
			statement.execute("INSERT INTO DISCUSSIONS VALUES (5, 34, NULL, 23, 'COME AGAIN', 0)");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropMangaSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE MANGA_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropUsersSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE USERS_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropFavsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE FAV_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropForumsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE FORUM_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropPostsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE POST_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropReviewsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE REVIEW_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void dropDiscussionsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("DROP SEQUENCE FORUM_POST_ID_SEQ");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createMangaSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE MANGA_ID_SEQ START WITH 24 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createUsersSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE USERS_ID_SEQ START WITH 24 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createFavsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE FAV_ID_SEQ START WITH 26 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createPostsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE POST_ID_SEQ START WITH 35 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createReviewsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE REVIEW_ID_SEQ START WITH 15 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createDiscussionsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE FORUM_POST_ID_SEQ START WITH 6 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public void createForumsSequence(){
		try {
			Statement statement = this.connection.createStatement();
			statement.execute("CREATE SEQUENCE FORUM_ID_SEQ START WITH 5 INCREMENT BY 1");
		} catch (SQLException e) {
			e.printStackTrace();
		} 
	}
}

