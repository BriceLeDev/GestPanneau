-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 18 juin 2023 à 16:04
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `panneau_pub`
--

-- --------------------------------------------------------

--
-- Structure de la table `abonnement`
--

CREATE TABLE `abonnement` (
  `IdAbon` int(11) NOT NULL,
  `DateDebut` date NOT NULL,
  `DateFin` date NOT NULL,
  `TelClient` int(11) NOT NULL,
  `Nom_Client` varchar(50) NOT NULL,
  `NbrJours` int(11) NOT NULL,
  `NbrPanneau` int(11) NOT NULL,
  `MontantTotApayer` int(11) NOT NULL,
  `MontantPayer` int(11) NOT NULL,
  `MontantRestant` int(11) NOT NULL,
  `nom_Utilisateur` varchar(25) NOT NULL,
  `DateJrAbn` date NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `abonnement`
--

INSERT INTO `abonnement` (`IdAbon`, `DateDebut`, `DateFin`, `TelClient`, `Nom_Client`, `NbrJours`, `NbrPanneau`, `MontantTotApayer`, `MontantPayer`, `MontantRestant`, `nom_Utilisateur`, `DateJrAbn`) VALUES
(1, '2023-05-30', '2023-06-06', 99898195, 'ONG La Paix', 7, 6, 28500, 28500, 0, 'AB', '2023-05-30'),
(2, '2023-05-31', '2023-06-29', 99898195, 'ONG La Paix', 29, 4, 17500, 17500, 0, 'AB', '2023-05-30'),
(4, '2023-05-30', '2023-06-01', 99898195, 'ONG La Paix', 2, 2, 10500, 10500, 0, 'AB', '2023-05-30'),
(6, '2023-05-30', '2023-06-07', 98812214, 'ModeBabe', 8, 2, 9500, 9500, 0, 'AB', '2023-05-30'),
(7, '2023-05-06', '2023-05-31', 90748238, 'BriceONG', 25, 4, 4000, 4000, 0, 'AB', '2023-05-30'),
(8, '2023-05-06', '2023-05-31', 90748238, 'BriceONG', 25, 6, 7000, 7000, 0, 'AB', '2023-05-30'),
(9, '2023-05-31', '2023-06-14', 92223473, 'BeautyShop', 14, 4, 20000, 20000, 0, 'AB', '2023-05-30'),
(10, '2023-05-31', '2023-06-15', 99898195, 'ONG La Paix', 15, 4, 12000, 12000, 0, 'AB', '2023-05-30'),
(11, '2023-06-01', '2023-06-30', 99898195, 'ONG La Paix', 29, 3, 8500, 8500, 0, 'AB', '2023-06-01'),
(12, '2023-06-01', '2023-06-23', 99898195, 'ONG La Paix', 22, 1, 4500, 4500, 0, 'AB', '2023-06-01'),
(13, '2023-06-01', '2023-06-30', 99898195, 'ONG La Paix', 29, 2, 2500, 2500, 0, 'AB', '2023-06-01'),
(15, '2023-06-18', '2023-06-30', 92223473, 'BeautyShop', 12, 5, 216000, 216000, 0, 'AB', '2023-06-18');

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

CREATE TABLE `admin` (
  `id_Admin` int(11) NOT NULL,
  `Nom_Admin` varchar(25) NOT NULL,
  `PasseWrdAdmin` varchar(100) NOT NULL,
  `telephone_Admin` int(12) NOT NULL,
  `OptionPersonnel` varchar(14) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `admin`
--

INSERT INTO `admin` (`id_Admin`, `Nom_Admin`, `PasseWrdAdmin`, `telephone_Admin`, `OptionPersonnel`) VALUES
(6, 'eztr', 'rtytryr', 21343, 'Personnel'),
(8, 'ZERET', 'ZRET', 12345, 'Administrateur'),
(10, 'Brice', 'Brice1234', 99899988, 'Personnel'),
(11, 'AB', 'AB123', 99898195, 'Administrateur');

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `Id_Client` int(11) NOT NULL,
  `Nom_Client` varchar(50) NOT NULL,
  `Adresse_Client` varchar(50) NOT NULL,
  `Tel_Client` int(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`Id_Client`, `Nom_Client`, `Adresse_Client`, `Tel_Client`) VALUES
(3, 'Agri.ComCulture', 'Amoutiévé', 99898199),
(5, 'ONG La Paix', 'TokoinGBADAG', 99898195),
(6, '11122', 'LKJJLKJJK', 98999954),
(7, 'BeautyShop', 'Lomé', 92223473),
(8, 'ModeBabe', 'Kegué', 98812214),
(9, 'FOOT+BALL', 'Tokoin Trésor', 98998829),
(10, 'BriceONG', 'Lomé', 90748238),
(12, 'RERTFER', 'RERFE', 23323223);

-- --------------------------------------------------------

--
-- Structure de la table `ligneabonnement`
--

CREATE TABLE `ligneabonnement` (
  `NumLignAbn` int(11) NOT NULL,
  `Idpan` int(11) NOT NULL,
  `Itineraire` varchar(50) NOT NULL,
  `Localisation` varchar(50) NOT NULL,
  `IdClient` int(11) NOT NULL,
  `DateDebut` date NOT NULL,
  `DateFin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ligneabonnement`
--

INSERT INTO `ligneabonnement` (`NumLignAbn`, `Idpan`, `Itineraire`, `Localisation`, `IdClient`, `DateDebut`, `DateFin`) VALUES
(1, 2, 'Bd d\'Eyadéma', 'Tsévier', 5, '2023-05-30', '2023-06-06'),
(2, 1, 'Bd de la Kara', 'Adéwi', 5, '2023-05-31', '2023-06-29'),
(3, 2, 'Bd d\'Eyadéma', 'Tsévier', 5, '2023-05-31', '2023-06-29'),
(4, 3, 'Bd du 13 janvier', 'Dékon', 5, '2023-05-31', '2023-06-29'),
(5, 4, 'Bd de la Kara', 'Gbossimé', 5, '2023-05-31', '2023-06-29'),
(6, 6, 'Bd d\'Eyadéma', 'Agoè1', 5, '2023-05-30', '2023-06-15'),
(7, 6, 'Bd d\'Eyadéma', 'Agoè1', 5, '2023-05-30', '2023-06-15'),
(8, 5, 'Bd d\'Eyadéma', 'Agoè', 5, '2023-05-30', '2023-06-15'),
(9, 7, 'Bd d\'Eyadéma', 'Tsévié', 5, '2023-05-30', '2023-06-01'),
(10, 8, 'Bd d\'Eyadéma', 'Lycée Tokoin', 5, '2023-05-30', '2023-06-01'),
(11, 9, 'Bd d\'Eyadéma', 'Amoutiévé', 10, '2023-05-30', '2023-06-15'),
(12, 10, 'Bd d\'Eyadéma', 'Notsé1', 10, '2023-05-30', '2023-06-15'),
(13, 11, 'Bd de Mono', 'Assigamé', 10, '2023-05-30', '2023-06-15'),
(14, 12, 'Bd de Mono', 'Aného', 8, '2023-05-30', '2023-06-07'),
(15, 13, 'Bd de Mono', 'Avépozo', 8, '2023-05-30', '2023-06-07'),
(16, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(17, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(18, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(19, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(20, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(21, 14, 'Bd d\'Eyadéma', 'Agoè2', 10, '2023-05-06', '2023-05-31'),
(22, 15, 'Bd d\'Eyadéma', 'Agoè3', 10, '2023-05-06', '2023-05-31'),
(23, 15, 'Bd d\'Eyadéma', 'Agoè3', 10, '2023-05-06', '2023-05-31'),
(24, 16, 'Bd d\'Eyadéma', 'Lycée Tokoin2', 7, '2023-05-31', '2023-06-14'),
(25, 16, 'Bd d\'Eyadéma', 'Lycée Tokoin2', 7, '2023-05-31', '2023-06-14'),
(26, 16, 'Bd d\'Eyadéma', 'Lycée Tokoin2', 7, '2023-05-31', '2023-06-14'),
(27, 16, 'Bd d\'Eyadéma', 'Lycée Tokoin2', 7, '2023-05-31', '2023-06-14'),
(28, 17, 'Bd d\'Eyadéma', 'Agoè4', 5, '2023-05-31', '2023-06-15'),
(29, 17, 'Bd d\'Eyadéma', 'Agoè4', 5, '2023-05-31', '2023-06-15'),
(30, 17, 'Bd d\'Eyadéma', 'Agoè4', 5, '2023-05-31', '2023-06-15'),
(31, 17, 'Bd d\'Eyadéma', 'Agoè4', 5, '2023-05-31', '2023-06-15'),
(32, 14, 'Bd d\'Eyadéma', 'Agoè2', 5, '2023-06-01', '2023-06-30'),
(33, 8, 'Bd d\'Eyadéma', 'Lycée Tokoin', 5, '2023-06-01', '2023-06-30'),
(34, 15, 'Bd d\'Eyadéma', 'Agoè3', 5, '2023-06-01', '2023-06-30'),
(35, 7, 'Bd d\'Eyadéma', 'Tsévié', 5, '2023-06-01', '2023-06-23'),
(36, 18, 'Bd de Mono', 'Assigmé5', 5, '2023-06-01', '2023-06-30'),
(37, 19, 'Bd Eyadéma', 'Agooè', 5, '2023-06-01', '2023-06-30'),
(38, 21, 'Bd des armés', 'Dékon1', 8, '2023-06-02', '2023-06-30'),
(39, 13, 'Bd de Mono', 'Avépozo', 7, '2023-06-18', '2023-06-30'),
(40, 10, 'Bd d\'Eyadéma', 'Notsé1', 7, '2023-06-18', '2023-06-30'),
(41, 6, 'Bd d\'Eyadéma', 'Agoè1', 7, '2023-06-18', '2023-06-30'),
(42, 11, 'Bd de Mono', 'Assigamé', 7, '2023-06-18', '2023-06-30'),
(43, 12, 'Bd de Mono', 'Aného', 7, '2023-06-18', '2023-06-30');

-- --------------------------------------------------------

--
-- Structure de la table `panneau`
--

CREATE TABLE `panneau` (
  `Id_panneau` int(11) NOT NULL,
  `Itineraire` varchar(50) NOT NULL,
  `Localisation_panneau` varchar(100) NOT NULL,
  `Taille` int(11) NOT NULL,
  `Prix` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `panneau`
--

INSERT INTO `panneau` (`Id_panneau`, `Itineraire`, `Localisation_panneau`, `Taille`, `Prix`) VALUES
(1, 'Bd de la Kara', 'Adéwi', 500, 5000),
(2, 'Bd d\'Eyadéma', 'Tsévier', 200, 4500),
(3, 'Bd du 13 janvier', 'Dékon', 500, 5000),
(4, 'Bd de la Kara', 'Gbossimé', 300, 3000),
(5, 'Bd d\'Eyadéma', 'Agoè', 400, 4000),
(6, 'Bd d\'Eyadéma', 'Agoè1', 500, 2000),
(7, 'Bd d\'Eyadéma', 'Tsévié', 2000, 4500),
(8, 'Bd d\'Eyadéma', 'Lycée Tokoin', 300, 6000),
(9, 'Bd d\'Eyadéma', 'Amoutiévé', 500, 2000),
(10, 'Bd d\'Eyadéma', 'Notsé1', 300, 4500),
(11, 'Bd de Mono', 'Assigamé', 300, 2000),
(12, 'Bd de Mono', 'Aného', 700, 7000),
(13, 'Bd de Mono', 'Avépozo', 400, 2500),
(14, 'Bd d\'Eyadéma', 'Agoè2', 100, 1000),
(15, 'Bd d\'Eyadéma', 'Agoè3', 200, 1500),
(16, 'Bd d\'Eyadéma', 'Lycée Tokoin2', 250, 5000),
(17, 'Bd d\'Eyadéma', 'Agoè4', 150, 3000),
(18, 'Bd de Mono', 'Assigmé5', 400, 1000),
(19, 'Bd Eyadéma', 'Agooè', 200, 1500),
(20, 'Rue de locam', 'Bè', 300, 2500),
(21, 'Bd des armés', 'Dékon1', 700, 4500);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD PRIMARY KEY (`IdAbon`),
  ADD KEY `TelClient` (`TelClient`);

--
-- Index pour la table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`id_Admin`);

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`Id_Client`),
  ADD UNIQUE KEY `Tel_Client` (`Tel_Client`);

--
-- Index pour la table `ligneabonnement`
--
ALTER TABLE `ligneabonnement`
  ADD PRIMARY KEY (`NumLignAbn`),
  ADD KEY `Idpan` (`Idpan`),
  ADD KEY `IdClient` (`IdClient`);

--
-- Index pour la table `panneau`
--
ALTER TABLE `panneau`
  ADD PRIMARY KEY (`Id_panneau`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `abonnement`
--
ALTER TABLE `abonnement`
  MODIFY `IdAbon` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT pour la table `admin`
--
ALTER TABLE `admin`
  MODIFY `id_Admin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `Id_Client` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT pour la table `ligneabonnement`
--
ALTER TABLE `ligneabonnement`
  MODIFY `NumLignAbn` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT pour la table `panneau`
--
ALTER TABLE `panneau`
  MODIFY `Id_panneau` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `abonnement`
--
ALTER TABLE `abonnement`
  ADD CONSTRAINT `abonnement_ibfk_1` FOREIGN KEY (`TelClient`) REFERENCES `client` (`Tel_Client`);

--
-- Contraintes pour la table `ligneabonnement`
--
ALTER TABLE `ligneabonnement`
  ADD CONSTRAINT `ligneabonnement_ibfk_1` FOREIGN KEY (`Idpan`) REFERENCES `panneau` (`Id_panneau`),
  ADD CONSTRAINT `ligneabonnement_ibfk_2` FOREIGN KEY (`IdClient`) REFERENCES `client` (`Id_Client`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
