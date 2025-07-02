CREATE TABLE questions (
     id	char(36)	NOT NULL,
     writer	char(36)	NOT NULL,
     content	varchar(2000)	NOT NULL,
     date	timestamp	NOT NULL,
     title	varchar(64)	NOT NULL
);

CREATE TABLE users (
     id	char(36)	NOT NULL,
     email	varchar(254)	NOT NULL,
     password	varchar(64)	NOT NULL,
     salt	varchar(8)	NULL,
     nickname	varchar(10)	NOT NULL
);

CREATE TABLE answers (
   id	char(36)	NOT NULL,
   writer	char(36)	NOT NULL,
   question_id	char(36)	NOT NULL,
   content	varchar(2000)	NOT NULL,
   date	timestamp	NOT NULL
);