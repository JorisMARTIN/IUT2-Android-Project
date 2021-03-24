CREATE TABLE level (
    id INT PRIMARY KEY,
    activity VARCHAR(200),
    difficulty INT,
    hasBeenPlayed BOOLEAN,
    score INT
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
