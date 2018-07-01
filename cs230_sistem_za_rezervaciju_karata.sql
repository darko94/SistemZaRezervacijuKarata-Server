-- phpMyAdmin SQL Dump
-- version 4.5.1
-- http://www.phpmyadmin.net
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2018 at 04:46 PM
-- Server version: 10.1.9-MariaDB
-- PHP Version: 5.6.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cs230_sistem_za_rezervaciju_karata`
--

-- --------------------------------------------------------

--
-- Table structure for table `film`
--

CREATE TABLE `film` (
  `id` int(11) NOT NULL,
  `drzava` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `duzina_trajanja` int(11) DEFAULT NULL,
  `godina` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `naslov` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `opis` varchar(4000) COLLATE utf8mb4_bin DEFAULT NULL,
  `originalni_naslov` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `pocetak_prikazivanja` datetime DEFAULT NULL,
  `youtube_url` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `slika_url` varchar(4000) COLLATE utf8mb4_bin DEFAULT NULL,
  `zanr` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `film`
--

INSERT INTO `film` (`id`, `drzava`, `duzina_trajanja`, `godina`, `naslov`, `opis`, `originalni_naslov`, `pocetak_prikazivanja`, `youtube_url`, `slika_url`, `zanr`) VALUES
(1, 'SAD', 94, '2017', 'Bilo jednom u Veneciji', 'Stiv Ford (Brus Vilis) je privatni detektiv iz Los An?elesa koji se povukao iz posla ali ne sasvim. Njegov profesionalni i privatni život se ruši kada njegovog voljenog psa ljubimca Badija ukrade ozloglašena banda. ?itav niz ludih okolnosti dovodi ga dotle da se kladi sa bandom, dok ga u isto vreme ga progone dva osvetoljubiva brata Samoanca, debelokožca, i još nekoliko drugih mutnih likova. Kažu da je pas najbolji ?ovekov prijatelj, a Stiv nam pokazuje koliko daleko je ?ovek spreman da ode da bi ponovo bio sa njim.', 'Once Upon a Time in Venice', '2017-07-26 02:00:00', 'https://www.youtube.com/embed/dx6PEzBhtwM', 'https://img.cineplexx.at/media/rs/inc/movies_licences/OUATIV-Banner-223x324.jpg', 'komedija'),
(12, 'SAD', 117, '2018', 'Deadpool 2', 'Ne bi li nekako sebi zabiberio život, a u potrazi za kondenzatorom fluksa, Vejd mora da se bori protiv nindži, jakuza i ?opora seksualno agresivnih pasa, putuju?i po svetu i otkrivaju?i važnost porodice, prijatelja i ukusa. Ono što ?e na?i jeste dašak avanture i nešto za ?ime je žudeo, a to je šolja za kafu s titulom „Najbolji ljubavnik na svetu" na njoj.', 'Deadpool 2', '2018-05-17 02:00:00', 'https://www.youtube.com/embed/D86RtevtfrA', 'https://img.cineplexx.at/media/rs/inc/movies_licences/223_8.png', 'akcija, fantazija'),
(13, 'SAD', 100, '2018', 'Ti juriš!', 'Svakog meseca u godini, petoro prijatelja koji vole da se takmi?e, igraju Tag od prvog razreda škole – rizikuju?i i posao i sopstvene odnose. Ove godine igra se podudara s datumom ven?anja jedinog neporaženog igra?a, što bi napokon trebalo da ga u?initi lakom metom. Ali on zna da oni dolaze…i spreman je!', 'Tag', '2018-06-28 02:00:00', 'https://www.youtube.com/embed/_AohA5bSLgc', 'https://img.cineplexx.at/media/rs/inc/movies_licences/B1_TAG_SRB.jpg', 'komedija'),
(14, 'Island', 85, '2018', 'Cvrle - nikad ne letiš sam', 'Glavni junak je Cvrle koji nije nau?io da leti na vreme kad njegova porodica migrira u toplije krajeve, te on mora da na?e na?in da se izbori sa zimom, neprijateljima, ali i samim sobom, kako bi ponovo bio sa svojom porodicom u prole?e. Cvrle kre?e u veliku avanturu u kojoj ?e mu pomo?i I prijatelji.', 'Ploey - you never fly alone', '2018-04-19 02:00:00', 'https://www.youtube.com/embed/oOqqSLxi0XE', 'https://img.cineplexx.at/media/rs/inc/movies_licences/223_sr.png', 'animirani');

-- --------------------------------------------------------

--
-- Table structure for table `korisnik`
--

CREATE TABLE `korisnik` (
  `id` int(11) NOT NULL,
  `datum_rodjenja` datetime DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `ime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `password` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `pol` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `prezime` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `korisnik`
--

INSERT INTO `korisnik` (`id`, `datum_rodjenja`, `email`, `ime`, `password`, `pol`, `prezime`, `username`) VALUES
(1, '1994-03-30 00:00:00', 'darkomisic94@gmail.com', 'Darko', 'darko', 'Muški', 'Misic', 'darko'),
(2, NULL, NULL, NULL, NULL, NULL, NULL, NULL),
(3, '1994-03-30 02:00:00', 'darkomisic94@gmail.com', 'Darko', 'darko', 'Muški', 'Misic', 'darko'),
(4, '1994-03-30 02:00:00', 'darkomisic94@gmail.com', 'Darko', 'darko', 'Muaki', 'Misic', 'darko'),
(5, NULL, 'darko@test.and', '', '', '', '', ''),
(6, NULL, 'darko@test.andr', '', '', '', NULL, ''),
(7, NULL, 'dd@sss.bb', '', '', '', NULL, ''),
(8, NULL, 'dd@sss.bb', '', '', '', NULL, ''),
(9, NULL, 'dd@sss.bb', '', '', '', NULL, ''),
(10, NULL, 'dd@sss.bbc', '', '', '', NULL, ''),
(11, NULL, 'dd@sss.bbt', '', '', '', NULL, ''),
(12, NULL, 'dd@sss.bbt', '', '', '', NULL, ''),
(13, NULL, 'dd@sss.bbo', '', '', '', NULL, ''),
(14, NULL, 'dd@sss.bbo', '', '', '', NULL, ''),
(15, NULL, 'eee@xxx.mmm', '', '', '', NULL, ''),
(16, NULL, 'eee@xxx.mmi', '', '', '', NULL, ''),
(17, NULL, 'eee@xxx.mmi', '', '', '', NULL, ''),
(18, NULL, 'eee@xxx.mmi', '', '', '', NULL, ''),
(19, NULL, 'tt@dd.mm', '', '', '', NULL, ''),
(20, NULL, 'tt@dd.mm', '', '', '', NULL, ''),
(21, NULL, 'ads@sdaf.dd', '', '', '', NULL, ''),
(22, NULL, 'mar@nnn.mm', '', '', '', NULL, '');

-- --------------------------------------------------------

--
-- Table structure for table `projekcija`
--

CREATE TABLE `projekcija` (
  `id` int(11) NOT NULL,
  `slobodno_sedista` int(11) DEFAULT NULL,
  `film_id` int(11) DEFAULT NULL,
  `sala_id` int(11) DEFAULT NULL,
  `datum` date DEFAULT NULL,
  `vreme` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `projekcija`
--

INSERT INTO `projekcija` (`id`, `slobodno_sedista`, `film_id`, `sala_id`, `datum`, `vreme`) VALUES
(4, 50, 1, 16, '2018-05-16', '18:00'),
(6, 50, 1, 16, '2018-06-15', '12:00'),
(7, 50, 12, 16, '2018-06-23', '20:00'),
(8, 50, 12, 16, '2018-06-24', '20:00'),
(9, 80, 1, 21, '2018-07-02', '20:00');

-- --------------------------------------------------------

--
-- Table structure for table `rezervacija`
--

CREATE TABLE `rezervacija` (
  `id` int(11) NOT NULL,
  `broj_karata` int(11) DEFAULT NULL,
  `korisnik_id` int(11) DEFAULT NULL,
  `projekcija_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `rezervacija`
--

INSERT INTO `rezervacija` (`id`, `broj_karata`, `korisnik_id`, `projekcija_id`) VALUES
(3, 3, 1, 4),
(6, 3, 1, 6),
(7, 2, 1, 8),
(11, 3, 1, 4),
(22, 4, 22, 9);

-- --------------------------------------------------------

--
-- Table structure for table `sala`
--

CREATE TABLE `sala` (
  `id` int(11) NOT NULL,
  `broj_sedista` int(11) DEFAULT NULL,
  `naziv` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
  `tehnologija` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `sala`
--

INSERT INTO `sala` (`id`, `broj_sedista`, `naziv`, `tehnologija`) VALUES
(16, 50, 'Sala 1', '3D'),
(20, 30, 'Sala 2', '3D'),
(21, 80, 'Sala 3', '2D');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `username` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `password` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`username`, `password`, `enabled`) VALUES
('admin', 'admin', 1),
('darko', 'darko', 1);

-- --------------------------------------------------------

--
-- Table structure for table `user_roles`
--

CREATE TABLE `user_roles` (
  `user_role_id` int(11) NOT NULL,
  `username` varchar(45) COLLATE utf8mb4_bin NOT NULL,
  `role` varchar(45) COLLATE utf8mb4_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;

--
-- Dumping data for table `user_roles`
--

INSERT INTO `user_roles` (`user_role_id`, `username`, `role`) VALUES
(2, 'admin', 'ROLE_ADMIN'),
(1, 'admin', 'ROLE_USER'),
(3, 'darko', 'ROLE_USER');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `film`
--
ALTER TABLE `film`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `korisnik`
--
ALTER TABLE `korisnik`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `projekcija`
--
ALTER TABLE `projekcija`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FK8AFDCB6054A62AB4` (`sala_id`),
  ADD KEY `FK8AFDCB60B29B9714` (`film_id`);

--
-- Indexes for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKB0AC91DC53C3AE14` (`projekcija_id`),
  ADD KEY `FKB0AC91DC345B4994` (`korisnik_id`);

--
-- Indexes for table `sala`
--
ALTER TABLE `sala`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`username`);

--
-- Indexes for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_role_id`),
  ADD UNIQUE KEY `uni_username_role` (`role`,`username`),
  ADD KEY `fk_username_idx` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `film`
--
ALTER TABLE `film`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;
--
-- AUTO_INCREMENT for table `korisnik`
--
ALTER TABLE `korisnik`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `projekcija`
--
ALTER TABLE `projekcija`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
--
-- AUTO_INCREMENT for table `rezervacija`
--
ALTER TABLE `rezervacija`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;
--
-- AUTO_INCREMENT for table `sala`
--
ALTER TABLE `sala`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;
--
-- AUTO_INCREMENT for table `user_roles`
--
ALTER TABLE `user_roles`
  MODIFY `user_role_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
--
-- Constraints for dumped tables
--

--
-- Constraints for table `projekcija`
--
ALTER TABLE `projekcija`
  ADD CONSTRAINT `FK8AFDCB6054A62AB4` FOREIGN KEY (`sala_id`) REFERENCES `sala` (`id`),
  ADD CONSTRAINT `FK8AFDCB60B29B9714` FOREIGN KEY (`film_id`) REFERENCES `film` (`id`);

--
-- Constraints for table `rezervacija`
--
ALTER TABLE `rezervacija`
  ADD CONSTRAINT `FKB0AC91DC345B4994` FOREIGN KEY (`korisnik_id`) REFERENCES `korisnik` (`id`),
  ADD CONSTRAINT `FKB0AC91DC53C3AE14` FOREIGN KEY (`projekcija_id`) REFERENCES `projekcija` (`id`);

--
-- Constraints for table `user_roles`
--
ALTER TABLE `user_roles`
  ADD CONSTRAINT `fk_username` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
