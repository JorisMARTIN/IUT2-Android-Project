CREATE TABLE question (
    id INTEGER NOT NULL PRIMARY KEY,
    question TEXT,
    answers TEXT,
    good_answer INTEGER NOT NULL, -- The index in the future array after parsing of answers --
    tag TEXT
);

INSERT INTO question VALUES
(1, "Quelle est la capitale de la France ?", "Lyon, Marseille, Paris", 2, "géographie"),
(2, "Quelle est la capitale de l'Italie ?", "Rome, Turin, Naples", 0, "géographie"),
(3, "Quelle est la capitale de l'Angleterre ?", "Londres, Manchester, Cambridge", 0, "géographie"),
(4, "Quelle est la capitale de l'Allemagne ?", "Munich, Berlin, Dortmund", 1, "géographie"),
(5, "Quelle est la capitale de l'Espagne ?", "Séville, Barcelone, Madrid", 2, "géographie"),
(6, "Quelle est la capitale des Etats-Unis ?", "New-York, Washintown, Las-Vegas", 1, "géographie"),
(7, "Quelle est la capitale du Portugal ?", "Lisbonne, Porto, Braga", 0, "géographie"),
(8, "Quelle est la capitale de la Belgique ?", "Bruges, Namur, Bruxelles", 2, "géographie"),
(9, "Quelle est la capitale de la Russie ?", "Saint-Pétersbourg, Moscou, Kazan", 1, "géographie"),
(10, "Quelle est la capitale de la Chine ?", "Pékin, Shangai, Hong-Kong", 0, "géographie"),
(11, "Quelle est la capitale du Japon ?", "Hiroshima, Kyoto, Tokyo", 2, "géographie"),
(12, "Quelle est la capitale de l'Australie ?", "Sidney, Camberra, Melbourne", 1, "géographie"),
(13, "Quelle est la capitale de l'Inde ?", "Calcutta, Bombay, New-Delhi", 2, "géographie"),
(14, "Quelle est la capitale de l'Algérie ?", "Alger, rie, Oran", 0, "géographie"),
(15, "Quelle est la capitale de l'Argentine ?", "Buenos-Aire, Cordoba, Mendoza", 0, "géographie"),
(16, "Quelle est la capitale du Brezil ?", "Rio-de-janeiro, Sao-Paulo, Brasilia", 2, "géographie"),
(17, "Quelle est la capitale du Maroc ?", "Casablanca, Marrakech, Rabat", 2, "géographie"),
(18, "Quelle est la capitale de la Tunisie ?", "Tunis, ie, Sfax", 0, "géographie"),
(19, "Quelle est la capitale du Mexique ?", "Monterrey, Mexico, Guadalajara", 1, "géographie"),
(20, "Quelle est la capitale du Canada ?", "Montréal, Ottawa, Toronto", 1, "géographie");
