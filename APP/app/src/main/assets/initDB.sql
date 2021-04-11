CREATE TABLE game (
    id INTEGER NOT NULL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    level_id INTEGER NOT NULL,
    score FLOAT NOT NULL
);

CREATE TABLE level (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT,
    difficulty INTEGER NOT NULL,
    game_mode TEXT
);

CREATE TABLE user (
    id INTEGER NOT NULL PRIMARY KEY,
    name TEXT,
    firstname TEXT,
    current_user INTEGER NOT NULL
);

INSERT INTO user VALUES (
    0,
    "Bob",
    "Loustic",
    0
);

INSERT INTO user VALUES (
    1,
    "Marie",
    "Loustiquette",
    0
);

INSERT INTO level VALUES (
    0,
    "Les additions",
    1,
    "addition"
);

INSERT INTO level VALUES (
    1,
    "Les additions",
    2,
    "addition"
);

INSERT INTO level VALUES (
    2,
    "Les additions",
    3,
    "addition"
);

INSERT INTO level VALUES (
    3,
    "Les soustractions",
    1,
    "soustraction"
);

INSERT INTO level VALUES (
    4,
    "Les soustractions",
    2,
    "soustraction"
);

INSERT INTO level VALUES (
    5,
    "Les soustractions",
    3,
    "soustraction"
);

INSERT INTO level VALUES (
    6,
    "Les multiplications",
    1,
    "multiplication"
);

INSERT INTO level VALUES (
    7,
    "Les multiplications",
    2,
    "multiplication"
);

INSERT INTO level VALUES (
    8,
    "Les multiplications",
    3,
    "multiplication"
);

INSERT INTO level VALUES (
    9,
    "Les divisions",
    1,
    "division"
);

INSERT INTO level VALUES (
    10,
    "Les divisions",
    2,
    "division"
);

INSERT INTO level VALUES (
    11,
    "Les divisions",
    3,
    "division"
);

INSERT INTO level VALUES (
    12,
    "Les capitales",
    1,
    "géographie"
);

INSERT INTO level VALUES (
    13,
    "Conjugaison",
    1,
    "conjugaison"
);





