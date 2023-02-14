DROP DATABASE IF EXISTS MusicStore;

CREATE DATABASE MusicStore;

USE MusicStore;

CREATE TABLE Article(
	COD_ART INT AUTO_INCREMENT PRIMARY KEY,
	STOCK INT NOT NULL,
	PRICE DECIMAL(6, 2) NOT NULL,
		CONSTRAINT CH_PRICE CHECK (0 < PRICE)
);

CREATE TABLE Ticket(
	COD_TIC INT AUTO_INCREMENT PRIMARY KEY,
	DATE_TIME TIMESTAMP NOT NULL
); 

CREATE TABLE Article_Ticket(
	COD_ART_AT INT NOT NULL,
	COD_TIC_AT INT NOT NULL,
	AMOUNT INT NOT NULL,
		primary key (COD_ART_AT, COD_TIC_AT),
		FOREIGN KEY (COD_ART_AT) REFERENCES ARTICLE(COD_ART)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		FOREIGN KEY (COD_TIC_AT) REFERENCES TICKET(COD_TIC)
			ON UPDATE CASCADE
			ON DELETE cascade,
		CONSTRAINT CH_AMOUNT CHECK (0 < AMOUNT)
);

CREATE TABLE Album(
	COD_CD INT PRIMARY KEY,
	TITLE VARCHAR(50) NOT NULL,
	AUTHOR VARCHAR(50) NOT NULL,
	GENRE ENUM("ROCK", "TRAP", "POP", "HIPHOP", "OTHERS") NOT NULL,
	CD_LENGTH INT NOT NULL,
	SONGS INT NOT NULL,
	CD_FORMAT ENUM ("VINYL", "CASSETTE", "DVD", "DIGITAL", "BLURAY", "CD"),
		CONSTRAINT Check_CD UNIQUE (TITLE, AUTHOR, CD_FORMAT),
		FOREIGN KEY (COD_CD) REFERENCES ARTICLE(COD_ART)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT CH_TITLE CHECK (LENGTH(TITLE) >= 2),
		CONSTRAINT CH_AUTHOR CHECK (LENGTH(AUTHOR) >= 2),
		CONSTRAINT CH_LENGTH CHECK (10 < CD_LENGTH < 300),
		CONSTRAINT CH_SONGS CHECK (0 < SONGS < 51)
);

CREATE TABLE Poster(
	COD_POSTER INT PRIMARY KEY,
	BAND VARCHAR(50) NOT NULL,
	HEIGHT INT NOT NULL,
	WIDTH INT NOT NULL,
	MATERIAL ENUM ("PLASTIC", "FABRIC", "METALIC", "OTHERS"),
		FOREIGN KEY (COD_POSTER) REFERENCES ARTICLE(COD_ART)
			ON UPDATE CASCADE
			ON DELETE CASCADE,
		CONSTRAINT CH_BAND CHECK (LENGTH(BAND) >= 2),
		CONSTRAINT CH_HEIGHT CHECK (0 < HEIGHT < 1000),
		CONSTRAINT CH_WIDTH CHECK (0 < WIDTH < 1000)
);

insert into article(STOCK, PRICE)
values ('14', '8.99'),
('10', '12.99'),
('20', '7.99'),
('19', '23.99'),
('12', '10.99'),
('11', '6.99'),
('10', '8.99'),
('22', '17.99'),
('21', '9.99'),
('16', '15.99'),
('17', '14.99'),
('15', '19.99'),
('23', '22.99'),
('29', '9.99'),
('22', '13.99'),
('24', '16.99'),
('15', '49.99'),
('22', '29.99'),
('26', '26.99'),
('13', '15.99')
;

insert into ticket(DATE_TIME)
values('2022-05-27 11:04:56'),
('2022-05-28 17:12:40'),
('2022-05-28 17:15:43'),
('2022-05-29 19:40:32'),
('2022-05-30 10:51:12'),
('2022-05-30 18:23:12'),
('2022-05-31 13:12:22'),
('2022-05-31 19:10:19'),
('2022-06-01 11:42:52'),
('2022-06-01 17:31:12'),
('2022-06-01 18:42:02')
;

insert into Article_Ticket(COD_ART_AT, COD_TIC_AT, AMOUNT)
values ('1', '1', '2'),
('14', '1', '1'),
('4', '2', '2'),
('11', '2', '3'),
('2', '3', '1'),
('8', '3', '1'),
('12', '3', '3'),
('3', '4', '1'),
('12', '4', '2'),
('13', '4', '2'),
('20', '4', '1'),
('1', '5', '2'),
('2', '5', '1'),
('6', '5', '1'),
('10', '5', '1'),
('7', '6', '6'),
('15', '7', '2'),
('16', '7', '1'),
('17', '7', '1'),
('4', '8', '3'),
('5', '8', '1'),
('9', '8', '1'),
('11', '8', '4'),
('14', '8', '2'),
('16', '8', '1'),
('19', '8', '1'),
('2', '9', '2'),
('10', '9', '2'),
('17', '9', '1'),
('20', '9', '3'),
('3', '10', '2'),
('6', '10', '1'),
('8', '10', '2'),
('11', '10', '1'),
('4', '11', '2'),
('9', '11', '1'),
('13', '11', '2'),
('14', '11', '1'),
('16', '11', '3'),
('19', '11', '1')
;

insert into album(COD_CD, TITLE, AUTHOR, GENRE, CD_LENGTH, SONGS, CD_FORMAT)
values ('1', 'Spider-Man: Into the Spider-Verse (Soundtrack)', 'Daniel Pemberton', 'TRAP', '50', '13', 'CD'),
('2', 'Spider-Man: Into the Spider-Verse (Soundtrack)', 'Daniel Pemberton', 'TRAP', '50', '13', 'DIGITAL'),
('3', 'Blood Sugar Sex Magik', 'Red Hot Chili Peppers', 'ROCK', '74', '16', 'CD'),
('4', 'Blood Sugar Sex Magik', 'Red Hot Chili Peppers', 'ROCK', '74', '16', 'VINYL'),
('5', 'Blood Sugar Sex Magik', 'Red Hot Chili Peppers', 'ROCK', '74', '16', 'DIGITAL'),
('6', 'Demon Days', 'Gorillaz', 'OTHERS', '51', '15', 'CD'),
('7', 'Demon Days', 'Gorillaz', 'OTHERS', '51', '15', 'VINYL'),
('8', 'X 100pre', 'Bad Bunny', 'TRAP', '54', '15', 'CD'),
('9', 'Favourite Worst Nightmare', 'Arctic Monkeys', 'ROCK', '38', '12', 'VINYL'),
('10', 'El Círculo', 'Kase O', 'HIPHOP', '70', '17', 'VINYL')
;

insert into poster(COD_POSTER, BAND, HEIGHT, WIDTH,	MATERIAL)
values ('11', 'AC/DC', '91', '61', 'PLASTIC'),
('12', 'Jimi Hendrix', '112', '75', 'FABRIC'),
('13', 'The Beatles', '150', '82', 'PLASTIC'),
('14', 'Billie Eilish', '69', '43', 'PLASTIC'),
('15', 'Siniestro Total', '87', '60', 'METALIC'),
('16', 'Queens of the Stone Age', '111', '72', 'FABRIC'),
('17', 'Rick Astley', '430', '278', 'PLASTIC'),
('18', 'Grateful Dead', '94', '167', 'FABRIC'),
('19', 'Red Hot Chili Peppers', '134', '134', 'FABRIC'),
('20', 'Eminem', '125', '84', 'PLASTIC')
;

#Triggers that avoids sales if there's no stock

DELIMITER //
DROP TRIGGER IF EXISTS ZeroStock //
CREATE TRIGGER ZeroStock
AFTER INSERT ON article_ticket
FOR EACH ROW
BEGIN
	declare Total_Stock int;
	declare Cod_Article int;
	select a.STOCK into Total_Stock from article a where new.COD_ART_AT = a.COD_ART;
	select a.COD_ART into Cod_Article from article a where new.COD_ART_AT = a.COD_ART;
	if Total_Stock < new.AMOUNT then
		delete from article_ticket at2 where new.COD_ART_AT = Cod_Article;
	end if;
END;
//
DELIMITER ;

DELIMITER //
DROP TRIGGER IF EXISTS ZeroStock2 //
CREATE TRIGGER ZeroStock2
BEFORE UPDATE ON article_ticket
FOR EACH ROW
BEGIN
	declare Total_Stock int;
	declare Cod_Article int;
	select a.STOCK into Total_Stock from article a where new.COD_ART_AT = a.COD_ART;
	select a.COD_ART into Cod_Article from article a where new.COD_ART_AT = a.COD_ART;
	if (Total_Stock + old.AMOUNT) < new.AMOUNT then
		set new.AMOUNT = old.AMOUNT;
	end if;
END;
//
DELIMITER ;

#Trigger to reduce stock every time there is a new sale

DELIMITER //
DROP TRIGGER IF EXISTS SoldArticle //
CREATE TRIGGER SoldArticle
AFTER INSERT ON article_ticket
FOR EACH ROW
BEGIN
	declare Pre_Stock int;
	declare Cod_Article int;
	select a.STOCK into Pre_Stock from article a where new.COD_ART_AT = a.COD_ART;
	select a.COD_ART into Cod_Article from article a where new.COD_ART_AT = a.COD_ART;
	update article set STOCK = Pre_Stock - new.AMOUNT where COD_ART = new.COD_ART_AT;
END;
//
DELIMITER ;

#Tigger to rise the stock of an article when a row of article_ticket is deleted

DELIMITER //
DROP TRIGGER IF EXISTS CompleteReturn //
CREATE TRIGGER CompleteReturn
AFTER DELETE ON article_ticket
FOR EACH ROW
BEGIN
	update article a set a.STOCK = a.STOCK + old.AMOUNT where COD_ART = old.COD_ART_AT;
	call CheckATRows(old.COD_TIC_AT) ;
END;
//
DELIMITER ;

#Trigger to call the procedure CheckATRows

DELIMITER //
DROP TRIGGER IF EXISTS DeleteArticle //
CREATE TRIGGER DeleteArticle
AFTER DELETE ON article
FOR EACH ROW
BEGIN
	declare C_T int;
	select at2.COD_TIC_AT into C_T from article_ticket at2 where at2.COD_ART_AT = old.COD_ART;
	call CheckATRows(C_T);
END;
//
DELIMITER ;

#Procedure that removes a ticket from the database if it doesn't appear in the table article_ticket

DELIMITER //
DROP PROCEDURE IF EXISTS CheckATRows//
CREATE PROCEDURE CheckATRows(IN C_T int)
	BEGIN
		declare C_A int;
		select at2.COD_ART_AT into C_A from article_ticket at2 where C_T = at2.COD_TIC_AT limit 1;
		if C_A is null then
			delete from ticket t where t.COD_TIC = C_T;
		end if;
	END;
//
DELIMITER ;

#Trigger to rise or reduce the stock when there's a change in a ticket.

DELIMITER //
DROP TRIGGER IF EXISTS PartialReturn //
CREATE TRIGGER PartialReturn
AFTER UPDATE ON article_ticket
FOR EACH ROW
BEGIN
	declare Cod_Article int;
	select a.COD_ART into Cod_Article from article a where old.COD_ART_AT = a.COD_ART;
	update article a set a.STOCK = a.STOCK - (new.AMOUNT - old.AMOUNT) where COD_ART = old.COD_ART_AT;
END;
//
DELIMITER ;

#View for the Admin to chech the available articles

drop view if exists AvailableArticles ;

create view AvailableArticles as
select a.COD_ART, a.STOCK, a2.TITLE, p.BAND from article a
left join album a2 ON a.COD_ART = a2.COD_CD 
left join poster p on a.COD_ART = p.COD_POSTER
where a.STOCK > 0
;

#View to see the resume of the tickets ordered by date

drop view if exists AdminViewTickets ;

create view AdminViewTickets as
select t.COD_TIC , at2.COD_ART_AT , at2.AMOUNT , t.DATE_TIME from article_ticket at2 
join ticket t on at2.COD_TIC_AT = t.COD_TIC 
order by t.DATE_TIME desc;

#View to check the top 5 articles

drop view if exists Top5Articles ;

create view Top5Articles as
select at2.COD_ART_AT , a2.TITLE , p.BAND , a.STOCK , a.PRICE , t.UD_SOLD from article_ticket at2
join article a on at2.COD_ART_AT = a.COD_ART
left join album a2 ON a.COD_ART = a2.COD_CD 
left join poster p on a.COD_ART = p.COD_POSTER
join (select at3.COD_ART_AT as C_AT, sum(at3.AMOUNT) as UD_SOLD from article_ticket at3 group by at3.COD_ART_AT) as t on t.C_AT = at2.COD_ART_AT
group by at2.COD_ART_AT order by t.Ud_Sold desc limit 5;

#Creation of user admin

DROP USER IF EXISTS 'admin';
CREATE USER 'admin' IDENTIFIED BY 'admin';
GRANT UPDATE, DELETE, INSERT, SELECT ON article TO 'admin';
GRANT UPDATE, DELETE, INSERT, SELECT ON album TO 'admin';
GRANT UPDATE, DELETE, INSERT, SELECT ON poster TO 'admin';
GRANT EXECUTE ON PROCEDURE CheckATRows TO 'admin';
GRANT SELECT ON Ticket TO 'admin';
GRANT SELECT ON AdminViewTickets TO 'admin';
GRANT SELECT ON Top5Articles TO 'admin';
