drop table manga cascade constraints;
drop table USERS cascade constraints;
drop table favourites cascade constraints;
drop table posts cascade constraints;
drop table forums cascade constraints;
drop table reviews cascade constraints;
drop table discussions cascade constraints;

drop sequence manga_id_seq;
drop sequence users_id_seq;
drop sequence fav_id_seq;
drop sequence forum_id_seq;
drop sequence review_id_seq;
drop sequence forum_post_id_seq;
drop sequence post_id_seq;

create sequence manga_id_seq start with 21 increment by 1;
create sequence users_id_seq start with 15 increment by 1;
create sequence fav_id_seq start with 23 increment by 1;
create sequence forum_id_seq start with 2 increment by 1;
create sequence post_id_seq start with 31 increment by 1;
create sequence review_id_seq start with 11 increment by 1;
create sequence forum_post_id_seq start with 21 increment by 1;

create table manga(
MANGA_ID number(4) primary key,
TITLE VARCHAR2(40) unique,
AUTHOR VARCHAR2(40),
GENRE VARCHAR2(20),
SUMMARY VARCHAR2(4000),
PUBLICATION_DATE date,
IMAGE_URL VARCHAR2(255)
);

create table USERS(
USER_ID number(3) primary key,
FIRST_NAME VARCHAR2(30),
LAST_NAME VARCHAR2(30),
EMAIL VARCHAR2(50) unique,
PASSWORD VARCHAR2(30),
USERNAME VARCHAR2(30) unique
);

create table favourites(
fav_id number(4) primary key,
user_id number(4),
manga_id number(4),
constraint fk_fav_manga_id foreign key (manga_id) references manga(manga_id),
constraint fk_fav_user_id foreign key (user_id) references users(user_id)
);

create table forums(
forum_id number(3) primary key,
user_id number(4),
start_date date,
forum_name VARCHAR2(255),
constraint fk_forums_user_id foreign key (user_ID) references users(user_ID)
);

create table posts(
post_id number(5) primary key,
user_id number(4),
date_posted date,
constraint fk_posts_user_id foreign key (user_id) references users(user_id)
);

create table reviews(
review_id number(3) primary key,
manga_id number(4),
post_id number(5),
rating number(2),
review_content VARCHAR2(4000),
constraint fk_reviews_manga_id foreign key (MANGA_ID) references manga(MANGA_ID),
constraint fk_reviews_post_id foreign key (post_id) references posts(post_id)
);

create table discussions(
forum_post_id number(3) primary key,
post_id number(5),
forum_id number(3),
manga_id number(4),
post_content varchar2(4000),
like_content number(3),
constraint fk_dis_post_id foreign key (post_id) references posts(post_ID),
constraint fk_dis_forum_id foreign key (forum_id) references forums(forum_id),
constraint fk_dis_manga_id foreign key (manga_id) references manga(manga_id)
);

insert into manga values (1,	'Sirens Lament',	'instantmiso',	'Romance', 'Content with her ordinary life, Lyra is somewhat of a wallflower. However, her comfortable lifestyle suddenly goes astray when she accidently plunges into the world of sirens. Entangled in a curse, Lyra will learn that her world may be a lot bigger than she had ever imagined.',	to_date('2016-03-05', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (2,	'Always Human',	'walkingnorth',	'Romance', 'This is a story about nanobots, genetic enginerring, and two girls falling in love. No matter how technology changes us, we will always be human.',	to_date('2015-10-10', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (3,	'The God of High School',	'Yongje Park', 'Action',	'It all began as a fighting tournament to seek out the best fighter among all high school students in Korea. Mori Jin, a Taekwondo specialist and a high school student, soon learns that there is someting much greater beneath the stage of the tournament.',	to_date('2011-04-14', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (4,	'Catharsis',	'Ahniki',	'Fantasy', 'Fear is a powerful emotion that can end up overtaking lives. Leon thought that he knew that. But when he is thrown into the demonic realm that Catharsis governs over, he will learn that there is more to "true" fear that he could ever have imagine... But will he learn how to face it? <Challenge League Contest 3rd Place Winner>',	to_date('2015-03-04', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (5,	'Hooky', 	'Miriam Bonastre Tur',	'Fantasy', 'Dani and Dorian have missed the bus to the School of Magic. Now they must find someone who will teach them to be good witches and wizards... Or maybe not.',	to_date('2015-04-10', 'YYYY-MM-DD'), 'resources/images/Hooky.jpg');
insert into manga values (6,	'The Gamer',	'Sangyoung Seong',	'Fantasy', 'What if your life is just like playing a game? What if you can upgrade your status and gain more levels? A fantasy world is coming right at you!',	to_date('2013-09-06', 'YYYY-MM-DD'), 'resources/images/gamer.png');
insert into manga values (7, 'Super Secret',	'eon',	'Romance', 'The boy next door, friends for life, is actually a werewolf!',	to_date('2016-05-04', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (8,	'Dice', 'Hyunseok Yun',	'Fantasy',	'What if you could restart your life with a different setting just like in a game? Dongtae always gets bullied by his classmates, but Taebin sits right next to him, is handsome and popular in school; and now Taebin suggests Dongtae joins him in playing a little game...', to_date('2013-06-07', 'YYYY-MM-DD'), 'resources/images/dice.jpg');
insert into manga values (9,	'Noblesse',	'Jeho Sun',	'Fantasy', 'Rai wakes up from a 820-years long sleep and starts his new life as a student in a high school founded by his loyal servant, Frankenstein. But his peaceful days with other human students are soon interrupted by mysterious attackers knowns are the "Unions".',	to_date('2010-02-28', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (10,	'Tower of God',	'SIU',	'Fantasy', 'What do you desire? Money and wealth? Honor and pride? Authority and power? Revenge? Or something that transcends them all? Whatever you desire, its here.',	to_date('2010-09-04', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (11,	'Hive',	'Kyusam Kim',	'Thriller', 'This is not your typical invasion story. Gigantic oxygen doped bees are attempting to dominate the human race by reversing the food chain. Lee is a middle-manager at a large company who only has one thing on his mind: save his family from the bug-apocalypse.',	to_date('2014-04-29', 'YYYY-MM-DD'), 'resources/images/hive.jpg');
insert into manga values (12,	'Oh! Holy',	'Ahyun',	'Comedy',	'A perfect school idol meets a gloomy loner boy!', to_date('2016-10-17', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (13,	'Girls of the Wilds',	'Hun', 'Sports',	'Wilds High School, an all girls educational institute specialising in MMA, has a very special freshman enrolling this year. Jaegu, who fears women from being abondoned by his mother, is about to find out what girls are really all about in this action-packed school drama.',	to_date('2011-11-08', 'YYYY-MM-DD'), 'resources/images/gotw.jpg');
insert into manga values (14,	'Witch Hunt',	'SSO PARK',	'Drama', 'At the first day of his school. Gamin saw something he never should have seen and from that day, his previoulsy witch-less life became double double toiled and troubled.',	to_date('2014-12-11', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (15,	'Flow',	'Honey B',	'Fantasy', 'A fantasy comic about a boy and his destiny that changes when he receives a gift from his guardian God.',	to_date('2013-07-23', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (16,	'Duty After School',	'Ilkwon Ha',	'Drama', 'What would you do if your school extracurricular activity was military service amidst an actual war? A class of students are turned into platoons of soldiers in a war against the unknown, possibly alien, slime-like objects.',	to_date('2012-08-26', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (17,	'Dead Days',	'Dey',	'Thriller', 'Beginning on the 4th day after a zombie virus broke out, this is a story about the will to live, human instincts and our selfish desires. A thrilling tale about the survirors of a zombie apocalypse.',	to_date('2014-09-25', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (18,	'Annarasumanara',	'Ilkwon Ha',	'Drama', 'Yunai wanted to be a magician when she was little. But now in reality, she is a high school student who cannot even afford new stockings. Since the day she met a real magician at a fair, she desparately wants to follow her dream.',	to_date('2012-05-04', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (19,	'God of Bath',	'Ilkwon Ha',	'Comedy',	'NOW! Here is a new webtoon from the author, Ilkwon Ha, who had written the massively popular webtoon "Annarasumanara". Heosae wants to be the best person in spa massage and scrub, has battles with others.', to_date('2011-08-06', 'YYYY-MM-DD'), 'resources/images/no-profile-photo.jpg');
insert into manga values (20,	'Bastard',	'Youngchan Hwang',	'Thriller', 'There is a serial killer in my house!', 	to_date('2015-06-08', 'YYYY-MM-DD'), 'resources/images/Bastard.jpg');

insert into users values (1, 'Karen', 'Luong', 'karen@luong', 'pass', 'chowchow');
insert into users values (2, 'Robert', 'Chung', 'robert@chung', 'fruit', 'radish');
insert into users values (3, 'Lewis', 'Luong', 'lewis@luong', 'fail', 'taro');
insert into users values (4, 'Mahad', 'Abdourahman', 'mahad@fdm', 'qwerty', 'mahad');
insert into users values (5, 'Vishal', 'Mirchandani', 'vishal@fdm', 'password', 'vishal');
insert into users values (6, 'Jerry', 'Wang', 'jerry@wang', 'computers', 'j.wang');
insert into users values (7, 'Kasia', 'Macrae', 'kasia@macrae', 'developers', 'k.macrae');
insert into users values (8, 'Partick', 'Rode', 'patric@rode', 'germany', 'p.rode');
insert into users values (9, 'Ji', 'Yu', 'Ji@Yu', '!QAZSE4', 'yuji');
insert into users values (10, 'Jake', 'Laver', 'j@layton', 'password', 'santa');
insert into users values (11, 'Matt', 'Layton', 'm@layton', 'sharks', 'whyWontSenpaiNoticeMe'); 
insert into users values (12, 'Darryl', 'Jackson', 'd@jackson', 'football', 'anon');
insert into users values (13, 'Liban', 'Mahamed', 'l@mahamed', 'cookies', 'writer');
insert into users values (14, 'James', 'Fisher', 'j@fisher', 'weed', 'jeff');

insert into favourites values (1, 1, 12);
insert into favourites values (2, 1, 13);
insert into favourites values (3, 1, 15);
insert into favourites values (4, 2, 7);
insert into favourites values (5, 2, 13);
insert into favourites values (6, 2, 8);
insert into favourites values (7, 3, 6);
insert into favourites values (8, 3, 3);
insert into favourites values (9, 3, 13);
insert into favourites values (10, 4, 1);
insert into favourites values (11, 5, 2);
insert into favourites values (12, 6, 4);
insert into favourites values (13, 7,6);
insert into favourites values (14, 8, 6);
insert into favourites values (15, 9,9);
insert into favourites values (16,10,10);
insert into favourites values (17, 11,11);
insert into favourites values (18,12,16);
insert into favourites values (19,13,17);
insert into favourites values (20,14,13);
insert into favourites values (21,14,13);
insert into favourites values (22,14,1);

insert into forums values (1,1, to_date('2016-12-02', 'YYYY-MM-DD'), 'Opinion on the ending of Girls of the wilds');

insert into posts values (1, 3,	to_date('2016-11-15','YYYY-MM-DD'));
insert into posts values (2, 1,	to_date('2016-11-21','YYYY-MM-DD'));
insert into posts values (3,1,	to_date('2016-11-25','YYYY-MM-DD'));
insert into posts values (4,3,	to_date('2016-12-25','YYYY-MM-DD'));
insert into posts values (5,1,	to_date('2016-11-30','YYYY-MM-DD'));
insert into posts values (6,2,	to_date('2016-12-30','YYYY-MM-DD'));
insert into posts values (7,1,	to_date('2016-12-01','YYYY-MM-DD'));
insert into posts values (8,2,	to_date('2016-12-02','YYYY-MM-DD'));
insert into posts values (9,1,	to_date('2016-12-02','YYYY-MM-DD'));
insert into posts values (10,1,	to_date('2016-12-02','YYYY-MM-DD'));
insert into posts values (11,2, to_date('2016-12-03','YYYY-MM-DD'));
insert into posts values (12,1,to_date('2016-12-04','YYYY-MM-DD'));
insert into posts values (13,2, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (14,3, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (15,1, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (16,2, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (17,3, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (18,2, to_date('2016-12-05','YYYY-MM-DD'));
insert into posts values (19,1, to_date('2016-12-06','YYYY-MM-DD'));
insert into posts values (20,14, to_date('2016-12-06','YYYY-MM-DD'));
insert into posts values (21,1, to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (22,2,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (23,3,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (24,2,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (25,1,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (26,3,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (27,2,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (28,3,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (29,1,to_date('2016-12-08','YYYY-MM-DD'));
insert into posts values (30,2,to_date('2016-12-08','YYYY-MM-DD'));

insert into reviews values (1, 13, 1, 8, 'Whats with this ending man');
insert into reviews values (2,13,2,7,'OMG, this guy needs to man up!');
insert into reviews values (3,12,3,10,'So funny omdais');
insert into reviews values (4,6,4,10,'wish this was my life');
insert into reviews values (5,6,5,7,'the grind is real');
insert into reviews values (6,13,6,10,'Queen is my waifu');
insert into reviews values (7,15,7,9,'This is one of the saddest stories i have ever read');
insert into reviews values (8,8,8,8,'Playing with rng gods is real');
insert into reviews values (9,20,9,8,'This guy needs to die');
insert into reviews values (10,20,10,10,'This is one realistic story, I feel him');

insert into discussions values (1,10, 1,null,'So guys, I made this website so we can all discuss our favourite mangas and webtoons! Feel free to register and participate!', 2);
insert into discussions values (2,11,1,null, 'Hi there! I just made an account to join in!', 0);
insert into discussions values (3,12,1,null, 'Welcome!', 0);
insert into discussions values (4,13,1,null, 'So what is the purpose of this site?', -1);
insert into discussions values (5,14,1,null, 'heyyyyya', 0);
insert into discussions values (6,15,1,null, 'Welcome guys!', 0);
insert into discussions values (7,16,1,null, 'answer my question!', 0);
insert into discussions values (8,17,1,null, 'well if you looked at the first post, you will see why this website was created!', 0);
insert into discussions values (9,18,1,null, 'oh my bad', 0);
insert into discussions values (10,19,1,null, 'play nicely guys', 0);
insert into discussions values (11,21,null,13, 'What did you think of that ending guys?', 0);
insert into discussions values (12,22,null,13, 'sucks man', -1);
insert into discussions values (13,23,null,13, 'but my waifu...', 0);
insert into discussions values (14,24,null,13, 'nobody cares about your waifu, did you not see how long it took Jaegu to chase after her? She was obviously not worth it', -1);
insert into discussions values (15,25,null,13, 'after 60+ chapters, they didn’t even get together!!!! WHAT HAPPENED TO MY SHIP', -1);
insert into discussions values (16,26,null,13, 'well i did say that she is my waifu, gosh', 0);
insert into discussions values (17,27,null,13, 'get yourself a body pillow', -1);
insert into discussions values (18,28,null,13, 'I already have one', 0);
insert into discussions values (19,29,null,13, 'wow....', 0);
insert into discussions values (20,30,null,13, 'okay...', -1);

commit;