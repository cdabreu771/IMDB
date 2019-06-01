CREATE TABLE MOVIES
(MID VARCHAR(100) NOT NULL,
NAME VARCHAR(200),
YEAR VARCHAR (100),
RATING VARCHAR(100),
NUM_REVIEW VARCHAR(100),
PRIMARY KEY(MID)
);
CREATE INDEX MOVIE_INDEX
ON MOVIES (RATING);
CREATE INDEX MOVIE_NUMREVIEW_INDEX
ON MOVIES (NUM_REVIEW);
CREATE INDEX MOVIE_YEAR
ON MOVIES (YEAR);

CREATE TABLE MOVIE_GENRES
(MID VARCHAR(100) NOT NULL,
GENRE VARCHAR(100)NOT NULL,
PRIMARY KEY (MID,GENRE),
FOREIGN KEY (MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);
CREATE INDEX GENRE_INDEX
ON MOVIE_GENRES (GENRE);
CREATE INDEX GENRE_MID_INDEX
ON MOVIE_GENRES (MID);

CREATE TABLE MOVIE_DIRECTORS
(MID VARCHAR(100)NOT NULL,
DID VARCHAR(100)NOT NULL,
NAME VARCHAR(100),
PRIMARY KEY (MID,DID),
FOREIGN KEY (MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);

CREATE TABLE MOVIE_ACTORS
(MID VARCHAR(100)NOT NULL,
AID VARCHAR(100)NOT NULL,
NAME VARCHAR(100),
RANKING VARCHAR(100),
PRIMARY KEY(MID, AID),
FOREIGN KEY (MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);

CREATE TABLE MOVIE_COUNTRIES
(MID VARCHAR(100) NOT NULL,
COUNTRY VARCHAR(100),
PRIMARY KEY(MID),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);
CREATE INDEX COUNTRY_INDEX
ON MOVIE_COUNTRIES (COUNTRY);

CREATE TABLE MOVIE_LOCATIONS
(MID VARCHAR(200) NOT NULL,
COUNTRY VARCHAR(200),
STATE VARCHAR(200),
CITY VARCHAR(200),
STREET VARCHAR(200),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);
CREATE INDEX LOCATION_INDEX
ON MOVIE_LOCATIONS (COUNTRY);

CREATE TABLE TAGS
(TID VARCHAR(100) NOT NULL,
TEXT VARCHAR(100),
PRIMARY KEY(TID)
);


CREATE TABLE MOVIE_TAGS
(MID VARCHAR(100) NOT NULL,
TID VARCHAR(100) NOT NULL,
TWEIGHT VARCHAR(100),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE,
FOREIGN KEY(TID) REFERENCES TAGS(TID) ON DELETE CASCADE
);
CREATE INDEX TAGS
ON MOVIE_TAGS (TID);
CREATE INDEX TAG_WEIGHT
ON MOVIE_TAGS (TWEIGHT);

CREATE TABLE USER_TAGGEDMOVIES
(UserID VARCHAR(100) NOT NULL,
MID VARCHAR(100) NOT NULL,
TID VARCHAR(100) NOT NULL,
DAY VARCHAR(100),
MONTH VARCHAR(100),
YEAR VARCHAR(100),
HOUR VARCHAR(100),
MIN VARCHAR(100),
SEC VARCHAR(100),
PRIMARY KEY(UserID, MID, TID),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE,
FOREIGN KEY(TID) REFERENCES TAGS(TID) ON DELETE CASCADE
);

CREATE TABLE USER_TAGGEDMOVIES_TIMESTAMPS
(UserID VARCHAR(100) NOT NULL,
MID VARCHAR(100) NOT NULL,
TID VARCHAR(100) NOT NULL,
TIMESTAMP VARCHAR(100),
PRIMARY KEY (UserID, MID, TID),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE,
FOREIGN KEY(TID) REFERENCES TAGS(TID) ON DELETE CASCADE
);

CREATE TABLE USER_RATEDMOVIES
(UserID VARCHAR(100) NOT NULL,
MID VARCHAR(100) NOT NULL,
RATING VARCHAR(100),
DAY VARCHAR(100),
MONTH VARCHAR(100),
YEAR VARCHAR(100),
HOUR VARCHAR(100),
MIN VARCHAR(100),
SEC VARCHAR(100),
PRIMARY KEY(UserID,MID),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);

CREATE TABLE USER_RATEDMOVIES_TIMESTAMPS
(UserID VARCHAR(100) NOT NULL,
MID VARCHAR(100) NOT NULL,
RATING VARCHAR(100),
TIMESTAMP VARCHAR(100),
PRIMARY KEY (UserID,MID),
FOREIGN KEY(MID) REFERENCES MOVIES(MID) ON DELETE CASCADE
);