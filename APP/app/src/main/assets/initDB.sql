CREATE TABLE level (
    id INTEGER NOT NULL PRIMARY KEY,
    activity TEXT,
    difficulty INTEGER NOT NULL,
    hasBeenPlayed INTEGER NOT NULL,
    score INTEGER NOT NULL
);

INSERT INTO level VALUES (
    0,
    "Les multiplications",
    1,
    0,
    0
);

INSERT INTO level VALUES (
    1,
    "Les multiplications",
    2,
    0,
    0
);

INSERT INTO level VALUES (
    2,
    "Les multiplications",
    3,
    0,
    0
);
