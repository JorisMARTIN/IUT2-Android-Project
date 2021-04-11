CREATE TABLE question (
    id INTEGER NOT NULL PRIMARY KEY,
    question TEXT,
    answers TEXT,
    good_answer INTEGER NOT NULL, -- The index in the future array after parsing of answers --
    tag TEXT
);

INSERT INTO question VALUES
(1, "Quelle est la capitale de la France ?", "Lyon, Marseille, Paris", 2, "Geographie"),
(2, "Quelle est la capitale de l'Italie ?", "Rome, Turin, Naples", 0, "Geographie"),
(3, "Quelle est la capitale de l'Angleterre ?", "Londres, Manchester, Cambridge", 0, "Geographie"),
(4, "Quelle est la capitale de l'Allemagne ?", "Munich, Berlin, Dortmund", 1, "Geographie"),
(5, "Quelle est la capitale de l'Espagne ?", "Séville, Barcelone, Madrid", 2, "Geographie"),
(6, "Quelle est la capitale des Etats-Unis ?", "New-York, Washintown, Las-Vegas", 1, "Geographie"),
(7, "Quelle est la capitale du Portugal ?", "Lisbonne, Porto, Braga", 0, "Geographie"),
(8, "Quelle est la capitale de la Belgique ?", "Bruges, Namur, Bruxelles", 2, "Geographie"),
(9, "Quelle est la capitale de la Russie ?", "Saint-Pétersbourg, Moscou, Kazan", 1, "Geographie"),
(10, "Quelle est la capitale de la Chine ?", "Pékin, Shangai, Hong-Kong", 0, "Geographie"),
(11, "Quelle est la capitale du Japon ?", "Hiroshima, Kyoto, Tokyo", 2, "Geographie"),
(12, "Quelle est la capitale de l'Australie ?", "Sidney, Camberra, Melbourne", 1, "Geographie"),
(13, "Quelle est la capitale de l'Inde ?", "Calcutta, Bombay, New-Delhi", 2, "Geographie"),
(14, "Quelle est la capitale de l'Algérie ?", "Alger, rie, Oran", 0, "Geographie"),
(15, "Quelle est la capitale de l'Argentine ?", "Buenos-Aire, Cordoba, Mendoza", 0, "Geographie"),
(16, "Quelle est la capitale du Brezil ?", "Rio-de-janeiro, Sao-Paulo, Brasilia", 2, "Geographie"),
(17, "Quelle est la capitale du Maroc ?", "Casablanca, Marrakech, Rabat", 2, "Geographie"),
(18, "Quelle est la capitale de la Tunisie ?", "Tunis, ie, Sfax", 0, "Geographie"),
(19, "Quelle est la capitale du Mexique ?", "Monterrey, Mexico, Guadalajara", 1, "Geographie"),
(20, "Quelle est la capitale du Canada ?", "Montréal, Ottawa, Toronto", 1, "Geographie");
