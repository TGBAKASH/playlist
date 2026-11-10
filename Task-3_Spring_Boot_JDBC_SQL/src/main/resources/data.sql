-- =========================================================
-- SQL Initial Data: All 16 Harris Jayaraj Tracks from Receipt
-- Demonstrates DML (Data Manipulation Language) for Task 3
-- =========================================================

INSERT INTO songs (id, title, movie, release_year, duration, query, tags, play_count, is_favorite) VALUES
(0, 'MUN ANDHI', '7 Aum Arivu', '2011', '6:14', 'Mun Andhi Harris Jayaraj', 'surya,romantic', 1420, TRUE),
(1, 'MAZHAI VARA POGUDHAE', 'Yennai Arindhaal', '2015', '5:42', 'Mazhai Vara Pogudhae', 'romantic', 980, FALSE),
(2, 'PUYALAE PUYALAE', 'Kovil', '2004', '4:58', 'Puyale Puyale', 'classic', 750, FALSE),
(3, 'ONDRA RENDA', 'Kaakha Kaakha', '2003', '5:06', 'Ondra Renda', 'surya,classic,romantic', 1650, TRUE),
(4, 'RAGASIYA KANAVUGAL', 'Bheema', '2008', '5:59', 'Ragasiya Kanavugal', 'romantic', 890, FALSE),
(5, 'THEE ILLAI', 'Engeyum Kadhal', '2011', '5:27', 'Thee Illai', 'romantic', 640, FALSE),
(6, 'LESA LESA', 'Lesa Lesa', '2003', '4:41', 'Lesa Lesa Harris Jayaraj', 'classic,romantic', 1120, FALSE),
(7, 'EN FRIENDA POLA', 'Nanban', '2012', '3:57', 'En Frienda Pola', 'classic', 1340, FALSE),
(8, 'SUTTUM VIZHI', 'Ghajini', '2005', '5:18', 'Suttum Vizhi', 'surya,classic,romantic', 1890, TRUE),
(9, 'ENNAMO YEDHO', 'Ko', '2011', '5:34', 'Ennamo Yeadho', 'romantic', 1230, FALSE),
(10, 'UNNAALE UNNAALE', 'Unnale Unnale', '2007', '4:43', 'Unnale Unnale', 'romantic', 1480, TRUE),
(11, 'EN ANBE', 'Sathyam', '2008', '6:07', 'En Anbe Sathyam', 'romantic', 820, FALSE),
(12, 'AATHANGARA ORATHIL', 'Yaan', '2014', '4:24', 'Aathangara Orathil', 'romantic', 790, FALSE),
(13, 'PAARTHA MUDHAL', 'Vettaiyaadu Vilaiyaadu', '2006', '6:08', 'Paartha Mudhal', 'romantic', 1560, TRUE),
(14, 'AVA ENNA', 'Vaaranam Aayiram', '2008', '5:18', 'Ava Enna Vaaranam Aayiram', 'surya,romantic', 1980, TRUE),
(15, 'POOPOL POOPOL', 'Minnalae', '2001', '1:57', 'Poopol Poopol', 'classic,romantic', 910, FALSE);

-- Seed initial favorites
INSERT INTO favorites (song_id) VALUES (0), (3), (8), (10), (13), (14);
