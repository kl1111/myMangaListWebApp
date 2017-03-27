/*Manga stored procedures*/

create or replace procedure ADD_MANGA (
  title_in  IN VARCHAR2,
  author_in  IN VARCHAR2,
  genre_in  IN VARCHAR2,
  summary_in in varchar2,
  publication_date_in  IN varchar2,
  image_url_in in varchar2
)
IS 
BEGIN
  INSERT into manga(
  manga_id,
  title,
  author,
  genre,
  summary,
  publication_date,
  image_url
  ) VALUES (
    manga_id_seq.nextval,
    title_in,
    author_in,
    genre_in,
    summary_in,
    to_date(publication_date_in, 'YYYY-MM-DD'),
    image_url_in
  );
end;
/

create or replace PROCEDURE UPDATE_MANGA
(
  title_in IN VARCHAR2,
  author_in IN VARCHAR2,
  genre_in IN VARCHAR2,
  summary_in in varchar2,
  publication_date_in IN varchar2,
  image_url_in IN varchar2
)
IS
BEGIN
    UPDATE Manga
    SET
      author = author_in,
      genre = genre_in,
      summary = summary_in,
      publication_date = to_date(publication_date_in, 'YYYY-MM-DD'),
      image_url = image_url_in
    WHERE
      title = title_in;
END;
/

create or replace PROCEDURE FIND_MANGA_BY_TITLE
(
  TITLE_IN IN VARCHAR2,
  MANGA_ID_OUT OUT NUMBER,
  TITLE_OUT OUT VARCHAR2,
  AUTHOR_OUT OUT VARCHAR2,
  GENRE_OUT OUT VARCHAR2,
  SUMMARY_OUT OUT VARCHAR2,
  PUBLICATION_DATE_OUT OUT VARCHAR2,
  IMAGE_URL_OUT OUT VARCHAR2
)
IS
BEGIN
    SELECT 
      MANGA_ID, 
      TITLE , 
      AUTHOR, 
      GENRE, 
      SUMMARY, 
      TO_CHAR(PUBLICATION_DATE,'YYYY-MM-DD'),
      IMAGE_URL
    INTO 
      MANGA_ID_OUT,
      TITLE_OUT,
      AUTHOR_OUT,
      GENRE_OUT,
      SUMMARY_OUT,
      PUBLICATION_DATE_OUT,
      IMAGE_URL_OUT
    FROM MANGA 
    WHERE TITLE=TITLE_IN;
END;
/

create or replace PROCEDURE FIND_MANGA_BY_ID
(
  MANGA_ID_IN IN NUMBER,
  MANGA_ID_OUT OUT NUMBER,
  TITLE_OUT OUT VARCHAR2,
  AUTHOR_OUT OUT VARCHAR2,
  GENRE_OUT OUT VARCHAR2,
  SUMMARY_OUT OUT VARCHAR2,
  PUBLICATION_DATE_OUT OUT VARCHAR2,
  IMAGE_URL_OUT OUT VARCHAR2
)
IS
BEGIN
    SELECT 
      MANGA_ID, 
      TITLE , 
      AUTHOR, 
      GENRE, 
      SUMMARY, 
      TO_CHAR(PUBLICATION_DATE,'YYYY-MM-DD'),
      IMAGE_URL
    INTO 
      MANGA_ID_OUT,
      TITLE_OUT,
      AUTHOR_OUT,
      GENRE_OUT,
      SUMMARY_OUT,
      PUBLICATION_DATE_OUT,
      IMAGE_URL_OUT
    FROM MANGA 
    WHERE MANGA_ID=MANGA_ID_IN;
END;
/

create or replace PROCEDURE DELETE_MANGA_BY_TITLE
(
  TITLE_IN IN VARCHAR2
)
IS
BEGIN
    DELETE 
    FROM MANGA 
    WHERE TITLE=TITLE_IN;
END;
/







/*User stored procedures*/

create or replace procedure ADD_USER(
  username_in IN VARCHAR2,
  password_in  IN VARCHAR2,
  firstName_in  IN VARCHAR2,
  lastName_in  IN VARCHAR2,
  email_in  IN VARCHAR2

)
IS 
BEGIN

  INSERT into Users(
  user_id,
  username,
  password,
  first_Name,
  last_Name,
  email

  ) VALUES (
    users_id_seq.nextval,
    username_in,
    password_in,
    firstName_in,
    lastName_in,
    email_in
  );
end;
/
 
  create or replace PROCEDURE UPDATE_USERS
(
  username_in IN VARCHAR2,
  password_in IN VARCHAR2,
  firstname_in IN VARCHAR2,
  lastname_in IN VARCHAR2,
  email_in IN VARCHAR2
)
IS
BEGIN
    UPDATE USERS
    
    SET
       password = password_in,
       first_name = firstname_in,
       last_name = lastname_in,
       email = email_in

    WHERE
      username = username_in;
END;
/


create or replace PROCEDURE FIND_USER_BY_EMAIL
(
  EMAIL_IN IN VARCHAR2,
  USER_ID_OUT OUT NUMBER,
  USERNAME_OUT OUT VARCHAR2,
  PASSWORD_OUT OUT VARCHAR2,
  FIRST_NAME_OUT OUT VARCHAR2,
  LAST_NAME_OUT OUT VARCHAR2,
  EMAIL_OUT OUT VARCHAR2
)
IS
BEGIN
    SELECT 
      USER_ID, 
      USERNAME, 
      PASSWORD, 
      FIRST_NAME, 
      LAST_NAME, 
      EMAIL
    INTO 
      USER_ID_OUT,
      USERNAME_OUT,
      PASSWORD_OUT,
      FIRST_NAME_OUT,
      LAST_NAME_OUT,
      EMAIL_OUT
    FROM USERS 
    WHERE EMAIL=EMAIL_IN;
END;
/

create or replace PROCEDURE FIND_USER_BY_USERNAME
(
  USERNAME_IN IN VARCHAR2,
  USER_ID_OUT OUT NUMBER,
  USERNAME_OUT OUT VARCHAR2,
  PASSWORD_OUT OUT VARCHAR2,
  FIRST_NAME_OUT OUT VARCHAR2,
  LAST_NAME_OUT OUT VARCHAR2,
  EMAIL_OUT OUT VARCHAR2
)
IS
BEGIN
    SELECT 
      USER_ID, 
      USERNAME, 
      PASSWORD, 
      FIRST_NAME, 
      LAST_NAME, 
      EMAIL
    INTO 
      USER_ID_OUT,
      USERNAME_OUT,
      PASSWORD_OUT,
      FIRST_NAME_OUT,
      LAST_NAME_OUT,
      EMAIL_OUT
    FROM USERS 
    WHERE USERNAME=USERNAME_IN;
END;
/


create or replace PROCEDURE DELETE_USER_BY_USERNAME
(
  USERNAME_IN IN VARCHAR2
)
IS
BEGIN
    DELETE 
    FROM USERS 
    WHERE USERNAME=USERNAME_IN;
END;
/


/*Favourites stored procedures*/

create or replace procedure ADD_favourites (
  userid_in  IN number,
  mangaid_in  IN number
)
IS 
BEGIN
  INSERT into favourites(
    fav_id,
    user_id,
    manga_id
  ) VALUES (
    fav_id_seq.nextval,
    userid_in,
    mangaid_in
  );
end;
/
 
  create or replace PROCEDURE UPDATE_favourites
(
  favid_in IN number,
  userid_in IN number,
  mangaid_in IN number
)
IS
BEGIN
    UPDATE favourites
    
    SET
       manga_id = mangaid_in,
       user_id = userid_in

    WHERE
     fav_id = favid_in;
END;
/


create or replace PROCEDURE DELETE_FAVS
(
  FAVID_IN IN VARCHAR2
)
IS
BEGIN
    DELETE 
    FROM FAVOURITES
    WHERE FAV_ID=FAVID_IN;
END;
/



/*Forums stored procedures*/

create or replace procedure ADD_forums (
  userid_in  IN number,
  startdate_in in varchar2,
  forumname_in in varchar2
)

IS 
BEGIN

  INSERT into forums(
forum_id,
user_id,
start_date,
forum_name
 
  ) VALUES (
  forum_id_seq.nextval,
userid_in,
to_date(startdate_in, 'YYYY-MM-DD'),
forumname_in
  );
end;
/

create or replace PROCEDURE UPDATE_forums
(
    forumid_in in number,
    forumname_in in varchar2
)
IS
BEGIN
    UPDATE forums
    
    SET
       forum_name = forumname_in
    WHERE
     forum_id = forumid_in;
END;
/


create or replace PROCEDURE DELETE_FORUMS
(
  FORUMID_IN IN VARCHAR2
)
IS
BEGIN
    DELETE 
    FROM FORUMS
    WHERE FORUM_ID=FORUMID_IN;
END;
/


/*Posts stored procedures*/

create or replace procedure ADD_posts (
  userid_in  IN number,
  postdate_in  IN varchar2,
  postid_out OUT number
)
IS 
postid number;
BEGIN
  
  INSERT into posts(
  post_id,
  user_id,
 date_posted
  )
  VALUES (
  post_id_seq.nextval,
  userid_in,
  to_date(postdate_in, 'YYYY-MM-DD')
  )
  RETURNING post_id into postid
  ;
  postid_out:=postid;
end;
/



create or replace PROCEDURE FIND_POST
(
  POSTID_IN IN NUMBER,
  POSTID_OUT OUT NUMBER,
  USER_ID_OUT OUT NUMBER,
  POST_DATE_OUT OUT VARCHAR2  
)
IS
BEGIN
    SELECT 
      POST_ID,
      USER_ID, 
      TO_CHAR(DATE_POSTED, 'YYYY-MM-DD')    
    INTO 
      POSTID_OUT,
      USER_ID_OUT,
      POST_DATE_OUT    
    FROM POSTS 
    WHERE POST_ID=POSTID_IN;
END;
/




create or replace PROCEDURE DELETE_POSTS
(
  POSTID_IN IN NUMBER
)
IS
BEGIN
    DELETE 
    FROM POSTS
    WHERE POST_ID=POSTID_IN;
END;
/


/*Reviews stored procedures*/

create or replace procedure ADD_reviews (
  mangaid_in  IN number,
  postid_in in number,
  rating_in in number,
  review_content_in in varchar2  
)

IS 
BEGIN
  
  INSERT into reviews(
 review_id,
 manga_id,
 post_id,
 rating,
 review_content
 
  ) VALUES (
  review_id_seq.nextval,
  mangaid_in,
  postid_in,
  rating_in,
  review_content_in
  );
end;
/
 
  create or replace PROCEDURE UPDATE_reviews
(
  reviewid_in in number,
  mangaid_in  IN number,
  rating_in in number,
  review_content_in in varchar2  
)
IS
BEGIN
    UPDATE reviews
    
    SET
       manga_id = mangaid_in,
       rating = rating_in,
       review_content = review_content_in

    WHERE
     review_id = reviewid_in;
END;
/

create or replace PROCEDURE DELETE_REVIEWS
(
  REVIEWID_IN IN NUMBER
)
IS
BEGIN
    DELETE 
    FROM REVIEWS
    WHERE REVIEW_ID=REVIEWID_IN;
END;
/

create or replace PROCEDURE FIND_REVIEW_BY_ID
(
  REVIEW_ID_IN IN NUMBER,
  REVIEW_ID_OUT OUT NUMBER,
  MANGA_ID_OUT OUT NUMBER,
  POST_ID_OUT OUT NUMBER,
  RATING_OUT OUT NUMBER,
  REVIEW_CONTENT_OUT OUT VARCHAR2
)
IS
BEGIN
    SELECT 
      REVIEW_ID, 
      MANGA_ID, 
      POST_ID, 
      RATING, 
      REVIEW_CONTENT
    INTO 
      REVIEW_ID_OUT,
      MANGA_ID_OUT,
      POST_ID_OUT,
      RATING_OUT,
      REVIEW_CONTENT_OUT
    FROM REVIEWS 
    WHERE REVIEW_ID=REVIEW_ID_IN;
END;
/

/*Discussions stored procedures*/

create or replace procedure ADD_discussions (
  postid_in  IN number,
  forumid_in in number,
  mangaid_in in number,
  post_content_in in varchar2
)

IS 
BEGIN
  
  INSERT into discussions(
forum_post_id,
post_id,
forum_id,
manga_id,
post_content,
like_content
 
  ) VALUES (
  forum_post_id_seq.nextval,
postid_in,
forumid_in,
mangaid_in,
post_content_in,
0
  );
end;
/

 create or replace PROCEDURE UPDATE_discussions
(
 forumpostid_in in number,
 postid_in in number,
 forumid_in in number,
 mangaid_in in number,
 post_content_in in varchar2,
 like_content_in in number
)
IS
BEGIN
    UPDATE discussions
    
    SET
       post_id = postid_in,
       forum_id = forumid_in,
       manga_id = mangaid_in,
       post_content = post_content_in,
       like_content = like_content_in

    WHERE
     forum_post_id = forumpostid_in;
END;
/

create or replace PROCEDURE DELETE_DISCUSSIONS
(
  FORUMPOSTID_IN IN NUMBER
)
IS
BEGIN
    DELETE 
    FROM DISCUSSIONS
    WHERE FORUM_POST_ID=FORUMPOSTID_IN;
END;
/


commit;