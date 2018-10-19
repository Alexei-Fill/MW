CREATE SCHEMA `movie_warehouse` DEFAULT CHARACTER SET utf8 ;
use `movie_warehouse`;
CREATE TABLE `movie` (
	`MOVIE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`MOVIE_IMDBID` bigint(20) NOT NULL UNIQUE,
	`MOVIE_IMAGE_URL` TEXT,
	`MOVIE_BUDGET` bigint(20),
	`MOVIE_AGE_LIMIT` int(3),
	`MOVIE_RELEASE_DATE` DATE NOT NULL,
	`MOVIE_UPLOAD_DATE` DATE NOT NULL,
	`MOVIE_DURATION` TIME NOT NULL,
	`MOVIE_DUES` bigint(20),
	PRIMARY KEY (`MOVIE_ID`)
);

CREATE TABLE `language` (
	`LANGUAGE_ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`LANGUAGE_NAME` varchar(40) NOT NULL UNIQUE,
	`LANGUAGE_LOCALE` varchar(20) NOT NULL,
	`LANGUAGE_DATE_FORMAT` varchar(45) NOT NULL,
	PRIMARY KEY (`LANGUAGE_ID`)
);

CREATE TABLE `characteristics_of_movie` (
	`LANGUAGE_ID` bigint(20) NOT NULL,
	`MOVIE_ID` bigint(20) NOT NULL,
	`MOVIE_NAME` TEXT NOT NULL,
	`MOVIE_DESCRIPTION` TEXT NOT NULL,
	`MOVIE_COUNTRY` TEXT NOT NULL,
	PRIMARY KEY (`LANGUAGE_ID`,`MOVIE_ID`)
);

CREATE TABLE `genre` (
	`GENRE_ID` bigint(20) NOT NULL,
	`LANGUAGE_ID` bigint(20) NOT NULL,
	`GENRE_NAME` varchar(45) NOT NULL,
	PRIMARY KEY (`GENRE_ID`,`LANGUAGE_ID`)
);

CREATE TABLE `genre_of_movie` (
	`MOVIE_ID` bigint(20) NOT NULL,
	`GENRE_ID` bigint(20) NOT NULL,
	PRIMARY KEY (`MOVIE_ID`,`GENRE_ID`)
);

CREATE TABLE `human` (
	`HUMAN_ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`HUMAN_BIRTH_DATE` DATE NOT NULL,
	`HUMAN_IMAGE_URL` TEXT,
	PRIMARY KEY (`HUMAN_ID`)
);

CREATE TABLE `characteristics_of_human` (
	`HUMAN_ID` bigint(20) NOT NULL,
	`LANGUAGE_ID` bigint(20) NOT NULL,
	`HUMAN_NAME` varchar(24) NOT NULL,
	`HUMAN_SURNAME` varchar(45),
	`HUMAN_PATRONYMIC` varchar(32),
	`HUMAN_BIOGRAPHY` TEXT NOT NULL,
	PRIMARY KEY (`HUMAN_ID`,`LANGUAGE_ID`)
);

CREATE TABLE `role_human_in_movie` (
	`HUMAN_ID` bigint(20) NOT NULL,
	`MOVIE_ID` bigint(20) NOT NULL,
	`HUMAN_ROLE` int(11) NOT NULL,
	PRIMARY KEY (`HUMAN_ID`,`MOVIE_ID`,`HUMAN_ROLE`)
);

CREATE TABLE `user` (
	`USER_ID` bigint(20) NOT NULL AUTO_INCREMENT,
	`USER_LOGIN` varchar(40) NOT NULL UNIQUE,
	`USER_PASSWORD` varchar(140) NOT NULL,
	`USER_MAIL` varchar(40) NOT NULL,
	`USER_BIRTH_DATE` DATE,
	`USER_ROLE` int NOT NULL,
	`USER_REGISTRATION_DATE` DATE NOT NULL,
	`USER_IMAGE_URL` TEXT,
	PRIMARY KEY (`USER_ID`)
);

CREATE TABLE `voted_liked_the_movie` (
	`USER_ID` bigint(20) NOT NULL,
	`MOVIE_ID` bigint(20) NOT NULL,
	`ITS_LIKED` int(2) NOT NULL DEFAULT '0',
	`ITS_VOTED` int(2) NOT NULL DEFAULT '0',
	PRIMARY KEY (`USER_ID`,`MOVIE_ID`)
);

ALTER TABLE `characteristics_of_movie` ADD CONSTRAINT `characteristics_of_movie_fk0` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `language`(`LANGUAGE_ID`);

ALTER TABLE `characteristics_of_movie` ADD CONSTRAINT `characteristics_of_movie_fk1` FOREIGN KEY (`MOVIE_ID`) REFERENCES `movie`(`MOVIE_ID`);

ALTER TABLE `genre` ADD CONSTRAINT `genre_fk0` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `language`(`LANGUAGE_ID`);

ALTER TABLE `genre_of_movie` ADD CONSTRAINT `genre_of_movie_fk0` FOREIGN KEY (`MOVIE_ID`) REFERENCES `movie`(`MOVIE_ID`);

ALTER TABLE `genre_of_movie` ADD CONSTRAINT `genre_of_movie_fk1` FOREIGN KEY (`GENRE_ID`) REFERENCES `genre`(`GENRE_ID`);

ALTER TABLE `characteristics_of_human` ADD CONSTRAINT `characteristics_of_human_fk0` FOREIGN KEY (`HUMAN_ID`) REFERENCES `human`(`HUMAN_ID`);

ALTER TABLE `characteristics_of_human` ADD CONSTRAINT `characteristics_of_human_fk1` FOREIGN KEY (`LANGUAGE_ID`) REFERENCES `language`(`LANGUAGE_ID`);

ALTER TABLE `role_human_in_movie` ADD CONSTRAINT `role_human_in_movie_fk0` FOREIGN KEY (`HUMAN_ID`) REFERENCES `human`(`HUMAN_ID`);

ALTER TABLE `role_human_in_movie` ADD CONSTRAINT `role_human_in_movie_fk1` FOREIGN KEY (`MOVIE_ID`) REFERENCES `movie`(`MOVIE_ID`);

ALTER TABLE `voted_liked_the_movie` ADD CONSTRAINT `voted_liked_the_movie_fk0` FOREIGN KEY (`USER_ID`) REFERENCES `user`(`USER_ID`);

ALTER TABLE `voted_liked_the_movie` ADD CONSTRAINT `voted_liked_the_movie_fk1` FOREIGN KEY (`MOVIE_ID`) REFERENCES `movie`(`MOVIE_ID`);


INSERT INTO `movie_warehouse`.`language` (`LANGUAGE_NAME`, `LANGUAGE_LOCALE`, `LANGUAGE_DATE_FORMAT`) VALUES ('Русский', 'ru', 'd.MM.yyyy');
INSERT INTO `movie_warehouse`.`language`  (`LANGUAGE_NAME`, `LANGUAGE_LOCALE`, `LANGUAGE_DATE_FORMAT`) VALUES ('English', 'en', 'MM.d.yyyy');

INSERT INTO `movie_warehouse`.`movie` (`MOVIE_IMDBID`, `MOVIE_IMAGE_URL`, `MOVIE_BUDGET`, `MOVIE_AGE_LIMIT`, `MOVIE_RELEASE_DATE`, `MOVIE_UPLOAD_DATE`, `MOVIE_DURATION`, `MOVIE_DUES`) VALUES ('1014759', '/uploads_img/movie_img/img-3981061599366788372.jpg', '200000000', '12', '2010-02-25', '2018-09-07', '01:48:00', '1025467110');

INSERT INTO `movie_warehouse`.`movie`(`MOVIE_IMDBID`, `MOVIE_IMAGE_URL`, `MOVIE_BUDGET`, `MOVIE_AGE_LIMIT`, `MOVIE_RELEASE_DATE`, `MOVIE_UPLOAD_DATE`, `MOVIE_DURATION`, `MOVIE_DUES`)   VALUES ('3416532', '/uploads_img/movie_img/img-6887975622524322481.jpg', '43000000', '16', '2016-09-09', '2018-09-06', '01:49:00', '47309313');

INSERT INTO `movie_warehouse`.`movie` (`MOVIE_IMDBID`, `MOVIE_IMAGE_URL`, `MOVIE_BUDGET`, `MOVIE_AGE_LIMIT`, `MOVIE_RELEASE_DATE`, `MOVIE_UPLOAD_DATE`, `MOVIE_DURATION`, `MOVIE_DUES`) VALUES ('2404435', '/uploads_img/movie_img/img-4791011113462404757.jpg', '90000000', '16', '2016-09-08', '2018-09-08', '02:12:00', '162360636');



INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('1', '1', 'Алиса в Стране чудес', '«Али́са в Стране́ чуде́с» (англ. Alice in Wonderland) — фэнтезийный приключенческий фильм 2010 года, снятый Тимом Бёртоном по сценарию Линды Вулвертон с участием Мии Васиковской, Джонни Деппа, Хелены Бонэм Картер, Энн Хэтэуэй, Криспина Гловера, Майкла Шина и Стивена Фрая.', 'США');
INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('2', '1', 'Alice in Wonderland', 'Alice in Wonderland is a 2010 American fantasy adventure film directed by Tim Burton from a screenplay written by Linda Woolverton. The film stars Johnny Depp, Anne Hathaway, Helena Bonham Carter, Crispin Glover, Matt Lucas and Mia Wasikowska, and features the voices of Alan Rickman, Stephen Fry, Michael Sheen, and Timothy Spall. Loosely inspired by Lewis Carroll\'s fantasy novels, Alice\'s Adventures in Wonderland and Through the Looking-Glass, the film tells the story of a nineteen-year-old Alice Kingsleigh, who is told that she can restore the White Queen to her throne, with the help of the Mad Hatter. She is the only one who can slay the Jabberwock, a dragon-like creature that is controlled by the Red Queen and terrorizes Underland\'s inhabitants.', 'USA');

INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('1', '2', 'Голос монстра', 'Главному герою — мальчику Коннору пришлось повзрослеть раньше времени, так как на его хрупкие детские плечи рухнула гора проблем. У отца новая семья, а мать Коннора умирает от рака и не может оказать должное внимание и заботу сыну, поэтому Коннору самостоятельно приходится готовить еду, стирать, убирать и делать многое другое. В школе одноклассники постоянно издеваются над ним, друзей у парня нет, каждую ночь его мучают ночные кошмары. Ко всему прочему ему приходится переехать жить к бабушке, которую он считает сухой и чёрствой личностью.', 'Испания, США');
INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('2', '2', 'A Monster Calls', 'Twelve-year-old Connor O\'Malley must face his mother\'s terminal cancer, his strict grandmother, his estranged father, and the school bully, Harry. One night at 12:07 a.m., Connor is visited by the tree-like Monster, who tells Connor it has come to tell him three true stories, after which Connor must tell the Monster his own story: the truth behind his nightmare, which Connor refuses to do.', 'Spain, USA');

INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('1', '3', 'Великолепная семерка', 'В 1879 году барон-разбойник и золотодобывающий магнат Бартоломью Боeг берет под контроль пограничный городом Роуз-Крик и подвергает его жителей принудительному труду в своих шахтах. После осуждения церковным собранием Боуг сжигает церковь и убивает группу восставших жителей во главе с Мэттью Калленом. Вдова Мэттью, Эмма Каллен, и ее друг Тедди Кью ездят в поисках охотников за головами, чтобы помочь освободить город. Они вербуют Сэма Чизема, афро-американского маршала, который соглашается им помочь, только услышав о Боуге.', 'США');
INSERT INTO `movie_warehouse`.`CHARACTERISTICS_OF_MOVIE` (`LANGUAGE_ID`, `MOVIE_ID`, `MOVIE_NAME`, `MOVIE_DESCRIPTION`, `MOVIE_COUNTRY`) VALUES ('2', '3', 'The Magnificent Seven', 'In 1879, robber baron and gold-mining tycoon Bartholomew Bogue assumes control of the American frontier town of Rose Creek, and subjects its residents to forced labor in his mines. After a town church assembly denounces him, Bogue has the church torched and kills a group of rebellious locals led by Matthew Cullen. Matthew\'s widow, Emma Cullen, and her friend Teddy Q. ride in search of bounty hunters to help liberate the town. They recruit Sam Chisolm, an African-American U.S. Marshal, who only expresses interest after hearing of Bogue\'s involvement.', 'USA');



INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('1', '1', 'Фантастика');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('1', '2', 'Fantasy');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('2', '1', 'Драма');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('2', '2', 'Dram');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('3', '1', 'Боевик');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('3', '2', 'Shooter');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('4', '1', 'Вестерн');
INSERT INTO `movie_warehouse`.`genre` (`GENRE_ID`, `LANGUAGE_ID`, `GENRE_NAME`) VALUES ('4', '2', 'Vestern');


INSERT INTO `movie_warehouse`.`genre_of_movie` (`MOVIE_ID`, `GENRE_ID`) VALUES ('1', '1');
INSERT INTO `movie_warehouse`.`genre_of_movie` (`MOVIE_ID`, `GENRE_ID`) VALUES ('2', '1');
INSERT INTO `movie_warehouse`.`genre_of_movie` (`MOVIE_ID`, `GENRE_ID`) VALUES ('2', '2');
INSERT INTO `movie_warehouse`.`genre_of_movie` (`MOVIE_ID`, `GENRE_ID`) VALUES ('3', '4');
INSERT INTO `movie_warehouse`.`genre_of_movie` (`MOVIE_ID`, `GENRE_ID`) VALUES ('3', '3');


INSERT INTO `movie_warehouse`.`human` (`HUMAN_BIRTH_DATE`, `HUMAN_IMAGE_URL`) VALUES ('1979-06-21', '/uploads_img/human_img/img-926214477490130190.jpg');
INSERT INTO `movie_warehouse`.`human` (`HUMAN_BIRTH_DATE`, `HUMAN_IMAGE_URL`) VALUES ('1910-03-23', '/uploads_img/human_img/img-632291066627891557.jpg');
INSERT INTO `movie_warehouse`.`human` (`HUMAN_BIRTH_DATE`, `HUMAN_IMAGE_URL`) VALUES ('1966-01-19', '/uploads_img/human_img/img-3549255500705164229.jpg');




INSERT INTO `movie_warehouse`.`characteristics_of_human` (`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`) VALUES ('1', '1', 'Крис', ' Пратт', 'Майкл', 'Полное имя — Кристофер Майкл Пратт (Christopher Michael Pratt).');
INSERT INTO `movie_warehouse`.`characteristics_of_human`(`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`)VALUES ('1', '2', 'Chris ', ' Pratt', ' Michael', 'Full name is Christopher Michael Pratt (Christopher Michael Pratt).');

INSERT INTO `movie_warehouse`.`characteristics_of_human` (`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`) VALUES ('2', '2', 'Akira ', ' Kurosawa', '  ', 'He graduated from the school of western drawing Doshusha.');
INSERT INTO `movie_warehouse`.`characteristics_of_human` (`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`) VALUES ('2', '1', 'Акира ', ' Куросава', ' ', 'Окончил школу западного рисования Doshusha.');

INSERT INTO `movie_warehouse`.`characteristics_of_human` (`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`) VALUES ('3', '1', 'Антуан ', ' Фукуа', ' ', 'Антуан Фукуа вновь заинтересовался ремейком «Лица со шрамом»');
INSERT INTO `movie_warehouse`.`characteristics_of_human` (`HUMAN_ID`, `LANGUAGE_ID`, `HUMAN_NAME`, `HUMAN_SURNAME`, `HUMAN_PATRONYMIC`, `HUMAN_BIOGRAPHY`)  VALUES ('3', '2', 'Antoine ', ' Fuqua', '  ', 'Antoine Fukua again became interested in the remake of \"Faces with a scar\"');



INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('1', '3', '3');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('2', '3', '2');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('3', '3', '1');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('1', '2', '3');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('2', '2', '1');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('3', '2', '2');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('1', '1', '1');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('2', '1', '2');
INSERT INTO `movie_warehouse`.`role_human_in_movie` (`HUMAN_ID`, `MOVIE_ID`, `HUMAN_ROLE`) VALUES ('3', '1', '3');


INSERT INTO `movie_warehouse`.`user` (`USER_LOGIN`, `USER_PASSWORD`, `USER_MAIL`, `USER_BIRTH_DATE`, `USER_ROLE`, `USER_REGISTRATION_DATE`, `USER_IMAGE_URL`) VALUES ('admin11', '$2a$12$VCp3kBmImyPwbiifQu9lTeIFCut9V7xAzONZ5gBsXLeIipY8H2ilm', 'aexample@mail.ru', '2001-01-17', '2', '2017-03-03', '/uploads_img/user_img/img-2805283443837797790.jpg' );
INSERT INTO `movie_warehouse`.`user` (`USER_LOGIN`, `USER_PASSWORD`, `USER_MAIL`, `USER_BIRTH_DATE`, `USER_ROLE`, `USER_REGISTRATION_DATE`, `USER_IMAGE_URL`) VALUES ('user12', '$2a$12$QnZpola02oH1PRcGbLqIM.7OHOvwonP4M55jjsKNG0Id4GpiW7ep.', 'example@gmail.com', '1002-02-02', '1', '2010-10-10', ' /uploads_img/user_img/img-2805283443837797790.jpg');

INSERT INTO `movie_warehouse`.`voted_liked_the_movie` (`USER_ID`, `MOVIE_ID`, `ITS_LIKED`, `ITS_VOTED`) VALUES ('1', '1', '1', '5');
INSERT INTO `movie_warehouse`.`voted_liked_the_movie` (`USER_ID`, `MOVIE_ID`, `ITS_LIKED`, `ITS_VOTED`) VALUES ('2', '2', '1', '0');